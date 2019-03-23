package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The {@link ITab} representation of the Minecraft's vanilla Inventory {@link GuiScreen}
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
	public void onOpen(EntityPlayer player)
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
		public boolean isTabValid(GuiScreen gui)
		{
			Class<? extends GuiScreen> clazz = gui.getClass();
			return clazz == GuiInventory.class || clazz == GuiContainerCreative.class;
		}

		@Override
		public void onOpen(EntityPlayer player)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(player));
		}

		@Override
		public void onClose(EntityPlayer player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabBackpack.Client.ICON;
		}
	}
}
