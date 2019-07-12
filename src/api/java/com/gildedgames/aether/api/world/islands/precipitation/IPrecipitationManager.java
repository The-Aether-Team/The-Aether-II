package com.gildedgames.aether.api.world.islands.precipitation;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.vecmath.Vector2f;

public interface IPrecipitationManager extends INBTSerializable<CompoundNBT>
{
	float getSkyDarkness();

	PrecipitationStrength getStrength();

	Vector2f getWindVector();

	void tick();
}
