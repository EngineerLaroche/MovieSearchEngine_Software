package lab_3_database;

import lab_2_connexion.SessionFacade;

/**************************************************************
 * @CLASS_TITLE:	Query Contenu Library
 * 
 * @Description: 	C'est ici qu'on retrouve toutes les requetes
 * 					nécessaire à la recherche Contenu.On passe par 
 * 					la Session pour envoyer les requetes vers la
 * 					DB dans le but de respecter le patron de 
 * 					conception Façade.
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class QueryContentLibrary {

	/******************************************************
	 * @Titre:		Find Awards Categorie
	 ******************************************************/
	public static void findAwardsCategorie(String categorie){	
		String requete = "SELECT w.titre FROM films w " + 
						 "WHERE REGEXP_LIKE (w.award.extract('/award/categorie/text()').getStringVal(), '" + categorie + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Awards Nom Prix
	 ******************************************************/
	public static void findAwardsNomPrix(String nomPrix){
		String requete = "SELECT w.titre FROM films w " +
						 "WHERE REGEXP_LIKE (w.award.extract('/award/winner/text()').getStringVal(), '" + nomPrix + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Awards Annee Prix
	 ******************************************************/
	public static void findAwardsAnneePrix(int anneePrix){
		String requete = "SELECT w.titre FROM films w " +
						 "WHERE REGEXP_LIKE (w.award.extract('/award/annee/text()').getStringVal(), '" + anneePrix  + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Rang Classement
	 ******************************************************/
	public static void findRang(int rang){
		String requete = "SELECT w.titre FROM films w " +
						 "WHERE REGEXP_LIKE (w.classement.extract('/ranking/rang/text()').getStringVal(), '" + rang + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Rang et semaine Classement
	 ******************************************************/
	public static void findRangEtSemaine(int rang, int semaine){
		String requete = "SELECT w.titre FROM films w " + 
						 "WHERE REGEXP_LIKE (w.classement.extract('/ranking/rang/text()').getStringVal(), '" + rang + "', 'i') AND " + 
						 "REGEXP_LIKE (w.classement.extract('/ranking/semaine/text()').getStringVal(), '" + semaine + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Mois Classement
	 ******************************************************/
	public static void findMois(int mois){
		String requete = "SELECT w.titre FROM films w " +
						 "WHERE REGEXP_LIKE (w.classement.extract('/ranking/mois/text()').getStringVal(), '" + mois + "', 'i')";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find Couleur Dominante with range (Poster)
	 ******************************************************/
	public static void findCouleurDominante(int red, int green, int blue, int range){	
		String requete = "SELECT w.titre FROM films w WHERE " +
						"(w.DESCRIPTION.extract('/color/red/text()').getStringVal() " +
						"BETWEEN (" + red + "-" + range + ") AND (" + red + "+" + range + ")) " +  
						"AND " +
						"(w.DESCRIPTION.extract('/color/green/text()').getStringVal() " + 
						"BETWEEN (" + green + "-" + range + ") AND (" + green + "+" + range + ")) " +
						"AND " +
						"(w.DESCRIPTION.extract('/color/blue/text()').getStringVal() " + 
						"BETWEEN (" + blue + "-" + range + ") AND (" + blue + "+" + range + "))";
		SessionFacade.getSession().submitQuery(requete);
	}
	
	/******************************************************
	 * @Titre:		Find XMLType CLASSEMENT (ranking)
	 ******************************************************/
	public static void findClassementXMLType(String filmID){	
		SessionFacade.getSession().submitQueryXMLType("SELECT CLASSEMENT FROM FILMS WHERE IDFILM = '" + filmID + "'");
	}
	
	/******************************************************
	 * @Titre:		Find XMLType AWARD
	 ******************************************************/
	public static void findAwardXMLType(String filmID){	
		SessionFacade.getSession().submitQueryXMLType("SELECT AWARD FROM FILMS WHERE IDFILM = '" + filmID + "'");
	}
	
	/******************************************************
	 * @Titre:		Find XMLType DESCRIPTION (color)
	 ******************************************************/
	public static void findColorXMLType(String filmID){	
		SessionFacade.getSession().submitQueryXMLType("SELECT DESCRIPTION FROM FILMS WHERE IDFILM = '" + filmID + "'");
	}
}
