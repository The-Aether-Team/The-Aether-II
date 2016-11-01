package com.gildedgames.aether.api.registry.tab;

import net.minecraft.client.gui.GuiScreen;

import java.util.Map;

public interface ITabRegistry
{
	/**
	 * This method is used to register a {@link ITabGroupHandler} to the {@link ITabRegistry}. A registered {@link ITabGroupHandler} will be rendered
	 * onto the associated {@link GuiScreen}s and all functionality will be handled by the {@link ITabRegistry}. Note that if a
	 * {@link ITabGroupHandler} is not registered, it will not function in-game.
	 * @param tabGroup The {@link ITabGroupHandler} you'd like to register to the {@link ITabRegistry}
	 */
	void registerGroup(ITabGroupHandler tabGroup);

	ITabGroupHandler getActiveGroup();

	void setActiveGroup(ITabGroupHandler activeGroup);

	/**
	 * @return The default {@link ITabGroupHandler} which holds a {@link #getBackpackTab() Backpack} {@link ITab} for the vanilla Inventory GUI.
	 */
	ITabGroupHandler getInventoryGroup();

	/**
	 * @return The default {@link ITab} associated with Minecraft's vanilla Inventory GUI.
	 */
	ITab getBackpackTab();

	/**
	 * @return A map of the registered {@link ITabGroupHandler}s active within the game
	 */
	Map<Integer, ITabGroupHandler> getRegisteredTabGroups();
}
