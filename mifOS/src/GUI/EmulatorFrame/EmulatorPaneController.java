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


    private boolean memoryDumpIsLoad;
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
            /*int option = EmulatorPaneController.this.emulatorFrame.
                                        getMainPane().getFileChooser().
                                                  showOpenDialog(emulatorFrame);*/
            

            int[] dump = {
				0x0021,   //0
				0x0000,   //1
				0x0000,   //2
				0x0001,   //3
				0x0000,   //4
				0x0000,   //5
				0x0000,   //6
				0x0000,   //7
				0x0013,   //8  GD interupas
				0x0012,   //9
				0x0012,   //A
				0x0012,   //B
				0x0012,   //C
				0x0012,   //D
				0x0012,   //E
				0x0012,   //F
				0x0012,   //10
				0x0012,   //11
				0x49524554,  //12 tuscias interupas
				0x49524554,  //13 GD interupas
				0x00000001,  //14
				0x00000002,  //15
				0x3,         //16
				0x4,         //17
				0x5,         //18
				0x6,         //19
				0x7,         //1A
				0x8,         //1B
				0x9,         //1C
				0x40000000,  //1D  HELL
				0x01000000,  //1E  O WO
				0x524c4421,  //1F  RLD!
				0x00000019,  //20
				0x4C010020,  //21
				0x4C00001D,  //22
				0x4101001E,
				0x4900001D,
				0x5044001D,  //21 PD 1C
				0x6C4F0023,
				0x48414C54   //22
			};


                        EmulatorPaneController.this.machine.loadDump(dump);
                         EmulatorPaneController.this.memoryDumpIsLoad = true;

                    EmulatorPaneController.this.setRealMemoryValues();
                    EmulatorPaneController.this.parseCommands();

                    EmulatorPaneController.this.emulatorFrame.
                                 getMainPane().setLoadProgramButtonState(false);
                    EmulatorPaneController.this.emulatorFrame.repaint();
            /*File file = null;

            if (option == JFileChooser.APPROVE_OPTION)
            {
                if (EmulatorPaneController.this.emulatorFrame.getHeight() > 545)
                {
                    file = MachineDataUtilities.getSelectedFile();
                }
                else
                {
                    file = EmulatorPaneController.this.emulatorFrame.
                                            getMainPane().getFileChooser().
                                                              getSelectedFile();
                }

                try
                {
                    int[] programCode = FileUtilities.getDataFromFile(file);
                    EmulatorPaneController.this.machine.loadDump(programCode);

                    EmulatorPaneController.this.memoryDumpIsLoad = true;

                    EmulatorPaneController.this.setRealMemoryValues();
                    EmulatorPaneController.this.parseCommands();

                    EmulatorPaneController.this.emulatorFrame.
                                 getMainPane().setLoadProgramButtonState(false);
                    EmulatorPaneController.this.emulatorFrame.repaint();

                } catch (MifOSException e)
                {
                    EmulatorFrame.showMessage(e.getMessage());
                }
              
            }
        }*/

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

                } if (!EmulatorPaneController.this.memoryDumpIsLoad)
                {
                    String msg = "Mašina negali pradėti darbo,"
                                  + " nes programa nėra įkrauta";
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

                }  if (!EmulatorPaneController.this.memoryDumpIsLoad)
                {
                    String msg = "Mašina negali pradėti darbo,"
                                  + " nes programa nėra įkrauta";
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

            //---PTR
            if (ptr == 0)
            {
                int[] area = {0, 0};
                EmulatorPaneController.this.
                        emulatorFrame.getMainPane().
                        getMemoryTableCellRenderer().setPageTableArea(area);
            }
            else
            {
                int[] area = {ptr, ptr + 16};
                EmulatorPaneController.this.
                        emulatorFrame.getMainPane().
                        getMemoryTableCellRenderer().setPageTableArea(area);


                for (int index = 0; index < 16; index++)
                {

                    int blockAddress = EmulatorPaneController.this.analyzePageTable(index);

                    //Jei atmintis išskirta
                    if (blockAddress != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
                    {
                        try
                        {
                            EmulatorPaneController.this.emulatorFrame.getMainPane().
                                    getMemoryTableCellRenderer().
                                    setVirtualMemoryAreaByIndex(index, blockAddress);
                            //segmentQuantity++;
                        } catch(MifOSException e)
                        {
                            
                        }
                    }
                }
            }


            //---------Atnaujiname virtualia atmintį----------------------------
            int[] virtualMemory =
                         EmulatorPaneController.this.machine.getVirtualMemory();

            int segmentNumber = 0;

            for (int index = 0; index < 16; index++)
            {
                if (EmulatorPaneController.this.analyzePageTable(index)
                        != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
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
        
        int[] MPDArea = {0, 7};
        int[] interuptArea = {8, 19};
        int[] pageArea = {ptr, ptr + 15};

        if (ptr == 0) {
            pageArea[0] = 0;
            pageArea[1] = 0;
        }
        else
        {
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
        }


        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().setMPDArea(MPDArea);
        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().
                                             setInteruptTableArea(interuptArea);
        this.emulatorFrame.getMainPane().
                               getMemoryTableCellRenderer().
                                                     setPageTableArea(pageArea);

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

            if (blockAddress
                       != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
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


    /**
     * Išanalizuojama visa puslapių lentelė ir suskaičiuojama
     * išskirtų segmentų skaičius
     * @return išskirtų atminties segmentų kiekis.
     */
    private int getGivenMemorySegmentCount()
    {
        int givenSegmentCount = 0;

        //System.out.println(givenSegmentCount);

        for (int index = 0; index < 0x10; index++)
        {
            int blockAddress = this.analyzePageTable(index);

            if (blockAddress
                    != EmulatorPaneController.MEMORY_SEGMENT_IS_NOT_GIVEN)
            {
                givenSegmentCount++;
            }

        }

        return givenSegmentCount;
    }


    /**
     * Paruošiamas darbui GUI:
     * Pažymimi realios atminties ruožai pagal paskirtį
     * Įrašomi virtualūs adresai
     * Paruošiamą informacija VMMemoryFrame objektui.
     *
     * @throws MifOSException
     */
    private void prepareGUIToWork() throws MifOSException
    {
        this.isSettedColorAreas = true;
        this.setVirtualMemoryAddress();
        this.setColorAreas();
        
        VMMemoryFrame.create(EmulatorPaneController.this,
                             this.getGivenMemorySegmentCount());
    }
}
