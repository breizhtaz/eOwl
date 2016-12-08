package eOwl.etiquette;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;


public class VueListeFichierServeur extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListeFichierServeur liste;
	
	//creation d'un JTree pour afficher l'arbre
	private JTree tree;
	
	//creation d'un tableau pour afficher ListeFichierClient
	private JTable area;
	
	public VueListeFichierServeur (ListeFichierServeur liste_) {
    	super();
    	this.liste = liste_;
    	
    	//on cree une layout de dimension 1*2 pour accueillir nos deux elements
    	this.setLayout(new GridLayout(1,2));
    	
    	//creation de l'arbre => on recupere les infos directement de this.liste.getArbre();
    	this.tree = new JTree((DefaultMutableTreeNode) this.liste.getArbre().getRoot());
    			
    	this.getContentPane().add(new JScrollPane(this.tree)); //ajout de l'ascenseur
		
    	
    	//creation d'un tableau de n lignes et 4 colonnes (n : nbe de lignes de listeFichierClient)
        this.area = new JTable(this.liste.getListe().size(), 4);

        //remplissage du tableau : 
    	for(int i = 0; i < this.liste.getListe().size();i++){
        	this.area.setValueAt(this.liste.getListe().get(i).getNom(), i, 0); //colonne 1 : nom du fichier
        	this.area.setValueAt(this.liste.getListe().get(i).getEtiquetteFichier(), i, 1);//colonne 2: etiquette
        	this.area.setValueAt(this.liste.getListe().get(i).getPath(), i, 2);//colonne 3 : chemin du fichier
        	this.area.setValueAt(this.liste.getListe().get(i).getIp(), i, 3);//colonne 4 : adresse IP du serveur
       	}
    	this.getContentPane().add(new JScrollPane(this.area)); //et ajout d'un ascenseur

    	this.area.setShowHorizontalLines(true);//affichage de lignes horizontales dans le tableau
    	
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); //cree une fenetre suffisamment grande pour voir tous les composants
		this.setVisible(true);
		this.validate();
		System.out.println("creation du vuelistefichierserv");
		
	} 
	


}
