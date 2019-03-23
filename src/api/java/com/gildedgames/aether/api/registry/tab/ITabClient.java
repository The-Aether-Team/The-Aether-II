package com.gildedgames.aether.api.registry.tab;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITabClient extends ITab
{
	/**
	 * List all {@link GuiScreen} classes which are used within this tab. If you use a class which you have not listed here,
	 * the {@link ITabRegistry} will not be able to recognize its association with this particular tab, meaning it
	 * won't display when the {@link GuiScreen} is open.
	 * @return All of the {@link GuiScreen} classes which are used within this tab.
	 */
	@OnlyIn(Dist.CLIENT)
	boolean isTabValid(GuiScreen gui);

	/**
	 * Called when the player selects another {@link ITab} within this {@link ITab}'s parent {@link ITabGroupHandler}. This includes
	 * when the {@link ITabGroupHandler} is closed and this {@link ITab} was open.
	 */
	void onClose(EntityPlayer player);

	/**
	 * @return Returns the icon this tab has.
	 */
	@OnlyIn(Dist.CLIENT)
	ResourceLocation getIcon();
}
