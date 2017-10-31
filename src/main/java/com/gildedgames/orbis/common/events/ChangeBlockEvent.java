package com.gildedgames.orbis.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Fired whenever OrbisCore changes a block. This is NOT fired on base terrain generation.
 * @author Emile
 *
 */
public class ChangeBlockEvent extends Event
{
	public final World world;

	public final BlockPos pos;

	public final EntityPlayer changer;

	public ChangeBlockEvent(final World world, final BlockPos pos, final EntityPlayer changer)
	{
		super();
		this.world = world;
		this.pos = pos;
		this.changer = changer;
	}

}
