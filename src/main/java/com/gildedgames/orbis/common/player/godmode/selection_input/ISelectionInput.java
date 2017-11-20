package com.gildedgames.orbis.common.player.godmode.selection_input;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.api.world_object.IWorldObject;
import com.gildedgames.orbis.client.player.godmode.selection_inputs.ISelectionInputClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import net.minecraftforge.client.event.MouseEvent;

public interface ISelectionInput extends NBT
{

	boolean shouldClearSelectionOnEscape();

	/**
	 * @param isActive Whether or not this selection input
	 *                 implementation is the actively used
	 *                 implementation.
	 */
	void onUpdate(boolean isActive, IShapeSelector selector);

	void onMouseEvent(MouseEvent event, IShapeSelector selector, PlayerOrbisModule module);

	void clearSelection();

	IWorldObject getActiveSelection();

	void setActiveSelection(IWorldObject activeSelection);

	ISelectionInputClient getClient();

}
