package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockTallAetherGrass extends BlockBush implements IShearable
{
	public BlockTallAetherGrass()
	{
		super(Material.plants);

		this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);

		this.setStepSound(Block.soundTypeGrass);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		List<ItemStack> drops = new java.util.ArrayList<ItemStack>();
		drops.add(item);

		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return Block.EnumOffsetType.XYZ;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return this.canPlaceBlockOn(world.getBlockState(pos.down()).getBlock());
	}

	@Override
	public boolean canPlaceBlockOn(Block ground)
	{
		return ground == BlocksAether.aether_grass || ground == this;
	}

	@Override
	public boolean isReplaceable(World worldIn, BlockPos pos)
	{
		return true;
	}
}
