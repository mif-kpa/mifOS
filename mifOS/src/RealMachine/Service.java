package RealMachine;

/**
 *
 * @author neworld
 */
public class Service extends Process {
	public Service (Machine machine, String name, Busenos busena) {
		super(machine, name, busena, 0x0);
	}

	public void interupt(Interupt i) {
		
	}
	
	@Override
	public boolean run() {
		System.out.println("zazaDFSGF");
		doService();
		
		if (Busenos.READY == busena) {
			
			
			
			return true;
		} else {
			return false;
		}
	}

	public void doService() {
		
	}
}
