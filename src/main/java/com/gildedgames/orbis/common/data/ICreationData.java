package com.gildedgames.orbis.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

import java.util.Random;

public interface ICreationData
{

	World getWorld();

	Random getRandom();

	Rotation getRotation();

	EntityPlayer getCreator();

}
