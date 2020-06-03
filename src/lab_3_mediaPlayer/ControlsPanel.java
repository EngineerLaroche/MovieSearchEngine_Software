package lab_3_mediaPlayer;

import java.awt.BorderLayout; 
import java.awt.Dimension; 
import java.awt.FlowLayout; 
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors; 
import java.util.concurrent.ScheduledExecutorService; 
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton; 
import javax.swing.JFileChooser; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JSlider; 
import javax.swing.SwingUtilities; 
import javax.swing.border.EmptyBorder; 
import javax.swing.event.ChangeEvent; 
import javax.swing.event.ChangeListener; 
import javax.swing.filechooser.FileFilter; 

import uk.co.caprica.vlcj.binding.LibVlcConst; 
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory; 
import uk.co.caprica.vlcj.player.MediaPlayer; 
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter; 
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer; 

/**************************************************************
 * @CLASS_TITLE:	Controls Panel
 * 
 * @Description: 	Permet le contrôle d'un video avec 
 * 					des options tels que pause, stop, skip, etc.
 * 					Le panneau regroupe les options pour faciliter
 * 					son implementation avec le panneau de lecture video.
 * 
 * @Source:			http://www.javased.com/index.php?source_dir=vlcj/src/test/java/uk/co/caprica/vlcj/test/basic/PlayerControlsPanel.java
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class ControlsPanel extends JPanel{ 

	/******************************
	 * Constante
	 ******************************/
	private static final long serialVersionUID = 1L; 
	private static final int SKIP_TIME_MS = 10 * 1000; 

	/******************************
	 * Final Interface
	 ******************************/
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(); 
	private final EmbeddedMediaPlayer mediaPlayer; 

	/******************************
	 * Swing Object
	 ******************************/
	private JFileChooser fileChooser; 
	
	private JSlider 
	positionSlider,
	volumeSlider;

	private JLabel 
	chapterLabel,
	timeLabel;

	private JButton 
	previousChapterButton, 
	rewindButton, 
	stopButton,
	pauseButton, 
	playButton,
	fastForwardButton,
	nextChapterButton, 
	toggleMuteButton,
	captureButton,
	ejectButton,
	connectButton,
	fullScreenButton,
	subTitlesButton;

	/******************************
	 * Primitive
	 ******************************/
	private boolean mousePressedPlaying = false; 

	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public ControlsPanel(EmbeddedMediaPlayer _mediaPlayer) { 
		
		this.mediaPlayer = _mediaPlayer; 
		this.setBorder(new EmptyBorder(4, 4, 4, 4)); 
		this.setLayout(new BorderLayout()); 
		
		createUI(); 
		executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer), 0L, 1L, TimeUnit.SECONDS); 
	} 

	/******************************************************
	 * 					Create UI
	 ******************************************************/
	private void createUI() { 
		createControls(); 
		layoutControls(); 
		registerListeners(); 
	} 

	/******************************************************
	 * 					Create Controls
	 ******************************************************/
	private void createControls() { 
		
		timeLabel = new JLabel("hh:mm:ss"); 

		positionSlider = new JSlider(); 
		positionSlider.setMinimum(0); 
		positionSlider.setMaximum(1000); 
		positionSlider.setValue(0); 
		positionSlider.setToolTipText("Position"); 

		chapterLabel = new JLabel("00/00"); 

		previousChapterButton = new JButton(); 
		previousChapterButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_start_blue.png"))); 
		previousChapterButton.setToolTipText("Go to previous chapter"); 

		rewindButton = new JButton(); 
		rewindButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_rewind_blue.png"))); 
		rewindButton.setToolTipText("Skip back"); 

		stopButton = new JButton(); 
		stopButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_stop_blue.png"))); 
		stopButton.setToolTipText("Stop"); 

		pauseButton = new JButton(); 
		pauseButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_pause_blue.png"))); 
		pauseButton.setToolTipText("Play/pause"); 

		playButton = new JButton(); 
		playButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_play_blue.png"))); 
		playButton.setToolTipText("Play"); 

		fastForwardButton = new JButton(); 
		fastForwardButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_fastforward_blue.png"))); 
		fastForwardButton.setToolTipText("Skip forward"); 

		nextChapterButton = new JButton(); 
		nextChapterButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_end_blue.png"))); 
		nextChapterButton.setToolTipText("Go to next chapter"); 

		toggleMuteButton = new JButton(); 
		toggleMuteButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/sound_mute.png"))); 
		toggleMuteButton.setToolTipText("Toggle Mute"); 

		volumeSlider = new JSlider(); 
		volumeSlider.setOrientation(JSlider.HORIZONTAL); 
		volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME); 
		volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME); 
		volumeSlider.setPreferredSize(new Dimension(100, 40)); 
		volumeSlider.setToolTipText("Change volume"); 

		captureButton = new JButton(); 
		captureButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/camera.png"))); 
		captureButton.setToolTipText("Take picture"); 

		ejectButton = new JButton(); 
		ejectButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_eject_blue.png"))); 
		ejectButton.setToolTipText("Load/eject media"); 

		connectButton = new JButton(); 
		connectButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/connect.png"))); 
		connectButton.setToolTipText("Connect to media"); 

		fileChooser = new JFileChooser(); 
		fileChooser.setApproveButtonText("Play"); 
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter()); 
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter()); 
		fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter()); 
		FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter(); 
		fileChooser.addChoosableFileFilter(defaultFilter); 
		fileChooser.setFileFilter(defaultFilter); 

		fullScreenButton = new JButton(); 
		fullScreenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/image.png"))); 
		fullScreenButton.setToolTipText("Toggle full-screen"); 

		subTitlesButton = new JButton(); 
		subTitlesButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/comment.png"))); 
		subTitlesButton.setToolTipText("Cycle sub-titles"); 
	} 

	/******************************************************
	 * 				Layout Options de Controls
	 ******************************************************/
	private void layoutControls() { 

		JPanel positionPanel = new JPanel(); 
		positionPanel.setLayout(new GridLayout(1, 1)); 
		positionPanel.add(positionSlider); 

		JPanel topPanel = new JPanel(); 
		topPanel.setLayout(new BorderLayout(8, 0)); 
		topPanel.add(timeLabel, BorderLayout.WEST); 
		topPanel.add(positionPanel, BorderLayout.CENTER); 
		topPanel.add(chapterLabel, BorderLayout.EAST); 

		JPanel bottomPanel = new JPanel(); 
		bottomPanel.setLayout(new FlowLayout()); 
		bottomPanel.add(previousChapterButton); 
		bottomPanel.add(rewindButton); 
		bottomPanel.add(stopButton); 
		bottomPanel.add(pauseButton); 
		bottomPanel.add(playButton); 
		bottomPanel.add(fastForwardButton); 
		bottomPanel.add(nextChapterButton); 
		bottomPanel.add(volumeSlider); 
		bottomPanel.add(toggleMuteButton); 
		bottomPanel.add(captureButton); 
		bottomPanel.add(ejectButton); 
		bottomPanel.add(connectButton); 
		bottomPanel.add(fullScreenButton); 
		bottomPanel.add(subTitlesButton); 
		
		this.add(topPanel, BorderLayout.NORTH); 
		this.add(bottomPanel, BorderLayout.SOUTH); 
	} 

	/**
	 * Broken out position setting, handles updating mediaPlayer 
	 */ 
	private void setSliderBasedPosition() { 
		if(!mediaPlayer.isSeekable()) { 
			return; 
		} 
		float positionValue = positionSlider.getValue() / 1000.0f; 
		// Avoid end of file freeze-up 
		if(positionValue > 0.99f) { 
			positionValue = 0.99f; 
		} 
		mediaPlayer.setPosition(positionValue); 
	} 

	private void updateUIState() { 
		if(!mediaPlayer.isPlaying()) { 
			// Resume play or play a few frames then pause to show current position in video 
			mediaPlayer.play(); 
			if(!mousePressedPlaying) { 
				try { 
					// Half a second probably gets an iframe 
					Thread.sleep(500); 
				} 
				catch(InterruptedException e) { 
					// Don't care if unblocked early 
				} 
				mediaPlayer.pause(); 
			} 
		} 
		long time = mediaPlayer.getTime(); 
		int position = (int)(mediaPlayer.getPosition() * 1000.0f); 
		int chapter = mediaPlayer.getChapter(); 
		int chapterCount = mediaPlayer.getChapterCount(); 
		updateTime(time); 
		updatePosition(position); 
		updateChapter(chapter, chapterCount); 
	} 

	private void skip(int skipTime) { 
		// Only skip time if can handle time setting 
		if(mediaPlayer.getLength() > 0) { 
			mediaPlayer.skip(skipTime); 
			updateUIState(); 
		} 
	} 

	private void registerListeners() { 
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() { 
			@Override 
			public void playing(MediaPlayer mediaPlayer) { 
				updateVolume(mediaPlayer.getVolume()); 
			} 
		}); 

		positionSlider.addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent e) { 
				if(mediaPlayer.isPlaying()) { 
					mousePressedPlaying = true; 
					mediaPlayer.pause(); 
				} 
				else mousePressedPlaying = false; 
				setSliderBasedPosition(); 
			} 

			public void mouseReleased(MouseEvent e) { 
				setSliderBasedPosition(); 
				updateUIState(); 
			} 
		}); 

		previousChapterButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.previousChapter(); 
			} 
		}); 

		rewindButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				skip(-SKIP_TIME_MS); 
			} 
		}); 

		stopButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.stop(); 
				positionSlider.setValue(0); 
				timeLabel.setText("00:00:00"); 
			} 
		}); 

		pauseButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.pause(); 
			} 
		}); 

		playButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.play(); 
			} 
		}); 

		fastForwardButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				skip(SKIP_TIME_MS); 
			} 
		}); 

		nextChapterButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.nextChapter(); 
			} 
		}); 

		toggleMuteButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.mute(); 
			} 
		}); 

		volumeSlider.addChangeListener(new ChangeListener() { 
			public void stateChanged(ChangeEvent e) { 
				JSlider source = (JSlider)e.getSource(); 
				mediaPlayer.setVolume(source.getValue()); 
			} 
		}); 

		captureButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.saveSnapshot(); 
			} 
		}); 

		ejectButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.enableOverlay(false); 
				if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(ControlsPanel.this)) { 
					mediaPlayer.playMedia(fileChooser.getSelectedFile().getAbsolutePath()); 
				} 
				mediaPlayer.enableOverlay(true); 
			} 
		}); 

		connectButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.enableOverlay(false); 
				String mediaUrl = JOptionPane.showInputDialog(ControlsPanel.this, "Enter a media URL", "Connect to media", JOptionPane.QUESTION_MESSAGE); 
				if(mediaUrl != null && mediaUrl.length() > 0) { 
					mediaPlayer.playMedia(mediaUrl); 
				} 
				mediaPlayer.enableOverlay(true); 
			} 
		}); 

		fullScreenButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				mediaPlayer.toggleFullScreen(); 
			} 
		}); 

		subTitlesButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int spu = mediaPlayer.getSpu(); 
				if(spu > -1) { 
					spu ++ ; 
					if(spu > mediaPlayer.getSpuCount()) { 
						spu = -1; 
					} 
				} 
				else { 
					spu = 0; 
				} 
				mediaPlayer.setSpu(spu); 
			} 
		}); 
	} 

	/**************************************************************
	 * @CLASS_TITLE:	Update Runnable
	 * 
	 * @Description: 	Mise a jour des options de contrôles vodeo.
	 * 
	 * @Source:			http://www.javased.com/index.php?source_dir=vlcj/src/test/java/uk/co/caprica/vlcj/test/basic/PlayerControlsPanel.java
	 * 
	 * @Cours:			GTI660-01
	 * @Session:		H-2019	
	 * 
	 **************************************************************/
	private final class UpdateRunnable implements Runnable { 

		/******************************
		 * Final Interface
		 ******************************/
		private final MediaPlayer mediaPlayer; 

		/******************************************************
		 * 					Constructeur
		 ******************************************************/
		private UpdateRunnable(MediaPlayer mediaPlayer) { 
			this.mediaPlayer = mediaPlayer; 
		} 

		@Override 
		public void run() { 
			final long time = mediaPlayer.getTime(); 
			final int chapter = mediaPlayer.getChapter(); 
			final int chapterCount = mediaPlayer.getChapterCount(); 
			final int position = (int)(mediaPlayer.getPosition() * 1000.0f); 	

			// Updates to user interface components must be executed on the Event. Dispatch Thread 
			SwingUtilities.invokeLater(new Runnable() { 
				@Override 
				public void run() { 
					if(mediaPlayer.isPlaying()) { 
						updateTime(time); 
						updatePosition(position); 
						updateChapter(chapter, chapterCount); 
						endMedia();
					} 
				} 
			}); 
		} 
	} 

	private void updateTime(long millis) { 
		String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))); 
		timeLabel.setText(s); 
	} 

	private void updatePosition(int value) { 
		// positionProgressBar.setValue(value); 
		positionSlider.setValue(value); 		
	} 

	private void updateChapter(int chapter, int chapterCount) { 
		String s = chapterCount != -1 ? (chapter + 1) + "/" + chapterCount : "-"; 
		chapterLabel.setText(s); 
		chapterLabel.invalidate(); 
		validate(); 
	} 
	
	private void endMedia(){
		float positionValue = positionSlider.getValue() / 1000.0f;
		if(positionValue >= 0.98f){
			mediaPlayer.stop(); 
			positionSlider.setValue(0); 
			timeLabel.setText("00:00:00"); 
		}
	}

	private void updateVolume(int value) { 
		volumeSlider.setValue(value); 
	} 
}