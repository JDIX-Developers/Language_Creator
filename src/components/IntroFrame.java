package components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Jordan Aranda Tejada
 */
public class IntroFrame extends JFrame {

	private static final long	serialVersionUID	= - 8447565784108781483L;
	private PanelBackground		contentPane;

	/**
	 * Create the frame.
	 */
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

	/**
	 * @author Jordan Aranda Tejada
	 */
	class PanelBackground extends JPanel {

		private static final long	serialVersionUID	= - 6298418879639583183L;
		private String				imagePath;

		/**
		 * Create the panel with image.
		 * 
		 * @param imagePath The background image.
		 */
		public PanelBackground(String imagePath)
		{
			this.imagePath = imagePath;
		}

		@Override
		public void paint(Graphics g)
		{
			Dimension size = getSize();
			ImageIcon image = new ImageIcon(imagePath);
			g.drawImage(image.getImage(), 0, 0, size.width, size.height, null);
			setOpaque(false);
			super.paint(g);
		}
	}
}