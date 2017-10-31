package com.gildedgames.orbis_core.block;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_core.data.ICreationData;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class BlockFilter implements NBT
{
	private final List<BlockFilterLayer> filters = Lists.newArrayList();

	public BlockFilter()
	{
		super();
	}

	public BlockFilter(final BlockFilterLayer layer)
	{
		super();
		this.add(layer);
	}

	public BlockFilter(final BlockFilter blockLayerContainer)
	{
		super();
		this.addAll(blockLayerContainer.getFilters());
	}

	public BlockFilter(final List<BlockFilterLayer> layers)
	{
		super();
		this.addAll(layers);
	}

	public void apply(final IShape shape, final World world, final ICreationData options)
	{
		for (final BlockFilterLayer layer : this.filters)
		{
			if (layer != null)
			{
				layer.apply(shape, world, options);
			}
		}
	}

	public void add(final BlockFilterLayer layer)
	{
		this.filters.add(layer);
	}

	public void addAll(final Collection<BlockFilterLayer> layers)
	{
		this.filters.addAll(layers);
	}

	public void clear()
	{
		this.filters.clear();
	}

	public List<BlockFilterLayer> getFilters()
	{
		return this.filters;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.setList("filterList", this.filters);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.clear();
		final List<BlockFilterLayer> filters = funnel.getList("filterList");
		this.addAll(filters);
	}

	public boolean isEmpty()
	{
		return this.filters.isEmpty();
	}

	public BlockFilterLayer getByIndex(final int i)
	{
		return this.filters.get(i);
	}

}
