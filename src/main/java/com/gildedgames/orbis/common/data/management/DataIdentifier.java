package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;

public class DataIdentifier implements IDataIdentifier
{

	private int dataId;

	private IProjectIdentifier projectIdentifier;

	public DataIdentifier(final IProjectIdentifier identifier, final int dataId)
	{
		this.projectIdentifier = identifier;
		this.dataId = dataId;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("dataId", this.dataId);
		funnel.set("projectIdentifier", this.projectIdentifier);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.dataId = tag.getInteger("dataId");
		this.projectIdentifier = funnel.get("projectIdentifier");
	}

	@Override
	public int getDataId()
	{
		return this.dataId;
	}

	@Override
	public IProjectIdentifier getProjectIdentifier()
	{
		return this.projectIdentifier;
	}

	// TODO: Equals and hash implementations

}
