package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.core.OrbisAPI;
import com.gildedgames.aether.api.orbis_core.api.util.BlueprintUtil;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BlueprintInstance implements NBT
{

	private final ChunkPos[] chunksOccupied;

	private BlueprintDefinition def;

	private int definitionID;

	private String registryId;

	private ICreationData data;

	private boolean hasGeneratedAChunk;

	public BlueprintInstance(final BlueprintDefinition def, final ICreationData data)
	{
		this.def = def;

		this.registryId = def.getRegistry().getRegistryId();
		this.definitionID = def.getRegistry().getID(this.def);

		this.data = data;
		this.chunksOccupied = BlueprintUtil.getChunksInsideTemplate(this.getDef(), this.getCreationData());
	}

	public BlueprintInstance(final NBTTagCompound tag)
	{
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
		tag.setInteger("id", this.definitionID);

		funnel.set("data", this.data);

		tag.setBoolean("hasGeneratedAChunk", this.hasGeneratedAChunk);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.def = OrbisAPI.services().findDefinitionRegistry(tag.getString("registryId")).get(tag.getInteger("id"));

		this.data = funnel.get("data");

		this.hasGeneratedAChunk = tag.getBoolean("hasGeneratedAChunk");
	}
}
