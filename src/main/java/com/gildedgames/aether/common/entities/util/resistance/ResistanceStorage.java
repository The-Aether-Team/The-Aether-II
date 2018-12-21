package com.gildedgames.aether.common.entities.util.resistance;

import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResistanceStorage implements Capability.IStorage<IResistances>
{
	@Override
	public NBTBase writeNBT(Capability<IResistances> capability, IResistances instance, EnumFacing side)
	{
		NBTTagCompound tag = new NBTTagCompound();
		Iterator it = instance.getResistances().entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			tag.setFloat(pair.getKey().toString(), (float)pair.getValue());
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
