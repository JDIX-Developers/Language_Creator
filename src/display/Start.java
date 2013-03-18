package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Start extends JPanel {

	private static final long	serialVersionUID	= -3019955922941567348L;

	private JTabbedPane			tabs;

	public Start()
	{
		setLayout(new BorderLayout(0, 0));

		tabs = new JTabbedPane(JTabbedPane.TOP);
		add(tabs, BorderLayout.CENTER);

		JLabel copyLabel = new JLabel("JDIX Developers");
		copyLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
		copyLabel.setForeground(Color.GRAY);
		copyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		copyLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		copyLabel.setBorder(new EmptyBorder(0, 0, 3, 7));
		add(copyLabel, BorderLayout.SOUTH);
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		Window.getInstance().setJMenuBar(new Menu());
		Window.getInstance().setContentPane(new Start());
		Window.getInstance().setVisible(true);
	}

	/**
	 * @return Current tabbed pane of the view
	 */
	public JTabbedPane getTabbedPane()
	{
		return tabs;
	}
}