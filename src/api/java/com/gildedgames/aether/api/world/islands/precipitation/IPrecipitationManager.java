package com.gildedgames.aether.api.world.islands.precipitation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPrecipitationManager extends INBTSerializable<NBTTagCompound>
{
	float getSkyDarkness();

	PrecipitationStrength getStrength();

	void tick();
}
