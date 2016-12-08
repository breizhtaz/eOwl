package eOwl.serveur;

import java.io.Serializable;
import java.net.InetAddress;
import eOwl.client.Client;
import eOwl.etiquette.InfoFichier;

	/**<code>EtatServeur</code>
	 * 
	 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
	 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
	 * @version 1.0
	 */

public abstract class EtatServeur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <code>Serveur<code> est le serveur auquel sera lie la classe abstraite 
	 */
	protected Serveur serveur;

	/**
	 * Constructeur de EtatServeur
	 * @param serveur_ un <code>Serveur</code> qui correspondra au nouveau serveur
	 */
	public EtatServeur(Serveur serveur_){
		this.serveur = serveur_;
	}
	
	/**Getter 
	 * @return le <code>Serveur</code> 
	 */
	public Serveur getServeur(){
		return this.serveur;
	}
	
	/** Envoie le ficher, demande a l'aide d'un chemin precis  d'un fichier,
	 *  au client qui l'a demande
	 * @param reference le<code>String</code> du fichier que le client veut telecharger
	 * @param client l'<code>InetAdress</code> correspondant au client
	 * @return un <code>boolean</code> qui retourne vrai si l'envoi a ete effectue
	 */
	abstract public boolean envoyerFichier(String reference, InetAddress client);

	/**Envoie la liste des serveurs connus au client
	 * @param client un <code>InetAddres</code> correspondant a l'adresse du client
	 */
	abstract public boolean interrogerServeursConnus(InetAddress client);
	
	/**Cette methode <code>ecoute</code> a pour but de mettre le serveur en position
	 * d'attente d'une demande de la part du client. De cette maniere, lorsque le client 
	 * effectue une demande d'envoi quelconque, la methode capte cette demande et genere 
	 * une <code>Trame</code> qui sera transmise au main du serveur. Celui-ci dirigera la demande
	 * vers la methode adaptee
	 * @return la <code>Trame</code> contenant l'ensemble des informations permettant de repondre 
	 * a la demande du client
	 */
	abstract public Trame ecoute();

	/** Envoie la liste des fichiers du serveur
	 * @param client le <code>InetAddress</code> correspondant a l'adresse du client
	 * @return un <code>boolean</code> qui retourne vrai si l'envoi a ete effectue
	 */
	abstract public boolean envoyerListeFichiers(InetAddress client);
	
}
