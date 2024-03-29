package mods.eln.sim.watchdogs;

import mods.eln.Eln;
import mods.eln.gridnode.transformer.GridTransformerElement;
import mods.eln.misc.Coordinate;
import mods.eln.node.INodeElement;
import mods.eln.node.six.SixNodeElement;
import mods.eln.node.transparent.TransparentNodeElement;
import mods.eln.sim.electrical.ElectricalLoad;
import mods.eln.sim.electrical.mna.component.Bipole;
import mods.eln.sim.electrical.mna.component.Component;
import mods.eln.sim.electrical.mna.component.VoltageSource;
import mods.eln.sim.electrical.mna.state.State;
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherNode;
import net.minecraft.init.Blocks;

public class WorldFailure implements IDestructible {

    Object origin;

    Coordinate c;
    float strength;
    String type;

    INodeElement node;









    public WorldFailure(SixNodeElement e) {
        this.c = e.getCoordinate();
        this.type = e.toString();
        origin = e;
        node = e;


    }

    public WorldFailure(TransparentNodeElement e) {
        this.c = e.coordinate();
        this.type = e.toString();
        origin = e;
        node = e;
    }

    public WorldFailure cableExplosion() {
        strength = 1.5f;
        return this;
    }

    public WorldFailure machineExplosion() {
        strength = 3;
        return this;
    }

    @Override
    public void destructImpl() {
        if (Eln.explosionEnable)
            c.world().createExplosion(null, c.x, c.y, c.z, strength, true);
        else
            c.world().setBlock(c.x, c.y, c.z, Blocks.air);
    }

    @Override
    public String describe() {
        return String.format("%s (%s)", this.type, this.c.toString());
    }

    



}
