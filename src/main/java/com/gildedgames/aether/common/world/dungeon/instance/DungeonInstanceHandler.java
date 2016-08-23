package com.gildedgames.aether.common.world.dungeon.instance;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.core.util.BlockPosDimension;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DungeonInstanceHandler
{
	
	private InstanceHandler<DungeonInstance> handler;

	public DungeonInstanceHandler(InstanceHandler<DungeonInstance> handler)
	{
		this.handler = handler;
	}
	
	public DungeonInstance getFromDimId(int dimId)
	{
		return this.handler.getInstanceForDimension(dimId);
	}

	public DungeonInstance get(BlockPosDimension entrance)
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
