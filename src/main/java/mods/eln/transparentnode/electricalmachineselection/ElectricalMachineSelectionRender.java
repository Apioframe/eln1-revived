package mods.eln.transparentnode.electricalmachineselection;

import mods.eln.cable.CableRenderType;
import mods.eln.misc.Direction;
import mods.eln.misc.LRDUMask;
import mods.eln.node.transparent.TransparentNodeDescriptor;
import mods.eln.node.transparent.TransparentNodeElementInventory;
import mods.eln.node.transparent.TransparentNodeElementRender;
import mods.eln.node.transparent.TransparentNodeEntity;
import mods.eln.sound.LoopedSound;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.io.DataInputStream;
import java.io.IOException;

public class ElectricalMachineSelectionRender extends TransparentNodeElementRender {
    private final TransparentNodeElementInventory inventory;

    final ElectricalMachineSelectionDescriptor descriptor;

    private final Object drawHandle;

    private CableRenderType connectionType;
    private final LRDUMask eConn = new LRDUMask();

    private EntityItem inEntity;
    private EntityItem outEntity;
    float powerFactor;
    float processState;
    private float processStatePerSecond;

    float UFactor;

    public ElectricalMachineSelectionRender(TransparentNodeEntity tileEntity, final TransparentNodeDescriptor descriptor) {
        super(tileEntity, descriptor);
        this.descriptor = (ElectricalMachineSelectionDescriptor) descriptor;
        inventory = new ElectricalMachineSelectionInventory(3 + this.descriptor.outStackCount, 64, this);
        drawHandle = this.descriptor.newDrawHandle();

        if (this.descriptor.runningSound != null) {
            addLoopedSound(new LoopedSound(this.descriptor.runningSound, coordinate(), ISound.AttenuationType.LINEAR) {
                @Override
                public float getPitch() {
                    return powerFactor;
                }

                @Override
                public float getVolume() {
                    return ElectricalMachineSelectionRender.this.descriptor.volumeForRunningSound(processState, powerFactor);
                }
            });
        }
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        front.glRotateXnRef();
        descriptor.draw(this, drawHandle, inEntity, outEntity, powerFactor, processState);
        GL11.glPopMatrix();

        if (descriptor.drawCable())
            connectionType = drawCable(front.down(), descriptor.getPowerCableRender(), eConn, connectionType);
    }

    @Override
    public void refresh(float deltaT) {
        super.refresh(deltaT);
        processState += processStatePerSecond * deltaT;
        if (processState > 1f) processState = 1f;
        descriptor.refresh(deltaT, this, drawHandle, inEntity, outEntity, powerFactor, processState);
    }

    @Override
    public boolean cameraDrawOptimisation() {
        return false;
    }

    @Nullable
    @Override
    public GuiScreen newGuiDraw(@NotNull Direction side, @NotNull EntityPlayer player) {
        return new ElectricalMachineSelectionGuiDraw(player, inventory, this);
    }

    @Override
    public void networkUnserialize(DataInputStream stream) {
        super.networkUnserialize(stream);

        try {
            powerFactor = stream.readByte() / 64f;
            inEntity = unserializeItemStackToEntityItem(stream, inEntity);
            outEntity = unserializeItemStackToEntityItem(stream, outEntity);
            processState = stream.readFloat();
            processStatePerSecond = stream.readFloat();
            eConn.deserialize(stream);
            UFactor = stream.readFloat();
            connectionType = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyNeighborSpawn() {
        super.notifyNeighborSpawn();
        connectionType = null;
    }
}