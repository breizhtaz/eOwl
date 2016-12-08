package eOwl.serveur;

import eOwl.etiquette.*;

import java.io.IOException;
import java.net.InetAddress;

/**<code>ServeurIsNotReady</code>
 * 
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 1.0
 */
public class ServeurIsNotReady extends EtatServeur {

	/**
	 * Constructeur de <code>ServeurIsNotReady</code>
	 * @param serveur_ un <code>Serveur</code> 
	 */
	public ServeurIsNotReady(Serveur serveur_){
		super(serveur_);
		System.out.println(toString());
	}
	
	@Override
	public boolean envoyerFichier(String reference, InetAddress client) {
		return false;
	}

	@Override
	public boolean envoyerListeFichiers(InetAddress client){
		return false;
	}
	
	@Override
	public boolean interrogerServeursConnus(InetAddress client) {
		return false;
	}
	
	@Override
	public Trame ecoute() {
		return null;
	}
	

}
