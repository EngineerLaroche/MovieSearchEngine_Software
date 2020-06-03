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
 * @Description: 	Cette classe évite que le Client ait à 
 * 					communiquer directement avec la classe
 * 					de connexion et d'authentification.
 * 					Supporte le processus d'authentification,
 * 					d'initialisation et connexion à la DB. 
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
	MSG_DECONNEXION = "\n(SessionFacade) Fermeture de session et Déconnexion avec la DB.",
	MSG_CONNEXION = "\n******************************************" +
			"\n*  Accès à une nouvelle Session réussi ! *" +
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
	 * @Resumer:	Ajout du PATRON OBSERVATEUR à la classe
	 * 				de connexion DB pour suivre l'état de 
	 * 				la connexion. Recupère l'information
	 * 				de connexion à la DB Oracle à partir
	 * 				d'un fichier de type ".properties".
	 * 
	 ******************************************************/
	public void initConnection(){

		//Ajout du PATRON OBSERVATEUR pour suivre l'état de connexion
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
	 * 				on récupère l'ID du CLient et on
	 * 				procède au changement d'état qui
	 * 				permet d'afficher la fenêtre Recherche.
	 * 
	 ******************************************************/
	public boolean initSession(RSQUser user) { 

		if (authentification.isValid(user)) { 

			//Recupere l'ID Client et Change l'état de la fenêtre
			System.out.println(MSG_CONNEXION);
			StrategieFenetre.initFenetreRecherche(getResultats().get(0));
			StrategieFenetre.changeState(EtatFenetre.SEARCH_STATE);
			return true; 		
		} 
		else { //Affiche un message d'avertissement selon l'erreur identifié de la classe Authentification
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
