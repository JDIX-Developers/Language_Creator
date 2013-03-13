package utils;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable (int fila,int columna)
	{
		if(columna==2)
		{
			return true;	
		}
		return false;
	}
}