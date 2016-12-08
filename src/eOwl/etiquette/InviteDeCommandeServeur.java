package eOwl.etiquette;

import java.io.*;
import java.util.*;

import eOwl.serveur.Serveur;
import eOwl.serveur.ServeurIsUploading;
import eOwl.serveur.ServeurIsWaiting;
import eOwl.serveur.Trame;




/**
 * Cette classe permet a un utilisateur de lancer un serveur depuis la console. Celui-ci creera alors un serveur, choisira 
 * un dossier a partager et sauvegardera l'ensembles des informations sur un fichier texte stocke sur le disque dur.
 * @author Clément
 *
 */
public class InviteDeCommandeServeur {
	
	public static void main(String[] args) throws IOException{

		Scanner sc = new Scanner(System.in);
		System.out.println("**********************************************************\n****************** Debut du programme ********************");
		
		System.out.println("liste des actions possibles :\n" +
				"1 - Creation d'un nouveau serveur\n" +
				"2 - Modification du dossier de partage");
	
		String resul = sc.nextLine();

		if(resul.equals("1")){
			System.out.println("************************************Creation d'un nouveau serveur************************************");
			System.out.println("Entrez le nom du nouveau serveur :");
			resul = sc.nextLine();

			Serveur serveur = new Serveur(resul);
			System.out.println("creation du serveur :\n- NOM "+ serveur.getNom() + "\n- IP " + serveur.getAdresseIP());
			
			System.out.println("Choix du repertoire à partager. Entrer l'adresse :");
						
			File file = new File(sc.nextLine());
			
			System.out.println("partage de : "+ file);
			
			//conception de la liste des fichiers.
			serveur.getFichiersRecences().miseAJourListe(file.getPath(), null);
		
			File fichier = new java.io.File("sauvegarde_serveur.txt"); 

			try 
			{ 
				fichier.createNewFile(); 
			} 
			catch (IOException e) 
			{ 
				System.out.println("Impossible de créer le fichier"); 
			} 
							
			PrintWriter out  = new PrintWriter(new FileWriter("sauvegarde_serveur.txt", true));
		    
			out.println(serveur.getNom());
			out.println(serveur.getAdresseIP());
			out.println(file);
			
		    out.close();	
		    
		    
		    /**Le serveur est a l'ecoute des demandes envoyees par le client et selon la requete qu'il recoit,
			 * il se dirige vers le cas d'envoie d'une liste de fichiers (cas 1), d'un fichier (cas 2), 
			 * ou d'une liste de serveurs connus (cas 3) 
			 */
			while(true){
				Trame trame;
				trame = serveur.getEtatServeur().ecoute();
				
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
		else if(resul.equals("2")){
			System.out.println("***************Modification du répertoire*****************************");
			

			
			BufferedReader in = new BufferedReader(new FileReader("sauvegarde_serveur.txt"));
			
			String nom = in.readLine();
			String infoIP = in.readLine();
			
			int i = 0;
			while(i < infoIP.length() && infoIP.charAt(i) != '/' ){
				i++;
			}
			infoIP = infoIP.substring(i, infoIP.length());
			
			
			//recuperation du nouveau répertoire.
			String repertoire = sc.nextLine();
			
			PrintWriter out  = new PrintWriter(new FileWriter("sauvegarde_serveur.txt"));
			out.println(nom);
			out.println(infoIP);
			out.println(repertoire);
			
			
			out.close();
		}
		
		
		
		sc.close();
		
	}


}
