package utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import display.Start;
import display.Window;

public class TabPanel extends JPanel implements ActionListener {

	private static final long	serialVersionUID	= 8155818731609154350L;
	private String				title				= null;
	private JLabel				label				= null;
	private JButton				button				= null;
	private JTabbedPane			tabs				= null;
	private String				filePath;
	private int					lines;

	public TabPanel(String title, int lines, String filePath, JTabbedPane tabs)
	{
		this.lines = lines;
		this.filePath = filePath;
		this.title = title + " (" + this.lines + ")";
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
		if (removeTab(title))
		{
			Start st = (Start) Window.getInstance().getContentPane();
			Vector<String> vect = st.getOpenFiles();
			boolean enc = false;
			int i = 0;
			while (i < vect.size() && !enc)
			{
				if (this.filePath.equals(vect.get(i)))
				{
					enc = true;
				}
				else
				{
					i++;
				}
			}
			if (enc)
			{
				vect.remove(i);
			}
		}
	}

	public boolean removeTab(String title)
	{
		for (int index = 0; index < tabs.getTabCount(); index++)
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

	public int getLines()
	{
		return lines;
	}

	public void setLines(int lines)
	{
		this.lines = lines;
	}

}