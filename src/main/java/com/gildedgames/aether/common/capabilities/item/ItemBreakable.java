package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ItemBreakable implements IItemBreakable
{

	private boolean canBreak = true;

	public ItemBreakable()
	{

	}

	@Override
	public boolean canBreak()
	{
		return this.canBreak;
	}

	@Override
	public void setCanBreak(boolean canBreak)
	{
		this.canBreak = canBreak;
	}

	public static class Storage implements Capability.IStorage<IItemBreakable>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemBreakable> capability, IItemBreakable instance, EnumFacing side)
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setBoolean("canBreak", instance.canBreak());
			
			return tag;
		}

		@Override
		public void readNBT(Capability<IItemBreakable> capability, IItemBreakable instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tag = (NBTTagCompound)nbt;

			instance.setCanBreak(tag.getBoolean("canBreak"));
		}
	}

}

