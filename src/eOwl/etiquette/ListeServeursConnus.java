package eOwl.etiquette;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import eOwl.serveur.Serveur;

/*
 * @author Benjamin Bercovici et Laura Hoinville
 */
public class ListeServeursConnus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private  Vector<Serveur> liste;
	private  Arbre arbre;

	/**
	 * <code>ListeServeursConnus()<code> cree une nouvelle instance VIDE de la classe ListeServeursConnus
	 */
	public  ListeServeursConnus() {
		this.liste = new Vector<Serveur>();
		this.arbre = new Arbre(new DefaultMutableTreeNode("racine"));

	}



	/**<code>ajouterServeurListe<code> ajoute un serveur a la liste des Serveurs connus
	 * on cree aussi un noeud dans l'arbre des serveurs connus
	 * @param nouveauServeur: serveur que l'on veut ajouter a la liste.
	 */
	public void ajouterServeurListe(Serveur nouveauServeur){
		this.liste.add(nouveauServeur);
		
		
		DefaultMutableTreeNode resul = (DefaultMutableTreeNode) this.arbre.getRoot();
		
		//creation d'un noeud correspondant au nouveau serveur.
		//on rattache ce noeud à la racine de l'arbre des fichiersServeur, et on lui donne le nom du serveur
		DefaultMutableTreeNode noeud = (DefaultMutableTreeNode) nouveauServeur.getFichiersRecences().getArbre().getRoot();
		noeud.setUserObject(nouveauServeur.getNom());
		
		resul.add(noeud);
		
	}
	
	
	/**
	 * <code>retirerServeurListeServeursConnus<code> permet de retirer un serveur de la liste des serveurs connus, en rentrant son nom.
	 * @param nomServeur: nom du serveur que l'on souhaite retirer
	 */
	public ListeServeursConnus retirerServeurListeServeursConnus(String nomServeur){
		Serveur temp = null;

		/*On cherche le serveur dans la liste correspondant au nom fourni et on le met dans la variable temporelle temp afin de pouvoir
	  le retirer de la liste hors de la boucle*/
		for(Serveur iter : this.liste){
			if(iter.getNom().compareTo(nomServeur) == 0){
				temp = iter;
			}
		}

		this.liste.remove(temp);
		this.arbre.removeNodeFromParent(this.arbre.rechercherNoeud(this.arbre, (DefaultMutableTreeNode) this.arbre.getRoot(), nomServeur));
		return this;
	}

	public Serveur rechercherServeur(String nomServeur){

		Serveur resultat = null;

		boolean b = true;

		int i = 0;
		int longueur = this.liste.size();

		while(b && i < longueur){
			if(this.liste.get(i).getNom().compareTo(nomServeur) == 0){
				resultat = this.liste.get(i);
				b = false;
			}
			i++;
		}

		return resultat;

	}

	/**<code>getListe()<code> Accesseur sur l'attribut liste de ListeServeurConnus
	 * 
	 * @return le Vector<InfoServeur> contenant les serveurs connus.
	 */
	public Vector<Serveur> getListe() {
		return liste;
	}

	/**<code>setListe(Vector<InfoServeur> liste)<code> Modifieur sur l'attribut liste de ListeServeurConnus
	 * 
	 * @param liste la nouvelle liste.
	 */
	public void setListe(Vector<Serveur> liste) {
		this.liste = liste;
	}


	public Arbre getArbre() {
		return arbre;
	}


	public void setArbre(Arbre arbre) {
		this.arbre = arbre;
	}

}