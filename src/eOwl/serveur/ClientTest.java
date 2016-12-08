package eOwl.serveur;


import java.net.InetAddress;
import java.net.UnknownHostException;

import eOwl.client.*;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;


/**
 *  <code> ClientTest </code> permet de tester les interactions entre le client et le serveur
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 3.0
 */
public class ClientTest {

	
	public static void main(String[] args) {
		Client client= new Client("test");
		
		/**
		 * Variable permettant de choisir le cas d'utilisation
		 * 1 permet de tester l'envoie d'une trame via la fonction telechargementPrecis
		 * 2 test l'envoie d'un fichier via la fonction telechargementPrecis
		 * 3 test l'envoie d'une liste via la fonction telechargementViaListe
		 * 4 test la recuperation de la liste des serveurs connus
		 */
		int cas = 2;
		switch(cas){
		case 1:
			client.setEtatClient(new isDownloading(client));
			
			//Pause d'une seconde
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Demande telechargement");
			client.getEtatClient().telechargementPrecis(client.getAdresseIP(), "test.txt");
			System.out.println("methode effectue");
		    break;
		case 2:
			client.setEtatClient(new isDownloading(client));
			client.getEtatClient().telechargementPrecis(client.getAdresseIP(), "09_smallRADIO_-_Leaf_Shaped_Feelings_-_7th_Gear_Remix.mp3");
			break;
		case 3:
			ListeFichierServeur lf;
			client.setEtatClient(new isDownloading(client));
			//On recupere la liste des fichier du serveur
			lf = client.getEtatClient().recupererFichierServeur(client.getAdresseIP());
			
			System.out.println(lf.getListe().get(0).toString());
			break;
		case 4:
			ListeServeursConnus ls;
			client.setEtatClient(new isDownloading(client));
			//On recupere la liste des serveurs connus
			ls=client.getEtatClient().recupererServeursProche(client.getAdresseIP());
	
			System.out.println(ls.getListe().get(0).getNom());
			break;
		default:
			break;	
		}
	}
}
