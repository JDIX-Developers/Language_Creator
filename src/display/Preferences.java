package display;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import utils.Lang;

/**
 * @author Razican (Iban Eguia)
 */
public class Preferences extends JPanel {

	private static final long		serialVersionUID	= -9082799207563983259L;
	private JComboBox<String>		langCombo, lfCombo;
	private HashMap<String, String>	lfhm;
	private String					currentLookAndFeel;

	/**
	 * Create the panel.
	 */
	public Preferences()
	{
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 78, 32, 0, 0};
		gbl_panel.rowHeights = new int[] {24, 0, 0};
		gbl_panel.columnWeights = new double[] {1.0, 0.0, 1.0, 1.0,
		Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[] {0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblLanguage = new JLabel(Lang.getLine("pref_lang"));
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 1;
		gbc_lblLanguage.gridy = 0;
		panel.add(lblLanguage, gbc_lblLanguage);

		langCombo = new JComboBox<>(Lang.getCombableLocales());
		langCombo.setSelectedIndex(Lang.getCurrentLocaleKey());
		GridBagConstraints gbc_langCombo = new GridBagConstraints();
		gbc_langCombo.insets = new Insets(0, 0, 5, 5);
		gbc_langCombo.anchor = GridBagConstraints.NORTH;
		gbc_langCombo.gridx = 2;
		gbc_langCombo.gridy = 0;
		panel.add(langCombo, gbc_langCombo);

		JLabel lbLookandfeel = new JLabel(Lang.getLine("pref_look_and_feel"));
		GridBagConstraints gbc_lbLookandfeel = new GridBagConstraints();
		gbc_lbLookandfeel.insets = new Insets(0, 0, 0, 5);
		gbc_lbLookandfeel.gridx = 1;
		gbc_lbLookandfeel.gridy = 1;
		panel.add(lbLookandfeel, gbc_lbLookandfeel);

		lfCombo = new JComboBox<>(getAvailableLF());
		lfCombo.setSelectedItem(currentLookAndFeel);
		GridBagConstraints gbc_lfCombo = new GridBagConstraints();
		gbc_lfCombo.insets = new Insets(0, 0, 0, 5);
		gbc_lfCombo.gridx = 2;
		gbc_lfCombo.gridy = 1;
		panel.add(lfCombo, gbc_lfCombo);
	}

	/**
	 * @return Current selected locale's index
	 */
	public int getLocaleIndex()
	{
		return langCombo.getSelectedIndex();
	}

	/**
	 * @return Current selected LookAndFeelInfo
	 */
	public String getLookAndFeel()
	{
		return lfhm.get(lfCombo.getSelectedItem());
	}

	private Vector<String> getAvailableLF()
	{
		LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
		lfhm = new HashMap<>(lfs.length);
		Vector<String> v = new Vector<>(lfs.length);

		for (LookAndFeelInfo lf2: lfs)
		{
			lfhm.put(lf2.getName(), lf2.getClassName());
			v.add(lf2.getName());
			if (utils.Preferences.getLookAndFeel().equals(lf2.getClassName()))
			{
				currentLookAndFeel = lf2.getName();
			}
		}
		return v;
	}
}