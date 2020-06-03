package lab_3_ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import lab_2_connexion.RSQUser;
import lab_2_connexion.SessionFacade;


/**************************************************************
 * @CLASS_TITLE:	Panel Login
 * 
 * @Description: 	Fenetre qui permet au Client d'entrer son
 * 					Email et Password pour démarrer une session.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class PanelLogin extends JPanel implements ActionListener, PropertyChangeListener{

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constante
	 ******************************/
	private static final int 
	NB_CHAR = 15;

	private static final String 
	LOGIN = "Login",
	EMAIL = "Email: ",
	PASSWORD = "Password: ",
	AUTHENTIF = "AUTHENTIFICATION";

	/******************************
	 * Swing Object 
	 ******************************/
	private JLabel labelEtat = null;
	private JButton boutonLogin = null;
	
	private Font 
	fontGras = null,
	fontSimple = null;

	private JTextField  
	textEmail = null,
	textPassw = null;


	/******************************************************
	 * 					Constructeur
	 ******************************************************/
	public PanelLogin(){ 
		fontGras = new Font("Arial", Font.BOLD, 15);
		fontSimple = new Font("Arial", Font.PLAIN, 15);
		
		this.setLayout(new GridLayout(4,1,10,10));
		this.setPreferredSize(new Dimension(600,180));
		loginPanel(); 
	}

	/******************************************************
	 * Login Panel
	 * 
	 * @Resumer:	Panneau qui demande au Client d'entrer
	 * 				son email et password pour acccéder à
	 * 				la recherche.
	 * 
	 ******************************************************/
	public void loginPanel(){

		//Label qui affiche l'etat de la connexion
		labelEtat = new JLabel("Fermé");	
		labelEtat.setForeground(Color.RED);
		labelEtat.setFont(fontGras);

		JLabel 
		labelEmail = new JLabel(EMAIL),
		labelPassw = new JLabel(PASSWORD),
		labelConnect = new JLabel("État connexion: ");

		labelEmail.setFont(fontSimple);
		labelPassw.setFont(fontSimple);
		labelConnect.setFont(fontGras);
		labelEtat.setFont(fontSimple);

		//Panneau qui affiche l'etat de la connexion
		JPanel panelEtat = new JPanel(new GridLayout(1,2));
		panelEtat.add(labelConnect);
		panelEtat.add(labelEtat);

		//Boite de texte pour le username(email) et password
		textEmail = new JTextField(NB_CHAR);
		textPassw = new JPasswordField(NB_CHAR);
		textEmail.setFont(fontSimple);
		textPassw.setFont(fontSimple);

		//Initialise un Login Client pour accelerer les tests sur l'app
		textEmail.setText("RobertCFlores21@gmail.com");
		textPassw.setText("eishie3meiH");

		//Configuration du Bouton Login et de son Listener
		boutonLogin = new JButton(LOGIN);
		boutonLogin.addActionListener(this);
		boutonLogin.setFont(fontGras);
		boutonLogin.setEnabled(false);  

		//Bordure autour du Panel Login
		TitledBorder border = new TitledBorder(AUTHENTIF);
		border.setBorder(BorderFactory.createLineBorder(Color.black));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(fontGras);

		this.setBorder(border);
		this.add(new JLabel(" "));
		this.add(new JLabel(" "));
		this.add(labelEmail);
		this.add(textEmail);
		this.add(labelPassw);
		this.add(textPassw);
		this.add(panelEtat);
		this.add(boutonLogin);
	}

	/******************************************************
	 * Action Performed
	 * 
	 * @Resumer:	Lorsque le bouton Login est appuyé, on
	 * 				démarre la vérification des info Client 
	 * 				pour ensuite créer une session.
	 * 
	 ******************************************************/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonLogin) { 
			SessionFacade session = SessionFacade.getSession();
			session.initSession(new RSQUser(textEmail.getText(),textPassw.getText()));
		}
	}

	/******************************************************
	 * Property Change
	 * 
	 * @Resumer:	Reçoit des évènements lancés par la 
	 * 				classe de connexion DB pour mettre à
	 * 				jour l'état de la connexion sur le UI.
	 * 
	 ******************************************************/
	public void propertyChange(PropertyChangeEvent evt) {

		//Connexion Ouverte: Activation du bouton Login et update le Label état
		if(evt.getPropertyName().equals("connexionOuverte")){		
			labelEtat.setText("Ouverte");
			labelEtat.setFont(fontGras);
			labelEtat.setForeground(new Color(0,204,0));	
			boutonLogin.setEnabled((boolean) evt.getNewValue());
		}
		//Connexion en cours: Update le Label état
		if(evt.getPropertyName().equals("connexionEnCours")){	
			labelEtat.setText("En cours...");
			labelEtat.setFont(fontGras);
			labelEtat.setForeground(new Color(255,140,0));
		}

		//Connexion Fermé: Update le Label état
		if(evt.getPropertyName().equals("connexionImpossible")){		
			labelEtat.setText("Fermé");
			labelEtat.setFont(fontGras);
			labelEtat.setForeground(Color.RED);
		}
	}
}
