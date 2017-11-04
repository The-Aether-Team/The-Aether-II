package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.core.OrbisAPI;
import com.gildedgames.aether.api.orbis_core.api.util.BlueprintUtil;
import com.gildedgames.aether.api.orbis_core.block.BlockData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

public class BlueprintInstance implements NBT
{
	private final World world;

	private BlockDataChunk[] chunks;

	private BlueprintDefinition def;

	private int definitionID;

	private String registryId;

	private ICreationData data;

	private boolean hasGeneratedAChunk;

	public BlueprintInstance(final World world, final BlueprintDefinition def, final ICreationData data)
	{
		this.world = world;
		this.def = def;

		this.registryId = def.getRegistry().getRegistryId();
		this.definitionID = def.getRegistry().getID(this.def);

		this.data = data;

		this.bakeChunks();
	}

	public BlueprintInstance(final World world, final NBTTagCompound tag)
	{
		this.world = world;

		this.read(tag);

		this.bakeChunks();
	}

	private void bakeChunks()
	{
		final BlockDataContainer blocks = this.def.getData().getBlockDataContainer();

		final ChunkPos[] chunksOccupied = BlueprintUtil.getChunksInsideTemplate(this.getDef().getData(), this.getCreationData());

		this.chunks = new BlockDataChunk[chunksOccupied.length];

		final BlockPos min = this.data.getPos();

		final Region region = new Region(new BlockPos(0, 0, 0), new BlockPos(blocks.getWidth() - 1, blocks.getHeight() - 1, blocks.getLength() - 1));

		final int startChunkX = min.getX() >> 4;
		final int startChunkZ = min.getZ() >> 4;

		int xDif = min.getX() % 16;
		int zDif = min.getZ() % 16;

		if (xDif < 0)
		{
			xDif = 16 - Math.abs(xDif);
		}

		if (zDif < 0)
		{
			zDif = 16 - Math.abs(zDif);
		}

		for (final BlockPos.MutableBlockPos iterPos : region.getMutableBlockPosInRegion())
		{
			final int chunkX = ((min.getX() + iterPos.getX()) >> 4) - startChunkX;
			final int chunkZ = ((min.getZ() + iterPos.getZ()) >> 4) - startChunkZ;

			int index = 0;

			for (int i = 0; i < chunksOccupied.length; i++)
			{
				final ChunkPos p = chunksOccupied[i];

				if (p.chunkXPos - startChunkX == chunkX && p.chunkZPos - startChunkZ == chunkZ)
				{
					if (this.chunks[i] == null)
					{
						this.chunks[i] = new BlockDataChunk(p, new BlockDataContainer(16, blocks.getHeight(), 16));
					}

					index = i;
					break;
				}
			}

			final BlockDataChunk chunk = this.chunks[index];

			final BlockData block = blocks.get(iterPos.getX(), iterPos.getY(), iterPos.getZ());

			if (chunk != null && block != null)
			{
				chunk.getContainer()
						.set(block, (iterPos.getX() + xDif) % 16, iterPos.getY(), (iterPos.getZ() + zDif) % 16);
			}
		}
	}

	public BlueprintDefinition getDef()
	{
		return this.def;
	}

	public ICreationData getCreationData()
	{
		return this.data;
	}

	public BlockDataChunk[] getDataChunks()
	{
		return this.chunks;
	}

	public void markGeneratedAChunk()
	{
		this.hasGeneratedAChunk = true;
	}

	public boolean hasGeneratedAChunk()
	{
		return this.hasGeneratedAChunk;
	}

	@Override
	public boolean equals(final Object obj)
	{
		boolean flag = false;

		if (obj == this)
		{
			flag = true;
		}
		else if (obj instanceof BlueprintInstance)
		{
			final BlueprintInstance o = (BlueprintInstance) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.definitionID, o.definitionID);
			builder.append(this.data, o.data);
			builder.append(this.hasGeneratedAChunk, o.hasGeneratedAChunk);

			flag = builder.isEquals();
		}

		return flag;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.definitionID);
		builder.append(this.data);
		builder.append(this.hasGeneratedAChunk);

		return builder.toHashCode();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setString("registryId", this.registryId);
		tag.setInteger("definitionId", this.definitionID);

		funnel.set("creation", this.data);

		tag.setBoolean("hasGeneratedAChunk", this.hasGeneratedAChunk);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.def = OrbisAPI.services().findDefinitionRegistry(tag.getString("registryId")).get(tag.getInteger("definitionId"));

		this.data = funnel.get(this.world, "creation");

		this.hasGeneratedAChunk = tag.getBoolean("hasGeneratedAChunk");
	}

	@Override
	public BlueprintInstance clone()
	{
		final BlueprintInstance clone = new BlueprintInstance(this.world, this.def, this.data.clone());

		clone.chunks = Arrays.copyOf(this.chunks, this.chunks.length);
		clone.hasGeneratedAChunk = this.hasGeneratedAChunk;

		return clone;
	}
}
