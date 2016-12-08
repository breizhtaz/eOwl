package eOwl.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.etiquette.*;

/**
 * <code>Interface</code> est une classe permettant de stocker des objets
 * de type <code>String</code>.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */

public class InterfaceGUI extends Observable {

	private Client client;
	private ListeFichier listeFichier;

	//C'est le tableau contenant les infos sur l'état du téléchargement
	private JTable table;

	/**
	 * Creer une instance de <code>Interface</code>.
	 *
	 */
	public InterfaceGUI(String nom_) {
		this.client = new Client(nom_);
		this.setListeFichier(this.client.getFichiersClient());

	}

		/**
	 * Méthode pour qu'après la recherche par mot-clef l'arbre qui s'affiche s'affiche à partir du noeud contenant les mots clefs
	 * @param motclef : filtre de fichier
	 * @return
	 */
	public ListeFichier rechercher_motclef(String motclef){
		this.setChanged();
		this.notifyObservers();

		return this.client.getFichiersClient().filtrerEtiquetteListe(motclef);
	}

	
	
	/**
	 * Méthode qui à partir du nom du serveur et du nom du fichier affiche simplement le fichier concerné dans l'arbre ou dans la liste
	 * @param nom_fichier : nom du fichier recherché
	 * @return l'infoFichier concerné
	 */
	public InfoFichier recherche_directe(String nom_fichier) {
		return this.client.getFichiersClient().rechercherFichier(nom_fichier);

	}

	
	
	/**
	 * Méthode permettant de mettra à jour la liste après avoir interrogé les serveurs. Ca doit rafraîchir this.liste et this.arbre
	 */
	public void etendre_recherche(){
		//Interroger les serveurs (A COMPLETER)

		this.client.getFichiersClient().updateListeFichierClient(this.client.getListeServeursConnus());
		//j'ai corrigé le code mais ce qui est écrit ne sert à rien
	}

	
	
	/**
	 * Méthode permettant de lancer le téléchargement connaissant le fichier.
	 * @param infos : fichier en téléchargement
	 */
	//Cette méthode doit modifier this.table qui est le tableau contenant le nom du fichier en cours de téléchargement, 
	//la progression du téléchargement et l'état du serveur (A COMPLETER)
	public void afficher_telechargement(InfoFichier infos){

	}

	
	/**
	 * Méthode permettant de stopper le téléchargement.
	 */
	//Je pense qu'elle devrait faire disparaitre le contenu de this.table pour re-afficher le vide initial et remettre
	//à zero les barres de progression (A COMPLETER)
	public void stop_telechargement(){

	}

	/**
	 * Méthode permettant de changer à partir du nouveau nom entré l'étiquette d'un fichier dans la liste et aussi transmettre cette info à l'arbre
	 * @param etiquette : nouvelle étiquette
	 * @param ancien_nom : fichier à modifier
	 */
	//pour qu'il affiche le fichier au bon endroit
	public void changer_etiquette(String etiquette, String ancien_nom){
		ArrayList<String> nouvelleEtiquetteFichier = new ArrayList<String>();
		nouvelleEtiquetteFichier.add(etiquette);
		this.client.getFichiersClient().changerEtiquetteListe(nouvelleEtiquetteFichier, ancien_nom);
	}		



	/**
	 * Methode permettant d'acceder a l'attribut table
	 * @return table l'attribut table de la classe
	 */
	public JTable get_table(){
		return this.table;
	}

	
	/**
	 * Methode permettant de modifier le tableau contenant les infos sur le fichier en cours
	 * de telechargement (nom du fichier, progression du telechargement, etat du serveur)
	 * @param table_ le tableau mis a jour
	 */
	public void set_table(JTable table_){
		this.table = table_;
	}
	

	/** Getter du client
	 * @return le client
	 */

	public Client getClient() {
		return client;
	}

	
	/** Setter du client : le modifie
	 * @return le client modifié
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	public ListeFichier getListeFichier() {
		return listeFichier;
	}

	public void setListeFichier(ListeFichier listeFichier) {
		this.listeFichier = listeFichier;
	}

}


