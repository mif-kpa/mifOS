/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RealMachine;

/**
 *
 * @author neworld
 */
public class Runner extends Thread {
	private RealMachine rm;

	public Runner(RealMachine rm) {
		this.rm = rm;
	}

	@Override
	public void run() {
		while (rm.isRunning())
			if (!rm.step())
				return;

		return;
	}
}
