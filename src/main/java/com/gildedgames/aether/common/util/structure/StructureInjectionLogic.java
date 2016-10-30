package com.gildedgames.aether.common.util.structure;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import javax.annotation.Nullable;
import java.util.List;

public class StructureInjectionLogic
{

	public static boolean detectSize(TileEntityStructure te)
	{
		if (te.getMode() != TileEntityStructure.Mode.SAVE)
		{
			return false;
		}
		else
		{
			BlockPos blockpos = te.getPos();

			int i = 80;

			BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
			BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);

			List<TileEntityStructure> list = getNearbyCornerBlocks(te.getWorld(), blockpos1, blockpos2);
			List<TileEntityStructure> list1 = filterRelatedCornerBlocks(te, list);

			if (list1.size() < 1)
			{
				return false;
			}
			else
			{
				StructureBoundingBox structureboundingbox = calculateEnclosingBoundingBox(blockpos, list1);

				if (structureboundingbox.maxX - structureboundingbox.minX > 1 && structureboundingbox.maxY - structureboundingbox.minY > 1 && structureboundingbox.maxZ - structureboundingbox.minZ > 1)
				{
					te.setPosition(new BlockPos(structureboundingbox.minX - blockpos.getX() + 1, structureboundingbox.minY - blockpos.getY() + 1, structureboundingbox.minZ - blockpos.getZ() + 1));
					te.setSize(new BlockPos(structureboundingbox.maxX - structureboundingbox.minX - 1, structureboundingbox.maxY - structureboundingbox.minY - 1, structureboundingbox.maxZ - structureboundingbox.minZ - 1));
					te.markDirty();
					IBlockState iblockstate = te.getWorld().getBlockState(blockpos);
					te.getWorld().notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}

	private static List<TileEntityStructure> filterRelatedCornerBlocks(final TileEntityStructure te, List<TileEntityStructure> p_184415_1_)
	{
		Iterable<TileEntityStructure> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntityStructure>()
		{
			public boolean apply(@Nullable TileEntityStructure p_apply_1_)
			{
				return p_apply_1_.getMode() == TileEntityStructure.Mode.CORNER && te.getName().equals(p_apply_1_.getName());
			}
		});
		return Lists.newArrayList(iterable);
	}

	private static List<TileEntityStructure> getNearbyCornerBlocks(World world, BlockPos p_184418_1_, BlockPos p_184418_2_)
	{
		List<TileEntityStructure> list = Lists.newArrayList();

		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(p_184418_1_, p_184418_2_))
		{
			IBlockState iblockstate = world.getBlockState(blockpos$mutableblockpos);

			if (iblockstate.getBlock() == BlocksAether.structure_extended)
			{
				TileEntity tileentity = world.getTileEntity(blockpos$mutableblockpos);

				if (tileentity != null && tileentity instanceof TileEntityStructure)
				{
					list.add((TileEntityStructure)tileentity);
				}
			}
		}

		return list;
	}

	private static StructureBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<TileEntityStructure> p_184416_2_)
	{
		StructureBoundingBox structureboundingbox;

		if (p_184416_2_.size() > 1)
		{
			BlockPos blockpos = p_184416_2_.get(0).getPos();
			structureboundingbox = new StructureBoundingBox(blockpos, blockpos);
		}
		else
		{
			structureboundingbox = new StructureBoundingBox(p_184416_1_, p_184416_1_);
		}

		for (TileEntityStructure tileentitystructure : p_184416_2_)
		{
			BlockPos blockpos1 = tileentitystructure.getPos();

			if (blockpos1.getX() < structureboundingbox.minX)
			{
				structureboundingbox.minX = blockpos1.getX();
			}
			else if (blockpos1.getX() > structureboundingbox.maxX)
			{
				structureboundingbox.maxX = blockpos1.getX();
			}

			if (blockpos1.getY() < structureboundingbox.minY)
			{
				structureboundingbox.minY = blockpos1.getY();
			}
			else if (blockpos1.getY() > structureboundingbox.maxY)
			{
				structureboundingbox.maxY = blockpos1.getY();
			}

			if (blockpos1.getZ() < structureboundingbox.minZ)
			{
				structureboundingbox.minZ = blockpos1.getZ();
			}
			else if (blockpos1.getZ() > structureboundingbox.maxZ)
			{
				structureboundingbox.maxZ = blockpos1.getZ();
			}
		}

		return structureboundingbox;
	}

}
