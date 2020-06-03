/*
 * @(#)RSQIPAddress.java 1.0 02/07/31
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */
package lab_2_connexion;


/**************************************************************
 * @CLASS_TITLE:	Adress
 * 
 * @Description: 	Class that connect to the database and keep 
 * 					a connection to it in order to send Query. 
 * 					This class is a little bit like a socket 
 * 					connection.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class Address {	

	/******************************
	 *Constantes Driver
	 ******************************/
	final String dbdrivertype = "jdbc"; 
	final String dbdrivername = "oracle";
	final String dbparam = "thin";
	
	/******************************
	 *Variables Connexion
	 ******************************/
	private String address = null;  
	private String port = null; 
	private String dbname = null; 

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public Address(String address, String port, String dbname) { 
		this.address = address;  
		this.port = port;  	
		this.dbname = dbname; 		
	}

	/******************************************************
	 * 					Accesseurs
	 ******************************************************/
	public String getAddress() 	{ return address; }
	public String getPort() 	{ return port; }
	public String getDBName() 	{ return dbname; }
	public String toString() { // jdbc:oracle:thin:@myhost:1521:orcl
		return (dbdrivertype + ":" + dbdrivername +  ":" + dbparam + ":@"
				+ address + ":" + port + ":" + dbname);
	} 
}