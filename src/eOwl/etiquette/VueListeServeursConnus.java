package eOwl.etiquette;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;


public class VueListeServeursConnus extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListeServeursConnus liste;
	
	//creation d'un JTree pour afficher l'arbre
	private JTree tree;
	
	
	public VueListeServeursConnus (ListeServeursConnus liste_) {
    	super();
    	this.liste = liste_;
    	
    	
    	this.setLayout(new GridLayout());
    	
    	//creation de l'arbre
    	this.tree = new JTree((DefaultMutableTreeNode) this.liste.getArbre().getRoot());
    			
    	this.getContentPane().add(new JScrollPane(this.tree)); //ajout de l'ascenseur
		
    	
    	
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); //cree une fenetre suffisamment grande pour voir tous les composants
		this.setVisible(true);
		this.validate();
		
	} 
	


}