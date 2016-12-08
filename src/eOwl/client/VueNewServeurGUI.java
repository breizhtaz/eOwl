package eOwl.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VueNewServeurGUI extends JFrame implements Observer{

	/**
	 * Creer une instance de <code>VueClientInitGUI</code> qui sera la fenêtre de lancement.
	 *
	 * @param inter_ l'instance de <code>Interface</code> que l'on veut associer 
	 * @param titre le titre de la fenetre
	 */
	private static final long serialVersionUID = 1L;

	private InterfaceGUI inter;

	private JTextField nom_client;
	private JTextField adresseIPDebut;
	private JButton bouton_lancement;

	private boolean b;
	private Client client;
	
	

	

	private boolean ClientInit;
	
	public VueNewServeurGUI(InterfaceGUI inter_, String titre){
		super(titre);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.inter = inter_;
		this.inter.addObserver(this);
		this.setName(titre);
		this.b = true;
		this.ClientInit=false;
		this.client= inter.getClient();
		
		//Définition des JTextField et des boutons
		

		JLabel adresse = new JLabel("Entrer l'adresse IP d'un serveur :");
		this.adresseIPDebut = new JTextField(45);
		adresseIPDebut.setName("adressIPDebut");

		this.bouton_lancement = new JButton("Ajout Serveur");
		bouton_lancement.setName("bouton_lancement");

		//Placement des composants sur la fenêtre de lancement
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gb);

		gbc.fill = GridBagConstraints.NONE;

	
	

		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(adresse, gbc);
		add(adresse, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(adresseIPDebut, gbc);
		add(adresseIPDebut, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(bouton_lancement, gbc);
		add(bouton_lancement, gbc);

		//Reglage des parametres permettant d'afficher la fenetre avec des proprietes standard
		this.pack();


		//Mise à l'écoute d'ajout
		bouton_lancement.addActionListener(new EcouteurBoutonNewServeur(this.inter, this.adresseIPDebut, this));
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
	
	public boolean getClientInit() {
		return ClientInit;
	}

	public void setClientInit(boolean clientInit) {
		ClientInit = clientInit;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	

}
