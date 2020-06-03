package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	InfoCredit
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class InfoCredit {
	
	/***************************
	 * Variables
	 ***************************/
	private String
	carte 	= null,
	no 		= null;
	
	private int
	expMois  = -1,
	expAnnee = -1;
	
	
	/******************************************************
	 * @Titre:			InfoCredit CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par défaut.
	 * 
	 ******************************************************/
	public InfoCredit(){}
	
	/******************************************************
	 * @Titre:			Accesseurs 
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public String getCarte()	{return carte;}
	public String getNo()		{return no;}
	public int getExpMois()		{return expMois;}
	public int getExpAnnee()	{return expAnnee;}

	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setCarte(String _carte)		{this.carte 	= _carte;}
	public void setNo(String _no)			{this.no 		= _no;}
	public void setExpMois(int _expMois)	{this.expMois 	= _expMois;}
	public void setExpAnnee(int _expAnnee)	{this.expAnnee 	= _expAnnee;}
}
