package com.gildedgames.aether.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.gen.structure.template.Template.transformedBlockPos;

public class TemplatePrimer
{

	private static List<Template.BlockInfo> getBlocks(Template template)
	{
		return ObfuscationReflectionHelper.getPrivateValue(Template.class, template, 0);
	}

	public static void primeChunk(Template template, World world, ChunkPos chunk, ChunkPrimer primer, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings)
	{
		List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			Block block = settings.getReplacedBlock();

			int minX = chunk.chunkXPos * 16;
			int minY = 0;
			int minZ = chunk.chunkZPos * 16;

			int maxX = minX + 15;
			int maxY = world.getActualHeight();
			int maxZ = minZ + 15;

			StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (Template.BlockInfo template$blockinfo : blocks)
			{
				BlockPos blockpos = transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				Template.BlockInfo template$blockinfo1 = processor != null ? processor.func_189943_a(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && chunkBB.isVecInside(blockpos))
					{
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						try
						{
							primer.setBlockState(blockpos.getX() - minX, blockpos.getY(), blockpos.getZ() - minZ, iblockstate1);
						}
						catch (ArrayIndexOutOfBoundsException ex)
						{
							System.out.println(blockpos.getX() - minX);
							System.out.println("z " + (blockpos.getZ() - minZ));
						}
					}
				}
			}
		}
	}

	public static void populateChunk(Template template, World world, ChunkPos chunk, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings, int placementFlags)
	{
		List<Template.BlockInfo> blocks = TemplatePrimer.getBlocks(template);

		if (!blocks.isEmpty() && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
		{
			Block block = settings.getReplacedBlock();

			int minX = chunk.chunkXPos * 16;
			int minY = 0;
			int minZ = chunk.chunkZPos * 16;

			int maxX = minX + 15;
			int maxY = world.getActualHeight();
			int maxZ = minZ + 15;

			StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

			for (Template.BlockInfo template$blockinfo : blocks)
			{
				BlockPos blockpos = transformedBlockPos(settings, template$blockinfo.pos).add(pos);
				Template.BlockInfo template$blockinfo1 = processor != null ? processor.func_189943_a(world, blockpos, template$blockinfo) : template$blockinfo;

				if (template$blockinfo1 != null)
				{
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && chunkBB.isVecInside(blockpos))
					{
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(settings.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(settings.getRotation());

						if (template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity = world.getTileEntity(blockpos);

							if (tileentity != null)
							{
								if (tileentity instanceof IInventory)
								{
									((IInventory)tileentity).clear();
								}
							}
						}

						if (template$blockinfo1.tileentityData != null)
						{
							TileEntity tileentity2 = world.getTileEntity(blockpos);

							if (tileentity2 != null)
							{
								template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
								template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
								template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
								tileentity2.readFromNBT(template$blockinfo1.tileentityData);
								tileentity2.func_189668_a(settings.getMirror());
								tileentity2.func_189667_a(settings.getRotation());
							}
						}
					}
				}
			}

			for (Template.BlockInfo template$blockinfo2 : blocks)
			{
				if (block == null || block != template$blockinfo2.blockState.getBlock())
				{
					BlockPos blockpos1 = transformedBlockPos(settings, template$blockinfo2.pos).add(pos);

					if (chunkBB == null || chunkBB.isVecInside(blockpos1))
					{
						world.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock());

						if (template$blockinfo2.tileentityData != null)
						{
							TileEntity tileentity1 = world.getTileEntity(blockpos1);

							if (tileentity1 != null)
							{
								tileentity1.markDirty();
							}
						}
					}
				}
			}
		}
	}

}
