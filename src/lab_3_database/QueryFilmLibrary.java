package lab_3_database;

import lab_2_connexion.SessionFacade;

/**************************************************************
 * @CLASS_TITLE:	Query Film Library
 * 
 * @Description: 	C'est ici qu'on retrouve toutes les requetes
 * 					nécessaire à la recherche Film.On passe par 
 * 					la Session pour envoyer les requetes vers la
 * 					DB dans le but de respecter le patron de 
 * 					conception Façade.
 * 
 * 					Cette librairie inclue plus de requetes 
 * 					Film que nécessaire. 
 * 					
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class QueryFilmLibrary {

	/******************************************************
	 * @Titre:		Find Genres Uniques
	 ******************************************************/
	public static void findGenresUniques(){
		SessionFacade.getSession().submitQuery("SELECT * FROM GENRES_UNIQUE");
	}

	/******************************************************
	 * @Titre:		Find Films
	 ******************************************************/
	public static void findFilms(String titre, String annee, String langue, String duree, String resume){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE " +
				"(lower(TITRE) LIKE lower('%" + titre + "%')) AND " +
				"(ANNEE LIKE ('%" + annee + "%')) AND " +
				"(lower(LANGUE) LIKE lower('%" + langue + "%')) AND " +
				"(DUREE LIKE ('%" + duree + "%')) AND " +
				"(lower(RESUME) LIKE lower('%" + resume + "%'))");
	}

	/******************************************************
	 * @Titre:	Find Films by person and genre 
	 ******************************************************/
	public static void findFilmsByPersonAndGenre(String nom, String genre){		
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS, PERSONNES WHERE IDFILM IN " +
				"(SELECT IDFILM FROM SCENARISTES WHERE IDPERSONNE = " +
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "')) " + 
				"OR IDFILM IN (SELECT IDFILM FROM ROLES WHERE IDPERSONNE = " +  
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "')) " +
				"OR IDFILM IN (SELECT IDFILM FROM REALISATEURS WHERE IDPERSONNE = " +
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "'))" +
				"AND IDFILM IN (SELECT IDFILM FROM GENRES WHERE GENRE = '" + genre + "') GROUP BY TITRE");
	}

	/******************************************************
	 * @Titre:	Find Films by Realisateur and genre 
	 ******************************************************/
	public static void findFilmsByRealisateurAndGenre(String nom, String genre){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS, PERSONNES WHERE IDFILM IN " +
				"(SELECT IDFILM FROM REALISATEURS WHERE IDPERSONNE = " +
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "'))" +
				"AND IDFILM IN (SELECT IDFILM FROM GENRES WHERE GENRE = '" + genre + "') GROUP BY TITRE");
	}

	/******************************************************
	 * @Titre:	Find Films by Scenariste and genre 
	 ******************************************************/
	public static void findFilmsByScenaristeAndGenre(String nom, String genre){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS, PERSONNES WHERE IDFILM IN " +
				"(SELECT IDFILM FROM SCENARISTES WHERE IDPERSONNE = " +
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "'))" +
				"AND IDFILM IN (SELECT IDFILM FROM GENRES WHERE GENRE = '" + genre + "') GROUP BY TITRE");
	}

	/******************************************************
	 * @Titre:	Find Films by Acteur and genre 
	 ******************************************************/
	public static void findFilmsByActeurAndGenre(String nom, String genre){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS, PERSONNES WHERE IDFILM IN " +
				"(SELECT IDFILM FROM ROLES WHERE IDPERSONNE = " +
				"(SELECT IDPERSONNE FROM PERSONNES WHERE NOM ='" + nom + "'))" +
				"AND IDFILM IN (SELECT IDFILM FROM GENRES WHERE GENRE = '" + genre + "') GROUP BY TITRE");
	}

	/******************************************************
	 * @Titre:	Find Films juste avec l'ID Film
	 ******************************************************/
	public static void findFilmsByID(String filmID){	
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE (IDFILM LIKE ('%"+ filmID +"%'))");
	}

	/******************************************************
	 * @Titre:	Find Films by years interval
	 ******************************************************/
	public static void findFilmsIntervalleAnnee(String anneeMin, String anneeMax){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE " +
				"(ANNEE BETWEEN '" + anneeMin + "' AND '" + anneeMax + "')");
	}

	/******************************************************
	 * @Titre:	Find Films by duration interval 
	 ******************************************************/
	public static void findFilmsIntervalleDuree(String dureeMin, String dureeMax){	
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE " +
				"(DUREE BETWEEN '" + dureeMin + "' AND '" + dureeMax + "')");
	}

	/******************************************************
	 * @Titre:		Find Film avec Personne
	 ******************************************************/
	public static void findFilmByPersonne(String nom){
		SessionFacade.getSession().submitQuery("SELECT IDPERSONNE,NOM,IDFILM,TITRE,ANNEE,LANGUE,DUREE,RESUME "
				+ "FROM FILMS,PERSONNES WHERE REGEXP_LIKE(NOM,'" + nom + "','i')");
	}

	/******************************************************
	 * @Titre:		Find Film avec Personne et Genre
	 ******************************************************/
	public static void findFilmPersonneGenre(String genre, String nom){
		SessionFacade.getSession().submitQuery("SELECT * FROM GENRES WHERE GENRE='" + genre + "' AND IDFILM "
				+ "IN(SELECT IDFILM FROM FILMS,PERSONNES WHERE REGEXP_LIKE(NOM,'" + nom + "','i'))");
	}

	/******************************************************
	 * @Titre:		Find Film ID
	 ******************************************************/
	public static void findFilmID(String titreFilm){
		SessionFacade.getSession().submitQuery("SELECT IDFILM FROM FILMS WHERE lower(TITRE)=lower('" + titreFilm + "')");
	}

	/******************************************************
	 * @Titre:		Find Film Titre
	 ******************************************************/
	public static void findFilmTitre(String idFilm){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE IDFILM='" + idFilm + "'");
	}

	/******************************************************
	 * @Titre:		Find Information Film
	 ******************************************************/
	public static void findInfoFilm(String filmID){
		SessionFacade.getSession().submitQuery("SELECT TITRE,ANNEE,LANGUE,DUREE,RESUME,POSTER FROM FILMS WHERE IDFILM='" + filmID + "'");
	}

	/******************************************************
	 * @Titre:		Find Genres
	 ******************************************************/
	public static void findGenresFilm(String filmID){
		SessionFacade.getSession().submitQuery("SELECT GENRE FROM GENRES WHERE IDFILM='" + filmID + "'");
	}

	/******************************************************
	 * @Titre:		Find Genres Exclude
	 ******************************************************/
	public static void findGenresFilmExclude(String filmID, String genre){
		SessionFacade.getSession().submitQuery("SELECT * FROM GENRES WHERE IDFILM='" + filmID + "' AND !='" + genre + "'");
	}
	
	/******************************************************
	 * @Titre:		Find Annonces
	 ******************************************************/
	public static void findAnnonces(String filmID){
		SessionFacade.getSession().submitQuery("SELECT LIEN FROM ANNONCES WHERE IDFILM='" + filmID + "'");
	}

	/******************************************************
	 * @Titre:		Find Pays
	 ******************************************************/
	public static void findPays(String filmID){
		SessionFacade.getSession().submitQuery("SELECT PAYS FROM PAYS WHERE IDFILM='" + filmID + "'");
	}
	
	/******************************************************
	 * @Titre:		Ajouter Location Film
	 ******************************************************/
	public static void locationFilm(int locationID, int clientID, int filmID, float prix){
		SessionFacade.getSession().submitQuery("INSERT INTO LOUER (IDLOUER,IDCLIENT,IDFILM,COUT_LOCATION) "
				+ "VALUES (" + locationID + "," + clientID + "," + filmID + "," + prix + ")");
	}

	/******************************************************
	 * @Titre:		Find Prix Location
	 ******************************************************/
	public static void findPrixLocation(String titreFilm){
		SessionFacade.getSession().submitQuery("SELECT IDFILM,PRIX FROM FILMS WHERE TITRE='" + titreFilm + "'");
	}

	/******************************************************
	 * @Titre:		Retirer Location Film
	 ******************************************************/
	public static void retirerLocationFilm(String clientID, String titreFilm){
		SessionFacade.getSession().submitQuery("DELETE FROM LOUER WHERE IDCLIENT='" + clientID + "' AND "
				+ "IDFILM IN(SELECT IDFILM FROM FILMS WHERE TITRE='" + titreFilm + "')");
	}

	/******************************************************
	 * @Titre:		Find Location du CLient
	 ******************************************************/
	public static void findLocationClient(String clientID){
		SessionFacade.getSession().submitQuery("SELECT TITRE FROM FILMS WHERE IDFILM IN (SELECT IDFILM FROM LOUER WHERE IDCLIENT='" + clientID + "')");
	}
}
