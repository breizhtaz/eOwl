package eOwl.client;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//Classe qui permet d'afficher une JProgressBar dans une cellule de tableau
/**
 * <code>ProgressCellRenderer</code> est une classe qui permet d'integrer 
 * des JProgress Bar a l'interieur d'une JTable.
 *
 * @author Laura Hoinville et Benjamin Bercovici
 * @version 1.0
 */
public class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {

	/**
	 * Creer une instance de <code>ProgressCellRenderer</code>.
	 */

	public ProgressCellRenderer(){
		super(0, 100);
		setValue(0);
		setString("0%");
		setStringPainted(true);
	}

	/**
	 * Methode permettant de recuperer le composant que l'on cherche a 
	 * inserer dans la JTable.
	 * 
	 * @return Component le composant que l'on ajoute a la JTable
	 * 
	 * @param table la JTable dans laquelle on veut ajouter le composant
	 * @param value l'objet qui va servir a l'operation
	 * @param isSelected est un booleen
	 * @param hasFocus est un booleen
	 * @param row est le numero associe a la rangee de la JTable
	 * @param column est le numero de la colonne de la JTable
	 */
	public Component getTableCellRendererComponent(
			JTable table,
			Object value,
			boolean isSelected,
			boolean hasFocus,
			int row,
			int column) 
	{ final String sValue = value.toString();
	int index = sValue.indexOf('%');
	if (index != -1) {
		int p = 0;
		try{
			p = Integer.parseInt(sValue.substring(0, index));
		}
		catch(NumberFormatException e){
		}
		setValue(p);
		setString(sValue);
	}
	return this;
	}
}
