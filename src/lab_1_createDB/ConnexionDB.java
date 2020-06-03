package lab_1_createDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import lab_2_connexion.Address;
import lab_2_connexion.Connect;
import lab_2_connexion.RSQUser;


/**************************************************************
 * @CLASS_TITLE:	Connexion DB (Lab-1)
 * 
 * @Description: 	Initialisation de la connexion à la DB.
 * 					Supporte l'appel de requêtes manuelles
 * 					ou automatiques à la DB.  
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class ConnexionDB {

	/******************************
	 * Instances Classes
	 ******************************/
	private RSQUser user = null;
	private Address address = null;
	private Connection connection = null;
	private Statement stmt = null;

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
	MSG_CONNEXION 	= 	"\n*******************************************" +
						"\n* Initialisation de la connexion à la DB  *" + 
						"\n*******************************************",
	MSG_DECONNEXION = 	"\n*******************************************" +
						"\n*   Fermeture de la connexion de la DB    *" + 
						"\n*******************************************";


	/******************************************************
	 * @Titre:			Connection DB CONSTRUCTEUR
	 * 
	 * @Resumer:		Initialise les informations de
	 * 					l'authentification à la DB.
	 * 
	 ******************************************************/
	public ConnexionDB(){ initAuthentification();}

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
	 * @Titre:			Get Statement
	 * 
	 * @Resumer:		Creation de la connexion avec la DB
	 * 					et retourne le Statement. 
	 * 
	 ******************************************************/
	public Statement getStatement(){

		//On s'assure que l'information de connexion est valide
		if (user != null && address != null) {

			try{
				//Initialisation d'une connexion a la DB avec l'information d'authentification.
				connection = DriverManager.getConnection(address.toString(), user.getUsername(), user.getPassword());
				stmt = connection.createStatement();

				System.err.println(MSG_CONNEXION);
				
			}catch (Exception e) {JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur Connexion", JOptionPane.ERROR_MESSAGE);}
		}else { afficher(); }
		return stmt;
	}
	
	/******************************************************
	 * @Titre:			Get Connection
	 ******************************************************/
	public Connection getConnection(){
		return connection;
	}

	/******************************************************
	 * @Titre:			Close Connection
	 * 
	 * @Resumer:		Ferme la connexion à la DB.
	 * 
	 ******************************************************/
	public void closeConnexion(){
		if (connection != null){
			try {
				connection.close();
				System.err.println(MSG_DECONNEXION);} 
			catch (SQLException e) {e.printStackTrace();}
		}
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