package eOwl.serveur;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import eOwl.etiquette.*;

/**<code>ServeurIsUploading</code>
 * 
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 1.0
 */
public class ServeurIsUploading3 extends EtatServeur {

	/**
	 * Constructeur de <code>ServeurIsUploading</code>
	 * @param serveur_ un <code>Serveur</code> 
	 */
	public ServeurIsUploading3(Serveur serveur_){
		super(serveur_);
		System.out.println(toString());
	}
	
	@Override
	public boolean envoyerFichier(String reference, InetAddress client) {

		ServerSocket serverSocket;
		Socket socket;
		String message = null;
		ObjectInputStream in;
		BufferedOutputStream outBuffer;
		
		//Tampon qui sert lors de la lecture du fichier
		BufferedInputStream inBuffer;
		
		
		try{
			//On ouvre la ServerSocket pour la communication
			serverSocket = new ServerSocket(5000);
			//On accepte la communication avec le client
			socket = serverSocket.accept();
			//on initialise in
			in = new ObjectInputStream(socket.getInputStream());
			
			File tosend = new File(reference, "r"); //Ouverture du fichier
			//on associe le Buffer au fichier correspondant a la reference
			inBuffer = new BufferedInputStream(new FileInputStream(tosend));
			//on cree un tableau de taille 1024
			byte[] tableau = new byte[1024];
			//on associe le outBuffer a un ByteArrayOutputStream
			outBuffer = new BufferedOutputStream(new ByteArrayOutputStream());
			
			int nbreOctetsLus = 0;
			
			/* On insere les donnees du fichier dans le tableau jusqu'a ce que le nombre d'octets lus corresponde a -1, 
			 * c'est-a-dire qu'on arrive a la fin du fichier
			 */
			while((nbreOctetsLus = inBuffer.read(tableau)) != -1){
	            outBuffer.write(tableau,0,1024);
	         }
			
			//attente de confirmation de reception de la part du client
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){}
			}while(message.equals("END")==false);
			
		//Fermeture des flux et sockets
		inBuffer.close();
		outBuffer.close();
		in.close();
		socket.close();
		serverSocket.close();
		
		return true;
		
		}catch(IOException io){
		}
		
		return false;
	}
	
	@Override
	public boolean envoyerListeFichiers(InetAddress client){
		ServerSocket serverSocket;
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		String message = null;
		ListeFichier tosend;
		
		try{
			//On ouvre la ServerSocket pour la communication
			serverSocket = new ServerSocket(5000);
			//On accepte la communication avec le client
			socket = serverSocket.accept();
			
			//ouverture des flux
			out = new ObjectOutputStream(socket.getOutputStream());
			in =new ObjectInputStream(socket.getInputStream());
			//on vide le tampon avant de mettre en tampon notre fichier a envoyer
			out.flush();
			
			//On envoie au client qu'on est pret a envoyer le fichier
			out.writeObject("OK");
			out.flush();
			
			//creation de la liste
			tosend = this.serveur.getFichiersRecences();
			out.writeObject(tosend);
			out.flush();
			
			//attente de confirmation de reception de la part du client
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){}
			}while(message.equals("END")==false);
			
		//Fermeture des flux et sockets
		out.close();
		in.close();
		serverSocket.close();
		socket.close();
		
		return true;
		}catch(IOException io){
		}
		
		return false;
	}

	@Override
	public boolean interrogerServeursConnus(InetAddress client) {
		ServerSocket serverSocket;
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		String message = null;
		ListeServeursConnus tosend;
		
		try{
			//On ouvre la ServerSocket pour la communication
			serverSocket = new ServerSocket(5000);
			//On accepte la communication avec le client
			socket = serverSocket.accept();
			
			//ouverture des flux
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			//on vide le tampon avant de mettre en tampon notre fichier a envoyer
			out.flush();
			
			//On envoie au client qu'on est pret a envoyer le fichier
			out.writeObject("OK");
			out.flush();
			
			//creation de la liste
			tosend = this.serveur.getServeursLies();
			out.writeObject(tosend);
			out.flush();
			
			//attente de confirmation de reception de la part du client
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){}
			}while(message.equals("END")==false);
			
		//Fermeture des flux et sockets
		out.close();
		in.close();
		serverSocket.close();
		socket.close();
		
		return true;
		}catch(IOException io){
		}
		
		return false;
	}
	
	@Override
	public Trame ecoute() {
		return null;
	}
}
