/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Event;

import RealMachine.RealMachine;

/**
 *
 * @author neworld
 */
public abstract class Event {
	public abstract boolean onStep(RealMachine rm);
	public abstract boolean onScreen(RealMachine rm);
	public abstract boolean onRequestInput(RealMachine rm);
}
