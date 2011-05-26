/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RealMachine.Services;

import RealMachine.Busenos;
import RealMachine.Interupt;
import RealMachine.Machine;
import RealMachine.Service;

/**
 *
 * @author neworld
 */
public class StopMachine extends Service {
	public void interup(Interupt i) {
		machine.halt();
	}
	
	public StopMachine (Machine machine, String name, Busenos busena) {
		super(machine, name, busena);
	}
}
