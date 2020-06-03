package lab_3_ui;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import lab_2_connexion.SessionFacade;
import lab_3_database.QueryContentLibrary;
import lab_3_database.QueryFilmLibrary;
import lab_3_database.QueryPersonneLibrary;
import lab_3_database.ReadXMLType;
import lab_3_mediaPlayer.FenetreMediaPlayer;
import lab_3_tools.OutilsFenetre;

/**************************************************************
 * @CLASS_TITLE:	Fenetre Fiche Film
 * 
 * @Description: 	 Affiche l'information complète d'un film.
 * 					 Permet la lecture du contenu multimedia
 * 					 en cliquant sur le lien.
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class FenetreFicheFilm extends JFrame implements ActionListener{

	/******************************
	 * Constante Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante Serial
	 ******************************/
	private static final String
	TITRE_ERROR_WINDOW = "Error Browser Window",
	TITRE_BROKEN_LINK = "Error link multimedia",
	MSG_BROKEN_LINK = "Broken link - Error ",
	MSG_ERROR_WINDOW = "Probleme avec le format d'affichage.",
	LINK_TO_MEDIA = "<HTML><FONT color=\"#000099\"><U>Lien au Média</U></FONT></HTML>";

	/******************************
	 * Classe
	 ******************************/
	private URI uri = null;
	private Font fontGras = null;

	/******************************
	 * Variables
	 ******************************/
	private ArrayList<URI> listURI = null;

	/******************************
	 * Variables
	 ******************************/
	private String titre = null;
	private int indexMedia = 0;

	/******************************************************
	 * 					ONSTRUCTEUR
	 ******************************************************/
	public FenetreFicheFilm(Object object){

		this.titre = object.toString();
		this.setSize(620,800);
		this.setResizable(true);
		this.setVisible(true);
		this.setTitle("Film: " + titre);

		listURI = new ArrayList<>();
		fontGras = new Font("Arial", Font.BOLD, 15);

		panneauFiche();
	}

	/******************************************************
	 * @Titre:			Panneau Fiche
	 * 
	 * @Resumer:		Panneau qui regroupe toute
	 * 					l'information du film.
	 * 
	 * @Source:			https://www.mkyong.com/java/how-to-read-an-image-from-file-or-url/		
	 * 
	 ******************************************************/
	private void panneauFiche(){

		//Recupere l'ID du Film 
		QueryFilmLibrary.findFilmID(titre);
		String filmID = SessionFacade.getSession().getResultats().get(0);
		JLabel txtFilmID = new JLabel("Identifiant: ");
		JLabel labelFilmID = new JLabel(filmID);
		JPanel panelFilmID = new JPanel(new GridLayout(1,2));
		panelFilmID.add(txtFilmID);
		panelFilmID.add(labelFilmID);

		//Recupere Info Film (Table FILMS)
		QueryFilmLibrary.findInfoFilm(filmID);

		//Titre 
		JLabel txtTitre = new JLabel("Titre: ");
		JLabel labelTitre = new JLabel(SessionFacade.getSession().getResultats().get(0));
		JPanel panelTitre = new JPanel(new GridLayout(1,2));
		panelTitre.add(txtTitre);
		panelTitre.add(labelTitre);

		//Annee 
		JLabel txtAnnee = new JLabel("Année: ");
		JLabel labelAnnee = new JLabel(SessionFacade.getSession().getResultats().get(1));
		JPanel panelAnnee = new JPanel(new GridLayout(1,2));
		panelAnnee.add(txtAnnee);
		panelAnnee.add(labelAnnee);

		//Langue
		JLabel txtLangue = new JLabel("Langue: ");
		JLabel labelLangue = new JLabel(SessionFacade.getSession().getResultats().get(2));
		JPanel panelLangue = new JPanel(new GridLayout(1,2));
		panelLangue.add(txtLangue);
		panelLangue.add(labelLangue);

		//Duree
		JLabel txtDuree = new JLabel("Durée: ");
		JLabel labelDuree = new JLabel(SessionFacade.getSession().getResultats().get(3));
		JPanel panelDuree = new JPanel(new GridLayout(1,2));
		panelDuree.add(txtDuree);
		panelDuree.add(labelDuree);

		//Resume
		JLabel txtResume = new JLabel("Résumé: ");
		JTextArea resumeArea = new JTextArea(SessionFacade.getSession().getResultats().get(4));
		resumeArea.setLineWrap(true);
		resumeArea.setWrapStyleWord(true);
		resumeArea.setOpaque(false);
		resumeArea.setEditable(false);

		JPanel panelResume = new JPanel(new GridLayout(1,2));
		panelResume.add(txtResume);
		panelResume.add(resumeArea);

		//Poster avec click hyperlien    			
		JPanel panelPoster = new JPanel(new GridLayout(1,2));
		JLabel txtPoster = new JLabel("Poster: ");
		String url = SessionFacade.getSession().getResultats().get(5);
		panelPoster.add(txtPoster);
		panelPoster.add(getButtonURI(url));

		//Pays
		QueryFilmLibrary.findPays(filmID);
		ArrayList<String> pays = SessionFacade.getSession().getResultats();
		JPanel panelGroupPays = new JPanel(new GridLayout(pays.size(),1));

		for (int i = 0; i < pays.size(); i++) {
			JPanel panelPays = new JPanel(new GridLayout(1,2));
			JLabel txtPays = new JLabel("Pays: ");
			JLabel labelPays = new JLabel(pays.get(i));
			panelPays.add(txtPays);
			panelPays.add(labelPays);
			panelGroupPays.add(panelPays);
		}

		//Genres
		QueryFilmLibrary.findGenresFilm(filmID);
		ArrayList<String> genres = SessionFacade.getSession().getResultats();
		JPanel panelGroupGenres = new JPanel(new GridLayout(genres.size(),1));

		for (int i = 0; i < genres.size(); i++) {
			JPanel panelGenres = new JPanel(new GridLayout(1,2));
			JLabel txtGenres = new JLabel("Genre: ");
			JLabel labelGenres = new JLabel(genres.get(i));
			panelGenres.add(txtGenres);
			panelGenres.add(labelGenres);
			panelGroupGenres.add(panelGenres);
		}

		//Realisateurs
		QueryPersonneLibrary.findRealisateurID(filmID);
		String realisateurID = SessionFacade.getSession().getResultats().get(0);
		QueryPersonneLibrary.findNom(realisateurID);
		String realisateurNom = SessionFacade.getSession().getResultats().get(0);

		JPanel panelRealisateur = new JPanel(new GridLayout(1,2));
		JLabel txtRealisateur = new JLabel("Réalisateur: ");
		JLabel labelRealisateur = new JLabel(realisateurNom + " (" + realisateurID + ")");
		panelRealisateur.add(txtRealisateur);
		panelRealisateur.add(labelRealisateur);

		//Scenaristes
		QueryPersonneLibrary.findScenaristeID(filmID);
		ArrayList<String> sceID = SessionFacade.getSession().getResultats();
		JPanel panelGroupSce = new JPanel(new GridLayout(sceID.size(),1));

		for (int i = 0; i < sceID.size(); i++) {
			QueryPersonneLibrary.findNom(sceID.get(i));
			JPanel panelSce = new JPanel(new GridLayout(1,2));
			JLabel txtSce = new JLabel("Scénariste: ");
			JLabel labelSce = new JLabel(SessionFacade.getSession().getResultats().get(0));
			panelSce.add(txtSce);
			panelSce.add(labelSce);
			panelGroupSce.add(panelSce);
		}

		//Roles
		QueryPersonneLibrary.findActeurID(filmID);
		ArrayList<String> acteursID = SessionFacade.getSession().getResultats();	
		QueryPersonneLibrary.findPersonnage(filmID);
		ArrayList<String> personnages = SessionFacade.getSession().getResultats();			
		JPanel panelGroupRoles = new JPanel(new GridLayout(acteursID.size(),1));

		for (int i = 0; i < acteursID.size(); i++) {			
			QueryPersonneLibrary.findNom(acteursID.get(i));	
			String nomActeur = SessionFacade.getSession().getResultats().get(0);
			JPanel panelRole = new JPanel(new GridLayout(3,2));

			JLabel txtAct = new JLabel("Acteur: ");
			JLabel labelAct = new JLabel(nomActeur + " (" + acteursID.get(i) + ")");
			JLabel txtPerso = new JLabel("Personnage: ");
			JLabel labelPerso = new JLabel(personnages.get(i));

			panelRole.add(txtAct);
			panelRole.add(labelAct);
			panelRole.add(txtPerso);
			panelRole.add(labelPerso);
			panelRole.add(new JLabel(" "));
			panelRole.add(new JLabel(" "));
			panelGroupRoles.add(panelRole);
		}	

		//Annonces
		QueryFilmLibrary.findAnnonces(filmID);
		ArrayList<String> annonces = SessionFacade.getSession().getResultats();
		JPanel panelGroupAnn = new JPanel(new GridLayout(annonces.size(),2));

		for (int i = 0; i < annonces.size(); i++) {
			JPanel panelAnn = new JPanel(new GridLayout(1,2));
			JLabel txtAnn = new JLabel("Annonce: ");
			panelAnn.add(txtAnn);
			panelAnn.add(getButtonURI(annonces.get(i)));
			panelGroupAnn.add(panelAnn);
		}

		JPanel panelTitle = new JPanel();  
		TitledBorder borderTitre = OutilsFenetre.initTitledBorder("Film");
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
		panelTitle.setBorder(borderTitre);	
		panelTitle.add(panelFilmID);
		panelTitle.add(panelTitre);

		JPanel panelInfo = new JPanel(); 
		TitledBorder borderInfo = OutilsFenetre.initTitledBorder("Information");
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setBorder(borderInfo);
		panelInfo.add(panelAnnee);
		panelInfo.add(panelGroupPays);
		panelInfo.add(panelLangue);
		panelInfo.add(panelDuree);
		panelInfo.add(panelResume);
		panelInfo.add(panelGroupGenres);

		//Panel qui Regroupe l'information des Remarques (XMLType)
		JPanel panelRemarques = getPanelRemarques(filmID);

		JPanel panelReali = new JPanel();  
		TitledBorder borderReali = OutilsFenetre.initTitledBorder("Réalisateur & Scénaristes");
		panelReali.setLayout(new BoxLayout(panelReali, BoxLayout.Y_AXIS));
		panelReali.setBorder(borderReali);
		panelReali.add(panelRealisateur);
		panelReali.add(panelGroupSce);

		JPanel panelRoles = new JPanel();   
		TitledBorder borderRole = OutilsFenetre.initTitledBorder("Rôles");
		panelRoles.setLayout(new BoxLayout(panelRoles, BoxLayout.Y_AXIS));
		panelRoles.setBorder(borderRole);
		panelRoles.add(panelGroupRoles);

		JPanel panelMedia = new JPanel();     
		TitledBorder borderMedia = OutilsFenetre.initTitledBorder("Média");
		panelMedia.setLayout(new BoxLayout(panelMedia, BoxLayout.Y_AXIS));
		panelMedia.setBorder(borderMedia);
		panelMedia.add(panelPoster);
		panelMedia.add(panelGroupAnn);

		//Panel qui regroupe tous les Panels
		JPanel panelFicheFilm = new JPanel();       
		panelFicheFilm.setLayout(new BoxLayout(panelFicheFilm, BoxLayout.Y_AXIS));
		panelFicheFilm.add(panelTitle);
		panelFicheFilm.add(new JLabel(" "));
		panelFicheFilm.add(panelInfo);
		panelFicheFilm.add(new JLabel(" "));
		panelFicheFilm.add(panelRemarques);
		panelFicheFilm.add(new JLabel(" "));
		panelFicheFilm.add(panelReali);
		panelFicheFilm.add(new JLabel(" "));
		panelFicheFilm.add(panelRoles);
		panelFicheFilm.add(new JLabel(" "));
		panelFicheFilm.add(panelMedia);

		JScrollPane scroll = new JScrollPane(panelFicheFilm,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scroll);
	}

	/******************************************************
	 * @Titre:			Get Remarques Panel
	 * 
	 * @Resumer:		Retourne la Panel qui englobe 
	 * 					l'information du contenu XMLType.
	 * 
	 ******************************************************/
	private JPanel getPanelRemarques(String filmID){

		//Ranking
		QueryContentLibrary.findClassementXMLType(filmID);

		JLabel txtRanking = new JLabel("Ranking");
		JPanel panelRanking = new JPanel(new GridLayout(1,2));
		txtRanking.setFont(fontGras);
		panelRanking.add(txtRanking);
		panelRanking.add(new JLabel(" "));

		JLabel txtAnneeR = new JLabel("     Année:");
		JLabel labelAnneeR = new JLabel(ReadXMLType.readXML().getAnneeR());
		JPanel panelAnneeR = new JPanel(new GridLayout(1,2));
		panelAnneeR.add(txtAnneeR);
		panelAnneeR.add(labelAnneeR);

		JLabel txtMois = new JLabel("     Mois:");
		JLabel labelMois = new JLabel(ReadXMLType.readXML().getMois());
		JPanel panelMois = new JPanel(new GridLayout(1,2));
		panelMois.add(txtMois);
		panelMois.add(labelMois);

		String semaine = ReadXMLType.readXML().getSemaine();
		if(semaine.contains(" ")) semaine = semaine.replace(" ", " , ");

		JLabel txtSemaine = new JLabel("     Semaine:");
		JLabel labelSemaine = new JLabel(semaine);
		JPanel panelSemaine = new JPanel(new GridLayout(1,2));
		panelSemaine.add(txtSemaine);
		panelSemaine.add(labelSemaine);

		JLabel txtRang = new JLabel("     Rang:");
		JLabel labelRang = new JLabel(ReadXMLType.readXML().getRang());
		JPanel panelRang = new JPanel(new GridLayout(1,2));
		panelRang.add(txtRang);
		panelRang.add(labelRang);

		//Award
		QueryContentLibrary.findAwardXMLType(filmID);

		JLabel txtAward = new JLabel("Award");
		JPanel panelAward = new JPanel(new GridLayout(1,2));
		txtAward.setFont(fontGras);
		panelAward.add(txtAward);
		panelAward.add(new JLabel(" "));

		JLabel txtWinner = new JLabel("     Winner:");
		JTextArea labelWinner = new JTextArea(ReadXMLType.readXML().getWinner());
		labelWinner.setLineWrap(true);
		labelWinner.setWrapStyleWord(true);
		labelWinner.setOpaque(false);
		labelWinner.setEditable(false);

		JPanel panelWinner = new JPanel(new GridLayout(1,2));
		panelWinner.add(txtWinner);
		panelWinner.add(labelWinner);

		JLabel txtAnneeA = new JLabel("     Année:");
		JLabel labelAnneeA = new JLabel(ReadXMLType.readXML().getAnneeA());
		JPanel panelAnneeA = new JPanel(new GridLayout(1,2));
		panelAnneeA.add(txtAnneeA);
		panelAnneeA.add(labelAnneeA);

		JLabel txtCat = new JLabel("     Catégorie:");
		JTextArea labelCat = new JTextArea(ReadXMLType.readXML().getCateg());
		labelCat.setLineWrap(true);
		labelCat.setWrapStyleWord(true);
		labelCat.setOpaque(false);
		labelCat.setEditable(false);
		JPanel panelCat = new JPanel(new GridLayout(1,2));
		panelCat.add(txtCat);
		panelCat.add(labelCat);

		//Color (Description)
		QueryContentLibrary.findColorXMLType(filmID);

		JLabel txtColor = new JLabel("Poster color");
		JPanel panelColor = new JPanel(new GridLayout(1,2));
		txtColor.setFont(fontGras);
		panelColor.add(txtColor);
		panelColor.add(new JLabel(" "));
		
		//Recupere la panneau avec les info couleur dominante
		JPanel panelClr = OutilsFenetre.getPanelPosterClr();

		JLabel txtRGB = new JLabel("     RGB Primaire:");
		JPanel panelRGB = new JPanel(new GridLayout(1,2));
		panelRGB.add(txtRGB);
		panelRGB.add(panelClr);

		//Regroupe l'information XMLType du Film
		JPanel panelRemarques = new JPanel();   
		TitledBorder borderRemarques = OutilsFenetre.initTitledBorder("Remarques  (XMLType Content)");
		panelRemarques.setLayout(new BoxLayout(panelRemarques, BoxLayout.Y_AXIS));
		panelRemarques.setBorder(borderRemarques);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelRanking);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelAnneeR);
		panelRemarques.add(panelMois);	
		panelRemarques.add(panelSemaine);
		panelRemarques.add(panelRang);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelAward);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelAnneeA);
		panelRemarques.add(panelWinner);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelCat);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());
		panelRemarques.add(panelColor);
		panelRemarques.add(panelRGB);
		panelRemarques.add(OutilsFenetre.addEmptyPanel());

		return panelRemarques;
	}

	/******************************************************
	 * @Titre:			Get Button URI
	 * 
	 * @Resumer:		Rend un lien URL sous forme de 
	 * 					bouton pour pouvoir cliquer dessus
	 * 					et ouvrir le media dans un Browser.
	 * 
	 ******************************************************/
	private JButton getButtonURI(String url){

		MyButton button = new MyButton(indexMedia++);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);

		if(url.equals("n/a")){
			button.setText("n/a");
			return button;
		}else{
			try {
				uri = new URI(url);
				listURI.add(uri);
				button.setText(LINK_TO_MEDIA);
				button.setToolTipText(uri.toString());
				button.addActionListener(this);
			} catch (URISyntaxException e) {e.printStackTrace();}
			return button;
		}
	}

	/******************************************************
	 * @Titre:			Action Performed
	 * 
	 * @Resumer:		Initialise une nouvelle fenetre
	 * 					pour la lecture du contenu 
	 * 					multimedia de type Annonce
	 * 					ou Poster des Films. Avant de
	 * 					demarrer l'affichage du multimedia
	 * 					on verifie si le lien est valide.
	 * 
	 ******************************************************/
	public void actionPerformed(ActionEvent e) {	
		MyButton button = (MyButton) e.getSource();
		String link = listURI.get(button.index).toString();
		int errorCode = OutilsFenetre.getHttpErrorType(link);

		if(errorCode < 400 && errorCode >= 0 && link != null && link.length() > 0){
			if (Desktop.isDesktopSupported()){ 
				SwingUtilities.invokeLater(new Runnable() { 
					public void run() { new FenetreMediaPlayer(link); } 
				}); 
			}else  JOptionPane.showMessageDialog(this, MSG_ERROR_WINDOW, TITRE_ERROR_WINDOW, JOptionPane.ERROR_MESSAGE);
		}else JOptionPane.showMessageDialog(this, MSG_BROKEN_LINK + errorCode, TITRE_BROKEN_LINK, JOptionPane.ERROR_MESSAGE);
	}


	/******************************************************
	 * @Titre:			My Button
	 * 
	 * @Resumer:		Permet d'associer un index a un
	 * 					JButton pour permettre l'ouverture
	 * 					du bon contenu multimedia dans 
	 * 					la serie de contenus du Film.
	 * 
	 ******************************************************/
	private class MyButton extends JButton {		
		private static final long serialVersionUID = 1L;
		public int index;	
		MyButton(int index) {
			super(String.valueOf(index));
			this.index = index;
		}	
	}
}