package com.gildedgames.aether.common.containers.overlays.tabs;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
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
	public void onOpen(PlayerEntity player)
	{
		Minecraft.getInstance().displayGuiScreen(new GuiChat(""));
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
		public void onClose(PlayerEntity player)
		{

		}

		@Override
		public boolean isTabValid(Screen gui)
		{
			return gui instanceof GuiChat;
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabChat.Client.ICON;
		}

		@Override
		public Vec2f getCustomTabVec2()
		{
			return null;
		}
	}
}
