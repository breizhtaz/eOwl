package eOwl.serveur;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

public class Machine implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le nom de la machine
	 */
	private String nom;
	
	/**
	 * L'adresse de la machine
	 */
	
	private InetAddress adresseIP;
	
	/**
	 * Constructeur par defaut
	 */
	public Machine(){	
	}
	/**
	 * Cree une machine avec un nom
	 * @param nom_ le nom de la <code>Machine</code>
	 */
	public Machine(String nom_){
		this.nom = nom_;
		try{
			this.adresseIP = InetAddress.getLocalHost();
		}catch (IOException e){
			System.out.println("Probleme dâ€™entree/sortie : " + e);
			}
	}
		
	/**
	 * Permet de créer une machine en lui associant un nom et une adresse
	 * @param nom_ un <code>String</code> correspondant au nom de la machine
	 * @param adresse un <code>String</code> correspondant a l'adresse de la machine
	 */
	public Machine(String nom_, String adresse){
		this.nom = nom_;
		try{
			this.adresseIP = InetAddress.getByName(adresse);
		}catch (IOException e){
			System.out.println("Probleme dâ€™entree/sortie : " + e);
			}
	}
	
	/**
	 * Permet de créer une machine en lui associant un nom et une adresse
	 * @param nom_ un <code>String</code> correspondant au nom de la machine
	 * @param adresse un <code>InetAddress</code> correspondant a l'adresse de la machine
	 */
	public Machine(String nom_, InetAddress adresse){
		this.nom = nom_;
		this.adresseIP = adresse;
	}
	
	/**
	 * Getter
	 * @return Nom donne a la machine Serveur
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Setter
	 * @param nom Nouveau nom donne a la machine Serveur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**Getter
	 * @return Adressse IP de la machine Serveur
	 */
	public InetAddress getAdresseIP() {
		return this.adresseIP;
	}
}
