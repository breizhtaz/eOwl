package eOwl.client;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.etiquette.*;
import eOwl.serveur.*;


/**
 *  <code> Client </code> correspond aux fonctions que peut realiser l'utilisateur
 * @author <a href="mailto:b.clavell@isae.fr">Benjamin Clavell</a>
 * @author <a href="mailto:g.lever@isae.fr">Gweltaz Lever</a>
 * @version 1.0
 */

public class Client extends Machine{

    
    /**
     * <code>serveursConnus</code> est la liste de serveurs que connait le client.
     *
     */
    private ListeServeursConnus serveursConnus;
    
    /**
     * <code>fichiersClient</code> est la liste des fichiers que possede le client.
     *
     */
    private ListeFichierClient fichiersClient;
    
   
    
    /**
     * <code>etat<code> est l'ï¿½tat actuel du Client.
     */
    private EtatClient etat;
   
	/**
     * Cree une nouvelle instance de <code>Client</code>.
     *
     * @param nom_  un <code>String<Point></code> correspondant au nom du client
     */
    public Client(String nom_) {
    	super(nom_);
    	System.out.println(this.toString());
    	this.serveursConnus=new ListeServeursConnus();
    	this.fichiersClient=new ListeFichierClient(new Arbre(new DefaultMutableTreeNode()), new Vector<InfoFichier>());
    	this.etat= new isNotReady(this);
    }
   
       
	
            
    /**Ajoute l'info fichier associï¿½ ï¿½ un nouveau fichier dans la liste des fichiers du client.
     * 
     * @param nomFichier le nom du fichier que l'on ajoute dans la listeFichierClient
     */
    public void ajouterFichier(String nomFichier){
    	InfoFichier newInfoFichier= new InfoFichier(nomFichier, new ArrayList<String>(), new String(), new String());
    	this.fichiersClient.ajouterFichierListe(newInfoFichier);
    }
    
   /**Ajoute l'info fichier associï¿½ ï¿½ un nouveau fichier dans la liste des fichiers du client.
    * 
    * @param nomFichier Le nom du nouveau fichier
    * @param etiquette La liste d'ï¿½tiquette associï¿½e ï¿½ ce fichier
    */
    public void ajouterFichier(String nomFichier, ArrayList<String> etiquette){
    	InfoFichier newInfoFichier= new InfoFichier(nomFichier, etiquette, new String(), new String());
    	this.fichiersClient.ajouterFichierListe(newInfoFichier);
    }
    
    /**Ajoute l'info fichier associï¿½ ï¿½ un nouveau fichier dans la liste des fichiers du client.
     * 
     * @param nomFichier Le nom du nouveau fichier
     * @param etiquette La liste d'ï¿½tiquette associï¿½e ï¿½ ce fichier
     * @param ip L'IP du serveur hï¿½bergeant le fichier
     */
     public void ajouterFichier(String nomFichier, ArrayList<String> etiquette, String ip){
     	InfoFichier newInfoFichier= new InfoFichier(nomFichier, etiquette, ip, new String());
     	this.fichiersClient.ajouterFichierListe(newInfoFichier);
     }
     
     /**Ajoute l'info fichier associï¿½ ï¿½ un nouveau fichier dans la liste des fichiers du client.
      * 
      * @param nomFichier Le nom du nouveau fichier
      * @param etiquette La liste d'ï¿½tiquette associï¿½e ï¿½ ce fichier
      * @param ip L'IP du serveur hï¿½bergeant le fichier
      * @param path Le chemin absolu du fichier sur le serveur
      */
      public void ajouterFichier(String nomFichier, ArrayList<String> etiquette, String ip,String path){
      	InfoFichier newInfoFichier= new InfoFichier(nomFichier, etiquette, ip,path);
      	this.fichiersClient.ajouterFichierListe(newInfoFichier);
      }


	
	

	/**
     * <code>ajouterEtiquette</code> permet d'ajouter une etiquette a un fichier
     * @param etiquette le <code>String</code> a ajouter
     * @param nomFichier le <code>String</code> correspondant au nom du fichier auquel on ajoute l'etiquette
     * @return un <code>boolean</code> permettant de savoir si l'etiquette a ete associe avec succes
     */
    public boolean ajouterEtiquette(String etiquette, String nomFichier){
    	
    	ArrayList<String> newtag = new ArrayList<String>();
    	
    	//on vï¿½rifie que le fichier portant le nom nomFichier est bien connu du client
    	if(this.fichiersClient.rechercherFichier(nomFichier)!=null){
    		//si c'est le cas, on va le chercher, on modifie sa liste d'ï¿½tiquette qu'on stocke dans une liste provisoire
    		newtag=this.fichiersClient.rechercherFichier(nomFichier).getEtiquetteFichier();
    		newtag.add(etiquette);
    		//on passe ensuite cette liste provisoire en argument de la mï¿½thode changerEtiquetteListe
    		this.fichiersClient.changerEtiquetteListe(newtag,nomFichier);
    	return true;
    	}
    	else{
    		return false;
    	}
 	
	}


    
    
