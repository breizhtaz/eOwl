package eOwl.serveur;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.Before;
import org.junit.Test;

import eOwl.client.Client;

public class ServeurIsWaitingTest {
	Serveur serveur;

	@Before public void SetUp() {
		this.serveur = new Serveur("test");
	}
	

	@Test
	public void testEcoute() {
		Trame trame;
		
		//On recupere la trame emise par le client sur le serveur
		trame = this.serveur.getEtatServeur().ecoute();
		//On verifie que la trame recu est correcte
		assertEquals(2,trame.getOperation());
		assertEquals("toto.txt",trame.getParam());
		try{
			assertEquals( InetAddress.getLocalHost(),trame.getAdresse());
		}catch(IOException IO){};
	}

}
