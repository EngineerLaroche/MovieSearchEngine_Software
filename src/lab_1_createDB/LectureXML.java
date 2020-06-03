package lab_1_createDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**************************************************************
 * @CLASS_TITLE:	Lecture XML
 * 
 * @Description: 	Récupère les fichiers XMl et procède à
 * 					la lecture et retraits des données.
 *
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class LectureXML {   

	/******************************
	 * Classe
	 ******************************/
	private InsertionDB validation = null;

	
	/**************************************************************
	 * @CLASS_TITLE:	Role
	 * 
	 * @Description: 	Réuni l'information d'un rôle pour un film
	 * 					en particulier. Le rôle c'est en d'autres 
	 * 					mots, l'acteur qui jour un personnage.
	 *
	 * @Cours:			GTI660-01
	 * @Session:		H-2019	
	 * 
	 **************************************************************/
	public class Role {

		/******************************
		 * Variables 
		 ******************************/
		protected int id;
		protected String nom;
		protected String personnage;

		/******************************************************
		 * @Titre:			Role CONSTRUCTEUR
		 * 
		 * @Resumer:		Constructeur reçoit et initialise 
		 * 					l'information d'un rôle.
		 * 
		 ******************************************************/
		public Role(int _i, String _n, String _p) {
			this.id = _i;
			this.nom = _n;
			this.personnage = _p;
		}
	
		//Accesseurs (supporter pré-validation)
		public int getIdActeur(){return id;}
		public String getActeur(){return nom;}
		public String getPersonnage(){return personnage;}
	}

	/******************************************************
	 * @Titre:			Lecture XML CONSTRUCTEUR
	 * 
	 * @Resumer:		Constructeur qui initialise une
	 * 					nouvelle classe permettant
	 * 					l'insertion des données des
	 * 					fichiers XML dans la DB.
	 * 
	 ******************************************************/
	public LectureXML(){ validation = new InsertionDB(); }

	/******************************************************
	 * @Titre:			Lecture Personnes
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public void lecturePersonnes(String nomFichier, boolean toDB){      
		try {
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			InputStream is = new FileInputStream(nomFichier);
			parser.setInput(is, null);

			int 
			id = -1,
			eventType = parser.getEventType();

			String tag = null,
					tagType = null,
					nom = null,
					anniversaire = null,
					lieu = null,
					photo = null,
					bio = null;

			while (eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_TAG){
					tag = parser.getName();

					if (tag.equals("personne") && parser.getAttributeCount() == 1){
						id = Integer.parseInt(parser.getAttributeValue(0));
						tagType = parser.getName();
						validation.setTag(tagType);
					}
				} 
				else if (eventType == XmlPullParser.END_TAG){                              
					tag = null;

					if (parser.getName().equals("personne") && id >= 0){
						validation.insertionPersonne(toDB,id,nom,anniversaire,lieu,photo,bio);

						id = -1;
						nom = null;
						anniversaire = null;
						lieu = null;
						photo = null;
						bio = null;
					}
				}
				else if (eventType == XmlPullParser.TEXT && id >= 0){
					if (tag != null){    ;
						if (tag.equals("nom"))
							nom = parser.getText();
						else if (tag.equals("anniversaire"))
							anniversaire = parser.getText();
						else if (tag.equals("lieu"))
							lieu = parser.getText();
						else if (tag.equals("photo"))
							photo = parser.getText();
						else if (tag.equals("bio"))
							bio = parser.getText();
					}              
				}

				eventType = parser.next();            
			}
			if(eventType == XmlPullParser.END_DOCUMENT)
				validation.afficherQuantite();
				
		}catch (XmlPullParserException e) {System.out.println(e);
		}catch (IOException e) {System.out.println("IOException while parsing " + nomFichier);}	
	}   

	/******************************************************
	 * @Titre:			Lecture Films
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public void lectureFilms(String nomFichier, boolean toDB){
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			InputStream is = new FileInputStream(nomFichier);
			parser.setInput(is, null);

			int eventType = parser.getEventType();

			String tag = null,
					tagType = null,
					titre = null,
					langue = null,
					poster = null,
					roleNom = null,
					rolePersonnage = null,
					realisateurNom = null,
					resume = null;

			ArrayList<String> pays = new ArrayList<String>();
			ArrayList<String> genres = new ArrayList<String>();
			ArrayList<String> scenaristes = new ArrayList<String>();
			ArrayList<Role> roles = new ArrayList<Role>();         
			ArrayList<String> annonces = new ArrayList<String>();

			int id = -1,
					annee = -1,
					duree = -1,
					roleId = -1,
					realisateurId = -1;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					tag = parser.getName();

					if (tag.equals("film") && parser.getAttributeCount() == 1){
						id = Integer.parseInt(parser.getAttributeValue(0));
						tagType = parser.getName();
						validation.setTag(tagType);
					}
					else if (tag.equals("realisateur") && parser.getAttributeCount() == 1)
						realisateurId = Integer.parseInt(parser.getAttributeValue(0));
					else if (tag.equals("acteur") && parser.getAttributeCount() == 1)
						roleId = Integer.parseInt(parser.getAttributeValue(0));
				} 
				else if (eventType == XmlPullParser.END_TAG) {                              
					tag = null;

					if (parser.getName().equals("film") && id >= 0){
						validation.insertionFilm(toDB,id,titre,annee,pays,langue,
								duree,resume,genres,realisateurNom,
								realisateurId, scenaristes,
								roles,poster,annonces);

						id = -1;
						annee = -1;
						duree = -1;
						titre = null;                                 
						langue = null;                  
						poster = null;
						resume = null;
						realisateurNom = null;
						roleNom = null;
						rolePersonnage = null;
						realisateurId = -1;
						roleId = -1;

						genres.clear();
						scenaristes.clear();
						roles.clear();
						annonces.clear();  
						pays.clear();
					}
					if (parser.getName().equals("role") && roleId >= 0) {              
						roles.add(new Role(roleId, roleNom, rolePersonnage));
						roleId = -1;
						roleNom = null;
						rolePersonnage = null;
					}
				}
				else if (eventType == XmlPullParser.TEXT && id >= 0) {
					if (tag != null){                                    
						if (tag.equals("titre"))
							titre = parser.getText();          
						else if (tag.equals("annee"))
							annee = Integer.parseInt(parser.getText());
						else if (tag.equals("pays"))
							pays.add(parser.getText());
						else if (tag.equals("langue"))
							langue = parser.getText();
						else if (tag.equals("duree"))                 
							duree = Integer.parseInt(parser.getText());
						else if (tag.equals("resume"))                 
							resume = parser.getText();
						else if (tag.equals("genre"))
							genres.add(parser.getText());
						else if (tag.equals("realisateur"))
							realisateurNom = parser.getText();
						else if (tag.equals("scenariste"))
							scenaristes.add(parser.getText());
						else if (tag.equals("acteur"))
							roleNom = parser.getText();
						else if (tag.equals("personnage"))
							rolePersonnage = parser.getText();
						else if (tag.equals("poster"))
							poster = parser.getText();
						else if (tag.equals("annonce"))
							annonces.add(parser.getText());                  
					}          
				}          
				eventType = parser.next();            
			}
			if(eventType == XmlPullParser.END_DOCUMENT)
				validation.afficherQuantite();
		}
		catch (XmlPullParserException e) {System.out.println(e);}
		catch (IOException e) {System.out.println("IOException while parsing " + nomFichier);}
	}

	/******************************************************
	 * @Titre:			Lecture Clients
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public void lectureClients(String nomFichier, boolean toDB){
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			InputStream is = new FileInputStream(nomFichier);
			parser.setInput(is, null);
               
			int id = -1,
				expMois = -1,
				expAnnee = -1,
				eventType = parser.getEventType();
			
			String tag = null,
					tagType = null,
					nomFamille = null,
					prenom = null,
					courriel = null,
					tel = null,
					anniv = null,
					adresse = null,
					ville = null,
					province = null,
					codePostal = null,
					carte = null,
					noCarte = null,
					motDePasse = null,
					forfait = null;                                 

			while (eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_TAG){
					tag = parser.getName();

					if (tag.equals("client") && parser.getAttributeCount() == 1){
						id = Integer.parseInt(parser.getAttributeValue(0));
						tagType = parser.getName();
						validation.setTag(tagType);
					}
				} 
				else if (eventType == XmlPullParser.END_TAG){                              
					tag = null;

					if (parser.getName().equals("client") && id >= 0){
						validation.insertionClient(toDB,id,nomFamille,prenom,courriel,tel,
										anniv,adresse,ville,province,
										codePostal,carte,noCarte, 
										expMois,expAnnee,motDePasse,forfait);               

						nomFamille = null;
						prenom = null;
						courriel = null;               
						tel = null;
						anniv = null;
						adresse = null;
						ville = null;
						province = null;
						codePostal = null;
						carte = null;
						noCarte = null;
						motDePasse = null; 
						forfait = null;

						id = -1;
						expMois = -1;
						expAnnee = -1;
					}
				}
				else if (eventType == XmlPullParser.TEXT && id >= 0){         
					if (tag != null){                                    
						if (tag.equals("nom-famille"))
							nomFamille = parser.getText();
						else if (tag.equals("prenom"))
							prenom = parser.getText();
						else if (tag.equals("courriel"))
							courriel = parser.getText();
						else if (tag.equals("tel"))
							tel = parser.getText();
						else if (tag.equals("anniversaire"))
							anniv = parser.getText();
						else if (tag.equals("adresse"))
							adresse = parser.getText();
						else if (tag.equals("ville"))
							ville = parser.getText();
						else if (tag.equals("province"))
							province = parser.getText();
						else if (tag.equals("code-postal"))
							codePostal = parser.getText();
						else if (tag.equals("carte"))
							carte = parser.getText();
						else if (tag.equals("no"))
							noCarte = parser.getText();
						else if (tag.equals("exp-mois"))                 
							expMois = Integer.parseInt(parser.getText());
						else if (tag.equals("exp-annee"))                 
							expAnnee = Integer.parseInt(parser.getText());
						else if (tag.equals("mot-de-passe"))                 
							motDePasse = parser.getText();  
						else if (tag.equals("forfait"))                 
							forfait = parser.getText(); 
					}              
				}
				eventType = parser.next();            
			}
			if(eventType == XmlPullParser.END_DOCUMENT)
				validation.afficherQuantite();
		}
		catch (XmlPullParserException e) {System.out.println(e);}
		catch (IOException e) {System.out.println("IOException while parsing " + nomFichier);}	
	}   
}
