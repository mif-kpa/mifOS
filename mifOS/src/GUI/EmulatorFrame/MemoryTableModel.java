/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.EmulatorFrame;

import MachineDataUtilities.MachineDataUtilities;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Karolis Voicechovskis
 */
public class MemoryTableModel extends AbstractTableModel
{
    private static final int NUMBER_OF_COLUMNS = 0x4;
    private static final int NUMBER_OF_ROWS = 0xFFFF;

    private final String[] columnNames =
                                     {"R adr.", "V adr.", "Reikšmė", "Komanda"};

    private Object[][] data = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

    public MemoryTableModel()
    {
        this.initModel();
    }

    private void initModel()
    {
        for (int index = 0x0; index < NUMBER_OF_ROWS; index++)
        {
            data[index][0] = MachineDataUtilities.formatData(index, 4);
            data[index][1] = "-";
            data[index][2] = "-";
            data[index][3] = "-";
        }
    }

    @Override
    public Class getColumnClass( int column )
    {
        return getValueAt(0, column).getClass();
    }

    public int getColumnCount()
    {
        return columnNames.length;
    }
    @Override
    public String getColumnName( int column )
    {
        return columnNames[column];
    }
    public int getRowCount()
    {
        return data.length;
    }
    public Object getValueAt( int row, int column )
    {
        return data[row][column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        data[rowIndex][columnIndex] = ((String) aValue).toUpperCase();
    }
}
