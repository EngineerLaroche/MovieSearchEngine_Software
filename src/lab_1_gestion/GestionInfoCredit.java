package lab_1_gestion;

import lab_1_model.Client;
import lab_1_model.InfoCredit;

/**************************************************************
 * @CLASS_TITLE:	GestionInfoCredit
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionInfoCredit {

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA 				= "n/a",
	MSG_CARTE 		= "	Carte :		",
	MSG_NO 			= "	No :		",
	MSG_EXP_MOIS 	= "	Exp Mois :	",
	MSG_EXP_ANNEE 	= "	Exp Annee :	";

	/***************************
	 * Instances Classes 
	 ***************************/
	private Client client = null;
	private InfoCredit infoCredit = null;

	/******************************************************
	 * @Titre:			GestionInfoCredit CONSTRUCTOR
	 * 
	 * @Resumer:		Initialise une nouvelle Info-Credit
	 * 
	 ******************************************************/
	public GestionInfoCredit(Client _client){
		this.client = _client;
		infoCredit = new InfoCredit(); 
	}

	/******************************************************
	 * @Titre:			Insert Content XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void insertContent(String carte, String noCarte, int expMois, int expAnnee){

		//Carte InfoCredit
		if(carte != null){
			infoCredit.setCarte(carte);
			System.out.println(MSG_CARTE + carte);
		}else{
			infoCredit.setCarte(NA);
			System.out.println(MSG_CARTE + NA);
		}

		//No InfoCredit
		if(noCarte != null){
			infoCredit.setNo(noCarte);
			System.out.println(MSG_NO + noCarte);
		}else{
			infoCredit.setCarte(NA);
			System.out.println(MSG_NO + NA);
		}

		//ExpMois InfoCredit
		if(expMois >= 0){
			infoCredit.setExpMois(expMois);
			System.out.println(MSG_EXP_MOIS + expMois);
		}else{
			infoCredit.setExpMois(-1);
			System.out.println(MSG_EXP_MOIS + NA);
		}

		//ExpAnnee InfoCredit
		if(expAnnee >= 0){
			infoCredit.setExpAnnee(expAnnee);
			System.out.println(MSG_EXP_ANNEE + expAnnee + "\n");
		}else{
			infoCredit.setExpAnnee(-1);
			System.out.println(MSG_EXP_ANNEE + NA + "\n");
		}
		client.setInfoCredit(infoCredit);
	}
}
