package utils;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ConsoleContent extends DefaultStyledDocument {

	private StyleContext	sC;

	public ConsoleContent()
	{
		super();
	}

	public ConsoleContent(StyleContext sC)
	{
		super(sC);
		this.sC = sC;
	}

	@Override
	public int getLength()
	{
		return super.getLength();
	}

	public void addString(String string)
	{
		Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.BLACK);
		try
		{
			super.insertString(getLength(), string + "\n", style);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void addErrorMessage(String string)
	{
		Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.RED);
		try
		{
			super.insertString(getLength(), string + "\n", style);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void addWarningMessage(String string)
	{
		Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.ORANGE);
		try
		{
			super.insertString(getLength(), string + "\n", style);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void clearContent()
	{
		try
		{
			super.remove(0, getLength());
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
