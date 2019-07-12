package com.gildedgames.aether.api.registry.tab;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITabClient extends ITab
{
	/**
	 * List all {@link Screen} classes which are used within this tab. If you use a class which you have not listed here,
	 * the {@link ITabRegistry} will not be able to recognize its association with this particular tab, meaning it
	 * won't display when the {@link Screen} is open.
	 * @return All of the {@link Screen} classes which are used within this tab.
	 */
	@OnlyIn(Dist.CLIENT)
	boolean isTabValid(Screen gui);

	/**
	 * Called when the player selects another {@link ITab} within this {@link ITab}'s parent {@link ITabGroupHandler}. This includes
	 * when the {@link ITabGroupHandler} is closed and this {@link ITab} was open.
	 */
	void onClose(PlayerEntity player);

	/**
	 * @return Returns the icon this tab has.
	 */
	@OnlyIn(Dist.CLIENT)
	ResourceLocation getIcon();

	/**
	 * Default tabs use Minecraft's default tab texture with an icon drawn over it.
	 * A custom tab means instead of using the default tab texture, a custom tab texture should be used.
	 * @return 2D Vector of the tab's texture x and y.
	 */
	@OnlyIn(Dist.CLIENT)
	Vec2f getCustomTabVec2();
}
