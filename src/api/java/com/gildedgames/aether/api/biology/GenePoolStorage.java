package com.gildedgames.aether.api.biology;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class GenePoolStorage implements Capability.IStorage<GenePool>
{

	@Override
	public NBTBase writeNBT(Capability<GenePool> capability, GenePool instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("seed", instance.getSeed());
		tag.setInteger("fatherSeed", instance.getFatherSeed());
		tag.setInteger("motherSeed", instance.getMotherSeed());

		return tag;
	}

	@Override
	public void readNBT(Capability<GenePool> capability, GenePool instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound tag = (NBTTagCompound)nbt;

		int seed = tag.getInteger("seed");
		int fatherSeed = tag.getInteger("fatherSeed");
		int motherSeed = tag.getInteger("motherSeed");

		if (seed == fatherSeed && seed == motherSeed)
		{
			instance.transformFromSeed(seed);
		}
		else
		{
			instance.transformFromParents(seed, fatherSeed, motherSeed);
		}
	}

}
