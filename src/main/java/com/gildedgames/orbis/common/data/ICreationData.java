package com.gildedgames.orbis.common.data;

import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Random;

public interface ICreationData
{

	World getWorld();

	Random getRandom();

	OrbisRotation getRotation();

	EntityPlayer getCreator();

}
