package lab_3_ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import lab_2_connexion.SessionFacade;
import lab_3_database.QueryPersonneLibrary;


/**************************************************************
 * @CLASS_TITLE:	Fenetre COmpte CLient
 * 
 * @Description: 	Affiche toute l'information du Client.
 * 					Le client peut modifier ses parametres.
 * 					Si non respect des contraintes, un message
 * 					avertira le Client.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class FenetreCompteClient extends JFrame implements ActionListener, KeyListener {

	/******************************
	 * Serial
	 ******************************/
	private static final long serialVersionUID = 1L;

	/******************************
	 * Constantes
	 ******************************/
	private static final int 
	HAUTEUR = 650,
	LARGEUR = 600,
	NB_CHAR = 20;

	private static final String 
	TITRE 				= "Fenetre du Compte",
	MSG_UPDATE 			= "Êtes-vous certain de vouloir mettre à jour vos données ?",
	MSG_ERROR_COMPTE 	= "Mise à jour Client non réussi.\nContrainte non respectée !",
	MSG_ERROR_CREDIT 	= "Mise à jour Crédit non réussi.\nContrainte non respectée !",	
	MSG_APPROUVE 		= "\n******************************************" +
							"\n*	Modification du Compte réussi !	 *" +
							"\n******************************************";
	
	/******************************
	 * Swing Object
	 ******************************/
	private JPanel panelCompte = null; 

	private Font fontGras = null;

	private JButton
	boutonUpdate = null,
	boutonCancel = null;

	private JTextField 
	textPrenom 		= null, 
	textNomFamille 	= null,
	textCourriel 	= null, 
	textTel 		= null, 
	textAnniv 		= null,
	textPassw 		= null,
	textForfait 	= null,

	textAdresse 	= null,
	textVille 		= null,
	textProvince 	= null,
	textCodePostal 	= null,

	textCarte 		= null,
	textNo 			= null,
	textExpMois 	= null,
	textExpAnnee 	= null;

	/******************************
	 * Variable
	 ******************************/
	private String clientID = null;

	/******************************
	 * Liste info client
	 ******************************/
	private ArrayList<String> 
	listeInfoClient = null,
	listeInfoCredit = null;


	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public FenetreCompteClient(String _clientID){

		this.clientID = _clientID;

		this.setSize(LARGEUR,HAUTEUR);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle(TITRE);

		listeInfoClient = new ArrayList<>();
		listeInfoCredit = new ArrayList<>();

		panelCompte = new JPanel();
		fontGras = new Font("Arial", Font.BOLD, 15);

		setInfoClient();
		setInfoCredit();
		fenetreBoutons();
	}

	/******************************************************
	 * Set Information Client
	 * 
	 * @Resumer:	Recherche l'info du Client avec son ID.
	 * 				Pour tout l'info Client trouvé, on 
	 * 				l'ajoute dans une liste et on initialise
	 * 				l'affichage de l'information en sections.
	 * 
	 ******************************************************/
	public void setInfoClient(){
		QueryPersonneLibrary.findClient(clientID);
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++) 
			listeInfoClient.add(SessionFacade.getSession().getResultats().get(i));
		
		//Affichage de l'information en section
		infoCompte();
		infoPersonel();
		infoAdresse();
	}

	/******************************************************
	 * Set Information Credit
	 * 
	 * @Resumer:	Recherche du Credit a partir de l'ID Client.
	 * 				Pour tout l'info Credit trouvé, on l'ajoute 
	 * 				dans une liste et on affiche l'info Credit.
	 * 
	 ******************************************************/
	public void setInfoCredit(){
		QueryPersonneLibrary.findCredit(clientID);
		for (int i = 0; i < SessionFacade.getSession().getResultats().size(); i++) 
			listeInfoCredit.add(SessionFacade.getSession().getResultats().get(i));

		infoCredit(); //Affichage de l'information du Credit
	}

	/******************************************************
	 * Fenetre Boutons
	 * 
	 * @Resumer:	Affiche les boutons "Cancel" et "Update".
	 * 				Les boutons observent les actions du Client.
	 * 
	 ******************************************************/
	public void fenetreBoutons(){

		boutonUpdate = new JButton("Update");
		boutonCancel = new JButton("Cancel");
		boutonUpdate.addActionListener(this);
		boutonCancel.addActionListener(this);

		JPanel boutonsPanel = new JPanel(new GridLayout(1,2,10,10));
		boutonsPanel.add(boutonUpdate);
		boutonsPanel.add(boutonCancel);
		panelCompte.add(boutonsPanel);

		this.add(panelCompte);
	}

	/******************************************************
	 * Information Compte
	 * 
	 * @Resumer:	Affiche et permet la modification de
	 * 				l'information Compte du Client.
	 * 
	 ******************************************************/
	public void infoCompte(){

		JLabel
		labelId = new JLabel(listeInfoClient.get(11)),
		labelIdentif = new JLabel("Identifiant"),
		labelCourriel = new JLabel("Courriel"),
		labelPassw = new JLabel("Mot de Passe");

		textCourriel = new JTextField(NB_CHAR); 
		textPassw = new JTextField(NB_CHAR);

		//Met l'info du Compte dans les cases 
		textCourriel.setText(listeInfoClient.get(4));
		textPassw.setText(listeInfoClient.get(5));

		//Panel Info général du Compte
		TitledBorder border = initTitledBorder("INFO COMPTE");
		JPanel panelInfoPCompte = new JPanel(new GridLayout(3,1,10,10));

		panelInfoPCompte.setBorder(border);
		panelInfoPCompte.add(labelIdentif);
		panelInfoPCompte.add(labelId);
		panelInfoPCompte.add(labelCourriel);
		panelInfoPCompte.add(textCourriel);
		panelInfoPCompte.add(labelPassw);
		panelInfoPCompte.add(textPassw);

		panelCompte.add(panelInfoPCompte);
	}

	/******************************************************
	 * Information Personnel
	 * 
	 * @Resumer:	Affiche et permet la modification de
	 * 				l'information Personnel du Client.
	 * 
	 ******************************************************/
	public void infoPersonel(){

		JLabel
		labelPrenom = new JLabel("Prenom"),
		labelNomFamille = new JLabel("Nom Famille"), 
		labelTel = new JLabel("Telephone"),
		labelAnniversaire = new JLabel("Anniversaire"), 	
		labelForfait = new JLabel("Forfait");

		textPrenom = new JTextField(NB_CHAR); 
		textNomFamille = new JTextField(NB_CHAR);	
		textTel = new JTextField(NB_CHAR); 
		textAnniv = new JTextField(NB_CHAR);	
		textForfait = new JTextField(NB_CHAR);	

		String anniversaire = listeInfoClient.get(3);

		//Temporaire (retirer l'heure de la Date)
		if(anniversaire.length() > 10) 
			anniversaire = anniversaire.substring(0, 10);

		//Met l'info du Client dans les cases 
		textPrenom.setText(listeInfoClient.get(0));
		textNomFamille.setText(listeInfoClient.get(1));	
		textTel.setText(listeInfoClient.get(2)); 
		textAnniv.setText(anniversaire);
		textForfait.setText(listeInfoClient.get(10));

		//Panel Info Personnel
		TitledBorder border = initTitledBorder("INFO PERSONNEL");
		JPanel panelInfoPerso = new JPanel(new GridLayout(5,1,10,10));
		panelInfoPerso.setBorder(border);

		panelInfoPerso.add(labelPrenom);
		panelInfoPerso.add(textPrenom);
		panelInfoPerso.add(labelNomFamille);
		panelInfoPerso.add(textNomFamille);	
		panelInfoPerso.add(labelTel);
		panelInfoPerso.add(textTel);
		panelInfoPerso.add(labelAnniversaire);
		panelInfoPerso.add(textAnniv);
		panelInfoPerso.add(labelForfait);
		panelInfoPerso.add(textForfait);

		panelCompte.add(panelInfoPerso);
	}

	/******************************************************
	 * Information Adresse
	 * 
	 * @Resumer:	Affiche et permet la modification de
	 * 				l'information Adresse du Client.
	 * 
	 ******************************************************/
	public void infoAdresse(){

		JLabel
		labelAdresse = new JLabel("Adresse"), 
		labelVille = new JLabel("Ville"),
		labelProvince = new JLabel("Province"), 
		labelCodePostal = new JLabel("Code Postal"); 

		textAdresse = new JTextField(NB_CHAR);
		textVille = new JTextField(NB_CHAR);
		textProvince = new JTextField(NB_CHAR);
		textCodePostal = new JTextField(NB_CHAR);

		//Affiche l'info trouvé du client dans les JText FIeld
		textAdresse.setText(listeInfoClient.get(6));
		textVille.setText(listeInfoClient.get(7));
		textProvince.setText(listeInfoClient.get(8));
		textCodePostal.setText(listeInfoClient.get(9));

		//Panel Info Adresse
		TitledBorder border = initTitledBorder("ADRESSE");
		JPanel panelAddress = new JPanel(new GridLayout(4,1,10,10));
		panelAddress.setBorder(border);

		panelAddress.add(labelAdresse);
		panelAddress.add(textAdresse);
		panelAddress.add(labelVille);
		panelAddress.add(textVille);
		panelAddress.add(labelProvince);
		panelAddress.add(textProvince);
		panelAddress.add(labelCodePostal);
		panelAddress.add(textCodePostal);

		panelCompte.add(panelAddress);
	}

	/******************************************************
	 * Information Credit
	 * 
	 * @Resumer:	Affiche et permet la modification de
	 * 				l'information Credit du Client.
	 * 
	 ******************************************************/
	public void infoCredit(){

		JLabel
		labelCarte = new JLabel("Carte"), 
		labelNo = new JLabel("Numéro"),
		labelExpMois = new JLabel("Exp-Mois"), 
		labelExpAnnee = new JLabel("Exp-Annee");

		textCarte = new JTextField(NB_CHAR);
		textNo = new JTextField(NB_CHAR);
		textExpMois = new JTextField(NB_CHAR);
		textExpAnnee = new JTextField(NB_CHAR);

		//Affiche l'info trouvé du client dans les JText FIeld
		textCarte.setText(listeInfoCredit.get(0));
		textNo.setText(listeInfoCredit.get(1));
		textExpMois.setText(listeInfoCredit.get(2));
		textExpAnnee.setText(listeInfoCredit.get(3));

		//Panel Info Credit
		TitledBorder border = initTitledBorder("INFO CREDIT");
		JPanel panelInfoCredit = new JPanel(new GridLayout(4,1,10,10));
		panelInfoCredit.setBorder(border);

		panelInfoCredit.add(labelCarte);
		panelInfoCredit.add(textCarte);
		panelInfoCredit.add(labelNo);
		panelInfoCredit.add(textNo);
		panelInfoCredit.add(labelExpMois);
		panelInfoCredit.add(textExpMois);
		panelInfoCredit.add(labelExpAnnee);
		panelInfoCredit.add(textExpAnnee);

		panelCompte.add(panelInfoCredit);
	}

	/******************************************************
	 * Action Performed
	 * 
	 * @Resumer:	Si Client click sur "Cancel", la page
	 * 				du compte se ferme sans rien sauvegarder,
	 * 				sinon on procède à la sauvegarde.
	 * 
	 ******************************************************/
	public void actionPerformed (ActionEvent e){
		if (e.getSource() == boutonUpdate) { updateInfo(); }
		else if (e.getSource() == boutonCancel)	this.dispose(); 
	}

	/******************************************************
	 * Update Information Client
	 * 
	 * @Resumer:	Met à jour l'information du Compte 
	 * 				entrée par le Client et affiche un
	 * 				message d'erreur si les contraintes
	 * 				de la DB ne sont pas respectées.
	 * 
	 ******************************************************/
	public void updateInfo(){ 

		//Vérifie la validité de l'information du Compte
		boolean goodCompte = QueryPersonneLibrary.updateClient(textPrenom.getText(), textNomFamille.getText(), 
				textCourriel.getText(), textPassw.getText(), textTel.getText(), textAdresse.getText(), 
				textVille.getText(), textProvince.getText(), textCodePostal.getText(), textForfait.getText(), clientID);

		//Vérifie la validité de l'information du Crédit
		boolean goodCredit = QueryPersonneLibrary.updateCredit(Integer.parseInt(clientID), textCarte.getText(), 
				textNo.getText(), Integer.parseInt(textExpMois.getText()), Integer.parseInt(textExpAnnee.getText()));

		//Si non respect des contraintes SQL, on affiche un message, sinon on ferme la page
		if(!goodCompte) JOptionPane.showMessageDialog(null, MSG_ERROR_COMPTE, "Erreur Compte" , JOptionPane.ERROR_MESSAGE);
		else if(!goodCredit)  JOptionPane.showMessageDialog(null, MSG_ERROR_CREDIT, "Erreur Credit", JOptionPane.ERROR_MESSAGE);
		else{ 
			//Si tout est valide, demande la confirmation de sauvegarde des info Client
			int reponse = JOptionPane.showConfirmDialog(null, MSG_UPDATE, "Update Client", JOptionPane.YES_NO_OPTION);
			if(reponse == JFileChooser.APPROVE_OPTION){ 
				this.dispose();
				System.out.println(MSG_APPROUVE);
			}
		}
	} 

	/******************************************************
	 * Initialise Titled Border
	 * 
	 * @Resumer:	Permet d'initialiser un nouveau
	 * 				TitledBorder pour les différentes
	 * 				section du UI. Permet principalement
	 * 				d'éviter la duplication de Code !
	 * 
	 ******************************************************/ 
	private TitledBorder initTitledBorder(String titre){
		TitledBorder border = new TitledBorder(titre);
		border.setBorder(BorderFactory.createLineBorder(Color.black));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(fontGras);	
		return border;
	}

	/******************************************************
	 * Key Typed
	 * 
	 * @Resumer:	Supporte la possibilité d'entrer
	 * 				seulement des chiffres ou des lettres 
	 * 				dans certains JTextLabel spécifiques.
	 * 				
	 ******************************************************/
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		
		//Pour seulement supporter l'entrée de chiffres.
		if(e.getSource() == textNo || e.getSource() == textExpMois || e.getSource() == textExpAnnee){
			if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
				e.consume();
		}
		//Pour seulement supporter l'entrée de lettres.
		else if(e.getSource() == textPrenom || e.getSource() == textNomFamille || e.getSource() == textForfait || 
				e.getSource() == textVille || e.getSource() == textProvince || e.getSource() == textCarte){
			if (!(Character.isAlphabetic(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
				e.consume();
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
