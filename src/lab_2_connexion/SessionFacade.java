package lab_2_connexion;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import lab_3_ui.EtatFenetre;
import lab_3_ui.PanelLogin;
import lab_3_ui.StrategieFenetre; 


/**************************************************************
 * @CLASS_TITLE:	Session Facade
 * 
 * @Description: 	Cette classe �vite que le Client ait � 
 * 					communiquer directement avec la classe
 * 					de connexion et d'authentification.
 * 					Supporte le processus d'authentification,
 * 					d'initialisation et connexion � la DB. 
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class SessionFacade {

	/******************************
	 * Instance Classe
	 ******************************/
	private PanelLogin login = null;
	private HeartBeat heartbeat = null;
	private Authentification authentification = null; 	

	/******************************
	 * PATRON SINGLETON
	 ******************************/
	private static SessionFacade session = new SessionFacade();

	/******************************
	 * Constante
	 ******************************/
	private static final String 
	TITRE_ERROR = "Erreur Authentification",
	MSG_DECONNEXION = "\n(SessionFacade) Fermeture de session et D�connexion avec la DB.",
	MSG_CONNEXION = "\n******************************************" +
			"\n*  Acc�s � une nouvelle Session r�ussi ! *" +
			"\n******************************************";

	/******************************
	 * Variable
	 ******************************/
	private String msgError = null;
	private ArrayList<String> resultats = null;


	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public SessionFacade(){	
		heartbeat = new HeartBeat();
		authentification = new Authentification(); 
	}

	/******************************************************
	 * Initialiser Session
	 * 
	 * @Resumer:	Ajout du PATRON OBSERVATEUR � la classe
	 * 				de connexion DB pour suivre l'�tat de 
	 * 				la connexion. Recup�re l'information
	 * 				de connexion � la DB Oracle � partir
	 * 				d'un fichier de type ".properties".
	 * 
	 ******************************************************/
	public void initConnection(){

		//Ajout du PATRON OBSERVATEUR pour suivre l'�tat de connexion
		Connect.getConnect().addChangeListener(login);

		//Ouverture de la connexion avec les l'info du fichier '.properties'
		ImportProperties properties = new ImportProperties();
		Connect.getConnect().openConnection(new RSQUser(properties.getUser(), properties.getPassw()), 
				new Address(properties.getServeur(), properties.getPort(), properties.getSid()));	

		heartbeat.start();//Demarre la verification en boucle de l'etat de connexion.
	}

	/******************************************************
	 * Create Session Client
	 * 
	 * @Resumer:	Si l'authentification est valide,
	 * 				on r�cup�re l'ID du CLient et on
	 * 				proc�de au changement d'�tat qui
	 * 				permet d'afficher la fen�tre Recherche.
	 * 
	 ******************************************************/
	public boolean initSession(RSQUser user) { 

		if (authentification.isValid(user)) { 

			//Recupere l'ID Client et Change l'�tat de la fen�tre
			System.out.println(MSG_CONNEXION);
			StrategieFenetre.initFenetreRecherche(getResultats().get(0));
			StrategieFenetre.changeState(EtatFenetre.SEARCH_STATE);
			return true; 		
		} 
		else { //Affiche un message d'avertissement selon l'erreur identifi� de la classe Authentification
			JOptionPane.showMessageDialog(null, msgError, TITRE_ERROR, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	} 

	/******************************************************
	 * Submit Query
	 * 
	 * @Resumer:	Envoi les requetes vers la DB
	 * 
	 ******************************************************/
	public boolean submitQuery(String query) { 
		try { 
			if(Connect.getConnect().isOpen()) 
				Connect.getConnect().sendQuery(query); 
		}catch(Exception e)  {
			System.err.println("(SessionFacade) Error cannot submit query"); 						
			return false; 
		}return true; 
	}

	/******************************************************
	 * Submit Query XMLType
	 * 
	 * @Resumer:	Envoi les requetes XMLType vers la DB
	 * 
	 ******************************************************/
	public boolean submitQueryXMLType(String query) { 
		try { 
			if(Connect.getConnect().isOpen()) 
				Connect.getConnect().sendXMLType(query); 
		}catch(Exception e)  {
			System.err.println("(SessionFacade) Error cannot submit query XMLType"); 						
			return false; 
		}return true; 
	}

	/******************************************************
	 * Destroy Session
	 * 
	 * @Resumer:	
	 * 
	 ******************************************************/
	public void destroySession() { 
		if (authentification != null) { 
			try {
				System.err.println(MSG_DECONNEXION);
				heartbeat.stop();
				Connect.getConnect().close(); 
				authentification = null; 
			}
			catch (SQLException e) {
				System.err.println("(SessionFacade) Error cannot close db connection"); 					
			}					
		}	
	}

	/******************************************************
	 * 					Accesseur (PATRON SINGLETON)
	 ******************************************************/
	public static SessionFacade getSession(){ return session; }
	public ArrayList<String> getResultats() { return resultats; }

	/******************************************************
	 * 					Mutateur
	 ******************************************************/
	public void setLogin(PanelLogin _login){ this.login = _login; }
	public void setMsgError(String _msgError){ this.msgError = _msgError; }
	public void setResultats(ArrayList<String> _resultats){ this.resultats = _resultats; }
}
