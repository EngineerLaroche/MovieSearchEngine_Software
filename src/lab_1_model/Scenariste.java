package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Scenariste
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Scenariste {

	/***************************
	 * Variables
	 ***************************/
	private int id = -1;
	private String scenariste   = null;
	
	/******************************************************
	 * @Titre:			Realisateur CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par défaut.
	 * 
	 ******************************************************/
	public Scenariste(){}
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getScenaristeId() 		{return id;}
	public String getNomScenariste()	{return scenariste;}
	
	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setScenaristeID(int _id)					{this.id = _id;}
	public void setNomScenariste(String _scenariste)	{this.scenariste = _scenariste;}
}
