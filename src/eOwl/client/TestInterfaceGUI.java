package eOwl.client;

import java.io.File;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.etiquette.Arbre;
import eOwl.etiquette.ListeFichierServeur;
import eOwl.etiquette.ListeServeursConnus;


/**
 * <code>TestInterface</code> cree une interface et une vue pour cette interface.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */

public class TestInterfaceGUI {

	public static void main(String[] args){

		//demander le nom au démarrage ?
		InterfaceGUI inter_ = new InterfaceGUI("client");
		
		System.out.println(new File("").getAbsolutePath());
		

		
		Client clientTemp = inter_.getClient();
		try{
			clientTemp.setListeServeursConnus(clientTemp.chargerServeursConnus());
		}
		catch(IOException e){
			
			System.out.println("Erreur de chargement des serveurs : " + e);
		}
		inter_.setClient(clientTemp);
		
		//Mise à jour de la liste des fichiers du client
		clientTemp.setFichiersClient(clientTemp.getFichiersClient().updateListeFichierClient(clientTemp.getListeServeursConnus()));
		inter_.setClient(clientTemp);


		//Ca affiche en premier la fenetre du logiciel
		new VueInterfaceGUI(inter_, "Logiciel e-Owl", clientTemp);
		
		
		//Ca affiche par dessus la fenetre de lancement
		//new VueInterfaceGUI(inter_, "Lancement logiciel e-Owl");
		
		
		
		//Sauvegarde des serveurs connus sur le fichier texte
		try {
			inter_.getClient().sauvegarderServeursConnus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
