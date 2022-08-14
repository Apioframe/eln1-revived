package mods.eln.transparentnode.electricalmachinetwoslot;

import mods.eln.sim.IProcess;

public class ElectricalMachineTwoSlotSlowProcess implements IProcess {
    private final ElectricalMachineTwoSlotElement element;

    private double lastPublishAt = 0;
    private double lastUpdate = 0;
    private boolean boot = true;

    public ElectricalMachineTwoSlotSlowProcess(ElectricalMachineTwoSlotElement element) {
        this.element = element;
    }

    @Override
    public void process(double time) {
        double P = element.electricalResistor.getP();
        lastUpdate += time;
        if (!boot) {
            if (Math.abs((P - lastPublishAt) / (lastPublishAt + 1.0)) > 1 / 32.0 && lastUpdate > 0.2) {
                element.needPublish();
                lastPublishAt = P;
                lastUpdate = 0;
            }
        }

        boot = false;
    }
}
