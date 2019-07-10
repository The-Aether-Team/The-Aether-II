package com.gildedgames.aether.common.entities.resistance;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Map;

public class ResistanceStorage implements Capability.IStorage<IResistances>
{
	@Override
	public NBTBase writeNBT(Capability<IResistances> capability, IResistances instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();
		for (Map.Entry<String, Float> stringFloatEntry : instance.getResistances().entrySet())
		{
			Map.Entry pair = stringFloatEntry;
			tag.setFloat(pair.getKey().toString(), (float) pair.getValue());
		}
		return tag;
	}

	@Override
	public void readNBT(Capability<IResistances> capability, IResistances instance, EnumFacing side, NBTBase nbt)
	{
		Map<String, Float> resist = new HashMap<>();
		//((NBTTagCompound)nbt).get
		instance.setResistances(resist);
	}
}
