package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Personne
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Personne {

	/***************************
	 * Classe Instanciee
	 ***************************/
	private Naissance naissance = null;
	
	/***************************
	 * Variables
	 ***************************/
	private int id = -1;
	
	private String
	nom 	= null,
	bio 	= null,
	photo	= null;

	/******************************************************
	 * @Titre:			Personne CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par defaut
	 * 
	 ******************************************************/
	public Personne(){}
	
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getId()		 		{return id;}
	public String getNom()			{return nom;}
	public String getBio()			{return bio;}	
	public String getPhoto()		{return photo;}
	public Naissance getNaissance()	{return naissance;}
	
	
	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setId(int _id)			{this.id = _id;}
	public void setNom(String _nom)		{this.nom = _nom;}
	public void setBio(String _bio)		{this.bio = _bio;}
	public void setPhoto(String _photo)	{this.photo = _photo;}
	public void setNaissance(Naissance _naissance) {this.naissance = _naissance;}
}
