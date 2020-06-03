package lab_2_connexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**************************************************************
 * @CLASS_TITLE:	Import Properties
 * 
 * @Description: 	Recupere les valeurs d'un fichier de 
 * 					type 'properties'.
 * 	
 * 					user = GTI660
 * 					compte1 = ...
 * 					compte2 = ...
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class ImportProperties {

	/******************************
	 * Constante
	 ******************************/
	private static final String 
	PROP_FILE = "./properties/connexion.properties",
	TITRE_LECTURE_PROP = "Erreur Lecture Propriétés";

	/******************************
	 * Variable
	 ******************************/
	private String
	serveur = null,
	port = null,
	sid = null,
	username = null,
	passw = null;

	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Resumer:
	 * 
	 ******************************************************/
	public ImportProperties(){ readProperties(); }

	/******************************************************
	 * Get Propreties
	 * 
	 * @Resumer:
	 * 
	 * @Source: 	https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
	 * 
	 ******************************************************/
	public void readProperties() {

		FileInputStream input = null;

		try {

			//Initialisation de la lecture du fichier properties
			Properties prop = new Properties();			
			input = new FileInputStream(PROP_FILE);
			prop.load(input); 

			//Récupère la valeur indiquée 
			serveur = prop.getProperty("serveur");
			port = prop.getProperty("port");
			sid = prop.getProperty("sid");
			username = prop.getProperty("username");
			passw = prop.getProperty("passw");

			//JOptionPane.showMessageDialog(null, "Fichier ' " + PROP_FILE + " ' non troué !", TITRE_FILE_ERROR, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {JOptionPane.showMessageDialog(null, "Erreur de Lecture: " + e.getMessage(), TITRE_LECTURE_PROP, JOptionPane.ERROR_MESSAGE);} 
		finally {
			try {input.close();} 
			catch (IOException e) {e.printStackTrace();}
		}
	}

	/******************************************************
	 * 				Accesseur Serveur
	 ******************************************************/
	public String getServeur(){ return serveur; }

	/******************************************************
	 * 				Accesseur Port
	 ******************************************************/
	public String getPort(){ return port; }

	/******************************************************
	 * 				Accesseur SID
	 ******************************************************/
	public String getSid(){ return sid; }

	/******************************************************
	 * 				Accesseur Username
	 ******************************************************/
	public String getUser(){ return username; }

	/******************************************************
	 * 				Accesseur Password
	 ******************************************************/
	public String getPassw(){ return passw; }
}
