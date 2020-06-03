package lab_1_gestion;

import lab_1_model.Film;
import lab_1_model.Role;

/**************************************************************
 * @CLASS_TITLE:	GestionRole
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionRoles {

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA 		  = "n/a",
	MSG_ACTEUR 	= "	Acteur : 	",
	MSG_PERSO = "	Personnage :	";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Role role = null;
	private Film film = null;


	/******************************************************
	 * @Titre:			GestionInfoCredit CONSTRUCTOR
	 * 
	 * @Resumer:		Initialise une nouvelle Info-Credit
	 * 
	 ******************************************************/
	public GestionRoles(Film _film){ 
		this.film = _film;
		role = new Role(); 
	}

	/******************************************************
	 * @Titre:			Insert Content XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void insertContent(int idActeur, String nomActeur, String personnage){

		//ID Acteur
		if(idActeur >= 0){
			role.setIdActeur(idActeur);		
		}else{role.setIdActeur(-1);}

		//Nom Acteur
		if(nomActeur != null){
			
			// Si possede un " ' ", on le retire pour la BD
			if(nomActeur.contains("'")) nomActeur = nomActeur.replace("'", " ");
						
			role.setActeur(nomActeur);
			System.out.println(MSG_ACTEUR + nomActeur + " (ID: " + role.getIdActeur() +")");
		}else{
			role.setActeur(NA);
			System.out.println(MSG_ACTEUR + NA);
		}

		//Personnage
		if(personnage != null){
			
			// Si possede un " ' ", on le retire pour la BD
			if(personnage.contains("'")) personnage = personnage.replace("'", " ");
						
			role.setPersonnage(personnage);
			System.out.println(MSG_PERSO + personnage + "\n");
		}else{
			role.setPersonnage(NA);
			System.out.println(MSG_PERSO + NA + "\n");
		}

		film.addRole(role);
	}
}
