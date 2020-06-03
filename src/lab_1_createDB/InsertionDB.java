package lab_1_createDB;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import lab_1_createDB.LectureXML.Role;
import lab_1_gestion.GestionClients;
import lab_1_gestion.GestionFilms;
import lab_1_gestion.GestionPersonnes;
import lab_1_model.Client;
import lab_1_model.Film;
import lab_1_model.Personne;

/**************************************************************
 * @CLASS_TITLE:	Insertion DB
 * 
 * @Description: 	Récupère les données de lecture des fichiers
 * 					XML et démarre un processus de pré-validation.
 * 					Initialise la connexion à la DB, nettoie les
 * 					tables SQL et insère les données dans les tables.
 * 
 * @Quantités:		- Films: 631
 * 					- Clients: 21550
 * 					- Personnes: 4549
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class InsertionDB {

	/******************************
	 * Classes 
	 ******************************/
	private GestionFilms gFilms = null;
	private GestionClients gClients = null;
	private GestionPersonnes gPersonnes = null;

	/******************************
	 * Constantes - Verification
	 ******************************/
	private static final int 
	QTE_FILMS = 631,
	QTE_CLIENTS = 21550,
	QTE_PERSONNES = 4549;

	/******************************
	 * Constantes - Script SQL
	 ******************************/
	private static final String 
	SCRIPT_SQL_PERSONNES = "./sql/CleanTablesPersonnes.sql", 
	SCRIPT_SQL_CLIENTS = "./sql/CleanTablesClients.sql", 
	SCRIPT_SQL_FILMS = "./sql/CleanTablesFilms.sql";

	/******************************
	 * Constantes - Message Info
	 ******************************/
	private static final String
	SEP 			= 	"','",

	MSG_FILMS 		= " Films		*\n",
	MSG_CLIENTS 	= " Clients		*\n",
	MSG_PERSONNES 	= " Personnes		*\n",

	TAG_FILM 		= "film",
	TAG_CLIENT 		= "client",
	TAG_PERSONNE 	= "personne",

	MSG_QUANTITE 	= "\n*	XML contient: ",
	CADRE 			= "*************************************************",
	MSG_FIN 		= "\n*************************************************" +
					"\n*	Fin de la lecture du fichier XML	*";

	/******************************
	 * Variable - Tag 
	 ******************************/
	private String tag = null;


	/******************************************************
	 * @Titre:			Insertion DB CONSTRUCTOR
	 * 
	 * @Resumer:		Constructeur initialise les classes
	 * 					de gestion et affichage de
	 * 					l'information des fichiers XML dans
	 * 					la Console. L'application ne dépend
	 * 					pas de ces classes pour fonctionner.
	 * 					
	 ******************************************************/
	public InsertionDB(){
		gFilms = new GestionFilms();
		gClients = new GestionClients();
		gPersonnes = new GestionPersonnes();
	}


	/******************************************************
	 * @Titre:			Insertion Film
	 * 
	 * @Resumer:		Nettoyage de la table FILMS et 
	 * 					insertion des valeurs dans la table
	 * 					FILMS, REALISATEURS, SCENARISTES,
	 * 					GENRES, PAYS, ROLES et ANNONCES de 
	 * 					la DB.
	 * 
	 * @Inspiration :	
	 * 
	 ******************************************************/
	public void insertionFilm(boolean toDB, int id, String titre, int annee,
			ArrayList<String> pays, String langue, int duree, String resume,
			ArrayList<String> genres, String realisateurNom, int realisateurId,
			ArrayList<String> scenaristes,
			ArrayList<Role> roles, String poster,
			ArrayList<String> annonces) {         

		//Affiche l'information dans la Console et effectue une pré-validation
		gFilms.afficherFilms(id,titre,annee,pays,langue,
				duree,resume,genres,realisateurNom,
				realisateurId, scenaristes,
				roles,poster,annonces);

		//Confirme qu'il ne manque pas de Films
		if(gFilms.getListFilms().size() == gFilms.getNbFilms() && QTE_FILMS == gFilms.getNbFilms() && toDB){

			//Initialisation connexion DB et nettoyage des tables (script SQL)
			ConnexionDB connectDB = new ConnexionDB();
			
			//Demarre l'execution d'un script SQL pour nettoyer les tables avant insertion.
			new ExecuteScriptSQL(SCRIPT_SQL_FILMS, connectDB.getConnection());
			
			//Tous les Films récupérés après la lecture du fichier XML
			ArrayList<Film> films = gFilms.getListFilms();

			try {
				
				//Table PAYS UNIQUE
				for (int j = 0; j < gFilms.getPays().size(); j++) {
					connectDB.getStatement().executeUpdate("INSERT INTO PAYS_UNIQUE (PAYS) VALUES ('" + 
							gFilms.getPays().get(j) + "')");
				}
				
				//Table GENRES UNIQUE
				for (int j = 0; j < gFilms.getGenres().size(); j++) {
					connectDB.getStatement().executeUpdate("INSERT INTO GENRES_UNIQUE (GENRE) VALUES ('" + 
							gFilms.getGenres().get(j) + "')");
				}
				
				//Parcour la liste des Films
				for(int i = 0; i < films.size(); i++){	
		
					String requeteFilm =
							films.get(i).getIdFilm() + SEP +
							films.get(i).getTitre() + SEP +
							films.get(i).getAnnee() + SEP +
							films.get(i).getLangue() + SEP + 
							films.get(i).getDuree() + SEP +
							films.get(i).getResume() + SEP +
							films.get(i).getPoster();

					//Table FILMS 
					connectDB.getStatement().executeUpdate("INSERT INTO FILMS (IDFILM,TITRE,ANNEE,LANGUE,DUREE,RESUME,POSTER) VALUES ('" + requeteFilm + "')");
					System.err.println((i+1) + " - Film: " + requeteFilm);
				
					
					//Table REALISATEURS
					connectDB.getStatement().executeUpdate("INSERT INTO REALISATEURS (IDPERSONNE,IDFILM) SELECT IDPERSONNE," + 
							gFilms.getListFilms().get(i).getIdFilm() + " FROM PERSONNES WHERE IDPERSONNE=" + 
							gFilms.getListFilms().get(i).getRealisateur().getId());

					//Table SCENARISTES
					for (int j = 0; j < films.get(i).getScenaristes().size(); j++) {
						connectDB.getStatement().executeUpdate("INSERT INTO SCENARISTES (IDPERSONNE,IDFILM) SELECT IDPERSONNE," + 
								films.get(i).getIdFilm() + " FROM PERSONNES WHERE NOM='" + 
								films.get(i).getScenaristes().get(j) + "'"); 
					}

					//Table GENRES
					for (int j = 0; j < films.get(i).getGenres().size(); j++) {
						connectDB.getStatement().executeUpdate("INSERT INTO GENRES (GENRE,IDFILM) VALUES ('" + 
								films.get(i).getGenres().get(j) + SEP + 
								films.get(i).getIdFilm() + "')");
					}
					
					//Table PAYS
					for (int j = 0; j < films.get(i).getPays().size(); j++) {
						connectDB.getStatement().executeUpdate("INSERT INTO PAYS (PAYS,IDFILM) VALUES ('" + 
								films.get(i).getPays().get(j) + SEP + 
								films.get(i).getIdFilm() + "')");
					}

					//Table ROLES
					for (int j = 0; j < films.get(i).getRoles().size(); j++) {
						connectDB.getStatement().executeUpdate("INSERT INTO ROLES (IDPERSONNE,PERSONNAGE,IDFILM) SELECT IDPERSONNE,'" + 
								films.get(i).getRoles().get(j).getPersonnage() + "'," + films.get(i).getIdFilm() + 
								" FROM PERSONNES WHERE IDPERSONNE=" + films.get(i).getRoles().get(j).getIdActeur());
					}

					//Table ANNONCES
					for (int j = 0; j < films.get(i).getAnnonces().size(); j++) {
						connectDB.getStatement().executeUpdate("INSERT INTO ANNONCES (LIEN,IDFILM) VALUES ('" + 
								films.get(i).getAnnonces().get(j) + SEP + 
								films.get(i).getIdFilm() + "') ");
					}
				}	
			} catch (SQLException e) {e.printStackTrace();}
			connectDB.closeConnexion();
		}
	}

	/******************************************************
	 * @Titre:			Insertion Client
	 * 
	 * @Resumer:		Nettoyage de la table CLIENTS et 
	 * 					insertion des valeurs dans la table
	 * 					CLIENTS et INFOCREDIT de la DB.
	 * 
	 ******************************************************/
	public void insertionClient(boolean toDB, int id, String nomFamille, String prenom,
			String courriel, String tel, String anniv,
			String adresse, String ville, String province,
			String codePostal, String carte, String noCarte,
			int expMois, int expAnnee, String motDePasse,
			String forfait) {

		//Affiche l'information dans la Console et effectue une pré-validation
		gClients.afficherClients(id,nomFamille,prenom,courriel,tel,
				anniv,adresse,ville,province,
				codePostal,carte,noCarte, 
				expMois,expAnnee,motDePasse,forfait);

		//Confirme qu'il ne manque pas de Clients
		if(gClients.getListClients().size() == gClients.getNbClients() && QTE_CLIENTS == gClients.getNbClients() && toDB){

			//Initialisation connexion DB et nettoyage des tables (script SQL)
			ConnexionDB connectDB = new ConnexionDB();
			
			//Demarre l'execution d'un script SQL pour nettoyer les tables avant insertion.
			new ExecuteScriptSQL(SCRIPT_SQL_CLIENTS, connectDB.getConnection());

			//Tous les Clients récupérés après la lecture du fichier XML
			ArrayList<Client> clients = gClients.getListClients();	

			try {
				//Parcour la liste des Clients
				for(int i = 0; i < clients.size(); i++){	
						
					String anniversaire = "TO_DATE ('" + clients.get(i).getAnniversaire() + "','YYYY-MM-DD')";
							
					String requeteClient =
							clients.get(i).getId() + SEP +
							clients.get(i).getPrenom() + SEP +
							clients.get(i).getNomFamille() + SEP +
							clients.get(i).getAdresse() + SEP + 
							clients.get(i).getVille() + SEP + 
							clients.get(i).getProvince() + SEP + 
							clients.get(i).getCodePostal() + SEP + 
							clients.get(i).getMotDePasse() + SEP + 
							clients.get(i).getForfait() + SEP + 
							clients.get(i).getCourriel() + SEP + 
							clients.get(i).getTel() + "'," + 
							anniversaire;

					//Table CLIENT
					connectDB.getStatement().executeUpdate("INSERT INTO CLIENTS (IDCLIENT,PRENOM,NOMFAMILLE,ADRESSE,VILLE,PROVINCE,CODEPOSTAL," +
							"MOTDEPASSE,FORFAIT,COURRIEL,NOTELEPHONE,ANNIVERSAIRE) VALUES ('" + requeteClient + ")");
					System.err.println((i+1) + " - Client: " + requeteClient);

					//Table INFOCREDIT
					connectDB.getStatement().executeUpdate("INSERT INTO INFOCREDIT (CARTE,NOCARTE,EXPMOIS,EXPANNEE,IDCLIENT) VALUES ('" + 
							clients.get(i).getInfoCredit().getCarte() + SEP + 
							clients.get(i).getInfoCredit().getNo() + SEP + 
							clients.get(i).getInfoCredit().getExpMois() + SEP +
							clients.get(i).getInfoCredit().getExpAnnee() + SEP +
							clients.get(i).getId() + "')");
				}

			} catch (SQLException e) {e.printStackTrace();}
			connectDB.closeConnexion();
		}
	}

	/******************************************************
	 * @Titre:			Insertion Personne
	 * 
	 * @Resumer:		Nettoyage de la table PERSONNES et 
	 * 					insertion des valeurs dans la table
	 * 					PERSONNE de la DB.
	 * 
	 ******************************************************/
	public void insertionPersonne(boolean toDB, int id, String nom, String anniv, String lieu, String photo, String bio){      

		//Affiche l'information dans la Console et effectue une pré-validation
		gPersonnes.afficherPersonnes(id,nom,anniv,lieu,photo,bio);

		//Confirme qu'il ne manque pas de Personnes
		if(gPersonnes.getListPersonnes().size() == gPersonnes.getNbPersonnes() && QTE_PERSONNES == gPersonnes.getNbPersonnes() && toDB){
			
			//Initialisation connexion DB et nettoyage des tables (script SQL)
			ConnexionDB connectDB = new ConnexionDB();
			
			//Demarre l'execution d'un script SQL pour nettoyer les tables avant insertion.
			new ExecuteScriptSQL(SCRIPT_SQL_PERSONNES, connectDB.getConnection());
			
			//Toutes les Personnes récupérées après la lecture du fichier XML
			ArrayList<Personne> personnes = gPersonnes.getListPersonnes();
			
			try {	
				//Parcour la liste des Personnes
				for(int i = 0; i < personnes.size(); i++){	

					String anniversaire = "TO_DATE ('" + personnes.get(i).getNaissance().getAnniversaire() + "','YYYY-MM-DD')";
					
					String requetePersonne =
							personnes.get(i).getId() + SEP +
							personnes.get(i).getNom() + SEP +
							personnes.get(i).getNaissance().getLieu() + SEP + 
							personnes.get(i).getPhoto() + "'," + 
							getBioBlocs(i,personnes) + "," +
							anniversaire;

					//Table PERSONNE
					connectDB.getStatement().executeUpdate("INSERT INTO PERSONNES (IDPERSONNE,NOM,LIEUNAISSANCE,PHOTO,BIO,ANNIVERSAIRE) VALUES ('" + requetePersonne + ")");
					System.err.println((i+1) + " - Personne: " + requetePersonne);
				}
			} catch (SQLException e) {e.printStackTrace();}
			connectDB.closeConnexion();
		}	
	}
	
	/******************************************************
	 * @Titre:			Get Bio Blocs
	 * 
	 * @Resumer:		Le type de donnée utilisée dans la
	 * 					DB pour supporter la BIO de la 
	 * 					table PERSONNE est: CLOB. Elle
	 * 					permet de garder en mémoire jusqu'à
	 * 					4GB. Cependant, Oracle limite 
	 * 					l'insertion directe à 4000 
	 * 					charactères. Il faut donc séparer
	 * 					la BIO en plusieurs blocs de String
	 * 					de moins de 4000 charactères. On 
	 * 					utilise ensuite la méthode d'oracle
	 * 					'TO_CLOB()' pour gérer et envoyer
	 * 					plusieurs blocs de String.
	 * 
	 * @Source: 		https://stackoverflow.com/questions/18394691/oracle-clob-cant-insert-beyond-4000-character
	 * 					https://anupambl.wordpress.com/2014/02/28/how-to-insert-clob-data-more-than-4000-character-in-clob-column/
	 * 
	 ******************************************************/
	private String getBioBlocs(int i, ArrayList<Personne> personnes){

		String bloc1 = "", bloc2 = "", bloc3 = "", bloc4 = "", bloc5 = "";

		//Évite de démarrer la boucle si la PERSONNE n'a pas de BIO.
		if(personnes.get(i).getBio() != null && !personnes.get(i).getBio().equals("n/a")){

			//Boucle autour de chaque charactères d'une BIO d'une PERSONNE.
			for(int j = 0; j < personnes.get(i).getBio().length(); j++){

				//On recupere chaque caractere former des blocs de String <= 3000 characteres.
				if(j <= 3000) 				bloc1 += Character.toString(personnes.get(i).getBio().charAt(j));	
				if(j > 3000 && j <= 6000) 	bloc2 += Character.toString(personnes.get(i).getBio().charAt(j));		
				if(j > 6000 && j <= 9000)	bloc3 += Character.toString(personnes.get(i).getBio().charAt(j));
				if(j > 9000 && j <= 12000)	bloc4 += Character.toString(personnes.get(i).getBio().charAt(j));
				if(j > 12000 && j <= 15000)	bloc5 += Character.toString(personnes.get(i).getBio().charAt(j));
			}
			//Méthode TO_CLOB d'Oracle permet de supporter plusieurs blocs de String. Les blocs inutilisés seront invisible dans la DB.
			return ("TO_CLOB('" + bloc1 + "') || TO_CLOB('" + bloc2 + "') || TO_CLOB('" + 
					bloc3 + "') || TO_CLOB('" + bloc4 + "') || TO_CLOB('" + bloc5 + "')");				
		}
		else{return "'n/a'";}
	}

	/******************************************************
	 * @Titre:			Afficher Quantité
	 * 
	 * @Resumer:		En fonction du tag, permet 
	 * 					d'afficher la quantité d'objets de 
	 * 					type client, personne ou film. 
	 * 
	 ******************************************************/
	public void afficherQuantite(){
		System.out.println(MSG_FIN);
		if(tag.equals(TAG_FILM))
			System.out.println(CADRE + MSG_QUANTITE + gFilms.getNbFilms() + MSG_FILMS + CADRE);
		if(tag.equals(TAG_CLIENT))
			System.out.println(CADRE + MSG_QUANTITE + gClients.getNbClients() + MSG_CLIENTS + CADRE);
		if(tag.equals(TAG_PERSONNE))
			System.out.println(CADRE + MSG_QUANTITE + gPersonnes.getNbPersonnes() + MSG_PERSONNES + CADRE);
	}

	/******************************************************
	 * @Titre:			Set Tag
	 * 
	 * @Resumer:		Mutateur Tag
	 * 
	 ******************************************************/
	public void setTag(String _tag){
		this.tag = _tag;
	}
}
