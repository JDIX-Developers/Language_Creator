package utils;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

/**
 * @author Razican (Iban Eguia)
 */
public class Lang {

	private static Vector<Locale>	locales;

	private Lang()
	{
	}

	/**
	 * @return All available locales, ready to be used in a JComboBox
	 */
	public static Vector<String> getCombableAvalaibleLocales()
	{
		final Locale list[] = DateFormat.getAvailableLocales();
		final Vector<String> combo = new Vector<String>();

		for (int i = 0; i < list.length; i++)
		{
			if ( ! list[i].getCountry().equals(""))
			{
				combo
				.add(StringUtils.firstToUpper(list[i].getDisplayLanguage())
				+ " (" + list[i].getDisplayCountry() + ")");
			}
		}
		Collections.sort(combo);
		return combo;
	}

	/**
	 * @return All available locales
	 */
	public static Vector<Locale> getAllAvalaibleLocales()
	{
		final Locale list[] = DateFormat.getAvailableLocales();
		final Vector<Locale> vector = new Vector<Locale>();
		for (int i = 0; i < list.length; i++)
		{
			if ( ! list[i].getCountry().equals(""))
			{
				vector.add(list[i]);
			}
		}
		return vector;
	}

	/**
	 * @param string - The string for the JComboBox returned by
	 *            getCombableAvalaibleLocales()
	 * @return File name for the JComboBox string
	 */
	public static String getNameFileLang(final String string)
	{
		int i = 0;
		final Vector<Locale> vector = getAllAvalaibleLocales();
		boolean enc = false;
		while ( ! enc && i < vector.size())
		{
			final String aux = StringUtils.firstToUpper(vector.get(i)
			.getDisplayLanguage())
			+ " ("
			+ vector.get(i).getDisplayCountry()
			+ ")";
			if (aux.equals(string))
			{
				enc = true;
			}
			else
			{
				i++;
			}
		}
		if (enc)
		{
			return vector.get(i).getLanguage() + "_"
			+ vector.get(i).getCountry() + ".lang";
		}
		else
		{
			return null;
		}
	}
}