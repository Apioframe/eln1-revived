package mods.eln.transparentnode.electricalmachineselection;

import mods.eln.gui.GuiContainerEln;
import mods.eln.gui.GuiHelperContainer;
import mods.eln.gui.GuiVerticalVoltageSupplyBar;
import mods.eln.node.transparent.TransparentNodeElementInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class ElectricalMachineSelectionGuiDraw extends GuiContainerEln {
    private final TransparentNodeElementInventory inventory;
    private final ElectricalMachineSelectionRender render;

    private GuiVerticalVoltageSupplyBar voltageBar;

    public ElectricalMachineSelectionGuiDraw(EntityPlayer player, IInventory inventory, ElectricalMachineSelectionRender render) {
        super(new ElectricalMachineSelectionContainer(null, player, inventory, render.descriptor));
        this.inventory = (TransparentNodeElementInventory) inventory;
        this.render = render;
    }

    @Override
    public void initGui() {
        super.initGui();

        voltageBar = new GuiVerticalVoltageSupplyBar(176 - 1, 8, 20, 122 - 18 + 120, helper);
        voltageBar.setNominalU((float) render.descriptor.nominalU);
        helper.add(voltageBar);
    }

    @Override
    protected GuiHelperContainer newHelper() {
        return new GuiHelperContainer(this, 176 + 28, 241, 8, 160);
    }

    @Override
    protected void postDraw(float f, int x, int y) {
        super.postDraw(f, x, y);

        helper.drawProcess(67, 33 - 20 - 2, render.processState);

        voltageBar.setVoltage((float) (render.UFactor * render.descriptor.nominalU));
        voltageBar.setPower((float) (render.powerFactor * render.descriptor.nominalP));
    }
}
