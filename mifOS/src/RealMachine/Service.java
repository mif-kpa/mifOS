package RealMachine;

/**
 *
 * @author neworld
 */
public class Service extends Process {
	protected Service (Machine machine, String name, Busenos busena, int descID, byte data[]) {
		super(machine, name, busena, descID, data);
	}
}
