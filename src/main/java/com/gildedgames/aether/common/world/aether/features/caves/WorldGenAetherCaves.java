package com.gildedgames.aether.common.world.aether.features.caves;

import com.gildedgames.aether.api.world.generation.caves.CaveSystemNode;
import com.gildedgames.aether.api.world.generation.caves.CaveSystemTunnel;
import com.gildedgames.aether.api.world.generation.caves.ICaveSystemGenerator;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis.lib.preparation.impl.ChunkMask;
import com.gildedgames.orbis.lib.preparation.impl.ChunkMaskSegment;
import net.minecraft.util.math.MathHelper;

public class WorldGenAetherCaves
{
	private void tryCarveTunnel(ChunkMask mask, CaveSystemNode node, double chunkBlockCenterX, double chunkBlockCenterZ, int centerChunkX, int centerChunkZ)
	{
		double chunkMaxY = (mask.getMaxYSegment() * 8) + 8;
		double chunkMinY = (mask.getMinYSegment() * 8);

		for (CaveSystemTunnel segment : node.entries)
		{
			double d4 = segment.posX - chunkBlockCenterX;
			double d5 = segment.posZ - chunkBlockCenterZ;

			double nodesLeft = (double) segment.nodesLeft;

			double d7 = (segment.nodeSizeMultiplier + 18.0F);

			if (d4 * d4 + d5 * d5 - nodesLeft * nodesLeft > d7 * d7)
			{
				return;
			}

			if (segment.posY - segment.nodeHeightRadius - 1 > chunkMaxY)
			{
				continue;
			}

			if (segment.posY + segment.nodeHeightRadius + 1 < chunkMinY)
			{
				continue;
			}

			// Generates a tunnel only if the position of the node is with a 3x3 chunk area from the center chunk + the diameter of the node
			if (segment.posX >= chunkBlockCenterX - 16.0D - segment.nodeWidthRadius * 2.0D
					&& segment.posZ >= chunkBlockCenterZ - 16.0D - segment.nodeWidthRadius * 2.0D
					&& segment.posX <= chunkBlockCenterX + 16.0D + segment.nodeWidthRadius * 2.0D
					&& segment.posZ <= chunkBlockCenterZ + 16.0D + segment.nodeWidthRadius * 2.0D)
			{
				int minPosX = MathHelper.floor(segment.posX - segment.nodeWidthRadius) - centerChunkX * 16 - 1;
				int maxPosX = MathHelper.floor(segment.posX + segment.nodeWidthRadius) - centerChunkX * 16 + 1;
				int minPosY = MathHelper.floor(segment.posY - segment.nodeHeightRadius) - 1;
				int maxPosY = MathHelper.floor(segment.posY + segment.nodeHeightRadius) + 1;
				int minPosZ = MathHelper.floor(segment.posZ - segment.nodeWidthRadius) - centerChunkZ * 16 - 1;
				int maxPosZ = MathHelper.floor(segment.posZ + segment.nodeWidthRadius) - centerChunkZ * 16 + 1;

				// Makes sure the node stays within the 16x256x16 chunk area
				if (minPosX < 0)
				{
					minPosX = 0;
				}

				if (maxPosX > 16)
				{
					maxPosX = 16;
				}

				if (minPosY < 1)
				{
					minPosY = 1;
				}

				if (maxPosY > 248)
				{
					maxPosY = 248;
				}

				if (minPosZ < 0)
				{
					minPosZ = 0;
				}

				if (maxPosZ > 16)
				{
					maxPosZ = 16;
				}

				if (this.carveTunnel(mask, centerChunkX, centerChunkZ, segment, minPosX, maxPosX, minPosY, maxPosY, minPosZ, maxPosZ))
				{
					return;
				}
			}
		}

		for (CaveSystemNode branch : node.branches)
		{
			this.tryCarveTunnel(mask, branch, chunkBlockCenterX, chunkBlockCenterZ, centerChunkX, centerChunkZ);
		}
	}

