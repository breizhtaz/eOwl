package eOwl.etiquette;

import static org.junit.Assert.*;

import javax.swing.tree.*;

import org.junit.*;


/**
 * JUnit test de la classe heritee
 * @author Clément
 *
 */
public class ArbreTest {
	
	private Arbre arbre;
	
	@Before public void SetUp(){
		//creation de la racine de l'arbre
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("racine");
		
		arbre = new Arbre(racine);
		
		//creation des noeuds
		DefaultMutableTreeNode musique = new DefaultMutableTreeNode("musique");
		DefaultMutableTreeNode films = new DefaultMutableTreeNode("films");
		DefaultMutableTreeNode rock = new DefaultMutableTreeNode("rock");
		DefaultMutableTreeNode classique = new DefaultMutableTreeNode("classique");
		
		//creation des liaisons
		racine.add(musique);

		musique.add(rock);
		musique.add(classique);
		racine.add(films);

	}
	

	@Test
	public void testRechercherNoeud() {
	
		assertEquals(arbre.rechercherNoeud(arbre, (DefaultMutableTreeNode) arbre.getRoot(),"racine").getUserObject(),"racine");
		assertEquals(arbre.rechercherNoeud(arbre, (DefaultMutableTreeNode) arbre.getRoot(), "musique").getUserObject(),"musique");
		assertEquals(arbre.rechercherNoeud(arbre, (DefaultMutableTreeNode) arbre.getRoot(), "films").getUserObject(),"films");
		assertEquals(arbre.rechercherNoeud(arbre, (DefaultMutableTreeNode) arbre.getRoot(), "classique").getUserObject(),"classique");
		assertFalse(arbre.rechercherNoeud(arbre, (DefaultMutableTreeNode) arbre.getRoot(), "photos").getUserObject() == "photos");

		
		
	}

}
