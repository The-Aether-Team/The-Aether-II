package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public class ItemSkyrootTool extends ItemAetherTool
{
	public ItemSkyrootTool(EnumToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 0);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack heldStack, World world, Block block, BlockPos pos, EntityLivingBase living)
	{
		if (!world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);

			if (!EnchantmentHelper.getSilkTouchModifier(living) && block instanceof ISkyrootMinable)
			{
				ISkyrootMinable doubleBlock = (ISkyrootMinable) block;

				if (block.isToolEffective(this.toolType.getToolClass(), state))
				{
					if (doubleBlock.canBlockDropDoubles(living, heldStack, state))
					{
						Collection<ItemStack> stacks = doubleBlock.getAdditionalDrops(world, pos, state, living);

						for (ItemStack stack : stacks)
						{
							Block.spawnAsEntity(world, pos, stack);
						}
					}
				}
			}
		}

		return super.onBlockDestroyed(heldStack, world, block, pos, living);
	}
}
