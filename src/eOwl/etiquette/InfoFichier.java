package eOwl.etiquette;

import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;


/*
 * @author Benjamin Bercovici & Laura Hoinville
 */
public class InfoFichier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private InetAddress ip;
	private String path;
	private ArrayList<String> etiquetteFichier;


	/** <code>InfoFichier<code> créé une nouvelle instance de la classe InfoFichier. 
	 * 
	 * 
	 * @param nomFichier: String représentant le nom du fichier. Exemple: "Matrix"
	 * @param tag:ArrayList<String> représentant l'étiquette du fichier. Cette étiquette est la concaténation de toutes les étiquettes caractérisant le fichier. Les liens entre étiquettes sont déduits de l'arbre des étiquettes.
	 *  Exemple. "Film+action+avi" caractérise un Film d'action en format Avi. Les trois éléments de etiquetteFichier seront donc "Film", "Action" et "avi".
	 * @param ip: String représentant l'IP du serveur sur lequel est hébergé le fichier. Exemple: "10.193.251.252"
	 * @param path: String représentant le chemin absolu où se trouve le fichier. Exemple: "D:\Partage Chuck\Films\Matrix.avi"
	 */
	public  InfoFichier(String nom_, ArrayList<String> etiquetteFichier_, InetAddress ip_, String path_) {
		this.nom = nom_;
		this.ip = ip_;
		this.path = path_;
		this.etiquetteFichier = etiquetteFichier_;
	}

	public  InfoFichier(String nom_, ArrayList<String> etiquetteFichier_, String ip_, String path_) {
		this.nom = nom_;
		try{
			this.ip = InetAddress.getByName(ip_);
		}catch (IOException e){
			System.out.println("Probleme d'entree/sortie : " + e);
		}
		this.path = path_;
		this.etiquetteFichier = etiquetteFichier_;
	}



	/**<code>getNom()<code> accesseur sur l'attribut nom de InfoFichier. 
	 * @return nomFichier Nom du fichier
	 */
	public String getNom() {
		return this.nom;
	}


	/**<code>getIp()<code>accesseur sur l'attribut ip de InfoFichier. 
	 * @return ip IP du serveur hébergeant le fichier.
	 */
	public InetAddress getIp() {
		return this.ip;
	}



	/**
	 * <code>getPath()<code> accesseur sur l'attribut path de InfoFichier
	 * @return path Path du fichier hébergé.
	 */
	public String getPath(){
		return this.path;
	}


	/**
	 * <code>getEtiquetteFichier()<code> accesseur sur l'étiquette globale du fichier
	 * @return etiquetteFichier ArrayList<String> stockant l'étiquette globale du fichier.
	 */
	public ArrayList<String> getEtiquetteFichier(){
		return this.etiquetteFichier;
	}

	
	/**<code>setnom(String nomFichier)<code> est un modifieur sur l'attribut nom de InfoFichier
	 * @param nomFichier nom du fichier.
	 */
	public void setNomFichier(String nomFichier_) {
		this.nom = nomFichier_;
	}


	/**
	 * <code>setPath(String path_)<code> modifieur sur l'attribut path de InfoFichier
	 */
	public void setPath(String path_){
		this.path = path_;
	}

	/**<code>setEtiquetteFichier(ArrayList<String> etiquetteFichier_)<code> modifieur l'attribut étiquette de InfoFichier
	 * 
	 * @param etiquetteFichier_ la nouvelle etiquette.
	 */
	public void setEtiquetteFichier(ArrayList<String> etiquetteFichier_){
		this.etiquetteFichier = etiquetteFichier_;
	}

	/**
	 * <code>setIP(String ip_)<code> modifieur sur l'attribut ip de InfoFichier
	 */
	public void setIp(InetAddress ip_){
		this.ip = ip_;
	}

	/**
	 * Methode toString permettant d'afficher un infofichier
	 */
	public String toString(){
		return "nom " + this.nom + " | ip " + this.ip.toString() + " | chemin " + this.path + " | etiquettes " + this.etiquetteFichier;
	}
}

