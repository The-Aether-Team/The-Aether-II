package com.gildedgames.aether.api.world.templates;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.util.TemplateUtil;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TemplateInstance implements NBT
{

	private final ChunkPos[] chunksOccupied;

	private TemplateDefinition def;

	private int templateID;

	private TemplateLoc loc;

	private boolean hasGeneratedAChunk;

	public TemplateInstance(final TemplateDefinition def, final TemplateLoc loc)
	{
		this.def = def;
		this.templateID = AetherAPI.content().templates().getID(this.def);
		this.loc = loc;
		this.chunksOccupied = TemplateUtil.getChunksInsideTemplate(this.getDef(), this.getLoc());
	}

	public TemplateInstance(final CompoundNBT tag)
	{
		this.read(tag);

		this.chunksOccupied = TemplateUtil.getChunksInsideTemplate(this.getDef(), this.getLoc());
	}

	public TemplateDefinition getDef()
	{
		return this.def;
	}

	public TemplateLoc getLoc()
	{
		return this.loc;
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
		else if (obj instanceof TemplateInstance)
		{
			final TemplateInstance o = (TemplateInstance) obj;
			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(this.templateID, o.templateID);
			builder.append(this.loc, o.loc);
			builder.append(this.hasGeneratedAChunk, o.hasGeneratedAChunk);

			flag = builder.isEquals();
		}

		return flag;
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.templateID);
		builder.append(this.loc);
		builder.append(this.hasGeneratedAChunk);

		return builder.toHashCode();
	}

	@Override
	public void write(final CompoundNBT tag)
	{
		tag.putInt("id", this.templateID);
		tag.put("loc", NBTHelper.writeRaw(this.loc));
		tag.putBoolean("hasGeneratedAChunk", this.hasGeneratedAChunk);
	}

	@Override
	public void read(final CompoundNBT tag)
	{
		this.def = AetherAPI.content().templates().get(tag.getInt("id"));
		this.loc = new TemplateLoc(tag.getCompound("loc"));
		this.hasGeneratedAChunk = tag.getBoolean("hasGeneratedAChunk");
	}
}
