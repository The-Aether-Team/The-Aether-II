package com.gildedgames.aether.api.orbis_core.block;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.ICreationData;
import com.gildedgames.aether.api.orbis_core.data.DataCondition;
import com.gildedgames.aether.api.util.NBT;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockFilterLayer implements NBT
{

	public boolean chooseBlockPerBlock = true;

	protected List<BlockDataWithConditions> requiredBlocks = Lists.newArrayList();

	protected List<BlockDataWithConditions> replacementBlocks = Lists.newArrayList();

	protected String name = "";

	protected DataCondition condition;

	private BlockFilterType blockFilterType = BlockFilterType.ALL;

	public BlockFilterLayer()
	{
		super();
		this.condition = new DataCondition();
	}

	public BlockFilterLayer(List<BlockDataWithConditions> requiredBlocks, List<BlockDataWithConditions> newBlocks)
	{
		this();
		this.requiredBlocks = Lists.newArrayList(requiredBlocks);
		this.replacementBlocks = Lists.newArrayList(newBlocks);
	}

	/**
	 * Gets the list of blocks that trigger the filter
	 */
	public List<BlockDataWithConditions> getRequiredBlocks()
	{
		return this.requiredBlocks;
	}

	/**
	 * Sets the list of blocks that trigger the filter
	 */
	public void setRequiredBlocks(BlockDataWithConditions... requiredBlocks)
	{
		this.requiredBlocks = Lists.newArrayList(Arrays.asList(requiredBlocks));
	}

	/**
	 * Sets the list of blocks that trigger the filter
	 */
	public void setRequiredBlocks(List<BlockDataWithConditions> requiredBlocks)
	{
		this.requiredBlocks = Lists.newArrayList(requiredBlocks);
	}

	public List<BlockDataWithConditions> getReplacementBlocks()
	{
		return this.replacementBlocks;
	}

	public void setReplacementBlocks(BlockDataWithConditions... newBlocks)
	{
		this.replacementBlocks = Lists.newArrayList(Arrays.asList(newBlocks));
	}

	public void setReplacementBlocks(List<BlockDataWithConditions> newBlocks)
	{
		this.replacementBlocks = newBlocks;
	}

	public BlockFilterType getFilterType()
	{
		return this.blockFilterType;
	}

	public void setFilterType(BlockFilterType blockFilterType)
	{
		this.blockFilterType = blockFilterType;
	}

	private BlockDataWithConditions getRandom(Random random, World world)
	{
		final float randomValue = random.nextFloat() * this.totalBlockChance();
		float chanceSum = 0.0f;

		for (final BlockDataWithConditions block : this.replacementBlocks)
		{
			if (block.getReplaceCondition().isMet(randomValue, chanceSum, random, world))
			{
				return block;
			}

			chanceSum += block.getReplaceCondition().getWeight();
		}

		return null;
	}

	/**
	 * Applies this layer to a shape
	 */
	public void apply(IShape shape, World world, ICreationData options)
	{
		if (this.condition == null)
		{
			this.condition = new DataCondition();
		}

		if (!this.condition.isMet(options.getRandom(), world) || this.replacementBlocks.isEmpty())
		{
			return;
		}

		BlockDataWithConditions replacementBlock = null;

		if (!this.chooseBlockPerBlock)
		{
			replacementBlock = this.getRandom(options.getRandom(), world);
		}

		for (final BlockPos.MutableBlockPos pos : shape.createShapeData())
		{
			final IBlockState state = world.getBlockState(pos);

			if (!this.getFilterType().filter(state, this.requiredBlocks, world, options.getRandom()))
			{
				continue;
			}

			if (this.chooseBlockPerBlock)
			{
				replacementBlock = this.getRandom(options.getRandom(), world);
			}

			if (pos.getY() >= 256 || replacementBlock == null || !replacementBlock.getReplaceCondition().isMet(options.getRandom(), world))
			{
				continue;
			}

			world.setBlockState(pos.toImmutable(), replacementBlock.getBlockState(), 3);//TODO: Test for weird interactions

			// TODO: Re-enable event
			/*final ChangeBlockEvent blockEvent = new ChangeBlockEvent(world, pos, options.getCreator());
			MinecraftForge.EVENT_BUS.post(blockEvent);*/
		}
	}

	public float totalBlockChance()
	{
		float total = 0f;

		for (final BlockDataWithConditions BlockDataFilter : this.replacementBlocks)
		{
			total += BlockDataFilter.getReplaceCondition().getWeight();
		}

		return total;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setString("name", this.name);

		tag.setString("filterName", this.getFilterType().name());

		tag.setBoolean("chooseBlockPerBlock", this.chooseBlockPerBlock);

		NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.set("condition", this.condition);

		funnel.setList("requiredBlocks", this.requiredBlocks);
		funnel.setList("replacementBlocks", this.replacementBlocks);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.name = tag.getString("name");

		this.blockFilterType = BlockFilterType.valueOf(tag.getString("filterName"));

		this.chooseBlockPerBlock = tag.getBoolean("chooseBlockPerBlock");

		NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.condition = funnel.get("condition");

		this.requiredBlocks = funnel.getList("requiredBlocks");
		this.replacementBlocks = funnel.getList("replacementBlocks");
	}

	@Override
	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.getReplacementBlocks());
		builder.append(this.getRequiredBlocks());
		builder.append(this.getFilterType());

		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BlockFilterLayer))
		{
			return false;
		}

		final BlockFilterLayer layer = (BlockFilterLayer) obj;

		return this.getReplacementBlocks().equals(layer.getReplacementBlocks()) && this.getRequiredBlocks().equals(layer.getRequiredBlocks()) && this.getFilterType().equals(layer.getFilterType());
	}

}

