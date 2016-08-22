package com.gildedgames.aether.common.world.labyrinth.instance;

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
		if (!inst.hasGenerated())
		{
			/*GenUtil.cuboidVaried(world, new BlockPos(10, 10, 10), new BlockPos(20, 20, 20), BlocksAether.carved_stone.getDefaultState(), BlocksAether.sentry_stone.getDefaultState(), 8, world.rand, true);
			GenUtil.cuboid(world, new BlockPos(11, 11, 11), new BlockPos(19, 19, 19), Blocks.air.getDefaultState());
			
			world.setBlockState(new BlockPos(15, 11, 15), BlocksAether.labyrinth_totem.getDefaultState());
			
			inst.flagGenerated();*/

			inst.getGenerator().generateLayout(player.getServer(), FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(inst.getDimIdInside()).rand, inst, inst.getRoomProvider());
		}
		
		World world = this.handler.teleportPlayerToDimension(inst, player);

		player.connection.setPlayerLocation(inst.getInsideEntrance().getX(), inst.getInsideEntrance().getY(), inst.getInsideEntrance().getZ(), 0, 0);
	}

	public void teleportBack(EntityPlayerMP player)
	{
		this.handler.teleportPlayerOutsideInstance(player);
	}
}
