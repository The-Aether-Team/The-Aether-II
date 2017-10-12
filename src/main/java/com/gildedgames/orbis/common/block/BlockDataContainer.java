package com.gildedgames.orbis.common.block;

import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.common.OrbisCore;
import com.gildedgames.orbis.common.exceptions.OrbisMissingModsException;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockDataContainer implements NBT
{

	private final World world;

	private BlockData[] data;

	private int width, height, length;

	private BlockDataContainer(final World world, final NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	/**
	 * @param width
	 * @param height Maximum height possible is 256
	 * @param length
	 */
	public BlockDataContainer(final World world, final int width, final int height, final int length)
	{
		this.world = world;

		this.width = width;
		this.height = Math.min(256, height);
		this.length = length;

		this.data = new BlockData[this.getVolume()];
	}

	public BlockDataContainer(final World world, final IRegion region)
	{
		this(world, region.getWidth(), region.getHeight(), region.getLength());
	}

	public int getVolume()
	{
		return this.width * this.height * this.length;
	}

	private int getIndex(final int x, final int y, final int z)
	{
		final int index = z + y * this.length + x * this.height * this.length;

		if (index < this.getVolume() && index >= 0)
		{
			return index;
		}

		throw new ArrayIndexOutOfBoundsException("Tried to access position that's not in this BlockDataContainer");
	}

	private BlockData get(final int x, final int y, final int z)
	{
		final int index = this.getIndex(x, y, z);

		return this.data[index];
	}

	public BlockData get(final BlockPos pos)
	{
		return this.get(pos.getX(), pos.getY(), pos.getZ());
	}

	public void set(final BlockData block, final BlockPos pos)
	{
		if (block == null)
		{
			throw new NullPointerException("Tried to set a null block");
		}
		final int index = this.getIndex(pos.getX(), pos.getY(), pos.getZ());

		this.data[index] = block;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public int getLength()
	{
		return this.length;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}

		if (obj instanceof BlockDataContainer)
		{
			final BlockDataContainer cont = (BlockDataContainer) obj;
			return cont.getVolume() == this.getVolume() && Arrays.equals(this.data, cont.data);
		}

		return false;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setInteger("width", this.getWidth());
		tag.setInteger("height", this.getHeight());
		tag.setInteger("length", this.getLength());

		final byte[] blocks = new byte[this.getVolume()];
		byte[] addBlocks = null;

		final byte[] metadata = new byte[this.getVolume()];

		/**
		 * Create maps to compress data written
		 * Instead of writing entire ResourceLocations for each block, we'll
		 * be writing an integer and the map will point us to the appropriate
		 * ResourceLocation when reading back
		 */
		final Map<Integer, ResourceLocation> identifiers = Maps.newHashMap();
		final Map<Integer, TileEntity> tileEntities = Maps.newHashMap();

		int index = 0;

		for (final BlockData block : this.data)
		{
			final IBlockState blockState = block.getBlockState();

			final int id = OrbisCore.getRegistrar().getBlockId(blockState.getBlock());
			final int meta = blockState.getBlock().getMetaFromState(blockState);

			final ResourceLocation identifier = OrbisCore.getRegistrar().getIdentifierFor(blockState.getBlock());
			final TileEntity tileEntity = block.getTileEntity();

			if (!identifiers.containsKey(id))
			{
				identifiers.put(id, identifier);
			}

			/**
			 * Stuff I don't quite understand - looks like some sort of compression
			 * method when block ids surpass 255
			 */
			if (id > 255)
			{
				if (addBlocks == null)
				{
					addBlocks = new byte[(blocks.length >> 1) + 1];
				}

				final int addBlocksIndex = index >> 1;

				if ((index & 1) == 0)
				{
					final byte result = (byte) (addBlocks[addBlocksIndex] & 0xF0 | id >> 8 & 0xF);
					addBlocks[addBlocksIndex] = result;
				}
				else
				{
					final byte result = (byte) (addBlocks[addBlocksIndex] & 0xF | (id >> 8 & 0xF) << 4);
					addBlocks[addBlocksIndex] = result;
				}
			}

			blocks[index] = (byte) id;
			metadata[index] = (byte) meta;

			if (tileEntity != null)
			{
				tileEntities.put(index, tileEntity);
			}

			index++;
		}

		/**
		 * Saving the identifier map for later reference
		 */
		final NBTTagList identifierList = new NBTTagList();

		for (final Map.Entry<Integer, ResourceLocation> entry : identifiers.entrySet())
		{
			final NBTTagCompound data = new NBTTagCompound();

			final ResourceLocation identifier = entry.getValue();

			data.setString("mod", identifier.getResourceDomain());
			data.setString("name", identifier.getResourcePath());
			data.setInteger("id", entry.getKey());

			identifierList.appendTag(data);
		}

		tag.setTag("identifiers", identifierList);

		/**
		 * Saving tile entity data
		 */
		final NBTTagList tileEntityList = new NBTTagList();

		for (final Map.Entry<Integer, TileEntity> entry : tileEntities.entrySet())
		{
			final NBTTagCompound data = new NBTTagCompound();

			final NBTTagCompound tileEntData = new NBTTagCompound();

			entry.getValue().writeToNBT(tileEntData);

			data.setTag("tileEnt", tileEntData);
			data.setInteger("orbisTEIndex", entry.getKey());

			tileEntityList.appendTag(data);
		}

		tag.setTag("tileEntities", tileEntityList);

		tag.setByteArray("blocks", blocks);
		tag.setByteArray("metadata", metadata);

		tag.setByteArray("addBlocks", addBlocks);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.width = tag.getInteger("width");
		this.height = tag.getInteger("height");
		this.length = tag.getInteger("length");

		/** Read back identifier list so we can figure out which
		 * ids belong to what blocks (as well as their parent mods)
		 */
		final NBTTagList identifierList = tag.getTagList("identifiers", 10);
		final Map<Integer, Block> blocks = Maps.newHashMap();
		final Set<String> missingMods = new HashSet<>();

		for (int i = 0; i < identifierList.tagCount(); i++)
		{
			final NBTTagCompound data = identifierList.getCompoundTagAt(i);

			final String modname = data.getString("mod");
			final String blockname = data.getString("name");

			final Block block = OrbisCore.getRegistrar().findBlock(new ResourceLocation(modname, blockname));

			/**
			 * Add to missing mods list if we can't find the block with our registrar
			 */
			if (block == null)
			{
				data.getInteger("id");
				missingMods.add(modname);
			}
			else
			{
				blocks.put(data.getInteger("id"), block);
			}
		}

		if (!missingMods.isEmpty())
		{
			throw new OrbisMissingModsException("Failed loading BlockDataContainer", missingMods, AetherCore.MOD_ID);
		}

		/**
		 * Read back tile entities
		 */
		final Map<Integer, TileEntity> tileEntities = Maps.newHashMap();
		final NBTTagList tileEntityList = tag.getTagList("tileEntities", 10);

		for (int i = 0; i < tileEntityList.tagCount(); i++)
		{
			final NBTTagCompound data = tileEntityList.getCompoundTagAt(i);

			final NBTTagCompound tileEntData = data.getCompoundTag("tileEnt");
			tileEntities.put(data.getInteger("orbisTEIndex"), TileEntity.create(this.world, tileEntData));
		}

		final byte[] blockComp = tag.getByteArray("blocks");
		final byte[] blockdata = tag.getByteArray("blockData");
		final byte[] addBlocks = tag.getByteArray("addBlocks");

		if (blockComp.length != this.getVolume())
		{
			throw new IllegalStateException("Size of data mismatched dimensions given");
		}

		this.data = new BlockData[blockComp.length];

		/**
		 * Weird decompression stuff for ids beyond 255
		 */
		for (int i = 0; i < blockComp.length; i++)
		{
			final int finalId;

			if (i >> 1 >= addBlocks.length)
			{
				finalId = blockComp[i] & 0xFF;
			}
			else
			{
				if ((i & 1) == 0)
				{
					finalId = ((addBlocks[i >> 1] & 0x0F) << 8) + (blockComp[i] & 0xFF);
				}
				else
				{
					finalId = ((addBlocks[i >> 1] & 0xF0) << 4) + (blockComp[i] & 0xFF);
				}
			}

			final Block block = blocks.get(finalId);

			if (block == null)
			{
				throw new NullPointerException("Wasn't able to load block with id " + finalId);
			}

			final IBlockState blockState = OrbisCore.getRegistrar().getStateFromMeta(block, blockdata[i]);
			this.data[i] = new BlockData(blockState, tileEntities.get(i));
		}
	}
}
