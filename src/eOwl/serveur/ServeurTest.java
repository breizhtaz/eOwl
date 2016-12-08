package eOwl.serveur;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Cl�ment Dauge & Annie Puchot
 *
 */
public class ServeurTest {

	
	Serveur s1 = new Serveur("Clem");

	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
	}
	
	/**
	 * Test du constructeur 
	 */
	@Test
	public void testGetNom() {
		assertEquals(s1.getNom(), "Clem");
	}
	
	@Test
	public void testSetNom() {
		s1.setNom("Annie");
		assertEquals(s1.getNom(), "Annie");
	}
	
	@Test
	public void testGetAdresseIP() {
		try{
			assertEquals(s1.getAdresseIP(), InetAddress.getLocalHost());
		}catch(IOException io){}
	}
	
	
}
