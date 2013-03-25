package display;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
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
		contentPane.add(new IntroPlayer(new File("media/jdix-intro.avi")));

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
		frame.dispose();

		Window.getInstance().setVisible(true);
		((Start) Window.getInstance().getContentPane()).getTabbedPane()
		.requestFocus();
	}

	class IntroPlayer extends JPanel {

		private static final long	serialVersionUID	= - 2606088515040690895L;

		public IntroPlayer(File file)
		{
			setLayout(new BorderLayout());
			Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
			try
			{
				URL url = file.toURL();
				Player mediaPlayer = Manager.createRealizedPlayer(url);

				Component video = mediaPlayer.getVisualComponent();

				if (video != null)
				{
					add(video, BorderLayout.CENTER);
				}
				mediaPlayer.start();

			}
			catch (NoPlayerException noplayerexception)
			{

				System.out.println("File not exist.");
			}
			catch (CannotRealizeException noreleace)
			{

				System.out.println("Invalid file.");
			}
			catch (IOException es)
			{
				System.out.println("Error reading file.");
			}
		}
	}
}