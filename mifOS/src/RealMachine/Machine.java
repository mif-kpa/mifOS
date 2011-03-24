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

			new Thread(new Runner(this)).start();

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

	synchronized public boolean step() {
		if (!inited) init();

		return makeStep();
	}

	synchronized private boolean makeStep() {
		int instruction = getActualWord(registers.ic++);
		registers.ic %= 0x10000;

		int komanda = instruction >>> 24;
		int x = (instruction & 0xFF0000) >>> 16;
		int y = (instruction & 0xFF00) >>> 8;
		int z = (instruction & 0xFF);

		int yz = (instruction & 0xFF);

		if (komanda == 'H' && x == 'A' && y == 'L' && z == 'T') {
			if (isSuper())
				HALT();
			else
				setSuperMode();
		} else {

			switch (komanda) {
				case 'A': Axyz(x, y, z); break;
				case 'S': Sxyz(x, y, z); break;
				case 'C': Cxyz(x, y, z); break;
				case 'U':
					if (x == 'U') USyz(y, z);
					else Uxyz(x, y, z);
				break;
				case 'T': Txyz(x, y, z); break;
				case 'I': dataSend(true, x, y, z); break;
				case 'L': dataSend(false, x, y, z); break;
				case 'J':
					if (x == 'M') jump(4, y, z);
				break;
				case 'D': Dxyz(x, y, z); break;
				case 'B': Bxyz(x, y, z); break;
				case 'Z': Zxyz(x, y, z); break;
				case '0': _0xyz(x, y, z); break;
				case '1': _1xyz(x, y, z); break;
				case 'N': Nxyz(x, y, z); break;
				case 'O': Oxyz(x, y, z); break;
				case 'X': Xxyz(x, y, z); break;
				case 'P':
					if (x == 'S' && y == 'H') PSHx(z);
					if (x == 'P' && y == 'P') POPx(z);
					if (x == 'D') PD(y, z);
					break;
				case 's':
					if (x == 'L') sLxy(y, z);
					if (x == 'R') sRxy(y, z);
					if (x == 'P') sPxy(y, z);
					break;
				case 'l':
					if (x == 'O') lOxy(y, z);
					if (x == 'P') lPxy(z);
					if (x == 'e' && y == 'a' && z == 'v') leave();
					break;
				case 'E':
					if (x == 'N' && y == 'T' && z == 'R') enter();
					break;
				case 'c':
					if (x == 'L') call(y, z);
					break;
				case 'R':
					if (x == 'E' && y == 'T' && z < 40) RETx(z);
					if (x == 'E' && y == 'T' && z == 'I') IRET();
					break;
				case 'G':
					if (x == 'D') GD(y, z);
					break;
			}

		}

		if (events != null)
			events.onStep(this);

		return true;
	}

	/**
	 * duomenu reg <-> mem persiuntimas
	 * @param kryptis false: reg->mem; true: mem->reg;
	 * @param x
	 * @param y
	 * @param z
	 */
	private void dataSend(boolean kryptis, int x, int y, int z) {
		int adr = y * 0x100 + z;

		if (kryptis) {
			switch (x) {
				case 0: setActualWord(adr, registers.r); break;
				case 1: setActualWord(adr, registers.m); break;
			}
		} else {
			switch (x) {
				case 0: registers.r = getActualWord(adr); break;
				case 1: registers.m = getActualWord(adr); break;
			}
		}

		switch (x) {
			case 0: generateSF(registers.r); break;
			case 1: generateSF(registers.m); break;
		}
	}

	private void Dxyz(int x, int y, int z) {
		if (check(registers.sf, 01000, 01000)) jump(x, y, z);
	}

	private void Bxyz(int x, int y, int z) {
		if (check(registers.sf, 01000, 0)) jump(x, y, z);
	}

	private void Zxyz(int x, int y, int z) {
		if (check(registers.sf, 1, 1)) jump(x, y, z);
	}

	private void _0xyz(int x, int y, int z) {
		if (check(registers.sf, 010000, 010000)) jump(x, y, z);
	}

	private void _1xyz(int x, int y, int z) {
		if (check(registers.sf, 010000, 0)) jump(x, y, z);
	}

	private void jump(int adr) {
		registers.ic = adr;
	}

	private void Nxyz(int x, int y, int z) {
		logika((byte)0, x, y, z);
	}

	private void Oxyz(int x, int y, int z) {
		logika((byte)1, x, y, z);
	}
	private void Xxyz(int x, int y, int z) {
		logika((byte)2, x, y, z);
	}

	private void logika(byte comm, int x, int y, int z) {
		int op1 = 0, op2 = 0;

		switch (x) {
			case 0:
					if (z == 0) {
						op1 = registers.r;
						op2 = registers.r;
					} else {
						op1 = registers.r;
						op2 = registers.m;
					}
				break;
			case 1:
					op1 = registers.r;
					op2 = getActualWord(y * 0x100 + z);
				break;

			case 16:
					if (z == 0) {
						op1 = registers.m;
						op2 = registers.r;
					} else {
						op1 = registers.m;
						op2 = registers.m;
					}
				break;
			case 17:
					op1 = registers.m;
					op2 = getActualWord(y * 0x100 + z);
				break;
		}

		int ats = 0;

		switch (comm) {
			case 0: ats = op1 & op2; break;
			case 1: ats = op1 | op2; break;
			case 2: ats = op1 ^ op2; break;
		}

		if (check(x, 010000, 010000))
			registers.m = ats;
		else
			registers.r = ats;

		generateSF(ats);
	}

	private void jump(int x, int y, int z) {
		int adr = 0;
		switch (x) {
			case 0: adr = registers.r; break;
			case 1: adr = registers.m; break;
			case 2: adr = getActualWord(0x100 * y + z); break;
			case 3: adr = (registers.ic + 0x100 * y + z) % 0x10000; break;
			case 4: adr = 0x100 * y + z; break;
		}
		jump(adr);

	}

	private void jump(int x, int y) {
		jump(4, x, y);
	}

	private void generateSF(int value) {
		generateSF(value, 0, 0);
	}

	private void generateSF(int value, int op1, int op2) {
		int rez = 0;
		if (value == 0) rez += 1;
		if (check(value, 0x00A0, 0x00A0)) rez += 010;
		if (check(value, 0xA000, 0xA000)) rez += 0100;

		byte[] a = extract(op1);
		byte[] b = extract(op2);

		for (int i = 15; i > 0; i--)
			if (a[i] < b[i]) {
				rez += 01000;
				break;
			}

		if (op1 < op2) rez += 010000;
	}

	private byte[] extract(int value) {
		int y = value;
		byte[] rez = new byte[16];
		for (int i = 0; (i < 16 && y != 0); i++) {
			rez[i] = (byte)(y % 2);
			y = y >> 1;
		}

		return rez;
	}

	private boolean isSuper() {
		return check(registers.mode, 0x1, 0x1);
	}

	private void HALT() {
		halt();
	}

	private void Txyz(int x, int y, int z) {
		if (x == 0) {
			if (z == 0) registers.r = registers.ptr;
			else registers.m = registers.ptr;
		} else {
			setActualWord(y * 0x10 + z, registers.ptr);
		}
	}

	private void Uxyz(int x, int y, int z) {
		if (x == 0) {
			if (z == 0) registers.ptr = registers.r;
			else registers.ptr = registers.m;
		} else {
			registers.ptr = getActualWord(y * 0x10 + z);
		}
	}

    // Sudetis
    private void Axyz(int x, int y, int z) {
        Arritmetic(true, true, x, y, z);
    }

    // Atimtis
    private void Sxyz(int x, int y, int z) {
        Arritmetic(false, true, x, y, z);
    }
	//lyginimas
	private void Cxyz(int x, int y, int z) {
		Arritmetic(false, true, x, y, z);
	}

	private void USyz(int y, int z) {
		registers.pd = y * 0x100 + z;
		setUserMode();
	}

    // Jei type yra true - sudetis, jei false - atimtis
    private void Arritmetic(boolean type, boolean save, int x, int y, int z) {
		int op1 = 0, op2 = 0, rez = 0;
        switch (x) {
            case 0:
                if (z == 0) {
					op1 = op2 = registers.r;
					if (save) {
						if (type)
							registers.r += registers.r;
						else
							registers.r -= registers.r;
					}

					rez = registers.r;
                }
                else if (z ==1) {
					op1 = registers.r;
					op2 = registers.m;

					if (save) {
						if (type)
							registers.r += registers.m;
						else
							registers.r -= registers.m;
					}

					rez = registers.r;
                }
                break;

            case 1:
				op1 = registers.r;
				op2 = getActualWord(y * 0x10 + z);

				if (save) {
					if (type)
						registers.r += getActualWord(y * 0x10 + z);
					else
						registers.r -= getActualWord(y * 0x10 + z);
				}

				rez = registers.r;
				
                break;

            case 16:
                if (z == 0) {
					op1 = registers.m;
					op2 = registers.r;
					if (save) {
						if (type)
							registers.m += registers.r;
						else
							registers.m -= registers.r;
					}

					rez = registers.m;
                }
                else if (z ==1) {
					op1 = op2 = registers.m;

					if (save) {
						if (type)
							registers.m += registers.m;
						else
							registers.m -= registers.m;
					}

					rez = registers.m;
                }
                break;

            case 17:
				op1 = registers.m;
				op2 = getActualWord(y * 0x10 + z);

				if (save) {
					if (type)
						registers.m += getActualWord(y * 0x10 + z);
					else
						registers.m += getActualWord(y * 0x10 + z);
				}

				rez = registers.m;

                break;
        }

		generateSF(rez, op1, op2);
    }

	private int getActualWord(int adr) {
		adr %= 0x10000;

		if (check(registers.mode, 0x1, 0x1)) {
			//supervizoriaus rezimas
			return ram[adr];
		}

		int vir = registers.ptr * 0x10 + ((adr & 0xF00) >> 16);
		int adr2 = ram[vir];

		return ram[adr2 * 0x100 + adr & 0xFF];
	}

	private void setActualWord(int adr, int word) {
		adr %= 0x10000;

		if (check(registers.mode, 0x1, 0x1)) {
			//supervizoriaus rezimas
			ram[adr] = word;
		}

		int vir = registers.ptr * 0x10 + ((adr & 0xF00) >> 16);
		int adr2 = ram[vir];

		ram[adr2 * 0x100 + adr & 0xFF] = word;
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
		int[] vm = new int[0x1000];

		for (byte i = 0; i < 0x10; i++) {
			int adr = ram[registers.ptr * 0x10 + i];
			for (int a = 0; a < 0x100; a++)
				vm[i * 0x100 + a] = ram[adr * 0x100 + a];
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

		inited = false;

		if (events != null)
			events.onHalt(this);

		return ram;
	}

	public boolean loadDump(int[] data) {
		if (data.length > ram.length) return false;
		
		for (int i = 0; i < data.length; i++)
			ram[i] = data[i];
		
		return true;
	}

	public static void main(String[] args) {
		int[] dump = {
			0x0021,   //0
			0x0000,   //1
			0x0000,   //2
			0x0001,   //3
			0x0000,   //4
			0x0000,   //5
			0x0000,   //6
			0x0000,   //7
			0x0013,   //8  GD interupas
			0x0012,   //9
			0x0012,   //A
			0x0012,   //B
			0x0012,   //C
			0x0012,   //D
			0x0012,   //E
			0x0012,   //F
			0x0012,   //10
			0x0012,   //11
			0x49524554,  //12 tuscias interupas
			0x49524554,  //13 GD interupas
			0x00000001,  //14
			0x00000002,  //15
			0x3,         //16
			0x4,         //17
			0x5,         //18
			0x6,         //19
			0x7,         //1A
			0x8,         //1B
			0x9,         //1C
			0x40000000,  //1D  HELL
			0x01000000,  //1E  O WO
			0x524c4421,  //1F  RLD!
			0x00000019,  //20
            0x4C010020,  //21
            0x4C00001D,  //22
            0x4101001E,
            0x4900001D,
			0x5044001D,  //21 PD 1C
            0x6C4F0023,
			0x48414C54   //22
		};

		RealMachine rm = Machine.createMachine();
		rm.loadDump(dump);

		//rm.run();

		for (int i = 0; i <= 400; i++) {
			rm.step();
			//System.out.println(rm.getRegister().m);
		}

		//rm.step();

		/*

		int[] mem = rm.getMemoryDump();

		int i = 0;

		for (int m : mem) {
			if (i++ % 16 == 0) {
				System.out.println();
				System.out.print(i / 16 + ": ");
			}

			System.out.print(parseInt(m) + " ");

			if (i > 80) break;
		}
		 */

		byte[] screen = rm.getScreen();

		int i = 0;
		for (byte b : screen) {
			System.out.print((char)b);
			if (++i == 80)
				System.out.println();
		}
	}

	public static String parseInt(int v) {
		String rez = "";

		char[] a = new char[8];

		for (int i = 7; i >= 0; i--) {
			int b = v % 0x10;
			v >>= 4;
			if (b <= 9)
				a[i] = (char)(48 + b);
			else
				a[i] = (char)(65 + b - 10);
		}

		for (char c : a)
			rez += c;

		return rez;
	}

	public boolean isRunning() {
		return running;
	}

	private void PSHx(int x) {
		registers.s++;
		registers.s &= 0xFFFF;
		switch (x) {
			case 1: ram[registers.s] = registers.r; break;
			case 2: ram[registers.s] = registers.m; break;
			case 3: ram[registers.s] = registers.sf; break;
			case 4: ram[registers.s] = registers.pd; break;
			case 5: ram[registers.s] = registers.ptr; break;
			case 6: ram[registers.s] = registers.ic; break;
		}
	}

	private void POPx(int x) {
		registers.s--;
		registers.s &= 0xFFFF;
		switch (x) {
			case 1: registers.r = ram[registers.s]; break;
			case 2: registers.m = ram[registers.s]; break;
			case 3: registers.sf = ram[registers.s]; break;
			case 4: registers.pd = ram[registers.s]; break;
			case 5: registers.ptr = ram[registers.s]; break;
			case 6: registers.ic = ram[registers.s]; break;
		}
	}

	private void enter() {
		PSHx(1);
		PSHx(2);
		PSHx(3);
		PSHx(4);
		PSHx(5);
	}

	private void leave() {
		POPx(5);
		POPx(4);
		POPx(3);
		POPx(2);
		POPx(1);
	}

	private void sLxy(int x, int y) {
		switch (x) {
			case 1: registers.r <<= y; break;
			case 2: registers.m <<= y; break;
		}
	}

	private void sRxy(int x, int y) {
		switch (x) {
			case 1: registers.r >>>= y; break;
			case 2: registers.m >>>= y; break;
		}
	}

	private void sPxy(int x, int y) {
		switch (x) {
			case 1: registers.r >>= y; break;
			case 2: registers.m >>= y; break;
		}
	}

	private void loop(int adr) {
		if (registers.m != 0) {
			registers.m--;
			registers.ic = adr;
		}
	}

	private void lOxy(int x, int y) {
		loop(x * 0x100 + y);
	}

	private void lPxy(int y) {
		loop(registers.ic - y);
	}

	private void cLxy(int x, int y) {
		call(x, y);
	}

	private void call(int x, int y) {
		call(0x100 * x + y);
	}

	private void call(int x) {
		PSHx(5);
		jump(x);
	}

	private void RETx(int x) {
		POPx(5);
		registers.s -= x;
		registers.s &= 0xFFFF;
	}

	private void IRET() {
		leave();
	}

	private void interupt(int x) {
		boolean wasSuper = false;

		if (!isSuper())
			halt();
		else
			wasSuper = true;


		enter();

		if (wasSuper) registers.pd = 0;

		jump(2, 0, 7 + x);
	}

	private void interupt(int x, boolean changeR, int r, boolean changeM, int m) {
		interupt(x);
		
		if (changeR)
			registers.r = r;

		if (changeM)
			registers.m = m;
	}

	public boolean pushData(byte[] data) {
		for (int i = 0; i < data.length; i+= 4) {
			int rez = 0;
			if (data.length > i) rez += data[i] * 0x1000000;
			if (data.length > i+1) rez += data[i + 1] * 0x10000;
			if (data.length > i+2) rez += data[i + 2] * 0x100;
			if (data.length > i+3) rez += data[i + 3];

			ram[registers.chm2 + i / 4] = rez;
		}

		interupt(1, true, registers.chm2, false, 0);
		registers.pd = 0;

		return true;
	}

	private int virtualToReal(int virtual) {
		int seg = virtual / 0x100;
		int a = ram[registers.ptr + seg];
		return a * 0x100 + virtual % 0x100;
	}

	private int virtualToReal(int x, int y) {
		return virtualToReal(0x100 * x + y);
	}

	private void VMGD(int x, int y) {
		int adr = 0x100 * x + y;
		int realadr = virtualToReal(adr);

		interupt(1, true, realadr, false, 0);
	}

	private void GD(int x, int y) {
		if (!isSuper()) {
			VMGD(x, y);
			return;
		}

		registers.chm2 = 0x100 * x + y;
		if (events != null)
			events.onRequestInput(this);
	}

	private void pushOnScreen(byte[] data) {
		for (byte b : data) {
			if (b == 13)
				screenPointer = ((screenPointer / 80) + 1) * 80;

			screen[screenPointer++] = b;
			if (screenPointer >= screen.length)
				screenPointer = 0;
		}
	}

	private void PD(int x, int y) {
		int adr = 0x100 * x + y;

		if (!isSuper()) adr = virtualToReal(adr);

		byte[] buffer = new byte[256];
		boolean stop = false;

		for (int i = 0; i < 256; i++) {
			if (!stop) {
				buffer[i * 4] = (byte)((ram[adr + i] & 0xFF000000) >>> 24);
				if (buffer[i * 4] == 0) stop = true;
			}
			if (!stop) {
				buffer[i * 4 + 1] = (byte)((ram[adr + i] & 0xFF0000) >>> 16);
				if (buffer[i * 4 + 1] == 0) stop = true;
			}
			if (!stop) {
				buffer[i * 4 + 2] = (byte)((ram[adr + i] & 0xFF00) >>> 8);
				if (buffer[i * 4 + 2] == 0) stop = true;
			}
			if (!stop) {
				buffer[i * 4 + 3] = (byte)(ram[adr + i] & 0xFF);
				if (buffer[i * 4 + 3] == 0) stop = true;
			}
		}

		//skaiciuojam ilgi
		int ilgis;
		for (ilgis = 0; (ilgis < buffer.length && buffer[ilgis] != 0); ilgis++);

		byte[] realBuffer = new byte[ilgis];

		for (int i = 0; i < ilgis; i++)
			realBuffer[i] = buffer[i];

		pushOnScreen(realBuffer);

                if (events != null)
                    events.onScreen(this);
	}

	
}