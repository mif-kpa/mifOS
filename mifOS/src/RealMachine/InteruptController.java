package RealMachine;

import java.util.ArrayList;

/**
 *
 * @author neworld
 */
public class InteruptController {
	private Machine machine;
	private ArrayList<Interupt> interupts= new ArrayList<Interupt>();
	
	InteruptController(Machine machine) {
		this.machine = machine;
	}
	
	public void interupt(Interupt interupt) {
		this.interupts.add(interupt);
	}
	
	public Interupt get() {
		Interupt i = interupts.get(0);
		interupts.remove(i);
		return i;
	}
}
