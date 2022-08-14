package mods.eln.gridnode.transformer

import mods.eln.sim.IProcess
import mods.eln.sim.electrical.mna.misc.IRootSystemPreStepProcess

class GridTransformerThermalFailureProcess(element: GridTransformerElement) : IRootSystemPreStepProcess, IProcess {

    var gridTransformerElement: GridTransformerElement = element

    var doFailure = false

    var maxHeat = 0.0

    private var baseRsPrimary = gridTransformerElement.primaryLoad.rs
    private var baseRsSecondary = gridTransformerElement.secondaryLoad.rs


    fun doFailure() {
        doFailure = true
    }

    fun doFailure(doFailure: Boolean) {
        this.doFailure = doFailure
    }



    override fun rootSystemPreStepProcess() {
       //println("its pre time!")
        if(doFailure) {
            if(Math.random() < 0.25) {
                gridTransformerElement.primaryLoad.rs = baseRsPrimary
                gridTransformerElement.secondaryLoad.rs = baseRsSecondary
            } else {
                gridTransformerElement.primaryLoad.rs += Math.random()*20
                gridTransformerElement.secondaryLoad.rs += Math.random()*20
            }
        }
    }

    override fun process(time: Double) {
       // println("processes")

        if(gridTransformerElement.thermalLoad.t > maxHeat) {
            doFailure()
            gridTransformerElement.desc.arcVolume = 1.0f
        } else {
            gridTransformerElement.desc.arcVolume = 0f
            doFailure(false)
        }
    }
}
