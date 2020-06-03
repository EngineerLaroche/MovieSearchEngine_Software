package lab_3_tools;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/******************************************************
 * JComboCheckBox
 * 
 * @Resumer:	Permet d'obtenir une liste deroulante
 * 				JCheckBox avec un Label qui les identifient.
 * 
 * @Source: http://esus.com/how-do-i-put-a-jcheckbox-in-a-jcombobox/
 * 
 ******************************************************/
public class JComboCheckBox extends JComboBox<Object>{

	/******************************
	 * Constante - Serial
	 ******************************/
	private static final long serialVersionUID = 1L;
	private ArrayList <String> listeGenres = new ArrayList<>();
	private JCheckBox jcb = null;
	private int index = 0;
	private boolean isEmpty = true;

	public JComboCheckBox() { init(); }

	public JComboCheckBox(JCheckBox[] items) { 
		super(items); 
		init(); 
	}

	public JComboCheckBox(Vector items) { 
		super(items); 
		init(); 
	}

	public JComboCheckBox(ComboBoxModel aModel) { 
		super(aModel); 
		init(); 
	}

	private void init() {
		setRenderer(new ComboBoxRenderer());
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) { 
				itemSelected(); 
			}
		});
	}

	private void itemSelected() {
		if (getSelectedItem() instanceof JCheckBox) {
			jcb = (JCheckBox)getSelectedItem();
			jcb.setSelected(!jcb.isSelected());
			
			if(jcb.isSelected() && !listeGenres.contains(jcb.getText())){
				listeGenres.add(jcb.getText());
				index++;
			}else{ 
				listeGenres.remove(jcb.getText());
				index--;
			}
			if(index == 0){ isEmpty = true;}
			else {isEmpty = false;}
		}
	}
	
	public ArrayList<String> getGenres(){
		return listeGenres;
	}
	
	public boolean listIsEmpty(){
		return isEmpty;
	}

	/******************************************************
	 * ComboBoxRenderer
	 * 
	 * @Resumer:
	 * 
	 * @Source: http://esus.com/how-do-i-put-a-jcheckbox-in-a-jcombobox/
	 * 
	 ******************************************************/
	class ComboBoxRenderer implements ListCellRenderer {

		private JLabel label;

		public ComboBoxRenderer() { setOpaque(true); }

		public Component getListCellRendererComponent(JList list, Object value, int index, 
				boolean isSelected, boolean cellHasFocus) {
			if (value instanceof Component) {
				Component c = (Component)value;
				if (isSelected) {
					c.setBackground(list.getSelectionBackground());
					c.setForeground(list.getSelectionForeground());
				} else {
					c.setBackground(list.getBackground());
					c.setForeground(list.getForeground());
				}
				return c;
			} 
			else {
				if (label ==null) label = new JLabel(value.toString());
				else 			  label.setText(value.toString());
				return label;
			}
		}
	}
}
