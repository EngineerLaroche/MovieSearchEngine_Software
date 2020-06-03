package lab_3_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import lab_2_connexion.SessionFacade;
import lab_3_database.ExportResultsToXML;
import lab_3_tools.JComboCheckBox;
import lab_3_tools.OutilsFenetre;
import lab_3_tools.OutilsRecherche;


/**************************************************************
 * @CLASS_TITLE:	Fenetre Recherche Film
 * 
 * @Description: 	Page principale de l'application après 
 * 					l'authentification du Client. C'est ici
 * 					qu'on procède aux recherches. Le client 
 * 					peut accéder à son compte et modifier 
 * 					l'information.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class PanelRechercheFilm extends JPanel implements ActionListener, MouseListener, KeyListener{

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante
	 ******************************/
	private static final int 
	NB_CHAR = 15,
	MAX_VALUE = 100;

	private static final String 
	ACTEUR = "Acteur",
	SCENARISTE = "Scenariste",
	REALISATEUR = "Realisateur",
	PERSONNE = "Personne",
	FILMID = "filmID",
	ANNEE_DIFF = "AnneeDiff",
	DUREE_DIFF = "DureeDiff",
	SPACE = "                   ";

	/******************************
	 * Instance Classe
	 ******************************/
	private Point point = null;
	private JComboCheckBox 
	comboBox = null,
	comboBoxInclude = null,
	comboBoxExclude = null;

	/******************************
	 * Listes de resultats
	 ******************************/
	private JList<?> 
	jListeSelect = null,
	jListFilm = null,
	jListPersonne = null;

	private DefaultListModel<String> 
	listeFilms = null,
	listePersonnes = null,
	listeLocation = null,
	historique = null;

	/******************************
	 * Swing Object 
	 ******************************/
	private JLabel labelPrixTotal = null;

	private Font 
	fontGras = null,
	fontSimple = null;

	private JTextField  
	textFilmID = null,
	textTitre = null,
	textAnnee = null,
	textAnneeMin = null,
	textAnneeMax = null,
	textLangue = null,
	textDuree = null,
	textDureeMin = null,
	textDureeMax = null,
	textResume = null,
	textActeur = null,
	textReali = null,
	textScena = null,
	textNom = null,
	textPersonneID = null;

	private JButton 
	boutonSearchFilm = null,
	boutonSearchFilmAvancee = null,
	boutonSearchParticipant = null,
	boutonSearchPersonne = null,
	boutonSearchAll = null,
	boutonAjouter = null,
	boutonRetirer = null,
	boutonExportXML = null;

	/******************************
	 * Variable
	 ******************************/
	private String 
	clientID = null,
	focusListener = null;


	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public PanelRechercheFilm(String _clientID){

		this.clientID = _clientID;
		fontGras = new Font("Arial", Font.BOLD, 15);
		fontSimple = new Font("Arial", Font.PLAIN, 15);

		fenetreRecherche();
	}

	/******************************************************
	 * Fenetre Recherche Film & Personne
	 * 
	 * @Resumer:	Panneau qui rassemble tous les panneaux
	 * 				de recherches (Film ou Personne) ainsi 
	 * 				que les panneaux de recherches résultats.
	 * 
	 ******************************************************/
	private void fenetreRecherche(){

		JPanel panelActivity = new JPanel(new GridLayout(3,1,20,20));
		panelActivity.add(personnePanel()); //Ajout du panneau de recherche et resultats Personne
		panelActivity.add(filmPanel()); 	//Ajout du panneau de recherche et resultats Film
		panelActivity.add(locationPanel()); //Ajout du panneau de gestion des Locations

		this.add(panelActivity);
	}

	/******************************************************
	 * Film Panel
	 * 
	 * @Resumer:	Le panneau inclu la recherche des Films 
	 * 				et la recherche avancée des films.
	 * 				Affiche les résultats des Films trouvés.
	 * 
	 ******************************************************/
	private JPanel filmPanel(){

		JTabbedPane tabFilm = new JTabbedPane(JTabbedPane.TOP);
		tabFilm.add("Recherche",rechercheFilm());
		tabFilm.add("Recherche avancée",rechercheFilmAvancee());

		//Liste qui garde en memoire les films trouvés
		listeFilms = new DefaultListModel<String>();
		listeFilms.setSize(MAX_VALUE);
		jListFilm = new JList<String>(listeFilms);		
		jListFilm.addMouseListener(this);

		//Panel resultats Films
		TitledBorder borderResultats = OutilsFenetre.initTitledBorder("RESULTATS - FILMS");
		JPanel panelResultats = new JPanel(new BorderLayout());
		panelResultats.setBorder(borderResultats);
		panelResultats.add(new JScrollPane(jListFilm));
		
		//Reunion des Panel Search (TabbedPane) et Resultats
		JPanel panelFilm = new JPanel(new GridLayout(1,2,10,10));
		panelFilm.add(tabFilm);
		panelFilm.add(panelResultats);

		return panelFilm;
	}

	/******************************************************
	 * Recherch Film
	 * 
	 * @Resumer:	Le panneau qui permet la recherche de
	 * 				films avec l'information général d'un film.
	 * 
	 * @Source: 	http://esus.com/how-do-i-put-a-jcheckbox-in-a-jcombobox/
	 * 
	 ******************************************************/
	private JPanel rechercheFilm(){

		JLabel 
		labelTitre = new JLabel("Titre"),
		labelAnnee = new JLabel("Année"),
		labelLangue = new JLabel("Langue"),
		labelDuree = new JLabel("Duée"),
		labelResume = new JLabel("Résumé"),
		labelGenre = new JLabel("Genre");

		//Grosseur des caracteres des Labels
		labelTitre.setFont(fontSimple);
		labelAnnee.setFont(fontSimple);
		labelLangue.setFont(fontSimple);
		labelDuree.setFont(fontSimple);
		labelResume.setFont(fontSimple);
		labelGenre.setFont(fontSimple);

		//Boite de texte pour chercher un Film
		textTitre = new JTextField(NB_CHAR);
		textAnnee = new JTextField(4);
		textLangue = new JTextField(NB_CHAR);
		textDuree = new JTextField(4);
		textResume = new JTextField(NB_CHAR);

		textTitre.setFont(fontSimple);
		textAnnee.setFont(fontSimple);
		textLangue.setFont(fontSimple);
		textDuree.setFont(fontSimple);
		textResume.setFont(fontSimple);

		textTitre.addKeyListener(this);
		textAnnee.addKeyListener(this);
		textLangue.addKeyListener(this);
		textDuree.addKeyListener(this);
		textResume.addKeyListener(this);

		//Initialisation de la liste des checkBox 
		Vector<JCheckBox> vectorInclude = OutilsFenetre.getVectorCheckBox();
		comboBox = new JComboCheckBox(vectorInclude);
		comboBox.addActionListener(this);

		//Parametre du bouton de recherche Film
		boutonSearchFilm = new JButton("Search Film");
		boutonSearchFilm.addActionListener(this);
		boutonSearchFilm.setFont(fontSimple);
		boutonSearchFilm.setEnabled(false);
		
		boutonExportXML = new JButton("Export xml");
		boutonExportXML.setEnabled(false);
		boutonExportXML.addActionListener(this);

		//Panel recherche Film
		TitledBorder borderFilm = OutilsFenetre.initTitledBorder("FILM");
		JPanel panelSearch = new JPanel(new GridLayout(7,1,10,10));
		panelSearch.setBorder(borderFilm);

		//Ajouts des elements de recherche Film au panneau
		panelSearch.add(labelTitre);
		panelSearch.add(textTitre);
		panelSearch.add(labelLangue);
		panelSearch.add(textLangue);
		panelSearch.add(labelResume);
		panelSearch.add(textResume);
		panelSearch.add(labelAnnee);
		panelSearch.add(textAnnee);
		panelSearch.add(labelDuree);
		panelSearch.add(textDuree);
		panelSearch.add(labelGenre);
		panelSearch.add(comboBox);
		panelSearch.add(boutonSearchFilm);
		panelSearch.add(boutonExportXML);

		return panelSearch;
	}

	/******************************************************
	 * Recherche Film Avancée
	 * 
	 * @Resumer:	Le panneau qui permet la recherche 
	 * 				avancée de films.
	 * 
	 * @Source: 	http://esus.com/how-do-i-put-a-jcheckbox-in-a-jcombobox/
	 * 
	 ******************************************************/
	private JPanel rechercheFilmAvancee(){

		JLabel 
		labelFilmID = new JLabel("Identifiant"),
		labelAnnee = new JLabel("Année (min-max)"),
		labelDuree = new JLabel("Duée (min-max)"),
		labelGenre = new JLabel("Genre (include-exclude)");

		//Grosseur des caracteres des Labels
		labelFilmID.setFont(fontSimple);
		labelAnnee.setFont(fontSimple);
		labelDuree.setFont(fontSimple);
		labelGenre.setFont(fontSimple);

		//Boite de texte pour chercher un Film
		textFilmID = new JTextField(4);
		textAnneeMin = new JTextField(4);
		textAnneeMax = new JTextField(4);
		textDureeMin = new JTextField(4);
		textDureeMax = new JTextField(4);

		textFilmID.setFont(fontSimple);
		textAnneeMin.setFont(fontSimple);
		textAnneeMax.setFont(fontSimple);
		textDureeMin.setFont(fontSimple);
		textDureeMax.setFont(fontSimple);

		textFilmID.addKeyListener(this);
		textAnneeMin.addKeyListener(this);
		textAnneeMax.addKeyListener(this);
		textDureeMin.addKeyListener(this);
		textDureeMax.addKeyListener(this);

		//Initialisation de la liste des checkBox (inclusion et exclusion)
		Vector<JCheckBox> vectorInclude = OutilsFenetre.getVectorCheckBox();
		Vector<JCheckBox> vectorExclude = OutilsFenetre.getVectorCheckBox();
		comboBoxInclude = new JComboCheckBox(vectorInclude);
		comboBoxExclude = new JComboCheckBox(vectorExclude);
		comboBoxInclude.addActionListener(this);
		comboBoxExclude.addActionListener(this);
		comboBoxInclude.setEnabled(false);
		comboBoxExclude.setEnabled(false);

		SessionFacade.getSession().getResultats().clear();

		JPanel panelAnneeDiff = new JPanel(new GridLayout(1,2,10,10));
		panelAnneeDiff.add(textAnneeMin);
		panelAnneeDiff.add(textAnneeMax);

		JPanel panelDureeDiff = new JPanel(new GridLayout(1,2,10,10));
		panelDureeDiff.add(textDureeMin);
		panelDureeDiff.add(textDureeMax);

		JPanel panelGenreDiff = new JPanel(new GridLayout(1,2,10,10));
		panelGenreDiff.add(comboBoxInclude);
		panelGenreDiff.add(comboBoxExclude);

		//Parametre du bouton de recherche Film
		boutonSearchFilmAvancee = new JButton("Search Film");
		boutonSearchFilmAvancee.addActionListener(this);
		boutonSearchFilmAvancee.setFont(fontSimple);
		boutonSearchFilmAvancee.setEnabled(false);

		//Panel recherche Film Avancee
		TitledBorder borderFilm = OutilsFenetre.initTitledBorder("FILM");
		JPanel panelSearch = new JPanel(new GridLayout(5,1,10,10));
		panelSearch.setBorder(borderFilm);

		//Ajouts des elements de recherche Film au panneau
		panelSearch.add(labelFilmID);
		panelSearch.add(textFilmID);
		panelSearch.add(labelAnnee);
		panelSearch.add(panelAnneeDiff);
		panelSearch.add(labelDuree);
		panelSearch.add(panelDureeDiff);
		panelSearch.add(labelGenre);
		panelSearch.add(panelGenreDiff);
		panelSearch.add(boutonSearchFilmAvancee);

		return panelSearch;
	}

	/******************************************************
	 * Personne Panel
	 * 
	 * @Resumer:	Le panneau de recherche des Personnes et 
	 * 				affiche les résultats des Personnes 
	 * 				trouvées. La recherche peut être effectuée
	 * 				par type de personne ou simplement par
	 * 				l'ensemble des Personnes.
	 * 
	 ******************************************************/
	private JPanel personnePanel(){

		JLabel
		labelReali = new JLabel("Réalisateur"),
		labelScena = new JLabel("Scénariste"),
		labelActeur = new JLabel("Acteur"),
		labelNom = new JLabel("Nom"),
		labelID = new JLabel("Identifiant");

		//Boite de texte pour la recherche de Personnes
		textReali = new JTextField(NB_CHAR);
		textActeur = new JTextField(NB_CHAR);
		textScena = new JTextField(NB_CHAR);
		textNom = new JTextField(NB_CHAR);
		textPersonneID = new JTextField(NB_CHAR);

		textReali.addKeyListener(this);
		textScena.addKeyListener(this);
		textActeur.addKeyListener(this);
		textNom.addKeyListener(this);
		textPersonneID.addKeyListener(this);

		textReali.setFont(fontSimple);
		textActeur.setFont(fontSimple);
		textScena.setFont(fontSimple);
		textNom.setFont(fontSimple);
		textPersonneID.setFont(fontSimple);

		labelReali.setFont(fontSimple);
		labelScena.setFont(fontSimple);
		labelActeur.setFont(fontSimple);
		labelNom.setFont(fontSimple);
		labelID.setFont(fontSimple);

		boutonSearchParticipant = new JButton("Search Participant");
		boutonSearchParticipant.addActionListener(this);
		boutonSearchParticipant.setFont(fontSimple);
		boutonSearchParticipant.setEnabled(false);

		//Panel recherche Participants
		TitledBorder borderParticipants = OutilsFenetre.initTitledBorder("PARTICIPANT");
		JPanel panelParticipants = new JPanel(new GridLayout(4,2,10,10));
		panelParticipants.setBorder(borderParticipants);
		panelParticipants.add(labelReali);
		panelParticipants.add(textReali);
		panelParticipants.add(labelScena);
		panelParticipants.add(textScena);
		panelParticipants.add(labelActeur);
		panelParticipants.add(textActeur);
		panelParticipants.add(boutonSearchParticipant);

		boutonSearchPersonne = new JButton("Search Personne");
		boutonSearchPersonne.addActionListener(this);
		boutonSearchPersonne.setFont(fontSimple);
		boutonSearchPersonne.setEnabled(false);

		//Panel recherche Personne
		TitledBorder borderPerso = OutilsFenetre.initTitledBorder("PERSONNE");
		JPanel panelPerso = new JPanel(new GridLayout(3,2,10,10));
		panelPerso.setBorder(borderPerso);
		panelPerso.add(labelNom);
		panelPerso.add(textNom);
		panelPerso.add(labelID);
		panelPerso.add(textPersonneID);
		panelPerso.add(boutonSearchPersonne);

		//Regroupement des Panel Recherche Personnes
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BoxLayout(panelSearch, BoxLayout.Y_AXIS));
		panelSearch.add(panelPerso);
		panelSearch.add(new JLabel(" "));
		panelSearch.add(panelParticipants);

		//Liste qui garde en memoire les personnes trouvés
		listePersonnes = new DefaultListModel<String>();
		listePersonnes.setSize(MAX_VALUE);
		jListPersonne = new JList<String>(listePersonnes);
		jListPersonne.addMouseListener(this);

		//Panel Resultats Personnes
		TitledBorder borderResultats = OutilsFenetre.initTitledBorder("RESULTATS - PERSONNES");
		JPanel panelResultats = new JPanel(new BorderLayout());
		panelResultats.add(new JScrollPane(jListPersonne));
		panelResultats.setBorder(borderResultats);

		//Reunion des Panel Search et Resultats 
		JPanel panelPersonne = new JPanel(new GridLayout(1,2,10,10));
		panelPersonne.add(panelSearch);
		panelPersonne.add(panelResultats);

		return panelPersonne;
	}

	/******************************************************
	 * Location Panel
	 * 
	 * @Resumer:	Permet d'ajouter, retirer et afficher 
	 * 				des films pour la gestion des Locations
	 * 				de Film. Panel qui permet aussi de
	 * 				visualiser l'historique des recherches.
	 * 
	 ******************************************************/
	private JPanel locationPanel(){

		//Parametre du bouton de recherche general
		boutonSearchAll = new JButton("Search All");
		boutonSearchAll.addActionListener(this);
		boutonSearchAll.setFont(fontGras);
		boutonSearchAll.setEnabled(false);

		//Parametre du bouton d'ajout de film dans les favoris
		boutonAjouter = new JButton("Louer Film");
		boutonAjouter.addActionListener(this);
		boutonAjouter.setFont(fontSimple);
		boutonAjouter.setEnabled(false);

		//Parametre du bouton de retirer un film des favoris
		boutonRetirer = new JButton("Retirer Film");
		boutonRetirer.addActionListener(this);
		boutonRetirer.setFont(fontSimple);
		boutonRetirer.setEnabled(false);

		//Panneau des boutons d'ajout et retirer de la Location
		JPanel panelBoutonsLocation = new JPanel(new GridLayout(1,2,10,10));
		panelBoutonsLocation.add(boutonAjouter);
		panelBoutonsLocation.add(boutonRetirer);

		//Panneau des boutons de recherche general et ajout dans Location
		JPanel panelBoutons = new JPanel(new GridLayout(2,1,10,10));
		panelBoutons.add(boutonSearchAll);
		panelBoutons.add(panelBoutonsLocation);

		labelPrixTotal = new JLabel("Prix total des locations (0) :  0.00 $");
		labelPrixTotal.setFont(fontGras);

		//Liste qui garde en memoire les Films en Location
		listeLocation = new DefaultListModel<String>();

		//Initialise les films loués du Client de la DB		
		OutilsRecherche.recupererLocationFilm(clientID, boutonRetirer, listeLocation);
		OutilsRecherche.setPrixLocation(labelPrixTotal, listeLocation);
		JList<String> jListLocation = new JList<String>(listeLocation);
		jListLocation.addMouseListener(this);

		//Panel Location Film
		TitledBorder borderLocation = OutilsFenetre.initTitledBorder("LOCATION FILMS");
		JPanel panelPrixAndLoc = new JPanel();
		panelPrixAndLoc.setLayout(new BoxLayout(panelPrixAndLoc, BoxLayout.Y_AXIS));
		panelPrixAndLoc.setBorder(borderLocation);
		panelPrixAndLoc.add(labelPrixTotal);
		panelPrixAndLoc.add(new JScrollPane(jListLocation));

		//Liste qui garde en memoire l'historique des recherches
		historique = new DefaultListModel<String>();
		JList<String> jListHistorique = new JList<String>(historique);
		jListHistorique.setBackground(Color.LIGHT_GRAY);
		jListHistorique.setSelectionBackground(Color.LIGHT_GRAY);

		//Panel Location Film
		TitledBorder borderHistorique = OutilsFenetre.initTitledBorder("HISTORIQUE RECHERCHE");
		JPanel panelHistorique = new JPanel(new BorderLayout());
		panelHistorique.add(new JScrollPane(jListHistorique));
		panelHistorique.setBorder(borderHistorique);

		//Panneau Historique et Bouton Ajout/Retrait Location Film
		JPanel panelHistAndLoc = new JPanel(new GridLayout(2,1,10,10));
		panelHistAndLoc.add(panelHistorique);
		panelHistAndLoc.add(panelBoutons);

		//Panneau qui regroupe les autres Panel
		JPanel panelLocation = new JPanel(new GridLayout(1,2,10,10));
		panelLocation.add(panelHistAndLoc);
		panelLocation.add(panelPrixAndLoc);

		return panelLocation;
	}

	/******************************************************
	 * Set Historique
	 * 
	 * @Resumer:	Garde en mémoire l'historique de recherche
	 * 				du Client peut importe si c'est pour une
	 * 				Personne ou pour un Film.
	 * 
	 ******************************************************/
	private void setHistorique(){

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(Calendar.getInstance().getTime());

		historique.addElement("=========================================== Nouvelle recherche (" + time + ")");

		if(!textTitre.getText().isEmpty()) 		historique.addElement("Titre:" + SPACE + textTitre.getText());
		if(!textFilmID.getText().isEmpty()) 	historique.addElement("Film ID:" + SPACE + textFilmID.getText());
		if(!textAnnee.getText().isEmpty()) 		historique.addElement("Année:" + SPACE + textAnnee.getText());
		if(!textLangue.getText().isEmpty()) 	historique.addElement("Langue:" + SPACE + textLangue.getText());
		if(!textDuree.getText().isEmpty()) 		historique.addElement("Durée:" + SPACE + textDuree.getText());
		if(!textResume.getText().isEmpty()) 	historique.addElement("Résumé:" + SPACE + textResume.getText());

		if(!textAnneeMin.getText().isEmpty() && !textAnneeMax.getText().isEmpty()) 	historique.addElement("Année (min-max):" + SPACE + textAnneeMin.getText() + " - " + textAnneeMax.getText());
		if(!textDureeMin.getText().isEmpty() && !textDureeMax.getText().isEmpty()) 	historique.addElement("Durée (min-max):" + SPACE + textDureeMin.getText() + " - " + textDureeMax.getText());
		
		if(!textReali.getText().isEmpty()) 		historique.addElement("Nom Réalisateur:" + SPACE + textReali.getText());
		if(!textScena.getText().isEmpty()) 		historique.addElement("Nom Scénariste:" + SPACE + textScena.getText());		
		if(!textActeur.getText().isEmpty()) 	historique.addElement("Nom Acteur:" + SPACE + textActeur.getText());	
		if(!textNom.getText().isEmpty()) 		historique.addElement("Nom Personne:" + SPACE + textNom.getText());
		if(!textPersonneID.getText().isEmpty()) historique.addElement("Personne ID:" + SPACE + textPersonneID.getText());

		for (int i = 0; i < comboBox.getGenres().size(); i++) 		 historique.addElement("Genre:" + SPACE + comboBox.getGenres().get(i));
		for (int i = 0; i < comboBoxInclude.getGenres().size(); i++) historique.addElement("Genre Include:" + SPACE + comboBoxInclude.getGenres().get(i));
		for (int i = 0; i < comboBoxExclude.getGenres().size(); i++) historique.addElement("Genre Exclude:" + SPACE + comboBoxInclude.getGenres().get(i));

	}

	/******************************************************
	 * 					Action Event
	 ******************************************************/
	public void actionPerformed(ActionEvent e){

		//Les Genres qu'on inclu dans la recherche
		if(e.getSource() == comboBox){ buttonActivation(true); }

		//Les Genres qu'on inclu dans la recherche
		else if(e.getSource() == comboBoxInclude){ buttonActivation(true); }

		//Les Genres qu'on exclu de la recherche
		else if(e.getSource() == comboBoxExclude){ buttonActivation(true); }

		//Permet au client de exporter les resultats dans un fichier XML
		else if(e.getSource() == boutonExportXML){ new ExportResultsToXML(listeFilms); }

		//Demarre la recherche de Films
		else if(e.getSource() == boutonSearchFilm){ 
			setHistorique(); 	
			OutilsRecherche.searchFilms(textTitre, textAnnee, textLangue, textDuree, textResume, boutonAjouter, boutonExportXML, listeFilms);
		}

		//Demarre la recherche de Films avancée
		else if(e.getSource() == boutonSearchFilmAvancee){
			setHistorique();
			OutilsRecherche.searchFilmsAdvance(focusListener, textFilmID, textAnneeMin, textAnneeMax, textDureeMin, textDureeMax, boutonAjouter, listeFilms);
		}

		//Demarre la recherche de Participants Film
		else if(e.getSource() == boutonSearchParticipant){ 
			setHistorique();
			OutilsRecherche.searchParticipants(focusListener, textReali, textScena, textActeur, listePersonnes);
		}

		//Demarre la recherche de Personnes
		else if(e.getSource() == boutonSearchPersonne){ 
			setHistorique();
			OutilsRecherche.searchPersonnes(focusListener, textNom, textPersonneID, listePersonnes);
		}

		//Demarre la recherche de Films aussi en fonction des parametres Personnes
		else if(e.getSource() == boutonSearchAll){
			setHistorique();
			OutilsRecherche.searchAll(focusListener, textReali, textScena, textActeur, textNom, boutonAjouter, comboBox, listeFilms);
		}

		//Ajout d'un film dans le panneau Location
		else if(e.getSource() == boutonAjouter){ 
			OutilsRecherche.louerFilm(clientID, point, jListeSelect, listeLocation);
			OutilsRecherche.setPrixLocation(labelPrixTotal, listeLocation);
		}

		//Retire un film du panneau Location
		else if(e.getSource() == boutonRetirer){
			OutilsRecherche.removeFilm(clientID, point, boutonAjouter, jListeSelect, listeLocation);
			OutilsRecherche.setPrixLocation(labelPrixTotal, listeLocation);
		}
	}

	/******************************************************
	 * Button Activation
	 * 
	 * @Resumer:	Si tous les champs d'une section de la
	 * 				recherche n'est pas vide, on active le 
	 * 				bouton de recherche de cette section,
	 * 				sinon on désactive le bouton.
	 * 
	 ******************************************************/
	private void buttonActivation(boolean isChecked){

		if(!isChecked){
			//Si tous les champs de recherche Film ne sont pas vide, on active le bouton de recherche Film
			if(!textTitre.getText().isEmpty() || !textAnneeMin.getText().isEmpty() || !textAnneeMax.getText().isEmpty() || 
					!textLangue.getText().isEmpty()|| !textDureeMax.getText().isEmpty() || !textDureeMin.getText().isEmpty() || !textResume.getText().isEmpty() ||
					!comboBoxInclude.listIsEmpty() || !textAnnee.getText().isEmpty() || !textDuree.getText().isEmpty() || !textFilmID.getText().isEmpty()){ 
				boutonSearchFilm.setEnabled(true);
				boutonSearchFilmAvancee.setEnabled(true);
			}else{ 
				boutonSearchFilm.setEnabled(false); 
				boutonSearchFilmAvancee.setEnabled(false);
			}
		}else{
			if(!textTitre.getText().isEmpty() || !textAnneeMin.getText().isEmpty() || !textAnneeMax.getText().isEmpty() || 
					!textLangue.getText().isEmpty() || !textDureeMax.getText().isEmpty() || !textDureeMin.getText().isEmpty() || !textResume.getText().isEmpty() ||
					comboBoxInclude.listIsEmpty() || comboBoxInclude.getGenres().size() > 0  || !textAnnee.getText().isEmpty() 
					|| !textDuree.getText().isEmpty() || !textFilmID.getText().isEmpty()){ 
				boutonSearchFilm.setEnabled(true);
				boutonSearchFilmAvancee.setEnabled(true);
			}else{
				boutonSearchFilm.setEnabled(false);
				boutonSearchFilmAvancee.setEnabled(false);
			}
		}

		if(!isChecked){
			if((!textNom.getText().isEmpty() || !textReali.getText().isEmpty() || !textScena.getText().isEmpty() ||
					!textActeur.getText().isEmpty()) && comboBox.getGenres().size() == 1)
				boutonSearchAll.setEnabled(true);
			else boutonSearchAll.setEnabled(false);
		}else{
			if((!textNom.getText().isEmpty() || !textReali.getText().isEmpty() || !textScena.getText().isEmpty() ||
					!textActeur.getText().isEmpty()) && (comboBox.getGenres().size() == 1 || comboBoxInclude.listIsEmpty()))
				boutonSearchAll.setEnabled(true);
			else boutonSearchAll.setEnabled(false);
		}

		//Si tous les champs de recherche Participant ne sont pas vide, on active le bouton de recherche Participant
		if(!textReali.getText().isEmpty() || !textScena.getText().isEmpty() || !textActeur.getText().isEmpty())
			boutonSearchParticipant.setEnabled(true);
		else boutonSearchParticipant.setEnabled(false);

		//Si tous les champs de recherche Personne ne sont pas vide, on active le bouton de recherche Personne
		if(!textNom.getText().isEmpty() || !textPersonneID.getText().isEmpty())
			boutonSearchPersonne.setEnabled(true);
		else boutonSearchPersonne.setEnabled(false);
	}

	/******************************************************
	 * Boite Info Selection (Mouse Clicked)
	 * 
	 * @Resumer:	Permet d'identifier si le Client a 
	 * 				double cliqué sur un Film ou une
	 * 				Personne pour ensuite afficher la fiche 
	 * 				complète de la sélection en question.
	 * 
	 * @Source: 	https://stackoverflow.com/questions/32574450/double-click-event-on-jlist-element
	 * 
	 ******************************************************/
	public void mouseClicked(MouseEvent e) {
		point = e.getPoint();
		jListeSelect = (JList<?>) e.getSource();
		OutilsRecherche.selectionFiche(jListPersonne, jListeSelect, jListFilm, e);
	}

	/******************************************************
	 * Key Typed
	 * 
	 * @Resumer:	Permet de désactiver les boutons de
	 * 				recherche si aucuns caractères ne sont 
	 * 				entrés dans les JTextField. Vice versa, 
	 * 				les boutons seront activés. Permet aussi
	 * 				d'identifier si le Client veut faire une 
	 * 				recherche seulement par type de Personne.
	 * 				Enfin, supporte la possibilité d'entrer
	 * 				seulement des chiffres ou des lettres dans
	 * 				certains JTextLabel spécifiques.
	 * 				
	 ******************************************************/
	public void keyTyped(KeyEvent e) {

		//Désactive ou active les boutons de recherche en fonction des JTextfield.
		buttonActivation(false);
		char c = e.getKeyChar();

		//Recherche seulement par Realisateur
		if(e.getSource() == textReali){
			focusListener = REALISATEUR;
			textScena.setText("");
			textActeur.setText("");
		}
		//Recherche seulement par Scenariste
		if(e.getSource() == textScena){
			focusListener = SCENARISTE;
			textReali.setText("");
			textActeur.setText("");
		}
		//Recherche seulement par Acteur
		if(e.getSource() == textActeur){
			focusListener = ACTEUR;
			textReali.setText("");
			textScena.setText("");
		}

		//Recherche seulement par Acteur
		if(e.getSource() == textNom){
			focusListener = PERSONNE;
		}

		//Recherche seulement par ID FIlm
		if(e.getSource() == textFilmID){
			focusListener = FILMID;
			textAnneeMin.setText(""); textAnneeMax.setText("");
			textDureeMin.setText(""); textDureeMax.setText("");
			comboBoxInclude.getGenres().clear();
			comboBoxExclude.getGenres().clear();
		}

		//Recherche seulement par Intervalle Annee
		if(e.getSource() == textAnneeMin || e.getSource() == textAnneeMax){
			focusListener = ANNEE_DIFF;
			textFilmID.setText("");
			textDureeMin.setText(""); textDureeMax.setText("");
			comboBoxInclude.getGenres().clear();
			comboBoxExclude.getGenres().clear();
		}

		//Recherche seulement par Intervalle Duree
		if(e.getSource() == textDureeMin || e.getSource() == textDureeMax){
			focusListener = DUREE_DIFF;
			textFilmID.setText("");
			textAnneeMin.setText(""); textAnneeMax.setText("");
			comboBoxInclude.getGenres().clear();
			comboBoxExclude.getGenres().clear();
		}

		//Pour seulement supporter l'entrée de chiffres.
		if(e.getSource() == textPersonneID || e.getSource() == textFilmID || e.getSource() == textDuree ||  e.getSource() == textAnnee || 
				e.getSource() == textAnneeMin || e.getSource() == textAnneeMax || e.getSource() == textDureeMin){
			if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) 
				e.consume();
		}
		//Pour seulement supporter l'entrée de lettres.
		if(e.getSource() == textReali || e.getSource() == textScena || e.getSource() == textActeur || e.getSource() == textNom){
			if (!(Character.isAlphabetic(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) 
				e.consume();	
		}

		//Une année ne devrait pas avoir plus de 4 caractères
		if(e.getSource() == textAnnee){ if(textAnnee.getText().length() >= 4) e.consume(); }
		if(e.getSource() == textAnneeMin){ if(textAnneeMin.getText().length() >= 4) e.consume(); }
		if(e.getSource() == textAnneeMax){ if(textAnneeMax.getText().length() >= 4) e.consume(); }

		//Une Film ne devrait pas avoir une durée supérieur à 9999 minutes (4 caractères).
		if(e.getSource() == textDuree){ if(textDuree.getText().length() >= 4) e.consume(); }
		if(e.getSource() == textDureeMin){ if(textDureeMin.getText().length() >= 4) e.consume(); }
		if(e.getSource() == textDureeMax){ if(textDureeMax.getText().length() >= 4) e.consume(); }
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}


}
