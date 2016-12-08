package eOwl.serveur;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import eOwl.etiquette.*;

/**<code>EtatServeur</code>
 * 
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 1.0
 */

public class ServeurIsWaiting extends EtatServeur {

	/**
	 * Constructeur de <code>ServeurIsWaiting</code>
	 * @param serveur_ un <code>Serveur</code> 
	 */
	public ServeurIsWaiting(Serveur serveur_){
		super(serveur_);
		System.out.println(toString());
	}
	
	@Override
	public boolean envoyerFichier(String reference, InetAddress client) {
		return false;
	}
	
	@Override
	public boolean envoyerListeFichiers(InetAddress client){
		return false;
	}

	@Override
	public boolean interrogerServeursConnus(InetAddress client) {
		return false;
	}
	
	@Override
	public Trame ecoute() {
		ServerSocket serverSocket;
		Socket socket;
		ObjectInputStream in;
		ObjectOutputStream out;
		String message = null;
		Trame trame= new Trame();
		try{
			//On ouvre la ServerSocket pour la communication
			serverSocket = new ServerSocket(5000);
			//On accepte la communication avec le client
			socket = serverSocket.accept();
			System.out.println("Connexion accepte");
			in = new ObjectInputStream(socket.getInputStream());
			try{
				trame = (Trame) in.readObject();
				System.out.println("Trame recu operation : " + trame.getOperation());
				}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			
			
			// Pour l'initialisation		
			if (trame.getOperation() == 0){
				try{
					//ouverture des flux
					out = new ObjectOutputStream(socket.getOutputStream());
					//on vide le tampon avant de mettre en tampon notre fichier a envoyer
					out.flush();
					//On envoie au client qu'on est pret a envoyer le fichier
					out.writeObject(this.serveur.getNom());
					out.flush();
					out.close();
					
					System.out.println("Client initialise");
				}catch(IOException io){
				}
			}
			
			//On verifie que la trame est bien valide
			if ((trame.getOperation() > - 1 )&& (trame.getOperation() < 4)){
				System.out.println("Trame valide");
			}
			
		

			in.close();
			socket.close();
			serverSocket.close();
			in.close();
			socket.close();
			serverSocket.close();
			
			
		}catch(IOException io){}
		

		return trame;
		
	}
	
	
	/** <code>annulerTelechargement<code> permet au client de stopper le telechargement 
	 * 
	 */
	public void annulerTelechargement(){
	}

}
