package mods.eln.transparentnode.electricalmachineselection;

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

import java.util.ArrayList;
import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ElectricalMachineSelectionContainer extends BasicContainer implements INodeContainer {
    private NodeBase node = null;

    public ElectricalMachineSelectionContainer(NodeBase node, EntityPlayer player, IInventory inventory,
                                               ElectricalMachineSelectionDescriptor descriptor) {
        super(player, inventory, getSlot(inventory, descriptor));
        this.node = node;
    }

    private static Slot[] getSlot(IInventory inventory, ElectricalMachineSelectionDescriptor descriptor) {
        Slot[] slots = new Slot[12];

        int slotIndex = 0;
        for (int storageArrayX=1; storageArrayX<=6; storageArrayX++) {
            for (int storageArrayY=1; storageArrayY<=2; storageArrayY++) {
                slots[slotIndex] = new SlotWithSkin(inventory, slotIndex, 8+16, 8+16,SlotSkin.medium);
                System.out.println("[SLOTGEN] x:"+storageArrayX+" y:"+storageArrayY+" id:"+slotIndex);
                slotIndex++;
            }
        }

        //slots[0] = new SlotWithSkin(inventory, 0, 24, 24, SlotSkin.medium);



        /*for (int idx = 0; idx < descriptor.outStackCount; idx++) {
            slots[idx] = new SlotWithSkin(inventory, idx, 130 - 32 + idx * 18, 12, SlotSkin.medium);
        }*/
        /*
        slots[slots.length+descriptor.outStackCount] = new SlotWithSkin(inventory,
            slots.length+descriptor.outStackCount, 8 + 36, 12, SlotSkin.big);
        slots[descriptor.outStackCount+2] = new SlotWithSkin(inventory,
            slots.length+descriptor.outStackCount + 2, 26, 12, SlotSkin.none);
        slots[slots.length+descriptor.outStackCount + 1] = new GenericItemUsingDamageSlot(inventory,
            slots.length+descriptor.outStackCount + 1, 8, 12, 5,
            MachineBoosterDescriptor.class,
            SlotSkin.medium,
            new String[]{tr("Booster slot")});
        */
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
