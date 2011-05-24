package RealMachine;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Arrays;

/**
 *
 * @author neworld
 */
public class Resource {
	public static Hashtable<String, Process> registered = new Hashtable<String, Process>();
	public static ArrayList<Resource> resources = new ArrayList<Resource>();
	
	public Process process;
	public Process target;
	public byte message[];
	public byte response[];
	public String type;
	
	public static final String GD = "GD";
	
	Resource(Process creator, byte[] msg, String type) {
		message = Arrays.copyOf(msg, msg.length);
		process = creator;
		this.type = type;
		
		if ((target = registered.get(type)) != null) {
			target.addResource(this);
		}
		
		resources.add(this);
	}
	
	public void response(byte response[]) {
		this.response = Arrays.copyOf(response, response.length);
		if (process.waiting == this)
			process.busena = Busenos.READY;
		
		if (target != null)
			target.resources.remove(this);
	}
	
	public void delete() {
		resources.remove(this);
	}
}
