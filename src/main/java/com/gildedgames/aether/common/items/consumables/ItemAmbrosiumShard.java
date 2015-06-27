package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemAmbrosiumShard extends Item
{
	public ItemAmbrosiumShard()
	{
		this.setCreativeTab(AetherCreativeTabs.tabMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == BlocksAether.aether_grass && state.getValue(BlockAetherGrass.PROPERTY_VARIANT) == BlockAetherGrass.AETHER_GRASS)
		{
			if (player.canPlayerEdit(pos, side, stack))
			{
				world.setBlockState(pos, BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED_AETHER_GRASS));

				if (!player.capabilities.isCreativeMode)
				{
					stack.stackSize -= 1;
				}

				return true;
			}
		}

		return false;
	}
}
