package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public interface ICreationData extends NBT
{

	ICreationData set(BlockPos pos);

	ICreationData set(World world);

	ICreationData set(Rotation rotation);

	ICreationData set(Random random);

	ICreationData set(EntityPlayer creator);

	ICreationData set(boolean placeAir);

	/**
	 * Should return the centered position if
	 * this creation data returns true on isCentered()
	 * @return The position we're creating at.
	 */
	BlockPos getPos();

	World getWorld();

	Random getRandom();

	Rotation getRotation();

	@Nullable
	EntityPlayer getCreator();

	boolean placeAir();

	ICreationData clone();

}
