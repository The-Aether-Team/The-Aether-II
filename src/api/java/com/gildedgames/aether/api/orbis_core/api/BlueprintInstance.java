package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.core.OrbisAPI;
import com.gildedgames.aether.api.orbis_core.api.util.BlueprintUtil;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

public class BlueprintInstance implements NBT
{
	private final World world;

	private ChunkPos[] chunksOccupied;

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
		this.chunksOccupied = BlueprintUtil.getChunksInsideTemplate(this.getDef(), this.getCreationData());
	}

	public BlueprintInstance(final World world, final NBTTagCompound tag)
	{
		this.world = world;

		this.read(tag);

		this.chunksOccupied = BlueprintUtil.getChunksInsideTemplate(this.getDef(), this.getCreationData());
	}

	public BlueprintDefinition getDef()
	{
		return this.def;
	}

	public ICreationData getCreationData()
	{
		return this.data;
	}

	public ChunkPos[] getChunksOccupied()
	{
		return this.chunksOccupied;
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

		clone.chunksOccupied = Arrays.copyOf(this.chunksOccupied, this.chunksOccupied.length);
		clone.hasGeneratedAChunk = this.hasGeneratedAChunk;

		return clone;
	}
}
