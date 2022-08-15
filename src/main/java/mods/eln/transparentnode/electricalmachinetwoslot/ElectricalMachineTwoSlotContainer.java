package mods.eln.transparentnode.electricalmachinetwoslot;

import mods.eln.generic.GenericItemUsingDamageSlot;
import mods.eln.gui.ISlotSkin.SlotSkin;
import mods.eln.gui.SlotWithSkin;
import mods.eln.item.MachineBoosterDescriptor;
import mods.eln.misc.BasicContainer;
import mods.eln.node.INodeContainer;
import mods.eln.node.NodeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import static mods.eln.i18n.I18N.tr;

public class ElectricalMachineTwoSlotContainer extends BasicContainer implements INodeContainer {
    private NodeBase node = null;

    public ElectricalMachineTwoSlotContainer(NodeBase node, EntityPlayer player, IInventory inventory,
                                             ElectricalMachineTwoSlotDescriptor descriptor) {
        super(player, inventory, getSlot(inventory, descriptor));
        this.node = node;
    }

    private static Slot[] getSlot(IInventory inventory, ElectricalMachineTwoSlotDescriptor descriptor) {
        Slot[] slots = new Slot[3 + descriptor.outStackCount];
        for (int idx = 0; idx < descriptor.outStackCount; idx++) {
            slots[idx] = new SlotWithSkin(inventory, idx, 130 - 32 + idx * 18, 12, SlotSkin.medium);
        }
        slots[descriptor.outStackCount] = new SlotWithSkin(inventory,
            descriptor.outStackCount, 8 + 36, 12, SlotSkin.medium);
        slots[descriptor.outStackCount+2] = new SlotWithSkin(inventory,
            descriptor.outStackCount + 2, 26, 12, SlotSkin.medium);
        slots[descriptor.outStackCount + 1] = new GenericItemUsingDamageSlot(inventory,
            descriptor.outStackCount + 1, 8, 12, 5,
            MachineBoosterDescriptor.class,
            SlotSkin.medium,
            new String[]{tr("Booster slot")});

        return slots;
    }

    @Override
    public NodeBase getNode() {
        return node;
    }

    @Override
    public int getRefreshRateDivider() {
        return 1;
    }
}