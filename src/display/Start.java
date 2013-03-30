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
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleContext;

import utils.ConsoleContent;

import components.ToolBar;

public class Start extends JPanel {

	private static final long	serialVersionUID	= - 3019955922941567348L;

	private ToolBar				toolBar;
	private Vector<LangEditor>	langEditors;
	private JSplitPane			splitPane;
	private JTabbedPane			tabs;
	private JPanel				panel;
	private JLabel				lblConsole;
	private JScrollPane			scrollPane;
	private JTextPane			textPane_console;

	public Start()
	{
		setLayout(new BorderLayout(0, 0));

		toolBar = new ToolBar();
		add(toolBar, BorderLayout.NORTH);

		langEditors = new Vector<LangEditor>();

		splitPane = new JSplitPane();
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(10);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
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

		JLabel signatureLabel = new JLabel("JDIX Developers");
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

	public void setToolBar(ToolBar toolBar)
	{
		this.toolBar = toolBar;
	}

	public JTextPane getTextPane_console()
	{
		return textPane_console;
	}

	public void setTextPane_console(JTextPane textPane_console)
	{
		this.textPane_console = textPane_console;
	}

	public Vector<LangEditor> getLangEditors()
	{
		return langEditors;
	}

	public void setLangEditors(Vector<LangEditor> langEditors)
	{
		this.langEditors = langEditors;
	}
}