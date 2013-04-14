package components;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import utils.ConsoleContent;
import utils.FileChooser;
import utils.Lang;
import display.LangEditor;
import display.Start;

/**
 * @author Jordan Aranda Tejada
 */
public class ToolBar extends JToolBar implements ActionListener {

	private static final long	serialVersionUID	= - 4146568246110544527L;
	private JButton				btnNewFile;
	private JButton				btnOpenFile;
	private JButton				btnSaveFile;
	private JButton				btnSaveAsFile;
	private JButton				btnPrint;
	private JButton				btnAddRow;
	private JButton				btnRemoveRow;
	private JLabel				lblFilter;
	private JTextField			textField_Find;

	/**
	 * Create the toolbar.
	 */
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
		btnSaveAsFile.setToolTipText("Save File As...");
		btnSaveAsFile.addActionListener(this);

		btnPrint = new JButton(new ImageIcon("img/print-icon.png"));
		btnPrint.setFocusable(false);
		btnPrint.setToolTipText("Print File");
		btnPrint.addActionListener(this);

		btnAddRow = new JButton(new ImageIcon("img/newRow-icon.png"));
		btnAddRow.setFocusable(false);
		btnAddRow.setToolTipText("Add new row");
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(new ImageIcon("img/removeRow-icon.png"));
		btnRemoveRow.setFocusable(false);
		btnRemoveRow.setToolTipText("Remove row");
		btnRemoveRow.addActionListener(this);

		lblFilter = new JLabel(" Filter  ");
		lblFilter.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));

		textField_Find = new JTextField();
		textField_Find.setColumns(8);
		textField_Find.addKeyListener(new java.awt.event.KeyAdapter()
		{

			@Override
			public void keyTyped(java.awt.event.KeyEvent e)
			{
				Start startPanel = (Start) Window.getInstance()
				.getContentPane();
				JTabbedPane tabs = startPanel.getTabbedPane();

				int index = tabs.getSelectedIndex();
				if (index >= 0)
				{
					LangEditor langEditor = startPanel.getLangEditors().get(
					index);
					JTable table = langEditor.getTable();

					final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
					(TableModel) table.getModel());
					table.setRowSorter(sorter);
					String word = textField_Find.getText().trim();
					if (word.length() == 0)
					{
						sorter.setRowFilter(null);
					}
					else
					{
						try
						{
							sorter.setRowFilter(RowFilter.regexFilter(word));
						}
						catch (PatternSyntaxException pse)
						{
							System.err.println("Bad regex pattern");
						}
					}
				}
			}

			@Override
			public void keyPressed(java.awt.event.KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					textField_Find.setText("");
				}
			}
		});

		add(btnNewFile);
		add(btnOpenFile);
		add(btnSaveFile);
		add(btnSaveAsFile);
		add(btnPrint);
		addSeparator();
		add(btnAddRow);
		add(btnRemoveRow);
		addSeparator();
		add(lblFilter);
		add(textField_Find);
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
		else if (e.getSource() == btnSaveAsFile)
		{
			saveAsAction(tPane, langEditors);
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
		File file = FileChooser.openFile("Language file", "lang");
		if (file != null)
		{
			int i = isOpenFile(file, langEditors);
			if (i == langEditors.size())
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
					TabPanel pTab = new TabPanel(file.getName(),
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
				doc.addString("The file: " + file.toString()
				+ " is already open.");
				tabs.setSelectedIndex(i);
			}
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
					Start st = (Start) Window.getInstance().getContentPane();
					ConsoleContent doc = (ConsoleContent) st
					.getTextPane_console().getStyledDocument();
					doc.clearContent();
					try
					{
						ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(langEditor.getFile().toString()));
						oos.writeObject(langEditor.getFinaleHashMap());
						oos.close();
						langEditor.setSaved(true);
						doc.addString("The file "
						+ langEditor.getFile().toString()
						+ " successfully saved.");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					saveAsAction(tabs, langEditors);
				}
			}
		}
	}

	private void saveAsAction(JTabbedPane tabs, Vector<LangEditor> langEditors)
	{
		int index = tabs.getSelectedIndex();
		if (index >= 0)
		{
			LangEditor langEditor = langEditors.get(index);
			if (langEditor.isCorrectLang())
			{
				Start st = (Start) Window.getInstance().getContentPane();
				ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
				.getStyledDocument();
				doc.clearContent();

				String path = FileChooser.saveObjectFile(
				langEditor.getFinaleHashMap(), "Language File", "lang",
				langEditor.getFile());
				if (path != "")
				{
					langEditor.setSaved(true);
					langEditor.setLblFilePathText(path);
					langEditor.setFile(new File(path));
					doc.addString("The file " + langEditor.getFile().toString()
					+ " successfully saved.");
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