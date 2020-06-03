package lab_1_gestion;

import java.util.ArrayList;

import lab_1_model.Personne;

/**************************************************************
 * @CLASS_TITLE:	GestionPersonne
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionPersonnes{

	/***************************
	 * Constantes
	 ***************************/
	private static final String
	NA			= "n/a",
	MSG_ID 		= "ID :	",
	MSG_NOM 	= "Nom :	",
	MSG_NAISS 	= "\nNaissance:",
	MSG_PHOTO 	= "Photo :	",
	MSG_BIO 	= "Bio :	",
	SEPERATOR 	= "\n============================================================\n";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Personne personne = null;
	private GestionNaissance gNaissance = null;

	/***************************
	 * ArrayList
	 ***************************/
	private ArrayList<Personne> personnes = null;

	/***************************
	 * Variable (qte)
	 ***************************/
	int nbPersonnes = 0;

	/******************************************************
	 * @Titre:			GestionPersonne CONSTRUCTOR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public GestionPersonnes(){personnes = new ArrayList<>();}


	/******************************************************
	 * @Titre:			Afficher Personnes XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void afficherPersonnes(int id, String nom, String anniv, String lieu, String photo, String bio){

		personne = new Personne();

		//ID Personne
		if(id >= 0){
			personne.setId(id);
			System.out.println(MSG_ID + id);
		}else{personne.setId(-1);}

		//Nom Personne
		if(nom != null){

			// Si un nom possede un " ' ", on le retire pour la BD
			if(nom.contains("'")) nom = nom.replace("'", " ");

			personne.setNom(nom);
			System.out.println(MSG_NOM + nom);
		}else{
			personne.setNom(NA);
			System.out.println(MSG_NOM + NA);
		}

		//Naissance Personne
		System.out.println(MSG_NAISS);
		gNaissance = new GestionNaissance(personne);
		gNaissance.insertContent(anniv,lieu);
		System.out.print("\n");

		//Photo Personne
		if(photo != null){
			personne.setPhoto(photo);
			System.out.println(MSG_PHOTO + photo);
		}else{
			personne.setPhoto(NA);
			System.out.println(MSG_PHOTO + NA);
		}

		//Bio Personne
		if (bio != null ){

			// Si possede un " ' ", on le retire pour la BD
			if(bio.contains("'")) bio = bio.replace("'", " ");
			
			personne.setBio(bio);
			System.out.println(MSG_BIO + bio);
		}else{
			personne.setBio(NA);
			System.out.println(MSG_BIO + NA);
		}

		System.out.println(SEPERATOR);

		//Ajout de la personne dans la liste des personnes du fichier XML
		personnes.add(personne);
		nbPersonnes++;
	}

	/******************************************************
	 * @Titre:			Get Nombre Personnes
	 * 
	 * @Resumer:		Accesseurs qui permet de récupérer
	 * 					la quantité de personnes contenu
	 * 					dans le fichier Personnes.XML .
	 * 
	 ******************************************************/
	public int getNbPersonnes(){return nbPersonnes;}

	/******************************************************
	 * @Titre:			Get Liste Personnes
	 * 
	 * @Resumer:		Accesseurs qui permet de récupérer
	 * 					la liste de toutes les personnes du
	 * 					fichier Personnes.XML .
	 * 
	 ******************************************************/
	public ArrayList<Personne> getListPersonnes(){return personnes;}
}
