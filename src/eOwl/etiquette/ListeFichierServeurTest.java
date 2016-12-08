package eOwl.etiquette;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import javax.swing.tree.*;
import org.junit.*;


public class ListeFichierServeurTest {

	private ListeFichierServeur liste;


	@Before
	public void SetUp() {
		Arbre arbre = new Arbre(new DefaultMutableTreeNode("racine"));
		liste = new ListeFichierServeur(arbre);

		ArrayList<String> a1 = new ArrayList<String>();
		InfoFichier inf = new InfoFichier("BE java", a1, "10.193.245.188", "c:\\Clement\\desktop\\eOwl");

		ArrayList<String> a2 = new ArrayList<String>();
		a2.add("film");
		InfoFichier inf2 = new InfoFichier("monFilm", a2, "10.193.245.188", "c:\\Clement\\desktop\\eOwl\\films");

		liste.ajouterFichierListe(inf);
		liste.ajouterFichierListe(inf2);
	}

	@Test
	public void testRenvoyerRepertoirePartage() {
		assertEquals(liste.renvoyerRepertoirePartage(), "c:\\Clement\\desktop\\eOwl");

	}


	@Test
	public void testMiseAJourListe() {

		//test avec un répertoire de mon ordinateur
		//File file = new File("C:\\Users\\Clément\\Desktop\\eOwl");
		
		File file = new File("ressources");
		
		System.out.println("*******************************");
		liste.setListe(liste.miseAJourListe(file.getPath(), null).getListe());
		
		InfoFichier inf = liste.rechercherFichier("mig.jpg");
		assertEquals(inf.getNom(), "mig.jpg");

		inf = liste.rechercherFichier("Bell_X-1_in_flight.jpg");
		assertEquals(inf.getNom(), "Bell_X-1_in_flight.jpg");

	}


}