package display;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * @author Razican (Iban Eguia)
 */
public class Menu extends JMenuBar implements ActionListener
{

	private static final long	serialVersionUID	= -2674054941368737779L;

	private JMenu				file, edit;
	private JMenuItem			help;
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

		help = new JMenuItem("Edit");
		help.setMargin(new Insets(5, 5, 5, 5));
		help.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Create help menu
			}
		});

		open = new JMenuItem("Open");
		open.setMargin(new Insets(5, 5, 5, 5));
		open.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("1");
				try{
					FileInputStream fis = new FileInputStream("/Users/jordanarandatejada/Documents/workspace/Animature_Map/lang/es_ES.lang");
					ObjectInputStream ois = new ObjectInputStream(fis);
					@SuppressWarnings("unchecked")
					HashMap<String,String> hashMap = (HashMap<String, String>)ois.readObject();
					ois.close();
					LangEditor lEdit = new LangEditor(hashMap);
					Window.getInstance().setContentPane(lEdit);
					lEdit.updateUI();
				}catch(Exception ex){
					System.out.println("Error al abrir el fichero");
				}
				System.out.println("2");
			}
		});
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
				FileInputStream fis = new FileInputStream("/Users/jordanarandatejada/Documents/workspace/Animature_Map/lang/es_ES.lang");
				ObjectInputStream ois = new ObjectInputStream(fis);
				@SuppressWarnings("unchecked")
				HashMap<String,String> hashMap = (HashMap<String, String>)ois.readObject();
				ois.close();
				Window.getInstance().setContentPane(new LangEditor(hashMap));
			}catch(Exception ex){
				System.out.println("Error al abrir el fichero");
			}
		}
	}
	
	
}