package eOwl.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * <code>Interface</code> est une classe permettant de stocker des objets
 * de type <code>String</code>.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */

public class Interface extends Observable {

	private ArrayList<String> chaines;

	/**
	 * Creer une instance de <code>Interface</code>.
	 *
	 */

	public Interface() {
		this.chaines = new ArrayList<String>();
	}

	/**
	 * <code>ajouter</code> une chaine de caracteres.
	 *
	 * @param s l'instance de <code>String</code> a ajouter
	 */

	public void ajouter(String s) {
		this.chaines.add(s);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Obtenir la derniere chaine entree.
	 *
	 * @return une instance de <code>String</code> representant
	 *         la derniere chaine entree
	 */

	public String getLast() {
		return this.chaines.get(this.chaines.size() - 1);
	}
}
