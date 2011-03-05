package RealMachine;

import java.util.Collection;

/**
 *
 * @author neworld
 */
public interface RealMachine {
	public boolean run();
	public boolean stop();
	public boolean step();

	public boolean putChar(char ch);
	public Collection<Byte> getScreen();
	public Collection<Byte> getMemoryDump();
	public Collection<Byte> getExternalMemory();
	public void changeMemory(int adr, byte data);

	public Registers getRegister();
	public boolean loadDump(Collection<Byte> data);
	/**
	 * priskiriame RealMachine.Event realizacij1, kurioje bus apibrezti eventu funkcijos
	 * @param event
	 * @return
	 */
	public boolean event(Event event);

	public Collection<Byte> halt();
}
