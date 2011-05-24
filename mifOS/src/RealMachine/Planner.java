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
				pr.priority += 10 + Math.round((float)Math.random() * 2);
				if (!(best instanceof Service) || best.priority < pr.priority)
					best = pr;
			} else {
				if (!(best instanceof Service) && best.priority < pr.priority)
					best = pr;
			}
		}
		best.priority /= machine.processes.size() + 1;
		return best;
	}
}
