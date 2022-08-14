package mods.eln.transparentnode.electricalmachinetwoslot;

import mods.eln.Eln;
import mods.eln.misc.*;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.sim.thermal.ThermalLoadInitializer;
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor;
import mods.eln.transparentnode.electricalmachine.ElectricalMachineDescriptor;
import mods.eln.transparentnode.electricalmachine.ElectricalMachineRender;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

public class CableMakerDescriptor extends ElectricalMachineTwoSlotDescriptor {
    private final Obj3D obj;
    private Obj3DPart main;
    private Obj3DPart chute;


    public CableMakerDescriptor(String name, String modelName, double nominalU, double nominalP, double maximalU,
                                ThermalLoadInitializer thermal, ElectricalCableDescriptor cable, RecipesList recipe) {
        super(name, nominalU, nominalP, maximalU, thermal, cable, recipe);
        obj = Eln.obj.getObj(modelName);
        if (obj != null) {
            main = obj.getPart("main");
            //chute = obj.getPart("Chute_Cube.001");
            chute = obj.getPart("move");
        }
    }

    class CableMakerDescriptorHandle {
        float counter = 0, itemCounter = 0;
        final RcInterpolator interpolator = new RcInterpolator(0.5f);
    }

    @Override
    Object newDrawHandle() {
        return new CableMakerDescriptorHandle();
    }

    @Override
    void draw(ElectricalMachineTwoSlotRender render, Object handleO, EntityItem inEntity, EntityItem outEntity,
              float powerFactor, float processState) {


        main.draw();
        chute.draw();

        //UtilsClient.enableDepthTest();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
    }

    @Override
    void refresh(float deltaT, ElectricalMachineTwoSlotRender render, Object handleO, EntityItem inEntity,
                 EntityItem outEntity, float powerFactor, float processState) {
        CableMakerDescriptorHandle handle = (CableMakerDescriptorHandle) handleO;

        handle.interpolator.setTarget(powerFactor);
        handle.interpolator.step(deltaT);
        handle.counter += deltaT * handle.interpolator.get() * 180;
        while (handle.counter >= 360f) handle.counter -= 360;

        handle.itemCounter += deltaT * 90;
        while (handle.itemCounter >= 360f) handle.itemCounter -= 360;
    }

    @Override
    public boolean powerLrdu(Direction side, Direction front) {
        return side != front && side != front.getInverse();
    }
}
