package eOwl.etiquette;

import java.io.Serializable;

import javax.swing.tree.*;

/**
 * Classe Arbre heritee de DefaultTreeModel : on rajoute une methode de recherche de noeud
 * @author Clément
 *
 */
public class Arbre extends DefaultTreeModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructeur de l'arbre. 
	 * @param racine : de la forme DefaultMutableTreeNode, c'est le noeud de base de l'arbre
	 */
	public Arbre(DefaultMutableTreeNode racine){
		super(racine);
	}
	
	
	/**
	 * Recherche un noeud et le renvoie. Methode iterative
	 * @param arbre : l'arbre dans lequel on fait la rechercher
	 * @param noeud : le noeud de depart
	 * @param noeudRecherche : le nom du noeud recherche (string)
	 * @return
	 */
	public DefaultMutableTreeNode rechercherNoeud(Arbre arbre, DefaultMutableTreeNode noeud, String noeudRecherche){
		/*Declaration des variables necessaires : resul pour renvoyer le Noeud trouve,
		 * cc qui compte le nombre de fils d'un noeud, i pour l'iteration
		 */
		DefaultMutableTreeNode resul = new DefaultMutableTreeNode();
	    int  cc  = arbre.getChildCount(noeud);
	    int i = 0;

		if(arbre.getRoot().toString() == noeudRecherche){
			resul = (DefaultMutableTreeNode) arbre.getRoot();
		}
	    
	    
	    while(i < cc && resul.getUserObject() != noeudRecherche){
	    	
	      DefaultMutableTreeNode noeudFils = (DefaultMutableTreeNode) arbre.getChild(noeud, i );

	      if (noeudFils.getUserObject() == noeudRecherche ){
	    	  resul = noeudFils;
	      }
	      else {
	        resul = rechercherNoeud(arbre, noeudFils, noeudRecherche);
	      }
	      i++;
	      
	     }
	    
  	  return resul;
	  }
	
	/**
	 *  Regarde si l'arbre est vide ou non
	 * @return booleen : vrai si l'arbre est vide, faux sinon
	 */
	public boolean isEmpty(){
		boolean b;
		
		if(this.root.getChildCount() == 0){
			b = true;
		}
		else{
			b = false;
		}
		
		return b;
	}
	  
}