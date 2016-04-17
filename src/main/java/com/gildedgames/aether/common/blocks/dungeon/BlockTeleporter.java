package com.gildedgames.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.dungeon.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.DungeonInstanceHandler;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.instances.PlayerInstances;
import com.gildedgames.util.modules.world.common.BlockPosDimension;

public class BlockTeleporter extends Block
{

	public BlockTeleporter(Material materialIn)
	{
		super(materialIn);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			EntityPlayerMP player = (EntityPlayerMP) playerIn;
			
			DungeonInstanceHandler handler = AetherCore.locate().getDungeonInstanceHandler();
			DungeonInstance inst = handler.get(new BlockPosDimension(pos, world.provider.getDimensionId()));
			
			PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);
			
			if (hook.getInstance() != null)
			{
				handler.teleportBack(player);
			}
			else
			{
				handler.teleportToInst(player, inst);
			}
		}

		return true;
	}

}
