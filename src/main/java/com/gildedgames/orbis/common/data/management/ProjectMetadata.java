package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis.management.IProjectMetadata;
import com.gildedgames.aether.api.util.IText;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * A basic concrete implementation of IProjectMetadata.
 * Can be decorated with ProjectMetadataDecorator.
 */
public class ProjectMetadata implements IProjectMetadata
{
	private List<IText> display;

	private IDataIdentifier identifier;

	private List<IDataIdentifier> dependencies;

	public ProjectMetadata()
	{
		this.display = Lists.newArrayList();
		this.dependencies = Lists.newArrayList();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("identifier", this.identifier);

		funnel.setList("dependencies", this.dependencies);
		funnel.setList("display", this.display);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.identifier = funnel.get("identifier");

		this.dependencies = funnel.getList("dependencies");
		this.display = funnel.getList("display");
	}

	@Override
	public List<IText> getMetadataDisplay()
	{
		return this.display;
	}

	@Override
	public List<IDataIdentifier> getDependencies()
	{
		return this.dependencies;
	}

	@Override
	public IDataIdentifier getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public void setIdentifier(final IDataIdentifier projectIdentifier)
	{
		this.identifier = identifier;
	}
}
