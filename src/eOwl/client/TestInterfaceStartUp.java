package eOwl.client;

import java.io.IOException;

import eOwl.etiquette.ListeServeursConnus;
import eOwl.serveur.Serveur;

public class TestInterfaceStartUp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterfaceGUI inter_ = new InterfaceGUI("client");
		ListeServeursConnus temp;
		boolean next=false;
		//on prépare la fenêtre d'initialisation si besoin est.
		VueClientInitGUI vueinit= new VueClientInitGUI(inter_, "Logiciel e-Owl");
		vueinit.setVisible(false);

		//Dans cette boucle, on regarde si les serveurs connus au préalable par le client sont fonctionnels ou non
		while(next==false){
			try {
				/* ***** CHARGEMENT DES SERVEURS DEPUIS LE FICHIER TEXTE */
				temp=inter_.getClient().chargerServeursConnus();
				
				
				switch(temp.getListe().size()){
				//Si la liste est vide (on ne connait pas de serveurs), 
				//on demande à l'utilisateur d'en saisir un, qu'on teste
				case 0:
					vueinit.setVisible(true);
					while(vueinit.getClientInit()==false){
						if(vueinit.getClientInit()==true){
							/* JE N'AI AUCUNE EXPLICATION A CE FAIT, MAIS LE 
							 * SYSO SUIVANT EST INDISPENSABLE
							 */
							System.out.println("TRUE");
						}
					}
					try {
						inter_.getClient().sauvegarderServeursConnus();

					} catch (IOException e) {
						e.printStackTrace();
					}
					vueinit.setVisible(false);
					next=true;
					break;
					//Si la liste n'est pas vide ( on connait au moins un serveur), 
					//On va chercher dans la liste les serveurs.
					//On les teste pour vérifier qu'ils sont valides.
					//Si c'est le cas, initialize les ajoutent à la liste des serveurs effectivement
					//connus par le client.
				default:
					for(Serveur s: temp.getListe()){
						System.out.println(s.getAdresseIP());
						inter_.getClient().getEtatClient().initialize(s.getAdresseIP());

					}

					if(inter_.getClient().getListeServeursConnus().getListe().size()!=0){
						next=true;
					}
					break;
				}
				//on sauvegarde les serveurs connus et fonctionnels
				try {
					inter_.getClient().sauvegarderServeursConnus();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		//Création de la fenêtre principale.
		VueInterfaceGUI vueinterface = new VueInterfaceGUI(inter_, "Client E-Owl",inter_.getClient());







	}

}
