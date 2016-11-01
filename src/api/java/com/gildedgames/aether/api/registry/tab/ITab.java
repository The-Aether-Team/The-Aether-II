package com.gildedgames.aether.api.registry.tab;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

/**
 * This is the interface you should use to construct a Tab and its functionality. With two or more {@link ITab}s,
 * you can develop a whole {@link ITabGroupHandler} to link to a {@link GuiScreen}'s interface.
 * @author Brandon Pearce
 */
public interface ITab
{
	/**
	 * This is the name which is displayed upon hovering over this {@link ITab} or when it is currently selected. Note that this is
	 * unlocalized and will be translated to the correct value if your mod uses a language file.
	 * @return This {@link ITab}'s unlocalized name which is used for rendering.
	 */
	String getUnlocalizedName();

	/**
	 * Called when the player selects this {@link ITab} within its parent {@link ITabGroupHandler}. This includes
	 * when the {@link ITabGroupHandler} is opened up again and this {@link ITab} was its last-remembered tab.
	 */
	void onOpen(EntityPlayer player);

	/**
	 * When an {@link ITab} is enabled, it will render as normal within its parent {@link ITabGroupHandler}. However,
	 * if it is disabled, it will not render and can not be selected by the player.
	 * @return Whether or not this {@link ITab} is enabled
	 */
	boolean isEnabled();

	/**
	 * If an {@link ITab} can be remembered and it was the last {@link ITab} selected upon the closing of its parent
	 * {@link ITabGroupHandler}, it will be opened up the next time that {@link ITabGroupHandler} is reopened.
	 * @return Whether or not this {@link ITab} should be remembered by its parent {@link ITabGroupHandler}
	 */
	boolean isRemembered();

}