	/**
     * <code>filtrerEtiquette</code> permet de filtrer les etiquettes pour ne garder que celles qui nous interessent a ce moment
     * @param etiquette le<code>String</code> de filtre.
     * @return une <code>Liste</code> contenant les fichiers ranges par sous-etiquettes
     */
    public ListeFichier filtrerEtiquette( String etiquette){
    	return this.fichiersClient.filtrerEtiquetteListe(etiquette);
	}
    
    /**
     * <code>trierEtiquette</code> permet d'effectuer un tri par etiquette en gerant l'arborescence de celles-ci
     * @return une <code>ListeFichier</code> contenant les fichiers ranges par etiquettes
     */
   /* public ListeFichier trierEtiquette(){
    	return null;
	}*/
    
    
	/**
     * <code>getListeServeursConnus</code> permet d'acceder a la liste de serveurs connus
     * @return une <code>ListeServeursConnus</code> 
     */
    public ListeServeursConnus getListeServeursConnus(){
    	return this.serveursConnus;
    }
    
	/**
     * <code>setListeServeursConnus</code> permet de definir la liste des serveurs connus
     * @param  serveursConnus_ une <code>ListeServeursConnus</code> 
     */
    public void setListeServeursConnus(ListeServeursConnus serveursConnus_){
    	this.serveursConnus = serveursConnus_;
    }
    
    
    
    /**<code>getFichiersClient()</code> Accesseur sur l'attribut <code>fichiersClient</code> du Client
     * 
     * @return this.fichiersClient
     */
    public ListeFichierClient getFichiersClient() {
		return fichiersClient;
	}
    
    
    /**<code>setFichiersClient(ListeFichierClient fichiersClient)</code> Modifieur sur l'attribut <code>fichiersClient</code>
     * 
     * @param fichiersClient
     */
	public void setFichiersClient(ListeFichierClient fichiersClient) {
		this.fichiersClient = fichiersClient;
	}
	
	/**Accesseur sur l'ï¿½tat du client
	 * 
	 * @return l'ï¿½tat actuel du client
	 */
	public EtatClient getEtatClient(){
		return this.etat;
	}
	
	
	/**Modifieur sur l'ï¿½tat du client
	 * 
	 * @param etat_ le nouvel ï¿½tat du client
	 */
	public void setEtatClient(EtatClient etat_){
		this.etat=etat_;
	}
	
	/**
	 * Renvoie le nom et l'ip du Client sous forme textuelle
	 */
	public String toString(){
		return "Nom du client: "+ this.getNom() + System.getProperty("line.separator") + "IP du client: " + this.getAdresseIP();
	}
	
	
	/**
	 * Sauvegarde la totalité des serveurs connus (nom + IP + repertoire de partage)
	 * dans un fichier texte nomme sauvegarde_serveur_client
	 * @throws IOException
	 */
	public void sauvegarderServeursConnus() throws IOException{
		
		File fichier = new java.io.File("sauvegarde_serveur_client.txt.txt"); 

		try 
		{ 
			fichier.createNewFile(); 
		} 
		catch (IOException e) 
		{ 
			System.out.println("Impossible de créer le fichier"); 
		} 
		
		PrintWriter out  = new PrintWriter(new FileWriter("sauvegarde_serveur_client.txt"));

		//Le client se met lui meme en serveur connu de base. Ca ne sert à rien de l'enregistrer sur le txt
		for(int i = 0; i < this.serveursConnus.getListe().size(); i++){
			out.println(this.serveursConnus.getListe().get(i).getNom());
			out.println(this.serveursConnus.getListe().get(i).getAdresseIP());
			out.println(this.serveursConnus.getListe().get(i).getFichiersRecences().renvoyerRepertoirePartage());
		}
		
	    out.close();	
		
	}

	
	
	/**
	 * Charge les serveurs sauvegardés dans le fichier texte
	 * @return ListeServeursConnus : la liste des serveurs chargés
	 * @throws IOException
	 */
	public ListeServeursConnus chargerServeursConnus() throws IOException{
		ListeServeursConnus liste = new ListeServeursConnus();
		Serveur temp;
		
				
		BufferedReader in = new BufferedReader(new FileReader("sauvegarde_serveur_client.txt"));
		String infoNom;
		
		while((infoNom = in.readLine() ) != null){
			String infoIP = in.readLine();
			
			//extraction de l'IP du String (on ne garde que la partie avec les chiffres)
			
			int i = 0;
			while(i < infoIP.length() && infoIP.charAt(i) != '/' ){
				i++;
			}
			infoIP = infoIP.substring(i, infoIP.length());
			
			String infoPath = in.readLine();
			temp = new Serveur(infoNom, infoIP.substring(1, infoIP.length()));
			temp.setFichiersRecences(temp.getFichiersRecences().miseAJourListe(infoPath, null));
			
			liste.ajouterServeurListe(temp);
		}
		
		
		in.close();
		return liste;
	}
	
	
	
	
	
}
