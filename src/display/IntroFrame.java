package display;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class IntroFrame extends JFrame {

	private JPanel	contentPane;

	public IntroFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// AÑADIR CONTENIDO
	}

	public static void main(String[] args)
	{
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
		IntroFrame frame = new IntroFrame();
		frame.setVisible(true);
		try
		{
			Thread.sleep(4000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		frame.dispose();
		Window.getInstance().setVisible(true);
		((Start) Window.getInstance().getContentPane()).getTabbedPane()
		.requestFocus();
	}
}