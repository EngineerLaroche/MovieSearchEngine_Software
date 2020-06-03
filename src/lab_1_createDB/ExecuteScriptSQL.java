package lab_1_createDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

/**************************************************************
 * @CLASS_TITLE:	Execution Script SQL
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class ExecuteScriptSQL {

	/******************************
	 * Constantes - Message
	 ******************************/
	private static final String
	MSG_TABLES 		= 	"*	 Nettoyage des tables de la DB	  *" + 
						"\n*******************************************\n",
	MSG_INSERTION 	= 	"\n*******************************************" +
						"\n*   Démarrage de l'insertion dans le DB   *" + 
						"\n*******************************************";


	/******************************************************
	 * @Titre:			Execute Script SQL CONSTRUCTEUR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public ExecuteScriptSQL(String fichierScript, Connection connect){
		executeScript(fichierScript, connect);
	}

	/******************************************************
	 * @Titre:			Execute Script SQL
	 * 
	 * @Resumer:		Permet d'executer un script SQL sur
	 * 					la connexion établie. Dans le cas
	 * 					de cette application, on execute
	 * 					des scripts SQL pour nettoyer la 
	 * 					DB pour chaque nouvelle insertion
	 * 					de données XML dans la DB.  
	 * 
	 * @Source:			https://codippa.com/how-to-execute-a-database-script-in-java/
	 * 
	 ******************************************************/
	public void executeScript(String fichierScript, Connection connect) {

		System.err.println(MSG_TABLES);
		Reader reader = null;

		try {
			// Créé un Objet ScriptRunner
			ScriptRunner scriptExecutor = new ScriptRunner(connect, false, false);
			reader = new BufferedReader(new FileReader(fichierScript));

			// Execute le script avec le lecteur de fichier en entrée
			scriptExecutor.runScript(reader);
		} 
		catch (Exception e) {e.printStackTrace();} 
		finally {
			if (reader != null){
				try {reader.close();} 
				catch (IOException e) {}
			}
		}
		System.err.println(MSG_INSERTION);
	}
}
