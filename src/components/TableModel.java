package components;

import javax.swing.table.DefaultTableModel;

/**
 * @author Jordan Aranda Tejada
 */
public class TableModel extends DefaultTableModel {

	private static final long	serialVersionUID	= - 6263295638389047704L;

	@Override
	public boolean isCellEditable(final int row, final int column)
	{
		if (column == 0)
		{
			return false;
		}
		return true;
	}
}