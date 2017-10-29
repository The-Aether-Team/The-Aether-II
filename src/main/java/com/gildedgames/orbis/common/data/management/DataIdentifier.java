package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IDataIdentifier;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DataIdentifier implements IDataIdentifier
{

	private int dataId;

	private IProjectIdentifier projectIdentifier;

	private DataIdentifier()
	{

	}

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

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof IDataIdentifier)
		{
			final IDataIdentifier id = (IDataIdentifier) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.getDataId(), id.getDataId());
			builder.append(this.getProjectIdentifier(), id.getProjectIdentifier());

			return builder.isEquals();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder(3, 7);

		builder.append(this.getDataId());
		builder.append(this.getProjectIdentifier());

		return builder.toHashCode();
	}

}
