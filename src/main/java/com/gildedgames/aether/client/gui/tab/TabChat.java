package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TabChat implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.chat.name";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiChat(""));
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
	public static class Client extends TabChat implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/chat.png");

		@Override
		public void onClose(EntityPlayer player)
		{

		}

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return gui instanceof GuiChat;
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabChat.Client.ICON;
		}
	}
}
