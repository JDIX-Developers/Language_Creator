package display;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import components.Menu;

public class Window extends JFrame {

	private static final long	serialVersionUID	= - 8641413596663241575L;
	private static Window		instance;
	private Menu				menu;

	private Window()
	{
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage((new ImageIcon("img/app-icon.png")).getImage());
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Language Creator");
		this.menu = new Menu();
		setJMenuBar(menu);
	}

	public Menu getMenu()
	{
		return menu;
	}

	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}

	/**
	 * @return Instance for the current Window
	 */
	public static Window getInstance()
	{
		if (instance == null)
		{
			instance = new Window();
		}

		return instance;
	}

	@Override
	public void pack()
	{
		// CAMBIAR
		super.pack();
		Dimension d = getSize();
		setSize(d);
	}
}