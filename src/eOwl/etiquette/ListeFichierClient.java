package eOwl.etiquette;

import java.util.Vector;

import eOwl.serveur.Serveur;

/*@author Benjamin Bercovici & Laura Hoinville
 */
public class ListeFichierClient extends ListeFichier {
	/**
	 * <code>ListeFichierClient<code> Cree une nouvelle instance de ListeFichier
	 * @param arbre: Ontologie des etiquettes liees a cette instance de ListeFichier.
	 * @param liste: liste des fichiers clients
	 */
	public ListeFichierClient(Arbre arbre, Vector<InfoFichier> liste) {
		super(arbre, liste);
	}

	public ListeFichierClient(Arbre arbre) {
		super(arbre);
	}


	/**<code>updateListeFichierClient(ListeServeur listeServeur)<code> permet de mettre a jour ListeFichierClient en allant rechercher les listes de fichiers 
	 * heberges sur chacun des serveurs references dans listeServeur
	 * 
	 * @param listeServeur: la liste des serveurs connus.
	 * @return 
	 * @return la ListeFichierClient mise a jour.
	 */
	public ListeFichierClient updateListeFichierClient(ListeServeursConnus listeServeur) {
		ListeFichierServeur temp = null;
		ListeFichierClient listeMAJ = this;

		for(Serveur iter : listeServeur.getListe()){

			temp = iter.getFichiersRecences();
			int i = 0;
			int longueur = temp.getListe().size();

			for(i=0; i<longueur; i++){ // pour chaque serveur, on va ajouter la liste des fichiers disponibles
				if(this.rechercherFichier(temp.getListe().get(i).getNom()) == null) {//si le fichier n'est pas présent dans la liste des fichiers du client

					Vector<InfoFichier> vectorTemp = listeMAJ.getListe();
					vectorTemp.add(temp.getListe().get(i));

					listeMAJ.setListe(vectorTemp);// on l'ajoute

				}
			}

		}

		return listeMAJ;

	}

}