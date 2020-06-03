package lab_2_connexion;


/**************************************************************
 * @CLASS_TITLE:	RSQ User
 * 
 * @Description: 	Classe qui défini le Client qui utilise
 * 					le service de connexion à la DB.		  
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class RSQUser{
	
	/******************************
	 * Variable
	 ******************************/
	private String username = null; 
	private String password = null; 

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public RSQUser(String username, String password){
		this.username = username; 
		this.password = password; 
	}

	/******************************************************
	 * 					Accesseurs
	 ******************************************************/
	public String getPassword() { return password; }
	public String getUsername() { return username; }

	/******************************************************
	 * 					Mutateurs
	 ******************************************************/
	public void setPassword(String password) { 
		this.password = password; 
	}
	public void setUsername(String username) { 
		this.username = username; 
	}
}
