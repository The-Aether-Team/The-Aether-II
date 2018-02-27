package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.api.world.instances.IInstanceHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class NecromancerTowerInstanceHandler
{

	private final IInstanceHandler<NecromancerTowerInstance> handler;

	public NecromancerTowerInstanceHandler(final IInstanceHandler<NecromancerTowerInstance> handler)
	{
		this.handler = handler;
	}

	public NecromancerTowerInstance getFromDimId(final int dimId)
	{
		return this.handler.getInstanceForDimension(dimId);
	}

	public NecromancerTowerInstance get(final BlockPosDimension entrance)
	{
		for (final NecromancerTowerInstance inst : this.handler.getInstances())
		{
			if (inst.getOutsideEntrance().equals(entrance))
			{
				return inst;
			}
		}

		final NecromancerTowerInstance inst = this.handler.createNew();
		inst.setOutsideEntrance(entrance);

		return inst;
	}

	public void teleportToInst(final EntityPlayerMP player, final NecromancerTowerInstance inst)
	{
		final World world = this.handler.teleportPlayerToDimension(inst, player);

		player.connection.setPlayerLocation(inst.getInsideEntrance().getX(), inst.getInsideEntrance().getY(), inst.getInsideEntrance().getZ(), 0, 0);
	}

	public void teleportBack(final EntityPlayerMP player)
	{
		this.handler.teleportPlayerOutsideInstance(player);
	}
}
