/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MachineDataUtilities;

/**
 *
 * @author Karolis Voicechovskis
 */
public class MachineDataUtilities
{

    public static String parseInstruction(int instruction)
    {
        
        int command = instruction >> 24;
        int x = (instruction & 0xFF0000) >> 16;
        int y = (instruction & 0xFF00) >> 8;
	int z = (instruction & 0xFF);

        int yz = (instruction & 0xFF);

        if (command == 'H' && x == 'A' && y == 'L' && z == 'T')
        {
            return "HALT";
        }

        switch(command)
        {
            case 'A': 
                return MachineDataUtilities.checkCommand
                                ("A", MachineDataUtilities.
                                               parseArritmeticCommand(x, y, z));

            case 'S':
                return MachineDataUtilities.checkCommand
                                ("S", MachineDataUtilities.
                                               parseArritmeticCommand(x, y, z));

            case 'C':
                return MachineDataUtilities.checkCommand
                                ("C", MachineDataUtilities.
                                               parseArritmeticCommand(x, y, z));

            case 'U':
                if (x == 'U')
                {
                    return "US " + Integer.toHexString(16 * y + z);
                } else
                {
                     return MachineDataUtilities.checkCommand
                                        ("U", MachineDataUtilities.
                                                     parseUxyzCommand(x, y, z));

                }

            case 'T':
                 MachineDataUtilities.checkCommand
                                ("T", MachineDataUtilities.
                                           parseTxyzCommand(x, y, z));

            case 'I': 
                 MachineDataUtilities.checkCommand
                                ("I", MachineDataUtilities.
                                           parseDataSendCommand(true, x, y, z));

            case 'L':
                 MachineDataUtilities.checkCommand
                                ("L", MachineDataUtilities.
                                          parseDataSendCommand(false, x, y, z));

            case 'J':
                if (x == 'M')
                {
                    return "JM";
                     /*return MachineDataUtilities.checkCommand
                                    ("JM", MachineDataUtilities.
                                                     parseJumpCommand(4, y, z));*/
                }
                
                
            case 'D':
                return "D";

            case 'B':
                return "B";

            case 'Z':
                return "Z";

            case '0':
                return "0";

            case '1':
                return "1";

            case 'N':
               return MachineDataUtilities.checkCommand
                                    ("N", MachineDataUtilities.
                                                    parseLogicCommand(x, y, z));

            case 'O':
                return MachineDataUtilities.checkCommand
                                    ("O", MachineDataUtilities.
                                                    parseLogicCommand(x, y, z));

            case 'X':
                return MachineDataUtilities.checkCommand
                                    ("X", MachineDataUtilities.
                                                    parseLogicCommand(x, y, z));

            default: return "";
        }

    }


    /*private static String parseConditionalJumpCommand(int x, int y, int z)
    {
        return MachineDataUtilities.parseJumpCommand(x, y, z);
    }*/

    /*private static String parseJumpCommand(int x, int y, int z)
    {
        int adr = 0;
        switch (x)
        {
            case 0:
                return "R";
                
            case 1:
                return "M";
                
            case 2:
                return Integer.toHexString(0x100 * y + z);
                
            case 3:
                return

                adr = (registers.ic + 0x100 * y + z) % 0x10000;
                break;
            case 4:
                adr = 0x100 * y + z;
                break;
        }
        jump(adr);



        return Integer.toHexString(16 * y + z);
    }*/

    private static String parseLogicCommand(int x, int y, int z)
    {
        switch (x)
        {
            case 0:
                if (z == 0)
                {
                    return "R, R";

                } else
                {
                    return "R, M";

                }
            
            case 1:
                return "R, " + Integer.toHexString(0x100 * y + z);
                
            case 16:
                if (z == 0)
                {
                    return "M, R";

                } else
                {
                    return "M, M";
                }
                
            case 17:
                return "M, " + Integer.toHexString(0x100 * y + z);
        }

        return "";
    }

    private static String parseTxyzCommand(int x, int y, int z)
    {
        if (x == 0)
        {
            if (z == 0)
            {
                return "R, PTR";
                
            } else
            {
                return "M, PTR";

            }
        } else
        {
            return Integer.toHexString(16 * y + z) + ", PTR";
        }
    }

    private static String parseUxyzCommand(int x, int y, int z)
    {
        if (x == 0)
        {
            if (z == 0)
            {
                return "PTR, R";
            } else
            {
                return "PTR, M";
            }
        } else if (x == 1)
        {
            return "PTR, " + Integer.toHexString(16 * y + z);
        }
        else
        {
            return "";
        }
    }

    //Aritmetiniu komandu nagrinejimas
    private static String parseArritmeticCommand(int x, int y, int z)
    {
        switch(x)
        {
            case 0:

                if (z == 0)
                {
                    return "R, R";
                }
                else
                {
                    return "R, M";
                }

            case 1:

                return "R, " + Integer.toHexString(16 * y + z);

            case 16:

                if (z == 0)
                {
                    return "M, R";
                }
                else
                {
                    return "M, M";
                }

            case 17:

                return "M, " + Integer.toHexString(16 * y + z);

            default: return "";
        }
    }

    //Analizuojamos duomenų persiuntimo komandos
    private static String parseDataSendCommand(boolean way, int x, int y, int z)
    {
        if (way)
        {
            switch (x)
            {
                case 0:
                    return Integer.toHexString(16 * y + z) + ", R";
                    
                case 1:
                    return Integer.toHexString(16 * y + z) + ", M";
            }
        } else
        {
            switch (x)
            {
                case 0:
                    return "R, " + Integer.toHexString(16 * y + z);

                case 1:
                    return "M, " + Integer.toHexString(16 * y + z);
            }
        }

        return "";
    }

    private static String checkCommand(String command, String op)
    {
        if (op.equals(""))
        {
            return "";
        }
        else
        {
            return command + " " + op;
        }
    }

    //--------------------------------------------------------------------------
    public static String formatData(int data, int requeredSymbolCount)
    {
        String hexValue = Integer.toHexString(data).toUpperCase();

        int digitCount = hexValue.length();
        int extraDigitCount = requeredSymbolCount - digitCount;

        if (extraDigitCount >= 0)
        {
            for (int index = 0; index < extraDigitCount; index++)
            {
                hexValue = "0" + hexValue;
            }
        }

        return hexValue;
    }
}
