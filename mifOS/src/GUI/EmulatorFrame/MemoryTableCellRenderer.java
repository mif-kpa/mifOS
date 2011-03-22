/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.EmulatorFrame;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Karolis Voicechovskis
 */
public class MemoryTableCellRenderer extends DefaultTableCellRenderer
{
    private int[] mainProcessDescriptorArea = new int[2];
    private int[] interuptTableArea = new int[2];
    private int[] pageTableArea = new int[2];
    private int[] virtualMemoryArea = new int[16];

    public MemoryTableCellRenderer(int[] mainProcessDescriptorArea, 
                                   int[] interuptTableArea,
                                   int[] pageTableArea, int[]virtualMemoryArea)
    {
        super();
        this.initAreas(mainProcessDescriptorArea, interuptTableArea,
                       pageTableArea, virtualMemoryArea);
    }


    private void initAreas(int[] mainProcessDescriptorArea,
                                   int[] interuptTableArea,
                                   int[] pageTableArea, int[] virtualMemoryArea)
    {
        this.mainProcessDescriptorArea[0] = mainProcessDescriptorArea[0];
        this.mainProcessDescriptorArea[1] = mainProcessDescriptorArea[1];

        this.interuptTableArea[0] = interuptTableArea[0];
        this.interuptTableArea[1] = interuptTableArea[1];

        this.pageTableArea[0] = pageTableArea[0];
        this.pageTableArea[1] = pageTableArea[1];
        
        //this.virtualMemoryArea[0] = virtualMemoryArea[0];
        //this.virtualMemoryArea[1] = virtualMemoryArea[1];
    }

    @Override
    public Component getTableCellRendererComponent
                         (JTable table, Object value, boolean isSelected,
                                          boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent
                              (table, value, isSelected, hasFocus, row, column);

        if( value instanceof String )
        {
            if (column == 0)
            {
                int addressValue = Integer.valueOf((String) value, 16);
                if ((addressValue >= pageTableArea[0])
                        && (addressValue <= pageTableArea[1]))
                {
                    cell.setBackground(Color.RED);

                    Component virtualAddressCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 1);

                    Component addressValueCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 2);

                    Component commandValueCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 3);

                    virtualAddressCell.setBackground(Color.RED);
                    addressValueCell.setBackground(Color.RED);
                    // You can also customize the Font and Foreground this way
                    // cell.setForeground();
                    // cell.setFont();
                }
                else if ((addressValue >= virtualMemoryArea[0])
                        && (addressValue <= virtualMemoryArea[1]))
                {
                    cell.setBackground(Color.GREEN);
                    Component virtualAddressCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 1);

                    Component addressValueCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 2);

                    Component commandValueCell =
                                super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 3);

                    virtualAddressCell.setBackground(Color.GREEN);
                    addressValueCell.setBackground(Color.GREEN);

                }
                else
                {
                    cell.setBackground(Color.WHITE);
                }
            }
            else
            {
                cell.setBackground(super.getTableCellRendererComponent
                                   (table, value, isSelected, hasFocus, row, 0).
                                    getBackground());
            }
        }
        return cell;
    }

    public void setPageTableArea(int begin, int end)
    {
        this.pageTableArea[0] = begin;
        this.pageTableArea[1] = end;
    }

    public void setvirtualMemoryArea(int begin, int end)
    {
        this.virtualMemoryArea[0] = begin;
        this.virtualMemoryArea[1] = end;
    }
}
