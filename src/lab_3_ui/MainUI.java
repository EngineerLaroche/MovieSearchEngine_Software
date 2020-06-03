package lab_3_ui;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import lab_2_connexion.SessionFacade;


/**************************************************************
 * @CLASS_TITLE:	Main Interface
 * 
 * @Description: 	D�marrage de l'application avec interface 
 * 					utilisateur qui permet a un Client de 
 * 					s'authentifier,faire des recherches de films 
 * 					et de personnes, de modifier l'information de
 * 					son compte et d'ajouter des films pour la
 * 					location. Le client peut aussi garder un suivi
 * 					sur son historique de recherche et exporter
 * 					une liste de films dans un fichier XML. 		  
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class MainUI {

	/*******************************************************
	 * @Titre: 		MAIN THREAD
	 * 
	 * @Resumer:	On d�marre l'interface UI dans un 
	 * 				processus s�par� pour �viter les conflits 
	 * 				de gestion d'�v�nements et on initialise 
	 * 				une nouvelle session.
	 *  
	 ******************************************************/
	public static void main(String[] args){
		
		//On commence toujours l'app avec la page Login
		PanelLogin login = new PanelLogin();

		//Separation du Thred pour mettre � jour le UI
		SwingUtilities.invokeLater(new Runnable(){ 

			//Initialise le Controlleur UI qui utilise le PATRON STRAT�GIE
			public void run(){ new StrategieFenetre(login); }});
		
		//Initialisation d'une nouvelle Session
		SessionFacade session = new SessionFacade();
		session.setLogin(login);
		session.initConnection();
	}
}
