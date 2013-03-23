package display;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import utils.FileMode;
import utils.TabPanel;

public class ToolBar extends JToolBar implements ActionListener {

	private JButton	btnNewFile;
	private JButton	btnOpenFile;
	private JButton	btnSaveFile;
	private JButton	btnSaveAsFile;
	private JButton	btnPrint;

	public ToolBar()
	{
		super();
		setMargin(new Insets(0, 5, 0, 5));

		btnNewFile = new JButton(new ImageIcon("img/new-icon.png"));
		btnNewFile.setToolTipText("New File");
		btnOpenFile = new JButton(new ImageIcon("img/open-icon.png"));
		btnOpenFile.setToolTipText("Open File");
		btnOpenFile.addActionListener(this);
		btnSaveFile = new JButton(new ImageIcon("img/save-icon.png"));
		btnSaveFile.setToolTipText("Save File");
		btnSaveAsFile = new JButton(new ImageIcon("img/save-as-icon.png"));
		btnPrint = new JButton(new ImageIcon("img/print-icon.png"));
		btnPrint.setToolTipText("Print File");

		add(btnNewFile);
		add(btnOpenFile);
		add(btnSaveFile);
		add(btnSaveAsFile);
		add(btnPrint);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Start startPanel = (Start) Window.getInstance().getContentPane();
		JTabbedPane tPane = startPanel.getTabbedPane();
		Vector<String> vector = startPanel.getOpenFiles();
		if (e.getSource() == btnNewFile)
		{

		}
		else if (e.getSource() == btnOpenFile)
		{
			openAction(tPane, vector);
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

	public JButton getBtnNewFile()
	{
		return btnNewFile;
	}

	public void setBtnNewFile(JButton btnNewFile)
	{
		this.btnNewFile = btnNewFile;
	}

	public JButton getBtnOpenFile()
	{
		return btnOpenFile;
	}

	public void setBtnOpenFile(JButton btnOpenFile)
	{
		this.btnOpenFile = btnOpenFile;
	}

	public JButton getBtnSaveFile()
	{
		return btnSaveFile;
	}

	public void setBtnSaveFile(JButton btnSaveFile)
	{
		this.btnSaveFile = btnSaveFile;
	}

	public JButton getBtnSaveAsFile()
	{
		return btnSaveAsFile;
	}

	public void setBtnSaveAsFile(JButton btnSaveAsFile)
	{
		this.btnSaveAsFile = btnSaveAsFile;
	}

	public JButton getBtnPrint()
	{
		return btnPrint;
	}

	public void setBtnPrint(JButton btnPrint)
	{
		this.btnPrint = btnPrint;
	}
}