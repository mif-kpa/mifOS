package RealMachine;

import java.util.ArrayList;

/**
 *
 * @author neworld
 */
public class Process {
	public int id;
	public String name;
	public Busenos busena;
	public ArrayList<Process> childs = new ArrayList<Process>();
	public ArrayList<Resource> resources = new ArrayList<Resource>();
	public Resource waiting;
	private Process parent;
	private Machine machine;
	public byte priority;
	
	public int description;
	
	private static int _ID = 0;
	
	protected Process (Machine machine, String name, Busenos busena, int descID, byte data[]) {
		this.name = name;
		this.busena = busena;
		this.description = descID;
		this.machine = machine;
		id = _ID++;
	}
	
	protected boolean addChild(Process child) {
		if (child.parent != null)
			return false;
		
		childs.add(child);
		child.parent = this;
		return true;
	}
	
	protected boolean removeChild(Process child) {
		if (childs.indexOf(child) == -1)
			return false;
		
		childs.remove(child);
		return true;
	}
	
	protected void addResource(Resource r) {
		resources.add(r);
		if (Busenos.BLOCK == busena && waiting == null)
			busena = Busenos.READY;
	}
}
