package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemGravititeTool extends ItemAetherTool
{
	public ItemGravititeTool(EnumToolType toolType)
	{
		super(ToolMaterial.EMERALD, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 3);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);

			if (ForgeHooks.isToolEffective(world, pos, stack))
			{
				if (world.isAirBlock(pos.up()))
				{
					world.setBlockToAir(pos);

					EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state);
					world.spawnEntityInWorld(floatingBlock);

					stack.damageItem(5, player);
				}

				return true;
			}
		}

		return false;
	}
}
