package com.gildedgames.aether.common.world.dungeon.instances;

import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.modules.world.common.BlockPosDimension;

public class DungeonInstanceHandler
{
	private InstanceHandler<DungeonInstance> handler;

	public DungeonInstanceHandler(InstanceHandler<DungeonInstance> handler)
	{
		this.handler = handler;
	}

	public DungeonInstance get(BlockPosDimension entrance)
	{
		for (DungeonInstance inst : this.handler.getInstances())
		{
			if (inst.getEntrance().equals(entrance))
			{
				return inst;
			}
		}
		
		DungeonInstance inst = this.handler.createNew();
		inst.setEntrance(entrance);
		
		return inst;
	}

	public void teleportToInst(EntityPlayerMP player, DungeonInstance inst)
	{
		this.handler.teleportPlayerToDimension(inst, player);
	}

	public void teleportBack(EntityPlayerMP player)
	{
		this.handler.teleportPlayerOutsideInstance(player);
	}
}