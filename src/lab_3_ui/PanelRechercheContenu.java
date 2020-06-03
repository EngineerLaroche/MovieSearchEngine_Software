package lab_3_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import lab_3_tools.OutilsFenetre;
import lab_3_tools.OutilsRecherche;


/**************************************************************
 * @CLASS_TITLE:	Panel Recherche Contenu
 * 
 * @Description: 	Panneau qui permet la recherche de contenu
 * 					pour trouver des titre de films de la DB.
 * 					On retrouve une recherche par Awards, 
 * 					Classement et par couleur similaire au
 * 					Poster d'un Film.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class PanelRechercheContenu extends JPanel implements ActionListener, MouseListener, KeyListener{

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante
	 ******************************/
	private static final String 
	CATEGORIE = "Categorie",
	NOM_PRIX = "NomPrix",
	ANNEE_PRIX = "AnneePrix";

	/******************************
	 * Listes de resultats
	 ******************************/
	private int[] rgb = null;

	private JList<?> 
	jListeSelect = null,
	jListFilmColor = null,
	jListFilmAwards = null,
	jListFilmClassements = null;

	private DefaultListModel<String>
	listFilmColor = null,
	listFilmAwards = null,
	listFilmClassements = null;

	/******************************
	 * Swing Object 
	 ******************************/
	private JPanel 
	panelImage = null,
	panelClrDominante = null;

	private Font 
	fontSimple = null,
	fontGras = null;

	private JTextField  
	textRed = null,
	textGreen = null,
	textBlue = null,
	textTolerance = null,

	textCategorie = null,
	textNomPrix = null,
	textAnneePrix = null,

	textRang = null,
	textMois = null,
	textSemaine = null;

	private JButton
	boutonSearchColor = null,
	boutonSearchAwards = null,
	boutonFileChooser = null,
	boutonSearchClassement = null;

	/******************************
	 * Variable
	 ******************************/
	private String focusListener = null;



	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public PanelRechercheContenu(){
		fontGras = new Font("Arial", Font.BOLD, 15);
		fontSimple = new Font("Arial", Font.PLAIN, 15);
		fenetreRecherche(); 
	}

	/******************************************************
	 * Fenetre Recherche Contenu Multimedia
	 * 
	 * @Resumer:	Récupère les trois panneaux de recherche 
	 * 				de contenu sur les Awards, les classements
	 * 				et les similarités de couleurs du Poster.
	 * 
	 ******************************************************/
	private void fenetreRecherche(){

		JPanel panelActivity = new JPanel();
		panelActivity.setLayout(new BoxLayout(panelActivity, BoxLayout.Y_AXIS));
		panelActivity.add(new JLabel(" ")); 	
		panelActivity.add(awardPanel()); 
		panelActivity.add(new JLabel(" ")); 
		panelActivity.add(classementPanel());
		panelActivity.add(new JLabel(" ")); 
		panelActivity.add(colorPanel());  	

		this.setLayout(new BorderLayout());
		this.add(panelActivity);
	}

	/******************************************************
	 * Award Panel
	 * 
	 * @Resumer:	Permet de trouver les films qui ont
	 * 				de l'information sur les Awards
	 * 				similaire à ce que l'utilisateur cherche.
	 * 
	 ******************************************************/
	private JPanel awardPanel(){

		JLabel 
		labelCategorie = new JLabel("Catégorie"),
		labelNomPrix = new JLabel("Winner Price"),
		labelAnneePrix = new JLabel("Année Prix");

		//Grosseur des caracteres des Labels
		labelCategorie.setFont(fontSimple);
		labelNomPrix.setFont(fontSimple);
		labelAnneePrix.setFont(fontSimple);

		//Boite de texte pour chercher un contenu Award
		textCategorie = new JTextField(15);
		textNomPrix = new JTextField(15);
		textAnneePrix = new JTextField(15);

		textCategorie.setFont(fontSimple);
		textNomPrix.setFont(fontSimple);
		textAnneePrix.setFont(fontSimple);

		textCategorie.addKeyListener(this);
		textNomPrix.addKeyListener(this);
		textAnneePrix.addKeyListener(this);

		//Parametre du bouton de recherche Award
		boutonSearchAwards = new JButton("Search Awards");
		boutonSearchAwards.addActionListener(this);
		boutonSearchAwards.setFont(fontSimple);
		boutonSearchAwards.setEnabled(false);

		//Panel recherche Award
		TitledBorder borderAward = OutilsFenetre.initTitledBorder("AWARD");
		JPanel panelSearch = new JPanel(new GridLayout(4,1,10,10));
		panelSearch.setBorder(borderAward);

		//Ajouts des elements de recherche Awards au panneau
		panelSearch.add(labelCategorie);
		panelSearch.add(textCategorie);
		panelSearch.add(labelNomPrix);
		panelSearch.add(textNomPrix);
		panelSearch.add(labelAnneePrix);
		panelSearch.add(textAnneePrix);
		panelSearch.add(boutonSearchAwards);

		//Liste qui garde en memoire les resultats trouvés
		listFilmAwards = new DefaultListModel<String>();
		jListFilmAwards = new JList<String>(listFilmAwards);		
		jListFilmAwards.addMouseListener(this);

		//Panel resultats Award
		TitledBorder borderResultats = OutilsFenetre.initTitledBorder("RESULTATS - AWARDS");
		JPanel panelResultats = new JPanel(new BorderLayout());
		panelResultats.setBorder(borderResultats);
		panelResultats.add(new JScrollPane(jListFilmAwards));

		//Reunion des Panel Search et Resultats
		JPanel panelAward = new JPanel(new GridLayout(1,2,10,10));
		panelAward.add(panelSearch);
		panelAward.add(panelResultats);

		return panelAward;
	}

	/******************************************************
	 * Classement Panel
	 * 
	 * @Resumer:	Permet de trouver les films qui ont
	 * 				de l'information sur le classement
	 * 				similaire à ce que l'utilisateur cherche.
	 * 
	 ******************************************************/
	private JPanel classementPanel(){

		JLabel 
		labelRang = new JLabel("Rang"),
		labelMois = new JLabel("Mois"),
		labelSemaine = new JLabel("Semaine");

		//Grosseur des caracteres des Labels
		labelRang.setFont(fontSimple);
		labelMois.setFont(fontSimple);
		labelSemaine.setFont(fontSimple);

		//Boite de texte pour chercher un contenu Classement
		textRang = new JTextField(15);
		textMois = new JTextField(15);
		textSemaine = new JTextField(15);

		textRang.setFont(fontSimple);
		textMois.setFont(fontSimple);
		textSemaine.setFont(fontSimple);

		textRang.addKeyListener(this);
		textMois.addKeyListener(this);
		textSemaine.addKeyListener(this);

		//Parametre du bouton de recherche Classement
		boutonSearchClassement = new JButton("Search Classement");
		boutonSearchClassement.addActionListener(this);
		boutonSearchClassement.setFont(fontSimple);
		boutonSearchClassement.setEnabled(false);

		//Panel recherche Classement
		TitledBorder borderClassement = OutilsFenetre.initTitledBorder("CLASSEMENT");
		JPanel panelSearch = new JPanel(new GridLayout(4,2,10,10));
		panelSearch.setBorder(borderClassement);

		//Ajouts des elements de recherche Classement au panneau
		panelSearch.add(labelRang);
		panelSearch.add(textRang);
		panelSearch.add(labelSemaine);
		panelSearch.add(textSemaine);
		panelSearch.add(labelMois);
		panelSearch.add(textMois);
		panelSearch.add(boutonSearchClassement);

		//Liste qui garde en memoire les resultats trouvés
		listFilmClassements = new DefaultListModel<String>();
		jListFilmClassements = new JList<String>(listFilmClassements);		
		jListFilmClassements.addMouseListener(this);

		//Panel resultats Classement
		TitledBorder borderResultats = OutilsFenetre.initTitledBorder("RESULTATS - CLASSEMENT");
		JPanel panelResultats = new JPanel(new BorderLayout());
		panelResultats.setBorder(borderResultats);
		panelResultats.add(new JScrollPane(jListFilmClassements));

		//Reunion des Panel Search et Resultats
		JPanel panelCLassement = new JPanel(new GridLayout(1,2,10,10));
		panelCLassement.add(panelSearch);
		panelCLassement.add(panelResultats);

		return panelCLassement;
	}

	/******************************************************
	 * Color Panel
	 * 
	 * @Resumer:	Permet de trouver les films qui ont 
	 * 				un "poster" similaire en terme de 
	 * 				couleur dominante avec une image 
	 * 				importé par l'utilisateur.
	 * 
	 ******************************************************/
	private JPanel colorPanel(){

		JLabel 
		labelCouleur = new JLabel("Couleur Dominante"),
		labelTolerance = new JLabel("Tolérance Couleur (+/-)"),
		labelRed = new JLabel("R"),
		labelGreen = new JLabel("G"),
		labelBlue = new JLabel("B");

		labelCouleur.setHorizontalAlignment(JLabel.CENTER);
		labelTolerance.setHorizontalAlignment(JLabel.CENTER);

		//Grosseur des caracteres des Labels
		labelCouleur.setFont(fontSimple);
		labelTolerance.setFont(fontSimple);
		labelRed.setFont(fontSimple);
		labelGreen.setFont(fontSimple);
		labelBlue.setFont(fontSimple);

		//Boite de texte avec les valeurs RGB de la couleur dominante
		textRed = new JTextField(4);
		textGreen = new JTextField(4);
		textBlue = new JTextField(4);
		textTolerance = new JTextField(4);

		textRed.addKeyListener(this);
		textGreen.addKeyListener(this);
		textBlue.addKeyListener(this);
		textTolerance.addKeyListener(this);

		textRed.setFont(fontSimple);
		textGreen.setFont(fontSimple);
		textBlue.setFont(fontSimple);
		textTolerance.setFont(fontSimple);

		textTolerance.setText("10");

		//Parametre du bouton de recherche Color
		boutonSearchColor = new JButton("Search Similar Color");
		boutonSearchColor.addActionListener(this);
		boutonSearchColor.setFont(fontSimple);
		boutonSearchColor.setEnabled(false);

		boutonFileChooser = new JButton("File Chooser");
		boutonFileChooser.addActionListener(this);
		boutonFileChooser.setFont(fontSimple);

		JPanel panelValeursRGB = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelValeursRGB.add(labelRed);
		panelValeursRGB.add(textRed);
		panelValeursRGB.add(labelGreen);
		panelValeursRGB.add(textGreen);
		panelValeursRGB.add(labelBlue);
		panelValeursRGB.add(textBlue);

		JPanel panelTolerance = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelTolerance.add(textTolerance);

		//Paneau qui regroupe les valeurs RGB de la couleur dominante et son label
		JPanel panelRGB = new JPanel(new GridLayout(2,2,5,5));
		panelRGB.add(labelTolerance, BorderLayout.CENTER);
		panelRGB.add(labelCouleur, BorderLayout.CENTER);
		panelRGB.add(panelTolerance);
		panelRGB.add(panelValeursRGB);

		//Panel qui affiche l'image
		panelImage = new JPanel();
		panelImage.setPreferredSize(new Dimension(100,300));

		TitledBorder borderImage = OutilsFenetre.initTitledBorder("Image");
		JPanel panelImageSupport = new JPanel(new BorderLayout());
		panelImageSupport.setBorder(borderImage);
		panelImageSupport.add(panelImage);

		//Panel qui affiche la couleur dominante
		panelClrDominante = new JPanel();
		panelClrDominante.setPreferredSize(new Dimension(100,300));

		TitledBorder borderClrDominante = OutilsFenetre.initTitledBorder("Couleur Dominante");
		JPanel panelClrDominanteSupport = new JPanel(new BorderLayout());
		panelClrDominanteSupport.setBorder(borderClrDominante);
		panelClrDominanteSupport.add(panelClrDominante);

		//Panneau qui regroupe l'image importée et sacouleur dominante
		JPanel panelImageColor = new JPanel(new GridLayout(1,2,5,5));
		panelImageColor.add(panelImageSupport);
		panelImageColor.add(panelClrDominanteSupport);

		//Panneau qui regroupe le bouton de selection image et bouton recherche
		JPanel panelBoutons = new JPanel(new GridLayout(1,2,10,10));
		panelBoutons.add(boutonFileChooser);
		panelBoutons.add(boutonSearchColor);

		//Panel recherche Color
		TitledBorder borderColor = OutilsFenetre.initTitledBorder("MAIN COLOR");
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BoxLayout(panelSearch, BoxLayout.Y_AXIS));
		panelSearch.setBorder(borderColor);
		panelSearch.add(panelRGB);
		panelSearch.add(new JLabel(" ")); 
		panelSearch.add(panelImageColor);
		panelSearch.add(new JLabel(" ")); 
		panelSearch.add(panelBoutons);

		//Liste qui garde en memoire les resultats trouvés
		listFilmColor = new DefaultListModel<String>();
		jListFilmColor = new JList<String>(listFilmColor);		
		jListFilmColor.addMouseListener(this);

		//Panel resultats Color
		TitledBorder borderResultats = OutilsFenetre.initTitledBorder("RESULTATS - COLOR");
		JPanel panelResultats = new JPanel(new BorderLayout());
		panelResultats.setBorder(borderResultats);
		panelResultats.add(new JScrollPane(jListFilmColor));

		//Reunion des Panel Search et Resultats
		JPanel panelColor = new JPanel(new GridLayout(1,2,10,10));
		panelColor.add(panelSearch);
		panelColor.add(panelResultats);

		return panelColor;
	}

	/******************************************************
	 * File Chooser Set RGB
	 * 
	 * @Resumer:	Initialise un JFileChooser et recupere 
	 * 				le repertoire de l'image importée.
	 * 				Recupere la couleur dominante de 
	 * 				l'image et envoie les valeurs RGB
	 * 				dans les JTextField (RGB).
	 * 
	 ******************************************************/
	private void fileChooserSetRGB(){

		//Importe l'image, recupere la couleur dominante et insère les valeurs RGB.
		String repertoire = OutilsFenetre.buildFileChooser();
		if(repertoire != null){
			rgb = OutilsFenetre.getDominantColor(repertoire);
			textRed.setText(Integer.toString(rgb[0]));
			textGreen.setText(Integer.toString(rgb[1]));
			textBlue.setText(Integer.toString(rgb[2]));

			//Force l'affichage de la couleur dominante.	
			textRed.requestFocusInWindow();
			textGreen.requestFocusInWindow();
			textBlue.requestFocusInWindow();
			OutilsFenetre.startRobot();

			//Affiche l'image importée.
			OutilsFenetre.setImage(repertoire,panelImage); 
		}else System.err.println("(PanelRechercheContenu) Importation Image JPG annulée.");
	}

	/******************************************************
	 * Action Performed
	 * 
	 * @Resumer:	Selon le bouton que l'utilisateur appui,
	 * 				on demarre l'action de recherche de
	 * 				contenu pour trouver les titres Films.
	 * 
	 ******************************************************/
	public void actionPerformed(ActionEvent e) {

		//Initialise un JFileChooser pour importer une image JPG
		if(e.getSource() == boutonFileChooser) fileChooserSetRGB();

		//Recherche FIlm avec contenu Awards en fonction du champs sélectionné par l'utilisateur
		if(e.getSource() == boutonSearchAwards) 
			OutilsRecherche.searchAwards(focusListener,textCategorie,textNomPrix,textAnneePrix,listFilmAwards);

		//Recherche Film avec contenu Classement par rang ou par rang et semaine
		if(e.getSource() == boutonSearchClassement) 
			OutilsRecherche.searchClassements(textRang,textSemaine,textMois,listFilmClassements);

		//Recherche Film avec valeurs RGB similaire au Poster
		if(e.getSource() == boutonSearchColor){
			OutilsRecherche.searchCouleurDominante(textRed,textGreen,textBlue,textTolerance,listFilmColor);
		}
	}

	/******************************************************
	 * Mouse Clicked
	 * 
	 * @Resumer:	Selon le film de la liste sélectionné
	 * 				par le client, s'il double click dessus,
	 * 				une nouvelle fenêtre ouvre pour afficher
	 * 				la fiche complète du film.
	 * 
	 ******************************************************/
	public void mouseClicked(MouseEvent e) {
		jListeSelect = (JList<?>) e.getSource();
		if (e.getClickCount() == 2) {
			OutilsRecherche.ficheFilm(jListeSelect, jListFilmAwards, e);
			OutilsRecherche.ficheFilm(jListeSelect, jListFilmClassements, e);
			OutilsRecherche.ficheFilm(jListeSelect, jListFilmColor, e);
		}
	}

	/******************************************************
	 * Key Typed
	 * 
	 * @Resumer:	Cette section vérifie si les caractères
	 * 				entrées dans les JTextfield correspondes
	 * 				bien au type de donnée à rechercher.
	 * 				Exemple, la recherche d'une année en 
	 * 				particulier ne devrait pas supporter 
	 * 				les lettres. C'est aussi ici qu'on
	 * 				contrôle le nombre de caractère et qu'on
	 * 				change le couleur du panel qui représente
	 * 				la couleur dominante d'une image importée
	 * 				ou non.
	 * 
	 ******************************************************/
	public void keyTyped(KeyEvent e) {

		//Recupere les caracteres des JTextField
		char c = e.getKeyChar();

		if(e.getSource() == textRed || e.getSource() == textGreen || e.getSource() == textBlue || e.getSource() == textTolerance){

			//Pour seulement supporter l'entrée de chiffres.
			if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) 
				e.consume();

			//Une valeur RGB ne devrait pas avoir plus de 3 caractères (max: 255)
			if(e.getSource() == textRed)   		if(textRed.getText().length() >= 3) 	  e.consume(); 
			if(e.getSource() == textGreen) 		if(textGreen.getText().length() >= 3) 	  e.consume();
			if(e.getSource() == textBlue)  		if(textBlue.getText().length() >= 3) 	  e.consume(); 
			if(e.getSource() == textTolerance)  if(textTolerance.getText().length() >= 3) e.consume(); 

			//Si aucuns des champs RGB ne sont vide, alors on procede au changement de couleur du Panel.
			if(!textRed.getText().isEmpty() && !textGreen.getText().isEmpty() && !textBlue.getText().isEmpty() && !textTolerance.getText().isEmpty()){

				//Les valeurs RGB doivent rester entre 0 et 255
				OutilsFenetre.setMinMaxRGB(textRed, textGreen, textBlue, textTolerance);

				//On peinture le Panel avec la couleur dominante de l'image importée
				int r = Integer.parseInt(textRed.getText());
				int g = Integer.parseInt(textGreen.getText());
				int b = Integer.parseInt(textBlue.getText());
				panelClrDominante.setBackground(new Color(r,g,b));	
				boutonSearchColor.setEnabled(true);
			}else boutonSearchColor.setEnabled(false);
		}

		//Pour seulement supporter l'entrée de chiffres.
		if(e.getSource() == textAnneePrix || e.getSource() == textRang || e.getSource() == textSemaine || e.getSource() == textMois)	
			if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) 
				e.consume();

		//Pour seulement supporter l'entrée de lettres.
		if(e.getSource() == textCategorie || e.getSource() == textNomPrix)
			if (!(Character.isAlphabetic(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_SPACE)) 
				e.consume();

		//Une année ne devrait pas avoir plus de 4 caractères
		if(e.getSource() == textAnneePrix) if(textAnneePrix.getText().length() >= 4) 
			e.consume(); 

		//Un mois ne devrait pas avoir plus de 2 caractères (1-12 mois)
		if(e.getSource() == textAnneePrix) if(textAnneePrix.getText().length() >= 2) 
			e.consume(); 

		//Les semaines ne devraient pas avoir plus de 2 caractères (1-52 semaines / an)
		if(e.getSource() == textAnneePrix) if(textAnneePrix.getText().length() >= 2) 
			e.consume(); 

		//Si un des champs de la recherche Awards n'est pas vide on active le bouton de recherche, sinon on le ferme
		if(e.getSource() == textCategorie || e.getSource() == textNomPrix || e.getSource() == textAnneePrix) 
			boutonSearchAwards.setEnabled(true);
		if(c == KeyEvent.VK_BACK_SPACE && textCategorie.getText().isEmpty() && textNomPrix.getText().isEmpty() && textAnneePrix.getText().isEmpty())
			boutonSearchAwards.setEnabled(false);

		//Si un des champs de la recherche Classement n'est pas vide on active le bouton de recherche, sinon on le ferme
		if(e.getSource() == textRang || e.getSource() == textMois || (e.getSource() == textSemaine && !textRang.getText().isEmpty())) 
			boutonSearchClassement.setEnabled(true);
		if(c == KeyEvent.VK_BACK_SPACE && textRang.getText().isEmpty() && textSemaine.getText().isEmpty() && textMois.getText().isEmpty())
			boutonSearchClassement.setEnabled(false);

		//Recherche seulement par Awards Categorie
		if(e.getSource() == textCategorie){
			focusListener = CATEGORIE;
			textNomPrix.setText("");
			textAnneePrix.setText("");
		}
		//Recherche seulement par Awards Nom Prix
		if(e.getSource() == textNomPrix){
			focusListener = NOM_PRIX;
			textCategorie.setText("");
			textAnneePrix.setText("");
		}
		//Recherche seulement par Awards Annee prix
		if(e.getSource() == textAnneePrix){
			focusListener = ANNEE_PRIX;
			textCategorie.setText("");
			textNomPrix.setText("");
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
