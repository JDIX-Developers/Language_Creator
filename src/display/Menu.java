package display;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import utils.Lang;


/**
 * @author Razican (Iban Eguia)
 */
public class Menu extends JMenuBar implements ActionListener
{

	private static final long	serialVersionUID	= -2674054941368737779L;

	private JMenu				file, edit, help;
	private JMenuItem			open, save, save_where;

	/**
	 * Create the menu.
	 */
	public Menu()
	{
		super();

		file = new JMenu("File");
		file.setMargin(new Insets(5, 5, 5, 5));

		edit = new JMenu("Edit");
		edit.setMargin(new Insets(5, 5, 5, 5));

		help = new JMenu("Help");
		help.setMargin(new Insets(5, 5, 5, 5));
		
		open = new JMenuItem("Open");
		open.setMargin(new Insets(5, 5, 5, 5));
		open.addActionListener(this);
		
		save = new JMenuItem("Save");
		save.setMargin(new Insets(5, 5, 5, 5));
		save_where = new JMenuItem("Save in...");
		save_where.setMargin(new Insets(5, 5, 5, 5));

		file.add(open);
		file.add(save);
		file.add(save_where);

		add(file);
		add(edit);
		add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==open)
		{
			try{
				FileInputStream fis = new FileInputStream("lang/es_ES.lang");
				ObjectInputStream ois = new ObjectInputStream(fis);
				@SuppressWarnings("unchecked")
				HashMap<String,String> hashMap = (HashMap<String, String>)ois.readObject();
				ois.close();
				LangEditor lEdit = new LangEditor(hashMap);
				Window.getInstance().setContentPane(lEdit);
				lEdit.updateUI();
			}catch(Exception ex){
				System.out.println("Error al pulsar open");
			}
			
			Vector<String> v = Lang.getCombableLocales();
			for(int i=0; i<v.size(); i++)
			{
				System.out.println(v.get(i).toString());
			}
		}
	}
	
	
}