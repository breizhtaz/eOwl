package eOwl.client;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.etiquette.Arbre;
import eOwl.etiquette.InfoFichier;
import eOwl.etiquette.ListeFichier;
import eOwl.etiquette.ListeFichierClient;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;
import eOwl.serveur.*;

public class isDownloading extends EtatClient {

	private Serveur serveur;


	/**Constructeur d'une nouvelle instance de isDownloading
	 * 
	 * @param client
	 */
	public isDownloading(Client client){
		super(client);
		System.out.println(toString());
	}





	@Override
	public boolean telechargementPrecis(InetAddress adresse, String nomFichier) {
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		BufferedInputStream inBuffer;
		String message = null;
		
		//Definition de la trame cf eOwl.serveur.Trame
		String rspath = "src\\eOwl\\serveur\\Partage\\";
		Trame trame = new Trame(2,rspath+nomFichier,adresse);

		try{
			//on ouvre la connexion avec l'adresse du serveur et on garde le port 5000 du serveur
			System.out.println("Ouverture socket...");
			socket = new Socket(adresse,5000);
			System.out.println("Socket ouverte");
			//ouverture des flux
			out = new ObjectOutputStream(socket.getOutputStream());
			//on vide le tampon avant de mettre en tampon notre fichier a envoyer
			out.flush();
			//On envoie la trame afin de faire basculer d'etat le serveur 
			out.writeObject(trame);
			out.flush();
			out.close();
			socket.close();

			System.out.println("Trame envoye");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			/*
			 * Transfert du fichier
			 */
			System.out.println("Client: debut du transfert");
			//on reouvre la socket
			socket = new Socket(adresse,5000);
			
			//on ouvre les flux
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			
			//on attend que le serveur est le temps de demarrer le processus de bufferisation
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			inBuffer = new BufferedInputStream(socket.getInputStream());
			System.out.println("Client: 1");
			//on recupere le fichier contenu dans le flux entrant
			String rcpath = "src\\eOwl\\client\\Telecharge\\";
			
			FileOutputStream fos = new FileOutputStream(new File(rcpath+nomFichier));
			BufferedOutputStream ecritureFichier = new BufferedOutputStream(fos);
			System.out.println("Client: 2");
		    byte buf[] = new byte[1024];
	        int n = 0;
	        	ecritureFichier.flush();
	        System.out.println("Client: 2.2");	
	        while(((n=inBuffer.read(buf))!=-1)){
	        	System.out.println("Boucle");
	            ecritureFichier.write(buf,0,n);
	            System.out.println("Boucle1");
	            ecritureFichier.flush();
	            System.out.println("Boucle2");
	            
		    }
	        System.out.println("Client: 3");
			
	        inBuffer.close();
	        ecritureFichier.close();
	        fos.close();
	        
			//on reouvre la socket
	        socket = new Socket(adresse,5000);
	        out  = new ObjectOutputStream(socket.getOutputStream());
	        //on confirme la reception du fichier
	        out.flush();
			out.writeObject("END");
			out.flush();
			System.out.println("Fichier recu");
			
			
			//Fermeture des flux et sockets
			//fos.close();
			//inBuffer.close();
			//ecritureFichier.close();
			out.close();
			socket.close();

			return true;
		} catch(IOException io){
			io.printStackTrace();
		}

		return false;
	}

	@Override
	public ListeServeursConnus recupererServeursProche(InetAddress adresse){
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		String message = null;
		ListeServeursConnus serveurs = new ListeServeursConnus();
		//Definition de la trame cf eOwl.serveur.Trame
		Trame trame = new Trame(3,"",adresse);

		try{
			//on ouvre la connexion avec l'adresse du serveur et on garde le port 5000 du serveur
			System.out.println("Ouverture socket...");
			socket = new Socket(adresse,5000);
			System.out.println("Socket ouverte");
			//ouverture des flux
			out = new ObjectOutputStream(socket.getOutputStream());
			//on vide le tampon avant de mettre en tampon notre fichier a envoyer
			out.flush();
			//On envoie la trame afin de faire basculer d'etat le serveur 
			out.writeObject(trame);

			//Fermeture et reouverture socket/flux
			out.flush();
			out.close();
			socket.close();

			System.out.println("Trame envoye");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			socket = new Socket(adresse,5000);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Socket et flux ouverts");

			//On attend que le serveur soit pret
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){
					cnfe.printStackTrace();
				}
			}while(message.equals("OK")==false);

