package display;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import components.Menu;

/**
 * @author Jordan Aranda Tejada
 */
public class Window extends JFrame {

	private static final long	serialVersionUID	= - 8641413596663241575L;
	private static Window		instance;
	private Menu				menu;

	private Window()
	{
		super();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setIconImage((new ImageIcon("img/app-icon.png")).getImage());
		setSize(800, 600);
		setMinimumSize(new Dimension(450, 500));
		setLocationRelativeTo(null);
		setTitle("Language Creator");
		this.menu = new Menu();
		setJMenuBar(menu);
		addWindowListener(new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent winEvt)
			{
				if ( ! allSaved())
				{
					String[] options = {"Yes", "No"};
					int selection = JOptionPane
					.showOptionDialog(
					Window.getInstance(),
					"Some files have not been saved, are you sure you want to exit the application?",
					"Not Saved", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, new ImageIcon(
					"img/warning.png"), options, options[1]);

					if (selection == 0)
					{
						dispose();
					}
				}
				else
				{
					dispose();
				}
			}
		});
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
		super.pack();
		Dimension d = getSize();
		setSize(d);
	}

	private boolean allSaved()
	{
		Start startPanel = (Start) Window.getInstance().getContentPane();
		Vector<LangEditor> langEditors = startPanel.getLangEditors();
		int i = 0;
		boolean allSaved = true;
		while (allSaved && i < langEditors.size())
		{
			if (langEditors.get(i).isSaved())
			{
				i++;
			}
			else
			{
				allSaved = false;
			}
		}
		return allSaved;
	}
}