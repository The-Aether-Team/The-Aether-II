package com.gildedgames.aether.api.world.generation;

import com.gildedgames.orbis.lib.util.mc.NBT;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TemplateLoc implements NBT
{

	private BlockPos pos;

	private PlacementSettings settings;

	private boolean isCentered;

	public TemplateLoc()
	{
		this.pos = BlockPos.ORIGIN;
		this.settings = new PlacementSettings().setMirror(Mirror.NONE).setIgnoreEntities(false).setIgnoreStructureBlock(false);
	}

	public TemplateLoc(final NBTTagCompound tag)
	{
		this.read(tag);
	}

	public TemplateLoc set(final BlockPos pos)
	{
		this.pos = pos;

		return this;
	}

	public TemplateLoc set(final PlacementSettings settings)
	{
		this.settings = settings;

		return this;
	}

	public TemplateLoc set(final boolean isCentered)
	{
		this.isCentered = isCentered;

		return this;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	public PlacementSettings getSettings()
	{
		return this.settings;
	}

	public boolean isCentered()
	{
		return this.isCentered;
	}

	@Override
	public boolean equals(final Object obj)
	{
		boolean flag = false;

		if (obj == this)
		{
			flag = true;
		}
		else if (obj instanceof TemplateLoc)
		{
			final TemplateLoc o = (TemplateLoc) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.pos.toLong(), o.pos.toLong());
			builder.append(this.isCentered, o.isCentered);

			flag = builder.isEquals();
		}

		return flag;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.pos.toLong());
		builder.append(this.isCentered);

		return builder.toHashCode();
	}

	@Override
	public TemplateLoc clone()
	{
		return new TemplateLoc().set(this.pos).set(this.settings).set(this.isCentered);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setTag("pos", NBTHelper.writeBlockPos(this.pos));

		//TODO: Write settings somehow

		tag.setBoolean("isCentered", this.isCentered);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.pos = NBTHelper.readBlockPos(tag.getCompoundTag("pos"));

		//TODO: Read settings back instead of creating new settings

		this.settings = new PlacementSettings().setMirror(Mirror.NONE).setIgnoreEntities(false).setIgnoreStructureBlock(false);

		this.isCentered = tag.getBoolean("isCentered");
	}
}
