package eOwl.serveur;

import eOwl.etiquette.InfoFichier;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;

public class ServeurEOWLCasUtilisation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/** Main
		 * 
		 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
		 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
		 * @version 1.0
		 */
		Serveur serveur = new Serveur("Serveur1");
		
		/**
		 * Variable permettant de choisir le cas d'utilisation
		 * 1 permet de tester l'envoi d'une trame via la fonction telechargementPrecis
		 * 2 test l'envoie d'un fichier via la fonction telechargementPrecis
		 * 3 test l'envoie d'une liste via la fonction telechargementViaListe
		 * 4 test la fonction interrogerProche qui envoie la liste de serveurs connus
		 */
		int cas = 2;
		boolean b = true;
		
		Trame trame;
		
		switch(cas){
		case 1:
			while(b == true){

				//recuperation de la trame
				trame = serveur.getEtatServeur().ecoute();
				if(trame.getAdresse().equals(serveur.getAdresseIP()) == true)
					System.out.println("Adresse recu");
				else
					System.out.println("Adresse non recu");
				if(trame.getOperation() == 2)
					System.out.println("Operation recu");
				else
					System.out.println("Operation non recu");
				if(trame.getParam().equals("test")== true){
					System.out.println("Parametre recu");
					b = false;
				}else
					System.out.println("Parametre non recu");
				
				serveur.setEtatServeur(new ServeurIsWaiting(serveur));
			}
		case 2:
			while(b == true){
				trame = serveur.getEtatServeur().ecoute();
					if(trame.getOperation()==2){
						serveur.setEtatServeur(new ServeurIsUploading(serveur));
						serveur.getEtatServeur().envoyerFichier(trame.getParam(), trame.getAdresse());
						System.out.println("Fichier envoye");
						b = false;
					}else{
						System.out.println("Trame non valide");
					}
					serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				}
				
		case 3:
			while(true){
				trame = serveur.getEtatServeur().ecoute();
					if(trame.getOperation()==1){
						ListeFichierServeur lf;
						lf = new ListeFichierServeur();
						lf.ajouterFichierListe(new InfoFichier("toto", null, "12", null));
						serveur.setFichiersRecences(lf);
						
						serveur.setEtatServeur(new ServeurIsUploading(serveur));
						serveur.getEtatServeur().envoyerListeFichiers(trame.getAdresse());
						System.out.println("Liste envoye");
						serveur.setEtatServeur(new ServeurIsWaiting(serveur));
				}
			}
		case 4:
			while(true){
				trame = serveur.getEtatServeur().ecoute();
				if(trame.getOperation()==3){
					//On ajoute le serveur s a la liste des serveurs connus
					ListeServeursConnus l;
					Serveur s = new Serveur("taz");
					l = new ListeServeursConnus();
					l.ajouterServeurListe(s);
					serveur.setServeursLies(l);
					
					serveur.setEtatServeur(new ServeurIsUploading(serveur));
					serveur.getEtatServeur().interrogerServeursConnus(trame.getAdresse());
					System.out.println("Liste serveur envoye");
					serveur.setEtatServeur(new ServeurIsWaiting(serveur));
					
					}
				}
			
		}
	}

}
