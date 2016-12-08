package eOwl.client;

import java.awt.event.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import eOwl.etiquette.InfoFichier;
import eOwl.etiquette.ListeFichier;


/**
 * <code>EcouteursBouton</code> permet de modifier une instance de 
 * <code>Interface</code> en fonction de ce que l'utilisateur entre dans 
 * la vue.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */

public class EcouteurBouton implements ActionListener {

	private InterfaceGUI inter;
	private JTextField champ1;
	private JTextField champ2;
	private VueInterfaceGUI vue ;

	/**
	 * Creer une instance de <code>EcouteurBoutons</code>.
	 *
	 * @param inter_ l'instance de <code>Interface</code> a mettre a jour
	 * @param champ1_ l'instance de <code>JTextField</code> pour recuperer la premiere
	 *             chaine de caracteres associee au bouton
	 * @param champ2_ l'instance de <code>JTextField</code> pour recuperer la deuxieme
	 *             chaine de caracteres associee au bouton
	 * @param vue_ l'instance de <code>VueInterface</code> contenant les boutons a ecouter
	 */
	public EcouteurBouton(InterfaceGUI inter_, JTextField champ1_, JTextField champ2_, VueInterfaceGUI vue_){
		this.inter = inter_;
		this.champ1 = champ1_;
		this.champ2 = champ2_;
		this.vue = vue_;
	}

	/**
	 * Creer une instance de <code>EcouteurBoutons</code>.
	 *
	 * @param inter_ l'instance de <code>Interface</code> a mettre a jour
	 * @param champ_ l'instance de <code>JTextField</code> pour recuperer la
	 *             chaine de caracteres
	 * @param vue_ l'instance de <code>VueInterface</code> contenant les boutons a ecouter
	 */

	public EcouteurBouton(InterfaceGUI inter_, JTextField champ_, VueInterfaceGUI vue_) {
		this.inter = inter_;
		this.champ1 = champ_;
		this.vue = vue_;
	}

	/**
	 * Creer une instance de <code>EcouteurBoutons</code>.
	 *
	 * @param inter_ l'instance de <code>Interface</code> a mettre a jour
	 * @param vue_ l'instance de <code>VueInterface</code> contenant les boutons a ecouter
	 */
	public EcouteurBouton(InterfaceGUI inter_, VueInterfaceGUI vue_) {
		this.inter = inter_;
		this.vue = vue_;
	}

	/**
	 * Methode qui associe a une action sur un bouton une methode a appeler
	 * @param e l'evenement qui s'est produit
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String nom_serveur = null;
		String nom_fichier = null;
		String nom_motclef = null;

		if(source == this.vue.get_add_serveur()){
			
			VueNewServeurGUI vueNS = new VueNewServeurGUI(this.inter,"");
			vueNS.setVisible(true);

		} else if(source == this.vue.get_ok_fichier()){
			System.out.println("Ok fichier");

			//enregistrer le nom du fichier et lancer la recherche en couplant nom_serveur et le nom du fichier
			nom_serveur = this.champ1.getText();

			nom_fichier = this.champ2.getText();
			//this.inter.getClient().getListeServeursConnus().getListe().
			//recherche de serveur pour renvoyer l'ip
			//test nom valide
			
			//this.inter.getClient().getEtatClient().telechargementPrecis(inf.getIp(), nom_fichier);
			
		} else if(source == this.vue.get_ok_motclef()){ //filtre par mot clé
			nom_motclef = this.champ1.getText();
			this.inter.getClient().getFichiersClient().updateListeFichierClient(this.inter.getClient().getListeServeursConnus());
			
			this.inter.setListeFichier(this.inter.rechercher_motclef(nom_motclef));
			
			
			this.vue.update();

		} else if(source == this.vue.get_oui()){
			System.out.println("Ok oui");

			//lancer la recherche sur d'autres serveurs pour Gweltaz et Benjamin 
			//A COMPLETER
			this.inter.etendre_recherche();

		} else if(source == this.vue.get_non()){
			System.out.println("Ok non");

			//Le bouton ne sert à rien, juste à être le symétrique du oui

		} else if(source == this.vue.get_telecharger()){

			//recuperer ton JTree
			TreePath path = this.vue.getTree().getSelectionPath();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			InfoFichier inf = this.inter.getListeFichier().rechercherFichier((String) selectedNode.getUserObject());
			
			//télécharge le fichier
			this.inter.getClient().getEtatClient().telechargementPrecis(inf.getIp(), inf.getNom());
			
		} else if(source == this.vue.get_stop()){
			System.out.println("Ok stop");
			this.vue.getClient().getEtatClient().annulerTelechargement();
			//A COMPLETER

		} else if(source == this.vue.get_etiquette()){
			
			for(int j = 0; j < this.inter.getListeFichier().getListe().size(); j++){
				String valCell = (String) this.vue.getArea().getValueAt(j,1).toString();
				valCell = valCell.substring(1, valCell.length()-1);

				ArrayList<String> myList = new ArrayList<String>(Arrays.asList(valCell.split(", ")));
				
				this.inter.getListeFichier().getListe().get(j).setEtiquetteFichier(myList);
			}
					
			this.vue.update();

		} else if(source  == this.vue.get_telecharger_depuis_liste()){
			
			int ligneSelectionne = this.vue.getArea().getSelectedRow();

			//on récupére la valeur de la première colonne de la ligne sélectionné
			String nom_ = (String) this.vue.getArea().getValueAt(ligneSelectionne, 0);
			InetAddress IP_ = (InetAddress) this.vue.getArea().getValueAt(ligneSelectionne, 3);
					
			//telechargement du fichier trouvé
			this.inter.getClient().getEtatClient().telechargementPrecis(IP_, nom_);
			
		}
		
	}

}