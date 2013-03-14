package display;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JMenuBar implements ActionListener {

	private static final long	serialVersionUID	= -2674054941368737779L;

	private JMenu				file, edit, help;
	private JMenuItem			newLang, open, save, save_where;

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

		newLang = new JMenuItem("New...");
		newLang.setMargin(new Insets(5, 5, 5, 5));
		open = new JMenuItem("Open");
		open.setMargin(new Insets(5, 5, 5, 5));
		open.addActionListener(this);
		save = new JMenuItem("Save");
		save.setMargin(new Insets(5, 5, 5, 5));
		save_where = new JMenuItem("Save in...");
		save_where.setMargin(new Insets(5, 5, 5, 5));

		file.add(newLang);
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
		if (e.getSource() == open)
		{
			openAction();
		}
	}

	private void openAction()
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter langFilter = new FileNameExtensionFilter(
		"LANG", "lang");
		fileChooser.setFileFilter(langFilter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int op = fileChooser.showOpenDialog(null);

		if (op == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			String absolutePath = file.getAbsolutePath();
			try
			{
				ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(absolutePath));
				@SuppressWarnings ("unchecked")
				HashMap<String, String> hashMap = (HashMap<String, String>) ois
				.readObject();
				ois.close();
				LangEditor langEditor = new LangEditor(hashMap, absolutePath);
				Window.getInstance().setContentPane(langEditor);
				((JPanel) langEditor).updateUI();
			}
			catch (IOException | ClassNotFoundException e)
			{
				JOptionPane.showMessageDialog(null,
				"Error al abrir el fichero " + absolutePath);
			}
		}
	}
}