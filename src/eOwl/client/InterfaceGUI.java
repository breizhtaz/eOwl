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

	//C'est le tableau contenant les infos sur l'�tat du t�l�chargement
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
	 * M�thode pour qu'apr�s la recherche par mot-clef l'arbre qui s'affiche s'affiche � partir du noeud contenant les mots clefs
	 * @param motclef : filtre de fichier
	 * @return
	 */
	public ListeFichier rechercher_motclef(String motclef){
		this.setChanged();
		this.notifyObservers();

		return this.client.getFichiersClient().filtrerEtiquetteListe(motclef);
	}

	
	
	/**
	 * M�thode qui � partir du nom du serveur et du nom du fichier affiche simplement le fichier concern� dans l'arbre ou dans la liste
	 * @param nom_fichier : nom du fichier recherch�
	 * @return l'infoFichier concern�
	 */
	public InfoFichier recherche_directe(String nom_fichier) {
		return this.client.getFichiersClient().rechercherFichier(nom_fichier);

	}

	
	
	/**
	 * M�thode permettant de mettra � jour la liste apr�s avoir interrog� les serveurs. Ca doit rafra�chir this.liste et this.arbre
	 */
	public void etendre_recherche(){
		//Interroger les serveurs (A COMPLETER)

		this.client.getFichiersClient().updateListeFichierClient(this.client.getListeServeursConnus());
		//j'ai corrig� le code mais ce qui est �crit ne sert � rien
	}

	
	
	/**
	 * M�thode permettant de lancer le t�l�chargement connaissant le fichier.
	 * @param infos : fichier en t�l�chargement
	 */
	//Cette m�thode doit modifier this.table qui est le tableau contenant le nom du fichier en cours de t�l�chargement, 
	//la progression du t�l�chargement et l'�tat du serveur (A COMPLETER)
	public void afficher_telechargement(InfoFichier infos){

	}

	
	/**
	 * M�thode permettant de stopper le t�l�chargement.
	 */
	//Je pense qu'elle devrait faire disparaitre le contenu de this.table pour re-afficher le vide initial et remettre
	//� zero les barres de progression (A COMPLETER)
	public void stop_telechargement(){

	}

	/**
	 * M�thode permettant de changer � partir du nouveau nom entr� l'�tiquette d'un fichier dans la liste et aussi transmettre cette info � l'arbre
	 * @param etiquette : nouvelle �tiquette
	 * @param ancien_nom : fichier � modifier
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
	 * @return le client modifi�
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


