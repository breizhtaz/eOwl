package eOwl.client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;


import org.junit.Before;
import org.junit.Test;

import eOwl.serveur.Serveur;

public class ClientTest {

	private Client client;

	@Before
	public void setUp() throws Exception {

		/* On initialise le client */
		this.client = new Client("toto");
		this.client.ajouterFichier("lol");

	}


	@Test
	public void testAjouterEtiquette() {
		/* On verifie si l'ajout de l'etiquette a reussi */
		assertEquals(true,this.client.ajouterEtiquette("Film", "lol"));
	}


	/**
	 * On test les accesseurs et mutateurs herite de la classe machine 
	 **/
	@Test
	public void testGetNom() {
		assertEquals(client.getNom(), "toto");
	}

	@Test
	public void testSetNom() {
		client.setNom("Titi");
		assertEquals(client.getNom(), "Titi");
	}

	@Test
	public void testGetAdresseIP() {
		try{
			assertEquals(client.getAdresseIP(), InetAddress.getLocalHost());
		}
		catch(Exception e){

		}
	}

	@Test
	public void testSauvegardeServeurListe() throws IOException{
		Serveur s1 = new Serveur("Clem");
		s1.setFichiersRecences(s1.getFichiersRecences().miseAJourListe("C:\\Users\\Clément\\Desktop\\eOwl", null));
		//s1.getFichiersRecences().miseAJourListe("\\ressources", null);

		Serveur s2 = new Serveur("Annie");
		s2.setFichiersRecences(s2.getFichiersRecences().miseAJourListe("C:\\Users\\Clément\\Desktop\\eOwl\\films", null));

		client.getListeServeursConnus().ajouterServeurListe(s1);
		client.getListeServeursConnus().ajouterServeurListe(s2);

		client.sauvegarderServeursConnus();


	}

	@Test
	public void testChargerServeursConnus() throws IOException{
		System.out.println("***************************************debut du test***************************************");
		client.setListeServeursConnus(client.chargerServeursConnus());

		for(int i = 0; i < client.getListeServeursConnus().getListe().size();i++){
			System.out.println(client.getListeServeursConnus().getListe().get(i).getNom());
			System.out.println(client.getListeServeursConnus().getListe().get(i).getAdresseIP());
			System.out.println(client.getListeServeursConnus().getListe().get(i).getFichiersRecences().renvoyerRepertoirePartage());
		}

		System.out.println("***************************************fin du test***************************************");

	}

}
