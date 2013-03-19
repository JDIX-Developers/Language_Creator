package display;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import utils.FileMode;
import utils.Lang;
import utils.TabPanel;

public class Menu extends JMenuBar implements ActionListener {

	private static final long	serialVersionUID	= -2674054941368737779L;

	private JMenu				file, edit, help;
	private JMenuItem			newLang, open, save, save_as, print;

	/**
	 * Create the menu.
	 */
	public Menu()
	{
		super();

		file = new JMenu("File");
		file.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		file.setMargin(new Insets(5, 5, 5, 5));

		edit = new JMenu("Edit");
		edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		edit.setMargin(new Insets(5, 5, 5, 5));

		help = new JMenu("Help");
		help.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		help.setMargin(new Insets(5, 5, 5, 5));

		newLang = new JMenuItem("New...");
		newLang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newLang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
		InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		newLang.setIcon(new ImageIcon("img/new-icon.png"));
		newLang.setMargin(new Insets(5, 5, 5, 5));
		newLang.addActionListener(this);

		open = new JMenuItem("Open");
		open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
		InputEvent.CTRL_MASK));
		open.setIcon(new ImageIcon("img/open-icon.png"));
		open.setMargin(new Insets(5, 5, 5, 5));
		open.addActionListener(this);

		save = new JMenuItem("Save");
		save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
		InputEvent.CTRL_MASK));
		save.setIcon(new ImageIcon("img/save-icon.png"));
		save.setMargin(new Insets(5, 5, 5, 5));

		save_as = new JMenuItem("Save as...");
		save_as.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		save_as.setMargin(new Insets(5, 5, 5, 5));
		save_as.setIcon(new ImageIcon("img/save-as-icon.png"));

		print = new JMenuItem("Print...");
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
		InputEvent.CTRL_MASK));
		print.setIcon(new ImageIcon("img/print-icon.png"));
		print.setMargin(new Insets(5, 5, 5, 5));

		file.add(newLang);
		file.add(open);
		file.add(save);
		file.add(save_as);
		file.add(print);

		add(file);
		add(edit);
		add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Start startPanel = (Start) Window.getInstance().getContentPane();
		JTabbedPane tPane = startPanel.getTabbedPane();
		Vector<String> vector = startPanel.getOpenFiles();

		if (e.getSource() == newLang)
		{
			newLangAction(tPane);
		}
		else if (e.getSource() == open)
		{
			openAction(tPane, vector);
		}

	}

	private void newLangAction(JTabbedPane tabs)
	{
		Vector<String> vect = Lang.getCombableLocales();

		String selection = (String) JOptionPane.showInputDialog(null,
		"Select a language", "Select a language", JOptionPane.QUESTION_MESSAGE,
		new ImageIcon("img/app-icon.png"), vect.toArray(), vect.get(0));

		if (selection != null)
		{
			Locale l = new Locale(selection);
			File file = new File(l.getLanguage());
			System.out.println("* " + l.getDisplayName().toString());

			LangEditor langEditor = new LangEditor(
			new HashMap<String, String>(), file);
			tabs.addTab(file.getName(), langEditor);
			tabs.setSelectedIndex(tabs.getTabCount() - 1);
		}
	}

	private void openAction(JTabbedPane tabs, Vector<String> openFiles)
	{
		File file = FileMode.openFileMode("Language file", "lang");
		int i = isOpenFile(file, openFiles);
		if (file != null && i == openFiles.size())
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(file.toString()));
				@SuppressWarnings ("unchecked")
				HashMap<String, String> hashMap = (HashMap<String, String>) ois
				.readObject();
				ois.close();

				LangEditor langEditor = new LangEditor(hashMap, file);
				TabPanel pTab = new TabPanel(file.getName(), hashMap.size(),
				file.toString(), tabs);
				tabs.addTab(file.getName() + " (" + hashMap.size() + ")",
				langEditor);

				tabs.setTabComponentAt(tabs.getTabCount() - 1, pTab);
				tabs.setSelectedIndex(tabs.getTabCount() - 1);

				openFiles.add(file.toString());
			}
			catch (IOException | ClassNotFoundException e)
			{
				JOptionPane.showMessageDialog(null,
				"Error al abrir el fichero " + file.toString());
			}
		}
		else if (i < openFiles.size())
		{
			tabs.setSelectedIndex(i);
		}
	}

	private int isOpenFile(File file, Vector<String> openFiles)
	{
		boolean enc = false;
		int i = 0;
		while (!enc && i < openFiles.size())
		{
			if (file.toString().equals(openFiles.get(i)))
			{
				enc = true;
			}
			else
			{
				i++;
			}
		}
		return i;
	}
}