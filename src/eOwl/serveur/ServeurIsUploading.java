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
public class ServeurIsUploading extends EtatServeur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de <code>ServeurIsUploading</code>
	 * @param serveur_ un <code>Serveur</code> 
	 */
	public ServeurIsUploading(Serveur serveur_){
		super(serveur_);
		System.out.println(toString());
	}
	
	@Override
	public boolean envoyerFichier(String reference, InetAddress client) {
		ServerSocket serverSocket;
		Socket socket;
		String message = null;
		ObjectOutputStream out;
		ObjectInputStream in;
		BufferedOutputStream outBuffer;
		//Tampon qui sert lors de la lecture du fichier
		BufferedInputStream inBuffer;
			
			
			try{
				System.out.println("Entree dans envoyerFichier");
				
				/*
				 *On demarre le transfert de fichier avec decoupage 
				 */
				
				//on ouvre a nouveau la socket
				serverSocket = new ServerSocket(5000);
				socket = serverSocket.accept();
				System.out.println("Serveur: 1");
				//on ouvre les flux 
				in = new ObjectInputStream(socket.getInputStream());
				System.out.println("Serveur: 2");
				//Ouverture du fichier
				
				File tosend = new File(reference); 
				
				//on associe le Buffer au fichier correspondant a la reference
				System.out.println("the directory exists = " + tosend.exists());
				FileInputStream fis = new FileInputStream(tosend);
				inBuffer = new BufferedInputStream(fis);
				System.out.println("Serveur: 3");
				
				//on cree un tableau de taille 1024
				byte[] tableau = new byte[1024];
				
				//on associe le outBuffer a la socket permettant l'envoi
				outBuffer = new BufferedOutputStream(socket.getOutputStream());
				System.out.println("Serveur: 4");
				int nbreOctetsLus = 0;
				System.out.println("Debut du transfert");
				
				/* On insere les donnees du fichier dans le tableau jusqu'a ce que le nombre d'octets lus corresponde a -1, 
				 * c'est-a-dire qu'on arrive a la fin du fichier
				 */
					outBuffer.flush();
				while((nbreOctetsLus = inBuffer.read(tableau)) != -1){
					System.out.println("nbreOctetsLus:" + nbreOctetsLus);
					outBuffer.write(tableau,0,nbreOctetsLus);
		            outBuffer.flush();
		         }
				
				/* on envoi un dernier message (car lorsqu'on arrive a la fin de l'envoi, il faut que le client 
				 * puisse lui aussi constater que nous sommes arrives au bout du fichier
				 */
				fis.close();
				outBuffer.close();
				inBuffer.close();
				serverSocket.close();
				
				System.out.println("Fin du transfert");
			
			//on reouvre la socket
			serverSocket = new ServerSocket(5000);
			socket = serverSocket.accept();
			in = new ObjectInputStream(socket.getInputStream());
			//attente de confirmation de reception de la part du client
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){}
			}while(message.equals("END")==false);
			
			System.out.println("Reponse recu");
			//Fermeture des flux et sockets
			inBuffer.close();
			//outBuffer.close();
			//in.close();
			serverSocket.close();
			socket.close();
		
			return true;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean envoyerListeFichiers(InetAddress client) {
		ServerSocket serverSocket;
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		String message = null;
		ListeFichierServeur tosend;
		
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
			
			//definition de la liste
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
