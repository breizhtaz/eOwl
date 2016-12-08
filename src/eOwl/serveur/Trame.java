package eOwl.serveur;

import java.io.Serializable;
import java.net.*;

/**
 *  <code> Trame </code> represente l'objet contenant l'ensemble des informations liees aux requetes
 *  client-serveur. C'est donc l'instance qui resortira de la methode ecoute() de <code>EtatServeur</code>
 * 
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 1.0
 */
public class Trame implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** L'<code> int </code> operation correspond au type de requete qu'effectue le client.
	 * Il peut prendre trois valeurs :
	 * 0 : s'il s'agit d'un test du serveur
	 * 1 : s'il s'agit d'un envoi d'une <code>ListeFichier</code>
	 * 2 : s'il s'agit d'un envoi d'un <code>File</code>
	 * 3 : s'il s'agit de l'envoi de la <code>ListeServeursConnus</code> du <code>Serveur</code> 
	 */
	private int operation;
	
	/** Le <code>String</code> param correspond aux noms ou etiquettes necessaires a la recherche 
	 * de fichiers ou du fichier a telecharger 
	 */
	private String param;
	
	/** adresse, l'<code>InetAddress</code> du client 
	 * 
	 */
	private InetAddress adresse;
	
	public Trame(int operation_, String param_, InetAddress client){
		this.operation = operation_;
		this.param = param_;
		this.adresse = client;
	}

	public Trame() {
		this.operation=-1;
	}

	public int getOperation(){
		return this.operation;
	}
	
	public String getParam(){
		return this.param;
	}
	
	public InetAddress getAdresse(){
		return this.adresse;
	}
}
