package mods.eln.transparentnode.electricalmachinetwoslot;

import mods.eln.misc.Direction;
import mods.eln.node.transparent.TransparentNodeElementInventory;
import mods.eln.node.transparent.TransparentNodeElementRender;
import net.minecraft.item.ItemStack;

public class ElectricalMachineTwoSlotInventory extends TransparentNodeElementInventory {
    private ElectricalMachineTwoSlotElement machineElement;

    public ElectricalMachineTwoSlotInventory(int size, int stackLimit, ElectricalMachineTwoSlotElement machineElement) {
        super(size, stackLimit, machineElement);
        this.machineElement = machineElement;
    }

    public ElectricalMachineTwoSlotInventory(int size, int stackLimit, TransparentNodeElementRender TransparentnodeRender) {
        super(size, stackLimit, TransparentnodeRender);
    }

    ElectricalMachineTwoSlotDescriptor getDescriptor() {
        if (transparentNodeRender != null) return ((ElectricalMachineTwoSlotRender) transparentNodeRender).descriptor;
        if (transparentNodeElement != null) return ((ElectricalMachineTwoSlotElement) transparentNodeElement).descriptor;
        return null;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (transparentNodeElement == null) return new int[0];

        switch (Direction.fromIntMinecraftSide(side)) {
            case YP:
                return new int[]{machineElement.inSlotId};
            case XP:
                return new int[]{machineElement.inSlotTwoId};
            default:
                int[] slots = new int[machineElement.descriptor.outStackCount];
                for (int idx = 0; idx < slots.length; idx++) {
                    slots[idx] = idx + machineElement.outSlotId;
                }
                return slots;
        }
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack var2, int side) {
        switch (Direction.fromIntMinecraftSide(side)) {
            case YP:
                return true;
            case XP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int side) {
        switch (Direction.fromIntMinecraftSide(side)) {
            case YP:
                return false;
            case XP:
                return false;
            default:
                return true;
        }
    }
}