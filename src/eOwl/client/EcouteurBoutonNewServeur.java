package eOwl.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextField;

import eOwl.etiquette.InfoFichier;
import eOwl.serveur.Serveur;

public class EcouteurBoutonNewServeur implements ActionListener{


	private InterfaceGUI inter;
	private JTextField champ1;
	private VueNewServeurGUI vue ;


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
	public EcouteurBoutonNewServeur(InterfaceGUI inter_, JTextField champ1_, VueNewServeurGUI vue_){
		this.inter = inter_;
		this.champ1 = champ1_;
		this.vue = vue_;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if(this.vue.getClient().getEtatClient().initialize(InetAddress.getByName(this.champ1.getText()))==true){
				this.vue.dispose();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
