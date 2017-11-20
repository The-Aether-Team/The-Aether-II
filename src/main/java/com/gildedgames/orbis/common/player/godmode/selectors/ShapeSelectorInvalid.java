package com.gildedgames.orbis.common.player.godmode.selectors;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.world.World;

public class ShapeSelectorInvalid implements IShapeSelector
{

	@Override
	public boolean isSelectorActive(final PlayerOrbisModule module, final World world)
	{
		return false;
	}

	@Override
	public boolean canSelectShape(final PlayerOrbisModule module, final IShape shape, final World world)
	{
		return false;
	}

	@Override
	public void onSelect(final PlayerOrbisModule module, final IShape selectedShape, final World world)
	{

	}
}
