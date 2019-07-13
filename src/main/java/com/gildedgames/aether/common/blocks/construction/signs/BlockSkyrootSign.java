package com.gildedgames.aether.common.blocks.construction.signs;

import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockSkyrootSign extends AbstractSignBlock
{
	public BlockSkyrootSign(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(1.0f).doesNotBlockMovement());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntitySkyrootSign();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (world.isRemote())
		{
			return true;
		}

		TileEntity te = world.getTileEntity(pos);

		return te instanceof TileEntitySkyrootSign && ((TileEntitySkyrootSign) te).executeCommand(player);
	}
}
