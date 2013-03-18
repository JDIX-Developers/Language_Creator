package display;

import java.awt.Cursor;
import java.awt.Dimension;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import utils.FileMode;

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

		if (e.getSource() == open)
		{
			openAction(startPanel.getTabbedPane());
		}
	}

	private void openAction(JTabbedPane tabs)
	{
		File file = FileMode.openFileMode("LANG", "lang");
		if (file != null)
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(file.toString()));
				@SuppressWarnings ("unchecked")
				HashMap<String, String> hashMap = (HashMap<String, String>) ois
				.readObject();
				ois.close();

				LangEditor langEditor = new LangEditor(hashMap, file.getName());
				tabs.addTab(file.getName(), langEditor);

				PanelTab pTab = new PanelTab(file.getName(), tabs);
				tabs.setTabComponentAt(tabs.getTabCount() - 1, pTab);
				tabs.setSelectedIndex(tabs.getTabCount() - 1);
			}
			catch (IOException | ClassNotFoundException e)
			{
				JOptionPane.showMessageDialog(null,
				"Error al abrir el fichero " + file.toString());
			}
		}
	}

	private class PanelTab extends JPanel implements ActionListener {

		private static final long	serialVersionUID	= 8155818731609154350L;
		private String				title				= null;
		private JLabel				label				= null;
		private JButton				button				= null;
		private JTabbedPane			tabs				= null;

		public PanelTab(String title, JTabbedPane tabs)
		{
			this.title = title;
			this.label = new JLabel(title);
			this.button = new JButton(new ImageIcon("img/x-icon.png"));
			this.button.setPreferredSize(new Dimension(20, 20));
			this.button.setContentAreaFilled(false);
			this.tabs = tabs;
			button.addActionListener(this);
			this.add(label);
			this.add(button);
			this.setOpaque(false);
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			removeTab(title);
		}

		public boolean removeTab(String title)
		{
			int i = tabs.getTabCount();
			for (int index = 0; index < i; index++)
			{
				String temp = tabs.getTitleAt(index);
				if (temp.equals(title))
				{
					tabs.removeTabAt(index);
					return true;
				}
			}
			return false;
		}
	}
}