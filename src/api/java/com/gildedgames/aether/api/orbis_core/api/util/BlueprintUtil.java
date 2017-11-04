package com.gildedgames.aether.api.orbis_core.api.util;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.api.ICreationData;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class BlueprintUtil
{

	public static IRegion getRegionFromDefinition(final BlueprintData data, final ICreationData creationData)
	{
		final IRegion region =
				creationData.getRotation() == Rotation.NONE ? new Region(data) : RotationHelp.rotate(new Region(data), creationData.getRotation());

		final BlockPos pos = creationData.getPos();

		return (IRegion) region.translate(pos);
	}

	public static ChunkPos[] getChunksInsideTemplate(final BlueprintData data, final ICreationData loc)
	{
		final IRegion bb = BlueprintUtil.getRegionFromDefinition(data, loc);

		final int minX = Math.min(bb.getMin().getX(), bb.getMax().getX());
		final int minZ = Math.min(bb.getMin().getZ(), bb.getMax().getZ());

		final int maxX = Math.max(bb.getMin().getX(), bb.getMax().getX());
		final int maxZ = Math.max(bb.getMin().getZ(), bb.getMax().getZ());

		final int startChunkX = minX >> 4;
		final int startChunkY = minZ >> 4;

		final int endChunkX = maxX >> 4;
		final int endChunkY = maxZ >> 4;

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

}
