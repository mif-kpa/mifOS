package RealMachine.Services;

import RealMachine.*;
import RealMachine.Process;
import java.util.Arrays;

/**
 *
 * @author neworld
 */
public class Loader extends Service {
	public Loader (Machine machine, String name, Busenos busena) {
		super(machine, name, busena);
	}
	
	public void doService() {
		
	}
	
	public void interupt(Interupt i) {
		//atgaminam info
		int length = i.data[0];
		
		char c[] = new char[length];
		for (int x = 0; x < length; x++)
			c[x] = (char)i.data[x+1];
		
		String title = new String(c);
		int data[] = Arrays.copyOfRange(i.data, length + 1, i.data.length - 1);
		
		//iskiriam vietos deskriptoriui
		
		int desc = machine.memoryManagement.alloc(7);
		machine.ram[desc + 1] = data[1];
		machine.ram[desc + 3] = data[2];
		machine.ram[desc + 4] = data[3];
		machine.ram[desc + 5] = data[4];
		
		//iskriaim atminties kodui
		int segmentu = i.data[0];
		
		int page = machine.pager.createTable();
		
		for (int x = 0; x < segmentu; x++)
			machine.pager.allocPage(page, x);
		
		machine.registers.ptr = page;
		int lastMode = machine.registers.mode;
		machine.registers.mode = 0;
		
		for (int x = 5; x < data.length; x++)
			machine.setActualWord(x - 5, data[x]);
		
		machine.registers.mode = lastMode;
		machine.ram[desc + 6] = page;
		
		Process pr = new Process(machine, title, Busenos.READY, desc);
		machine.processes.add(pr);
	}
}
