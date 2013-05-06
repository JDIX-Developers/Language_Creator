package utils;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * @author Jordan Aranda Tejada
 */
public class ConsoleContent extends DefaultStyledDocument {

	private static final long	serialVersionUID	= - 5070493290147897938L;
	private final StyleContext	sC;

	/**
	 * Create Console Content
	 * 
	 * @param sC The style context for document.
	 */
	public ConsoleContent(final StyleContext sC)
	{
		super(sC);
		this.sC = sC;
	}

	/**
	 * Add text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addString(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.BLACK);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add red text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addErrorMessage(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.RED);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add orange text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addWarningMessage(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.ORANGE);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Clearn the document text
	 * 
	 * @param string The message for document.
	 */
	public void cleanContent()
	{
		try
		{
			super.remove(0, getLength());
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
