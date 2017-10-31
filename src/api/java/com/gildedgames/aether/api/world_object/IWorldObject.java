package com.gildedgames.aether.api.world_object;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis_core.data.management.IData;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The most basic object the world can hold
 * Each object has a position and a renderer
 */
public interface IWorldObject extends NBT
{

	World getWorld();

	BlockPos getPos();

	void setPos(BlockPos pos);

	@SideOnly(Side.CLIENT)
	IWorldRenderer getRenderer();

	IData getData();

}
