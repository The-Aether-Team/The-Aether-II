package com.gildedgames.aether.common.world.aether.island.data.virtual;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IVirtualChunk;
import com.gildedgames.aether.api.world.islands.IVirtualDataManager;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

/**
 * A virtual chunk class with variable height (still 16x16 in width and length)
 * Used for Island generation so we can determine the shape of the island before actual
 * block placement
 */
public class VirtualChunk implements IVirtualChunk
{

	public final static int WIDTH = 16, LENGTH = 16, HEIGHT = 256;

	public static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();

	private final int posX;

	private final int posZ;

	public char[] data;

	public VirtualChunk(final int posX, final int posZ)
	{
		this.posX = posX;
		this.posZ = posZ;
		this.data = new char[VirtualChunk.WIDTH * VirtualChunk.LENGTH * VirtualChunk.HEIGHT];
	}

	@Override
	public int getX()
	{
		return this.posX;
	}

	@Override
	public int getZ()
	{
		return this.posZ;
	}

	/**
	 * Since our data is 1D, we can determine the volume easily
	 * with the length of the array
	 * @return The volume of the chunk's data
	 */
	@Override
	public int getVolume()
	{
		return this.data.length;
	}

	/**
	 * Easy helper method to retrieve the index based on the provided coords
	 * Since our data is a flat 1D array, we need to squish the coords into
	 * an appropriate index. This improves performance and makes memory management
	 * easier.
	 * @param x The x coord
	 * @param y The y coord
	 * @param z The z coord
	 * @return The index for the chunk's data
	 */
	private int getIndexFrom(final int x, final int y, final int z)
	{
		final int index = (VirtualChunk.HEIGHT * VirtualChunk.WIDTH * z + VirtualChunk.WIDTH * y) + x;

		if (index < this.getVolume() && index >= 0)
		{
			return index;
		}

		throw new ArrayIndexOutOfBoundsException("Tried to access position that's not in this VirtualChunk");
	}

	/**
	 * Getter method for states in the chunk
	 * @param x The x coord
	 * @param y The y coord
	 * @param z The z coord
	 * @return The state at the specified coord
	 */
	@Override
	public IBlockState getState(final int x, final int y, final int z)
	{
		final IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[this.getIndexFrom(x, y, z)]);
		return iblockstate == null ? DEFAULT_STATE : iblockstate;
	}

	/**
	 * Setter method for states in the chunk
	 * @param x The x coord
	 * @param y The y coord
	 * @param z The z coord
	 * @param state The state you're setting at the specified coord
	 */
	@Override
	public void setState(final int x, final int y, final int z, final IBlockState state)
	{
		this.data[this.getIndexFrom(x, y, z)] = (char) Block.BLOCK_STATE_IDS.get(state);
	}

	/**
	 * Used to find the top block's y coord
	 * @param x The x coord
	 * @param z The y coord
	 * @return The top block's y coord
	 */
	@Override
	public int getHeightValue(final int x, final int z)
	{
		for (int y = VirtualChunk.HEIGHT - 1; y >= 0; --y)
		{
			final IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[this.getIndexFrom(x, y, z)]);

			if (iblockstate != null && iblockstate != DEFAULT_STATE)
			{
				return y;
			}
		}

		return -1;
	}

	@Override
	public void prepareThisAndNearbyChunks(final World world, final IIslandData island, final Random rand)
	{
		final IVirtualDataManager manager = island.getVirtualDataManager();

		final IVirtualChunk chunk = manager.getChunk(this.getX(), this.getZ() - 1);
		final IVirtualChunk chunk1 = manager.getChunk(this.getX() + 1, this.getZ());
		final IVirtualChunk chunk2 = manager.getChunk(this.getX(), this.getZ() + 1);
		final IVirtualChunk chunk3 = manager.getChunk(this.getX() - 1, this.getZ());

		if (chunk1 != null && chunk2 != null && manager.getChunk(this.getX() + 1, this.getZ() + 1) != null)
		{
			this.prepare(world, island, rand);
		}

		if (chunk3 != null && chunk2 != null && manager.getChunk(this.getX() - 1, this.getZ() + 1) != null)
		{
			chunk3.prepare(world, island, rand);
		}

		if (chunk != null && chunk1 != null && manager.getChunk(this.getX() + 1, this.getZ() - 1) != null)
		{
			chunk.prepare(world, island, rand);
		}

		if (chunk != null && chunk3 != null)
		{
			final IVirtualChunk chunk4 = manager.getChunk(this.getX() - 1, this.getZ() - 1);

			if (chunk4 != null)
			{
				chunk4.prepare(world, island, rand);
			}
		}
	}

	@Override
	public void prepare(final World world, final IIslandData island, final Random rand)
	{
		final BlockPos pos = new BlockPos((this.getX() * 16) + 16, 0, (this.getZ() * 16) + 16);

		final Biome biome = world.getBiome(pos);

		if (biome instanceof BiomeAetherBase)
		{
			final BiomeAetherBase aetherBiome = (BiomeAetherBase) biome;

			aetherBiome.getBiomeDecorator().prepareDecorationsPerChunk(world, island, rand, pos, aetherBiome);
		}
	}

}
