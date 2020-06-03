package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Naissance
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Naissance{
	
	/***************************
	 * Variables
	 ***************************/
	private String 
	anniversaire = null,
	lieu 		 = null;
	
	
	/******************************************************
	 * @Titre:			Personne CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par defaut
	 * 
	 ******************************************************/
	public Naissance(){}
	
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public String getLieu()			{return lieu;}
	public String getAnniversaire()	{return anniversaire;}
	
	
	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setLieu(String _lieu)					{this.lieu = _lieu;}
	public void setAnniversaire(String _anniversaire)	{this.anniversaire = _anniversaire;}
}
