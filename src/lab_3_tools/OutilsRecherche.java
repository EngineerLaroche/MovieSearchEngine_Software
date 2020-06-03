package lab_3_tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import lab_2_connexion.SessionFacade;
import lab_3_database.QueryContentLibrary;
import lab_3_database.QueryFilmLibrary;
import lab_3_database.QueryPersonneLibrary;
import lab_3_ui.FenetreFicheFilm;
import lab_3_ui.FenetreFichePersonne;

/**************************************************************
 * @CLASS_TITLE:	Outils Recherche
 * 
 * @Description: 	Regroupe tous les outils au support de
 * 					la recherche de Film, Personne ou Contenu.
 * 					Permet aussi de gérer la recherche et la
 * 					manipulation des locations du clients.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class OutilsRecherche {

	/******************************
	 * Constante
	 ******************************/
	private static final String 
	FILMID = "filmID",
	ANNEE_DIFF = "AnneeDiff",
	DUREE_DIFF = "DureeDiff",
	ACTEUR = "Acteur",
	SCENARISTE = "Scenariste",
	REALISATEUR = "Realisateur",
	PERSONNE = "Personne",
	CATEGORIE = "Categorie",
	NOM_PRIX = "NomPrix",
	ANNEE_PRIX = "AnneePrix";


	/******************************************************
	 * Search Films
	 * 
	 * @Resumer:	Recherche Film avec les info de base.
	 * 
	 ******************************************************/
	public static void searchFilms(JTextField textTitre, JTextField textAnnee, JTextField textLangue, JTextField textDuree, 
			JTextField textResume, JButton boutonAjouter, JButton boutonExport, DefaultListModel<String> listeFilms){

		listeFilms.clear();
		QueryFilmLibrary.findFilms(textTitre.getText(), textAnnee.getText(),textLangue.getText(), textDuree.getText(), textResume.getText());

		//Les résultats trouvés sont insérés dans la liste des films
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++)
			listeFilms.addElement(SessionFacade.getSession().getResultats().get(i));
		if(!listeFilms.isEmpty()){ 
			boutonAjouter.setEnabled(true);
			boutonExport.setEnabled(true);
		}else boutonExport.setEnabled(false);
	}

	/******************************************************
	 * Search Films Advanced
	 * 
	 * @Resumer:	Recherche Film avec les info avancées
	 * 				de type comparatif. (min-max)
	 * 
	 ******************************************************/
	public static void searchFilmsAdvance(String focusListener, JTextField textFilmID, JTextField textAnneeMin, JTextField textAnneeMax, 
			JTextField textDureeMin, JTextField textDureeMax, JButton boutonAjouter, DefaultListModel<String> listeFilms){

		listeFilms.clear();

		if(focusListener.equals(FILMID)) 		QueryFilmLibrary.findFilmsByID(textFilmID.getText());
		if(focusListener.equals(ANNEE_DIFF)) 	QueryFilmLibrary.findFilmsIntervalleAnnee(textAnneeMin.getText(), textAnneeMax.getText());
		if(focusListener.equals(DUREE_DIFF))	QueryFilmLibrary.findFilmsIntervalleDuree(textDureeMin.getText(), textDureeMax.getText());
		//if(focusListener.equals(GENRE_DIFF))	query.findTitreGenreeDiff(textAnneeMin.getText(), textAnneeMax.getText());

		//Les résultats trouvés sont insérés dans la liste des films
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++)
			listeFilms.addElement(SessionFacade.getSession().getResultats().get(i));
		if(!listeFilms.isEmpty()) boutonAjouter.setEnabled(true);
	}

	/******************************************************
	 * Search All
	 * 
	 * @Resumer:	Recherche en fonction du nom d'une
	 * 				Personne et d'un genre en particulier.
	 * 
	 ******************************************************/
	public static void searchAll(String focusListener, JTextField textReali, JTextField textScena, JTextField textActeur, 
			JTextField textNom, JButton boutonAjouter, JComboCheckBox comboBox, DefaultListModel<String> listeFilms){

		listeFilms.clear();

		if(comboBox.getGenres().size() == 1){
			String genre = comboBox.getGenres().get(0);
			if(focusListener.equals(REALISATEUR)) 	QueryFilmLibrary.findFilmsByRealisateurAndGenre(textReali.getText(), genre);
			if(focusListener.equals(SCENARISTE)) 	QueryFilmLibrary.findFilmsByScenaristeAndGenre(textScena.getText(), genre);
			if(focusListener.equals(ACTEUR))		QueryFilmLibrary.findFilmsByActeurAndGenre(textActeur.getText(), genre);
			if(focusListener.equals(PERSONNE)) 		QueryFilmLibrary.findFilmsByPersonAndGenre(textNom.getText(), genre);

			//Les résultats trouvés sont insérés dans la liste des films
			for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++) 
				listeFilms.addElement(SessionFacade.getSession().getResultats().get(i));
			if(listeFilms.isEmpty()) boutonAjouter.setEnabled(false);
		}
		else{JOptionPane.showMessageDialog(null, "Vous devez choisir un Genre seulement", "Error sélection Genre", JOptionPane.ERROR_MESSAGE);}
	}

	/******************************************************
	 * Search Participants
	 * 
	 * @Resumer:	En fonction du type de participant Film
	 * 				sélectionné, on procède à la recherche 
	 * 				pour écupérer le nom. 
	 * 				(Realisateur ou Scenariste ou Acteur)
	 * 
	 ******************************************************/
	public static void searchParticipants(String focusListener, JTextField textReali, 
			JTextField textScena, JTextField textActeur, DefaultListModel<String> listePersonnes){

		listePersonnes.clear();

		if(focusListener.equals(REALISATEUR)) 	QueryPersonneLibrary.findRealisateur(textReali.getText());
		if(focusListener.equals(SCENARISTE)) 	QueryPersonneLibrary.findScenariste(textScena.getText());
		if(focusListener.equals(ACTEUR))		QueryPersonneLibrary.findActeur(textActeur.getText());

		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++){
			String nom = SessionFacade.getSession().getResultats().get(i);
			if(!listePersonnes.contains(nom)) listePersonnes.addElement(nom);
		}
	}

	/******************************************************
	 * Search Personnes
	 * 
	 * @Resumer:	On procède à la recherche de Personnes
	 * 				de tout genre selon le nom et l'ID.
	 * 
	 ******************************************************/
	public static void searchPersonnes(String focusListener, JTextField textNom, 
			JTextField textPersonneID, DefaultListModel<String> listePersonnes){

		listePersonnes.clear();
		QueryPersonneLibrary.findPersonnes(textNom.getText(), textPersonneID.getText());
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++) {
			if(!listePersonnes.contains(SessionFacade.getSession().getResultats().get(i)))
				listePersonnes.addElement(SessionFacade.getSession().getResultats().get(i));
		}
	}

	/******************************************************
	 * Recuperer Location Film
	 * 
	 * @Resumer:	Au demarrage de l'app, recupere les
	 * 				films loués du Client (DB) et insere
	 * 				le tout dans une liste afin de les 
	 * 				affichers.
	 * 				
	 ******************************************************/
	public static void recupererLocationFilm(String clientID, JButton boutonRetirer, DefaultListModel<String> listeLocation){

		//Recupere les films loué du Client
		QueryFilmLibrary.findLocationClient(clientID);
		if(!SessionFacade.getSession().getResultats().isEmpty()){
			boutonRetirer.setEnabled(true);
			for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++)
				listeLocation.addElement(SessionFacade.getSession().getResultats().get(i));
		}
	}

	/******************************************************
	 * Set Prix Location
	 * 
	 * @Resumer:	
	 * 				
	 ******************************************************/
	public static void setPrixLocation(JLabel labelPrixTotal, DefaultListModel<String> listeLocation){
		double prix = 0.00;
		DecimalFormat df = new DecimalFormat("#.##");

		for (int i = 0; i < listeLocation.size(); i++) {
			QueryFilmLibrary.findPrixLocation(listeLocation.getElementAt(i));
			prix += Double.parseDouble(SessionFacade.getSession().getResultats().get(1));
		}

		if(listeLocation.isEmpty()) labelPrixTotal.setText("Prix total des locations (0) :  0.00 $");
		else labelPrixTotal.setText("Prix total des locations (" + listeLocation.size() + ") :  " + df.format(prix) + " $");
	}

	/******************************************************
	 * Louer Film
	 * 
	 * @Resumer:	Ajout du film selectionné des résultats
	 * 				Films de la Jlist. Permet au client
	 * 				d'avoir une liste de Film loués.
	 * 				
	 ******************************************************/
	public static void louerFilm(String clientID, Point point, JList<?> listeSelect, DefaultListModel<String> listeLocation){
		int index = listeSelect.locationToIndex(point);
		if (index >= 0) {
			Object o = listeSelect.getModel().getElementAt(index);  
			if(!listeLocation.contains(o.toString())){ 
				listeLocation.addElement(o.toString());
				QueryFilmLibrary.findPrixLocation(o.toString());
				QueryFilmLibrary.locationFilm(OutilsFenetre.getRandomNumber(), Integer.parseInt(clientID), 
						Integer.parseInt(SessionFacade.getSession().getResultats().get(0)), 
						Float.parseFloat(SessionFacade.getSession().getResultats().get(1)));
			}
		}
	}

	/******************************************************
	 * Remove Film Location
	 * 
	 * @Resumer:	Retirer un film de la Location.
	 * 
	 ******************************************************/
	public static void removeFilm(String clientID, Point point, JButton boutonAjouter, JList<?> listeSelect, DefaultListModel<String> listeLocation){
		int index = listeSelect.locationToIndex(point);
		if (index >= 0){
			QueryFilmLibrary.retirerLocationFilm(clientID, listeLocation.getElementAt(index));
			listeLocation.remove(index);
		}
		if(listeLocation.isEmpty()) boutonAjouter.setEnabled(false);
	}

	/******************************************************
	 * Selection Fiche (Mouse Clicked)
	 * 
	 * @Resumer:	Permet d'identifier si le Client a 
	 * 				double cliqué sur un Film ou une
	 * 				Personne pour ensuite afficher la fiche 
	 * 				complète de la sélection en question.
	 * 
	 * @Source: 	https://stackoverflow.com/questions/32574450/double-click-event-on-jlist-element
	 * 
	 ******************************************************/
	public static void selectionFiche(JList<?> jListPersonne, JList<?> listeSelect, JList<?> jListFilm, MouseEvent e){

		//Si la JList de Personne n'est pas vide
		if(!jListPersonne.isSelectionEmpty()){		
			listeSelect.clearSelection(); //Retire les selections antérieur pour éviter les conflits
			jListFilm.clearSelection();
			if (e.getClickCount() == 2) {//Double clique sur un resultat de type Personne
				int index = listeSelect.locationToIndex(e.getPoint());
				if (index >= 0) new FenetreFichePersonne(listeSelect.getModel().getElementAt(index));	
			}
		}//Si la JList de Film n'est pas vide
		if(!jListFilm.isSelectionEmpty()){
			listeSelect.clearSelection(); //Retire les selections antérieur pour éviter les conflits
			jListPersonne.clearSelection(); 
			if (e.getClickCount() == 2) {//Double clique sur un resultat de type Film
				int index = listeSelect.locationToIndex(e.getPoint());
				if (index >= 0) new FenetreFicheFilm(listeSelect.getModel().getElementAt(index));
			}
		}
	}

	/******************************************************
	 * Selection Fiche Film (Mouse Clicked)
	 * 
	 * @Resumer:	Permet d'ouvrir la fiche d'un film 
	 * 				lorsque l'utilisateur double click
	 * 				sur un des films de la liste.
	 * 
	 * @Source: 	https://stackoverflow.com/questions/32574450/double-click-event-on-jlist-element
	 * 
	 ******************************************************/
	public static void ficheFilm(JList<?> listeSelect, JList<?> jListContenu, MouseEvent e){

		//Si la JList de Film n'est pas vide
		if(!jListContenu.isSelectionEmpty()){
			listeSelect.clearSelection(); //Retire les selections antérieur pour éviter les conflits
			if (e.getClickCount() == 2) {//Double clique sur un resultat de type Film
				int index = listeSelect.locationToIndex(e.getPoint());
				if (index >= 0) new FenetreFicheFilm(listeSelect.getModel().getElementAt(index));
			}
		}
	}

	/******************************************************
	 * Search Awards
	 * 
	 * @Resumer:	
	 * 
	 ******************************************************/
	public static void searchAwards(String focusListener, JTextField textCategorie, 
			JTextField textNomPrix, JTextField textAnneePrix, DefaultListModel<String> listeFilmAwards){

		listeFilmAwards.clear();
		if(focusListener.equals(CATEGORIE)) 	QueryContentLibrary.findAwardsCategorie(textCategorie.getText());
		if(focusListener.equals(NOM_PRIX)) 		QueryContentLibrary.findAwardsNomPrix(textNomPrix.getText());
		if(focusListener.equals(ANNEE_PRIX))	QueryContentLibrary.findAwardsAnneePrix(Integer.parseInt(textAnneePrix.getText()));

		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++){
			String titreFilm = SessionFacade.getSession().getResultats().get(i);
			if(!listeFilmAwards.contains(titreFilm)) listeFilmAwards.addElement(titreFilm);
		}
	}

	/******************************************************
	 * Search Classements
	 * 
	 * @Resumer:	Recherche les titres de films selon
	 * 				le classement. Si l'utilisateur insère
	 * 				des valeurs dans les champs Rang et 
	 * 				Semaine, alors on appel la requete approprié,
	 * 				sinon on app el la requête simple.
	 * 
	 ******************************************************/
	public static void searchClassements(JTextField textRang, JTextField textSemaine, 
			JTextField textMois, DefaultListModel<String> listeFilmClassement){

		listeFilmClassement.clear();
		if(!textRang.getText().isEmpty() && !textSemaine.getText().isEmpty() && textMois.getText().isEmpty()) 		
			QueryContentLibrary.findRangEtSemaine(Integer.parseInt(textRang.getText()),Integer.parseInt(textSemaine.getText()));

		if(!textRang.getText().isEmpty() && textSemaine.getText().isEmpty() && textMois.getText().isEmpty()) 
			QueryContentLibrary.findRang(Integer.parseInt(textRang.getText()));
		
		if(!textMois.getText().isEmpty() && textSemaine.getText().isEmpty() && textRang.getText().isEmpty()) 
			QueryContentLibrary.findMois(Integer.parseInt(textMois.getText()));

		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++){
			String titreFilm = SessionFacade.getSession().getResultats().get(i);
			if(!listeFilmClassement.contains(titreFilm)) listeFilmClassement.addElement(titreFilm);
		}
	}

	/******************************************************
	 * Search Couleur Dominante
	 * 
	 * @Resumer:	Recherche les titres de films qui ont 
	 * 				un Poster avec une couleur dominante
	 * 				similaire aux valeurs RGB reçu en param.
	 * 				La comparaison est contraint avec un
	 * 				'range' +/- (RGB).
	 * 
	 ******************************************************/
	public static void searchCouleurDominante(JTextField textRed, JTextField textGreen, JTextField textBlue, 
			JTextField textTolerance, DefaultListModel<String> listeFilmColor){
		
		if(!textRed.getText().isEmpty() && !textGreen.getText().isEmpty() && !textBlue.getText().isEmpty()){
			OutilsFenetre.setRed(Integer.parseInt(textRed.getText()));
			OutilsFenetre.setGreen(Integer.parseInt(textGreen.getText()));
			OutilsFenetre.setBlue(Integer.parseInt(textBlue.getText()));
		}

		listeFilmColor.clear();
		QueryContentLibrary.findCouleurDominante(Integer.parseInt(textRed.getText()), Integer.parseInt(textGreen.getText()), 
				Integer.parseInt(textBlue.getText()), Integer.parseInt(textTolerance.getText()));

		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++){
			String titreFilm = SessionFacade.getSession().getResultats().get(i);
			if(!listeFilmColor.contains(titreFilm)) listeFilmColor.addElement(titreFilm);
		}
	}

	/******************************************************
	 * Get XMLType Colonne 
	 * 
	 * @Resumer:	Permet d'identifier quelle colonne
	 * 				on souhaite traiter pour recuperer
	 * 				du XMLType.
	 * 
	 ******************************************************/
	public static String getXMLTypeColumn(String query){
		String colonne = null;
		if(query.trim().contains("CLASSEMENT"))  colonne = "CLASSEMENT";
		if(query.trim().contains("AWARD")) 		 colonne = "AWARD";
		if(query.trim().contains("DESCRIPTION")) colonne = "DESCRIPTION";
		return colonne;
	}
}
