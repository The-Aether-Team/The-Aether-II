package com.gildedgames.orbis_core.block;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_core.data.DataCondition;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class BlockDataWithConditions extends BlockData
{
	private DataCondition replaceCondition = new DataCondition();

	private DataCondition requiredCondition = new DataCondition();

	protected BlockDataWithConditions()
	{
		super();
	}

	public BlockDataWithConditions(final Block block, final float placementChance)
	{
		super(block);

		this.replaceCondition.setPlacementChance(placementChance);
	}

	public BlockDataWithConditions(final IBlockState state, final float placementChance)
	{
		super(state);

		this.replaceCondition.setPlacementChance(placementChance);
	}

	public DataCondition getReplaceCondition()
	{
		return this.replaceCondition;
	}

	public void setReplaceCondition(final DataCondition replaceCondition)
	{
		this.replaceCondition = replaceCondition;
	}

	public DataCondition getRequiredCondition()
	{
		return this.requiredCondition;
	}

	public void setRequiredCondition(final DataCondition requiredCondition)
	{
		this.requiredCondition = requiredCondition;
	}

	public void setReplacementChance(final float chance)
	{
		this.replaceCondition.setPlacementChance(chance);
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		super.write(tag);

		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("replaceCondition", this.replaceCondition);
		funnel.set("requiredCondition", this.requiredCondition);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		super.read(tag);

		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.replaceCondition = funnel.get("replaceCondition");
		this.requiredCondition = funnel.get("requiredCondition");
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof BlockDataWithConditions))
		{
			return false;
		}

		final BlockDataWithConditions filter = (BlockDataWithConditions) obj;

		return super.equals(obj);
	}

}
