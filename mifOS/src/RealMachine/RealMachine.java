package RealMachine;

import Event.Event;

/**
 *
 * @author neworld
 */
public interface RealMachine {
	public boolean run();
	public boolean stop();
	public boolean step();

	public boolean putChar(byte ch);
	public byte[] getScreen();
	public int[] getMemoryDump();
	public int[] getExternalMemory();
	/**
	 * gauti virtualia atminti pagal puslapiu lentele
	 * @return Byte[]
	 */
	public int[] getVirtualMemory();
	public boolean changeMemory(int adr, int data);

	public Registers getRegister();
	public boolean loadDump(int[] data);
	/**
	 * priskiriame RealMachine.Event realizacij1, kurioje bus apibrezti eventu funkcijos
	 * @param event
	 * @return Byte[]
	 */
	public boolean event(Event event);
	/**
	 * nutraukiame masinos darba
	 * @return Byte[] atminties masyvas
	 */
	public int[] halt();
}
