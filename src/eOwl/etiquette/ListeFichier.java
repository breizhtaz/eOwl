package eOwl.etiquette;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.*;

import eOwl.etiquette.InfoFichier;

/* 
 * @author Benjamin Bercovici & Laura Hoinville

 */
public class ListeFichier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Vector<InfoFichier> liste;
	private Arbre arbre;

	/**
	 * <code>ListeFichier()<code> constructeur par defaut de ListeFichier.
	 */
	public ListeFichier(){
		this.arbre = new Arbre(new DefaultMutableTreeNode("racine"));
		this.liste = new Vector<InfoFichier>();
	}



	/**<code>ListeFichier<code>  constructeur de la classe ListeFichier
	 * 
	 * @param arbre: Arbre des etiquettes, representant l'ontologie des etiquettes i.e les liens de parente les reliant.
	 * @param liste: Vecteur de InfoFichier. Chaque ligne de ce vecteur caracterise donc un fichier particulier dans la liste.
	 */
	public ListeFichier(Arbre arbre_, Vector<InfoFichier> liste_) {
		this.arbre = arbre_;
		this.liste = liste_;
	}

	public ListeFichier(DefaultMutableTreeNode racine, Vector<InfoFichier> liste_) {
		this.arbre = new Arbre(racine);
		this.liste = liste_;
	}

	public ListeFichier(Arbre arbre){
		this.arbre = arbre;
		this.liste = new Vector<InfoFichier>();
	}



	/**
	 * <code>ajouterFichierListe(Arbre arbre, Vector<InfoFichier> liste)<code> methode ajoutant un fichier dans le Vector liste, en y ajoutant un objet InfoFichier.
	 * @param nomFichier: le nom du nouveau Fichier. Exemple: "Matrix"
	 * @param etiquetteFichier: l'etiquette caracterisant le nouveau fichier, sous la forme d'une ArrayListe<String>. Exemple: "Film+action+avi". trois etiquettes sont stockees dans l'ArrayList<String> pour generer l'etiquette globale du fichier.
	 * @param ip: l'adresse ip du serveur hebergeant le fichier sous forme InetAddress
	 * @param path: String representant le chemin absolu oï¿½ se trouve le fichier. Exemple: "D:\Part age Chuck\Films\Matrix.avi"
	 */
	public void ajouterFichierListe(String nomFichier, ArrayList<String> etiquetteFichier, InetAddress ip, String path) {
		InfoFichier nouveau = new InfoFichier(nomFichier, etiquetteFichier, ip, path);

		this.liste.add(nouveau);
	}



	/**
	 * <code>ajouterFichierListe(Arbre arbre, Vector<InfoFichier> liste)<code> methode ajoutant un fichier dans le Vector liste, en y ajoutant un objet InfoFichier.
	 * @param nomFichier: le nom du nouveau Fichier. Exemple: "Matrix"
	 * @param etiquetteFichier: l'etiquette caracterisant le nouveau fichier, sous la forme d'une ArrayListe<String>. Exemple: "Film+action+avi". trois etiquettes sont stockees dans l'ArrayList<String> pour generer l'etiquette globale du fichier.
	 * @param ip: l'adresse ip du serveur hebergeant le fichier. Exemple: "10.193.251.252"
	 * @param path: String representant le chemin absolu oï¿½ se trouve le fichier. Exemple: "D:\Part age Chuck\Films\Matrix.avi"
	 */
	public void ajouterFichierListe(String nomFichier, ArrayList<String> etiquetteFichier, String ip, String path) {
		InfoFichier nouveau = new InfoFichier(nomFichier, etiquetteFichier, ip, path);

		this.liste.add(nouveau);
	}


	public ListeFichier ajouterFichierListe(InfoFichier info) {
		this.liste.add(info);
		return this;
	}


	/**
	 * <code>retirerFichierListe(String nomFichier)<code> permet de retirer un fichier de la ListeFichier. On se base sur le nom du fichier pour chercher dans la liste la ligne a supprimer.
	 * @param nomFichier: Le nom du fichier a supprimer
	 */
	public void retirerFichierListe(String nomFichier) {
		/*On connait le nom du fichier a enlever, il faut donc trouver le InfoFichier associe puis
		 * l'enlever de vector<InfoFichier>
		 */

		boolean b = true;

		int i = 0;
		int longueur = this.liste.size();

		while(b && i < longueur){
			if(this.liste.get(i).getNom() == nomFichier){
				b = false;
			}
			i++;
		}

		if(b == false){
			this.liste.remove(i-1);
		}

	}



	/**
	 * <code>retirerEtiquetteArbre(String nomEtiquette)<code> permet de retirer une etiquette (i.e un noeud) de l'arbre. Si l'etiquette detruite a une descendance, la methode gere son rattachement a l'arbre ou bien la destruction de la descendance.
	 * @param etiquette: etiquette - ou noeud de l'arbre -  a supprimer dans l'arbre. Imaginons que l'etiquette "Film" se subdivise en une sous-etiquette "Action" et une sous-etiquette "Drame", et que l'etiquette "Drame" se subdivise en une sous-etiquette "avi" et une sous-etiquette "mp4".
	 * Charge a l'implantation de decider si la suppression de "Drame" fait """remonter""" "avi" et "mp4" au meme rang que "Action", ou si la suppression de "Drame" supprime egalement "avi" et "mp4"
	 */
	public void retirerEtiquetteArbre(String etiquette) {
		/*On recherche le noeud correspondant a l'etiquette fournie a l'aide de la methode
		 * rechercherNoeud de la classe Arbre, puis on l'ote.
		 */

		DefaultMutableTreeNode noeudTemp = this.arbre.rechercherNoeud(this.arbre, (DefaultMutableTreeNode) this.arbre.getRoot(), etiquette);

		this.arbre.removeNodeFromParent(noeudTemp);
	}



	/**<code>ajouterEtiquetteArbre(String nomNouvelleEtiquette, String nomParent)<code> permet de rajouter une etiquette dans l'arbre
	 * 
	 * @param nomNouvelleEtiquette: Le nom de la nouvelle etiquette. Exemple: "avi"
	 * @param nomParent: le nom de l'etiquette parente. Exemple "Drame"
	 */
	public void ajouterEtiquetteArbre(String nomNouvelleEtiquette, String nomParent) {
		/*On recherche le noeud correspondant a l'etiquette parente fournie a l'aide de la methode
		 * rechercherNoeud de la classe Arbre, puis on ajoute l'ï¿½tiquette fille
		 */
		DefaultMutableTreeNode nouvelleEtiquette = new DefaultMutableTreeNode();

		nouvelleEtiquette.setUserObject(nomNouvelleEtiquette);

		DefaultMutableTreeNode noeudTemp = this.arbre.rechercherNoeud(this.arbre, (DefaultMutableTreeNode) this.arbre.getRoot(), nomParent);

		noeudTemp.add(nouvelleEtiquette);
	}


	/**<code>changerEtiquetteListe(ArrayList<String> nouvelleEtiquetteFichier, String nomFichier)<code> 
	 * permet de changer l'etiquette rattachee a un fichier
	 * 
	 * @param nouvelleEtiquetteFichier: la ArrayList<String> representant la nouvelle etiquette du fichier.
	 * @param nomFichier: le nom du fichier dont on souhaite modifier l'etiquette. Exemple: "Matrix"
	 * 
	 */
	public void changerEtiquetteListe(ArrayList<String> nouvelleEtiquetteFichier, String nomFichier) {
		/*On cherche l'InfoFicher correspondant au fichier concernï¿½ dans la liste puis on modifie
		 * l'ï¿½tiquette
		 */

		boolean b = true;

		int i = 0;
		int longueur = this.liste.size();

		while(b && i < longueur){
			if(this.liste.get(i).getNom() == nomFichier){
				b = false;
			}
			i++;
		}

		if(b == false){
			this.liste.get(i-1).setEtiquetteFichier(nouvelleEtiquetteFichier);
		}


	}

	/**<code>rechercherFichier(String nomFichier_)<code> renvoie l'infoFichier rattachee au fichier de nom nomFichier_, s'il se trouve dans le Vector constituant la liste.
	 * 
	 * @param nomFichier_
	 * @return infoFichier l'Objet InfoFichier caracterisant le fichier (Nom, Ip, etiquetteFichier, path).
	 * @return null si le fichier est introuvable (pas de fichier portant ce nom dans la liste).
	 */
	public InfoFichier rechercherFichier(String nomFichier_){

		InfoFichier resultat = null;

		boolean b = true;

		int i = 0;
		int longueur = this.liste.size();

		while(b && i < longueur){
			if(this.liste.get(i).getNom().compareTo(nomFichier_) == 0){
				resultat = this.liste.get(i);
				b = false;
			}
			i++;
		}

		return resultat;

	}

	/**
	 * renvoie le InfoFichier situe au chemin entre
	 * @param path_ : chemin vers le fichier dont on recherche le InfoFichier
	 * @return
	 */
	public InfoFichier rechercherFichierAvecPath(String path_){

		InfoFichier resultat = null;

		boolean b = true;

		int i = 0;
		int longueur = this.liste.size();

		while(b && i < longueur){
			if(this.liste.get(i).getPath().compareTo(path_) == 0){
				resultat = this.liste.get(i);
				b = false;
			}
			i++;
		}

		return resultat;

	}

	/**<code>filtrerEtiquetteListe(String nomEtiquette)<code> permet de filtrer la liste de fichier en ne gardant que 
	 * les fichiers dont l'etiquette globale (i.e une ArrayList<String>) contient une etiquette particuliere.
	 * 
	 * @param etiquette: etiquette recherchee. Exemple: filtrerEtiquetteListe("avi") renverra tous les fichiers dont l'etiquette globale contient "avi": la ArrayList<String> representant l'etiquette globale ( par exemple "[Film;Drame;avi]") sera donc parcourue et les String qu'elle contient seront compares a l'etiquette recherchee.
	 * @return listeTriee: ListeFichier ne contenant que les fichiers retenus.
	 */
	public ListeFichier filtrerEtiquetteListe(String etiquette) {

		ListeFichier listeResultat = new ListeFichier();
		ArrayList<String> arrayListTemp = new ArrayList<String>();

		int i = 0;
		int j = 0;

		if(etiquette.contains(" ET ")){
			//on recupere les deux etiquettes separees => limitation à 2 champs
			int k = 0;
			while(etiquette.charAt(k) != 'E' && etiquette.charAt(k+1) != 'T'){
				k++;
			}
			String champ1 = etiquette.substring(0, k-1);
			champ1 = champ1.toLowerCase();
			String champ2 = etiquette.substring(k+3, etiquette.length());
			champ2 = champ2.toLowerCase();

			//on fait l'iteration sur tous les fichiers
			for(i = 0; i < this.liste.size(); i++){

				arrayListTemp = this.liste.get(i).getEtiquetteFichier();
				int compteur = 0;
				//on fait ensuite l'iteration sur toutes les etiquettes
				j = 0;
				while(j < arrayListTemp.size()){
					//on transforme toutes les string en minuscules pour ne pas avoir de probleme de casse, puis on regarde si le nom ou l'etiquette du fichier contiennent le mot recherche
					if(arrayListTemp.get(j).toLowerCase().contains(champ1) || arrayListTemp.get(j).toLowerCase().contains(champ2) ){
						compteur++; //si on vérifie une des conditions, on incrémente compteur. S'il a trouvé les 2, compteur = 2
					}
					j++;
				}
				if(compteur == 2){
					listeResultat.ajouterFichierListe(this.liste.get(i));				  
				}

			}

		}
		else if(etiquette.contains(" OU ")){
			//on recupere les deux etiquettes separees => limitation à 2 champs
			int k = 0;
			while(etiquette.charAt(k) != 'O' && etiquette.charAt(k+1) != 'U'){
				k++;
			}
			String champ1 = etiquette.substring(0, k-1);
			champ1 = champ1.toLowerCase();
			String champ2 = etiquette.substring(k+3, etiquette.length());
			champ2 = champ2.toLowerCase();

			//on fait l'iteration sur tous les fichiers
			for(i = 0; i < this.liste.size(); i++){

				arrayListTemp = this.liste.get(i).getEtiquetteFichier();

				//on fait ensuite l'iteration sur toutes les etiquettes
				boolean b2 = true;
				j = 0;
				while(j < arrayListTemp.size() && b2){
					//on transforme toutes les string en minuscules pour ne pas avoir de probleme de casse, puis on regarde si le nom ou l'etiquette du fichier contiennent le mot recherche
					if(arrayListTemp.get(j).toLowerCase().contains(champ1) || arrayListTemp.get(j).toLowerCase().contains(champ2)  ){
						listeResultat.ajouterFichierListe(this.liste.get(i));
						b2 = false;
					}
					j++;
				}

			}
		}
		else{

			//on fait l'iteration sur tous les fichiers
			for(i = 0; i < this.liste.size(); i++){
				arrayListTemp = this.liste.get(i).getEtiquetteFichier();

				//on fait ensuite l'iteration sur toutes les etiquettes
				boolean b2 = true;
				j = 0;
				while(j < arrayListTemp.size() && b2){
					//on transforme toutes les string en minuscules pour ne pas avoir de probleme de casse, puis on regarde si le nom ou l'etiquette du fichier contiennent le mot recherche
					if(arrayListTemp.get(j).toLowerCase().contains(etiquette.toLowerCase())){
						listeResultat.ajouterFichierListe(this.liste.get(i));
						b2 = false;
					}
					j++;
				}

			}
		}


		return listeResultat;
	}

	/**<code>getListe()<code> Accesseur sur l'attribut list de ListeFichier
	 * 
	 * @return this.liste Le Vector de InfoFichier associe a cette instance de ListeFichier.
	 */

	public Vector<InfoFichier> getListe() {
		return this.liste;
	}

	/**<code>getArbre()<code> Accesseur sur l'attribut Arbre de ListeFichier
	 * 
	 * @return this.arbre L'ontologie d'etiquette associee a cette instance de ListeFichier
	 */
	public Arbre getArbre(){
		return this.arbre;
	}

	/**Modifieur sur la liste
	 * 
	 * @param newliste
	 */
	public void setListe(Vector<InfoFichier> newliste){
		this.liste=newliste;
	}


}