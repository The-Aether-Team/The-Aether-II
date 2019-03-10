package com.gildedgames.aether.api.util;

import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class TemplateUtil
{

	public static StructureBoundingBox getBoundingBoxFromTemplate(final TemplateDefinition def, final TemplateLoc loc)
	{
		final Rotation rotation = loc.getSettings().getRotation();
		final BlockPos size = def.getTemplate().transformedSize(rotation);

		final StructureBoundingBox bb = new StructureBoundingBox(0, 0, 0, size.getX(), size.getY() - 1, size.getZ());

		switch (rotation)
		{
			case NONE:
			default:
				break;
			case CLOCKWISE_90:
				bb.offset(-size.getX(), 0, 0);
				break;
			case COUNTERCLOCKWISE_90:
				bb.offset(0, 0, -size.getZ());
				break;
			case CLOCKWISE_180:
				bb.offset(-size.getX(), 0, -size.getZ());
		}

		BlockPos pos = loc.getPos();

		if (loc.isCentered())
		{
			pos = TemplateUtil.getCenteredPos(def, loc);
		}

		bb.offset(pos.getX(), pos.getY(), pos.getZ());

		return bb;
	}

	public static BlockPos getCenteredPos(final TemplateDefinition def, final TemplateLoc info)
	{
		BlockPos pos = info.getPos();

		final BlockPos size = def.getTemplate().transformedSize(info.getSettings().getRotation());

		switch (info.getSettings().getRotation())
		{
			case NONE:
			default:
				pos = pos.add(-(size.getX() / 2.0) + 1, 0, -(size.getZ() / 2.0) + 1);
				break;
			case CLOCKWISE_90:
				pos = pos.add(size.getX() / 2.0, 0, -(size.getZ() / 2.0) + 1);
				break;
			case COUNTERCLOCKWISE_90:
				pos = pos.add(-(size.getX() / 2.0) + 1, 0, (size.getZ() / 2.0));
				break;
			case CLOCKWISE_180:
				pos = pos.add((size.getX() / 2.0), 0, (size.getZ() / 2.0));
				break;
		}

		if (def.getOffset() != null)
		{
			pos = pos.add(def.getOffset().process(info.getSettings().getRotation()));
		}

		return pos;
	}

	public static ChunkPos[] getChunksInsideTemplate(final TemplateDefinition template, final TemplateLoc loc)
	{
		final StructureBoundingBox bb = TemplateUtil.getBoundingBoxFromTemplate(template, loc);

		final int startChunkX = bb.minX >> 4;
		final int startChunkY = bb.minZ >> 4;

		final int endChunkX = bb.maxX >> 4;
		final int endChunkY = bb.maxZ >> 4;

		final int width = endChunkX - startChunkX + 1;
		final int length = endChunkY - startChunkY + 1;

		final ChunkPos[] chunks = new ChunkPos[width * length];

		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < length; y++)
			{
				chunks[x + (y * width)] = new ChunkPos(startChunkX + x, startChunkY + y);
			}
		}

		return chunks;
	}

	public static boolean canGrowInto(final Block block)
	{
		final Material material = block.getDefaultState().getMaterial();

		return material == Material.AIR || material == Material.LEAVES || material == Material.PLANTS || material == Material.SNOW;
	}

	public static boolean isReplaceable(final IBlockAccessExtended world, final BlockPos pos)
	{
		final IBlockState state = world.getBlockState(pos);

		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos)
				|| TemplateUtil.canGrowInto(state.getBlock());
	}

	@SafeVarargs
	public static <T> T pickRandom(final Random rand, final T... objects)
	{
		return objects[rand.nextInt(objects.length)];
	}

}
