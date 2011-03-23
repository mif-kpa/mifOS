/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.EmulatorFrame;

import FileUtilities.FileUtilities;
import Event.RMEventLauncher;
import Event.RMEventListener;
import Exception.MifOSException;
import GUI.VMMemoryFrame.VMMemoryFrame;
import MachineDataUtilities.MachineDataUtilities;
import RealMachine.RealMachine;
import java.awt.event.KeyEvent;
import rmComponents.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class EmulatorPaneController
{

    private final static int MEMORY_SEGMENT_IS_NOT_GIVEN = -1;

    private EmulatorFrame emulatorFrame;
    private VMMemoryFrame vmMemoryFrame;

    private RealMachine machine;
    private RMEventLauncher eventLauncher;

    private boolean machineIsRunning;
    private boolean machineIsHalted;

    //0 - neapibrezta
    //1 - vykdoma nepazingsniui
    private int executingType;
    private boolean isSettedColorAreas;

    public EmulatorPaneController(EmulatorFrame emulatorFrame,
                                                  RealMachine machine,
                                                  RMEventLauncher eventLauncher)
    {
        this.emulatorFrame = emulatorFrame;
        this.machine = machine;
        this.eventLauncher = eventLauncher;
        this.addListeners();
    }

    private void addListeners()
    {
        this.emulatorFrame.getMainPane().
                           getLoadProgram().addActionListener
                                        (new LoadProgramButtonActionListener());

        this.emulatorFrame.getMainPane().
                           getExecuteProgram().addActionListener
                                     (new ExecuteProgramButtonActionListener());

        this.emulatorFrame.getMainPane().
                           getExecuteByStep().addActionListener
                               (new ExecuteProgramByStepButtonActionListener());

        this.emulatorFrame.getMainPane().
                      getShowVMMemoryButton().addActionListener
                                             (new ShowVMMemoryButtonListener());


        //------------MachineListener----------
        this.eventLauncher.addListener(new RMListener());

        //-------------KeyListener-------------
        this.emulatorFrame.getMainPane().getInput().
                                       addKeyListener(new ChannelInputDevice());
    }

    public void setVMMemoryFrame(VMMemoryFrame vmMemoryFrame)
    {
        this.vmMemoryFrame = vmMemoryFrame;
    }

    //--------------------------------------------------------------------------
    class LoadProgramButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            int option = EmulatorPaneController.this.emulatorFrame.
                                        getMainPane().getFileChooser().
                                                  showOpenDialog(emulatorFrame);
            
            File file = null;

            if (option == JFileChooser.APPROVE_OPTION)
            {
                file = EmulatorPaneController.this.emulatorFrame.
                                            getMainPane().getFileChooser().
                                                              getSelectedFile();

                try
                {
                    int[] programCode = FileUtilities.getDataFromFile(file);
                    EmulatorPaneController.this.machine.loadDump(programCode);

                    EmulatorPaneController.this.setRealMemoryValues();
                    EmulatorPaneController.this.parseCommands();
                    EmulatorPaneController.this.emulatorFrame.repaint();

                } catch (MifOSException e)
                {
                    EmulatorFrame.showMessage(e.getMessage());
                }
              
            }
        }

    }

    class ExecuteProgramButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            try
            {
                if (EmulatorPaneController.this.machineIsHalted)
                {
                    String msg = "Programos toliau vykdyti negalima, "
                                              + "nes ji jau pabaigė savo darbą";
                    throw new MifOSException(msg);

                }
                else
                {
                    //Nuo šiol nebus pasiekiamas "VM atmintis" mygtukas
                    EmulatorPaneController.this.executingType = 1;

                    EmulatorPaneController.this.machine.run();
                    
                }

            } catch(MifOSException e)
            {
                EmulatorFrame.showMessage(e.getMessage());
            }
        }
    }

    class ExecuteProgramByStepButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            try
            {
                if (EmulatorPaneController.this.machineIsHalted)
                {
                    String msg = "Programos toliau vykdyti negalima, "
                                              + "nes ji jau pabaigė savo darbą";
                    throw new MifOSException(msg);

                }
                else
                {
                    //EmulatorPaneController.this.emulatorFrame.getMainPane().
                      //                              setCPUStateValue("laisvas");
                    EmulatorPaneController.this.machine.step();   
                }


            } catch(MifOSException e)
            {
                EmulatorFrame.showMessage(e.getMessage());
            }
        }
    }

    class ShowVMMemoryButtonListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            EmulatorPaneController.this.vmMemoryFrame.setVisible(true);
        }
        
    }

    class RMListener implements RMEventListener
    {

        public void inputRequested()
        {
            int chst1 = EmulatorPaneController.this.machine.getRegister().chm1;
            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue(Register.CHST1, chst1);

            EmulatorPaneController.this.emulatorFrame.
                                     getMainPane().setInputEditableStatus(true);
        }

        public void outputRequested()
        {
            int chst2 = EmulatorPaneController.this.machine.getRegister().chm1;
            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue(Register.CHST2, chst2);
            
            byte[] screen = EmulatorPaneController.this.machine.getScreen();
            EmulatorPaneController.this.
                        emulatorFrame.getMainPane().
                                setConsoleText(new String(screen));
        }

        public void stepRequested()
        {
            try
            {
                if (!EmulatorPaneController.this.isSettedColorAreas)
                {
                    EmulatorPaneController.this.prepareGUIToWork();
                }

            } catch(MifOSException e)
            {
                EmulatorFrame.showMessage(e.getMessage());
            }

            EmulatorPaneController.this.
                                    emulatorFrame.getMainPane().
                                                    setCPUStateValue("laisvas");

            int ic = EmulatorPaneController.this.machine.getRegister().ic;
            int sf =
                   EmulatorPaneController.this.machine.getRegister().sf;
            int r = EmulatorPaneController.this.machine.getRegister().r;
            int m = EmulatorPaneController.this.machine.getRegister().m;
            int s = EmulatorPaneController.this.machine.getRegister().s;

            int pd = EmulatorPaneController.this.machine.getRegister().pd;
            int ptr = EmulatorPaneController.this.machine.getRegister().ptr;
            int mode = EmulatorPaneController.this.machine.getRegister().mode;

            //------SF pabaiciui
            int sf_1 =
                      EmulatorPaneController.this.machine.
                                        getRegister().sf % 0x100;
            
            int sf_2 =
                   EmulatorPaneController.this.machine.
                                                getRegister().sf % 0x10000;
            sf_2 = sf_2 / 0x100;

            int sf_3 =
                   EmulatorPaneController.this.machine.
                                                getRegister().sf / 0x10000;
            sf_3 = sf_3 % 0x100;

            int sf_4 =
                      EmulatorPaneController.this.machine.
                                            getRegister().sf / 0x1000000;

            //-------CHST registrai
            int chst1 = EmulatorPaneController.this.machine.getRegister().chm1;
            int chst2 = EmulatorPaneController.this.machine.getRegister().chm2;
            int chst3 = EmulatorPaneController.this.machine.getRegister().chm3;

            //Nustatome ar leisti paspausti "VM atmintis" mygtuką
            if ((mode == 0) && (EmulatorPaneController.this.executingType == 0))
            {
                EmulatorPaneController.this.emulatorFrame.
                                 getMainPane().setShowVMMemoryButtonState(true);
            }
            else
            {
                EmulatorPaneController.this.emulatorFrame.
                                getMainPane().setShowVMMemoryButtonState(false);
            }

            EmulatorPaneController.this.emulatorFrame.
                                  getMainPane().setRegisterValue(Register.R, r);

            EmulatorPaneController.this.emulatorFrame.
                                  getMainPane().setRegisterValue(Register.M, m);

            EmulatorPaneController.this.emulatorFrame.
                                getMainPane().setRegisterValue(Register.IC, ic);

            EmulatorPaneController.this.emulatorFrame.
                                getMainPane().setRegisterValue(Register.SF, sf);

            EmulatorPaneController.this.emulatorFrame.
                                  getMainPane().setRegisterValue(Register.S, s);

            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().setRegisterValue(Register.MODE, mode);

            EmulatorPaneController.this.emulatorFrame.
                              getMainPane().setRegisterValue(Register.PTR, ptr);

            EmulatorPaneController.this.emulatorFrame.
                                getMainPane().setRegisterValue(Register.PD, pd);

            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().setRegisterValue(Register.SF_1, sf_1);

            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().setRegisterValue(Register.SF_2, sf_2);

            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().setRegisterValue(Register.SF_3, sf_3);

            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().setRegisterValue(Register.SF_4, sf_4);

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue(Register.CHST1, chst1);

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue(Register.CHST2, chst2);

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue(Register.CHST3, chst3);


            //EmulatorPaneController.this.setPageTableAndVirtualMemoryArea();

            EmulatorPaneController.this.setRealMemoryValues();
            EmulatorPaneController.this.emulatorFrame.repaint();

            //---------Atnaujiname virtualia atmintį----------------------------
            int[] virtualMemory =
                         EmulatorPaneController.this.machine.getVirtualMemory();

            int segmentNumber = 0;

            for (int index = 0; index < 16; index++)
            {
                if (EmulatorPaneController.this.analyzePageTable(index)
                        != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN);
                {
                    int[] virtualMemoryPart = new int[0x100];
                    //System.out.println(index);
                    System.arraycopy(virtualMemory, 
                                     index * 0x100,
                                     virtualMemoryPart, 0x0, 0x100);

                    EmulatorPaneController.this.vmMemoryFrame.
                                           updateVirtualMemoryTable
                                             (virtualMemoryPart, segmentNumber);

                    segmentNumber++;
                }
            }

            EmulatorPaneController.this.
                                        vmMemoryFrame.setNextCommandAddress(ic);

            
        }

        public void haltRequested()
        {
            EmulatorPaneController.this.
                                    emulatorFrame.getMainPane().
                                                    setCPUStateValue("laisvas");
            EmulatorPaneController.this.machineIsHalted = true;
            String msg = "Mašina baigė darbą";
            EmulatorFrame.showMessage(msg);
        }
    }

    class ChannelInputDevice implements KeyListener
    {

        private static final byte END_SYMBOL = 10;
        private static final int USER_INPUT_MAX_LENGTH = 0xFF;
        
        private byte[] inputBuffer = new byte[USER_INPUT_MAX_LENGTH];
        private int inputBufferPointer;

        //Nenaudojamas
        public void keyPressed(KeyEvent ke)
        {
        }

        //Nenaudojamas
        public void keyReleased(KeyEvent ke)
        {
        }

        public void keyTyped(KeyEvent ke)
        {
            char key = ke.getKeyChar();

            if (key == END_SYMBOL)
            {
                this.endInput();

            } else
            {
                if (inputBufferPointer == USER_INPUT_MAX_LENGTH - 1)
                {
                    this.inputBuffer[inputBufferPointer] = END_SYMBOL;
                    this.endInput();
                }
                else
                {
                    inputBuffer[inputBufferPointer] = (byte) key;
                    this.inputBufferPointer++;
                }
            }
        }

        /**
         * Nusiunčiama įvestis mašinai, bei išvalomas įvesties buferis.
         */
        private void endInput()
        {
            EmulatorPaneController.this.machine.pushData(inputBuffer);
            EmulatorPaneController.this.emulatorFrame.
                                                 getMainPane().setInputText("");
            EmulatorPaneController.this.emulatorFrame.
                                    getMainPane().setInputEditableStatus(false);
            this.inputBufferPointer = 0;

            this.inputBuffer = new byte[USER_INPUT_MAX_LENGTH];

        }

    }
    //--------------------------------------------------------------------------
    private void setRealMemoryValues()
    {
        int[] memoryDump = this.machine.getMemoryDump();
        for (int index = 0x0 ; index < 0xFFFF; index++)
        {
            EmulatorPaneController.this.emulatorFrame.
                                   getMainPane().setMemoryTableModelValue
                                               (memoryDump[index], index, 2, 8);
        }
    }

    /*private void setPageTableAndVirtualMemoryArea()
    {
        int ptr = this.machine.getRegister().ptr;

        this.emulatorFrame.getMainPane().
                getMemoryTableCellRenderer().setPageTableArea(ptr, ptr + 0xF);

        this.emulatorFrame.getMainPane().
                getMemoryTableCellRenderer().setPageTableArea(0x10, 0x100E);
    }*/

    private void parseCommands()
    {
        int[] memoryDump = this.machine.getMemoryDump();
        for (int index = 0x10 ; index < 0xFFF; index++)
        {
            String command =
                       MachineDataUtilities.parseInstruction(memoryDump[index]);


            EmulatorPaneController.this.emulatorFrame.
                                   getMainPane().setMemoryTableModelValue
                                                     (command, index, 3);
        }
    }

    /**
     * Nustato atminties spalvinimo intervalus:
     * MPD - statiškai [0000 - 0002]
     * Pertraukimu lentele - [0003 - 0005]
     * Puslapiu lentele pagal PTR registra
     * VM atmintis pagal puslapiu lentele
     */
    private void setColorAreas() throws MifOSException
    {
        int[] memoryDump = this.machine.getMemoryDump();
        int ptr = this.machine.getRegister().ptr;
        
        int[] MPDArea = {0, 1};
        int[] interuptArea = {2, 4};
        int[] pageArea = {ptr, ptr + 15};

        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().setMPDArea(MPDArea);
        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().
                                             setInteruptTableArea(interuptArea);
        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().
                                                     setPageTableArea(pageArea);

        //int segmentQuantity = 0;

        for (int index = 0; index < 16; index++)
        {
           
            int blockAddress = this.analyzePageTable(index);

            //Jei atmintis išskirta
            if (blockAddress != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
            {
                this.emulatorFrame.getMainPane().
                        getMemoryTableCellRenderer().
                            setVirtualMemoryAreaByIndex(index, blockAddress);
                //segmentQuantity++;
            }
        }
        //System.out.println(segmentQuantity);
          //  System.out.println("color");
    }

    /*
     * Analizuoja i-tąjį puslapių lentelės žodį
     * return - jei atmintis išskirta, tai bloko numerį
     *          jei ne, reikšmę -1
     */
    private int analyzePageTable(int index)
    {
        int[] memoryDump = this.machine.getMemoryDump();
        int ptr = this.machine.getRegister().ptr;

         int word = memoryDump[index + ptr];
         int isGiven = word % 0x100;

         if (isGiven == 1)
         {
             //Gaunamas puslapių lentelės žodio paskutinis baitas
             return word / 0x1000000;

         }
         else
         {
             return EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN;
         }
    }

    /*
     * Nustato virtualiuosius adresus
     */
    private void setVirtualMemoryAddress()
    {
        int givenMemoryBlockCount = 0;

        for (int index = 0; index < 16; index++)
        {
            int blockAddress = this.analyzePageTable(index);

            if (blockAddress != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
            {
                for (int jndex = 0; jndex < 0xFF; jndex++)
                {
                    int virtualAddress = jndex + (givenMemoryBlockCount * 0xFF);

                    int row = blockAddress * 0xFF + jndex;

                    this.emulatorFrame.getMainPane().
                            setMemoryTableModelValue(virtualAddress, row, 1, 4);

                }

                givenMemoryBlockCount++;
            }

        }
    }

    private void prepareGUIToWork() throws MifOSException
    {
        this.isSettedColorAreas = true;
        this.setVirtualMemoryAddress();
        this.setColorAreas();
        int segmentQuantity = 0;

         
        for (int index = 0; index < 0x10; index++)
        {
            int blockAddress = this.analyzePageTable(index);
            
            if (blockAddress != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN);
            {
                segmentQuantity++;
            }

        }

        //System.out.println(segmentQuantity);
        VMMemoryFrame.create(EmulatorPaneController.this, segmentQuantity);
    }
}
