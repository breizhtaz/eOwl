package eOwl.etiquette;

import java.net.*;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.serveur.Serveur;

public class TestListeFichierServeur {
	
	public static void main(String[] args) throws UnknownHostException{

		//creation d'une liste de fichier serveurs avec creation de l'arbre
		ListeFichierServeur liste = new ListeFichierServeur(new Arbre(new DefaultMutableTreeNode("racine")));
	
		File file = new File("C:\\Users\\Clément\\Desktop\\eOwl");
		//File file = new File("C:\\Users\\Laura\\Desktop\\Mes vidéos");
		liste.miseAJourListe(file.getPath(), null); //ajout de tous les fichiers situes dans ce repertoire aux fichiers presents sur ce serveur
	
		//creation d'une vue complete
		VueListeFichierServeur vue = new VueListeFichierServeur(liste);
		
	}

}
