package RealMachine;

import java.util.Arrays;

/**
 *
 * @author neworld
 */
public class Interupt {
	public int ID;
	public Process process = null;
	public InteruptType type;
	public byte[] data;
	
	private static int _ID = 0;
	
	protected Interupt(InteruptType type, byte[] data) {
		ID = _ID++;
		this.type = type;
		data = Arrays.copyOf(data, data.length);
	}
	
	protected Interupt(InteruptType type, byte[] data, Process process) {
		this(type, data);
		this.process = process;
	}
	
	
}
