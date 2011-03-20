/*
 * Klase reikalinga ivykiu paleidimui.
 * Ivykdzius metoda EmulatorFrame.createAndShow() bus sukuriamas launcher'io
 * objektas, kuris bus perduotas controller'iui -> RealMachine objektas turi
 * zinoti apie sukurtaji launcher'io objekta, o ne pati sukurti naujus
 * ir juos naudoti.
 */

package Event;

import RealMachine.RealMachine;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class RMEventLauncher extends Event
{
    private RMEventListener rmel;

    public boolean onStep(RealMachine rm)
    {
        rmel.stepRequested();
        return true;
    }

    public boolean onScreen(RealMachine rm)
    {
        rmel.outputRequested();
        return true;
    }

    public boolean onRequestInput(RealMachine rm)
    {
        rmel.inputRequested();
        return true;
    }

    public void addListener(RMEventListener rmel)
    {
        this.rmel = rmel;
    }

	@Override
	public boolean onHalt(RealMachine rm) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
