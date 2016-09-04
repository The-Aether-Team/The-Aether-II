package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.items.ItemsAether;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BlockGoldenOakLog extends BlockAetherLog implements ISkyrootMinable
{
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.golden_amber, world.rand.nextInt(3) + 1));

		super.harvestBlock(world, player, pos, state, te, stack);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.skyroot_log);
	}

	@Override
	public boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state)
	{
		return true;
	}

	@Override
	public Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living)
	{
		List<ItemStack> drops = Lists.newArrayList();

		drops.add(new ItemStack(state.getBlock().getItemDropped(state, living.getRNG(), 0)));
		drops.add(new ItemStack(ItemsAether.golden_amber, world.rand.nextInt(3) + 1));

		return drops;
	}
}
