/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.VMMemoryFrame;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Karolis Voicechovskis
 */
public class VirtualMemoryTableCellRender extends DefaultTableCellRenderer
{

    private int nextCommandAddress = -1;

    @Override
    public Component getTableCellRendererComponent
                         (JTable table, Object value, boolean isSelected,
                                          boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent
                              (table, value, isSelected, hasFocus, row, column);

        if ((value instanceof String) && (column != 0))
        {
            
            int address = Integer.valueOf
                             ((String) table.getModel().getValueAt(row, 0), 16);
            address += Integer.valueOf
                          ((String) table.getModel().getColumnName(column), 16);

            if (isNextCommand(address))
            {
                cell.setBackground(Color.YELLOW);
            }
            else
            {
                cell.setBackground(Color.WHITE);
            }
        }

        return cell;
    }


    private boolean isNextCommand(int address)
    {
        if (address == this.nextCommandAddress)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //--------------------------------------------------------------------------
    public void setNextCommandAddress(int nextCommand)
    {
        this.nextCommandAddress = nextCommand;
    }
}
