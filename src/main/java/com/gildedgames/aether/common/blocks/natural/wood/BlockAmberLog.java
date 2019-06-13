package com.gildedgames.aether.common.blocks.natural.wood;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAmberLog extends BlockAetherLog
{
	private static final Random RANDOM = new Random();

	public BlockAmberLog(AetherWoodType type)
	{
		super(type);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> list, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		super.getDrops(list, world, pos, state, fortune);

		// Why, Minecraft...
		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		list.add(new ItemStack(ItemsAether.golden_amber, rand.nextInt(3) + 1));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.skyroot_log);
	}
}
