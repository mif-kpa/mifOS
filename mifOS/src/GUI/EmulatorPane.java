/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmulatorPane.java
 *
 * Created on Mar 1, 2011, 10:37:40 AM
 */

package GUI;

import rmComponents.Register;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class EmulatorPane extends javax.swing.JPanel {

    /** Creates new form EmulatorPane */
    public EmulatorPane() {
        initComponents();
        initModel(4096);
        initRegisters();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vmRegistersTitle = new javax.swing.JLabel();
        registerR = new javax.swing.JLabel();
        registerM = new javax.swing.JLabel();
        registerIC = new javax.swing.JLabel();
        registerSF = new javax.swing.JLabel();
        titleChannelRegisters = new javax.swing.JLabel();
        registerCHST1 = new javax.swing.JLabel();
        registerCHST2 = new javax.swing.JLabel();
        registerCHST3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listRMMemory = new javax.swing.JList();
        titleRMMemory = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        titleConsole = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        restartCPU = new javax.swing.JButton();
        loadProgram = new javax.swing.JButton();
        executeProgram = new javax.swing.JButton();
        executeByStep = new javax.swing.JButton();
        registerRValue = new javax.swing.JLabel();
        registerMValue = new javax.swing.JLabel();
        registerICValue = new javax.swing.JLabel();
        registerSFValue = new javax.swing.JLabel();
        registerCHST1Value = new javax.swing.JLabel();
        registerCHST2Value = new javax.swing.JLabel();
        registerCHST3Value = new javax.swing.JLabel();
        stateCPUTitle = new javax.swing.JLabel();
        stateCPUValue = new javax.swing.JLabel();

        vmRegistersTitle.setText("VM registrai:");

        registerR.setText("R:");

        registerM.setText("M:");

        registerIC.setText("IC:");

        registerSF.setText("SF:");

        titleChannelRegisters.setText("Kanalų registrai:");

        registerCHST1.setText("CHST[1]:");

        registerCHST2.setText("CHST[2]:");

        registerCHST3.setText("CHST[3]:");

        listRMMemory.setModel(this.model);
        jScrollPane1.setViewportView(listRMMemory);

        titleRMMemory.setText("VM atmintis:");

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        jScrollPane2.setViewportView(console);

        titleConsole.setLabelFor(console);
        titleConsole.setText("Konsolė:");

        input.setEditable(false);

        restartCPU.setText("Perkrauti procesorių");

        loadProgram.setText("Įkelti programą");

        executeProgram.setText("Vykdyti programą");

        executeByStep.setText("Vykdyti pažingsniui");

        registerRValue.setText("####");

        registerMValue.setText("####");

        registerICValue.setText("##");

        registerSFValue.setText("####");

        registerCHST1Value.setText("#");

        registerCHST2Value.setText("#");

        registerCHST3Value.setText("#");

        stateCPUTitle.setText("CPU būsena:");

        stateCPUValue.setText("laisvas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vmRegistersTitle)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerIC)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(registerICValue))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerM)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(registerMValue))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerR)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(registerRValue)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerCHST3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(registerCHST3Value))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerCHST2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(registerCHST2Value))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(registerCHST1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(registerCHST1Value))
                                    .addComponent(titleChannelRegisters)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(registerSF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registerSFValue))
                            .addComponent(titleRMMemory))))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleConsole)
                        .addGap(55, 55, 55)
                        .addComponent(stateCPUTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stateCPUValue)
                        .addGap(136, 136, 136))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(restartCPU)
                            .addComponent(executeProgram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(executeByStep)
                            .addComponent(loadProgram))
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(input, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vmRegistersTitle)
                    .addComponent(titleChannelRegisters)
                    .addComponent(titleConsole)
                    .addComponent(stateCPUTitle)
                    .addComponent(stateCPUValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerR)
                            .addComponent(registerCHST1)
                            .addComponent(registerCHST1Value)
                            .addComponent(registerRValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerM)
                            .addComponent(registerCHST2)
                            .addComponent(registerCHST2Value)
                            .addComponent(registerMValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerIC)
                            .addComponent(registerCHST3)
                            .addComponent(registerCHST3Value)
                            .addComponent(registerICValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerSF)
                            .addComponent(registerSFValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(titleRMMemory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(restartCPU)
                            .addComponent(loadProgram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(executeProgram)
                            .addComponent(executeByStep))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private String[] generateListRMMemoryValues(int quantity)
    {
        String[] listValues = new String[quantity];

        for (int index = 0; index < listValues.length; index++)
        {
            if (index < 16)
            {
                listValues[index] =
                          "00" + Integer.toHexString(index).toUpperCase() + ":";
            }
            else if ((index > 15) && (index < 256))
            {
                listValues[index] =
                           "0" + Integer.toHexString(index).toUpperCase() + ":";
            }
            else
            {
                listValues[index] =
                                 Integer.toHexString(index).toUpperCase() + ":";
            }
        }

        return listValues;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
    private javax.swing.JButton executeByStep;
    private javax.swing.JButton executeProgram;
    private javax.swing.JTextField input;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList listRMMemory;
    private javax.swing.JButton loadProgram;
    private javax.swing.JLabel registerCHST1;
    private javax.swing.JLabel registerCHST1Value;
    private javax.swing.JLabel registerCHST2;
    private javax.swing.JLabel registerCHST2Value;
    private javax.swing.JLabel registerCHST3;
    private javax.swing.JLabel registerCHST3Value;
    private javax.swing.JLabel registerIC;
    private javax.swing.JLabel registerICValue;
    private javax.swing.JLabel registerM;
    private javax.swing.JLabel registerMValue;
    private javax.swing.JLabel registerR;
    private javax.swing.JLabel registerRValue;
    private javax.swing.JLabel registerSF;
    private javax.swing.JLabel registerSFValue;
    private javax.swing.JButton restartCPU;
    private javax.swing.JLabel stateCPUTitle;
    private javax.swing.JLabel stateCPUValue;
    private javax.swing.JLabel titleChannelRegisters;
    private javax.swing.JLabel titleConsole;
    private javax.swing.JLabel titleRMMemory;
    private javax.swing.JLabel vmRegistersTitle;
    // End of variables declaration//GEN-END:variables

    private javax.swing.JFileChooser fileChooser = new JFileChooser();
    private final DefaultListModel model = new DefaultListModel();


    private void initModel(int quantity)
    {
        String[] modelValues = this.generateListRMMemoryValues(quantity);
        for (int index = 0; index < modelValues.length; index++)
        {
            this.model.addElement(modelValues[index]);
        }
    }

    private void initRegisters()
    {
        this.setRegisterValue(Register.R, "0000");
        this.setRegisterValue(Register.M, "0000");
        this.setRegisterValue(Register.IC, "00");
        this.setRegisterValue(Register.SF, "0");

        this.setRegisterValue(Register.CHST1, "0");
        this.setRegisterValue(Register.CHST2, "0");
        this.setRegisterValue(Register.CHST3, "0");
    }
    //--------------------------------------------------------------------------
    public JButton getExecuteByStep()
    {
        return executeByStep;
    }

    public JButton getExecuteProgram()
    {
        return executeProgram;
    }

    public JButton getLoadProgram()
    {
        return loadProgram;
    }

    public JButton getRestartCPU()
    {
        return restartCPU;
    }

    public JList getListRMMemory()
    {
        return listRMMemory;
    }

    public JFileChooser getFileChooser()
    {
        return fileChooser;
    }

    public JTextField getInput()
    {
        return input;
    }
    //--------------------------------------------------------------------------

    public String getInputText()
    {
        return this.input.getText();
    }

    //--------------------------------------------------------------------------

    public void setConsoleText(String text)
    {
        this.console.setText(text);
    }

    public void addLineConsoleText(String line)
    {
        String previousText = this.console.getText();
        previousText += "\n" + line;
        this.console.setText(line);
    }

    public void setRegisterValue(Register register, String value)
    {
        switch (register)
        {
            case R: this.registerRValue.setText(value);
                break;

            case M: this.registerMValue.setText(value);
                break;

            case IC: this.registerICValue.setText(value);
                break;

            case SF: this.registerSFValue.setText(value);
                break;

            case CHST1: this.registerCHST1Value.setText(value);
                break;

            case CHST2: this.registerCHST2Value.setText(value);
                break;

            case CHST3: this.registerCHST3Value.setText(value);
                break;

            default: /*exception'as*/;
        }
    }

    public int getRegisterValue(Register register)
    {
        switch(register)
        {
            case R: return Integer.parseInt(this.registerRValue.getText());

            case M: return Integer.parseInt(this.registerMValue.getText());

            case IC: return Integer.parseInt(this.registerICValue.getText());

            case SF: return Integer.parseInt(this.registerSFValue.getText());

            case CHST1: return Integer.parseInt
                                            (this.registerCHST1Value.getText());

            case CHST2: return Integer.parseInt
                                            (this.registerCHST2Value.getText());

            case CHST3: return Integer.parseInt
                                            (this.registerCHST3Value.getText());

            default: return 0;//exception'as
        }
    }

    public void setInputEditableStatus(boolean status)
    {
        this.input.setEditable(status);
    }

    public String getValueOfVMMemory(int virtualAddress)
    {
        String memoryValue = (String)this.model.getElementAt(virtualAddress);
        String[] splittedValue = memoryValue.split(": ", 2);
        return splittedValue[1];
    }

    public void setValueVMMemory(int virtualAddress, int newValue)
    {
        String oldValue = (String)this.model.getElementAt(virtualAddress);
        String[] splittedOldValue = oldValue.split(": ", 2);
        this.model.setElementAt
                            (splittedOldValue[0]
                               + Integer.toHexString(newValue).toUpperCase(),
                                                                virtualAddress);
    }
}
