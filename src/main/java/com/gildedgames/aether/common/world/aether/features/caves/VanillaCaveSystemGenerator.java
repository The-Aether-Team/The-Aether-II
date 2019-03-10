package com.gildedgames.aether.common.world.aether.features.caves;

import com.gildedgames.aether.api.world.generation.caves.CaveSystemNode;
import com.gildedgames.aether.api.world.generation.caves.CaveSystemTunnel;
import com.gildedgames.aether.api.world.generation.caves.ICaveSystemGenerator;
import com.gildedgames.orbis.lib.util.ChunkMap;
import com.gildedgames.orbis.lib.util.random.XoRoShiRoRandom;
import net.minecraft.util.math.MathHelper;

public class VanillaCaveSystemGenerator implements ICaveSystemGenerator
{
	private final ChunkMap<CaveSystemNode> cache = new ChunkMap<>();

	private final long seed;

	private final long s1, s2;

	public VanillaCaveSystemGenerator(long seed)
	{
		this.seed = seed;

		XoRoShiRoRandom r = new XoRoShiRoRandom(this.seed);

		this.s1 = r.nextLong();
		this.s2 = r.nextLong();
	}

	private void addRoom(CaveSystemNode node, XoRoShiRoRandom rand, long seed, double dirX, double dirY,
			double dirZ)
	{
		this.addTunnel(node, seed, dirX, dirY, dirZ, 1.0F + rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	/**
	 * Tunnel generation
	 *
	 * @param seed                   The seed of the randomizer
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
	 */
	private void addTunnel(CaveSystemNode segment, long seed, double posX, double posY, double posZ, float nodeSizeMultiplier, float angleBetweenNodes,
			float distBetweenNodes, int nodeIndex, int noOfNodes, double startingNodeHeightMult)
	{
		float nodeAngleMult = 0.0F;
		float nodeDistMult = 0.0F;

		boolean isRoom = false;

		XoRoShiRoRandom random = new XoRoShiRoRandom(seed);

		// Generates number of nodes the tunnel is going to have based on the chunkRange
		if (noOfNodes <= 0)
		{
			int i = this.getNeighborChunkSearchRadius() * 16 - 16;
			noOfNodes = i - random.nextInt(i / 4);
		}

		// Determines if the tunnel is actually going to be a room
		if (nodeIndex == -1)
		{
			nodeIndex = noOfNodes / 2;
			isRoom = true;
		}

		// Gets a random value between (noOfNodes/4, noOfNodes/4 + noOfNodes/2 - 1)
		int branchNodeIndex = random.nextInt(noOfNodes / 2) + noOfNodes / 4;

		// Generates a tunnel from nodeIndex to noOfNodes - 1
		for (boolean higherDist = random.nextInt(6) == 0; nodeIndex < noOfNodes; ++nodeIndex)
		{
			// How wide and tall the node will be
			double nodeWidthRadius = 1.5D + ((double) MathHelper.sin((float) nodeIndex * (float) Math.PI / (float) noOfNodes) * nodeSizeMultiplier);
			double nodeHeightRadius = nodeWidthRadius * startingNodeHeightMult;

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
			if (!isRoom && nodeIndex == branchNodeIndex && nodeSizeMultiplier > 1.0F && noOfNodes > 0)
			{
				this.addTunnel(segment.addBranch(), random.nextLong(), posX, posY, posZ,
						random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes - ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D);
				this.addTunnel(segment.addBranch(), random.nextLong(), posX, posY, posZ,
						random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes + ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D);
				return;
			}

			// 75% chance to generate a node normally or 100% chance if it's a room
			if (isRoom || random.nextInt(4) != 0)
			{
				segment.entries.add(new CaveSystemTunnel(posX, posY, posZ, isRoom, nodeWidthRadius, nodeHeightRadius, nodeSizeMultiplier, noOfNodes, nodeIndex));
			}
		}
	}

	@Override
	public CaveSystemNode getNode(int chunkX, int chunkZ)
	{
		CaveSystemNode root = this.cache.get(chunkX, chunkZ);

		if (root != null)
		{
			return root;
		}

		root = this.generateNode(chunkX, chunkZ);

		this.cache.put(chunkX, chunkZ, root);

		return root;
	}

	private CaveSystemNode generateNode(int chunkX, int chunkZ)
	{
		CaveSystemNode root = new CaveSystemNode();

		long j1 = (long) chunkX * this.s1;
		long k1 = (long) chunkZ * this.s2;

		XoRoShiRoRandom rand = new XoRoShiRoRandom(j1 ^ k1 ^ this.seed);

		int tunnelsPerChunk = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1);

		if (rand.nextInt(5) != 0)
		{
			tunnelsPerChunk = 0;
		}

		for (int j = 0; j < tunnelsPerChunk; ++j)
		{
			double x = (double) (chunkX * 16 + rand.nextInt(16));
			double y = (double) rand.nextInt(128);
			double z = (double) (chunkZ * 16 + rand.nextInt(16));

			int tunnels = 2;

			if (rand.nextInt(4) == 0)
			{
				this.addRoom(root.addBranch(), rand, rand.nextLong(), x, y, z);
				tunnels += rand.nextInt(4);
			}

			for (int l = 0; l < tunnels; ++l)
			{
				float nodeAngle = rand.nextFloat() * ((float) Math.PI * 2F);
				float nodeDist = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float nodeSize = rand.nextFloat() * 2.0F + rand.nextFloat();

				// 10% chance for a node to have massively increased size
				if (rand.nextInt(10) == 0)
				{
					nodeSize *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
				}

				this.addTunnel(root.addBranch(), rand.nextLong(), x, y, z, nodeSize * 2.0F, nodeAngle, nodeDist, 0, 0, 0.5D);
			}
		}

		return root;
	}

	@Override
	public int getNeighborChunkSearchRadius()
	{
		return 8;
	}
}
