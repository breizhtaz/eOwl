package eOwl.serveur;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServeurIsUploadingTest {
	Serveur serveur;

	@Before public void SetUp() {
		this.serveur = new Serveur("test");
		this.serveur.setEtatServeur(new ServeurIsUploading(this.serveur));
	}
	
	@Test
	
	public void testEnvoyerFichier() {
		boolean b;
		//On effectue le test localement, le serveur et le client ont la meme adresse
		b = this.serveur.getEtatServeur().envoyerFichier("toto.txt", this.serveur.getAdresseIP());
		assertEquals(true,b);
	}

	@Test
	public void testEnvoyerListeFichiers() {
		boolean b;
		//On effectue le test localement, le serveur et le client ont la meme adresse
		b = this.serveur.getEtatServeur().envoyerListeFichiers(this.serveur.getAdresseIP());
		assertEquals(true,b);
	}

	@Test
	public void testInterrogerServeursConnus() {
		boolean b;
		//On effectue le test localement, le serveur et le client ont la meme adresse
		b = this.serveur.getEtatServeur().interrogerServeursConnus(this.serveur.getAdresseIP());
		assertEquals(true,b);
	}

}
