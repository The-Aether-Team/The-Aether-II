package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis_api.world.instances.IInstanceHandler;
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

	public NecromancerTowerInstance get(PlayerAether playerAether)
	{
		if (playerAether.getNecromancerTowerInstance() != null)
		{
			return playerAether.getNecromancerTowerInstance();
		}

		final NecromancerTowerInstance inst = this.handler.createNew();

		playerAether.setNecromancerTowerInstance(inst);

		return inst;
	}

	public void teleportToInst(final EntityPlayerMP player, final NecromancerTowerInstance inst)
	{
		if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(player, inst.getDimensionId()))
		{
			return;
		}

		this.handler.teleportPlayerToInstance(inst, player);

		player.connection.setPlayerLocation(inst.getInsideEntrance().getX(), inst.getInsideEntrance().getY(), inst.getInsideEntrance().getZ(), 215, 0);
	}

	public void teleportBack(final EntityPlayerMP player)
	{
		this.handler.returnPlayerFromInstance(player);
	}
}
