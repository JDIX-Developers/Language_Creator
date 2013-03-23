package display;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import utils.StringUtils;

public class LangEditor extends JPanel {

	private static final long		serialVersionUID	= 2486033380987365663L;

	private JScrollPane				scrollPane_Content;
	private JTable					table;
	private HashMap<String, String>	lines;
	private DefaultTableModel		modelTable;
	private JPanel					panelBtnSouth;
	private JButton					btnInsertRow, btnDeleteRow;
	private File					file;
	private boolean					changes;
	private JButton					btnUpdate;
	private JSplitPane				splitPane;
	private JPanel					panel;
	private JLabel					lblConsole;
	private JScrollPane				scrollPane;
	private JTextArea				textArea;

	public LangEditor(HashMap<String, String> lines, File file)
	{
		this.lines = lines;
		this.file = file;
		this.changes = false;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[] {1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[] {0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		Locale l = new Locale(file.getName().substring(0, 2), file.getName()
		.substring(3, 5));

		JLabel lblNombrelenguaje = new JLabel("Language: "
		+ StringUtils.firstToUpper(l.getDisplayLanguage()) + " ("
		+ l.getDisplayCountry() + ")");
		lblNombrelenguaje.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		lblNombrelenguaje.setForeground(Color.BLACK);
		lblNombrelenguaje.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNombrelenguaje = new GridBagConstraints();
		gbc_lblNombrelenguaje.anchor = GridBagConstraints.WEST;
		gbc_lblNombrelenguaje.insets = new Insets(10, 15, 10, 15);
		gbc_lblNombrelenguaje.gridx = 0;
		gbc_lblNombrelenguaje.gridy = 0;
		add(lblNombrelenguaje, gbc_lblNombrelenguaje);

		String[] header = {"", "id", "Description"};
		String[][] content = new String[this.lines.size()][3];
		loadContent(content);

		DefaultTableCellRenderer tableCellModel = new DefaultTableCellRenderer();
		tableCellModel.setHorizontalAlignment(SwingConstants.CENTER);

		// Table Model
		modelTable = new DefaultTableModel();
		modelTable.setDataVector(content, header);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 10, 10, 10);
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		add(splitPane, gbc_splitPane);

		scrollPane_Content = new JScrollPane();
		scrollPane_Content.setPreferredSize(new Dimension(2, 250));
		scrollPane_Content.setMinimumSize(new Dimension(23, 50));
		splitPane.setLeftComponent(scrollPane_Content);
		scrollPane_Content.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));

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

		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(0).setCellRenderer(tableCellModel);

		scrollPane_Content.setViewportView(table);

		panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		lblConsole = new JLabel("Console");
		panel.add(lblConsole, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		scrollPane.setViewportView(textArea);
	}

	private void loadContent(String[][] content)
	{
		Iterator<Entry<String, String>> it = this.lines.entrySet().iterator();
		int i = 0;
		while (it.hasNext())
		{
			Map.Entry<String, String> e = it.next();
			content[i][0] = i + 1 + "";
			content[i][1] = e.getKey().toString();
			content[i][2] = e.getValue().toString();
			i++;
		}
	}

	private void insertRow()
	{
		modelTable.addRow(new Vector<String>());
		int row = modelTable.getRowCount() - 1;
		Rectangle rect = table.getCellRect(row, 0, true);
		table.scrollRectToVisible(rect);
		table.clearSelection();
		table.setRowSelectionInterval(row, row);
		modelTable.fireTableDataChanged();
		updateHashMap();
	}

	private void deleteRow()
	{
		if (table.getSelectedRow() > 0)
		{
			modelTable.removeRow(table.getSelectedRow());
			updateHashMap();
		}
		else
		{
			JOptionPane.showMessageDialog(null,
			"Please, select a row to delete.", "Error",
			JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateHashMap()
	{
		deleteWhiteLines();
		this.lines = new HashMap<String, String>();
		boolean correct = true;
		int i = 0;
		while (correct && i < modelTable.getRowCount())
		{
			String key = (String) modelTable.getValueAt(i, 0);
			if (!this.lines.containsKey(key))
			{
				this.lines.put(key, (String) modelTable.getValueAt(i, 1));
				i++;
			}
			else
			{
				JOptionPane.showMessageDialog(this,
				"Ya existe un campo con la clave: " + key, "Error",
				JOptionPane.ERROR_MESSAGE);
				correct = false;
			}
		}
	}

	private void deleteWhiteLines()
	{
		for (int i = modelTable.getRowCount() - 1; 0 <= i; i--)
		{
			System.out.println("Valor: " + i + " es "
			+ modelTable.getValueAt(i, 0));
			if (((String) modelTable.getValueAt(i, 0)).trim().equals(null))
			{
				table.removeRowSelectionInterval(i, i);
			}
		}
	}

	public File getFile()
	{
		return this.file;
	}

	public void setFilePath(File file)
	{
		this.file = file;
	}

	public HashMap<String, String> getLines()
	{
		return lines;
	}

	public void setLines(HashMap<String, String> lines)
	{
		this.lines = lines;
	}

}