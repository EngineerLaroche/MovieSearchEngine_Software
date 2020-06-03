package lab_3_ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import lab_2_connexion.Connect;
import lab_2_connexion.SessionFacade;
import lab_3_database.QueryPersonneLibrary;
import lab_3_tools.OutilsFenetre;


/**************************************************************
 * @CLASS_TITLE:	Panel Group Recherche
 * 
 * @Description: 	Panneau principale de l'application après 
 * 					l'authentification du Client. Avec un 
 * 					JTabbedPane, on permet au client de faire 
 * 					une recherche orienté film (titre, personne,
 * 					location, etc.) et une recherche par type de
 * 					contenu multimedia (Couleur, Awards, etc.).
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class PanelGroupRecherche extends JPanel implements ActionListener, PropertyChangeListener{ 

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	private static final String 
	AIDE = " ? ",
	LOGOUT = "Déconnexion",
	COMPTE = "Mon Compte",
	MSG_CLIENTID = "(Recherche) Le Client avec l'ID ",
	MSG_EXEC = " est dans une session en cours d'exécution.";

	/******************************
	 * Swing Object 
	 ******************************/
	private JLabel labelEtat = null;
	
	private Font
	fontGras = null,
	fontSimple = null;

	private JButton 
	boutonAide = null,
	boutonCompte = null,
	boutonLogout = null;

	/******************************
	 * Variable
	 ******************************/
	private String clientID = null;


	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public PanelGroupRecherche(String _clientID){
		this.clientID = _clientID;

		fontGras = new Font("Arial", Font.BOLD, 15);
		fontSimple = new Font("Arial", Font.PLAIN, 15);	
		System.err.println(MSG_CLIENTID + clientID + MSG_EXEC);

		fenetrePrincipale();
	}

	/******************************************************
	 * Fenetre Principale
	 * 
	 * @Resumer:	Panneau qui rassemble tous les panneaux
	 * 				de recherches (Film ou Personne) ainsi 
	 * 				que les panneaux de recherches contenu.
	 * 
	 ******************************************************/
	private void fenetrePrincipale(){
		
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
		panelMenu.add(menuPanel()); //Ajout d'un menu avec boutons (aide, deconnexion, compte, etc.)	
		panelMenu.add(infoPanel()); //Affiche les informations utilisateur et de connexion
		
		JTabbedPane tabSearch = new JTabbedPane(JTabbedPane.TOP);
		tabSearch.add("Recherche Film", new PanelRechercheFilm(clientID));  //Recherche Film et Personne + Location
		tabSearch.add("Recherche Contenu",new PanelRechercheContenu());	   	//Recherche par contenu 
		tabSearch.setFont(fontGras);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panelMenu);
		this.add(new JLabel(" "));
		this.add(tabSearch);
	}

	/******************************************************
	 * Menu Panel
	 * 
	 * @Resumer:
	 * 
	 ******************************************************/
	private JPanel menuPanel() {

		//Bouton qui donne acces aux infor du compte client et bouton logout
		boutonAide = new JButton(AIDE);
		boutonCompte = new JButton(COMPTE);
		boutonLogout = new JButton(LOGOUT);

		boutonAide.addActionListener(this);
		boutonCompte.addActionListener(this);
		boutonLogout.addActionListener(this);

		//Structure et dimension du panel Menu de Droite
		JPanel panelMenuR = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelMenuR.setBackground(Color.GRAY);
		panelMenuR.add(boutonCompte);
		panelMenuR.add(boutonLogout);

		//Structure et dimension du panel enu de Gauche
		JPanel panelMenuL = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelMenuL.setBackground(Color.GRAY);
		panelMenuL.add(boutonAide);

		//Panneau qui regroupe le panel Gauche et Droite
		JPanel panelMenu = new JPanel(new GridLayout(1,2));
		panelMenu.setBackground(Color.GRAY);
		panelMenu.add(panelMenuL);
		panelMenu.add(panelMenuR);

		return panelMenu;
	}

	/******************************************************
	 * Info Panel
	 * 
	 * @Resumer:
	 * 
	 ******************************************************/
	private JPanel infoPanel(){

		//Parametre du format heure
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		QueryPersonneLibrary.findClientEmail(clientID);

		labelEtat = new JLabel("Ouvert");
		labelEtat.setForeground(new Color(35,155,86));
		labelEtat.setFont(fontGras);

		JLabel //Info de connexion temporairement statique
		labelCheckEtat = new JLabel("État Connexion:  "),
		labelEmail = new JLabel("[" + clientID + "]   Email:   " + SessionFacade.getSession().getResultats().get(0)),
		labelParam = new JLabel("Serveur:   " + Connect.getConnect().getAdresse().toString()),
		labelTime = new JLabel("	Heure de Connexion:   " + sdf.format(Calendar.getInstance().getTime()));

		JPanel panelEtat = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEtat.setBackground(Color.LIGHT_GRAY);
		panelEtat.add(labelCheckEtat);
		panelEtat.add(labelEtat);

		//Grosseur des caracteres des Labels
		labelCheckEtat.setFont(fontSimple);
		labelParam.setFont(fontSimple);
		labelEmail.setFont(fontGras);
		labelTime.setFont(fontSimple);

		//Structure et dimension du panel Information CLient
		JPanel panelInfoClient = new JPanel();
		panelInfoClient.setBackground(Color.LIGHT_GRAY);
		panelInfoClient.setLayout(new GridLayout(1,2));
		panelInfoClient.add(labelEmail);
		panelInfoClient.add(labelTime);

		//Structure et dimension du panel Information Connexion
		JPanel panelInfoConnexion = new JPanel();
		panelInfoConnexion.setBackground(Color.LIGHT_GRAY);
		panelInfoConnexion.setLayout(new GridLayout(1,2));
		panelInfoConnexion.add(panelEtat);
		panelInfoConnexion.add(labelParam);

		//Panel Information
		TitledBorder borderInfo = OutilsFenetre.initTitledBorder("INFORMATION");
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(borderInfo);
		panelInfo.setBackground(Color.LIGHT_GRAY);
		panelInfo.setLayout(new GridLayout(2,1,10,10));
		panelInfo.add(panelInfoClient);
		panelInfo.add(panelInfoConnexion);

		return panelInfo;
	}

	/******************************************************
	 * 					Action Event
	 ******************************************************/
	public void actionPerformed(ActionEvent e){

		//Deconnecte le Client et retourne a la page Login
		if(e.getSource() == boutonLogout){ OutilsFenetre.logout(); }

		//Demarre une fenetre d'aide utilisateur
		else if(e.getSource() == boutonAide){ new FenetreAide(); }

		//Demarre une fenetre qui affiche et qui permet de modifier les info du compte Client
		else if(e.getSource() == boutonCompte){ new FenetreCompteClient(clientID);}
	}
	
	/******************************************************
	 * Property Change
	 * 
	 * @Resumer:	Met a jour l'etat de la connexion
	 * 				du Label retrouvé dans le menu de l'app.
	 * 				
	 ******************************************************/
	public void propertyChange(PropertyChangeEvent evt) {
		
		//Connexion Fermé: Update le Label état
		if(evt.getPropertyName().equals("connexionImpossible")){		
			labelEtat.setText("Fermé");
			labelEtat.setFont(fontGras);
			labelEtat.setForeground(Color.RED);
		}

		//Connexion en cours: Update le Label état
		if(evt.getPropertyName().equals("connexionEnCours")){	
			labelEtat.setText(" En cours...");
			labelEtat.setForeground(new Color(211,84,0));
			labelEtat.setFont(fontGras);
		}

		//Connexion en cours: Update le Label état
		if(evt.getPropertyName().equals("connexionOuverte")){	
			labelEtat.setText("Ouvert");
			labelEtat.setForeground(new Color(35,155,86));
			labelEtat.setFont(fontGras);
		}
	}
}
