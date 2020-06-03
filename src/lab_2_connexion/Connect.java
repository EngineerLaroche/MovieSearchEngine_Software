package lab_2_connexion;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

import lab_3_database.RSQResult;
import lab_3_database.ReadXMLType;
import lab_3_tools.OutilsFenetre;
import lab_3_tools.OutilsRecherche;
import oracle.jdbc.OracleResultSet;
import oracle.xdb.XMLType;


/**************************************************************
 * @CLASS_TITLE:	Connect
 * 
 * @Description: 	Class that connect to the database and keep 
 * 					a connection to it in order to send Query. 
 * 					This class is a little bit like a socket 
 * 					connection.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class Connect{

	/******************************
	 * Constantes - Connexion DB
	 ******************************/
	private static final String
	MSG_CONNEXION = "\n******************************************" +
			"\n*	Connexion à la DB réussi !	 *" +
			"\n******************************************";

	/******************************
	 * PATRON SINGLETON
	 ******************************/
	private static Connect connectDB = new Connect();

	/******************************
	 * Instance Class
	 ******************************/
	private RSQResult result = null;
	private PropertyChangeSupport changeSupport = null;

	/******************************
	 * Instance Class DB
	 ******************************/
	private ResultSet rs = null;
	private Connection dbConnect = null;
	private ResultSetMetaData rsmd = null;
	private PreparedStatement pstmt = null;

	/******************************
	 * Liste Resultats
	 ******************************/
	private ArrayList<String> resultats = null;

	/******************************
	 * Variable & primitive
	 ******************************/
	private boolean isConnected = false;
	private Address adresse = null;


	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Resumer:	Initialisation de la demande 
	 * 				d'information à la DB.
	 * 
	 ******************************************************/
	public Connect(){ 
		result = new RSQResult(); 
		changeSupport = new PropertyChangeSupport(this);
	}

	/******************************************************
	 * Open Connection
	 * 
	 * @Resumer:	Création de la connexion avec la DB.
	 * 
	 ******************************************************/
	public boolean openConnection(RSQUser user, Address address){
		this.adresse = address;

		try{ 
			Thread.sleep(1000);//Réduit les possibilités de surcharge (VPN ÉTS)
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); 
			changeSupport.firePropertyChange("connexionEnCours", false, true);
		} 
		catch (ClassNotFoundException e){
			System.err.println("(Connect) DB Driver Class not found");
			return false;
		} catch(Exception e) {
			System.err.println("(Connect) No driver found");
			return false;
		}

		System.err.println("(Connect) Connection String: " + 
				address.toString() + " " +
				user.getUsername() + " " +
				user.getPassword() + 
				"\n(Connect) Connection en cours ...");

		try {
			dbConnect = DriverManager.getConnection(address.toString(), 
					user.getUsername(), user.getPassword());

			if (dbConnect != null) {
				isConnected = true;
				System.out.println(MSG_CONNEXION); 
				changeSupport.firePropertyChange("connexionOuverte", false, isConnected);
			} else {isConnected = false;}
		}catch (SQLException e) {
			isConnected = false;
			changeSupport.firePropertyChange("connexionImpossible", false, true);
		}
		return isConnected;
	}

	/******************************************************
	 * Send Query (RSQQuery)
	 * 
	 * @Resumer:	Gère le type de requête et pointe vers
	 * 				la bonne méthode.
	 * 
	 ******************************************************/
	public boolean sendQuery(String query){	

		if (isConnected) {
			try {pstmt = (PreparedStatement)dbConnect.prepareStatement(query);} 
			catch (SQLException e) {e.printStackTrace();}

			if (query.trim().matches("INSERT") || query.trim().matches("UPDATE") || query.trim().matches("DELETE"))
				return sendUpdate(query);
			else return send(query);
		}else return false;
	}

	/******************************************************
	 * Send Update Query
	 * 
	 * @Resumer:	Permet la mise a jour des donnees recus
	 * 				dans la DB Oracle.
	 * 
	 ******************************************************/
	public boolean sendUpdate(String query){
		try {
			int test = pstmt.executeUpdate();
			if (test >= 0) 	return true;		
			else return false;
		} catch (SQLException e) {
			if ( e.getMessage().trim().equals("No ResultSet was produced")) 
				return true;
			else return false;	
		}
	}

	/******************************************************
	 * Send Query (String)
	 * 
	 * @Resumer:
	 * 
	 ******************************************************/
	public boolean send(String requete){

		try {
			rs = (ResultSet)pstmt.executeQuery();
			rsmd = (ResultSetMetaData)rs.getMetaData();
			result.setResultSet(rs, rsmd);

			//Nouvelle liste pour chaque nouvelle recherche
			resultats = new ArrayList<String>();

			if (result.extractResult()) {
				for(int i = 0; i < result.getRowCount(); i++) {
					for(int j = 0; j < result.getColCount(); j++) {

						if(requete.contains("BIO"))
							resultats.add(OutilsFenetre.clobToString((Clob) result.getObjectAt(i, j)));
						else resultats.add(result.getObjectAt(i, j).toString());
					}
				}
				SessionFacade.getSession().setResultats(resultats);
				return true;
			}
		} catch (SQLException e) {
			if ( e.getMessage().trim().equals("No ResultSet was produced")) return true; 
			else return false;
		}
		resultats.clear();
		SessionFacade.getSession().setResultats(resultats);
		return false; 
	}

	/******************************************************
	 * Send Query XML Type (String)
	 * 
	 * @Resumer:	Permet de recuperer du XMLType de la DB
	 * 				afin d'afficher le contenu dans une 
	 * 				fiche Film. Si le film ne possede pas
	 * 				de XMLType, les champs auront comme
	 * 				valeur "n/a".
	 * 
	 * @Source:		http://thinktibits.blogspot.com/2013/01/read-xmltype-xml-data-oracle-java-jdbc.html
	 * 
	 ******************************************************/
	public boolean sendXMLType(String query){

		if (isConnected) {
			try {
				pstmt = (PreparedStatement)dbConnect.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				OracleResultSet ors = (OracleResultSet) rs; 
				String colonne = OutilsRecherche.getXMLTypeColumn(query);

				while (ors.next()) {
					if(ors.getOPAQUE(colonne) == null){
						if(colonne.equals("CLASSEMENT"))  	ReadXMLType.readXML().readRanking(null);
						if(colonne.equals("AWARD")) 		ReadXMLType.readXML().readAward(null);
						if(colonne.equals("DESCRIPTION")) 	ReadXMLType.readXML().readColor(null);	
						return false;
					}else{
						XMLType poxml = XMLType.createXML(ors.getOPAQUE(colonne));
						if(colonne.equals("CLASSEMENT"))  	ReadXMLType.readXML().readRanking(poxml.getStringVal());
						if(colonne.equals("AWARD")) 		ReadXMLType.readXML().readAward(poxml.getStringVal());
						if(colonne.equals("DESCRIPTION")) 	ReadXMLType.readXML().readColor(poxml.getStringVal());	
						return true;
					}
				}
			} catch (SQLException e) {
				if ( e.getMessage().trim().equals("No ResultSet was produced")) return true; 
				else return false; 
			}
		}return false;
	}

	/******************************************************
	 * Information Credit
	 * 
	 * @Resumer:	Fermeture de connexion avec la DB et
	 * 				remet l'etat initial de la connexion.
	 * 
	 ******************************************************/
	public void close() throws SQLException{
		if(dbConnect != null) {
			if(pstmt != null) 
				pstmt.close(); 
			dbConnect.close();
			isConnected = false;
		}
	}

	/******************************************************
	 * @Titre:			Accesseurs
	 ******************************************************/
	public boolean isOpen(){ return isConnected; }
	public Address getAdresse(){ return adresse; }

	/******************************************************
	 * @Titre:		PATRON SINGLETON
	 ******************************************************/
	public static Connect getConnect(){ return connectDB; }

	/******************************************************
	 * @Titre:		Add Property Change Listener
	 ******************************************************/
	public void addChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void fireChangeProperty(String support){
		changeSupport.firePropertyChange(support, false, true);
	}
}