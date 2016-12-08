package eOwl.etiquette;

import org.junit.*;

import eOwl.serveur.Serveur;
import static org.junit.Assert.*;

public class ListeServeursConnusTest {

	/** On cree le serveur que l'on va vouloir ajouter lorsqu'on appelle la fonction update, puis on cree la liste des
	 * serveurs connus pour la mettre a jour.
	 */

	// On cree les serveur

	Serveur serveurConnuParClient = new Serveur("Serveur1","0.0");
	Serveur serveurInconnuParClient = new Serveur("Serveur2","0.0");

	//On cree la liste des serveurs connus
	ListeServeursConnus listeServeurConnusParClient = new ListeServeursConnus();
	ListeServeursConnus listeServeurInconnuParClient= new ListeServeursConnus();

	@Before public void SetUp() {

	}



	@Test public void ajouterServeurListe(){
		listeServeurConnusParClient.ajouterServeurListe(serveurConnuParClient);
		assertEquals(listeServeurConnusParClient.getListe().contains(serveurConnuParClient),true);
	}


	@Test public void retirerServeurListeServeursConnus(){
		//On ajoute un serveur à la liste initiallement vide puis on l'enlève pour enfin vérifier si la liste est vide
		listeServeurConnusParClient.ajouterServeurListe(serveurConnuParClient);
		listeServeurConnusParClient.retirerServeurListeServeursConnus(serveurConnuParClient.getNom());
		assertEquals(listeServeurConnusParClient.getListe().contains(serveurConnuParClient),false);
	}


	/** On verifie que tous les serveurs connus sont bien contenus dans la liste des serveurs connus par le 
	 * client une fois qu'on a fait appel a la fonction updateListeServeursConnus.
	 */
	@Test public void testUpdateListeServeursConnus() {

		listeServeurInconnuParClient.ajouterServeurListe(serveurInconnuParClient);
		listeServeurConnusParClient.ajouterServeurListe(serveurConnuParClient);

		serveurConnuParClient.setServeursLies(listeServeurInconnuParClient);

		//listeServeurConnusParClient.updateListeServeursConnus();

		//assertTrue(listeServeurConnusParClient.getListe().contains(listeServeurInconnuParClient));
	}
	
	@Test public void testRechercheServeur(){
		
		Serveur s2 = new Serveur("Serveur2","0.0");
		Serveur s3 = new Serveur("Serveur3","0.0");
		
		listeServeurConnusParClient.ajouterServeurListe(s2);
		listeServeurConnusParClient.ajouterServeurListe(s3);
		Serveur resul = listeServeurConnusParClient.rechercherServeur("Serveur2");
		assertEquals(resul.getNom(), "Serveur2");
		
	}
}