package eOwl.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;

import eOwl.etiquette.*;
import eOwl.client.*;

/**
 *  <code> Serveur </code> represente une machine capable d'un communiquer avec un client
 *   Le serveur est a l'ecoute du client, il peut lui envoyer ses fichiers, sa liste de fichiers,
 *   ou encore sa liste de serveurs connus
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 3.0
 */
public class Serveur extends Machine implements Serializable{
	

	/**
	 * Ensemble des informations fichiers correspondant aux fichiers que la machine Serveur
	 * possede vraiment dans son disque dur
	 */
	private ListeFichierServeur fichiersRecences;

	/**
	 * Ensemble des autres Serveurs connus par la machine Serveur consideree
	 */
	private ListeServeursConnus serveursLies;
	
	/** L'etat du client, celui-ci permet d'acceder a certaines methodes, si elles sont possibles dans 
	 * l'etat considere
	 */
	private EtatServeur etat;
	
	/**
	 * Constructeur par defaut
	 */
	public Serveur(){
		super();
	}
	/**
	 * Constructeur de la classe Serveur
	 * @param nom un <code>String</code> correspondant au nom du serveur
	 */
	public Serveur(String nom) {
		super(nom);
		this.fichiersRecences = new ListeFichierServeur();
		this.serveursLies= new ListeServeursConnus();
		this.etat = new ServeurIsWaiting(this);
	}
	
	/**
	 * Constructeur de la classe Serveur
	 * @param nom un <code>String</code> correspondant au nom du serveur
	 * @param adress_, un <code>String</code> correspondant a l'adressse IP de la machine Serveur
	 */
	public Serveur(String nom, String adress_) {
		super(nom, adress_);
		this.fichiersRecences = new ListeFichierServeur();
		this.serveursLies= new ListeServeursConnus();
		this.etat = new ServeurIsWaiting(this);
	}
	
	/**
	 * Constructeur de la classe Serveur
	 * @param nom un <code>String</code> correspondant au nom du serveur
	 * @param adress_, un <code>InetAddress</code> correspondant a l'adressse IP de la machine Serveur
	 */
	public Serveur(String nom, InetAddress adress_) {
		super(nom, adress_);
		this.fichiersRecences = new ListeFichierServeur();
		this.serveursLies= new ListeServeursConnus();
		this.etat = new ServeurIsWaiting(this);
	}

	/** <code>setEtatServeur</code> permet de changer l'etat de notre serveur de maniere
	 *  a lui autoriser ou non l'acces a certaines methodes
	 *  @param nouvelEtat, un <code>EtatServeur</code> qui correspond au nouvel etat de notre serveur
	 */
	public void setEtatServeur(EtatServeur nouvelEtat){
		this.etat = nouvelEtat;
	}
	
	/**Getter 
	 * @return une <code>ListeFichierServeur</code> contenant l'ensemble des <code>InfoFichier</code> correspondant aux fichiers que le 
	 * <code>Serveur</code> possede vraiment sur son disque dur
	 */
	public ListeFichierServeur getFichiersRecences() {
		return this.fichiersRecences;
	}

	/**Setter 
	 * @param listeFichiersServeur, une <code>ListeFichierServeur</code> avec l'ensemble des <code>InfoFichier</code> correspondant aux 
	 * fichiers que la machine Serveur possede vraiment sur son disque dur
	 */
	public void setFichiersRecences(ListeFichierServeur listeFichiersServeur) {
		this.fichiersRecences = listeFichiersServeur;
	}

	/**Getter
	 * @return une <code>ListeServeursConnus</code>, ensemble des autres Serveurs connus par la machine Serveur consideree
	 */
	public ListeServeursConnus getServeursLies() {
		return this.serveursLies;
	}

	/**Setter
	 * @param listeServeursConnus, une nouvelle <code>ListeServeursConnus</code> avec les Serveurs connus par le serveur 
	 * considere
	 */
	public void setServeursLies(ListeServeursConnus listeServeursConnus) {
		this.serveursLies = listeServeursConnus;
	}
	
	/**Getter
	 * @return un <code>EtatServeur</code>,correspondant a l'etat de notre serveur
	 */
	public EtatServeur getEtatServeur(){
		return this.etat;
	}

	
}
