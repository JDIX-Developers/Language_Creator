package components;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import utils.ConsoleContent;
import utils.FileMode;
import utils.Lang;
import utils.TabPanel;
import display.LangEditor;
import display.Start;
import display.Window;

public class ToolBar extends JToolBar implements ActionListener {

	private JButton	btnNewFile;
	private JButton	btnOpenFile;
	private JButton	btnSaveFile;
	private JButton	btnSaveAsFile;
	private JButton	btnPrint;
	private JButton	btnAddRow;
	private JButton	btnRemoveRow;

	public ToolBar()
	{
		super();
		setMargin(new Insets(0, 5, 0, 5));

		btnNewFile = new JButton(new ImageIcon("img/new-icon.png"));
		btnNewFile.setFocusable(false);
		btnNewFile.setToolTipText("New File");
		btnNewFile.addActionListener(this);

		btnOpenFile = new JButton(new ImageIcon("img/open-icon.png"));
		btnOpenFile.setFocusable(false);
		btnOpenFile.setToolTipText("Open File");
		btnOpenFile.addActionListener(this);

		btnSaveFile = new JButton(new ImageIcon("img/save-icon.png"));
		btnSaveFile.setFocusable(false);
		btnSaveFile.setToolTipText("Save File");
		btnSaveFile.addActionListener(this);

		btnSaveAsFile = new JButton(new ImageIcon("img/save-as-icon.png"));
		btnSaveAsFile.setFocusable(false);

		btnPrint = new JButton(new ImageIcon("img/print-icon.png"));
		btnPrint.setFocusable(false);
		btnPrint.setToolTipText("Print File");
		btnPrint.addActionListener(this);

		btnAddRow = new JButton(new ImageIcon("img/newRow-icon.png"));
		btnAddRow.setFocusable(false);
		btnAddRow.setToolTipText("Add new row.");
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(new ImageIcon("img/removeRow-icon.png"));
		btnRemoveRow.setFocusable(false);
		btnRemoveRow.setToolTipText("Remove row.");
		btnRemoveRow.addActionListener(this);

		add(btnNewFile);
		add(btnOpenFile);
		add(btnSaveFile);
		add(btnSaveAsFile);
		add(btnPrint);
		addSeparator();
		add(btnAddRow);
		add(btnRemoveRow);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Start startPanel = (Start) Window.getInstance().getContentPane();
		JTabbedPane tPane = startPanel.getTabbedPane();
		Vector<LangEditor> langEditors = startPanel.getLangEditors();

		if (e.getSource() == btnNewFile)
		{
			newLangAction(tPane, langEditors);
		}
		else if (e.getSource() == btnOpenFile)
		{
			openAction(tPane, langEditors);
		}
		else if (e.getSource() == btnSaveFile)
		{
			saveAction(tPane, langEditors);
		}
		else if (e.getSource() == btnPrint)
		{
			printAction(tPane, startPanel);
		}
		else if (e.getSource() == btnAddRow)
		{
			addRowAction(tPane);
		}
		else if (e.getSource() == btnRemoveRow)
		{
			removeRowAction(tPane);
		}
	}

	private void newLangAction(JTabbedPane tabs, Vector<LangEditor> langEditors)
	{
		Start st = (Start) Window.getInstance().getContentPane();
		ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();

		Vector<String> vect = Lang.getCombableAvalaibleLocales();

		String selection = (String) JOptionPane.showInputDialog(null,
		"Select a language", "Select a language", JOptionPane.QUESTION_MESSAGE,
		new ImageIcon("img/app-icon.png"), vect.toArray(), vect.get(0));

		if (selection != null)
		{
			File file = new File(Lang.getNameFileLang(selection));
			int i = isOpenFile(file, langEditors);
			if (i == langEditors.size())
			{
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("", "");
				LangEditor langEditor = new LangEditor(hashMap, file);
				TabPanel pTab = new TabPanel(file.getName(), file.toString(),
				tabs);
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

	private void openAction(JTabbedPane tabs, Vector<LangEditor> langEditors)
	{
		Start st = (Start) Window.getInstance().getContentPane();
		ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();
		File file = FileMode.openFileMode("Language file", "lang");
		int i = isOpenFile(file, langEditors);
		if (file != null && i == langEditors.size())
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
				st.getLangEditors().add(langEditor);
				TabPanel pTab = new TabPanel(file.getName(), file.toString(),
				tabs);
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

	private void saveAction(JTabbedPane tabs, Vector<LangEditor> langEditors)
	{
		int index = tabs.getSelectedIndex();
		if (index >= 0)
		{
			LangEditor langEditor = langEditors.get(index);
			if (langEditor.isCorrectLang())
			{
				if (langEditor.getFile().exists())
				{
					try
					{
						ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(langEditor.toString()));
						// oos.writeObject();
						oos.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void printAction(JTabbedPane tabs, Start startPanel)
	{
		int index = tabs.getSelectedIndex();
		if (index >= 0)
		{
			LangEditor lEditor = startPanel.getLangEditors().get(index);
			try
			{
				lEditor.getTable().print();
			}
			catch (PrinterException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void addRowAction(JTabbedPane tPane)
	{
		int index = tPane.getSelectedIndex();
		Start st = (Start) Window.getInstance().getContentPane();
		if (index >= 0)
		{
			LangEditor lEditor = st.getLangEditors().get(index);
			lEditor.insertRow();
		}

	}

	private void removeRowAction(JTabbedPane tPane)
	{
		int index = tPane.getSelectedIndex();
		Start st = (Start) Window.getInstance().getContentPane();
		if (index >= 0)
		{
			LangEditor lEditor = st.getLangEditors().get(index);
			lEditor.deleteRow();
		}
	}

	private int isOpenFile(File file, Vector<LangEditor> langEditors)
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