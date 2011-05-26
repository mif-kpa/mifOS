package RealMachine;

/**
 *
 * @author neworld
 */
public class Pager {
	public Machine machine;
	private int first = 0;
	private int last = 0;
	
	Pager(Machine machine) {
		this.machine = machine;
	}
	
	public int createTable() {
		int current = machine.memoryManagement.alloc(17);
		if (current == 0) return 0;
		
		PagerHeader currentHeader = new PagerHeader(current, 0, 0);
		
		if (first == 0) {
			first = last = current;
		} else {
			PagerHeader ph = getHeader(last);
			ph.next = current;
			setHeader(ph);
			last = current;
		}
		
		setHeader(currentHeader);
		
		clearPage(current);
		
		return current;
	}
	
	public void clearPage(int adr) {
		for (int i = 0; i < 16; i++)
			machine.ram[adr + i] = 0;
	}
	
	public boolean allocPage(int adr, int segment) {
		if (machine.ram[adr + segment] / 0x1000000 > 0)
			return true;
		
		int new_adr = machine.memoryManagement.alloc(0x100);
		if (new_adr == 0) return false;
		
		machine.ram[adr + segment] = 0x1000000 + new_adr;
		
		return true;
	}
	
	public void freePage(int adr) {
		for (int i = 0; i < 16; i++)
			machine.memoryManagement.free(machine.ram[adr + i]);
		
		PagerHeader ph = getHeader(adr);
		
		PagerHeader next = null;
		PagerHeader prev = null;
		
		if (ph.next > 0) next = getHeader(ph.next);
		if (ph.prev > 0) prev = getHeader(ph.prev);
		
		if (first == last && first == adr) {
			first = last = 0;
		} else if (first == adr) {
			first = next.adr;
			next.prev = 0;
			setHeader(next);
		} else if (adr == last) {
			last = prev.adr;
			prev.next = 0;
			setHeader(prev);
		} else {
			prev.next = next.adr;
			next.prev = prev.adr;
			
			setHeader(next);
			setHeader(prev);
		}
		
		machine.memoryManagement.free(adr);
	}
	
	private PagerHeader getHeader(int adr) {
		int data = machine.ram[adr + 16];
		
		int next = data % 0x10000;
		int prev = data >>> 16;
		
		return new PagerHeader(adr, prev, next);
	}
	
	private void setHeader(PagerHeader header) {
		int data = header.prev * 0x10000 + header.next;
		
		machine.ram[header.adr + 16] = data;
	}
}

class PagerHeader {
	public int prev;
	public int next;
	public int adr;
	
	PagerHeader(int adr, int prev, int next) {
		this.adr = adr;
		this.prev = prev;
		this.next = next;
	}
	
	PagerHeader() {
		
	}
}