package eOwl.serveur;

public class ServeurEOWL {

	/** Main
	 * 
	 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
	 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
	 * @version 1.0
	 */
	public static void main(String[] args) {
		Serveur serveur = new Serveur("Serveur de Benjamin");
		
		/**Le serveur est a l'ecoute des demandes envoyees par le client et selon la requete qu'il recoit,
		 * il se dirige vers le cas d'envoie d'une liste de fichiers (cas 1), d'un fichier (cas 2), 
		 * ou d'une liste de serveurs connus (cas 3) 
		 */
		while(true){
			Trame trame;
			trame = serveur.getEtatServeur().ecoute();
			boolean b = false;
			
			switch(trame.getOperation()){	
			case 1:
				serveur.setEtatServeur(new ServeurIsUploading(serveur));
				serveur.getEtatServeur().envoyerListeFichiers(trame.getAdresse());
				serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				break;
				
			case 2:
				serveur.setEtatServeur(new ServeurIsUploading(serveur));
				serveur.getEtatServeur().envoyerFichier(trame.getParam(), trame.getAdresse());
				serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				break;
				
			case 3:
				serveur.setEtatServeur(new ServeurIsUploading(serveur));
				serveur.getEtatServeur().interrogerServeursConnus(trame.getAdresse());
				serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				break;
				
			default:
				serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				break;
			}
		}
	}

}