			//on recupere le fichier contenu dans le flux entrant
			try{
				serveurs = (ListeServeursConnus)in.readObject();		
			} catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
			}

			//on confirme la reception du fichier
			out.writeObject("END");
			out.flush();

			//Fermeture des flux et sockets
			out.close();
			in.close();
			socket.close();

			return serveurs;
		} catch(IOException io){
			io.printStackTrace();
		}

		return null;
	}

	@Override 
	public ListeFichierServeur recupererFichierServeur(InetAddress adresse){
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		File fichier;
		String message = null;
		ListeFichierServeur l = null;
		//Definition de la trame cf eOwl.serveur.Trame
		Trame trame = new Trame(1,"",adresse);

		try{
			//on ouvre la connexion avec l'adresse du serveur et on garde le port 5000 du serveur
			System.out.println("Ouverture socket...");
			socket = new Socket(adresse,5000);
			System.out.println("Socket ouverte");
			//ouverture des flux
			out = new ObjectOutputStream(socket.getOutputStream());
			//on vide le tampon avant de mettre en tampon notre fichier a envoyer
			out.flush();
			//On envoie la trame afin de faire basculer d'etat le serveur 
			out.writeObject(trame);

			//Fermeture et reouverture socket/flux
			out.flush();
			out.close();
			socket.close();

			System.out.println("Trame envoye");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			socket = new Socket(adresse,5000);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Socket et flux ouverts");

			//On attend que le serveur soit pret
			do{
				try{
					message = (String)in.readObject();
				}catch(ClassNotFoundException cnfe){
					cnfe.printStackTrace();
				}
			}while(message.equals("OK")==false);

			//on recupere le fichier contenu dans le flux entrant
			try{
				l = (ListeFichierServeur)in.readObject();		
			} catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
			}

			//on confirme la reception du fichier
			out.writeObject("END");
			out.flush();

			//Fermeture des flux et sockets
			out.close();
			in.close();
			socket.close();

			return l;
		} catch(IOException io){
			io.printStackTrace();
		}

		return null;
	}

	

	//ne fait rien
	@Override
	public boolean interrogerProche(String nomFichier) {
		return false;
	}

	@Override
	public void annulerTelechargement() {
		// TODO Auto-generated method stub

	}



	@Override
	public String toString() {
		return "isDownloading";
	}


	@Override
	public ListeFichier rechercheEtiquette(String etiquette) {
		//On créé la liste qu'on renverra en résultat
		ListeFichier listetemp=new ListeFichierClient(new Arbre(new DefaultMutableTreeNode()));
		//On créé une liste de listeFichier
		ArrayList<ListeFichier> listeliste= new ArrayList<ListeFichier>();
		//Pour chacun des serveurs connus
		for(Serveur s: this.client.getListeServeursConnus().getListe()){
			//on récupère sa liste dans la ArrayList
			listeliste.add(this.client.getEtatClient().recupererFichierServeur(s.getAdresseIP()));
		}
		//On parcourt ensuite cette arrayListe de Liste
		for(ListeFichier l: listeliste){
			//et pour chaque Liste
			for(InfoFichier info: l.getListe()){
				//on ajoute chacun de ses fichiers dans la liste des résultats.
				listetemp.ajouterFichierListe(info);
			}
		}
		//On filtre ensuite la liste temporaire.
		listetemp=listetemp.filtrerEtiquetteListe(etiquette);
		return listetemp;
	}


	@Override
	public boolean initialize(InetAddress ip) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}





	@Override
	public ListeServeursConnus majListeServeursConnus() {
		// TODO Auto-generated method stub
		return null;
	}

}
