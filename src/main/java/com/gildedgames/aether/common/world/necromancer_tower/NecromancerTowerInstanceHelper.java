package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.orbis.lib.world.instances.IInstanceHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public class NecromancerTowerInstanceHelper
{
	public final IInstanceHandler<NecromancerTowerInstance> handler;

	public NecromancerTowerInstanceHelper(final IInstanceHandler<NecromancerTowerInstance> handler)
	{
		this.handler = handler;
	}

	public NecromancerTowerInstance getFromDimId(final int dimId)
	{
		return this.handler.getInstanceForDimension(dimId);
	}

	public void teleportToInst(final EntityPlayerMP player)
	{
		NecromancerTowerInstance inst = this.handler.createNew();

		if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(player, inst.getDimensionId()))
		{
			this.handler.unregisterInstance(inst);

			return;
		}

		this.handler.teleportPlayerToInstance(inst, player);
	}

	public void teleportBack(final EntityPlayerMP player)
	{
		this.handler.returnPlayerFromInstance(player);
	}
}
