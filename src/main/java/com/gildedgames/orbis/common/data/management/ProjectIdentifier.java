package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A basic concrete implementation of IProjectIdentifier.
 */
public class ProjectIdentifier implements IProjectIdentifier
{
	private String projectId;

	private String originalCreator;

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

	// TODO: Equals and hash implementations

}
