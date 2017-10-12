package com.gildedgames.orbis.common.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

public interface CreationData
{

	World getWorld();

	Rotation getRotation();

	EntityPlayer getCreator();

}
