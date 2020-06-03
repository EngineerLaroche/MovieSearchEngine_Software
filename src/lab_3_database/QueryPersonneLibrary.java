package lab_3_database;

import lab_2_connexion.SessionFacade;

/**************************************************************
 * @CLASS_TITLE:	Query Personne Library
 * 
 * @Description: 	C'est ici qu'on retrouve toutes les requetes
 * 					nécessaire à la recherche Personne.On passe par 
 * 					la Session pour envoyer les requetes vers la
 * 					DB dans le but de respecter le patron de 
 * 					conception Façade.
 * 
 * 					Cette librairie inclue plus de requetes 
 * 					Personne que nécessaire. 
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class QueryPersonneLibrary{

	/******************************************************
	 * @Titre:		Find Realisateur ID
	 ******************************************************/
	public static void findRealisateurID(String filmID){
		SessionFacade.getSession().submitQuery("SELECT IDPERSONNE FROM REALISATEURS WHERE IDFILM='" + filmID + "'"); 
	}

	/******************************************************
	 * @Titre:		Find Realisateur (Nom)
	 ******************************************************/
	public static void findRealisateur(String nom){
		SessionFacade.getSession().submitQuery("SELECT NOM FROM PERSONNES WHERE IDPERSONNE IN "
				+ "(SELECT IDPERSONNE FROM REALISATEURS WHERE IDPERSONNE IN"
				+ "(SELECT IDPERSONNE FROM PERSONNES WHERE lower(NOM) "
				+ "LIKE lower('%" + nom + "%')))");
	}

	/******************************************************
	 * @Titre:		Find Scenariste (Nom)
	 ******************************************************/
	public static void findScenariste(String nom){
		SessionFacade.getSession().submitQuery("SELECT NOM FROM PERSONNES WHERE IDPERSONNE IN "
				+ "(SELECT IDPERSONNE FROM SCENARISTES WHERE IDPERSONNE IN"
				+ "(SELECT IDPERSONNE FROM PERSONNES WHERE lower(NOM) "
				+ "LIKE lower('%" + nom + "%')))");
	}

	/******************************************************
	 * @Titre:		Find Scenaristes ID
	 ******************************************************/
	public static void findScenaristeID(String filmID){
		SessionFacade.getSession().submitQuery("SELECT IDPERSONNE FROM SCENARISTES WHERE IDFILM='" + filmID + "'"); 
	}

	/******************************************************
	 * @Titre:		Find Acteur (Nom)
	 ******************************************************/
	public static void findActeur(String nom){
		SessionFacade.getSession().submitQuery("SELECT NOM FROM PERSONNES WHERE IDPERSONNE IN "
				+ "(SELECT IDPERSONNE FROM ROLES WHERE IDPERSONNE IN"
				+ "(SELECT IDPERSONNE FROM PERSONNES WHERE lower(NOM) "
				+ "LIKE lower('%" + nom + "%')))");
	}

	/******************************************************
	 * @Titre:		Find Acteur ID
	 ******************************************************/
	public static void findActeurID(String filmID){
		SessionFacade.getSession().submitQuery("SELECT IDPERSONNE FROM ROLES WHERE IDFILM='" + filmID + "'");
	}

	/******************************************************
	 * @Titre:		Find Personnage (Acteur)
	 ******************************************************/
	public static void findPersonnage(String filmID){
		SessionFacade.getSession().submitQuery("SELECT PERSONNAGE FROM ROLES WHERE IDFILM='" + filmID + "'");
	}

	/******************************************************
	 * @Titre:		Find Personnes 
	 ******************************************************/
	public static void findPersonnes(String nom, String id){
		SessionFacade.getSession().submitQuery("SELECT NOM FROM PERSONNES WHERE "
				+ "lower(NOM) LIKE lower('%" + nom + "%') AND IDPERSONNE LIKE '%" + id + "%'");
	}

	/******************************************************
	 * @Titre:		Find Personnes ID
	 ******************************************************/
	public static void findPersonnesID(String nom){
		SessionFacade.getSession().submitQuery("SELECT IDPERSONNE FROM PERSONNES WHERE lower(NOM) LIKE lower('%" + nom + "%')");
	}

	/******************************************************
	 * @Titre:		Find Personne Anniversaire
	 ******************************************************/
	public static void findPersonneAnniv(String personneID){
		SessionFacade.getSession().submitQuery("SELECT ANNIVERSAIRE FROM PERSONNES WHERE IDPERSONNE='" + personneID + "'");
	}

	/******************************************************
	 * @Titre:		Find Personne Lieu Naissance
	 ******************************************************/
	public static void findPersonneLieu(String personneID){
		SessionFacade.getSession().submitQuery("SELECT LIEUNAISSANCE FROM PERSONNES WHERE IDPERSONNE='" + personneID + "'");
	}

	/******************************************************
	 * @Titre:		Find Personne Photo
	 ******************************************************/
	public static void findPersonnePhoto(String personneID){
		SessionFacade.getSession().submitQuery("SELECT PHOTO FROM PERSONNES WHERE IDPERSONNE='" + personneID + "'");
	}

	/******************************************************
	 * @Titre:		Find Personne Bio
	 ******************************************************/
	public static void findPersonneBio(String personneID){
		SessionFacade.getSession().submitQuery("SELECT BIO FROM PERSONNES WHERE IDPERSONNE='" + personneID + "'");
	}

	/******************************************************
	 * @Titre:		Find Nom
	 ******************************************************/
	public static void findNom(String personneID){
		SessionFacade.getSession().submitQuery("SELECT NOM FROM PERSONNES WHERE IDPERSONNE='" + personneID + "'");
	}

	/******************************************************
	 * @Titre:		Find Client
	 ******************************************************/
	public static void findClient(String clientID){
		SessionFacade.getSession().submitQuery("SELECT PRENOM,NOMFAMILLE,NOTELEPHONE,ANNIVERSAIRE,"
				+ "COURRIEL,MOTDEPASSE,ADRESSE,VILLE,PROVINCE,CODEPOSTAL,"
				+ "FORFAIT,IDCLIENT FROM CLIENTS WHERE IDCLIENT='" + clientID + "'");
	}

	/******************************************************
	 * @Titre:		Find Credit Client
	 ******************************************************/
	public static void findCredit(String idCredit){
		SessionFacade.getSession().submitQuery("SELECT CARTE,NOCARTE,EXPMOIS,EXPANNEE "
				+ "FROM INFOCREDIT WHERE IDCLIENT='" + idCredit + "'");
	}


	/******************************************************
	 * @Titre:		Find Password
	 ******************************************************/
	public static void findPassw(String email){
		SessionFacade.getSession().submitQuery("SELECT MOTDEPASSE FROM CLIENTS WHERE COURRIEL='" + email + "'");
	}

	/******************************************************
	 * @Titre:		Find Client ID
	 ******************************************************/
	public static void findClientID(String email){
		SessionFacade.getSession().submitQuery("SELECT IDCLIENT FROM CLIENTS WHERE COURRIEL='" + email + "'");
	}

	/******************************************************
	 * @Titre:		Find Client Email
	 ******************************************************/
	public static void findClientEmail(String clientID){
		SessionFacade.getSession().submitQuery("SELECT COURRIEL FROM CLIENTS WHERE IDCLIENT='" + clientID + "'");
	}

	/******************************************************
	 * @Titre:		Update Client
	 ******************************************************/
	public static boolean updateClient(String prenom, String nomFamille, String courriel, String motDePasse, 
			String tel, String adresse, String ville, String province,
			String codePostal, String forfait, String clientID){

		return SessionFacade.getSession().submitQuery("UPDATE CLIENTS SET PRENOM='" + prenom + "', NOMFAMILLE='" + nomFamille +
				"', COURRIEL='" + courriel + "', MOTDEPASSE='" + motDePasse +"', NOTELEPHONE='" + tel + 
				"', ADRESSE='" + adresse + "', VILLE='" + ville + "', PROVINCE='" + province + "', CODEPOSTAL='" + codePostal + 
				"', FORFAIT='" + forfait + "' WHERE IDCLIENT='" + clientID + "'"); 
	}

	/******************************************************
	 * @Titre:		Update Credit
	 ******************************************************/
	public static boolean updateCredit(int clientID, String carte, String noCarte, int expMois, int expAnnee){
		return SessionFacade.getSession().submitQuery("UPDATE INFOCREDIT SET CARTE='" + carte + "', NOCARTE='" + noCarte +
				"', EXPMOIS='" + expMois + "', EXPANNEE='" + expAnnee + "' WHERE IDCLIENT='" + clientID + "'");
	}
}
