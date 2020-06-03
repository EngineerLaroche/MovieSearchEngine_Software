package lab_3_database;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

/**************************************************************
 * @CLASS_TITLE:	Lecture Contenu XML Type Film
 * 
 * @Description: 	Permet de récupérer les valeurs de la DB 
 * 					situées dans un champ XMLType. À partir de
 * 					la table FILM on récupère les valeurs de la
 * 					colonne CLASSEMENT, AWARD et DESCRIPTION.
 *
 * @Cours:			GTI660-01
 * @Session:		H-2019
 * 
 **************************************************************/
public class ReadXMLType {

	/******************************
	 * Singleton
	 ******************************/
	public static ReadXMLType read = new ReadXMLType();

	/******************************
	 * Variables
	 ******************************/
	private String 
	anneeR = null,
	mois = null,
	semaine = null,
	rang = null,
	winner = null,
	anneeA = null,
	categorie = null,
	red = null,
	green = null,
	blue = null;	


	/******************************************************
	 * @Titre:			Lecture Contenu Film Ranking
	 * 
	 * @Resumer:		On utilise cette méthode pour
	 * 					récupérer les valeurs de la DB
	 * 					situées dans un champs XMLType. 
	 * 					Ici, on récupère CLASSEMENT. 
	 * 
	 * @Source:			https://www.rgagnon.com/javadetails/java-0573.html
	 * 
	 ******************************************************/
	public void readRanking(String xml){   
		if(xml != null){
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xml));

				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("ranking");

				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					anneeR 	= getValue((Element) element.getElementsByTagName("annee").item(0));
					mois 	= getValue((Element) element.getElementsByTagName("mois").item(0));
					semaine = getValue((Element) element.getElementsByTagName("semaine").item(0));
					rang 	= getValue((Element) element.getElementsByTagName("rang").item(0));
				}
			}catch (Exception e) {e.printStackTrace();}
		}else anneeR = mois = semaine = rang = "n/a";
	}  

	/******************************************************
	 * @Titre:			Lecture Contenu Film Award
	 * 
	 * @Resumer:		On utilise cette méthode pour
	 * 					récupérer les valeurs de la DB
	 * 					situées dans un champs XMLType. 
	 * 					Ici, on récupère AWARD. 
	 * 
	 * @Source:			https://www.rgagnon.com/javadetails/java-0573.html
	 * 
	 ******************************************************/
	public void readAward(String xml){      
		if(xml != null){
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xml));

				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("award");

				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					winner 	  = getValue((Element) element.getElementsByTagName("winner").item(0));
					anneeA 	  = getValue((Element) element.getElementsByTagName("annee").item(0));
					categorie = getValue((Element) element.getElementsByTagName("categorie").item(0));
				}
			}catch (Exception e) {e.printStackTrace();}
		}else winner = anneeA = categorie = "n/a";
	} 

	/******************************************************
	 * @Titre:			Lecture Contenu Film Color
	 * 
	 * @Resumer:		On utilise cette méthode pour
	 * 					récupérer les valeurs de la DB
	 * 					situées dans un champs XMLType. 
	 * 					Ici, on récupère DESCRIPTION.
	 * 
	 *  @Source:		https://www.rgagnon.com/javadetails/java-0573.html
	 * 
	 ******************************************************/
	public void readColor(String xml){      
		if(xml != null){
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xml));

				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("color");

				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					red   = getValue((Element) element.getElementsByTagName("red").item(0));
					green = getValue((Element) element.getElementsByTagName("green").item(0));
					blue  = getValue((Element) element.getElementsByTagName("blue").item(0));
				}
			}catch (Exception e) {e.printStackTrace();}
		}else red = green = blue = "n/a";
	}  

	/******************************************************
	 * @Titre:			Get Value Element
	 * 
	 * @Resumer:		Recupere la valeur de l'Element
	 * 					du Noeud. Si on ne trouve rien,
	 * 					on renvoie le String n/a .
	 * 
	 ******************************************************/
	private String getValue(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "n/a";
	}

	/******************************************************
	 * 					Accesseurs
	 ******************************************************/
	public String getAnneeR()	{ return anneeR; }
	public String getMois()		{ return mois; 	}
	public String getSemaine()	{ return semaine; }
	public String getRang()		{ return rang; }

	public String getWinner()	{ return winner; }
	public String getAnneeA()	{ return anneeA; }
	public String getCateg()	{ return categorie; }

	public String getRed()		{ return red; }
	public String getGreen()	{ return green; }
	public String getBlue()		{ return blue; }

	/******************************************************
	 * 					Singleton
	 ******************************************************/
	public static ReadXMLType readXML(){ return read; }
}
