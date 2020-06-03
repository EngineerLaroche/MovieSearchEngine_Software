package lab_2_connexion;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

/**************************************************************
 * @CLASS_TITLE:	HeartBeat
 * 
 * @Description: 	Envoi un signal tous les 2 secondes au 
 * 					Socket pour lire en temps réel l'état de
 * 					la connexion. Avise le changement d'état
 * 					aux Labels du Ui.
 * 
 * @Cours:			GTI660-01
 * @Session:		H-2019	
 * 
 **************************************************************/
public class HeartBeat {

	/******************************
	 * Instance Classe
	 ******************************/
	private Timer timer = null;
	
	/******************************
	 * COnstante
	 ******************************/
	private static final int TIMEOUT = (10 * 1000);

	/******************************************************
	 * 					CONSTRUCTEUR
	 ******************************************************/
	public HeartBeat () { timer = new Timer(); }

	/******************************************************
	 * Constructeur
	 * 
	 * @Resumer:	Appel en boucle du Sucket.
	 * 
	 ******************************************************/
	public void start(){
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				isSocketAlive(Connect.getConnect().getAdresse());
			}
		}, 0, 3000);
	}

	/******************************************************
	 * Stop
	 * 
	 * @Resumer:	Fermeture du Timer.
	 * 
	 ******************************************************/
	public void stop(){ timer.purge(); }

	/******************************************************
	 * Is Socket Alive
	 * 
	 * @Resumer:	Creation d'un socket avec les memes 
	 * 				informations de connexion a la DB initiales
	 * 				et on appel cette methode en boucle pour
	 * 				verifier si la connexion est toujours 
	 * 				ouverte.
	 * 
	 ******************************************************/
	private boolean isSocketAlive(Address address) {
		boolean isAlive = false;
		SocketAddress socketAddress = new InetSocketAddress(address.getAddress(), Integer.parseInt(address.getPort()));
		Socket socket = new Socket();

		try {
			socket.connect(socketAddress, TIMEOUT);
			socket.close();
			isAlive = true;	

			Connect.getConnect().fireChangeProperty("connexionOuverte");
		} 
		catch (SocketTimeoutException e) {
			System.err.println("(Heartbeat) SocketTimeout Exception - " + address.toString() + "  " + e.getMessage());
			Connect.getConnect().fireChangeProperty("connexionEnCours");
		} 
		catch (IOException e) {

			if(e.getMessage().equals("No route to host: connect")){
				System.err.println("(Heartbeat) Connexion DB impossible - " + address.toString() + "  " + e.getMessage());
				Connect.getConnect().fireChangeProperty("connexionImpossible");
			}
			if(e.getMessage().equals(address.getAddress())){
				System.err.println("(Heartbeat) Tentative Connexion DB - " + address.toString() + "  " + e.getMessage());
				Connect.getConnect().fireChangeProperty("connexionEnCours");
			}
		}
		return isAlive;
	}
}
