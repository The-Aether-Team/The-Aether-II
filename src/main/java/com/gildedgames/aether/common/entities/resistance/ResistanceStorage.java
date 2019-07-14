package com.gildedgames.aether.common.entities.resistance;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Map;

public class ResistanceStorage implements Capability.IStorage<IResistances>
{
	@Override
	public INBT writeNBT(Capability<IResistances> capability, IResistances instance, Direction side)
	{
		CompoundNBT tag = new CompoundNBT();

		for (Map.Entry<String, Float> entry : instance.getResistances().entrySet())
		{
			tag.putFloat(entry.getKey(), entry.getValue());
		}

		return tag;
	}

	@Override
	public void readNBT(Capability<IResistances> capability, IResistances instance, Direction side, INBT nbt)
	{
		Map<String, Float> resist = new HashMap<>();
		//((NBTTagCompound)nbt).get
		instance.setResistances(resist);
	}
}
