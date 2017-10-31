package com.gildedgames.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_core.data.management.IProjectIdentifier;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A basic concrete implementation of IProjectIdentifier.
 */
public class ProjectIdentifier implements IProjectIdentifier
{
	private String projectId;

	private String originalCreator;

	private ProjectIdentifier()
	{

	}

	public ProjectIdentifier(final String projectId, final String originalCreator)
	{
		this.projectId = projectId;
		this.originalCreator = originalCreator;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setString("id", this.projectId);
		tag.setString("originalCreator", this.originalCreator);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.projectId = tag.getString("id");
		this.originalCreator = tag.getString("originalCreator");
	}

	@Override
	public String getProjectId()
	{
		return this.projectId;
	}

	@Override
	public String getOriginalCreator()
	{
		return this.originalCreator;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof IProjectIdentifier)
		{
			final IProjectIdentifier id = (IProjectIdentifier) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.getProjectId(), id.getProjectId());
			builder.append(this.getOriginalCreator(), id.getOriginalCreator());

			return builder.isEquals();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder(17, 37);

		builder.append(this.getProjectId());
		builder.append(this.getOriginalCreator());

		return builder.toHashCode();
	}

	@Override
	public String toString()
	{
		return this.projectId + ":" + this.originalCreator;
	}

}
