package display;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

	public LangEditor(HashMap<String, String> lines, File file)
	{
		this.lines = lines;
		this.file = file;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[] {1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[] {0.0, 1.0, 0.0,
		Double.MIN_VALUE};
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
		gbc_lblNombrelenguaje.insets = new Insets(10, 15, 20, 15);
		gbc_lblNombrelenguaje.gridx = 0;
		gbc_lblNombrelenguaje.gridy = 0;
		add(lblNombrelenguaje, gbc_lblNombrelenguaje);

		scrollPane_Content = new JScrollPane();
		scrollPane_Content.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 15, 10, 15);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane_Content, gbc_scrollPane);

		insertTable();
		insertBtnsPanel();
	}

	private void insertTable()
	{
		String[] header = {"id", "Description"};
		String[][] content = new String[this.lines.size()][2];
		loadContent(content);

		// Table Model
		modelTable = new DefaultTableModel();
		modelTable.setDataVector(content, header);

		table = new JTable(modelTable);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setDragEnabled(false);
		table.setSelectionForeground(Color.WHITE);
		table.setSelectionBackground(Color.BLUE);
		table.setForeground(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		table.setRowHeight(30);

		scrollPane_Content.setViewportView(table);
	}

	private void loadContent(String[][] content)
	{
		Iterator<Entry<String, String>> it = this.lines.entrySet().iterator();
		int i = 0;
		while (it.hasNext())
		{
			Map.Entry<String, String> e = it.next();
			content[i][0] = e.getKey() + "";
			content[i][1] = e.getValue() + "";
			i++;
		}
	}

	private void insertBtnsPanel()
	{
		panelBtnSouth = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBtnSouth.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 15, 10, 15);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panelBtnSouth, gbc_panel);

		btnInsertRow = new JButton("Insert");
		btnInsertRow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				modelTable.addRow(new Vector<String>());
				int row = modelTable.getRowCount() - 1;
				Rectangle rect = table.getCellRect(row, 0, true);
				table.scrollRectToVisible(rect);
				table.clearSelection();
				table.setRowSelectionInterval(row, row);
				modelTable.fireTableDataChanged();
			}
		});
		btnInsertRow.setForeground(Color.BLACK);
		panelBtnSouth.add(btnInsertRow);

		btnDeleteRow = new JButton("Delete");
		btnDeleteRow.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (table.getSelectedRow() > 0)
				{
					modelTable.removeRow(table.getSelectedRow());
				}
				else
				{
					JOptionPane.showMessageDialog(null,
					"Select a row to delete.");
				}
			}
		});
		btnDeleteRow.setForeground(Color.BLACK);
		panelBtnSouth.add(btnDeleteRow);
	}

	public File getFile()
	{
		return this.file;
	}

	public void setFilePath(File file)
	{
		this.file = file;
	}
}