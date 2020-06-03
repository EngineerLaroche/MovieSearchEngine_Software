package lab_1_model;

import java.util.ArrayList;

/**************************************************************
 * @CLASS_TITLE:	Film
 * 
 * @Description: 	
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class Film {

	/***************************
	 * Instances Classes 
	 ***************************/
	private Realisateur realisateur = null;
	
	/***************************
	 * Variables
	 ***************************/
	private int 
	idFilm = -1,
	annee = -1,
	duree = -1;
	
	private String
	titre 		= null,
	langue 		= null,
	resume 		= null,
	poster 		= null;

	/***************************
	* ArrayList String
	***************************/
	private ArrayList<String>
	pays 		= null,
	genres 		= null,
	scenaristes = null,
	annonces 	= null;
	
	private ArrayList<Role> roles = null;
	
	
	/******************************************************
	 * @Titre:			Film CONSTRUCTOR
	 * 
	 * @Resumer:		Initialise les ArrayList
	 * 
	 ******************************************************/
	public Film(){
		pays 		= new ArrayList<>();
		genres 		= new ArrayList<>();
		scenaristes = new ArrayList<>();
		annonces 	= new ArrayList<>();
		roles 		= new ArrayList<>();
	}
	
	/******************************************************
	 * @Titre:			Accesseurs
	 * 
	 * @Resumer:		Accès aux variables d'instance
	 * 
	 ******************************************************/
	public int getIdFilm()						{return idFilm;}
	public int getAnnee()						{return annee;}
	public int getDuree()						{return duree;}
	public String getTitre()					{return titre;}
	public String getLangue()					{return langue;}
	public String getResume()					{return resume;}
	public String getPoster()					{return poster;}
	public Realisateur getRealisateur()			{return realisateur;}
	
	public ArrayList<String> getPays()			{return pays;}
	public ArrayList<String> getGenres()		{return genres;} 
	public ArrayList<String> getScenaristes()	{return scenaristes;} 	
	public ArrayList<String> getAnnonces()		{return annonces;}	
	public ArrayList<Role> getRoles()			{return roles;} 

	/******************************************************
	 * @Titre:			Mutateurs
	 * 
	 * @Resumer:		Modifie les variables d'instance
	 * 
	 ******************************************************/
	public void setIdFilm(int _idFilm)						{this.idFilm = _idFilm;}
	public void setAnnee(int _annee)						{this.annee = _annee;}
	public void setDuree(int _duree)						{this.duree = _duree;}
	public void setTitre(String _titre)						{this.titre = _titre;}
	public void setLangue(String _langue)					{this.langue = _langue;}
	public void setResume(String _resume)					{this.resume = _resume;}
	public void setPoster(String _poster)					{this.poster = _poster;}
	public void setRealisateur(Realisateur _realisateur)	{this.realisateur = _realisateur;}

	public void setPays(ArrayList<String> _pays)		{this.pays = _pays;}
	public void setGenres(ArrayList<String> _genres)	{this.genres = _genres;} 
	public void setRoles(ArrayList<Role> _roles)		{this.roles = _roles;} 
	public void setScenaristes(ArrayList<String> _scen)	{this.scenaristes = _scen;}
	public void setAnnonces(ArrayList<String> _annonces){this.annonces = _annonces;}
	
	/******************************************************
	 * @Titre:			Add
	 * 
	 * @Resumer:		Ajouts d'elements dans les ArrayList<>
	 * 
	 ******************************************************/
	public void addPays(String _pays)				{pays.add(_pays);}
	public void addGenre(String _genres)			{genres.add(_genres);} 
	public void addRole(Role _role)					{roles.add(_role);} 
	public void addScenariste(String _scenaristes)	{scenaristes.add(_scenaristes);}
	public void addAnnonce(String _annonces)		{annonces.add(_annonces);}
}
