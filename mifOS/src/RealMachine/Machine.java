package RealMachine;

/**
 *
 * @author neworld
 */
public class Machine implements RealMachine {
	private int ram[] = new int[0xFFFF];
	private int external[] = new int[0x5FFFF];
	private byte screen[] = new byte[80*20];
	private byte screenBuffer[] = new byte[80];
	private int screenPointer = 0;
	private int screenBufferPointer = 0;
	private Registers registers = new Registers();

	public static RealMachine createRM() {
		return new Machine();
	}

	public boolean run() {
		return true;
	}

	public boolean stop() {
		return true;
	}

	public boolean step() {
		return true;
	}

	public boolean putChar(byte ch) {
		if (screenBufferPointer == 79) return false;

		screenBuffer[screenBufferPointer++] = ch;
		screen[screenPointer] = ch;
		if (++screenPointer >= 80*20) screenPointer = 0;
		
		return true;
	}

	public byte[] getScreen() {
		return screen;
	}

	public int[] getMemoryDump() {
		return ram;
	}

	public int[] getExternalMemory() {
		return external;
	}

	public int[] getVirtualMemory() {
		int[] vm = new int[0xFFF];

		for (byte i = 0; i < 0x10; i++) {
			int adr = registers.ptr * 0x10 + i;
			for (int a = 0; a < 0x100; a++)
				vm[i * 0x100 + a] = ram[adr];
		}

		return vm;
	}

	public boolean changeMemory(int adr, int data) {
		if (adr < 0 || adr > 0xFFFF) return false;

		ram[adr] = data;

		return true;
	}

	public Registers getRegister() {
		return registers;
	}

	public boolean event(Event event) {
		return true;
	}

	public int[] halt() {
		return ram;
	}

	public boolean loadDump(int[] data) {
		if (data.length > 0xFFFF) return false;
		
		for (int i = 0; i < data.length; i++)
			ram[i] = data[i];
		
		return true;
	}
}
