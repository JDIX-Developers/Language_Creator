package display;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {

	public ToolBar()
	{
		super();
		setMargin(new Insets(0, 5, 0, 5));

		JButton btnNewFile = new JButton(new ImageIcon("img/new-icon.png"));
		btnNewFile.setToolTipText("New File");
		JButton btnOpenFile = new JButton(new ImageIcon("img/open-icon.png"));
		btnOpenFile.setToolTipText("Open File");
		JButton btnSaveFile = new JButton(new ImageIcon("img/save-icon.png"));
		btnSaveFile.setToolTipText("Save File");
		JButton btnSaveAsFile = new JButton(new ImageIcon(
		"img/save-as-icon.png"));
		JButton btnPrint = new JButton(new ImageIcon("img/print-icon.png"));
		btnPrint.setToolTipText("Print File");

		add(btnNewFile);
		add(btnOpenFile);
		add(btnSaveFile);
		add(btnSaveAsFile);
		add(btnPrint);
	}
}