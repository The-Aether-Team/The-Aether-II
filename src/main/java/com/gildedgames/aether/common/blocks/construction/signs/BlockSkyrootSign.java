package com.gildedgames.aether.common.blocks.construction.signs;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSkyrootSign extends AbstractSignBlock implements IInternalBlock
{
	protected BlockSkyrootSign(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(1.0f).doesNotBlockMovement());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntitySkyrootSign();
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return ItemsAether.skyroot_sign;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			return tileentity instanceof TileEntitySkyrootSign && ((TileEntitySkyrootSign) tileentity).executeCommand(player);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(ItemsAether.skyroot_sign);
	}
}
