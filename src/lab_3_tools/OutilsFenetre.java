package lab_3_tools;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ.CMap;
import de.androidpit.colorthief.MMCQ.VBox;
import lab_2_connexion.SessionFacade;
import lab_3_database.QueryFilmLibrary;
import lab_3_database.ReadXMLType;
import lab_3_ui.EtatFenetre;
import lab_3_ui.StrategieFenetre;

/**************************************************************
 * @CLASS_TITLE:	Outil Fenetre
 * 
 * @Description: 	Regroupe plusieurs outils au support visuel
 * 					de l'interface Utilisateur
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class OutilsFenetre {

	/******************************
	 * Constante
	 ******************************/
	private static final String
	TITRE_LOGOUT = "Fenetre Deconnexion",
	MSG_LOGOUT = "Voullez-vous vraiment vous déconnecter ?";

	private static final int 
	RANDOM_MIN = 100000000,
	RANDOM_MAX = 999999999;

	private static double
	red = 0.0,
	green = 0.0,
	blue = 0.0;

	/******************************************************
	 * Initialise Titled Border
	 * 
	 * @Resumer:	Permet d'initialiser un nouveau
	 * 				TitledBorder pour les différentes
	 * 				section du UI. Permet principalement
	 * 				d'éviter la duplication de Code !
	 * 
	 ******************************************************/ 
	public static TitledBorder initTitledBorder(String titre){
		TitledBorder border = new TitledBorder(titre);
		border.setBorder(BorderFactory.createLineBorder(Color.black));
		border.setTitleFont(new Font("Arial", Font.BOLD, 15));	
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		return border;
	}

	/******************************************************
	 * Get Vector Check Box
	 * 
	 * @Resumer:	Recupere tous les genres et ajoute un 
	 * 				checkbox pour chacun.
	 * 
	 ******************************************************/ 
	public static Vector<JCheckBox> getVectorCheckBox(){
		Vector<JCheckBox> vector = new Vector<>();
		QueryFilmLibrary.findGenresUniques();
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++)
			vector.add(new JCheckBox(SessionFacade.getSession().getResultats().get(i), false)); 
		return vector;
	}

	/******************************************************
	 * Logout
	 * 
	 * @Resumer:	Demande la confirmation de déconnexion 
	 * 				du compte. Si accepté, on retourne à la 
	 * 				page Login en changeant l'état du Panel.
	 * 
	 ******************************************************/
	public static void logout(){
		int reponse = JOptionPane.showConfirmDialog(null, MSG_LOGOUT, TITRE_LOGOUT, JOptionPane.YES_NO_OPTION);
		if(reponse == JFileChooser.APPROVE_OPTION) StrategieFenetre.changeState(EtatFenetre.LOGIN_STATE);
		else  System.out.println("\n*** Impossible de vous déconnecter ***");
	}

	/******************************************************
	 * Get Random Number
	 * 
	 * @Resumer:	Genere une chiffre au hasard entre
	 * 				1000000000 et 999999999 pour le ID
	 * 				de la location effectué par le Client.
	 * 				
	 ******************************************************/
	public static int getRandomNumber(){
		Random rnd = new Random();
		return RANDOM_MIN + rnd.nextInt(RANDOM_MAX);
	}

	/*********************************************************************************************
	 * From CLOB to String
	 * 
	 * @return string representation of clob
	 * @Source: https://stackoverflow.com/questions/2169732/most-efficient-solution-for-reading-clob-to-string-and-string-to-clob-in-java
	 * 
	 *********************************************************************************************/
	public static String clobToString(Clob data){
		final StringBuilder sb = new StringBuilder();

		try{
			final Reader reader 	= data.getCharacterStream();
			final BufferedReader br = new BufferedReader(reader);

			int b;
			while(-1 != (b = br.read())){ sb.append((char)b); }
			br.close();
		}
		catch (SQLException e){return e.toString();}
		catch (IOException e){return e.toString();}
		return sb.toString();
	}

	/******************************************************
	 * Get Dominant Color
	 * 
	 * @Resumer:	Permet de recuperer la couleur dominante
	 * 				d'une image. Cette couleur est représentée
	 * 				avec les valeurs RGB.
	 * 
	 * @Source: 	https://stackoverflow.com/questions/10530426/how-can-i-find-dominant-color-of-an-image
	 * 
	 ******************************************************/
	public static int[] getDominantColor(String link) {

		try {
			BufferedImage img = ImageIO.read(new File(link));
			CMap result = ColorThief.getColorMap(img,5);
			VBox dominantColor = result.vboxes.get(0);
			int[] rgb = dominantColor.avg(false);
			System.err.println("(OutilsFenetre) Couleur dominante Image  -->  R:" + rgb[0] + "   G:" + rgb[1] + "   B:" + rgb[2]);
			return rgb;
		} 
		catch (IOException e) {
			e.printStackTrace(); 
			return null;
		}
	}

	/******************************************************
	 * Get HTTP Error Type
	 * 
	 * @Resumer:	Recupere le lien multimedia et verifie
	 * 				si le lien est valide. S'il n'est pas
	 * 				valide, on affiche l'erreur HTTP et
	 * 				retourne le numero representant l'erreur.
	 * 
	 * @Source: 	https://www.guru99.com/find-broken-links-selenium-webdriver.html
	 * 
	 ******************************************************/
	public static int getHttpErrorType(String link) {

		try {
			URL url = new URL(link);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("HEAD");//"GET"
			http.connect();
			return http.getResponseCode();
		} catch (IOException e) {
			System.err.println("\n(OutilsFenetre) Connexion vers le contenu multimedia impossible.");
			return -1;
		}
	}

	/******************************************************
	 * Build File Chooser
	 * 
	 * @Resumer:	Permet de recuperer une image de format
	 * 				jpg dans le but d'obtenir sa couleur
	 * 				dominante.
	 * 
	 * @Source:		https://www.mkyong.com/swing/java-swing-jfilechooser-example/
	 * 
	 ******************************************************/
	public static String buildFileChooser(){
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG images", "jpg");

		jfc.setDialogTitle("Select an image");
		jfc.setAcceptAllFileFilterUsed(false);	
		jfc.addChoosableFileFilter(filter);

		String repertoire = null;
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			repertoire = jfc.getSelectedFile().getPath();
			System.err.println("\n(OutilsFenetre) Image JPG importée: " + jfc.getSelectedFile().getPath());
			return repertoire;
		}else return repertoire;
	}

	/******************************************************
	 * Start Robot
	 * 
	 * @Resumer:	Simule l'appuie du "Enter" sur le 
	 * 				clavier pour forcer l'affichage de la 
	 * 				couleur dominante à partir des
	 * 				JTextField RGB.
	 * 
	 * @Source:		https://stackoverflow.com/questions/13563042/programmatically-trigger-a-key-events-in-a-jtextfield
	 * 
	 ******************************************************/
	public static void startRobot(){
		try {
			Robot robot = new Robot(); 
			robot.keyPress(KeyEvent.VK_ENTER); 
		} catch (AWTException awte) { awte.printStackTrace(); } 
	}

	/******************************************************
	 * Set Image
	 * 
	 * @Resumer:	Ajuste la taille et affiche l'image
	 * 				importée par l'utilisateur sur le
	 * 				JPanel approprié.
	 * 
	 * @Source:		https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel/16345968
	 * 
	 ******************************************************/
	public static void setImage(String link, JPanel panelImage){
		try {
			panelImage.removeAll();
			BufferedImage picture = ImageIO.read(new File(link));
			Image image = picture.getScaledInstance(panelImage.getWidth(), panelImage.getHeight(), Image.SCALE_SMOOTH);
			panelImage.add(new JLabel(new ImageIcon(image)));
			panelImage.repaint();
			panelImage.revalidate();
		} catch (IOException e1) {e1.printStackTrace();}
	}
	
	/******************************************************
	 * Set Min Max RGB
	 * 
	 * @Resumer:	On s'assure de rester entre 0 et 255.
	 * 				Si on depasse 255 on remet 255 et si
	 * 				on est sous 0, on remet a 0.
	 * 
	 ******************************************************/
	public static void setMinMaxRGB(JTextField textRed, JTextField textGreen, JTextField textBlue, JTextField textTolerance){
		if(Integer.parseInt(textRed.getText()) > 255) 		textRed.setText("255"); 
		if(Integer.parseInt(textRed.getText()) < 0) 		textRed.setText("0"); 
		if(Integer.parseInt(textGreen.getText()) > 255) 	textGreen.setText("255"); 
		if(Integer.parseInt(textGreen.getText()) < 0) 		textGreen.setText("0"); 
		if(Integer.parseInt(textBlue.getText()) > 255) 		textBlue.setText("255"); 
		if(Integer.parseInt(textBlue.getText()) < 0) 		textBlue.setText("0"); 
		if(Integer.parseInt(textTolerance.getText()) > 255) textTolerance.setText("255"); 
		if(Integer.parseInt(textTolerance.getText()) < 0) 	textTolerance.setText("0"); 
	}

	/******************************************************
	 * Get Panel Poster Color
	 * 
	 * @Resumer:	Creation du panneau qui affiche 
	 * 				la couleur dominante du Poster
	 * 				Film dans la fiche Film.
	 * 
	 ******************************************************/
	public static JPanel getPanelPosterClr(){

		JPanel panelClr = new JPanel();
		JLabel labelRGB = new JLabel();
		JLabel labelPourc = new JLabel();

		if(!ReadXMLType.readXML().getRed().equals("n/a") ){
			int //Recupere la couleur dominante du Poster Film
			r = Integer.parseInt(ReadXMLType.readXML().getRed()),
			g = Integer.parseInt(ReadXMLType.readXML().getGreen()),
			b = Integer.parseInt(ReadXMLType.readXML().getBlue());

			labelRGB.setText("R: " + r + "    G: " + g + "    B: " + b);
			labelPourc.setText("Différence :  " + getPercentageDiff(r,g,b) + " %");
			panelClr.setBackground(new Color(r,g,b));
		}else{
			String //Couleur dominante du Poster Film non applicable (n/a)
			r = ReadXMLType.readXML().getRed(),
			g = ReadXMLType.readXML().getGreen(),
			b = ReadXMLType.readXML().getBlue();

			labelRGB.setText("R: " + r + "    G: " + g + "    B: " + b);
			labelPourc.setText("Différence :  n/a");
			panelClr.setBorder(OutilsFenetre.initTitledBorder(""));
		}

		JPanel panelPourcClr = new JPanel(new GridLayout(2,1));
		panelPourcClr.add(labelRGB);
		panelPourcClr.add(labelPourc);

		//RGB et Boite couleur
		JPanel panelGroupClr = new JPanel(new GridLayout(1,2));
		panelGroupClr.add(panelPourcClr);
		panelGroupClr.add(panelClr);
		return panelGroupClr;
	}
	
	/******************************************************
	 * Add EMpty Panel
	 * 
	 * @Resumer:	Retourne un Panel vide pour créer
	 * 				des 'Spacer' dans un panel complexe.
	 * 
	 ******************************************************/
	public static JPanel addEmptyPanel(){
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(new JLabel(" "));
		panel.add(new JLabel(" "));
		return panel;
	}

	/******************************************************
	 * Get Difference Pourcentage Couleur Dominante
	 * 
	 * @Resumer:	Calcul la difference en % entre 
	 * 				la couleur dominante de l'image
	 * 				importée et le Poster d'un Film.
	 * 
	 ******************************************************/
	public static String getPercentageDiff(double _r, double _g, double _b){

		String pourcentage = null;
		double diffR = 0.0, diffG = 0.0, diffB = 0.0;

		if(red >= _r)   diffR = (_r/red);
		else 		   	diffR = (red/_r);

		if(green >= _g) diffG = (_g/green);
		else 		   	diffG = (green/_g);

		if(blue >= _b)  diffB = (_b/blue);
		else 		    diffB = (blue/_b);

		double moyenne = ((diffR + diffG + diffB) / 3.0);
		double p = Math.abs((moyenne * 100.0) - 100.0);

		DecimalFormat df = new DecimalFormat("#.##");
		pourcentage = df.format(p);
		if(p == 100.0) pourcentage = "n/a";
		
		return pourcentage;
	}

	public static void setRed(double _r)	{red = _r;}
	public static void setGreen(double _g)	{green = _g;}
	public static void setBlue(double _b)	{blue = _b;}
}
