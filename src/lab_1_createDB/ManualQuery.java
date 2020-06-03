package lab_1_createDB;

import lab_2_connexion.Address;
import lab_2_connexion.Connect;
import lab_2_connexion.RSQUser;


/**************************************************************
 * @CLASS_TITLE:	Manual Query
 * 
 * @Description: 	  
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class ManualQuery {

	/******************************
	 * Instances Classes
	 ******************************/
	private RSQUser user = null;
	private Address address = null;

	/******************************
	 * Constantes - Connexion DB
	 ******************************/
	private static final String
	USER 		= "EQUIPE119",
	PASSW 		= "eVWNC13U",
	HOST 		= "gti660ora12c.logti.etsmtl.ca",
	PORT 		= "1521",
	DB_NAME 	= "GTI660";

	/******************************
	 * Constantes - Message
	 ******************************/
	private static final String
	EXIT 			= 	"exit",
	CLOSE_CONNEXION = 	"(RSQConnect) Fermeture de connexion.",
	ERROR_CONNEXION = 	"\n*** Problem with DB because : ",

	MSG_CONNEXION 	= 	"\n*******************************************" +
			"\n* Initialisation de la connexion à la DB  *" + 
			"\n*******************************************";


	/******************************************************
	 * @Titre:			Manual Query CONSTRUCTEUR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public ManualQuery(){ 
		initAuthentification();
		connectManualQuery();
	}

	/******************************************************
	 * @Titre:			Initialisation Authentication (lab-1)
	 * 
	 * @Resumer:		Si toutes les informations de
	 * 					l'authentification ne sont pas null,
	 * 					on initialise le user et l'adresse 
	 * 					de la DB. Sinon, on affiche un message
	 * 					d'erreur.
	 * 
	 ******************************************************/
	private void initAuthentification(){

		//On s'assure que l'information de connexion est complète
		if (USER != null && PASSW != null && HOST != null && PORT != null && DB_NAME != null) {
			
			user = new RSQUser(USER, PASSW);
			address = new Address(HOST, PORT, DB_NAME);
		} 
		else { afficher(); }
	}

	/******************************************************
	 * @Titre:			Connexion DB - Query manuel (lab-1)
	 * 
	 * @Resumer:		Connexion à la DB et permet 
	 * 					l'insertion de requetes faites
	 * 					manuellement par l'utilisateur.
	 * 
	 ******************************************************/
	public void connectManualQuery(){

		//On s'assure que l'information de connexion est valide
		if (user != null && address != null) {

			String inputLine = new String("");

			//Debut de la tentative de connexion a la DB
			try {

				System.err.println(MSG_CONNEXION);

				//Initialisation d'une connexion a la DB
				Connect connect = new Connect();
				connect.openConnection(user, address);

				//Si la connexion a la DB est ouverte
				if (connect.isOpen()) { 

					while ((inputLine.trim()).compareTo(EXIT) != 0) {                        
						System.out.print(">>");                    

						while (true) {                        
							char c = (char)System.in.read();

							if (c == '\n') {

								//Si l'utilisateur ecrit 'exit' dans la Console
								if ((inputLine.trim()).compareTo(EXIT) == 0) {
									break;
								}
								//Si on insere des donnees --> ex: ... VALUES ('12345', 'Bob');
								if ((inputLine.trim()).indexOf(';') != -1) {
									connect.send((inputLine.trim()).substring(0, inputLine.indexOf(';')));
									inputLine = "";
									break;
								}
							}											
							// store the cmdline
							inputLine += c;
						}
					}
					System.err.println(CLOSE_CONNEXION);
					connect.close();
					new OptionsUtilisateur();
				}
			}catch (Exception e) {System.err.println(ERROR_CONNEXION + e.getMessage());}
		} else { afficher(); }
	}

	/******************************************************
	 * @Titre:			afficher
	 * 
	 * @Resumer:		On appel cette fonction pour 
	 * 					aviser l'utilisateur
	 * 					d'une information manquante
	 * 					a la connexion de la DB.
	 * 
	 ******************************************************/
	public static void afficher(){

		System.err.println("\nRSQConnect\n========================================");
		System.err.println("Connect to RSQ Database and allow to send via the command line ");
		System.err.println("valid SQL statements.");
		System.err.println(" Usage: RSQConnect [user] [passwd] [host] [port] [dbname]");
		System.err.println("\n");
	}
}