package eOwl.etiquette;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Before;
import org.junit.Test;

import eOwl.etiquette.ListeFichier;

public class ListeFichierTest {
	
	
	private ListeFichier listeFichier;//instance de listeFichier avec un arbre VIDE et une liste de fichier VIDE.
	private ListeFichier  listeFiltre;//instance de ListeFichier qui nous servira dans le dernier test.
	
	ArrayList<String> etiquette = new ArrayList<String>();//Nouvelle etiquette de Fichier, vide.
	ArrayList<String> etiquette2 = new ArrayList<String>();//Nouvelle etiquette de Fichier, vide.
	ArrayList<String> etiquette3 = new ArrayList<String>();//Nouvelle etiquette de Fichier, vide.
	ArrayList<String> etiquette4 = new ArrayList<String>();//Nouvelle etiquette de Fichier, vide.
	
	@Before
	public void setUp() {
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("racine");
		Arbre a = new Arbre(racine);//On cree un arbre VIDE pour le mettre dans listeFichier. (important pour testajouterEtiquetteArbre();
		Vector<InfoFichier> vecteurInfo= new Vector<InfoFichier>();//on cree un Vector pour le mettre dans listeFichier
		
		listeFichier= new ListeFichier(a,vecteurInfo);
		listeFiltre = new ListeFichier();

		
		etiquette.add("Action"); //L'etiquette n'est plus vide: elle contient"Action"
		 
	}
	
	@Test
	public void testajouterFichierListe() {
		assertEquals(listeFichier.getListe().isEmpty(),true);//on s'assure qu'on part bien d'un Vecteur vide.
		listeFichier.ajouterFichierListe("Fichier1", etiquette , "193.255.255.255", "C");//on rajoute un InfoFichier dans la liste.
		assertEquals(listeFichier.getListe().isEmpty(),false);//On verifie que l'ajout a bien fonctionne.
		
		InfoFichier inf = new InfoFichier("Fichier1", etiquette , "193.255.255.255", "C");
		assertEquals(listeFichier.getListe().get(0).getNom(), inf.getNom());
		assertEquals(listeFichier.getListe().get(0).getIp(), inf.getIp());
		assertEquals(listeFichier.getListe().get(0).getPath(), inf.getPath());
		
		
	}
	
	@Test
	public void testretirerFichierListe() {
		listeFichier.ajouterFichierListe("Fichier1", etiquette , "193.255.255.255", "C");//on rajoute un InfoFichier dans la liste.

		
		listeFichier.retirerFichierListe("Fichier1");//On enleve l'InfoFichier relatif au "Fichier1" de la liste.
		assertEquals(listeFichier.getListe().isEmpty(),true);//comme il n'y avait qu'un seul fichier avant l'appel a la methode, si tout va bien le Vecteur est vide.
	}
	
	
	
	@Test
	public void testajouterEtiquetteArbre() {
		
		assertEquals(listeFichier.getArbre().isEmpty(),true);//On verifie que l'arbre est bien vide avant de commencer
		
				
		listeFichier.ajouterEtiquetteArbre("Films", "racine");//On ajoute une branche "Films" a la racine
		assertEquals(listeFichier.getArbre().isEmpty(),false);//On verifie que l'ajout de la racine a ete pris en compte
		assertEquals(listeFichier.getArbre().getChild(listeFichier.getArbre().getRoot(), 0).toString(), "Films");

		listeFichier.ajouterEtiquetteArbre("Jeux","racine");//On ajoute une deuxieme branche "Jeux" a la racine.
		assertEquals(listeFichier.getArbre().getChildCount(listeFichier.getArbre().getRoot()),2);//Ici, on verifie que le NOEUD "ROOT" a bien 2 descendants.
	}
	
	
	@Test
	public void testretirerEtiquetteArbre() {
		listeFichier.ajouterEtiquetteArbre("Films", "racine");//On ajoute une branche "Films" a la racine
		listeFichier.ajouterEtiquetteArbre("Jeux","racine");//On ajoute une deuxieme branche "Jeux" a la racine.
		assertEquals(listeFichier.getArbre().isEmpty(),false);//On verifie que l'arbre n'est pas vide. Pas vraiment obligatoire ici.
		
		listeFichier.retirerEtiquetteArbre("Jeux");//On retire un des descendants de la racine.
		assertEquals(listeFichier.getArbre().getChildCount(listeFichier.getArbre().getRoot()),1);//On verifie que le noeud ROOT n'a plus que un descendant.
	}
	
	@Test
	public void testchangerEtiquetteListe() {
		ArrayList<String> newEtiquette=new ArrayList<String>();//On cree une nouvelle instance ArrayList<String>, pour stocker la nouvelle etiquette.
		newEtiquette.add("Drame");//on definit une nouvelle etiquette pour le fichier. Cette etiquette ne contient que "Drame"
		newEtiquette.add("Horreur");
		
		listeFichier.ajouterFichierListe("Fichier1", etiquette , "193.255.255.255", "C://");
		/*A ce stade de la classe de test, il n'y a aucun InfoFichier dans le vecteur. 
		Donc on en remet un avec une "vieille" etiquette.*/
		
		listeFichier.changerEtiquetteListe(newEtiquette, "Fichier1");
		//On change l'etiquette de l'InfoFichier dont le champ nom vaut "Fichier1".
		
		assertEquals(listeFichier.rechercherFichier("Fichier1").getEtiquetteFichier(),newEtiquette);//AssertEquals entre ArrayLists? Je ne sais pas si ça marche.
	}
	
	@Test
	public void testfiltrerEtiquetteListe() {
		etiquette2.add("drame");
		etiquette2.add("avi");
		
		etiquette3.add("action");
		etiquette3.add("avi");
		
		etiquette4.add("action");
		etiquette4.add("divx");
		
		listeFichier.ajouterFichierListe("Fichier2", etiquette2, "193.255.255.255","C://");
		listeFichier.ajouterFichierListe("Fichier3", etiquette3 , "193.255.255.255", "C");
		listeFichier.ajouterFichierListe("Fichier4", etiquette4,"193.255.255.255", "C");
		
		listeFiltre = listeFichier.filtrerEtiquetteListe("avi");//On récupère la liste des fichiers dont l'étiquette contient avi.
		
		assertEquals(listeFiltre.getListe().isEmpty(),false);//On vérifie que la liste filtree n'est pas vide.
		assertEquals(listeFiltre.getListe().size(),2);//On vérifie qu'il y a bien deux fichiers dans la liste filtree;
		
		assertEquals(listeFiltre.getListe().get(0).getNom(),"Fichier2");//normalement le premier fichier de la liste filtree est "Fichier2".
		assertEquals(listeFiltre.getListe().get(1).getNom(),"Fichier3");//normalement le deuxième fichier de la liste filtree est "Fichier3".
		
	
		//test de la recherche ET
		listeFiltre = listeFichier.filtrerEtiquetteListe("action ET avi");
		assertEquals(listeFiltre.getListe().get(0).getNom(),"Fichier3"); //seul le fichier 3 est bon
		
		//test de la recherche OU
		listeFiltre = listeFichier.filtrerEtiquetteListe("drame OU divx");
		assertEquals(listeFiltre.getListe().get(0).getNom(),"Fichier2"); //on recuperera fichier2 et fichier4
		assertEquals(listeFiltre.getListe().get(1).getNom(),"Fichier4"); 

	}
}
