package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.blocks.util.BlockWithDoubleDrops;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemSkyrootTool extends ItemAetherTool
{
	public ItemSkyrootTool(EnumToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 0);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase player)
	{
		if (!world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);

			if (state.getBlock().isToolEffective(this.toolType.getToolClass(), state))
			{
				if (state.getBlock() instanceof BlockWithDoubleDrops)
				{
					BlockWithDoubleDrops doubleDropBlock = (BlockWithDoubleDrops) state.getBlock();

					if (doubleDropBlock.canBeDoubleDropped(state))
					{
						doubleDropBlock.dropBlockAsItem(world, pos, state, 0);
					}
				}
			}
		}

		return super.onBlockDestroyed(stack, world, block, pos, player);
	}
	// TODO: Double drops.
}
