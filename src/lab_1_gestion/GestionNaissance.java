package lab_1_gestion;

import lab_1_model.Naissance;
import lab_1_model.Personne;

/**************************************************************
 * @CLASS_TITLE:	GestionNaissance
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionNaissance {

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA 			= "n/a",
	MSG_ANNI 	= "	Anniversaire :	",
	MSG_LIEU 	= "	Lieu :		";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Naissance naissance = null;
	private Personne personne = null;


	/******************************************************
	 * @Titre:			GestionNaissance CONSTRUCTOR
	 * 
	 * @Resumer:		Initialise une nouvelle naissance.
	 * 
	 ******************************************************/
	public GestionNaissance(Personne _personne){ 
		this.personne = _personne;
		naissance = new Naissance(); 
	}


	/******************************************************
	 * @Titre:			Insert Content XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void insertContent(String anniv, String lieu){

		//Anniversaire Naissance
		if(anniv != null){
			naissance.setAnniversaire(anniv);
			System.out.println(MSG_ANNI + anniv);
		}else{
			naissance.setAnniversaire("1900-01-01");
			System.out.println(MSG_ANNI + NA);
		}

		//Lieu Naissance
		if(lieu != null){

			// Si possede un " ' ", on le retire pour la BD
			if(lieu.contains("'")) lieu = lieu.replace("'", " ");
			naissance.setLieu(lieu);
			System.out.println(MSG_LIEU + lieu);
		}else{
			naissance.setLieu(NA);
			System.out.println(MSG_LIEU + NA);
		}
		personne.setNaissance(naissance);
	}
}
