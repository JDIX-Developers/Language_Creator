package utils;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

/**
 * @author Jordan Aranda Tejada
 */
public class Lang {

	/**
	 * @return All available locales, ready to be used in a JComboBox
	 */
	public static Vector<String> getCombableAvalaibleLocales()
	{
		Locale list[] = DateFormat.getAvailableLocales();
		Vector<String> combo = new Vector<String>();

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
		Locale list[] = DateFormat.getAvailableLocales();
		Vector<Locale> vector = new Vector<Locale>();
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
	public static String getNameFileLang(String string)
	{
		int i = 0;
		Vector<Locale> vector = getAllAvalaibleLocales();
		boolean enc = false;
		while ( ! enc && i < vector.size())
		{
			String aux = StringUtils.firstToUpper(vector.get(i)
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