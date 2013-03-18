package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * @author Razican (Iban Eguia)
 */
public class Preferences implements Serializable {

	private static final long	serialVersionUID	= -1140374742103678200L;
	private static Preferences	preferences;
	private Locale				locale;

	private Preferences(Locale l)
	{
		locale = l;
	}

	private void update()
	{
		ObjectOutputStream oos;
		try
		{
			oos = new ObjectOutputStream(new FileOutputStream("config.pref"));
			oos.writeObject(preferences);
			oos.close();
		}
		catch (IOException e)
		{
			preferences = new Preferences(Locale.getDefault());
		}
	}

	private static void init()
	{
		ObjectInputStream ois;
		try
		{
			ois = new ObjectInputStream(new FileInputStream("config.pref"));
			preferences = (Preferences) ois.readObject();
			ois.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			preferences = new Preferences(Locale.getDefault());
			preferences.update();
		}
	}

	/**
	 * @return Current locale
	 */
	public static Locale getLocale()
	{
		if (preferences == null)
		{
			init();
		}

		return preferences.locale;
	}

	/**
	 * @param l Locale to set as default
	 */
	public static void setLocale(Locale l)
	{
		if (preferences == null)
		{
			init();
		}

		if (Lang.getAvailableLocales().contains(l))
		{
			preferences.locale = l;
		}
		else
		{
			preferences.locale = Lang.getDefaultLocale();
		}

		preferences.update();
	}
}