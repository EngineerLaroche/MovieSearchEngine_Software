package lab_2_connexion;
import lab_3_database.QueryPersonneLibrary;

/**************************************************************
 * @CLASS_TITLE:	Authentification
 * 
 * @Description: 	Class that connect to the database and 
 * 					process the authentification of the user. 
 * 					User information are stored into Oracle 
 * 					information.  
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class Authentification{
	
	/******************************
	 * Constante
	 ******************************/
	private static final String 
	ERROR_PASSW = "Le Password entré est invalide !",
	ERROR_EMAIL = "Le Email entré est invalide !",
	ERROR_MISSING = "Information d'Authentification manquante !",
	MSG_TENTATIVE = "(Authentif.) Tentative d'accès -> Email: ",
	MSG_VERIF = "(Authentif.) Vérification de l'information d'authentification en cours ...";

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public Authentification() {} 	

	/******************************************************
	 * Is Valid
	 * 
	 * @Resumer:	Récupère le mot de passe du email entré
	 * 				par le Client et vérifie si c'est valide.
	 * 				
	 ******************************************************/
	public boolean isValid(RSQUser user) {

		if(user != null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()){
			System.err.println(MSG_VERIF);
			QueryPersonneLibrary.findPassw(user.getUsername());

			//Si le Email du Client est bien dans la DB et que le password a été récupéré
			if(SessionFacade.getSession().getResultats().size() == 1){
				printTentatives(user);

				//Si le password entré correspond à celui du email identifié dans la DB
				if(user.getPassword().equals(SessionFacade.getSession().getResultats().get(0))){
					QueryPersonneLibrary.findClientID(user.getUsername());
					return true;	
				}
				else{//Mauvais password entré 
					SessionFacade.getSession().setMsgError(ERROR_PASSW);
					printTentatives(user);
					return false;
				}
			}else{ //Mauvais email entré 
				SessionFacade.getSession().setMsgError(ERROR_EMAIL);
				printTentatives(user);
				return false; 
			}
		}else{//Information manquante
			SessionFacade.getSession().setMsgError(ERROR_MISSING);
			printTentatives(user);
			return false;
		}
	} 
	
	/******************************************************
	 * Print
	 * 
	 * @Resumer:	Imprima dans la console l'information
	 * 				d'authentification du Client.
	 * 				
	 ******************************************************/
	private void printTentatives(RSQUser user){
		System.err.println(MSG_TENTATIVE + user.getUsername() + " & Password: " + user.getPassword());
	}
}