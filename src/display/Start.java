package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleContext;

import utils.ConsoleContent;

import components.ToolBar;
import components.Window;

/**
 * @author Jordan Aranda Tejada
 */
public class Start extends JPanel {

	private static final long	serialVersionUID	= - 3019955922941567348L;

	private ToolBar				toolBar;
	private Vector<LangEditor>	langEditors;
	private final JSplitPane	splitPane;
	private final JTabbedPane	tabs;
	private final JPanel		panel;
	private final JLabel		lblConsole;
	private final JScrollPane	scrollPane;
	private JTextPane			textPane_console;

	public static void main(final String[] args)
	{
		/*
		 * IntroFrame frame = new IntroFrame(); try { Thread.sleep(3100); }
		 * catch (InterruptedException e) { e.printStackTrace(); }
		 * frame.dispose();
		 */

		try
		{
			UIManager.setLookAndFeel(utils.Preferences.getLookAndFeel());
		}
		catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		Window.getInstance().setContentPane(new Start());
		Window.getInstance().setVisible(true);
		((Start) Window.getInstance().getContentPane()).getTabbedPane()
		.requestFocus();
	}

	public Start()
	{
		setLayout(new BorderLayout(0, 0));

		toolBar = new ToolBar();
		add(toolBar, BorderLayout.NORTH);

		langEditors = new Vector<LangEditor>();

		splitPane = new JSplitPane();
		splitPane.setDividerSize(10);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(splitPane.getHeight() - 150);
		add(splitPane, BorderLayout.CENTER);

		tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setFocusable(false);
		tabs.setPreferredSize(new Dimension(5, 350));
		splitPane.setLeftComponent(tabs);

		panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));

		lblConsole = new JLabel("Console");
		panel.add(lblConsole, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		textPane_console = new JTextPane(new ConsoleContent(new StyleContext()));
		scrollPane.setViewportView(textPane_console);

		final JLabel signatureLabel = new JLabel("JDIX Developers");
		signatureLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
		signatureLabel.setForeground(Color.GRAY);
		signatureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		signatureLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		signatureLabel.setBorder(new EmptyBorder(0, 0, 3, 7));
		add(signatureLabel, BorderLayout.SOUTH);
	}

	/**
	 * @return Current tabbed pane of the view
	 */
	public JTabbedPane getTabbedPane()
	{
		return tabs;
	}

	public ToolBar getToolBar()
	{
		return toolBar;
	}

	public void setToolBar(final ToolBar toolBar)
	{
		this.toolBar = toolBar;
	}

	public JTextPane getTextPane_console()
	{
		return textPane_console;
	}

	public void setTextPane_console(final JTextPane textPane_console)
	{
		this.textPane_console = textPane_console;
	}

	public Vector<LangEditor> getLangEditors()
	{
		return langEditors;
	}

	public void setLangEditors(final Vector<LangEditor> langEditors)
	{
		this.langEditors = langEditors;
	}

	public JPanel getConsolePanel()
	{
		return this.panel;
	}

	public JSplitPane getSplitPane()
	{
		return this.splitPane;
	}
}