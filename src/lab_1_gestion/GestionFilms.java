package lab_1_gestion;

import java.util.ArrayList;

import lab_1_createDB.LectureXML.Role;
import lab_1_model.Film;


/**************************************************************
 * @CLASS_TITLE:	GestionFilm
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionFilms{

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA				= "n/a",
	MSG_ID 			= "ID :		",
	MSG_TITRE 		= "Titre :		",
	MSG_ANNEE 		= "Annee :		",
	MSG_PAYS		= "Pays :		",
	MSG_LANGUE 		= "Langue :	",
	MSG_DUREE 		= "Duree :		",
	MSG_RESUME 		= "\nResume :	",
	MSG_GENRE 		= "Genre :		",
	MSG_SCENARISTE 	= "Scenariste :	",
	MSG_ROLE 		= "\nRole:",
	MSG_POSTER 		= "Poster :	",
	MSG_ANNONCE 	= "Annonce :	",

	SEPERATOR 	 = "\n============================================================\n";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Film film = null;
	private GestionRoles gRole = null;
	private GestionRealisateur gRealisateur = null;


	/***************************
	 * ArrayList
	 ***************************/
	private ArrayList<Film>    films = null;	
	private ArrayList<Integer> acteursUniques = null;
	private ArrayList<Integer> realisateursUniques = null;	
	private ArrayList<String>  paysUniques = null;
	private ArrayList<String>  genresUniques = null;
	private ArrayList<String>  scenaristesUniques = null;
	private ArrayList<String>  annoncesUniques = null;


	/***************************
	 * Variable (qte)
	 ***************************/
	private int 
	nbFilms = 0;


	/******************************************************
	 * @Titre:			GestionFilm CONSTRUCTOR
	 * 
	 * @Resumer:		Le constructeur initialise une 
	 * 					liste de films. Les autres listes
	 * 					servent à récupérer les valeurs
	 * 					unique de tous les films.
	 * 
	 ******************************************************/
	public GestionFilms(){
		films = new ArrayList<>();
		paysUniques = new ArrayList<>();
		genresUniques = new ArrayList<>();
		realisateursUniques = new ArrayList<>();
		scenaristesUniques = new ArrayList<>();
		acteursUniques = new ArrayList<>();
		annoncesUniques = new ArrayList<>();
	}


	/******************************************************
	 * @Titre:			Afficher Films XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void afficherFilms(int id, String titre, int annee,
			ArrayList<String> pays, String langue, int duree, String resume,
			ArrayList<String> genres, String realisateurNom, int realisateurId,
			ArrayList<String> scenaristes, ArrayList<Role> roles, String poster,ArrayList<String> annonces){

		film = new Film();

		//ID Film
		if(id >= 0){
			film.setIdFilm(id);
			System.out.println(MSG_ID + id);
		}else{film.setIdFilm(-1);}

		//Titre Film
		if(titre != null){

			// Si possede un " ' ", on le retire pour la BD
			if(titre.contains("'")) titre = titre.replace("'", " ");

			film.setTitre(titre);
			System.out.println(MSG_TITRE + titre);
		}else{
			film.setTitre(NA);
			System.out.println(MSG_TITRE + NA);
		}

		//Annee Film
		if(annee >= 0){
			film.setAnnee(annee);
			System.out.println(MSG_ANNEE + annee);
		}else{
			film.setAnnee(1900);
			System.out.println(MSG_ANNEE + NA);
		}

		//Les Pays du Film
		if(!pays.isEmpty()){		
			for (int i = 0; i < pays.size(); i++) {
				film.addPays(pays.get(i));
				System.out.println(MSG_PAYS + pays.get(i));

				//Pour recuperer la totalité des pays (unique)
				if(!paysUniques.contains(pays.get(i)))
					paysUniques.add(pays.get(i));
			}
		}else{
			pays.add(NA);
			System.out.println(MSG_PAYS + NA);
		}

		//Langue Film
		if(langue != null){
			film.setLangue(langue);
			System.out.println(MSG_LANGUE + langue);
		}else{
			film.setLangue(NA);
			System.out.println(MSG_LANGUE + NA);
		}

		//Duree Film
		if(duree >= 0){
			film.setDuree(duree);
			System.out.println(MSG_DUREE + duree + " min");
		}else{
			film.setDuree(-1);
			System.out.println(MSG_DUREE + NA);
		}

		//Resume Film
		if(resume != null){

			// Si possede un " ' ", on le retire pour la BD
			if(resume.contains("'")) resume = resume.replace("'", " ");

			film.setResume(resume);
			System.out.println(MSG_RESUME + resume);
		}else{
			film.setResume(NA);
			System.out.println(MSG_RESUME + NA);
		}

		//Les Genres du Film
		if(!genres.isEmpty()){
			for (int i = 0; i < genres.size(); i++) {
				film.addGenre(genres.get(i));
				System.out.println(MSG_GENRE + genres.get(i));

				if(!genresUniques.contains(genres.get(i)))
					genresUniques.add(genres.get(i));
			}
		}else{
			film.addGenre(NA);
			System.out.println(MSG_GENRE + NA);
		}

		//Realisateur Film
		if(realisateurId >= 0 || realisateurNom != null){
			gRealisateur = new GestionRealisateur(film);
			gRealisateur.insertContent(realisateurId, realisateurNom);

			if(!realisateursUniques.contains(realisateurId))
				realisateursUniques.add(realisateurId);
		}else{
			gRealisateur = new GestionRealisateur(film);
			gRealisateur.insertContent(-1, null);
		}

		//Les Scenaristes du Film
		if(!scenaristes.isEmpty()){
			for (int i = 0; i < scenaristes.size(); i++) {

				String nomScenariste = scenaristes.get(i);
				// Si possede un " ' ", on le retire pour la BD
				if(nomScenariste.contains("'")) nomScenariste = nomScenariste.replace("'", " ");

				film.addScenariste(nomScenariste);
				System.out.println(MSG_SCENARISTE + nomScenariste);

				if(!scenaristesUniques.contains(nomScenariste))
					scenaristesUniques.add(nomScenariste);
			}
		}else{
			scenaristes.add(NA);
			System.out.println(MSG_SCENARISTE + NA);
		}

		//Les Roles du Film
		if(!roles.isEmpty()){
			System.out.println(MSG_ROLE);
			for (int i = 0; i < roles.size(); i++) {
				gRole = new GestionRoles(film);
				gRole.insertContent(roles.get(i).getIdActeur(), roles.get(i).getActeur(), roles.get(i).getPersonnage());

				if(!acteursUniques.contains(roles.get(i).getIdActeur()))
					acteursUniques.add(roles.get(i).getIdActeur());
			}
		}else{
			gRole = new GestionRoles(film);
			gRole.insertContent(-1, null, null);
		}

		//Poster Film
		if(poster != null){
			film.setPoster(poster);
			System.out.println(MSG_POSTER + poster + "\n");
		}else{
			film.setPoster(NA);
			System.out.println(MSG_POSTER + NA + "\n");
		}

		
		//Les Annonces du Film
		if(!annonces.isEmpty()){
			for (int i = 0; i < annonces.size(); i++) {

				film.addAnnonce(annonces.get(i));
				System.out.println(MSG_ANNONCE + annonces.get(i));

				if(!annoncesUniques.contains(annonces.get(i)))
					annoncesUniques.add(annonces.get(i));
			}	
		}else {
			film.addAnnonce(NA);
			System.out.println(MSG_ANNONCE + NA);
		}

		System.out.println(SEPERATOR);

		//Ajout du Film a la liste des films du fichier XML
		films.add(film);
		nbFilms++;
	}

	/******************************************************
	 * @Titre:		Accesseurs de la liste des films
	 ******************************************************/
	public ArrayList<Film> getListFilms(){return films;}

	/******************************************************
	 * @Titre:		Accesseurs des éléments uniques (films)
	 ******************************************************/
	public int getNbFilms(){return nbFilms;}
	public ArrayList<String> getPays(){return paysUniques;}
	public ArrayList<String> getGenres(){return genresUniques;}
	public ArrayList<Integer> getActeurs(){return acteursUniques;}
	public ArrayList<String> getAnnonces(){return annoncesUniques;}
	public ArrayList<String> getScenaristes(){return scenaristesUniques;}
	public ArrayList<Integer> getRealisateurs(){return realisateursUniques;}
}
