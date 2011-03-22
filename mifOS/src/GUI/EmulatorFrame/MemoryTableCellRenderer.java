/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.EmulatorFrame;

import Exception.MifOSException;
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
    private final static int COLUMN_COUNT = 4;

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
        System.arraycopy(mainProcessDescriptorArea, 0,
                         this.mainProcessDescriptorArea, 0, 2);

        System.arraycopy(interuptTableArea, 0,
                         this.interuptTableArea, 0, 2);

        System.arraycopy(pageTableArea, 0,
                         this.pageTableArea, 0, 2);

        System.arraycopy(virtualMemoryArea, 0, this.virtualMemoryArea, 0, 16);
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

                if (this.isMPDArea(addressValue))
                {
                    this.colorHoleLine(Color.YELLOW, table,
                                       value, isSelected, hasFocus, row);


                } else if (this.isInteruptTableArea(addressValue))
                {
                    this.colorHoleLine(Color.BLUE, table,
                                       value, isSelected, hasFocus, row);


                } else if (this.isPageTableArea(addressValue))
                {
                    this.colorHoleLine(Color.GREEN, table,
                                       value, isSelected, hasFocus, row);


                } else if (this.isVirtualMemoryArea(addressValue))
                {
                    this.colorHoleLine(Color.RED, table,
                                       value, isSelected, hasFocus, row);
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

    //-------------------Color hole line----------------------------------------
    private void colorHoleLine(Color color, JTable table,
                               Object value, boolean isSelected,
                               boolean hasFocus, int row)

    {
        for (int index = 0; index <= COLUMN_COUNT; index++)
        {
            Component cell = super.getTableCellRendererComponent
                                   (table, value, isSelected, 
                                   hasFocus, row, index);

            cell.setBackground(color);
        }
    }

    //---------------------------Define Area------------------------------------
    private boolean isMPDArea(int addressValue)
    {
        if ((addressValue >= this.mainProcessDescriptorArea[0])
                && (addressValue <= this.mainProcessDescriptorArea[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isInteruptTableArea(int addressValue)
    {
        if ((addressValue >= this.interuptTableArea[0])
                && (addressValue <= this.interuptTableArea[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isPageTableArea(int addressValue)
    {
         if ((addressValue >= this.pageTableArea[0])
                && (addressValue <= this.pageTableArea[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isVirtualMemoryArea(int addressValue)
    {
        for (int index = 0; index < this.virtualMemoryArea.length; index++)
        {
            if ((addressValue >= this.virtualMemoryArea[index])
                    && (addressValue <= this.virtualMemoryArea[index] + 0xFF))
            {
                return true;
            }

        }

        return false;
    }
    //--------------------------------------------------------------------------
    //Nustatomi spalvinimo ruozai
    public void setMPDArea(int[] area)
    {
        System.arraycopy(area, 0, this.mainProcessDescriptorArea, 0, 2);
    }

    public void setInteruptTableArea(int[] area)
    {
        System.arraycopy(area, 0, this.interuptTableArea, 0, 2);
    }

    public void setPageTableArea(int[] area)
    {
        System.arraycopy(area, 0, this.pageTableArea, 0, 2);
    }

    public void setVirtualMemoryArea(int[] area)
    {
        System.arraycopy(area, 0, this.virtualMemoryArea, 0, 16);
    }

    public void setVirtualMemoryAreaByIndex(int index, int value)
                                                           throws MifOSException
    {
        if ((index > this.virtualMemoryArea.length) || (index < 0))
        {
            String msg = "MemoryTableCellRenderer.setVirtualMemoryAreaByIndex:\n"
                             + "Nurodytas nekorektiškas indeksas";
              throw new MifOSException(msg);
        }
        else
        {
            this.virtualMemoryArea[index] = value;
        }
    }
}
