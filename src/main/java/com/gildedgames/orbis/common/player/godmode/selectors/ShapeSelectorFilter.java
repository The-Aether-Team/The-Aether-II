package com.gildedgames.orbis.common.player.godmode.selectors;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.data.CreationData;
import com.gildedgames.orbis.common.data.ICreationData;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Function;

public class ShapeSelectorFilter implements IShapeSelector
{

	private final Function<ItemStack, BlockFilter> filterSupplier;

	private final boolean canSelectWithoutItems;

	public ShapeSelectorFilter(final Function<ItemStack, BlockFilter> filterSupplier, final boolean canSelectWithoutItems)
	{
		this.filterSupplier = filterSupplier;
		this.canSelectWithoutItems = canSelectWithoutItems;
	}

	@Override
	public boolean isSelectorActive(final PlayerOrbisModule module, final World world)
	{
		final ItemStack held = module.getEntity().getHeldItemMainhand();

		return held.getItem() instanceof ItemBlock || this.canSelectWithoutItems;
	}

	@Override
	public boolean canSelectShape(final PlayerOrbisModule module, final IShape shape, final World world)
	{
		return true;
	}

	@Override
	public void onSelect(final PlayerOrbisModule module, final IShape selectedShape, final World world)
	{
		if (world.isRemote)
		{
			return;
		}

		final ItemStack held = module.getEntity().getHeldItemMainhand();
		final BlockFilter filter = this.filterSupplier.apply(held);

		final ICreationData creationData = new CreationData(world, module.getEntity());
		filter.apply(selectedShape, world, creationData);
	}
}
