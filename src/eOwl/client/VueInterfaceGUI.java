package eOwl.client;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import eOwl.etiquette.Arbre;
import eOwl.etiquette.ListeFichierServeur;


/**
 * <code>VueInterface</code> est une vue pour la classe representant 
 * l'interface graphique  <code>Interface</code>.
 * Elle permet au client d'interagir avec le logiciel e-Owl.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */

public class VueInterfaceGUI extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InterfaceGUI inter;

	private JTextField nom_client;
	private JTextField adresseIPDebut;
	private JButton bouton_lancement;

	private JButton bouton_telecharger;
	private JButton bouton_telecharger_depuis_liste;
	
	private JButton bouton_stop;

	private JTextField champ_serveur;
	private JButton add_serveur;

	private JTextField champ_fichier;
	private JButton ok_fichier;

	private JTextField champ_motclef;
	private JButton ok_motclef;

	private JButton ok_etiquette;

	private JButton oui = new JButton("Oui");
	private JButton non = new JButton("Non");

	private DefaultTableModel model = new DefaultTableModel(0,4);
	private JTable area;
	
	private JTree tree;

	private boolean b;
	private Client client;

	/**
	 * Creer une instance de <code>VueInterface</code>.
	 *
	 * @param inter_ l'instance de <code>Interface</code> que l'on veut associer 
	 * @param titre le titre de la fenetre
	 * @param liste- liste de fichier serveur qui doivent s'afficher dans l'arbre
	 */

	public VueInterfaceGUI(InterfaceGUI inter_, String titre, Client client_) {
		super(titre);
		this.inter = inter_;
		this.inter.addObserver(this);
		this.setName(titre);
		this.client=client_;
		//On cree tous les composants dont on a besoin dans l'interface graphique
		bouton_telecharger = new JButton("Télécharger depuis l'arborescence");
		bouton_telecharger.setName("bouton_telecharger");
		
		bouton_telecharger_depuis_liste = new JButton("Télécharger depuis la liste");
		bouton_telecharger_depuis_liste.setName("bouton_telecharger_depuis_liste");
		
		bouton_stop = new JButton("Stop Téléchargement");
		bouton_stop.setName("bouton_stop");

		champ_serveur = new JTextField(45);
		champ_serveur.setName("champ_serveur");

		add_serveur= new JButton("Ajouter Serveur");
		add_serveur.setName("ok_serveur");

		JLabel serveur = new JLabel("Spécifier le serveur :               ");

		champ_fichier = new JTextField(45);
		champ_fichier.setName("champ_fichier");

		ok_fichier= new JButton("Lancer Telechargement");
		ok_fichier.setName("ok_fichier");

		JLabel fichier = new JLabel("Spécifier le nom du fichier :      ");

		champ_motclef = new JTextField(45);
		champ_motclef.setName("champ_motclef");

		ok_motclef= new JButton("Recherche Liste Fichier Client");
		ok_motclef.setName("ok_motclef");

		ok_etiquette= new JButton("Enregistrer modifications des étiquettes");
		ok_etiquette.setName("ok_etiquette");

		JLabel motclef = new JLabel("Entrer les mots-clefs :          ");

		JLabel recherche = new JLabel("RECHERCHE :");
		JLabel recherche_precise = new JLabel("Téléchargement exact :");
		JLabel recherche_fichier = new JLabel("Rechercher un fichier :");
		JLabel etendre_recherche = new JLabel("Etendre la recherche :");
		JLabel appel_serveur = new JLabel("Souhaitez-vous rechercher des fichiers sur d'autres serveurs ?");
		JLabel ligne1 = new JLabel("--------------------");
		JLabel ligne2 = new JLabel("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
		JLabel ligne3 = new JLabel("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
		JLabel ligne4 = new JLabel("________________________________________________________________________");
		JLabel ligne5 = new JLabel("----------------------------");
		JLabel ligne6 = new JLabel("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
		JLabel ligne7 = new JLabel("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
		JLabel telechargement = new JLabel("TELECHARGEMENT :");

		//Creation du tableau affichant le fichier en cours de telechargement avec une barre de progression
		String[] columnNames = {"Nom  du   fichier","Etat d'avancement","Etat  du  serveur"};
		Object[][] data = {
				{"                 ","                 ","                 "}
		};

		JTable table_ = new JTable(data, columnNames);
		for (int i = 0; i < 3; i++) {
			table_.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		table_.setRowHeight(20);
		table_.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());
		this.inter.set_table(table_);

		//Creation de l'arbre => on recupere les infos directement de liste.getArbre();
		Arbre arbre = this.inter.getClient().getListeServeursConnus().getArbre(); 
		this.tree = new JTree((DefaultMutableTreeNode) arbre.getRoot());

		JScrollPane pane1 = new JScrollPane(tree);//Creation de l'ascenceur qui contient tout

		//Ici on cree la liste de fichier client qui s'affichera à côté de l'arbre
		//this.area = new JTable(this.inter.getClient().getFichiersClient().getListe().size(), 4);


		//remplissage du tableau : 
		for(int i = 0; i < this.inter.getClient().getFichiersClient().getListe().size();i++){
						
			this.model.addRow(data);
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getNom(), i, 0); //colonne 1 : nom du fichier
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getEtiquetteFichier(), i, 1);//colonne 2: etiquette
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getPath(), i, 2);//colonne 3 : chemin du fichier
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getIp(), i, 3);//colonne 4 : adresse IP du serveur
		}
		this.area = new JTable(model);
		for (int i = 0; i < 4; i++) {
			this.area.getColumnModel().getColumn(i).setPreferredWidth(100);
		}
		this.area.setRowHeight(20);
		JScrollPane pane2 = new JScrollPane(this.area);//Creation de l'ascenceur qui contient tout


		//On initialise la grille qui placera les composants au bon endroit
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gb);


		//Placement des elements sur la fenêtre (c'est super long et vous avez pas besoin de vous penchez dessus, ça marche)
		gbc.fill = GridBagConstraints.NONE;

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(recherche, gbc);
		add(recherche, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(ligne1, gbc);
		add(ligne1, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(recherche_precise, gbc);
		add(recherche_precise, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(serveur, gbc);
		add(serveur, gbc);
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(champ_serveur, gbc);
		add(champ_serveur, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(add_serveur, gbc);
		add(add_serveur, gbc);


		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(fichier, gbc);
		add(fichier, gbc);
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(champ_fichier, gbc);
		add(champ_fichier, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ok_fichier, gbc);
		add(ok_fichier, gbc);


		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne2, gbc);
		add(ligne2, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(recherche_fichier, gbc);
		add(recherche_fichier, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(motclef, gbc);
		add(motclef, gbc);
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(champ_motclef, gbc);
		add(champ_motclef, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(ok_motclef, gbc);
		add(ok_motclef, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne3, gbc);
		add(ligne3, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(etendre_recherche, gbc);
		add(etendre_recherche, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(appel_serveur, gbc);
		add(appel_serveur, gbc);
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(oui, gbc);
		add(oui, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(non, gbc);
		add(non, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne4, gbc);
		add(ligne4, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(telechargement, gbc);
		add(telechargement, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne5, gbc);
		add(ligne5, gbc);

		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(bouton_telecharger, gbc);
		add(bouton_telecharger, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(bouton_stop, gbc);
		add(bouton_stop, gbc);
		
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(bouton_telecharger_depuis_liste, gbc);
		add(bouton_telecharger_depuis_liste, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne6, gbc);
		add(ligne6, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(table_.getTableHeader(), gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(table_, gbc); 
		add(table_.getTableHeader(), gbc);
		add(table_, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ok_etiquette, gbc);
		add(ok_etiquette, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gb.setConstraints(ligne7, gbc);
		add(ligne7, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(pane1, gbc);
		add(pane1, gbc);

		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gb.setConstraints(pane2, gbc);
		add(pane2, gbc);

		//Mise à l'écoute des boutons de l'interface
		add_serveur.addActionListener(new EcouteurBouton(this.inter, this.champ_serveur, this));
		ok_fichier.addActionListener(new EcouteurBouton(this.inter, this.champ_fichier, this));
		ok_motclef.addActionListener(new EcouteurBouton(this.inter, this.champ_motclef, this));
		oui.addActionListener(new EcouteurBouton(this.inter, this));
		non.addActionListener(new EcouteurBouton(this.inter, this));
		bouton_telecharger.addActionListener(new EcouteurBouton(this.inter, this));
		bouton_telecharger_depuis_liste.addActionListener(new EcouteurBouton(this.inter, this));
		bouton_stop.addActionListener(new EcouteurBouton(this.inter, this));


		//Reglage des parametres permettant d'afficher la fenetre avec des proprietes standard
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setExtendedState(this.MAXIMIZED_BOTH);

		this.setVisible(true);
		this.area.setShowHorizontalLines(true);//affichage de lignes horizontales dans le tableau
	}


	

	public void update() {
		//mise à jour de la liste des fichiers du client qui aura peut être été triée.
		System.out.println(this.model.getRowCount());
		int j = 0;
		
		while(this.model.getRowCount() > 0 && j < 5000){
			this.model.removeRow(0);
			j++;
		}
		System.out.println(this.model.getRowCount());

		for(int i = 0; i < this.inter.getListeFichier().getListe().size();i++){
			this.model.insertRow(i, new Vector());

			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getNom(), i, 0); //colonne 1 : nom du fichier
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getEtiquetteFichier(), i, 1);//colonne 2: etiquette
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getPath(), i, 2);//colonne 3 : chemin du fichier
			this.model.setValueAt(this.inter.getListeFichier().getListe().get(i).getIp(), i, 3);//colonne 4 : adresse IP du serveur
		}
		System.out.println(this.model.getRowCount());

			
		
		this.revalidate();
	}

	/**
	 * Accesseur sur le bouton permettant de recuperer le nom d'un serveur
	 * @return ok_serveur le bouton servant a valider le champ de texte dans lequel on a rentré le nom du serveur
	 */
	public JButton get_add_serveur() {
		return this.add_serveur;
	}

	/**
	 * Accesseur sur le bouton permettant de recuperer le nom d'un fichier
	 * @return ok_fichier le bouton servant a valider le champ de texte dans lequel on a rentré le nom du fichier
	 */
	public JButton get_ok_fichier() {
		return this.ok_fichier;
	}

	/**
	 * Accesseur sur le bouton permettant de recuperer un mot clef
	 * @return ok_mot_clef le bouton servant a valider le champ de texte dans lequel on a rentré le mot clef
	 */
	public JButton get_ok_motclef() {
		return this.ok_motclef;
	}

	/**
	 * Accesseur sur le bouton permettant de valider l'interrogation d'autres serveurs
	 * @return oui le bouton servant a lancer l'interrogation des autres serveurs
	 */
	public JButton get_oui() {
		return this.oui;
	}

	/**
	 * Accesseur sur le bouton permettant de ne pas valider l'interrogation d'autres serveurs
	 * @return non le bouton servant a ne pas lancer l'interrogation des autres serveurs
	 */
	public JButton get_non() {
		return this.non;
	}

	/**
	 * Accesseur sur le bouton permettant de lancer le telechargement
	 * @return bouton_telechargement le bouton servant a lancer le telechargement
	 */
	public JButton get_telecharger() {
		return this.bouton_telecharger;
	}
	
	/**
	 * Accesseur sur le bouton permettant de lancer le telechargement depuis la liste
	 * @return bouton_telechargement le bouton servant a lancer le telechargement
	 */
	public JButton get_telecharger_depuis_liste() {
		return this.bouton_telecharger_depuis_liste;
	}

	/**
	 * Accesseur sur le bouton permettant de stopper le telechargement
	 * @return bouton_stop le bouton servant a stopper le telechargement
	 */
	public JButton get_stop() {
		return this.bouton_stop;
	}

	/**
	 * Accesseur sur le bouton permettant de recuperer le nom de la nouvelle etiquette a entrer
	 * @return ok_etiquette le bouton servant a valider le champ de texte dans lequel on a rentré le nom de la nouvelle etiquette
	 */
	public JButton get_etiquette() {
		return this.ok_etiquette;
	}

	/**
	 * Accesseur sur le bouton permettant de recuperer le nom d'un client et celui d'un serveur
	 * @return bouton_lancement le bouton servant a valider les champs de texte dans lesquels on a rentré le nom du client et celui du serveur
	 */
	public JButton get_lancement(){
		return this.bouton_lancement;
	}

	/**
	 * Accesseur sur le booleen permettant d'afficher ou non la fenetre de lancement
	 * @return booleen le booleen servant a afficher ou non la fenetre de lancement
	 */
	public Boolean get_boolean(){
		return this.b;
	}

	/**
	 * Modifieur sur le booleen permettant d'afficher ou non la fenetre de lancement
	 * @param booleen le booleen servant a afficher ou non la fenetre de lancement
	 */
	public void set_boolean(Boolean b_){
		this.b = b_;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}


	public JTree getTree() {
		return tree;
	}


	public void setTree(JTree tree) {
		this.tree = tree;
	}


	public JTable getArea() {
		return area;
	}


	public void setArea(JTable area) {
		this.area = area;
	}
}
