package lab_3_ui;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lab_2_connexion.Connect;
import lab_2_connexion.SessionFacade;


/**************************************************************
 * @CLASS_TITLE:	Fenetre Strategie
 * 
 * @Description: 	Création du Frame de base de l'application.
 * 					On utilise le PATRON STRATÉGIE pour changer
 * 					l'état de la fenêtre à afficher. Gère l'état
 * 					de la fenêtre Login et la fenêtre principale 
 * 					de recherche Films et Personnes.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class StrategieFenetre extends JFrame implements WindowListener{

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante
	 ******************************/
	private static final int 
	HAUTEUR = 1350,
	LARGEUR = 1200;

	private static final String 
	TITRE = "Application GTI660-H19",
	TITRE_CLOSE = "Fermeture Application",
	ERROR_STATE = "*** ERROR: État Inconnu ***",
	MSG_CLOSE = "Êtes-vous certain devouloir quitter l'application ?",
	MSG_FERMETURE = "\n******************************************" +
					"\n*  Fermeture complète de l'application	 *" +
					"\n******************************************",
	MSG_BIENVENU = 	"\n******************************************" +
					"\n*               BIENVENU !               *" +
					"\n*                                        *" +
					"\n*   Recherche de Films et de Personnes.  *" +
					"\n*  Location Films et modification compte.*" + 
					"\n*                                        *" +
					"\n******************************************";

	/******************************
	 * Instance Classe 
	 ******************************/
	private static PanelLogin login = null;
	private static PanelGroupRecherche search = null;

	/******************************
	 * Swing Object 
	 ******************************/
	private static JPanel supportPanel = null;

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public StrategieFenetre(PanelLogin _login){

		login = _login;
		System.out.println(MSG_BIENVENU);
		supportPanel = new JPanel(new GridBagLayout());	

		//Démarre l'app avec l'état pour accéder à la fenêtre Login
		changeState(EtatFenetre.LOGIN_STATE);

		//Parametres du Frame de l'app. 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.addWindowListener(this);
		this.setResizable(true);
		this.setVisible(true);
		this.setTitle(TITRE);
		this.add(supportPanel);
		//this.setSize(LARGEUR,HAUTEUR);
	}

	/******************************************************
	 * Change State
	 * 
	 * @Resumer:	Selon l'état reçu, on change la fenêtre
	 * 				à afficher de l'interface utilisateur.
	 * 
	 ******************************************************/
	public static void changeState(EtatFenetre etat){

		switch (etat) {

		//Affiche la page Login
		case LOGIN_STATE:
			supportPanel.removeAll();
			supportPanel.add(login);
			supportPanel.revalidate();
			supportPanel.repaint();
			break;

			//Affiche la page Recherche
		case SEARCH_STATE:
			supportPanel.removeAll();
			supportPanel.add(search);
			supportPanel.revalidate();
			supportPanel.repaint();
			break;

		default:
			System.err.println(ERROR_STATE);
			break;
		}
	}

	/******************************************************
	 * Initialise Fenetre Recherche
	 * 
	 * @Resumer:	Initialise la fenêtre de recherche Films
	 * 				et recherche Personnes.
	 * 
	 ******************************************************/
	public static void initFenetreRecherche(String clientID){
		search = new PanelGroupRecherche(clientID);
		Connect.getConnect().addChangeListener(search);
	}

	/******************************************************
	 * Window Closing
	 * 
	 * @Resumer:	On valide avec le Client s'il veut 
	 * 				vraiment fermer l'application. Si oui,
	 * 				on ferme la connexion avec la DB et 
	 * 				on ferme l'application.
	 * 
	 ******************************************************/
	public void windowClosing(WindowEvent e) {
		int reponse = JOptionPane.showConfirmDialog(null, MSG_CLOSE, 
				TITRE_CLOSE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);					
		
		if(reponse == JOptionPane.YES_OPTION){
			System.out.println(MSG_FERMETURE);
			SessionFacade.getSession().destroySession();	
			System.exit(0);
					
		}	
	}

	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
