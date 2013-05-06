package components;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import display.LangEditor;
import display.Start;

/**
 * @author Jordan Aranda Tejada
 */
public class Menu extends JMenuBar implements ActionListener {

	private static final long	serialVersionUID	= - 2674054941368737779L;

	private JMenu				file, window, help;
	private JMenuItem			newLang, open, save, save_as, print,
	showToolBar, showConsole;

	/**
	 * Create the menu.
	 */
	public Menu()
	{
		super();

		file = new JMenu("File");
		file.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		file.setMargin(new Insets(5, 5, 5, 5));

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
		save.addActionListener(this);

		save_as = new JMenuItem("Save as...");
		save_as.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		save_as.setMargin(new Insets(5, 5, 5, 5));
		save_as.setIcon(new ImageIcon("img/save-as-icon.png"));
		save_as.addActionListener(this);

		print = new JMenuItem("Print...");
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
		InputEvent.CTRL_MASK));
		print.setIcon(new ImageIcon("img/print-icon.png"));
		print.setMargin(new Insets(5, 5, 5, 5));
		print.addActionListener(this);

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

		window.add(showToolBar);
		window.add(showConsole);

		add(file);
		add(window);
		add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Start startPanel = (Start) Window.getInstance().getContentPane();
		JTabbedPane tabs = startPanel.getTabbedPane();
		Vector<LangEditor> langEditors = startPanel.getLangEditors();

		if (e.getSource() == newLang)
		{
			ToolBar.newLangAction(tabs, langEditors);
		}
		else if (e.getSource() == open)
		{
			ToolBar.openAction(tabs, langEditors);
		}
		else if (e.getSource() == save)
		{
			ToolBar.saveAction(tabs, langEditors);
		}
		else if (e.getSource() == save_as)
		{
			ToolBar.saveAsAction(tabs, langEditors);
		}
		else if (e.getSource() == print)
		{
			ToolBar.printAction(tabs, startPanel);
		}
		else if (e.getSource() == showToolBar)
		{
			ToolBar tB = startPanel.getToolBar();
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
			JSplitPane panel = startPanel.getSplitPane();
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
}