package RealMachine;

/**
 *
 * @author neworld
 */
public interface RealMachine {
	public boolean run();
	public boolean stop();
	public boolean step();

	public boolean putChar(char ch);
	public Byte[] getScreen();
	public Byte[] getMemoryDump();
	public Byte[] getExternalMemory();
	public void changeMemory(int adr, byte data);

	public Registers getRegister();
	public boolean loadDump(Byte[] data);
	/**
	 * priskiriame RealMachine.Event realizacij1, kurioje bus apibrezti eventu funkcijos
	 * @param event
	 * @return
	 */
	public boolean event(Event event);
	/**
	 * nutraukiame masinos darba
	 * @return atminties masyvas
	 */
	public Byte[] halt();
}
