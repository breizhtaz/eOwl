package eOwl.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import javax.naming.TimeLimitExceededException;

import eOwl.etiquette.ListeFichier;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;
import eOwl.serveur.Serveur;
import eOwl.serveur.Trame;

public class isNotReady extends EtatClient {
	/**Constructeur d'une nouvelle instance de isNotReady
	 * 
	 * @param client
	 */
	public isNotReady(Client client) {
		super(client);
		System.out.println(toString());

	}

	public isNotReady(Client client, InetAddress ip) {
		super(client);
		System.out.println(toString());


	}


	//ne fait rien.
	@Override
	public boolean telechargementPrecis(InetAddress adresse, String nomFichier) {
		return false;
	}

	//ne fait rien
	@Override
	public boolean interrogerProche(String nomFichier) {
		return false;
	}

	// A IMPLEMENTER
	@Override
	public void annulerTelechargement() {
	}

	/**
	 * <code>initialize()<code> Cette methode prend comme argument une IP et un port qu'elle teste pour savoir
	 * si le couple (IP, port) est lie un serveur EOwl fonctionnel.
	 * Si oui, elle renvoie le boolean "true" et elle bascule l'etat du client sur isReady.
	 * Si non, elle renvoie le boolean "false" et l'etat du Client reste sur isNotReady.
	 * 
	 *  * 	 * 
	 * @param ip L'ip du serveur que l'on souhaite affecter comme premier serveur.
	 * @param port Le numero du port auquel on souhaite se connecter au serveur.
	 */	

	public boolean initialize(InetAddress ip) throws IOException{
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		//****creation des elements reseau.*****//
		String reponse = null;
		socket = new Socket();
		try{
			socket.connect(new InetSocketAddress(ip, 5000), 500);
		}catch(IOException e){
			socket.close();
			return false;			
		}


		out = new ObjectOutputStream(socket.getOutputStream());

		//****************************************//

		Trame trame= new Trame(0, "test" , InetAddress.getLocalHost());

		out.flush();
		//on convertit le message 
		out.writeObject(trame);
		//on envoie le message
		out.flush();


		// recuperation des flux



		in = new ObjectInputStream(socket.getInputStream());
		try {
			reponse = (String)in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){

		}


		System.out.println("Reponse: " + reponse);

		out.close();
		in.close();
		socket.close();

		if(reponse != null){
			ListeServeursConnus newliste= new ListeServeursConnus();
			System.out.println("Serveur valide");
			//Le serveur est valide, on peut donc l'ajouter aux Serveurs connus par le Client.
			Serveur newServeur = new Serveur(reponse, ip);



			this.client.setEtatClient(new isDownloading(this.client));

			//On ajoute ce serveur à la liste des serveurs connus, en mettant à jour la liste des fichiers 
			//qu'il contient.
			newServeur.setFichiersRecences(client.getEtatClient().recupererFichierServeur(newServeur.getAdresseIP()));
			client.setEtatClient(new isReady(client));
			newliste.ajouterServeurListe(newServeur);



			this.client.setListeServeursConnus(newliste);			
			return true; 
		}
		else{
			return false;
		}
	}


	@Override
	public String toString() {
		return "isNotReady";
	}

	@Override
	public ListeFichier rechercheEtiquette(String etiquette) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListeServeursConnus recupererServeursProche(InetAddress adresse){
		return null;
	}

	@Override 
	public ListeFichierServeur recupererFichierServeur(InetAddress adresse){
		return null;
	}

	@Override
	public ListeServeursConnus majListeServeursConnus() {
		// TODO Auto-generated method stub
		return null;
	}
}
