package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import utils.ConsoleContent;
import utils.StringUtils;

import components.TableModel;
import components.Window;

/**
 * @author Jordan Aranda Tejada
 */
public class LangEditor extends JPanel {

	private static final long		serialVersionUID	= 2486033380987365663L;

	private final JScrollPane		scrollPane_Content;
	private final JTable			table;
	private HashMap<String, String>	lines;
	private final TableModel		modelTable;
	private File					file;
	private boolean					saved;
	private final JLabel			lblFilePath;

	public LangEditor(final HashMap<String, String> lines, final File file)
	{
		setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		this.lines = lines;
		this.file = file;
		this.saved = true;

		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[] {1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[] {0.0, 0.0, 1.0,
		Double.MIN_VALUE};
		setLayout(gridBagLayout);

		final Locale l = new Locale(file.getName().substring(0, 2), file
		.getName().substring(3, 5));

		final JLabel lblNameLanguage = new JLabel("Language: "
		+ StringUtils.firstToUpper(l.getDisplayLanguage()) + " ("
		+ l.getDisplayCountry() + ")");
		lblNameLanguage.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		lblNameLanguage.setForeground(Color.BLACK);
		lblNameLanguage.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblNombrelenguaje = new GridBagConstraints();
		gbc_lblNombrelenguaje.anchor = GridBagConstraints.WEST;
		gbc_lblNombrelenguaje.insets = new Insets(10, 15, 10, 15);
		gbc_lblNombrelenguaje.gridx = 0;
		gbc_lblNombrelenguaje.gridy = 0;
		add(lblNameLanguage, gbc_lblNombrelenguaje);

		lblFilePath = new JLabel("Saved file path: " + file.toString());
		lblFilePath.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		lblFilePath.setForeground(Color.BLACK);
		lblFilePath.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 15, 10, 15);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblFilePath, gbc_lblNewLabel);

		if ( ! file.exists())
		{
			lblFilePath.setText("Saved file path: Unsaved.");
		}

		final String[] header = {"Lines", "Key", "Description"};
		final String[][] content = new String[this.lines.size()][3];
		loadContent(content);

		final DefaultTableCellRenderer tableCellModel = new DefaultTableCellRenderer();
		tableCellModel.setHorizontalAlignment(SwingConstants.CENTER);

		modelTable = new TableModel();
		modelTable.setDataVector(content, header);

		scrollPane_Content = new JScrollPane();
		scrollPane_Content.setPreferredSize(new Dimension(2, 250));
		scrollPane_Content.setMinimumSize(new Dimension(23, 50));

		table = new JTable(modelTable);
		table.getTableHeader().setReorderingAllowed(false);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setDragEnabled(false);
		table.setSelectionForeground(Color.WHITE);
		table.setSelectionBackground(Color.BLUE);
		table.setForeground(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		table.setRowHeight(30);

		table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));

		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(0).setCellRenderer(tableCellModel);

		final GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 10, 10, 10);
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 2;
		add(scrollPane_Content, gbc_splitPane);

		scrollPane_Content.setViewportView(table);
	}

	private void loadContent(final String[][] content)
	{
		final Iterator<Entry<String, String>> it = this.lines.entrySet()
		.iterator();
		int i = 0;
		while (it.hasNext())
		{
			final Map.Entry<String, String> e = it.next();
			content[i][0] = i + 1 + "";
			content[i][1] = e.getKey().toString();
			content[i][2] = e.getValue().toString();
			i++;
		}
	}

	public void insertRow()
	{
		final Vector<String> vec = new Vector<String>();
		vec.add((modelTable.getRowCount() + 1) + "");
		vec.add("");
		vec.add("");
		modelTable.addRow(vec);
		final int row = modelTable.getRowCount() - 1;
		final Rectangle rect = table.getCellRect(row, 0, true);
		table.scrollRectToVisible(rect);
		table.clearSelection();
		table.setRowSelectionInterval(row, row);
		modelTable.fireTableDataChanged();
		this.saved = false;
	}

	public void deleteRow()
	{
		final Start st = (Start) Window.getInstance().getContentPane();
		final ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();
		final int[] indexRows = table.getSelectedRows();

		if (indexRows.length > 0)
		{
			for (int i = indexRows.length - 1; 0 <= i; i--)
			{
				modelTable.removeRow(indexRows[i]);
				doc.addString("Row " + (indexRows[i] + 1) + " deleted.");
			}
			doc.addString(indexRows.length + " row(s) have been removed.");
		}
		else
		{
			doc.addErrorMessage("¡Error! Please, select a row(s) to delete.");
		}
		updateLinesNumbers();
		this.saved = false;
	}

	private void updateLinesNumbers()
	{
		for (int i = 0; i < modelTable.getRowCount(); i++)
		{
			modelTable.setValueAt(i + 1, i, 0);
		}
	}

	public boolean isCorrectLang()
	{
		final Start st = (Start) Window.getInstance().getContentPane();
		final ConsoleContent doc = (ConsoleContent) st.getTextPane_console()
		.getStyledDocument();
		doc.clearContent();

		final Vector<Integer> lines = new Vector<Integer>();
		boolean isCorrectLang = true;

		for (int i = 0; i < modelTable.getRowCount(); i++)
		{
			if ( ! existLine(i, lines))
			{
				final String key1 = (String) modelTable.getValueAt(i, 1);
				for (int j = 0; j < modelTable.getRowCount(); j++)
				{
					final String key2 = (String) modelTable.getValueAt(j, 1);
					if (key1.trim().equals(key2.trim()) && i != j)
					{
						isCorrectLang = false;
						lines.add(j);
						doc.addErrorMessage("Lines " + (i + 1) + " and "
						+ (j + 1) + " have the same key.");
					}
				}
			}
		}

		for (int i = 0; i < modelTable.getRowCount(); i++)
		{
			if (((String) modelTable.getValueAt(i, 1)).trim().equals(""))
			{
				doc.addErrorMessage("The row " + (i + 1) + " has no key.");
				isCorrectLang = false;
			}
			if (((String) modelTable.getValueAt(i, 2)).trim().equals(""))
			{
				doc.addErrorMessage("The row " + (i + 1)
				+ " has no description.");
				isCorrectLang = false;
			}
		}

		return isCorrectLang;
	}

	private boolean existLine(final int i, final Vector<Integer> lines)
	{
		boolean enc = false;
		int index = 0;
		while ( ! enc && index < lines.size())
		{
			if (lines.get(index) == i)
			{
				enc = true;
			}
			else
			{
				index++;
			}
		}
		return enc;
	}

	public HashMap<String, String> getFinaleHashMap()
	{
		final HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < modelTable.getRowCount(); i++)
		{
			final String key = ((String) modelTable.getValueAt(i, 1)).trim();
			final String description = ((String) modelTable.getValueAt(i, 2))
			.trim();
			hashMap.put(key, description);
		}
		return hashMap;
	}

	public File getFile()
	{
		return this.file;
	}

	public void setFile(final File file)
	{
		this.file = file;
	}

	public HashMap<String, String> getLines()
	{
		return lines;
	}

	public void setLines(final HashMap<String, String> lines)
	{
		this.lines = lines;
	}

	public JTable getTable()
	{
		return table;
	}

	public TableModel getTableModel()
	{
		return this.modelTable;
	}

	public boolean isSaved()
	{
		return this.saved;
	}

	public void setSaved(final boolean saved)
	{
		this.saved = saved;
	}

	public void setLblFilePathText(final String string)
	{
		this.lblFilePath.setText("Saved file path: " + string);
	}
}