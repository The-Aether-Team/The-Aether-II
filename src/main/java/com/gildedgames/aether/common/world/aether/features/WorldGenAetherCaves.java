package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import com.gildedgames.orbis_api.util.XoShiRoRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class WorldGenAetherCaves
{
	protected final int chunkRange = 8;

	protected final ThreadLocal<XoShiRoRandom> rand = ThreadLocal.withInitial(XoShiRoRandom::new);

	protected void addRoom(long seed, int originalX, int originalY, int originalZ, ChunkSegmentMask mask, double dirX, double dirY, double dirZ, Biome[] biomes)
	{
		this.addTunnel(seed, originalX, originalY, originalZ, mask, dirX, dirY, dirZ, 1.0F + this.rand.get().nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, biomes);
	}

	/**
	 * Tunnel generation
	 *
	 * @param seed                   The seed of the randomizer
	 * @param centerChunkX           The original Chunk X position
	 * @param centerChunkZ           The original Chunk Z position
	 * @param mask                   mask of the land to carve out
	 * @param posX                   X block position for the starting node
	 * @param posY                   Y block position for the starting node
	 * @param posZ                   Z block position for the starting node
	 * @param nodeSizeMultiplier     Sets the size multiplier of each node
	 * @param angleBetweenNodes      Angle between this node and the next one on the horizontal plane;
	 *                               Multiply 2*pi with a value from 0 to 1 to get the precise value, since the algorithm uses a circle to determine the angle
	 * @param distBetweenNodes       The distance between each node, also used for random Y position of nodes;
	 *                               Takes a value between 0 and PI
	 * @param nodeIndex              The index node for a tunnel, used to keep track of node iterations to add branching tunnels; 0 is a good number for a starting index value
	 *                               Settings this to -1 will generate a value for you based on the noOfNodes value you put in or it generated
	 * @param noOfNodes              The number of nodes a tunnel will have starting from nodeIndex; Setting this to 0 will cause it to generate a value for you based on the chunkRange value
	 *                               A chunkRange value of 8 will result in a tunnel with (84, 112) nodes
	 * @param startingNodeHeightMult Sets the height of the elliptoid of the starting node of a tunnel, with 1.0D being a perfect sphere;
	 *                               Lower values flatten it out and larger make it taller
	 * @param biomes                 List of biomes which can be used for custom biome generation
	 */
	protected void addTunnel(long seed, int centerChunkX, int centerChunkY, int centerChunkZ, ChunkSegmentMask mask, double posX, double posY, double posZ, float nodeSizeMultiplier, float angleBetweenNodes, float distBetweenNodes, int nodeIndex, int noOfNodes, double startingNodeHeightMult, Biome[] biomes)
	{
		int chunkBlockCenterX = centerChunkX * 16 + 8;
		int chunkBlockCenterY = centerChunkY * 16 + 8;
		int chunkBlockCenterZ = centerChunkZ * 16 + 8;
		float nodeAngleMult = 0.0F;
		float nodeDistMult = 0.0F;
		boolean isRoom = false;
		XoShiRoRandom random = new XoShiRoRandom(seed);

		// Generates number of nodes the tunnel is going to have based on the chunkRange
		if (noOfNodes <= 0) {
			int i = this.chunkRange * 15;
			noOfNodes = i - random.nextInt(i / 4);
		}

		// Determines if the tunnel is actually going to be a room
		if (nodeIndex == -1) {
			nodeIndex = noOfNodes / 2;
			isRoom = true;
		}

		// Gets a random value between (noOfNodes/4, noOfNodes/4 + noOfNodes/2 - 1)
		int branchNodeIndex = random.nextInt(noOfNodes / 2) + noOfNodes / 4;
		//branchNodeIndex = nodeIndex + (noOfNodes - nodeIndex)/ 2; //-- Custom code to generate branches halfway of a tunnel/branch

		// Generates a tunnel from nodeIndex to noOfNodes - 1
		for (boolean higherDist = random.nextInt(6) == 0; nodeIndex < noOfNodes; ++nodeIndex)
		{
			double nodeWidthRadius = 1.5D + (double) (MathHelper.sin((float) nodeIndex * (float) Math.PI / (float) noOfNodes) * nodeSizeMultiplier); // How wide the node will be (it's more of a radius since nodes are spheres)
			double nodeHeightRadius = nodeWidthRadius * startingNodeHeightMult; // How tall a node is
			float nodeDistMultiplier = MathHelper.cos(distBetweenNodes); // A multiplier to determine how far apart each node will be from the other
			float nodeYIncrease = MathHelper.sin(distBetweenNodes); // By how much a node will move up

			// Determines the position of the next node of the tunnel
			posX += (double) (MathHelper.cos(angleBetweenNodes) * nodeDistMultiplier);
			posY += (double) nodeYIncrease;
			posZ += (double) (MathHelper.sin(angleBetweenNodes) * nodeDistMultiplier);


			// Changes distance between nodes based on higherDist for even more random generation (not sure why specifically those 2 values, though)
			distBetweenNodes *= (higherDist ? 0.92F : 0.7F);

			// Ensures that the next node is on a different position, so the tunnel isn't on the same Y level
			distBetweenNodes += nodeDistMult * 0.1F;
			angleBetweenNodes += nodeAngleMult * 0.1F;

			// Even more randomness introduced
			nodeDistMult = nodeDistMult * 0.9F;
			nodeAngleMult = nodeAngleMult * 0.75F;
			nodeDistMult = nodeDistMult + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			nodeAngleMult = nodeAngleMult + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			// Adding new branches starting at node index = branchNodeIndex
			if (!isRoom && nodeIndex == branchNodeIndex && nodeSizeMultiplier > 1.0F && noOfNodes > 0) {
				this.addTunnel(random.nextLong(), centerChunkX, centerChunkY, centerChunkZ, mask, posX, posY, posZ,
						random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes - ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D, biomes);
				this.addTunnel(random.nextLong(), centerChunkX, centerChunkY, centerChunkZ, mask, posX, posY, posZ,
						random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes + ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D, biomes);
				return;
			}

			// 75% chance to generate a node normally or 100% chance if it's a room
			if (isRoom || random.nextInt(4) != 0) {
				double d4 = posX - chunkBlockCenterX;
				double d5 = posZ - chunkBlockCenterZ;
				double nodesLeft = (double) (noOfNodes - nodeIndex);
				double d7 = (double) (nodeSizeMultiplier + 18.0F);
				if (d4 * d4 + d5 * d5 - nodesLeft * nodesLeft > d7 * d7) return;

				// Generates a tunnel only if the position of the node is with a 3x3 chunk area from the center chunk + the diameter of the node
				if (posX >= chunkBlockCenterX - 16.0D - nodeWidthRadius * 2.0D && posZ >= chunkBlockCenterZ - 16.0D - nodeWidthRadius * 2.0D && posY >= chunkBlockCenterY - 16.0D - nodeHeightRadius * 2.0D &&
					posX <= chunkBlockCenterX + 16.0D + nodeWidthRadius * 2.0D && posZ <= chunkBlockCenterZ + 16.0D + nodeWidthRadius * 2.0D && posY <= chunkBlockCenterY + 16.0D - nodeHeightRadius * 2.0D) {
					int minPosX = MathHelper.floor(posX - nodeWidthRadius) - centerChunkX * 16 - 1;
					int maxPosX = MathHelper.floor(posX + nodeWidthRadius) - centerChunkX * 16 + 1;
					int minPosY = MathHelper.floor(posY - nodeHeightRadius) - centerChunkY * 16 - 1;
					int maxPosY = MathHelper.floor(posY + nodeHeightRadius) - centerChunkY * 16 + 1;
					int minPosZ = MathHelper.floor(posZ - nodeWidthRadius) - centerChunkZ * 16 - 1;
					int maxPosZ = MathHelper.floor(posZ + nodeWidthRadius) - centerChunkZ * 16 + 1;

					// Makes sure the node stays within the 16x256x16 chunk area
					if (minPosX < 0) minPosX = 0;
					if (maxPosX > 16) maxPosX = 16;
					if (minPosY < 0) minPosY = 0;
					if (maxPosY > 16) maxPosY = 16;
					if (minPosZ < 0) minPosZ = 0;
					if (maxPosZ > 16) maxPosZ = 16;

					boolean doesNodeIntersectWater = false;

					// Goes through each block from maxPosY + 1 to minPosY - 1 to check if the block is a water tile, as long as doesNodeIntersectWater is false
					// Basically makes sure that the shell of the node doesn't intersect water and stops it from generating if it does
					for (int x = minPosX; !doesNodeIntersectWater && x < maxPosX; ++x) {
						for (int z = minPosZ; !doesNodeIntersectWater && z < maxPosZ; ++z) {
							for (int y = maxPosY - 1; !doesNodeIntersectWater && y >= minPosY; --y) {
								int block = mask.getBlock(x, y, z);
								if (this.isBlockX(mask, block, IslandBlockType.WATER_BLOCK)) doesNodeIntersectWater = true;
								// If the block is not part of the outer layer of the node (the furthest-most blocks of the node) set the y to minPosY
								// This makes it so it doesn't care if the interior has water in it, as long as none of the walls have water
								if (y != minPosY && x != minPosX && x != maxPosX - 1 && z != minPosZ && z != maxPosZ - 1) y = minPosY;
							}
						}
					}
					//System.out.println(centerChunkX + " " + centerChunkY + " " + centerChunkZ + " " + posX + " " + posY + " " + posZ);

					if (!doesNodeIntersectWater)
					{
						for (int x = minPosX; x < maxPosX; ++x) {
							// Gets the distance from the respective block position X to the center of the node and divides by the width radius
							double blockDistXMult = ((double) (x + centerChunkX * 16) + 0.5D - posX) / nodeWidthRadius;

							for (int z = minPosZ; z < maxPosZ; ++z) {
								// Gets the distance from the respective block position Z to the center of the node and divides by the width radius
								double blockDistZMult = ((double) (z + centerChunkZ * 16) + 0.5D - posZ) / nodeWidthRadius;

								// Makes it so it carves out a block from a cylinder rather than a cube (this is presumably to cut out on extra loops for blocks that shouldn't generate anyway)
								if (blockDistXMult * blockDistXMult + blockDistZMult * blockDistZMult < 1.0D) {
									for (int y = maxPosY - 1; y >= minPosY; --y) {
										// Gets the distance from the respective block position Y to the center of the node and divides by the height radius
										double blockDistYMult = ((double) (y + centerChunkY * 16) + 0.5D - posY) / nodeHeightRadius;

										// Checks to see if the block is inside the sphere (based on the Sphere's surface formula: x^2 + y^2 + z^2 = r2)
										if (blockDistXMult * blockDistXMult + blockDistYMult * blockDistYMult + blockDistZMult * blockDistZMult < 1.0D) {
											int block = mask.getBlock(x, y, z);
											this.digBlock(mask, x, y, z, block);
										}
									}
								}
							}
						}

						// If the tunnel is destined to be a room, it stops generation at 1 node
						if (isRoom) break;
					}
				}
			}
		}
	}

	public void generate(long seed, int centerChunkX, int centerChunkY, int centerChunkZ, ChunkSegmentMask mask, Biome[] biomes, IAetherChunkColumnInfo info)
	{
		XoShiRoRandom rand = this.rand.get();
		rand.setSeed(seed);
		int chunkHeight = info.getHeight(centerChunkX & 15, centerChunkZ & 15);
		if(chunkHeight <= 0 || centerChunkY <= 1 || (centerChunkY * 16 - chunkRange) > chunkHeight) return;

		// Generates tunnels in the current chunk and surrounding ones in chunkRange distance (which is 8, so it generates tunnels in a 16x16 chunks area)
		for (int chunkX = centerChunkX - chunkRange; chunkX <= centerChunkX + chunkRange; ++chunkX) {
			for (int chunkZ = centerChunkZ - chunkRange; chunkZ <= centerChunkZ + chunkRange; ++chunkZ) {
				for (int chunkY = centerChunkY - chunkRange; chunkY <= centerChunkY + chunkRange; ++chunkY) {
					long j1 = (long) chunkX * rand.nextLong();
					long k1 = (long) chunkZ * rand.nextLong();
					long l1 = (long) chunkY * rand.nextLong();
					long s = j1 ^ k1 ^ l1 ^ seed;
					rand.setSeed(s);
					this.recursiveGenerate(s, chunkX, chunkY, chunkZ, centerChunkX, centerChunkY, centerChunkZ, mask, biomes);
				}
			}
		}
	}

	/**
	 * Recursively called by generate()
	 */
	protected void recursiveGenerate(long seed, int chunkX, int chunkY, int chunkZ, int centerChunkX, int centerChunkY, int centerChunkZ, ChunkSegmentMask mask, Biome[] biomes)
	{
		Random rand = this.rand.get();
		rand.setSeed(seed);

		int tunnelsPerChunk = rand.nextInt(rand.nextInt(rand.nextInt(3) + 1) + 1);

		for (int j = 0; j < tunnelsPerChunk; ++j)
		{
			double x = (double) (chunkX * 16 + rand.nextInt(16));
			double y = (double) (chunkY * 16 + rand.nextInt(16));
			double z = (double) (chunkZ * 16 + rand.nextInt(16));

			int tunnels = 2;

			if (rand.nextInt(4) == 0) {
				this.addRoom(rand.nextLong(), centerChunkX, centerChunkY, centerChunkZ, mask, x, y, z, biomes);
				tunnels += rand.nextInt(4);
			}

			for (int l = 0; l < tunnels; ++l)
			{
				float nodeAngle = rand.nextFloat() * ((float) Math.PI * 2F);
				float nodeDist = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float nodeSize = rand.nextFloat() * 2.0F + rand.nextFloat();

				// 10% chance for a node to have massively increased size
				if (rand.nextInt(10) == 0) nodeSize *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;

				this.addTunnel(rand.nextLong(), centerChunkX, centerChunkY, centerChunkZ, mask, x, y, z, nodeSize * 2.0F, nodeAngle, nodeDist, 0, 80, 0.5D, biomes);
			}
		}
	}

	protected boolean canReplaceBlock(int state)
	{
		return state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.COAST_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal() || state == IslandBlockType.TOPSOIL_BLOCK.ordinal() || state == IslandBlockType.SOIL_BLOCK.ordinal();
	}

	protected boolean isBlockX(ChunkSegmentMask mask, int state, IslandBlockType blockType)
	{
		return state == blockType.ordinal();
	}

	private void digBlock(ChunkSegmentMask mask, int x, int y, int z, int state)
	{
		if (this.canReplaceBlock(state)) mask.setBlock(x, y, z, IslandBlockType.AIR_BLOCK.ordinal());
	}
}