	private boolean carveTunnel(ChunkMask mask, int centerChunkX, int centerChunkZ, CaveSystemTunnel segment, int minPosX, int maxPosX, int minPosY,
			int maxPosY, int minPosZ, int maxPosZ)
	{
		boolean doesNodeIntersectWater = false;

		outerLoop:

		// Goes through each block from maxPosY + 1 to minPosY - 1 to check if the block is a water tile, as long as doesNodeIntersectWater is false
		// Basically makes sure that the shell of the node doesn't intersect water and stops it from generating if it does
		for (int x = minPosX; x < maxPosX; ++x)
		{
			boolean a = x != minPosX && x != maxPosX - 1;

			for (int z = minPosZ; z < maxPosZ; ++z)
			{
				boolean b = z != minPosZ && z != maxPosZ - 1;

				for (int y = maxPosY + 1; y >= minPosY - 1; --y)
				{
					// If the block is not part of the outer layer of the node (the furthest-most blocks of the node) set the y to minPosY
					// This makes it so it doesn't care if the interior has water in it, as long as none of the walls have water
					if (y != minPosY - 1 && a && b)
					{
						y = minPosY;
					}

					int block = mask.getBlock(x, y, z);

					if (block == IslandBlockType.WATER_BLOCK.ordinal() || block == IslandBlockType.COAST_BLOCK.ordinal())
					{
						doesNodeIntersectWater = true;

						break outerLoop;
					}
				}
			}
		}

		if (!doesNodeIntersectWater)
		{
			double wStep = 1.0 / segment.nodeWidthRadius;
			double hStep = 1.0 / segment.nodeHeightRadius;

			double distX = (minPosX + (centerChunkX * 16) + 0.5D - segment.posX) * wStep;

			for (int x = minPosX; x < maxPosX; ++x)
			{
				double distXSq = distX * distX;

				if (distXSq < 1.0D)
				{
					double distZ = (minPosZ + (centerChunkZ * 16) + 0.5D - segment.posZ) * wStep;

					for (int z = minPosZ; z < maxPosZ; ++z)
					{
						double distZSq = distZ * distZ;

						// Makes it so it carves out a block from a cylinder rather than a cube (this is presumably to cut out on extra loops for blocks that shouldn't generate anyway)
						if (distXSq + distZSq < 1.0D)
						{
							double distY = (maxPosY - 0.5D - segment.posY) * hStep;

							for (int y = maxPosY; y > minPosY; --y)
							{
								double distYSq = distY * distY;

								// Checks to see if the block is inside the sphere (based on the Sphere's surface formula: x^2 + y^2 + z^2 = r2)
								if (distYSq + distXSq + distZSq < 1.0D)
								{
									this.digBlock(mask, x, y, z);
								}

								distY -= hStep;
							}
						}

						distZ += wStep;
					}
				}

				distX += wStep;
			}

			// If the tunnel is destined to be a room, it stops generation at 1 node
			return segment.isRoom;
		}

		return false;
	}

	private void digBlock(ChunkMask mask, int x, int y, int z)
	{
		ChunkMaskSegment segment = mask.getSegment(y >> 3);

		if (segment != null)
		{
			int block = segment.getBlock(x, y & 7, z);

			if (block == IslandBlockType.STONE_BLOCK.ordinal() || block == IslandBlockType.STONE_MOSSY_BLOCK.ordinal())
			{
				segment.setBlock(x, y & 7, z, IslandBlockType.AIR_BLOCK.ordinal());
			}
		}

//		mask.setBlock(x, y, z, IslandBlockType.FERROSITE_BLOCK.ordinal());
	}

	public void generate(ICaveSystemGenerator generator, int centerChunkX, int centerChunkZ, ChunkMask mask)
	{
		int i = generator.getNeighborChunkSearchRadius();

		// Generates tunnels in the current chunk and surrounding ones in chunkRange distance (which is 8, so it generates tunnels in a 16x16 chunks area)
		for (int chunkX = centerChunkX - i; chunkX <= centerChunkX + i; ++chunkX)
		{
			for (int chunkZ = centerChunkZ - i; chunkZ <= centerChunkZ + i; ++chunkZ)
			{
				CaveSystemNode tree = generator.getNode(chunkX, chunkZ);

				this.tryCarveTunnel(mask, tree, (centerChunkX * 16) + 8, (centerChunkZ * 16) + 8, centerChunkX, centerChunkZ);
			}
		}
	}

}
