package eOwl.client;

import java.io.IOException;
import java.net.InetAddress;

import eOwl.etiquette.ListeFichier;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;

abstract public class EtatClient {

	/**
	 * <code>client<code> est l'Objet concret liï¿½ ï¿½ cet ï¿½tat abstrait
	 */
	protected Client client;
	/**
	 * Crï¿½ï¿½ une nouvelle instance de l'ï¿½tat
	 * @param client_
	 */
	public EtatClient(Client client_){
		this.client=client_;
	}



	/**
	 * <code>majListe</code> met a jour la liste des actifs Serveurs que connait le client.
	 *
	 *
	 */
	abstract public ListeServeursConnus majListeServeursConnus();


	/**
	 * <code>telechargementPrecis</code> permet d'effectuer le telechargement d'un fichier cible,
	 * stockï¿½ chez un serveur connu. Cette fonction effectue aussi la mise a jour de la liste de fichiers que possede le client.
	 *
	 * @param  nomFichier un <code>String</code> correspondant au nom du fichier a telecharger
	 * @param adresse l'IP du serveur sur lequel on souhaite recuperer le fichier.
	 * @return un <code>Boolean</code> permettant de savoir si le telechargement a ete realise ou non
	 */
	abstract public boolean telechargementPrecis(InetAddress adresse, String nomFichier);

	
	/**
	 * <code>interrogerProche</code> permet de demander aux serveurs connus d'interroger les serveurs qu'il connait
	 * @param  nomFichier un <code>String</code> correspondant au nom du fichier a telecharger
	 * @return un <code>Boolean</code> permettant de savoir si le fichier cherche a ete trouve ou non
	 */
	abstract public boolean interrogerProche(String nomFichier);


	/**
	 * <code>annulerTelechargement()<code> est appellï¿½e lorsqu'on souhaite annuler un tï¿½lï¿½chargement.
	 */
	abstract public void annulerTelechargement();

	abstract public boolean  initialize(InetAddress ip)throws IOException;

	
	
	/**
	 * <code>rechercheEtiquette</code> permet d'effectuer une recherche a l'aide des etiquettes
	 * On demande à tous les serveurs connus de renvoyer leur liste fichier. Le client effectuera alors un tri au sein de la liste reçue. 
	 * @param  etiquette un <code>String</code> correspondant a l'etiquette cherchee
	 * @return une <code>ListeFichier</code> contenant les fichiers associes a ces etiquettes
	 */
	abstract public ListeFichier rechercheEtiquette(String etiquette);

	/**<code>recupererServeursProche</code>
	 * Permet de recuper la liste des serveurs connus d'un serveur
	 * @param adresse un <code>InetAddress</code> correspondant a l'adresse du serveur
	 * @return une <code>ListeServeursConnus</code> correspondant a la liste de serveur connu du serveur interroge
	 */
	abstract public ListeServeursConnus recupererServeursProche(InetAddress adresse);
	
	/**<code>recupererFichierServeur</code>
	 * Permet de recuper la liste des fichier du serveur interroge
	 * @param adresse un <code>InetAddress</code> correspondant a l'adresse du serveur
	 * @return une <code>ListeFichierServeur</code> correspondant a la liste de serveur connu du serveur interroge
	 */
	abstract public ListeFichierServeur recupererFichierServeur(InetAddress adresse);
	
	abstract public String toString();

}

