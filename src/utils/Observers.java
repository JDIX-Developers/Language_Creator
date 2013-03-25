package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import display.Menu;
import display.Window;

public class Observers implements ActionListener {

	private Window	window;
	private Menu	menu;

	public Observers(Window window)
	{
		this.window = window;
		this.menu = window.getInstance().getMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}
}