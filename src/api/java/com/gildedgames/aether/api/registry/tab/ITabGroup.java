package com.gildedgames.aether.api.registry.tab;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;

import java.util.List;

public interface ITabGroup<T extends ITab>
{

	/**
	 * This adds an {@link ITab} to this {@link ITabGroupHandler}, allowing it to render. If this {@link ITabGroupHandler} only
	 * contains one {@link ITab}, it will not render over the {@link Screen} interface.
	 * @param tab The {@link ITab} you'd like added to this {@link ITabGroupHandler}
	 */
	void add(T tab);

	/**
	 * @return The amount of {@link ITab}s contained within this {@link ITabGroupHandler}
	 */
	int getTabAmount();

	/**
	 * @return A List of the {@link ITabGroupHandler}'s contained {@link ITab}s which are currently enabled. This is achieved through checking their {@link ITab#isEnabled()} method
	 */
	List<T> getEnabledTabs();

	/**
	 * @return A List of the {@link ITabGroupHandler}'s contained {@link ITab}s, regardless of if they are enabled or not.
	 * Just note that this List includes disabled {@link ITab}s as well.
	 */
	List<T> getTabs();

	/**
	 * If this {@link ITabGroupHandler} remembers the selected {@link ITab}, it'll open up that {@link ITab} next time
	 * the {@link ITabGroupHandler} is re-opened.
	 * @return Whether this {@link ITabGroupHandler} should remember its last selected tab or not
	 */
	boolean getRememberSelectedTab();

	/**
	 * If this {@link ITabGroupHandler} remembers the selected {@link ITab}, it'll open up that {@link ITab} next time
	 * the {@link ITabGroupHandler} is re-opened.
	 * @param rememberSelectedTab Whether this {@link ITabGroupHandler} should remember its last selected tab or not
	 */
	void setRememberSelectedTab(boolean rememberSelectedTab);

	/**
	 * The selected {@link ITab} in a {@link ITabGroupHandler} will display its associated {@link Screen}
	 * and {@link Container} to the player.
	 * @return The selected {@link ITab} in this {@link ITabGroupHandler}
	 */
	T getSelectedTab();

	/**
	 * The selected {@link ITab} in a {@link ITabGroupHandler} will display its associated {@link Screen}
	 * and {@link Container} to the player.
	 * @param tab The {@link ITab} you'd like selected within this {@link ITabGroupHandler}
	 */
	void setSelectedTab(T tab);

	/**
	 * The remembered {@link ITab} in a {@link ITabGroupHandler} will open up the next time its parent
	 * {@link ITabGroupHandler} is opened.
	 * @return The currently-remembered {@link ITab}
	 */
	T getRememberedTab();

	/**
	 * This makes the passed {@link ITab} remembered by this {@link ITabGroupHandler}, meaning the next time
	 * this {@link ITabGroupHandler} is opened, it will open to the currently-remembered {@link ITab}.
	 * @param tab The {@link ITab} you'd like this {@link ITabGroupHandler} to remember
	 */
	void setRememberedTab(T tab);

}
