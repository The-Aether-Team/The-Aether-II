package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.world.World;

public interface IShapeSelector
{

	boolean isSelectorActive(PlayerOrbisModule module, World world);

	boolean canSelectShape(PlayerOrbisModule module, IShape shape, World world);

	void onSelect(PlayerOrbisModule module, IShape selectedShape, World world);

}