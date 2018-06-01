package com.gildedgames.aether.api.world.islands.precipitation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPrecipitationManager extends INBTSerializable<NBTTagCompound>
{
	void startPrecipitation(PrecipitationStrength strengthd);

	void endPrecipitation();

	float getStrength(float partialTicks);

	float getSkyDarkness();

	long getRemainingDuration();

	PrecipitationType getType();

	PrecipitationStrength getStrength();

	long getDuration();

	void tick();
}
