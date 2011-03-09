/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import RealMachine.Machine;
import Event.RMEventLauncher;
import RealMachine.RealMachine;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class EmulatorFrame extends JFrame
{

    private EmulatorPane mainPane;

    public EmulatorFrame()
    {
        initComponents();
        initFrame();
    }

    private void initComponents()
    {
        mainPane = new EmulatorPane();
    }

    private void initFrame()
    {
        this.getContentPane().setLayout
                            (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setTitle("RM emuliatorius");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.add(mainPane);
        this.setSize(700, 400);
        this.setResizable(false);
    }

    
    //--------------------------------------------------------------------------
    public EmulatorPane getMainPane()
    {
        return mainPane;
    }
    //--------------------------------------------------------------------------
    public static void createAndShow()
    {
        RMEventLauncher eventLauncher = new RMEventLauncher();
        RealMachine machine = Machine.createMachine();
        EmulatorFrame emulatorFrame = new EmulatorFrame();
        EmulatorPaneController emulatorPaneController =
                                    new EmulatorPaneController
                                        (emulatorFrame, machine, eventLauncher);

        emulatorFrame.setVisible(true);
    }
}
