package RealMachine;

/**
 *
 * @author neworld
 */
public class MemoryManagement {
	private Machine machine;
	private static final int START = 21;
	
	MemoryManagement(Machine machine) {
		this.machine = machine;
		
		MemoryHeader header = new MemoryHeader();
		header.adr = START;
		setHeader(header);
	}
	
	public final MemoryHeader getHeader(int adr) {
		int data = machine.ram[adr];
		
		int prev = data % 0x10000;
		data >>>= 16;
		int next = data;
		
		int size = machine.ram[adr + 1];
		
		return new MemoryHeader(next, prev, size, adr);
	}
	
	public final boolean setHeader(MemoryHeader header) {
		int data = header.next * 0x10000 + header.prev;
		machine.ram[header.adr] = data;
		machine.ram[header.adr + 1] = header.size;
		
		return true;
	}
	
	public final int alloc(int need) {
		int adr = START;
		
		while (true) {
			MemoryHeader header = getHeader(adr);
			
			int free;
			if (header.next > 0)
				free = header.next - header.size;
			else
				free = 0x10000 - adr - header.size;
			
			if (free >= need + 2) {
				MemoryHeader mh = new MemoryHeader();
				
				if (header.size != 0) {
					mh.adr = header.adr + header.size + 2;
					mh.next = header.next;
					mh.prev = header.adr;
					mh.size = need;
					
					header.next = mh.adr;
					
					if (mh.next > 0) {
						MemoryHeader next = getHeader(mh.next);
						next.prev = mh.adr;
						setHeader(next);
					}
					
					setHeader(header);
					setHeader(mh);
					return mh.adr + 2;
				} else {
					header.size = need;
					setHeader(header);
					return header.adr + 2;
				}
			}
			
			if ((adr = header.next) == 0) break;
		}
		
		return 0;
	}
	
	public final boolean free(int adr) {
		MemoryHeader header = getHeader(adr - 2);
		
		if (adr == START) {
			header.size = 0;
			setHeader(header);
			return true;
		}
		
		MemoryHeader prev = getHeader(header.prev);
		prev.next = header.next;
		setHeader(prev);
		
		if (header.next != 0) {
			MemoryHeader next = getHeader(header.next);
			next.prev = header.prev;
			setHeader(next);
		}
		
		return true;
	}
}
