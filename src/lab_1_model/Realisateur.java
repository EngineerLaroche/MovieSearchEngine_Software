package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Realisateur
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Realisateur {

	/***************************
	 * Variables
	 ***************************/
	private int id = -1;
	private String realisateur   = null;
	
	/******************************************************
	 * @Titre:			Realisateur CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par défaut.
	 * 
	 ******************************************************/
	public Realisateur(){}
	
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getId() 			 		{return id;}
	public String getNomRealisateur()	{return realisateur;}
	
	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setId(int _id)							{this.id = _id;}
	public void setRealisateur(String _realisateur)		{this.realisateur = _realisateur;}
}
