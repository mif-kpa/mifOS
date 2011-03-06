/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emulator.GUI;

import emulator.rmComponents.ChannelInputDevice;
import emulator.rmComponents.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class EmulatorPaneController
{

    private EmulatorFrame emulatorFrame;

    public EmulatorPaneController(EmulatorFrame emulatorFrame)
    {
        this.emulatorFrame = emulatorFrame;
        this.addActionListeners();
    }

    private void addActionListeners()
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

        //-------------KeyListener-------------
        this.emulatorFrame.getMainPane().getInput().
                                   addKeyListener(new ChannelInputDevice(this));
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
            }
        }

    }

    class ExecuteProgramButtonActionListener extends Thread implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            this.start();
        }

        @Override
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
        }


    }

    class ExecuteProgramByStepButtonActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent ae)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    //--------------------------------------------------------------------------
}
