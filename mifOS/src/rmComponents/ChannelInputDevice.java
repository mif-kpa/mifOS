/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rmComponents;

import GUI.EmulatorPaneController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Karolis Voiechovskis PS3
 */
public class ChannelInputDevice implements KeyListener
{

    //private static final int NUMBER_OF_CHARS = 40;
    private static final int END_SYMBOL = 10;

    //private char[] userInput;
    private EmulatorPaneController emulatorPaneController;

    public ChannelInputDevice(EmulatorPaneController emulatorPaneController)
    {
        this.emulatorPaneController = emulatorPaneController;
    }

    /*public void prepareChannel()
    {
        userInput = new char[NUMBER_OF_CHARS];
    }*/
    //--------------------------------------------------------------------------
    public void keyTyped(KeyEvent ke)
    {
        char key = ke.getKeyChar();
        if (key == END_SYMBOL)
        {

        }
        else
        {
            /*int length = userInput.length;
            if (length + 1 < NUMBER_OF_CHARS)
            {
                userInput[length] = key;
            }
            else
            {
                //exception'as!!!
            }*/
        }
    }

    //Nenaudojami!!!
    public void keyPressed(KeyEvent ke)
    {
        
    }

    //Nenaudojami!!!
    public void keyReleased(KeyEvent ke)
    {
    
    }


}
