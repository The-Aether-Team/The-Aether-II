package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public interface ISkyrootMinable
{
	boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state);

	Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living);
}
