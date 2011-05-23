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
	private Process parent;
	
	public int description;
	
	private static int _ID = 0;
	
	private Process (String name, Busenos busena, int description, byte data[]) {
		this.name = name;
		this.busena = busena;
		this.description = description;
		id = _ID++;
	}
	
	private boolean addChild(Process child) {
		if (child.parent != null)
			return false;
		
		childs.add(child);
		child.parent = this;
		return true;
	}
	
	private boolean removeChild(Process child) {
		if (childs.indexOf(child) == -1)
			return false;
		
		childs.remove(child);
		return true;
	}
	
	
}
