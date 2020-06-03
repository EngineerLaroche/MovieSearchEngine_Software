package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Role
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Role {

	/***************************
	 * Variables
	 ***************************/
	private int idActeur 		= -1;
	private String acteur 		= null;
	private String personnage  	= null;
	
	/******************************************************
	 * @Titre:			Role CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par défaut.
	 * 
	 ******************************************************/
	public Role(){}
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getIdActeur()		{return idActeur;}
	public String getActeur()		{return acteur;}
	public String getPersonnage()	{return personnage;}
	
	
	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setIdActeur(int _idActeur)			{this.idActeur = _idActeur;}
	public void setActeur(String _acteur)			{this.acteur = _acteur;}
	public void setPersonnage(String _personnage)	{this.personnage = _personnage;}
}
