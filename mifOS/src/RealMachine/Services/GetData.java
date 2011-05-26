package RealMachine.Services;

import RealMachine.*;
import RealMachine.Process;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author neworld
 */
public class GetData extends Service {
	private ArrayList<int[]> inBuffer = new ArrayList<int[]>();
	
	public GetData (Machine machine, String name, Busenos busena) {
		super(machine, name, busena);
	}
	
	public void doService() {
		if (inBuffer.isEmpty()) {
			//prasom ivesti
			machine.GD();
			busena = Busenos.BLOCK;
		} else {
			Resource r = resources.remove(0);
			r.response(inBuffer.remove(0));			
		}
	}
	
	@Override
	public void interupt(Interupt i) {
		if (resources.isEmpty()) {
			inBuffer.add(i.data);
		} else {
			Resource r = resources.remove(0);
			r.response(i.data);
		}
	}
}
