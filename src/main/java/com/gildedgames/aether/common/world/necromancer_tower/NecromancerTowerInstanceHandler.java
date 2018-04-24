package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.world.instances.IInstanceHandler;
import com.gildedgames.orbis_api.world.instances.IPlayerInstances;
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

		final World world = this.handler.teleportPlayerToDimension(inst, player);

		player.connection.setPlayerLocation(inst.getInsideEntrance().getX(), inst.getInsideEntrance().getY(), inst.getInsideEntrance().getZ(), 215, 0);

		inst.onJoin(player);
	}

	public void teleportBack(final EntityPlayerMP player)
	{
		final IPlayerInstances hook = OrbisAPI.instances().getPlayer(player);

		if (hook.getInstance() != null)
		{
			hook.getInstance().onLeave(player);
		}

		this.handler.teleportPlayerOutsideInstance(player);
	}
}
