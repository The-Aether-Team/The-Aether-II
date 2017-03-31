package com.gildedgames.aether.common.world.dungeon.instance;

import com.gildedgames.aether.api.capabilites.instances.IInstanceHandler;
import com.gildedgames.aether.api.util.WorldPos;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class DungeonInstanceHandler
{

	private IInstanceHandler<DungeonInstance> handler;

	public DungeonInstanceHandler(IInstanceHandler<DungeonInstance> handler)
	{
		this.handler = handler;
	}

	public DungeonInstance getFromDimId(int dimId)
	{
		return this.handler.getInstanceForDimension(dimId);
	}

	public DungeonInstance get(WorldPos entrance)
	{
		for (DungeonInstance inst : this.handler.getInstances())
		{
			if (inst.getOutsideEntrance().equals(entrance))
			{
				return inst;
			}
		}

		DungeonInstance inst = this.handler.createNew();
		inst.setOutsideEntrance(entrance);

		return inst;
	}

	public void teleportToInst(EntityPlayerMP player, DungeonInstance inst)
	{
		if (!inst.getGenerator().isLayoutReady())
		{
			inst.getGenerator().generateLayout(player.getServer(), inst.getLayoutSeed(), inst, inst.getRoomProvider());
		}

		World world = this.handler.teleportPlayerToDimension(inst, player);

		player.connection.setPlayerLocation(inst.getInsideEntrance().getX(), inst.getInsideEntrance().getY(), inst.getInsideEntrance().getZ(), 0, 0);
	}

	public void teleportBack(EntityPlayerMP player)
	{
		this.handler.teleportPlayerOutsideInstance(player);
	}
}
