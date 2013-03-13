package display;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Start extends JPanel
{
	private static final long serialVersionUID = -3019955922941567348L;

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
}
