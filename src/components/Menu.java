package components;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utils.ConsoleContent;
import utils.FileChooser;
import utils.Lang;
import display.LangEditor;
import display.Preferences;
import display.Start;

/**
 * @author Jordan Aranda Tejada
 */
public class Menu extends JMenuBar implements ActionListener {

	private static final long	serialVersionUID	= - 2674054941368737779L;

	private final JMenu			file, edit, window, help;
	private final JMenuItem		newLang, open, save, save_as, print,
	preferences, showToolBar, showConsole;

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

		window = new JMenu("Window");
		window.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		window.setMargin(new Insets(5, 5, 5, 5));

		help = new JMenu("Help");
		help.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		help.setMargin(new Insets(5, 5, 5, 5));

		newLang = new JMenuItem("New File");
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
		print.addActionListener(this);

		preferences = new JMenuItem("Preferences");
		preferences.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		preferences.setIcon(new ImageIcon("img/sett-icon.png"));
		preferences.addActionListener(this);

		showToolBar = new JMenuItem("Hide ToolBar");
		showToolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showToolBar.addActionListener(this);

		showConsole = new JMenuItem("Hide Console");
		showConsole.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showConsole.addActionListener(this);

		file.add(newLang);
		file.add(open);
		file.add(save);
		file.add(save_as);
		file.add(print);

		edit.add(preferences);

		window.add(showToolBar);
		window.add(showConsole);

		add(file);
		add(edit);
		add(window);
		add(help);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final Start startPanel = (Start) Window.getInstance().getContentPane();
		final JTabbedPane tPane = startPanel.getTabbedPane();
		final Vector<LangEditor> langEditors = startPanel.getLangEditors();

		if (e.getSource() == preferences)
		{
			preferencesAction();
		}
		else if (e.getSource() == newLang)
		{
			newLangAction(tPane, langEditors);
		}
		else if (e.getSource() == open)
		{
			openAction(tPane, langEditors);
		}
		else if (e.getSource() == print)
		{
			printAction(tPane, startPanel);
		}
		else if (e.getSource() == showToolBar)
		{
			final ToolBar tB = startPanel.getToolBar();
			if (showToolBar.getText().equals("Hide ToolBar"))
			{
				showToolBar.setText("Show ToolBar");
				tB.setVisible(false);
			}
			else
			{
				showToolBar.setText("Hide ToolBar");
				tB.setVisible(true);
			}
		}
		else if (e.getSource() == showConsole)
		{
			final JSplitPane panel = startPanel.getSplitPane();
			if (showConsole.getText().equals("Hide Console"))
			{
				showConsole.setText("Show Console");
				panel.getRightComponent().setVisible(false);
				panel.setDividerSize(0);
			}
			else
			{
				showConsole.setText("Hide Console");
				panel.setDividerSize(10);
				panel.getRightComponent().setVisible(true);
				panel.setDividerLocation(panel.getHeight() - 150);
				panel.revalidate();
				panel.repaint();
			}
		}
	}

	private void newLangAction(final JTabbedPane tabs,
	final Vector<LangEditor> langEditors)
	{
		final Start st = (Start) Window.getInstance().getContentPane();
		final ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();

		final Vector<String> vect = Lang.getCombableAvalaibleLocales();

		final String selection = (String) JOptionPane.showInputDialog(null,
		"Select a language", "Select a language", JOptionPane.QUESTION_MESSAGE,
		new ImageIcon("img/app-icon.png"), vect.toArray(), vect.get(0));

		if (selection != null)
		{
			final File file = new File(Lang.getNameFileLang(selection));
			final int i = isOpenFile(file, langEditors);
			if (i == langEditors.size())
			{
				final HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("", "");
				final LangEditor langEditor = new LangEditor(hashMap, file);
				final TabPanel pTab = new TabPanel(file.getName(),
				file.toString(), tabs);
				tabs.addTab(file.getName(), langEditor);
				langEditor.setSaved(false);

				tabs.setTabComponentAt(tabs.getTabCount() - 1, pTab);
				tabs.setSelectedIndex(tabs.getTabCount() - 1);

				st.getLangEditors().add(langEditor);

				doc
				.addString("A new language file has been succesfully created.");
			}
			else if (i < langEditors.size())
			{
				doc.addString("The file: " + file.toString()
				+ " is already open.");
				tabs.setSelectedIndex(i);
			}
		}
	}

	private void openAction(final JTabbedPane tabs,
	final Vector<LangEditor> langEditors)
	{
		final Start st = (Start) Window.getInstance().getContentPane();
		final ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();
		final File file = FileChooser.openFile("Language file", "lang");
		final int i = isOpenFile(file, langEditors);
		if (file != null && i == langEditors.size())
		{
			try
			{
				final ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(file.toString()));
				@SuppressWarnings ("unchecked")
				final HashMap<String, String> hashMap = (HashMap<String, String>) ois
				.readObject();
				ois.close();

				final LangEditor langEditor = new LangEditor(hashMap, file);
				st.getLangEditors().add(langEditor);
				final TabPanel pTab = new TabPanel(file.getName(),
				file.toString(), tabs);
				tabs.addTab(file.getName(), langEditor);

				tabs.setTabComponentAt(tabs.getTabCount() - 1, pTab);
				tabs.setSelectedIndex(tabs.getTabCount() - 1);

				doc.addString("The file: " + file.toString()
				+ "  successfully opened.");
			}
			catch (IOException | ClassNotFoundException e)
			{
				doc.addErrorMessage("Error opening: " + file.toString()
				+ " file");
			}
		}
		else if (i < langEditors.size())
		{
			doc.addString("The file: " + file.toString() + " is already open.");
			tabs.setSelectedIndex(i);
		}
	}

	private void printAction(final JTabbedPane tPane, final Start startPanel)
	{
		final int index = tPane.getSelectedIndex();
		if (index >= 0)
		{
			final LangEditor lEditor = startPanel.getLangEditors().get(index);
			try
			{
				lEditor.getTable().print();
			}
			catch (final PrinterException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void preferencesAction()
	{
		final Preferences p = new Preferences();

		final String[] options = {"OK", "Cancel"};
		final JOptionPane pane = new JOptionPane(p, JOptionPane.PLAIN_MESSAGE,
		JOptionPane.OK_CANCEL_OPTION, null, options, options[1]);
		final JDialog dialog = pane.createDialog("Preferences");
		dialog.setLocationRelativeTo(Window.getInstance());
		dialog.setVisible(true);

		if (pane.getValue() == options[0])
		{
			utils.Preferences.setLookAndFeelClass(p.getLookAndFeel());

			try
			{
				UIManager.setLookAndFeel(p.getLookAndFeel());
			}
			catch (ClassNotFoundException | InstantiationException
			| IllegalAccessException | UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			}

			SwingUtilities.updateComponentTreeUI(Window.getInstance());
			Window.getInstance().pack();
		}
		dialog.dispose();
	}

	private int isOpenFile(final File file, final Vector<LangEditor> langEditors)
	{
		boolean enc = false;
		int i = 0;
		while ( ! enc && i < langEditors.size())
		{
			if (file.toString().equals(langEditors.get(i).getFile().toString()))
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