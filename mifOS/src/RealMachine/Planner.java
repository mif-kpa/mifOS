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
			if (pr.priority < 100)
				pr.priority += 10 + Math.round((float)Math.random() * 5);
			
			if (pr.busena != Busenos.READY)
				continue;
			
			if (best == null) {
				best = pr;
				continue;
			}
			
			if (pr instanceof Service) {
				if (!(best instanceof Service) || best.priority < pr.priority)
					best = pr;
			} else {
				if (!(best instanceof Service) && best.priority < pr.priority)
					best = pr;
			}
		}
		if (best == null) return null;
		//best.priority /= machine.processes.size() + 10;
		best.priority = 0;
		return best;
	}
}
