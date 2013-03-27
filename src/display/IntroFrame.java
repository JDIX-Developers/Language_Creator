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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
<<<<<<< HEAD
		// AÑADIR CONTENIDO
=======

>>>>>>> parent of f6803ff... Se añade intro
	}

	public static void main(String[] args)
	{
		IntroFrame frame = new IntroFrame();
		frame.setVisible(true);
		try
		{
			Thread.sleep(5050);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
<<<<<<< HEAD
		Window.getInstance().setContentPane(new Start());
		Window.getInstance().setVisible(true);
		IntroFrame frame = new IntroFrame();
		frame.setVisible(true);
		try
		{
			Thread.sleep(4000);
=======
		frame.dispose();
		try
		{
			UIManager.setLookAndFeel(utils.Preferences.getLookAndFeel());
>>>>>>> parent of f6803ff... Se añade intro
		}
		catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
<<<<<<< HEAD
		frame.dispose();
=======

		Window.getInstance().setContentPane(new Start());
>>>>>>> parent of f6803ff... Se añade intro
		Window.getInstance().setVisible(true);
		((Start) Window.getInstance().getContentPane()).getTabbedPane()
		.requestFocus();
	}
}