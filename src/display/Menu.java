package display;

import java.awt.FileDialog;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
			openAction();
		}
	}
	
	
	
	private void openAction()
	{
		FileDialog fileDialog = new FileDialog(Window.getInstance(),"Open file", FileDialog.LOAD); 
		FilenameFilter filter = new FileListFilter("", "lang");
		fileDialog.setFilenameFilter(filter);
		fileDialog.setVisible(true);
		
		String absolutePath = fileDialog.getDirectory()+fileDialog.getFile();
		System.out.println(absolutePath);
		
		String extension = fileDialog.getFile().substring(fileDialog.getFile().lastIndexOf('.'), fileDialog.getFile().length());
		System.out.println("Extension: "+extension);
		
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(absolutePath));
			@SuppressWarnings("unchecked")
			HashMap<String,String> hashMap = (HashMap<String, String>)ois.readObject();
			ois.close();
			LangEditor lEdit = new LangEditor(hashMap,absolutePath);
			Window.getInstance().setContentPane(lEdit);
			((JPanel) lEdit).updateUI();
		} catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error al abrir el fichero "+fileDialog.getFile());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al abrir el fichero "+fileDialog.getFile());
		}
	}
	class FileListFilter implements FilenameFilter {
		  private String name; 
		  private String extension; 

		  public FileListFilter(String name, String extension) {
		    this.name = name;
		    this.extension = extension;
		  }

		  public boolean accept(File directory, String filename) {
		    boolean fileOK = true;

		    if (name != null) {
		      fileOK &= filename.startsWith(name);
		    }

		    if (extension != null) {
		      fileOK &= filename.endsWith('.' + extension);
		    }
		    return fileOK;
		  }
		}
}