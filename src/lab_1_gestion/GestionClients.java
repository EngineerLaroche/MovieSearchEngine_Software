package lab_1_gestion;

import java.util.ArrayList;

import lab_1_model.Client;

/**************************************************************
 * @CLASS_TITLE:	GestionClient
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class GestionClients {

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	NA				 = "n/a",
	MSG_ID 			 = "ID :		",
	MSG_PRENOM 		 = "Prenom :	",
	MSG_NOM_FAMILLE  = "Nom Famille :	",
	MSG_ANNIV 		 = "Anniversaire :	",

	MSG_TEL 		 = "Tel :		",
	MSG_COURRIEL 	 = "Courriel :	",

	MSG_ADRESSE 	 = "\nAdresse :	",
	MSG_VILLE 		 = "Ville :		",
	MSG_PROVINCE 	 = "Province :	",
	MSG_CODE_POSTAL  = "Code Postal :	",

	MSG_INFO_CREDIT  = "\nInfo-Credit:",
	MSG_MOT_PASSE 	 = "Password :	",
	MSG_FORFAIT 	 = "Forfait :	",

	SEPERATOR 	 	 = "\n============================================================\n";

	/***************************
	 * Instances Classes 
	 ***************************/
	//private StringBuffer sb = null;
	private Client client 	= null;
	private GestionInfoCredit gInfoCredit = null;

	/***************************
	 * ArrayList
	 ***************************/
	private ArrayList<Client> clients = null;

	/***************************
	 * Variable (qte)
	 ***************************/
	int nbClients = 0;


	/******************************************************
	 * @Titre:			GestionClient CONSTRUCTOR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public GestionClients(){clients = new ArrayList<>();}


	/******************************************************
	 * @Titre:			Afficher Clients XML
	 * 
	 * @Resumer:		Recupere le contenu et on initialise 
	 * 					les variables de la classe modèle.
	 * 
	 ******************************************************/
	public void afficherClients(int id, String nomFamille, String prenom,
			String courriel, String tel, String anniv,
			String adresse, String ville, String province,
			String codePostal, String carte, String noCarte,
			int expMois, int expAnnee, String motDePasse,
			String forfait){

		client = new Client();

		//ID Client
		if(id >= 0){
			client.setId(id);
			System.out.println(MSG_ID + id);
		}else{client.setId(-1);}

		//NomFamille Client
		if(nomFamille != null){
			client.setNomFamille(nomFamille);
			System.out.println(MSG_NOM_FAMILLE + nomFamille);
		}else{
			client.setNomFamille(NA);
			System.out.println(MSG_NOM_FAMILLE + NA);
		}

		//Prenom client
		if(prenom != null){
			client.setPrenom(prenom);
			System.out.println(MSG_PRENOM + prenom);
		}else{
			client.setPrenom(NA);
			System.out.println(MSG_PRENOM + NA);
		}

		//Courriel Client
		if(courriel != null){
			client.setCourriel(courriel);
			System.out.println(MSG_COURRIEL + courriel);
		}else{
			client.setCourriel(NA);
			System.out.println(MSG_COURRIEL + NA);
		}

		//Tel Client
		if(tel != null){
			client.setTel(tel);
			System.out.println(MSG_TEL + tel);
		}else{
			client.setTel(NA);
			System.out.println(MSG_TEL + NA);
		}

		//Anniversaire Client
		if(anniv != null){
			client.setAnniversaire(anniv);
			System.out.println(MSG_ANNIV + anniv);
		}else{
			client.setAnniversaire("1900-01-01");
			System.out.println(MSG_ANNIV + NA);
		}

		//Adresse Client
		if(adresse != null){

			// Si un nom possede un " ' ", on le retire pour la BD
			if(adresse.contains("'")) adresse = adresse.replace("'", " ");

			client.setAdresse(adresse);
			System.out.println(MSG_ADRESSE + adresse);
		}else{
			client.setAdresse(NA);
			System.out.println(MSG_ADRESSE + NA);
		}

		//Ville Client
		if(ville != null){

			// Si un nom possede un " ' ", on le retire pour la BD
			if(ville.contains("'")) ville = ville.replace("'", " ");

			client.setVille(ville);
			System.out.println(MSG_VILLE + ville);
		}else{
			client.setVille(NA);
			System.out.println(MSG_VILLE + NA);
		}

		//Province Client
		if(province != null){
			client.setProvince(province);
			System.out.println(MSG_PROVINCE + province);
		}else{
			client.setProvince(NA);
			System.out.println(MSG_PROVINCE + NA);
		}

		//CodePostal Client
		if(codePostal != null){
			client.setCodePostal(codePostal);
			System.out.println(MSG_CODE_POSTAL + codePostal);
		}else{
			client.setCodePostal(NA);
			System.out.println(MSG_CODE_POSTAL + NA);
		}

		//InfoCredit Client
		if(carte != null || noCarte != null || expMois >= 0 || expAnnee >= 0){
			System.out.println(MSG_INFO_CREDIT);
			gInfoCredit = new GestionInfoCredit(client);
			gInfoCredit.insertContent(carte, noCarte, expMois, expAnnee);
		}else{
			gInfoCredit = new GestionInfoCredit(client);
			gInfoCredit.insertContent(null, null, -1, -1);
		}
		
		//MotPasse Client
		if(motDePasse != null){
			client.setMotDePasse(motDePasse);
			System.out.println(MSG_MOT_PASSE + motDePasse);
		}else{client.setMotDePasse(NA);}

		//Forfait Client
		if(forfait != null){
			client.setForfait(forfait);
			System.out.println(MSG_FORFAIT + forfait);
		}else{client.setForfait(NA);}

		System.out.println(SEPERATOR);

		//Ajout du client dans la liste de clients du fichier XML
		clients.add(client);
		nbClients++;
	}

	/******************************************************
	 * @Titre:			Get Nombre Clients
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public int getNbClients(){return nbClients;}

	/******************************************************
	 * @Titre:			Get Liste Clients
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public ArrayList<Client> getListClients(){return clients;}
}
