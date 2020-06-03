package lab_1_createDB;

import java.sql.SQLException;
import java.util.Scanner;

/**************************************************************
 * @CLASS_TITLE:	Options Utilisateur
 * 
 * @Description: 	Donne � l'utilisateur des options de 
 * 					lectures des fichier XML, des entr�es
 * 					de requ�tes faites manuellement vers la DB
 * 					et l'insertion de donn�es vers la DB.
 * 					
 * 
 * @Quantit�s:		- Films: 631
 * 					- Clients: 21550
 * 					- Personnes: 4549
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class OptionsUtilisateur {

	/******************************
	 * Constantes - Message
	 ******************************/
	private static final String
	MSG_MENU = 	"\n******************************************" +
				"\n*		MENU PRINCIPAL		 *" +
				"\n******************************************",
	MSG_LECTURE = "\n******************************************" +
				"\n*	     LECTURE XML LOCAL	         *" +
				"\n******************************************",
	MSG_INSERT = "\n******************************************" +
				"\n*	   INSERTION XML --> BD		 *" +
				"\n******************************************",
	MSG_QUITTER = "\n******************************************" +
				"\n*		AU REVOIR !		 *" +
				"\n*  Fermeture du logiciel de lecture XML  *" + 
				"\n* 					 *" +
				"\n******************************************";
	
	/******************************
	 * Variable - Repertoire XML
	 ******************************/
	private String 
	fichierFilm = null,
	fichierClient = null,
	fichierPersonne = null;

	/******************************************************
	 * @throws SQLException 
	 * @Titre:			Insertion DB CONSTRUCTOR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public OptionsUtilisateur() throws SQLException{
		choixMenu();
	}
	
	/******************************************************
	 * @Titre:			Insertion DB CONSTRUCTOR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public OptionsUtilisateur(String filmXML, String clientXML, String personneXML) {
		this.fichierFilm = filmXML;
		this.fichierClient = clientXML;
		this.fichierPersonne = personneXML;
		choixMenu();
	}

	/******************************************************
	 * @Titre:			Choix Utilisateur (Menu Principal)
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public void choixMenu(){

		System.out.println(MSG_MENU);
		System.out.println("\nVeuillez choisir une des trois options suivantes:");
		System.out.println("\n a - LECTURE	local d'un fichier XML\n" +
							" b - REQUETE	manuelles vers la BD\n" +
							" c - INSERTION	automatique des donn�es dans la BD\n\n" +
							" q - (Quitter)");
		
		System.out.print("\n>> Option (a,b,c,q): ");

		//Recupere l'option entr�e par l'utilisateur dans le terminal
		Scanner sc = new Scanner(System.in);  
		String reponse = sc.nextLine();

		//Garde en m�moire l'option choisi et pointe vers le bon r�pertoire (fichier XML)
		if(reponse.equals("a")){ 	 
			choixLectureXML();
		}
		else if(reponse.equals("b")){ 
			new ManualQuery();
		}
		else if(reponse.equals("c")){ 
			choixInsertionBD();
		}
		else if(reponse.equals("q")){
			System.out.println(MSG_QUITTER);
			System.exit(0);
		}
		else{ System.out.println("\nVeuillez entrer 'a' ou 'b' ou 'c' ou 'q'");
			  choixMenu();
		}        	   
	}

	/******************************************************
	 * @Titre:			Choix lecture de Fichiers XML
	 * 
	 * @Resumer:		Demande � l'utilisateur de choisir
	 * 					entre la lecture d'un fichier XML
	 * 					de type film, client ou personne.
	 * 					Lecture XML local seulement.			
	 * 
	 ******************************************************/
	public void choixLectureXML(){

		System.out.println(MSG_LECTURE);
		System.out.println("\nVeuillez choisir un des trois fichiers suivants:");
		System.out.println("\n a - Films\n b - Clients\n c - Personnes\n\n m - (Menu)");
		System.out.print("\n>> Option (a,b,c,m): ");

		//Recupere l'option entr�e par l'utilisateur dans le terminal
		Scanner sc = new Scanner(System.in);  
		String reponse = sc.nextLine();
		
		LectureXML lecture = new LectureXML();

		//Garde en m�moire l'option choisi et pointe vers le bon r�pertoire (fichier XML)
		if(reponse.equals("a"))		 
			lecture.lectureFilms(fichierFilm, false);
		else if(reponse.equals("b")) 
			lecture.lectureClients(fichierClient, false);
		else if(reponse.equals("c")) 
			lecture.lecturePersonnes(fichierPersonne, false);
		else if(reponse.equals("m")) 
			choixMenu();
		else{System.out.println("\nVeuillez entrer 'a' ou 'b' ou 'c' ou 'm'");
			 choixLectureXML();
		}
		redemarrerLecture();
	}
	
	/******************************************************
	 * @Titre:			Choix Insertion XML dans la BD
	 * 
	 * @Resumer:		Demande � l'utilisateur de choisir
	 * 					entre la lecture d'un fichier XML
	 * 					de type film, client ou personne.
	 * 					Lecture XML et insertion dans la BD.
	 * 
	 ******************************************************/
	public void choixInsertionBD(){

		System.out.println(MSG_INSERT);
		System.out.println("\nVeuillez choisir un des trois fichiers suivants:");
		System.out.println("\n a - Films\n b - Clients\n c - Personnes\n d - All in One (3)\n\n m - (Menu)");
		System.out.print("\n>> Option (a,b,c,d,m): ");

		//Recupere l'option entr�e par l'utilisateur dans le terminal
		Scanner sc = new Scanner(System.in);  
		String reponse = sc.nextLine();
		
		LectureXML lecture = new LectureXML();

		//Garde en m�moire l'option choisi et pointe vers le bon r�pertoire (fichier XML)
		if(reponse.equals("a"))		 
			lecture.lectureFilms(fichierFilm, true);
		else if(reponse.equals("b")) 
			lecture.lectureClients(fichierClient, true);
		else if(reponse.equals("c")) 
			lecture.lecturePersonnes(fichierPersonne, true);
		else if(reponse.equals("d")){
			lecture.lecturePersonnes(fichierPersonne, true);
			lecture.lectureClients(fichierClient, true);
			lecture.lectureFilms(fichierFilm, true);}
		else if(reponse.equals("m")) 
			choixMenu();
		else{System.out.println("\nVeuillez entrer 'a' ou 'b' ou 'c' ou 'd' ou 'm'");
			choixInsertionBD();
		}     
		redemarrerLecture();
	}

	/******************************************************
	 * @throws SQLException 
	 * @Titre:			Redemarrer Lecture
	 * 
	 * @Resumer:		Une fois la lecture compl�te d'un 
	 * 					fichier XML. On demande � l'utilisateur
	 * 					s'il souhaite proc�der � une autre 
	 * 					lecture de fichier. 
	 * 
	 ******************************************************/
	private void redemarrerLecture(){

		String reponse = null;

		System.out.println("\nVoulez-vous proc�der � une autre lecture de fichier XML ?");
		System.out.println("\n a - Oui, LECTURE local XML\n b - Oui, INSERTION donn�es BD\n\n m - (Menu)");
		System.out.print("\n>> R�ponse (a,b,m): ");

		//Recupere la r�ponse entr�e par l'utilisateur dans le terminal
		Scanner sc = new Scanner(System.in);  
		reponse = sc.nextLine();

		//Garde en m�moire l'option choisi et pointe vers la bonne action
		if(reponse.equals("a"))
			choixLectureXML();
		if(reponse.equals("b"))
			choixInsertionBD();
		else if(reponse.equals("m"))
			choixMenu();
		else{System.out.println("\nVeuillez entrer 'a' ou 'b' ou 'm'");
			redemarrerLecture();
		}        	
	}
}
