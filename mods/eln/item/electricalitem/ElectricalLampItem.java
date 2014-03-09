package mods.eln.item.electricalitem;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import mods.eln.Eln;
import mods.eln.PlayerManager;
import mods.eln.item.electricalinterface.IItemEnergyBattery;
import mods.eln.misc.Utils;
import mods.eln.wiki.Data;

public class ElectricalLampItem extends LampItem implements IItemEnergyBattery{

	public ElectricalLampItem(
			String name,
			int lightMin,int rangeMin,double dischargeMin,int lightMax,int rangeMax,double dischargeMax,
			double energyStorage,double chargePower
			
			) {
		super(name);
		this.lightMin = lightMin;
		this.rangeMin = rangeMin;
		this.lightMax = lightMax;
		this.rangeMax = rangeMax;
		this.chargePower = chargePower;
		this.dischargeMin = dischargeMin;
		this.dischargeMax = dischargeMax;
		this.energyStorage = energyStorage;
		on = new ResourceLocation("eln", "textures/items/" + name.replace(" ", "").toLowerCase() + "on.png");
		off = new ResourceLocation("eln", "textures/items/" + name.replace(" ", "").toLowerCase() + "off.png");
	//	off = new ResourceLocation("eln", "/model/StoneFurnace/all.png");
	}
	int lightMin,rangeMin;
	int lightMax,rangeMax;
	double energyStorage, dischargeMin,dischargeMax, chargePower;

	ResourceLocation on,off;
	@Override
	public void setParent(Item item, int damage) {
		// TODO Auto-generated method stub
		super.setParent(item, damage);
		Data.addPortable(newItemStack());
		Data.addLight(newItemStack());
	}
	
	@Override
	int getRange(ItemStack stack) {
		// TODO Auto-generated method stub
		return getLightState(stack) == 1 ? rangeMin : rangeMax;
	}
	
	@Override
	int getLight(ItemStack stack) {
		double energy = getEnergy(stack);
		int state = getLightState(stack);
		double power = 0;
		switch (state) {
		case 1:
			power = dischargeMin*0.05;
			break;
		case 2:
			power = dischargeMax*0.05;
			break;
		}
		if(energy > power){
			setEnergy(stack, energy - power);
			return getLightLevel(stack);
		}
		else{
			setEnergy(stack,0);
			return 0;
		}
	}
	
	@Override
	public NBTTagCompound getDefaultNBT() {
		NBTTagCompound nbt = new NBTTagCompound("itemStackNBT");
		nbt.setDouble("energy",0);
		nbt.setBoolean("powerOn",false);
		nbt.setInteger("rand", (int) (Math.random()*0xFFFFFFF));
		return nbt;
	}
	

	
	int getLightState(ItemStack stack)
	{
		return getNbt(stack).getInteger("LightState");
	}
	void setLightState(ItemStack stack,int value)
	{
		getNbt(stack).setInteger("LightState",value);
	}
	
	int getLightLevel(ItemStack stack)
	{
		return getLightState(stack) == 1 ? lightMin : lightMax;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4,
			boolean par5) {
		
		if(world.isRemote == false && entity instanceof EntityPlayer && ((EntityPlayer) entity).inventory.getCurrentItem() == stack && Eln.playerManager.get((EntityPlayer) entity).getInteractRise()){
			int lightState = getLightState(stack) + 1;
			if(lightState > 1) lightState = 0;
			//((EntityPlayer) entity).addChatMessage("Flashlight !!!");
			switch (lightState) {
			case 0:
				((EntityPlayer) entity).addChatMessage("Flashlight OFF");
				break;
			case 1:
				((EntityPlayer) entity).addChatMessage("Flashlight ON");
				break;
			case 2:
				((EntityPlayer) entity).addChatMessage("Flashlight ON-2");
				break;

			default:
				break;
			}
			setLightState(stack, lightState);
		}
		super.onUpdate(stack, world, entity, par4, par5);
	}

	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer,
			List list, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(itemStack, entityPlayer, list, par4);
		
		list.add(Utils.plotEnergy("Energy stored", getEnergy(itemStack)) + "(" + (int)(getEnergy(itemStack)/energyStorage*100) + "%)");
		list.add("Power button is " + (getLightState(itemStack) != 0 ? "ON" : "OFF"));
	}
/*
	@Override
	public double putEnergy(ItemStack stack, double energy,double time) {
		double hit = Math.min(energy,Math.min(energyStorage - getEnergy(stack), chargePower*time));
		setEnergy(stack, getEnergy(stack) + hit);
		return energy - hit;
	}

	@Override
	public boolean isFull(ItemStack stack) {
		// TODO Auto-generated method stub
		return getEnergy(stack) == energyStorage;
	}
*/

	public double getEnergy(ItemStack stack)
	{
		return getNbt(stack).getDouble("energy");
	}
	public void setEnergy(ItemStack stack,double value)
	{
		getNbt(stack).setDouble("energy",value);
	}

	@Override
	public double getEnergyMax(ItemStack stack) {
		// TODO Auto-generated method stub
		return energyStorage;
	}

	@Override
	public double getChargePower(ItemStack stack) {
		// TODO Auto-generated method stub
		return chargePower;
	}

	@Override
	public double getDischagePower(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		if(type == ItemRenderType.INVENTORY)
			return false;
		return true;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {		
		if(type == ItemRenderType.INVENTORY)		
			Utils.drawEnergyBare(type,(float) (getEnergy(item)/getEnergyMax(item)));
		Utils.drawIcon(type,(getLightState(item) != 0? on : off));
	}
}
