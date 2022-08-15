package mods.eln.transparentnode.electricalmachineselection;

import mods.eln.Eln;
import mods.eln.PleaseWork;
import mods.eln.misc.*;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.sim.thermal.ThermalLoadInitializer;
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

public class SpecialCrafterDescriptor extends ElectricalMachineSelectionDescriptor {
    private final Obj3D obj;
    private Obj3DPart main;


    public SpecialCrafterDescriptor(String name, String modelName, double nominalU, double nominalP, double maximalU,
                                ThermalLoadInitializer thermal, ElectricalCableDescriptor cable, RecipesList recipe) {
        super(name, nominalU, nominalP, maximalU, thermal, cable, recipe);
        obj = Eln.obj.getObj(modelName);
        if (obj != null) {
            main = obj.getPart("Cube");
        }
    }

    class SpecialCrafterDescriptorHandle {
        float counter = 0, itemCounter = 0;
        final RcInterpolator interpolator = new RcInterpolator(0.5f);
    }

    @PleaseWork
    @Override
    Object newDrawHandle() {
        return new SpecialCrafterDescriptorHandle();
    }

    @PleaseWork
    @Override
    void draw(ElectricalMachineSelectionRender render, Object handleO, EntityItem inEntity, EntityItem outEntity,
              float powerFactor, float processState) {


        main.draw();

        //UtilsClient.enableDepthTest();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
    }

    @PleaseWork
    @Override
    void refresh(float deltaT, ElectricalMachineSelectionRender render, Object handleO, EntityItem inEntity,
                 EntityItem outEntity, float powerFactor, float processState) {
        SpecialCrafterDescriptorHandle handle = (SpecialCrafterDescriptorHandle) handleO;

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
