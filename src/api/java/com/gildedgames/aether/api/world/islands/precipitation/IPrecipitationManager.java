package com.gildedgames.aether.api.world.islands.precipitation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import javax.vecmath.Vector2f;

public interface IPrecipitationManager extends INBTSerializable<NBTTagCompound>
{
	float getSkyDarkness();

	PrecipitationStrength getStrength();

	Vector2f getWindVector();

	void tick();
}
