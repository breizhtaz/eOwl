package eOwl.client;

import java.io.IOException;
import java.net.InetAddress;

import eOwl.etiquette.InfoFichier;
import eOwl.etiquette.ListeFichier;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;
import eOwl.serveur.Serveur;

public class isReady extends EtatClient  {
	/**Constructeur d'une nouvelle instance de isReady
	 * 
	 * @param client
	 */
	public isReady(Client client){
		super(client);
		System.out.println(this.toString());
	}

	@Override
	public ListeServeursConnus majListeServeursConnus() {
		ListeServeursConnus listeprovisoire= new ListeServeursConnus();
		ListeServeursConnus listefinale= new ListeServeursConnus();

		listeprovisoire=this.client.getListeServeursConnus();
		//pour tous les serveurs connus à l'heure actuelle par le client
		for (Serveur s: listeprovisoire.getListe()){
			//on communique avec eux	
			this.client.setEtatClient(new isDownloading(this.client));
			//Pour ce serveur, on lui demande de renvoyer la liste des serveurs qu'il connait.
			for(Serveur s_ : this.client.getEtatClient().recupererServeursProche(s.getAdresseIP()).getListe()){
				//on ajoute chacun de ces serveurs à la liste finale
				listefinale.ajouterServeurListe(s_);
			}
			//
			this.client.setEtatClient(new isReady(this.client));
		}
		//quand on a interrogé tous les serveurs, on enregistre la nouvelle liste de serveurs.
		this.client.setListeServeursConnus(listefinale);
		return listefinale;

	}

	//Implantation incomplète. 
	@Override
	public boolean telechargementPrecis(InetAddress adresse, String nomFichier) {
		this.client.setEtatClient(new isDownloading(this.client));	
		this.client.getEtatClient().telechargementPrecis(adresse, nomFichier);	
		this.client.setEtatClient(new isReady(this.client));
		return true;
	}






	//Implantation incomplète. 

	@Override
	public boolean interrogerProche(String nomFichier) {
		for (Serveur s: this.client.getListeServeursConnus().getListe()){

			this.client.setEtatClient(new isDownloading(this.client));	
			this.client.getEtatClient().interrogerProche(nomFichier);
			this.client.setEtatClient(new isReady(this.client));
		}

		return false;
	}

	//Implantation incomplète. 	
	@Override
	public void annulerTelechargement() {
	}



	@Override
	public String toString() {
		return "isReady";
	}

	@Override
	public ListeFichier rechercheEtiquette(String etiquette) {
		ListeFichier listeFichierBrute= new ListeFichier();
		ListeFichier listeFichiertri = new ListeFichier();
		ListeFichier listeFichierServeur = new ListeFichier();
		//Pour chaque serveur s connu
		for (Serveur s: this.client.getListeServeursConnus().getListe()){

			//On communique avec s
			this.client.setEtatClient(new isDownloading(this.client));

			//On récupère la liste de ses fichiers grâce à la méthode rechercheEtiquette du WP Etiquette
			listeFichierServeur=this.client.getEtatClient().rechercheEtiquette(etiquette);

			//Pour chaque InfoFichier contenu dans le Vecteur ( qui lui est attribut de la listeFichier), on ajoute son info
			//à la liste globale
			for(InfoFichier fichier: listeFichierServeur.getListe()){
				listeFichierBrute.ajouterFichierListe(fichier);
			}

			//on repasse en mode isReady, et le cas échéant on sort de la boucle si on a interrogé tous les serveurs.
			this.client.setEtatClient(new isReady(this.client));
		}

		//On applique la méthode de tri à la liste brute pour récupérer la liste des fichiers intéressants.
		listeFichiertri=listeFichierBrute.filtrerEtiquetteListe(etiquette);
		return listeFichiertri;
	}

	@Override
	public boolean initialize(InetAddress ip) throws IOException {
		boolean b=false;
		this.client.setEtatClient(new isNotReady(client));
		b=this.client.getEtatClient().initialize(ip);
		this.client.setEtatClient(new isReady(client));		
		this.client.sauvegarderServeursConnus();
		this.client.chargerServeursConnus();
		if(b==true){
			System.out.println("SERVEUR AJOUTE");

		}
		return b;
	}

	@Override
	public ListeServeursConnus recupererServeursProche(InetAddress adresse){
		return null;
	}
	@Override 
	public ListeFichierServeur recupererFichierServeur(InetAddress adresse){
		return null;
	}



}
