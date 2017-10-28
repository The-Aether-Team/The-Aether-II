package com.gildedgames.orbis.common.player.godmode.selectors;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.GodPowerBlueprint;
import com.gildedgames.orbis.common.player.godmode.GodPowerSelect;
import com.gildedgames.orbis.common.player.godmode.IShapeSelector;
import net.minecraft.world.World;

public class ShapeSelectorSelect implements IShapeSelector
{
	private final GodPowerSelect power;

	public ShapeSelectorSelect(final GodPowerSelect power)
	{
		this.power = power;
	}

	@Override
	public boolean isSelectorActive(PlayerOrbisModule module, World world)
	{
		return false;
	}

	@Override
	public boolean canSelectShape(PlayerOrbisModule module, IShape shape, World world)
	{
		return true;
	}

	@Override
	public void onSelect(PlayerOrbisModule module, IShape selectedShape, World world)
	{

	}
}
