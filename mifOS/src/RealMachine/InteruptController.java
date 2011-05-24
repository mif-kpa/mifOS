package RealMachine;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author neworld
 */
public class InteruptController {
	private Machine machine;
	private ArrayList<Interupt> interupts= new ArrayList<Interupt>();
	private Hashtable<InteruptType, Service> map = new Hashtable<InteruptType, Service>();
	
	InteruptController(Machine machine) {
		this.machine = machine;
	}
	
	public void interupt(Interupt interupt) {
		this.interupts.add(interupt);
	}
	
	public Interupt get() {
		Interupt i = interupts.get(0);
		interupts.remove(i);
		
		if (map.containsKey(i.type)) {
			map.get(i.type).interupt(i);
		}
		
		return i;
	}
	
	public boolean atachInterupt(InteruptType type, Service service) {
		if (map.containsKey(type)) return false;
		
		map.put(type, service);
		return true;
	}
}
