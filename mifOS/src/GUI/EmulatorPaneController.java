/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import FileUtilities.FileUtilities;
import Event.RMEventLauncher;
import Event.RMEventListener;
import RealMachine.RealMachine;
import rmComponents.ChannelInputDevice;
import rmComponents.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class EmulatorPaneController
{

    private EmulatorFrame emulatorFrame;
    private RealMachine machine;
    private RMEventLauncher eventLauncher;

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
                           getRestartCPU().addActionListener
                                         (new RestartCPUButtonActionListener());

        this.emulatorFrame.getMainPane().
                           getLoadProgram().addActionListener
                                        (new LoadProgramButtonActionListener());

        this.emulatorFrame.getMainPane().
                           getExecuteProgram().addActionListener
                                     (new ExecuteProgramButtonActionListener());

        this.emulatorFrame.getMainPane().
                           getExecuteByStep().addActionListener
                               (new ExecuteProgramByStepButtonActionListener());

        this.eventLauncher.addListener(new RMListener());
        //-------------KeyListener-------------
        /*this.emulatorFrame.getMainPane().getInput().
                                   addKeyListener(new ChannelInputDevice(this));*/
    }

    //--------------------------------------------------------------------------
    class RestartCPUButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

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

                    EmulatorPaneController.this.setVMMemoryValues();

                } catch (IOException e)
                {
                    //Paleidziamas informacinis langas, paskutinis vartotojo
                    //veiksmas anuliuojamas
                }
              
            }
        }

    }

    class ExecuteProgramButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            do
            {
                EmulatorPaneController.this.machine.step();

            } while(0 != 0 /*isRunning*/);
        }

        /*@Override
        public void run()
        {
            EmulatorPaneController.this.emulatorFrame.
                            getMainPane().getExecuteProgram().setEnabled(false);
            
            do
            {   int registerICValue = EmulatorPaneController.
                                    this.emulatorFrame.getMainPane().
                                                  getRegisterValue(Register.IC);
                String command = EmulatorPaneController.
                                    this.emulatorFrame.getMainPane().
                                            getValueOfVMMemory(registerICValue);

                


            } while (0 == 1); //klaidinga!!

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().getExecuteProgram().addActionListener
                                     (new ExecuteProgramButtonActionListener());

            EmulatorPaneController.this.emulatorFrame.
                             getMainPane().getExecuteProgram().setEnabled(true);
        }*/


    }

    class ExecuteProgramByStepButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            EmulatorPaneController.this.machine.step();
        }

    }

    class RMListener implements RMEventListener
    {

        public void inputRequested()
        {
            throw new UnsupportedOperationException("Not supported yet.");
            /*EmulatorPaneController.this.emulatorFrame.
                                    getMainPane().setRegisterValue
                                                          (Register.CHST1, "1");
            EmulatorPaneController.this.emulatorFrame.
                                     getMainPane().setInputEditableStatus(true);*/
        }

        public void outputRequested()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void stepRequested()
        {
            int ic = EmulatorPaneController.this.machine.getRegister().ic;
            int sf = EmulatorPaneController.this.machine.getRegister().sf;
            int r = EmulatorPaneController.this.machine.getRegister().r;
            int m = EmulatorPaneController.this.machine.getRegister().m;
            int s = EmulatorPaneController.this.machine.getRegister().s;

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue
                             (Register.R, Integer.toHexString(r).toUpperCase());

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue
                             (Register.M, Integer.toHexString(m).toUpperCase());

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue
                           (Register.IC, Integer.toHexString(ic).toUpperCase());

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue
                           (Register.SF, Integer.toHexString(sf).toUpperCase());

            EmulatorPaneController.this.emulatorFrame.
                          getMainPane().setRegisterValue
                             (Register.S, Integer.toHexString(s).toUpperCase());

            EmulatorPaneController.this.setVMMemoryValues();
        }

    }
    //--------------------------------------------------------------------------

    private void setVMMemoryValues()
    {
        int[] memoryDump = this.machine.getMemoryDump();
        for (int index = 0x10 ; index <= 0x100E; index++)
        {
            EmulatorPaneController.this.emulatorFrame.
                                            getMainPane().setValueVMMemory
                                              (index - 0x10, memoryDump[index]);
        }
    }
}
