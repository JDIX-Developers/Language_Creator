package components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroFrame extends JFrame {

	private final PanelBackground	contentPane;

	public IntroFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane = new PanelBackground("img/intro-image.png");
		setContentPane(contentPane);
		getContentPane().setLayout(null);

		contentPane.updateUI();

		setVisible(true);
	}

	class PanelBackground extends JPanel {

		private ImageIcon		image;
		private final String	name;

		public PanelBackground(final String name)
		{
			this.name = name;
		}

		@Override
		public void paint(final Graphics g)
		{
			final Dimension size = getSize();
			image = new ImageIcon(name);
			g.drawImage(image.getImage(), 0, 0, size.width, size.height, null);
			setOpaque(false);
			super.paint(g);
		}
	}
}