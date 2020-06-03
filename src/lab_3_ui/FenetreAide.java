package lab_3_ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**************************************************************
 * @CLASS_TITLE:	Fenetre Aide
 * 
 * @Description: 	Affiche un Simple panneau avec une image
 * 					illustrant et expliquant les différentes
 * 					partie de l'interface UI.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class FenetreAide extends JFrame{

	/******************************
	 * Serial
	 ******************************/
	private static final long serialVersionUID = 1L;
	
	/******************************
	 * Constantes
	 ******************************/
	private static final String TITRE = "Fenetre du Compte";
	private static final int 
	HAUTEUR = 1000,
	LARGEUR = 825;
	
	
	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public FenetreAide(){ 
		
		this.setSize(LARGEUR,HAUTEUR);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle(TITRE);
		
		displayTutorial(); 
	}

	/******************************************************
	 * Display Tutorial
	 * 
	 * @Resumer:	
	 * 
	 ******************************************************/
	private void displayTutorial(){

		try {
			BufferedImage myPicture = ImageIO.read(new File("./doc/lab2/UserGuide.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			this.add(picLabel);
		} catch (IOException e) {e.printStackTrace();}

	}
}
