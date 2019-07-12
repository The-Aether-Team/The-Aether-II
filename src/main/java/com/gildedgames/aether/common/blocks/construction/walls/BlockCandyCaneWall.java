package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCandyCaneWall extends BlockCustomWall
{

	public BlockCandyCaneWall(BlockState state, float hardness, float resistance)
	{
		super(state, hardness, resistance);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		if (world.isRaining())
		{
			entity.motionX *= 0.1D;
			entity.motionZ *= 0.1D;
		}
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return Items.SUGAR;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return this.quantityDropped(random) + random.nextInt(fortune + 1);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 6 + random.nextInt(3);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		return true;
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}

	@Override
	public MapColor getMapColor(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return MapColor.getBlockColor(EnumDyeColor.RED);
	}
}
