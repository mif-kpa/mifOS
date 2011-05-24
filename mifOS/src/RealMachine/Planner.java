package RealMachine;

/**
 *
 * @author neworld
 */
public class Planner {
	private Machine machine;
	
	protected Planner(Machine machine) {
		this.machine = machine;
	}
	
	public Process nextProcess() {
		//tikrinam procesus ir ieskom su auksciausiu prioritetu. Servisai visada turi auksciausia prioriteta
		Process best = null;
		
		for (Process pr : machine.processes) {
			if (pr.busena != Busenos.READY)
				continue;
			
			if (pr instanceof Service) {
				pr.priority += Math.round((float)Math.random() * 5);
				if (!(best instanceof Service) || best.priority < pr.priority)
					best = pr;
			} else {
				if (!(best instanceof Service) && best.priority < pr.priority)
					best = pr;
			}
		}
		
		return best;
	}
}
