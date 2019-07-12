package com.gildedgames.aether.common.containers.overlays.tabs;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TabBugReport implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.bug_report";
	}

	@Override
	public void onOpen(PlayerEntity player)
	{
		// TODO: Re-implement
		//		if (player.worldObj.isRemote)
		//		{
		//			UiManager.inst().open("bugReportMenu", new MinecraftGui(new BugReportMenu()));
		//		}
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean isRemembered()
	{
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Client extends TabBugReport implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/bug_report.png");

		@Override
		public boolean isTabValid(Screen gui)
		{
			return gui instanceof GuiInventory || gui instanceof AbstractGuidebookPage;
		}

		@Override
		public void onClose(PlayerEntity player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return Client.ICON;
		}

		@Override
		public Vec2f getCustomTabVec2()
		{
			return null;
		}
	}
}
