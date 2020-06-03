package lab_3_mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


/**************************************************************
 * @CLASS_TITLE:	Fenetre Media Player
 * 
 * @Description: 	Permet l'ouverture des fichiers video dans
 * 					une nouvelle fenetre. Lecture du contenu
 * 					avec les options de controles.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class FenetreMediaPlayer extends JFrame implements WindowListener{

	/******************************
	 * Constante Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante Repertoire VLC
	 ******************************/
	private static final String 
	TITRE = "Lecteur Multimedia GTI660",
	NO_PLUGIN = "--no-plugins-cache",
	NO_TITLE = "--no-video-title-show",
	NO_SNAPSHOT = "--no-snapshot-preview",
	NO_CLOCK = "--clock-jitter=0",
	LECTURE = "\n(MediaPlayer) Début de Lecture: ",
	FERMETURE = "(MediaPlayer) Arrêt du Lecteur: ",
	REPERTOIRE_VLC = "C:\\Program Files\\VideoLAN\\VLC";

	/******************************
	 * Interface
	 ******************************/
	private EmbeddedMediaPlayer mediaPlayer = null;
	private MediaPlayerFactory mediaPlayerFactory = null;

	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public FenetreMediaPlayer(String link){

		this.setTitle(TITRE);
		this.addWindowListener(this);
		this.setLocation(100, 100);
		this.setSize(1050, 600);
		this.setResizable(true);
		this.setVisible(true);
		multimediaPanel(link);
	}

	/******************************************************
	 * @Titre:			Multimedia Panel
	 * 
	 * @Resumer:		Panneau permet l'affichage du video
	 * 					et qui affiche le panneau des 
	 * 					options de contrôles de lecture.
	 * 
	 * @Source:			https://stackoverflow.com/questions/13440760/embedding-vlcj-in-jpanel
	 * 
	 ******************************************************/
	private void multimediaPanel(String link){

		//Permet l'ouverture du contenu dans VLC
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), REPERTOIRE_VLC);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		//Panneau et Canvas qui supporte l'affichage du lecteur video
		JPanel panel = new JPanel(new BorderLayout());
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.black);
		panel.add(canvas, BorderLayout.CENTER);
		this.add(panel, BorderLayout.CENTER);

		//Initialisation du lecteur multimedia
		initMediaPlayer(link,canvas);
		String extension = link.substring(link.lastIndexOf("."));

		//Si ce n'est pas un Poster, on ajoute le panneau des boutons de controles (video)
		if(!extension.contains(".jpg") || !link.contains(".jpg"))
			panel.add(new ControlsPanel(mediaPlayer), BorderLayout.SOUTH);	

		System.err.println(LECTURE + link);
	}

	/******************************************************
	 * Init Media Player
	 * 
	 * @Resumer:	Initialise les parametre du lecteur de
	 * 				contenu multimedia.
	 * 
	 ******************************************************/
	private void initMediaPlayer(String link, Canvas canvas){

		//Retire les elements inutiles du lecteur VLC
		List<String> vlcArgs = new ArrayList<String>();
		vlcArgs.add(NO_CLOCK);
		vlcArgs.add(NO_PLUGIN);
		vlcArgs.add(NO_TITLE);
		vlcArgs.add(NO_SNAPSHOT);

		//Creation d'une nouvelle instance de 'media player' pour la lecture sur la plateforme 
		mediaPlayerFactory = new MediaPlayerFactory(vlcArgs.toArray(new String[vlcArgs.size()]));
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
		mediaPlayer.setPlaySubItems(true);	
		mediaPlayer.playMedia(link);
	}

	/******************************************************
	 * Window Closing
	 * 
	 * @Resumer:	À la fermeture du video (de la page), 
	 * 				on s'assure d'arreter le lecteur. 
	 * 
	 ******************************************************/
	public void windowClosing(WindowEvent e) {
		mediaPlayer.stop();
		//mediaPlayer.release(); 
		//mediaPlayerFactory.release();
		System.err.println(FERMETURE + mediaPlayerFactory.getClass().getName());
		this.dispose();
	}

	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}