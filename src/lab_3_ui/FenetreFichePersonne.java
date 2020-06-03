package lab_3_ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import lab_2_connexion.SessionFacade;
import lab_3_database.QueryPersonneLibrary;
import lab_3_mediaPlayer.FenetreMediaPlayer;
import lab_3_tools.OutilsFenetre;


/**************************************************************
 * @CLASS_TITLE:	Fenetre Fiche Personne
 * 
 * @Description: 	Affiche l'information complete d'une Personne.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class FenetreFichePersonne extends JFrame implements ActionListener{

	/******************************
	 * Constante Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante Serial
	 ******************************/
	private static final String
	TITRE_ERROR_WINDOW = "Error Browser Window",
	TITRE_BROKEN_LINK = "Error link multimedia",
	MSG_BROKEN_LINK = "Broken link - Error ",
	MSG_ERROR_WINDOW = "Probleme avec le format d'affichage.",
	LINK_TO_MEDIA = "<HTML><FONT color=\"#000099\"><U>Lien au Média</U></FONT></HTML>";

	/******************************
	 * Classe
	 ******************************/
	private URI uri = null;
	private Font fontGras = null;
	
	/******************************
	 * Variables
	 ******************************/
	private ArrayList<URI> listURI = null;

	/******************************
	 * Variables
	 ******************************/
	private String nom = null;
	private int indexMedia = 0;

	/******************************************************
	 * @Titre:			Fenetre FIche Film CONSTRUCTOR
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	public FenetreFichePersonne(Object object){

		this.nom = object.toString();
		
		this.setSize(600,800);
		this.setResizable(true);
		this.setVisible(true);
		this.setTitle("Personne: " + nom);

		listURI = new ArrayList<>();
		fontGras = new Font("Arial", Font.BOLD, 15);

		panneauFiche();
	}

	/******************************************************
	 * @Titre:			Panneau Fiche
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	private void panneauFiche(){

		//ID Personne
		QueryPersonneLibrary.findPersonnesID(nom);
		String personneID = SessionFacade.getSession().getResultats().get(0);
		JLabel txtPersoID = new JLabel("ID: ");
		JLabel labelPersoID = new JLabel(personneID);
		JPanel panelPersoID = new JPanel(new GridLayout(1,2));
		panelPersoID.add(txtPersoID);
		panelPersoID.add(labelPersoID);

		//Nom
		JLabel txtNom = new JLabel("Nom: ");
		JLabel labelNom = new JLabel(nom);
		JPanel panelNom = new JPanel(new GridLayout(1,2));
		panelNom.add(txtNom);
		panelNom.add(labelNom);

		//Anniversaire
		QueryPersonneLibrary.findPersonneAnniv(personneID);
		JLabel txtAnniv = new JLabel("Anniversaire: ");
		JLabel labelAnniv = new JLabel(SessionFacade.getSession().getResultats().get(0));
		JPanel panelAnniv = new JPanel(new GridLayout(1,2));
		panelAnniv.add(txtAnniv);
		panelAnniv.add(labelAnniv);

		//Lieu Naissance
		QueryPersonneLibrary.findPersonneLieu(personneID);
		JLabel txtLieu = new JLabel("Lieu: ");
		JLabel labelLieu = new JLabel(SessionFacade.getSession().getResultats().get(0));
		JPanel panelLieu = new JPanel(new GridLayout(1,2));
		panelLieu.add(txtLieu);
		panelLieu.add(labelLieu);

		//Photo avec click hyperlien
		QueryPersonneLibrary.findPersonnePhoto(personneID);
		JLabel txtPhoto = new JLabel("Photo: ");
		String url = SessionFacade.getSession().getResultats().get(0);
		
		JPanel panelPhoto = new JPanel(new GridLayout(1,2));
		panelPhoto.add(txtPhoto);
		panelPhoto.add(getButtonURI(url));

		//Bio
		QueryPersonneLibrary.findPersonneBio(personneID);
		JLabel txtBio = new JLabel("Bio: ");
		JTextArea bioArea = new JTextArea(SessionFacade.getSession().getResultats().get(0));

		bioArea.setLineWrap(true);
		bioArea.setWrapStyleWord(true);
		bioArea.setOpaque(false);
		bioArea.setEditable(false);
		
		JPanel panelBio = new JPanel(new GridLayout(1,2));
		panelBio.add(txtBio);
		panelBio.add(bioArea);

		//Border Personne
		TitledBorder borderTitre = new TitledBorder("Personne");
		borderTitre.setBorder(BorderFactory.createLineBorder(Color.black));
		borderTitre.setTitleJustification(TitledBorder.LEFT);
		borderTitre.setTitlePosition(TitledBorder.TOP);
		borderTitre.setTitleFont(fontGras);

		JPanel panelPerso = new JPanel();         
		panelPerso.setLayout(new BoxLayout(panelPerso, BoxLayout.Y_AXIS));
		panelPerso.setBorder(borderTitre);	
		panelPerso.add(panelPersoID);
		panelPerso.add(panelNom);
		panelPerso.add(panelPhoto);

		//Border Naissance
		TitledBorder borderNaissance = new TitledBorder("Naissance");
		borderNaissance.setBorder(BorderFactory.createLineBorder(Color.black));
		borderNaissance.setTitleJustification(TitledBorder.LEFT);
		borderNaissance.setTitlePosition(TitledBorder.TOP);
		borderNaissance.setTitleFont(fontGras);

		JPanel panelNaissance = new JPanel();         
		panelNaissance.setLayout(new BoxLayout(panelNaissance, BoxLayout.Y_AXIS));
		panelNaissance.setBorder(borderNaissance);	
		panelNaissance.add(panelAnniv);
		panelNaissance.add(panelLieu);

		//Border Bio
		TitledBorder borderBio = new TitledBorder("Biographie");
		borderBio.setBorder(BorderFactory.createLineBorder(Color.black));
		borderBio.setTitleJustification(TitledBorder.LEFT);
		borderBio.setTitlePosition(TitledBorder.TOP);
		borderBio.setTitleFont(fontGras);
		panelBio.setBorder(borderBio);
		
		//Panel qui regroupe tous les Panels
		JPanel panelFichePerso = new JPanel();       
		panelFichePerso.setLayout(new BoxLayout(panelFichePerso, BoxLayout.Y_AXIS));
		panelFichePerso.add(panelPerso);
		panelFichePerso.add(panelNaissance);
		panelFichePerso.add(panelBio);
		
		JScrollPane scroll = new JScrollPane(panelFichePerso,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.add(scroll	);
	}

	/******************************************************
	 * @Titre:			Get Button URI
	 * 
	 * @Resumer:		
	 * 
	 ******************************************************/
	private JButton getButtonURI(String url){

		MyButton button = new MyButton(indexMedia++);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);

		if(url.equals("n/a")){
			button.setText("n/a");
			return button;
		}else{
			try {
				uri = new URI(url);
				listURI.add(uri);
				button.setText(LINK_TO_MEDIA);
				button.setToolTipText(uri.toString());
				button.addActionListener(this);
			} catch (URISyntaxException e) {e.printStackTrace();}
			return button;
		}
	}

	/******************************************************
	 * @Titre:			Action Performed
	 * 
	 * @Resumer:		Initialise une nouvelle fenetre
	 * 					pour la lecture d'une image. Avant de
	 * 					demarrer l'affichage du multimedia
	 * 					on verifie si le lien est valide.
	 * 
	 ******************************************************/
	public void actionPerformed(ActionEvent e) {	
		MyButton button = (MyButton) e.getSource();
		String link = listURI.get(button.index).toString();
		int errorCode = OutilsFenetre.getHttpErrorType(link);

		if(errorCode < 400 && errorCode >= 0 && link != null && link.length() > 0){
			if (Desktop.isDesktopSupported()){ 
				SwingUtilities.invokeLater(new Runnable() { 
					public void run() { new FenetreMediaPlayer(link); } 
				}); 
			}else  JOptionPane.showMessageDialog(this, MSG_ERROR_WINDOW, TITRE_ERROR_WINDOW, JOptionPane.ERROR_MESSAGE);
		}else JOptionPane.showMessageDialog(this, MSG_BROKEN_LINK + errorCode, TITRE_BROKEN_LINK, JOptionPane.ERROR_MESSAGE);
	}


	/******************************************************
	 * @Titre:			My Button
	 * 
	 * @Resumer:		Permet d'associer un index a un
	 * 					JButton pour permettre l'ouverture
	 * 					du bon contenu multimedia dans 
	 * 					la serie de contenus de la Personne.
	 * 
	 ******************************************************/
	private class MyButton extends JButton {		
		private static final long serialVersionUID = 1L;
		public int index;	
		MyButton(int index) {
			super(String.valueOf(index));
			this.index = index;
		}	
	}
}
