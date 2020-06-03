package lab_3_database;

import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import lab_2_connexion.SessionFacade;


/**************************************************************
 * @CLASS_TITLE:	Export Results to XML
 * 
 * @Description: 	Exportation des resultats Film dans une 
 * 					fichier de type XMl. Toute l'information
 * 					des films se retroouveront dans le fichier
 * 					en question.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class ExportResultsToXML {

	private DefaultListModel<String> listeFilms = null;

	/******************************
	 * Constantes - Message
	 ******************************/
	private static final String 
	REPERTOIRE_XML 	= 	"xmlExport/resultats.xml",	
	MSG_ERROR 		= 	"Exportation des résultats dans un fichier XML non réussi...\nCause: ",
	MSG_TERMINER 	= 	"\n******************************************" +
						"\n*	Exportation XML Réussi !	 *" +
						"\n******************************************";

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public ExportResultsToXML(DefaultListModel<String> _listeFilms){
		this.listeFilms = _listeFilms;
		buildFileXML();
	}

	/******************************************************
	 * Build File XML
	 * 
	 * @Resumer:	Construction de la base du fichier XML
	 * 
	 * @Source:		https://crunchify.com/java-simple-way-to-write-xml-dom-file-in-java/
	 * 
	 ******************************************************/
	private void buildFileXML(){

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			Element element = doc.createElementNS(null,"films");
			doc.appendChild(element);

			//Recupere l'information complete des films
			setInfoFilm(element, doc);

			// Output de l'information dans un fichier XML
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.transform(new DOMSource(doc), new StreamResult(new File(REPERTOIRE_XML)));

			System.out.println(MSG_TERMINER);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, MSG_ERROR + e.getMessage(), "Erreur Exportation XML" , JOptionPane.ERROR_MESSAGE);
		}
	}

	/******************************************************
	 * Get Film
	 * 
	 * @Resumer:	Recupere les info des Films pour
	 * 				initialiser les variables.
	 * 
	 ******************************************************/
	private void setInfoFilm(Element element, Document doc){
		
		for (int i = 0; i < listeFilms.size(); i++) {			

			//Recupere ID Film
			QueryFilmLibrary.findFilmID(listeFilms.get(i));
			String filmID = SessionFacade.getSession().getResultats().get(0);

			//Recupere Info Film (Table FILMS)
			QueryFilmLibrary.findInfoFilm(filmID);
			String titre = SessionFacade.getSession().getResultats().get(0);
			String annee = SessionFacade.getSession().getResultats().get(1);
			String langue = SessionFacade.getSession().getResultats().get(2);
			String duree = SessionFacade.getSession().getResultats().get(3);
			String resume = SessionFacade.getSession().getResultats().get(4);
			String poster = SessionFacade.getSession().getResultats().get(5);

			//Recupere les Pays (Table PAYS)
			QueryFilmLibrary.findPays(filmID);
			ArrayList<String> pays = SessionFacade.getSession().getResultats();

			//Recupere les Genres (Table GENRES)
			QueryFilmLibrary.findGenresFilm(filmID);
			ArrayList<String> genres = SessionFacade.getSession().getResultats();

			//Recupere Realisateur (Table REALISATEURS)
			QueryPersonneLibrary.findRealisateurID(filmID);
			String realisateurID = SessionFacade.getSession().getResultats().get(0);
			QueryPersonneLibrary.findNom(realisateurID);
			String realisateurNom = SessionFacade.getSession().getResultats().get(0);

			//Recupere Scenariste (Table SCENARISTES)
			QueryPersonneLibrary.findScenaristeID(filmID);
			ArrayList<String> scenaristesNom = new ArrayList<>();
			ArrayList<String> scenaristesID = SessionFacade.getSession().getResultats();

			for (int j = 0; j < scenaristesID.size(); j++) {
				QueryPersonneLibrary.findNom(scenaristesID.get(j));
				scenaristesNom.add(SessionFacade.getSession().getResultats().get(0));
			}

			//Recupere les Roles (Table ROLES)
			QueryPersonneLibrary.findActeurID(filmID);
			ArrayList<String> acteursID = SessionFacade.getSession().getResultats();	

			QueryPersonneLibrary.findPersonnage(filmID);
			ArrayList<String> personnages = SessionFacade.getSession().getResultats();			
			ArrayList<String> acteursNom = new ArrayList<>();

			for (int j = 0; j < acteursID.size(); j++) {
				QueryPersonneLibrary.findNom(acteursID.get(j));
				acteursNom.add(SessionFacade.getSession().getResultats().get(0));
			}	

			//Recupere les Annonces (Table ANNONCES)
			QueryFilmLibrary.findAnnonces(filmID);
			ArrayList<String> annonces = SessionFacade.getSession().getResultats();

			//append child elements to root element
			element.appendChild(ajouterFilm(doc, filmID, titre, annee, langue, duree, resume, poster,
					pays, genres, realisateurID, realisateurNom, scenaristesNom, acteursID,
					personnages, acteursNom, annonces));
		}	
	}

	/******************************************************
	 * Ajouter Film
	 * 
	 * @Resumer:	Liaison des variables à la strcuture
	 * 				souhaité du fichier XML.
	 * 
	 ******************************************************/
	private Node ajouterFilm(Document doc, String filmID, String titre, String annee, 
			String langue, String duree, String resume, String poster, ArrayList<String> pays,
			ArrayList<String> genres, String realisateurID, String realisateurNom, 
			ArrayList<String> scenaristesNom, ArrayList<String> acteursID, ArrayList<String> personnages,
			ArrayList<String> acteursNom, ArrayList<String> annonces) {

		Element film = doc.createElement("film");

		//Langue, Titre et Annee
		film.setAttribute("id", filmID);
		film.appendChild(getFilmElements(doc, film, "titre", titre));
		film.appendChild(getFilmElements(doc, film, "annee", annee));

		//Pays
		for (int i = 0; i < pays.size(); i++) 
			film.appendChild(getFilmElements(doc, film, "pays", pays.get(i)));

		//Langue, Duree et Resume
		film.appendChild(getFilmElements(doc, film, "langue", langue));
		film.appendChild(getFilmElements(doc, film, "duree", duree));
		film.appendChild(getFilmElements(doc, film, "resume", resume));

		//Genres
		for (int i = 0; i < genres.size(); i++) 
			film.appendChild(getFilmElements(doc, film, "genre", genres.get(i)));

		//Realisateur et son ID
		Element realisateur = doc.createElement("realisateur");
		realisateur.setAttribute("id", realisateurID);
		film.appendChild(getFilmElements(doc, realisateur, realisateurNom));

		//Scenaristes
		for (int i = 0; i < scenaristesNom.size(); i++) 
			film.appendChild(getFilmElements(doc, film, "scenariste", scenaristesNom.get(i)));

		//Roles (ID Personne, nom Acteur et Personnage)
		for (int i = 0; i < acteursID.size(); i++) {
			Element role = doc.createElement("role");
			Element acteur = doc.createElement("acteur");
			acteur.setAttribute("id", acteursID.get(i));
			role.appendChild(getFilmElements(doc, acteur, acteursNom.get(i)));
			role.appendChild(getFilmElements(doc, role, "personnage", personnages.get(i)));
			film.appendChild(role);
		}

		//Poster
		film.appendChild(getFilmElements(doc, film, "poster", poster));

		//Annonces
		for (int i = 0; i < annonces.size(); i++) 
			film.appendChild(getFilmElements(doc, film, "annonce", annonces.get(i)));

		return film;
	}

	/******************************************************
	 * Gest Film Elements
	 * 
	 * @Resumer:	Méthode utilitaire pour créer un 
	 * 				noeud de texte.
	 * 
	 ******************************************************/
	private Node getFilmElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/******************************************************
	 * Gest Film Elements
	 * 
	 * @Resumer:	Méthode utilitaire pour créer un 
	 * 				noeud de texte. Pour créer des sous-
	 * 				groupes avec attribut.
	 * 
	 ******************************************************/
	private Node getFilmElements(Document doc, Element element, String value) {
		element.appendChild(doc.createTextNode(value));
		return element;
	}
}
