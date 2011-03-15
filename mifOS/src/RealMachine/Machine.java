package RealMachine;

import Event.Event;

/**
 *
 * @author neworld
 */
public class Machine implements RealMachine {
	private int ram[] = new int[0x10000];
	private int external[] = new int[0x60000];
	private byte screen[] = new byte[80*20];
	private byte screenBuffer[] = new byte[80];
	private int screenPointer = 0;
	private int screenBufferPointer = 0;
	private Registers registers = new Registers();

	private boolean inited = false;
	private Event events;

	private boolean running = false;

	public static RealMachine createMachine() {
		return new Machine();
	}

	public boolean run() {
		if (!inited) init();

		if (running) {
			return false;
		} else {
			running = true;

			//@TODO turi buti paleista masija

			return true;
		}
	}

	private void init() {
		registers.ic = ram[0];
		registers.sf = ram[1];
		registers.s = ram[2];
		registers.mode = ram[3];
		registers.pd = ram[4];
		registers.m = ram[5];
		registers.r = ram[6];
		registers.ptr = ram[7];

		inited = true;
	}

	private void setUserMode() {
		ram[0] = registers.ic;
		ram[1] = registers.sf;
		ram[2] = registers.s;
		ram[5] = registers.m;
		ram[6] = registers.r;

		registers.mode = 0;
		registers.ic = ram[registers.pd + 1];
		registers.sf = ram[registers.pd + 2];
		registers.s = ram[registers.pd + 3];
		registers.m = ram[registers.pd + 4];
		registers.r = ram[registers.pd + 5];

		if (ram[registers.pd] == 0) ram[registers.pd] = 0x100;
	}

	private void setSuperMode() {
		registers.mode = 1;
		ram[registers.pd + 1] = registers.ic;
		ram[registers.pd + 2] = registers.sf;
		ram[registers.pd + 3] = registers.s;
		ram[registers.pd + 4] = registers.m;
		ram[registers.pd + 5] = registers.r;

		registers.ic = ram[0];
		registers.sf = ram[1];
		registers.s = ram[2];
		registers.r = ram[6];
	}

	public boolean stop() {
		if (running) {
			running = false;
			//@TODO stabdom masina
			return true;
		} else {
			return false;
		}
	}

	public boolean step() {
		if (!inited) init();

		return makeStep();
	}

	private boolean makeStep() {
		int instruction = getActualyWord(registers.ic++);
		registers.ic %= 0x10000;

		int komanda = instruction >> 24;
		int x = (instruction & 0xF00) >> 16;
		int y = (instruction & 0xF0) >> 8;
		int z = (instruction & 0xF);
		switch (komanda) {
			case 'A': Axyz(x, y, z); break;
			case 'S': Sxyz(x, y, z); break;
			case 'U': USyz(y, z); break;
		}

		if (events != null)
			events.onStep(this);

		return true;
	}

    // Sudetis
    private void Axyz(int x, int y, int z) {
        Arritmetic(true, x, y, z);
    }

    // Atimtis
    private void Sxyz(int x, int y, int z) {
        Arritmetic(false, x, y, z);
    }

	private void USyz(int y, int z) {
		registers.pd = y * 0x100 + z;
		setUserMode();
	}

    // Jei type yra true - sudetis, jei false - atimtis
    private void Arritmetic(boolean type, int x, int y, int z) {
        switch (x) {
            case 0:
                if (z == 0) {
                    if (type)
                        registers.r += registers.r;
                    else
                        registers.r -= registers.r;
                }
                else if (z ==1) {
                    if (type)
                        registers.r += registers.m;
                    else
                        registers.r -= registers.m;
                }
                break;

            case 1:
                if (type)
                    registers.r += getActualyWord(y * 0x10 + z);
                else
                    registers.r -= getActualyWord(y * 0x10 + z);
                break;

            case 16:
                if (z == 0) {
                    if (type)
                        registers.m += registers.r;
                    else
                        registers.m -= registers.r;
                }
                else if (z ==1) {
                    if (type)
                        registers.m += registers.m;
                    else
                        registers.m -= registers.m;
                }
                break;

            case 17:
                if (type)
                    registers.m += getActualyWord(y * 0x10 + z);
                else
                    registers.m += getActualyWord(y * 0x10 + z);
                break;
        }
    }

	private int getActualyWord(int adr) {
		adr %= 0x10000;

		if (check(registers.mode, 0x1, 0x1)) {
			//supervizoriaus rezimas
			return ram[adr];
		}

		int vir = registers.ptr * 0x10 + ((adr & 0xF00) >> 16);
		int adr2 = ram[vir];

		return ram[adr2 * 0x100 + adr & 0xFF];
	}

	private boolean check(int word, int mask, int need) {
		return (word & mask) == need;
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
		if (adr < 0 || adr > ram.length) return false;

		ram[adr] = data;

		return true;
	}

	public Registers getRegister() {
		return registers;
	}

	public boolean event(Event event) {
		events = event;
		return true;
	}

	public int[] halt() {
		stop();

		ram[0] = registers.ic;
		ram[1] = registers.sf;
		ram[2] = registers.s;
		ram[3] = registers.mode;
		ram[4] = registers.pd;
		ram[5] = registers.m;
		ram[6] = registers.r;
		ram[7] = registers.ptr;

		return ram;
	}

	public boolean loadDump(int[] data) {
		if (data.length > ram.length) return false;
		
		for (int i = 0; i < data.length; i++)
			ram[i] = data[i];
		
		return true;
	}

	public static void main() {
		int[] dump = {
			0x0010,   //0
			0x0000,   //1
			0x0000,   //2
			0x0001,   //3
			0x0000,   //4
			0x0000,   //5
			0x0000,   //6
			0x0000    //7
		};

		RealMachine rm = Machine.createMachine();
		rm.loadDump(dump);

		
	}
}
