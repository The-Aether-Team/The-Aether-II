package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.util.XoShiRoRandom;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class WorldGenAetherCaves {
    protected final int chunkRange = 8;

    protected final ThreadLocal<XoShiRoRandom> rand = ThreadLocal.withInitial(XoShiRoRandom::new);

    protected void addRoom(long seed, int originalX, int originalZ, ChunkMask mask, double dirX, double dirY, double dirZ, Biome[] biomes) {
        this.addTunnel(seed, originalX, originalZ, mask, dirX, dirY, dirZ, 1.0F + this.rand.get().nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, biomes);
    }

    /**
     * Tunnel generation
     *
     * @param seed                   The seed of the randomizer
     * @param chunkX                 Chunk position X
     * @param chunkZ                 Chunk position Z
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
    protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkMask mask, double posX, double posY, double posZ, float nodeSizeMultiplier, float angleBetweenNodes, float distBetweenNodes, int nodeIndex, int noOfNodes, double startingNodeHeightMult, Biome[] biomes) {
        double chunkBlockCenterX = (double) (chunkX * 16 + 8);
        double chunkBlockCenterZ = (double) (chunkZ * 16 + 8);
        float nodeAngleMult = 0.0F;
        float nodeDistMult = 0.0F;
        XoShiRoRandom random = new XoShiRoRandom(seed);

        // Generates number of nodes the tunnel is going to have based on the chunkRange
        if (noOfNodes <= 0) {
            int i = this.chunkRange * 16 - 16;
            noOfNodes = i - random.nextInt(i / 4);
        }

        boolean isRoom = false;

        // Determines if the tunnel is actually going to be a room
        if (nodeIndex == -1) {
            nodeIndex = noOfNodes / 2;
            isRoom = true;
        }

        // Gets a random value between (noOfNodes/4, noOfNodes/4 + noOfNodes/2 - 1)
        int branchNodeIndex = random.nextInt(noOfNodes / 2) + noOfNodes / 4;
        // branchNodeIndex = nodeIndex + (noOfNodes - nodeIndex)/ 2; -- Custom code to generate branches every half of a tunnel and branch

        for (boolean higherDist = random.nextInt(6) == 0; nodeIndex < noOfNodes; ++nodeIndex) {
            double nodeHeight = 1.5D + (double) (MathHelper.sin((float) nodeIndex * (float) Math.PI / (float) noOfNodes) * nodeSizeMultiplier);
            double d3 = nodeHeight * startingNodeHeightMult;
            float f2 = MathHelper.cos(distBetweenNodes);
            float f3 = MathHelper.sin(distBetweenNodes);

            // Determines the position of the next node of the tunnel
            posX += (double) (MathHelper.cos(angleBetweenNodes) * f2);
            posY += (double) f3;
            posZ += (double) (MathHelper.sin(angleBetweenNodes) * f2);

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
                this.addTunnel(random.nextLong(), chunkX, chunkZ, mask, posX, posY, posZ,
                        random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes - ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D, biomes);
                this.addTunnel(random.nextLong(), chunkX, chunkZ, mask, posX, posY, posZ,
                        random.nextFloat() * 0.5F + 0.5F, angleBetweenNodes + ((float) Math.PI / 2F), distBetweenNodes / 3.0F, nodeIndex, noOfNodes, 1.0D, biomes);
                return;
            }

            if (isRoom || random.nextInt(4) != 0) {
                double d4 = posX - chunkBlockCenterX;
                double d5 = posZ - chunkBlockCenterZ;
                double nodesLeft = (double) (noOfNodes - nodeIndex);
                double d7 = (double) (nodeSizeMultiplier + 18.0F);
                //System.out.format("posX: %f; chunkBlockCenterX: %f; posZ: %f; chunkBlockCenterZ: %f; nodesLeft: %f; d7: %f\n", posX, chunkBlockCenterX, posZ, chunkBlockCenterZ, nodesLeft, d7);

                if (d4 * d4 + d5 * d5 - nodesLeft * nodesLeft > d7 * d7) {
                    return;
                }

                if (posX >= chunkBlockCenterX - 16.0D - nodeHeight * 2.0D && posZ >= chunkBlockCenterZ - 16.0D - nodeHeight * 2.0D && posX <= chunkBlockCenterX + 16.0D + nodeHeight * 2.0D
                        && posZ <= chunkBlockCenterZ + 16.0D + nodeHeight * 2.0D) {
                    int k2 = MathHelper.floor(posX - nodeHeight) - chunkX * 16 - 1;
                    int k = MathHelper.floor(posX + nodeHeight) - chunkX * 16 + 1;
                    int l2 = MathHelper.floor(posY - d3) - 1;
                    int l = MathHelper.floor(posY + d3) + 1;
                    int i3 = MathHelper.floor(posZ - nodeHeight) - chunkZ * 16 - 1;
                    int i1 = MathHelper.floor(posZ + nodeHeight) - chunkZ * 16 + 1;

                    if (k2 < 0) {
                        k2 = 0;
                    }

                    if (k > 16) {
                        k = 16;
                    }

                    if (l2 < 1) {
                        l2 = 1;
                    }

                    if (l > 248) {
                        l = 248;
                    }

                    if (i3 < 0) {
                        i3 = 0;
                    }

                    if (i1 > 16) {
                        i1 = 16;
                    }

                    boolean isBlockWater = false;

                    for (int j1 = k2; !isBlockWater && j1 < k; ++j1) {
                        for (int k1 = i3; !isBlockWater && k1 < i1; ++k1) {
                            for (int l1 = l + 1; !isBlockWater && l1 >= l2 - 1; --l1) {
                                if (l1 >= 0 && l1 < 256) {
                                    if (this.isOceanBlock(mask, j1, l1, k1, chunkX, chunkZ)) {
                                        isBlockWater = true;
                                    }

                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1) {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    if (!isBlockWater) {
                        for (int j3 = k2; j3 < k; ++j3) {
                            double d10 = ((double) (j3 + chunkX * 16) + 0.5D - posX) / nodeHeight;

                            for (int i2 = i3; i2 < i1; ++i2) {
                                double d8 = ((double) (i2 + chunkZ * 16) + 0.5D - posZ) / nodeHeight;
                                boolean foundTop = false;

                                if (d10 * d10 + d8 * d8 < 1.0D) {
                                    for (int j2 = l; j2 > l2; --j2) {
                                        double d9 = ((double) (j2 - 1) + 0.5D - posY) / d3;

                                        if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
                                            int block = mask.getBlock(j3, j2, i2);
                                            int topBlock = mask.getBlock(j3, j2 + 1, i2);

                                            if (this.isTopBlock(mask, biomes, j3, j2, i2, chunkX, chunkZ)) {
                                                foundTop = true;
                                            }
                                            this.digBlock(mask, j3, j2, i2, chunkX, chunkZ, foundTop, block, topBlock);
                                        }
                                    }
                                }
                            }
                        }

                        if (isRoom) {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected boolean canReplaceBlock(int state, int above) {
        return state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.COAST_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK
                .ordinal();
    }

    protected boolean isOceanBlock(ChunkMask data, int x, int y, int z, int chunkX, int chunkZ) {
        return data.getBlock(x, y, z) == IslandBlockType.WATER_BLOCK.ordinal();
    }

    public void generate(World worldIn, int x, int z, ChunkMask mask) {
        Biome[] biomes = worldIn.provider.getBiomeProvider().getBiomes(null, x, z, 16, 16, true);

        XoShiRoRandom rand = this.rand.get();
        rand.setSeed(worldIn.getSeed());

        int i = this.chunkRange;

        long j = rand.nextLong();
        long k = rand.nextLong();

        for (int chunkX = x - i; chunkX <= x + i; ++chunkX) {
            for (int chunkZ = z - i; chunkZ <= z + i; ++chunkZ) {
                long j1 = (long) chunkX * j;
                long k1 = (long) chunkZ * k;

                rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());

                this.recursiveGenerate(worldIn, chunkX, chunkZ, x, z, mask, biomes);
            }
        }
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World world, int chunkX, int chunkZ, int originalX, int originalZ, ChunkMask mask, Biome[] biomes) {
        Random rand = this.rand.get();

        int tunnelsPerChunk = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1);

        if (rand.nextInt(5) != 0) {
            tunnelsPerChunk = 0;
        }

        for (int j = 0; j < tunnelsPerChunk; ++j) {
            double x = (double) (chunkX * 16 + rand.nextInt(16));
            double y = (double) rand.nextInt(128);
            double z = (double) (chunkZ * 16 + rand.nextInt(16));

            int tunnels = 2;

            if (rand.nextInt(4) == 0) {
                this.addRoom(rand.nextLong(), originalX, originalZ, mask, x, y, z, biomes);
                tunnels += rand.nextInt(4);
            }

            for (int l = 0; l < tunnels; ++l) {
                float nodeAngle = rand.nextFloat() * ((float) Math.PI * 2F);
                float nodeDist = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float nodeSize = rand.nextFloat() * 2.0F + rand.nextFloat();

                if (rand.nextInt(10) == 0) {
                    nodeSize *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
                }

                this.addTunnel(rand.nextLong(), originalX, originalZ, mask, x, y, z, nodeSize * 2.0F, nodeAngle, nodeDist, 0, 0, 0.5D, biomes);
            }
        }
    }

    private boolean isTopBlock(ChunkMask data, Biome[] biomes, int x, int y, int z, int chunkX, int chunkZ) {
        return data.getBlock(x, y, z) == IslandBlockType.TOPSOIL_BLOCK.ordinal();
    }

    private void digBlock(ChunkMask data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, int state, int up) {
        if (this.canReplaceBlock(state, up) || state == IslandBlockType.TOPSOIL_BLOCK.ordinal() || state == IslandBlockType.SOIL_BLOCK.ordinal()) {
            data.setBlock(x, y, z, IslandBlockType.AIR_BLOCK.ordinal());

            if (foundTop && data.getBlock(x, y - 1, z) == IslandBlockType.SOIL_BLOCK.ordinal()) {
                data.setBlock(x, y - 1, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
            }
        }
    }
}
