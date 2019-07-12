package com.gildedgames.aether.common.containers.overlays.tabs;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

/**
 * The {@link ITab} representation of the Minecraft's vanilla Inventory {@link Screen}
 * @author Brandon Pearce
 */
public class TabBackpack implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.backpack.name";
	}

	@Override
	public void onOpen(PlayerEntity player)
	{
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean isRemembered()
	{
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Client extends TabBackpack implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/backpack.png");

		@Override
		public boolean isTabValid(Screen gui)
		{
			Class<? extends Screen> clazz = gui.getClass();
			return clazz == GuiInventory.class || clazz == GuiContainerCreative.class;
		}

		@Override
		public void onOpen(PlayerEntity player)
		{
			Minecraft.getInstance().displayGuiScreen(new GuiInventory(player));
		}

		@Override
		public void onClose(PlayerEntity player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabBackpack.Client.ICON;
		}

		@Override
		public Vec2f getCustomTabVec2()
		{
			return null;
		}
	}
}
