package display;

import javax.swing.JFrame;

/**
 * @author Razican (Iban Eguia)
 */
public class Window extends JFrame {

	private static final long	serialVersionUID	= -8641413596663241575L;
	private static Window		instance;

	private Window()
	{
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(null); // TODO set Animature World Icon
		setSize(800, 600);
		setLocationRelativeTo(null);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
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
}