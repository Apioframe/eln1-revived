package mods.eln.misc

import mods.eln.Eln
import mods.eln.transparentnode.electricalfurnace.ElectricalFurnaceProcess
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import java.util.*
import kotlin.collections.ArrayList

class RecipesListTwoSlot {
    val recipes = ArrayList<RecipeTwoSlot>()
    val machines = ArrayList<ItemStack>()
    fun addRecipe(recipe: RecipeTwoSlot) {
        recipes.add(recipe)
        recipe.setMachineList(machines)
    }

    fun addMachine(machine: ItemStack) {
        machines.add(machine)
    }

    fun getRecipe(input: ItemStack?, input2: ItemStack?): RecipeTwoSlot? {
        for (r in recipes) {
            if (r.canBeCraftedBy(input, input2)) return r
        }
        return null
    }

    fun getRecipeFromOutput(output: ItemStack?): ArrayList<RecipeTwoSlot> {
        if (output == null) return ArrayList()
        val list = ArrayList<RecipeTwoSlot>()
        for (r in recipes) {
            for (stack in r.outputCopy) {
                if (stack != null) {
                    if (Utils.areSame(stack, output)) {
                        list.add(r)
                        break
                    }
                }
            }
        }
        return list
    }

    companion object {
        val listOfList = ArrayList<RecipesListTwoSlot>()

    }

    init {
        listOfList.add(this)
    }
}
