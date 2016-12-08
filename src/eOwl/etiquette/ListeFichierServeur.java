package eOwl.etiquette;
import java.util.ArrayList;
import java.util.Vector;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * @author Benjamin Bercovici & Laura Hoinville
 */
public class ListeFichierServeur extends ListeFichier{


	/**
	 * <code>ListeFichierServeur(Arbre arbre, Vector liste)<code> Cree une nouvelle instance de ListeFichierServeur
	 * 
	 * @param arbre: arbre representant l'ontologie des etiquettes c�te serveur.
	 * @param liste: liste des fichiers heberges sur le serveur.
	 */
	public ListeFichierServeur(Arbre arbre, Vector<InfoFichier> liste) {
		super(arbre, liste);
	}

	public ListeFichierServeur() {
		super();
	}

	public ListeFichierServeur(Arbre arbre) {
		super(arbre);
	}

	/**
	 * Determine le repertoire partage par un serveur en s'aidant de la liste des fichiers partages
	 * @return : String representant le chemin vers le repertoire partage
	 */
	public String renvoyerRepertoirePartage(){
		/*
		 * On prend le premier fichier de ListeFichierServeur et on recupere son chemin. Pour chaque etiquette,
		 * on remonte d'un cran dans le repertoire (il a autant d'etiquettes que de dossiers parents cf miseAJourListe)
		 */
		if(this.getListe().size() != 0){
			InfoFichier inf = this.getListe().get(0);
			File cheminRepertoire = null;
			cheminRepertoire = new File(inf.getPath());

			for(int i = 0; i < inf.getEtiquetteFichier().size(); i++){
				cheminRepertoire = cheminRepertoire.getParentFile();
			}

			return cheminRepertoire.toString();
		}
		else{
			return "";
		}
	}


	/**Recuperation de la liste complete des fichiers partages dans le repertoire, et modification de la liste
	 * et de l'arbre (methode iterative)
	 * @param repertoire : repertoire partage par le serveur
	 * @param noeudCourant : fournit le noeud contenant les fichiers que l'on ajoutera (= null au premier appel de la methode)
	 * @return
	 */
	public ListeFichierServeur miseAJourListe(String repertoire, DefaultMutableTreeNode noeudCourant){
		ListeFichierServeur retour = this;

		File file = new File(repertoire);

		//Lors de la premiere iteration
		if(noeudCourant == null){
			noeudCourant = (DefaultMutableTreeNode) retour.getArbre().getRoot();
		}

		//cette operation va ajouter tous les fichiers du repertoire a liste
		File [] listefichiers; 
		listefichiers = file.listFiles(); 

		if(listefichiers != null){
			for(int i=0; i < listefichiers.length ; i++){

				if(listefichiers[i].isDirectory() == false){ //si ce n'est pas un dossier
					DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(listefichiers[i].getName());
					noeudCourant.add(noeud); //creation du noeud et ajout a l'arbre

					DefaultMutableTreeNode noeudTemp = noeudCourant;
					ArrayList<String> etiquette = new ArrayList<String>();

					while(noeudTemp != this.getArbre().getRoot() && noeudTemp != null){
						etiquette.add(noeudTemp.toString());
						noeudTemp = (DefaultMutableTreeNode) noeudTemp.getParent();
					}
					etiquette.add(listefichiers[i].getName());
					try {
						retour.ajouterFichierListe(new InfoFichier(listefichiers[i].getName(), etiquette, InetAddress.getLocalHost(), listefichiers[i].getAbsolutePath()));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
				else{
					//on recupere le nom du dossier qui sera ajoute en etiquette
					String nomDossier = listefichiers[i].getName();
					DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(nomDossier);

					noeudCourant.add(noeud);
					retour.setListe(retour.miseAJourListe(listefichiers[i].getPath(), noeud).getListe());
				}

			}
		}
		return retour;


	}



}