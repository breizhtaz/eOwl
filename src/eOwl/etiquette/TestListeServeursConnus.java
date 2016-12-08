package eOwl.etiquette;

import java.io.File;
import java.net.UnknownHostException;

import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.serveur.Serveur;

public class TestListeServeursConnus {
	public static void main(String[] args) throws UnknownHostException{

		//creation d'une liste de fichier serveurs avec creation de l'arbre
		ListeServeursConnus liste = new ListeServeursConnus();
	
		//File file = new File("C:\\Users\\Laura\\Desktop\\Mes vidéos");
		Serveur s1 = new Serveur("clem");
		File file = new File("C:\\Users\\Clément\\Desktop\\eOwl");
		s1.setFichiersRecences(s1.getFichiersRecences().miseAJourListe(file.toString(), null));
		
		
		Serveur s2 = new Serveur("Annie");
		File file2 = new File("D:\\chuck");
		s2.setFichiersRecences(s2.getFichiersRecences().miseAJourListe(file2.toString(), null));
			
		liste.ajouterServeurListe(s1);
		liste.ajouterServeurListe(s2);
		
		//creation d'une vue complete
		VueListeServeursConnus vue = new VueListeServeursConnus(liste);
		
	}

}
