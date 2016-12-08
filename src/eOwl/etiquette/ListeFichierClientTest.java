package eOwl.etiquette;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.*;

import eOwl.client.Client;
import eOwl.serveur.Serveur;
import static org.junit.Assert.*;

public class ListeFichierClientTest {

	/** On initialise le serveur, on l'ajoute a la liste des serveurs connus et on cree une liste de fichiers client a laquelle on va vouloir
ajouter les elements contenus dans le serveur.
	 */


	// On cree la liste des serveurs connus

	ListeServeursConnus listeServeur = new ListeServeursConnus();

	//On cree une liste fichier client et l'ensemble des attributs utiles
	ArrayList<String> etiquette = new ArrayList<String>();
	Arbre arbre = new Arbre(new DefaultMutableTreeNode("racine"));

	Vector<InfoFichier> listeinfos = new Vector<InfoFichier>();
	ListeFichierClient listeFile = new ListeFichierClient(arbre);

	// On cree une instance de serveur de serveur et de client.



	Serveur serveur = new Serveur("serveur1","193.255.255.255");
	Client client = new Client("client1");


	@Before public void SetUp(){

	}

	/*On verifie que tous les fichiers presents sur le serveur sont aussi contenus dans la liste de fichier du client
	une fois qu'on a fait appel a la fonction updateListeFichierClient.
	 */
	@Test public void testajouterServeurListeServeursConnus() {
		assertEquals(true,listeServeur.getListe().isEmpty());//on verifie que la liste des serveurs connus n'est plus vide.
		listeServeur.ajouterServeurListe(serveur);//on ajoute un serveur � la liste des serveurs connus.
		assertEquals(false,listeServeur.getListe().isEmpty());//on verifie que la liste des serveurs connus n'est plus vide.
		assertEquals(listeServeur.getListe().get(0), serveur);
	}

	@Test public void testUpdateListeFichierClient() {
		etiquette.add("Film");
		InfoFichier file1 = new InfoFichier("Fichier2", etiquette, "193.255.255.255","C://");
		InfoFichier file2 = new InfoFichier("Fichier3", etiquette, "193.255.255.255","C://");

		//listeinfos est un vector de infofichiers
		listeinfos.add(file1);
		listeinfos.add(file2);//on ajoute deux fichiers dans la liste des fichiers heberges par le serveur.

		//creation d'une liste de fichiers stockes sur un serveur
		ListeFichierServeur listeFichierServeur = new ListeFichierServeur(arbre,listeinfos);

		//on lie le serveur � la liste des fichiers qu'il heberge.
		serveur.setFichiersRecences(listeFichierServeur);

		//ajout de serveur a la liste des serveurs connus par le client
		listeServeur.ajouterServeurListe(serveur);
		//on indique que serveur est connu par client, en le mettant dans la liste des serveurs connus par lui.
		client.setListeServeursConnus(listeServeur);

		client.setFichiersClient(listeFile);//la liste des fichiers connus du client est pour le moment vide.

		//mise � jour de l'ancienne ListeFichierClient.
		client.setFichiersClient(client.getFichiersClient().updateListeFichierClient(client.getListeServeursConnus()));

		assertTrue(client.getFichiersClient().getListe().containsAll(serveur.getFichiersRecences().getListe()));//on verifie que la mise � jour a ete prise en compte dans la liste des fichiers connus par le client.

		//on cree un nouveau serveur poss�dant des fichiers, puis on l'ajoute au client, et on met a jour la liste des fichiers dispo
		Serveur serveurBis = new Serveur("clem", "007.007.007.007");

		serveurBis.setFichiersRecences((ListeFichierServeur) serveurBis.getFichiersRecences().ajouterFichierListe(new InfoFichier("BE-java", null, "100.0.0.2", "d://")));

		client.getListeServeursConnus().ajouterServeurListe(serveurBis);
		client.setFichiersClient(client.getFichiersClient().updateListeFichierClient(client.getListeServeursConnus()));

		assertTrue(client.getFichiersClient().getListe().containsAll(serveur.getFichiersRecences().getListe()));//on verifie que la mise � jour a ete prise en compte dans la liste des fichiers connus par le client.


	}

}