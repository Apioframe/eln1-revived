package mods.eln.misc

import net.minecraft.item.ItemStack
import java.util.*

class RecipeTwoSlot {
    @JvmField
    var input: ItemStack
    @JvmField
    var input2: ItemStack
    @JvmField
    var output: Array<ItemStack>
    @JvmField
    var energy: Double

    constructor(input: ItemStack, input2: ItemStack, output: Array<ItemStack>, energy: Double) {
        this.input = input
        this.output = output
        this.energy = energy
        this.input2 = input2
    }

    constructor(input: ItemStack, input2: ItemStack, output: ItemStack, energy: Double) {
        this.input = input
        this.output = arrayOf(output)
        this.energy = energy
        this.input2 = input2
    }

    fun canBeCraftedBy(stack: ItemStack?, stack2: ItemStack?): Boolean {
        return if (stack == null || stack2 == null) false else input.stackSize <= stack.stackSize && input2.stackSize <= stack2.stackSize && Utils.areSame(stack, input) && Utils.areSame(stack2,input2)
    }

    val outputCopy: Array<ItemStack?>
        get() {
            val cpy = arrayOfNulls<ItemStack>(output.size)
            for (idx in output.indices) {
                cpy[idx] = output[idx].copy()
            }
            return cpy
        }

    @JvmField
    var machineList = ArrayList<ItemStack>()
    fun setMachineList(machineList: ArrayList<ItemStack>) {
        this.machineList = machineList
    }
}
