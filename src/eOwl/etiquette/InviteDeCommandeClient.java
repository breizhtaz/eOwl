package eOwl.etiquette;

import java.io.*;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;

import eOwl.client.Client;


public class InviteDeCommandeClient {

	public static void main(String[] args) throws IOException{


		System.out.println("**********************************************************\n****************** Debut du programme ********************");
		System.out.println("Entrez le nom du client � cr�er");

		Scanner sc = new Scanner(System.in); //pour recuperer entrees au clavier
		String nomClient = sc.nextLine();
		Client client = new Client(nomClient);


		//chargement des serveurs du txt, puis stockage dans une listeServeursConnus
		client.setListeServeursConnus(client.chargerServeursConnus());

		//ListeServeursConnus chargementServeurs = client.chargerServeursConnus();
		//on test si le serveur est actif. S'il l'est, on l'ajoute aux serveurs connus du client
		/*for(int i = 0; i< chargementServeurs.getListe().size();i++){
			if(chargementServeurs.getListe().get(0) == ){
				System.out.println("serveur dans chargementServeurs: " + chargementServeurs.getListe().get(i).getNom());
				System.out.println("serveur dans chargementServeurs: " + chargementServeurs.getListe().get(i).getAdresseIP());
				client.setListeServeursConnus(client.getListeServeursConnus().ajouterServeurListe(chargementServeurs.getListe().get(i)));

				System.out.println("nom du serveur recupere : " +client.getListeServeursConnus().getListe().get(i).getNom());
				System.out.println("nom du serveur recupere : " +client.getListeServeursConnus().getListe().get(i).getAdresseIP());
			//}
		}*/

		
		//mise a jour de listeFichierClient
		client.setFichiersClient(client.getFichiersClient().updateListeFichierClient(client.getListeServeursConnus()));

		System.out.println("**************************************client initialis�**************************************");

		//debut du fonctionnement nominal
		boolean continuer = true;
		int choix = 0;

		while(continuer){
			System.out.println("****************************************************************************");
			
			System.out.println("Choix : \n"+
					"1 - T�l�chargement pr�cis\n" +
					"2 - Affichage des fichiers connus\n" +
					"3 - Rechercher un fichier \n" +
					"4 - Mettre � jour la liste des fichiers connus \n" +
					"5 - T�l�chargement de proche en proche \n" +
					"0 - quitter \n");

			choix = sc.nextInt();
			sc.nextLine(); //lecture du retour � la ligne


			switch(choix){
			case 1:{ //telecharger le fichier pr�cis
				System.out.println("entrez l'adresse pr�cise du fichier � t�lecharger");
				String nomFichier = sc.nextLine();
				
				//******************************************************
			}break;
			case 2:{ //affichage de la liste

				System.out.println("Affichage de la liste");
				for(int i = 0; i < client.getFichiersClient().getListe().size(); i++){
					System.out.println(client.getFichiersClient().getListe().get(i).toString());
				}
			}break;
			case 3:{ //Recherche �tiquette

				System.out.println("*************************Recherche de fichier*****************************");
				System.out.println("Entrez l'�l�ment de recherche :");
				String recherche = sc.nextLine();

				//recuperation et affichage des resultats
				ListeFichier resul = client.getFichiersClient().filtrerEtiquetteListe(recherche);
				for(int i = 0; i < resul.getListe().size(); i++){
					System.out.println(resul.getListe().get(i).toString());
				}

			}break;
			case 4:{ //Mise � jour de la liste

				//probleme ds le test, le setListeServeursConnus met null ds l'attribut liste de ListeServeursConnus
				client.setListeServeursConnus(client.getEtatClient().majListeServeursConnus());
				client.setFichiersClient(client.getFichiersClient().updateListeFichierClient(client.getListeServeursConnus()));
				
			} break;
			case 5:{ //T�l�chargement de proche en proche

				/*M�me probl�me que pour la fonction pr�c�dente...
				 * 
				 */
			} break;
			case 0:{ //Quitter
				client.sauvegarderServeursConnus();
				continuer = false;
			}break;


			}
			/*
			 * Est-ce qu'il ne serait pas aussi int�ressant de faire un t�l�chargement � partir
			 * d'un nom trouv� dans la liste par la recherche 3?
			 */
			
		}
		sc.close();

	}
}
