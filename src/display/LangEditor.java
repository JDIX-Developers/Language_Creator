package display;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import utils.TableModel;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LangEditor extends JPanel
{
	private static final long serialVersionUID = 2486033380987365663L;
	
	private JScrollPane 				scrollPane_Content;
	private JTable 						table;
	private HashMap<String, String>		lines;
	private TableModel 					modelTable;
	
	public LangEditor(HashMap<String, String> lines)
	{
		this.lines = lines;
		System.out.println(lines.toString());
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNombrelenguaje = new JLabel("NombreLenguaje");
		lblNombrelenguaje.setForeground(Color.BLACK);
		lblNombrelenguaje.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNombrelenguaje = new GridBagConstraints();
		gbc_lblNombrelenguaje.insets = new Insets(10, 0, 20, 0);
		gbc_lblNombrelenguaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombrelenguaje.gridx = 0;
		gbc_lblNombrelenguaje.gridy = 0;
		add(lblNombrelenguaje, gbc_lblNombrelenguaje);
		
		scrollPane_Content = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 15, 10, 15);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane_Content, gbc_scrollPane);
		
		insertTable();
	}
	private void insertTable()
	{
		String [] header = {"id","Description"};
		String [][] content = new String [this.lines.size()][2];
		
		modelTable = new TableModel();
		modelTable.setDataVector(content, header);
		table = new JTable(modelTable);
		
		scrollPane_Content.setViewportView(table);
	}
}