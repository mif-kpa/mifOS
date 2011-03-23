/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.VMMemoryFrame;

import MachineDataUtilities.MachineDataUtilities;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Karolis Voicechovskis
 */
public class VirtualMemoryTableModel extends AbstractTableModel
{
    private static final int NUMBER_OF_COLUMNS = 0x11;
    private static final int SEGMENT_SIZE_DIVIDED_INTO_BLOCK_SIZE = 0x10;

    private String[] columnNames;
    private Object[][] data;

    private int segmentQuantity;
    private int rowCount;


    public VirtualMemoryTableModel(int segmentQuantity)
    {
        this.initColumnNames();
        this.initModel(segmentQuantity);
    }

    private void initModel(int segmentQuantity)
    {
        this.segmentQuantity = segmentQuantity;
        this.rowCount = this.segmentQuantity * SEGMENT_SIZE_DIVIDED_INTO_BLOCK_SIZE;
        this.data = new Object[this.rowCount][NUMBER_OF_COLUMNS];

        for (int index = 0x0; index < this.rowCount; index++)
        {
            //Langeliai užpildomi tuščiomis žymėmis
            for (int jndex = 0; jndex < 17; jndex++)
            {
                data[index][jndex] = "-";
            }
        }
    }

    //Inicijuojami stulpeliu pavadinimai
    private void initColumnNames()
    {
        this.columnNames = new String[NUMBER_OF_COLUMNS];
        this.columnNames[0] = "V adr.";

        for (int index = 1; index < columnNames.length; index++)
        {
            this.columnNames[index] =
                                  MachineDataUtilities.formatData(index - 1, 2);
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
        data[rowIndex][columnIndex] = aValue;
    }
}
