package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Razican (Iban Eguia)
 */
public class Preferences implements Serializable {

	private static final long	serialVersionUID	= - 1140374742103678200L;
	private static Preferences	preferences;
	private Locale				locale;
	private String				lookAndFeelClass;

	private Preferences(Locale l, String lf)
	{
		locale = l;
		lookAndFeelClass = lf;
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
			preferences = new Preferences(Locale.getDefault(),
			UIManager.getSystemLookAndFeelClassName());
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
			if ( ! (e instanceof FileNotFoundException))
			{
				e.printStackTrace();
			}

			preferences = new Preferences(Locale.getDefault(),
			UIManager.getSystemLookAndFeelClassName());
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
	 * @return Current LookAndFeel
	 */
	public static String getLookAndFeel()
	{
		if (preferences == null)
		{
			init();
		}

		return preferences.lookAndFeelClass;
	}

	/**
	 * @param lf The new Look and feel to set
	 */
	public static void setLookAndFeelClass(String lf)
	{
		if (preferences == null)
		{
			init();
		}

		if (isLFAvailable(lf))
		{
			preferences.lookAndFeelClass = lf;
		}

		preferences.update();
	}

	private static boolean isLFAvailable(String lf)
	{
		LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo lf2: lfs)
		{
			if (lf2.getClassName().equals(lf))
			{
				return true;
			}
		}
		return false;
	}
}