package com.gildedgames.aether.api.orbis_core.api.util;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinition;
import com.gildedgames.aether.api.orbis_core.api.ICreationData;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Random;

public class BlueprintUtil
{

	public static IRegion getRegionFromDefinition(final BlueprintDefinition def, final ICreationData data)
	{
		final IRegion region = RotationHelp.rotate(new Region(def.getData()), data.getRotation());

		BlockPos pos = data.getPos();

		if (data.isCentered())
		{
			pos = BlueprintUtil.getCenteredPos(def, data);
		}

		return (IRegion) region.translate(pos);
	}

	public static BlockPos getCenteredPos(final BlueprintDefinition def, final ICreationData data)
	{
		BlockPos pos = data.getPos();

		final IRegion region = RotationHelp.rotate(new Region(def.getData()), data.getRotation());

		switch (data.getRotation())
		{
			case NONE:
			default:
				pos = pos.add(-(region.getWidth() / 2.0) + 1, 0, -(region.getLength() / 2.0) + 1);
				break;
			case CLOCKWISE_90:
				pos = pos.add(region.getWidth() / 2.0, 0, -(region.getLength() / 2.0) + 1);
				break;
			case COUNTERCLOCKWISE_90:
				pos = pos.add(-(region.getWidth() / 2.0) + 1, 0, (region.getLength() / 2.0));
				break;
			case CLOCKWISE_180:
				pos = pos.add((region.getWidth() / 2.0), 0, (region.getLength() / 2.0));
				break;
		}

		if (def.getOffset() != null)
		{
			pos = pos.add(def.getOffset().process(data.getRotation()));
		}

		return pos;
	}

	public static ChunkPos[] getChunksInsideTemplate(final BlueprintDefinition template, final ICreationData loc)
	{
		final IRegion bb = BlueprintUtil.getRegionFromDefinition(template, loc);

		final int startChunkX = bb.getMin().getX() >> 4;
		final int startChunkY = bb.getMax().getZ() >> 4;

		final int endChunkX = bb.getMax().getX() >> 4;
		final int endChunkY = bb.getMax().getZ() >> 4;

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

		return material == Material.AIR || material == Material.LEAVES || material == Material.PLANTS;
	}

	public static boolean isReplaceable(final IBlockAccessExtended world, final BlockPos pos)
	{
		final IBlockState state = world.getBlockState(pos);

		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos)
				|| BlueprintUtil.canGrowInto(state.getBlock());
	}

	public static <T> T pickRandom(final Random rand, final T... objects)
	{
		return objects[rand.nextInt(objects.length)];
	}

}
