package lab_1_model;

/**************************************************************
 * @CLASS_TITLE:	Client
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Client {

	/***************************
	 * Variables
	 ***************************/
	private InfoCredit infoCredit = null;

	/***************************
	 * Variables
	 ***************************/
	private int id = -1;

	private String
	prenom 			= null,
	nomFamille 		= null,
	anniversaire 	= null,

	tel 			= null,
	courriel 		= null,

	adresse 		= null,
	ville 			= null,
	province 		= null,
	codePostal 		= null,

	motDePasse 		= null,
	forfait 		= null;


	/******************************************************
	 * @Titre:			Client CONSTRUCTOR
	 * 
	 * @Resumer:		Créé par défaut.
	 * 
	 ******************************************************/
	public Client(){}

	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getId()				{return id;}
	public String getPrenom()		{return prenom;}
	public String getNomFamille()	{return nomFamille;}
	public String getAnniversaire()	{return anniversaire;}

	public String getTel()			{return tel;}
	public String getCourriel()		{return courriel;}

	public String getAdresse()		{return adresse;}
	public String getVille()		{return ville;}
	public String getProvince()		{return province;}
	public String getCodePostal()	{return codePostal;}

	public String getMotDePasse()	{return motDePasse;}
	public String getForfait()		{return forfait;}

	public InfoCredit getInfoCredit(){return infoCredit;}

	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setId(int _id)							{this.id 			= _id;}
	public void setPrenom(String _prenom)				{this.prenom 		= _prenom;}
	public void setNomFamille(String _nomFamille)		{this.nomFamille 	= _nomFamille;}
	public void setAnniversaire(String _anniversaire)	{this.anniversaire 	= _anniversaire;}

	public void setTel(String _tel)						{this.tel 			= _tel;}
	public void setCourriel(String _courriel)			{this.courriel 		= _courriel;}

	public void setAdresse(String _adresse)				{this.adresse 		= _adresse;}
	public void setVille(String _ville)					{this.ville 		= _ville;}
	public void setProvince(String _province)			{this.province 		= _province;}
	public void setCodePostal(String _codePostal)		{this.codePostal 	= _codePostal;}

	public void setMotDePasse(String _motDePasse)		{this.motDePasse 	= _motDePasse;}
	public void setForfait(String _forfait)				{this.forfait 		= _forfait;}

	public void setInfoCredit(InfoCredit _infoCredit)	{this.infoCredit = _infoCredit;}
}
