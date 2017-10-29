package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public interface IShapeSelector
{

	boolean isSelectorActive(PlayerOrbisModule module, World world);

	boolean canSelectShape(PlayerOrbisModule module, IShape shape, World world);

	void onSelect(PlayerOrbisModule module, IShape selectedShape, World world);

	/**
	 * @return True if it should use the regular active selection behaviour. False to cancel.
	 */
	boolean onRightClickShape(PlayerOrbisModule module, IShape selectedShape, MouseEvent event);

}