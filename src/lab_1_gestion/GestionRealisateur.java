package lab_1_gestion;

import lab_1_model.Film;
import lab_1_model.Realisateur;

/**************************************************************
 * @CLASS_TITLE:	GestionRealisateur
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionRealisateur {

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA				= "n/a",
	MSG_REALISATEUR = "\nRealisateur :	";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Realisateur realisateur = null;
	private Film film = null;

	/******************************************************
	 * @Titre:			GestionRealisateur CONSTRUCTOR
	 * 
	 * @Resumer:		Initialise un nouveau Realisateur
	 * 
	 ******************************************************/
	public GestionRealisateur(Film _film){ 
		this.film = _film;
		realisateur = new Realisateur(); 
	}

	/******************************************************
	 * @Titre:			Insert Content XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void insertContent(int realisateurId, String realisateurNom){

		if(realisateurId >= 0){
			realisateur.setId(realisateurId);
		}else{realisateur.setId(-1);}

		if(realisateurNom != null && realisateurId >= 0){
			
			// Si possede un " ' ", on le retire pour la BD
			if(realisateurNom.contains("'")) realisateurNom = realisateurNom.replace("'", " ");
			
			realisateur.setRealisateur(realisateurNom);
			System.out.println(MSG_REALISATEUR + realisateurNom + " (ID: " + realisateur.getId() +")" );
		}else{
			realisateur.setRealisateur(NA);
			System.out.println(MSG_REALISATEUR + NA);
		}

		film.setRealisateur(realisateur);
	}
}
