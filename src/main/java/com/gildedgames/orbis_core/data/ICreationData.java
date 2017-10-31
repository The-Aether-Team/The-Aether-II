package com.gildedgames.orbis_core.data;

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
