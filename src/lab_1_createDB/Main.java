package lab_1_createDB;

/**************************************************************
 * @CLASS_TITLE:		Main 
 * 
 * @Description: 	S'occupe simplement de démarrer l'application.  
 * 
 * @Cours:			GTI660-01
 * @Session:			H-2019	
 * 
 **************************************************************/
public class Main {

	/******************************
	 * Constantes - Message
	 ******************************/

	private static final String
	MSG_BIENVENU = 	"\n******************************************" +
					"\n*		BIENVENU !		 *" +
					"\n* Lecture de fichiers XML & insertion BD *" + 
					"\n* 					 *" +
					"\n******************************************";


	/******************************************************
	 * @Titre:			MAIN
	 * 
	 * @Resumer:		Demarre un Menu Principal qu'on
	 * 					retrouve dans la Console pour la
	 * 					selection d'options. Envoi en 
	 * 					parametre les trois fichiers XML.
	 * 
	 * @Fichiers_XML:	0 --> ${workspace_loc:gti660-Hiver2019/xmlFiles/films.xml}
	 *					1 --> ${workspace_loc:gti660-Hiver2019/xmlFiles/clients.xml}
	 *					2 --> ${workspace_loc:gti660-Hiver2019/xmlFiles/personnes.xml}
	 * 
	 ******************************************************/
	public static void main(String[] args) {

		System.out.println(MSG_BIENVENU);

		//***************************************************************************************
		//* Pour le lab 2 et 3, il faut utiliser la classe MainUI.java dans le package ui_lab_3 *
		//***************************************************************************************
		
		//new OptionsUtilisateur(args[0],args[1],args[2]);
	}
}
