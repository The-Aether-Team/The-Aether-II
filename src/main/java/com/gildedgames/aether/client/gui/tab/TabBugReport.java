package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.client.gui.menu.BugReportMenu;
import com.gildedgames.aether.client.ui.UiManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.client.ui.minecraft.util.decorators.MinecraftGui;
import com.gildedgames.aether.client.ui.minecraft.viewing.MinecraftGuiWrapper;
import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.ui.common.GuiDecorator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabBugReport implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.bug_report";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		if (player.worldObj.isRemote)
		{
			UiManager.inst().open("bugReportMenu", new MinecraftGui(new BugReportMenu()));
		}
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

	@SideOnly(Side.CLIENT)
	public static class Client extends TabBugReport implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/bug_report.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			boolean flag = false;

			if (gui instanceof MinecraftGuiWrapper)
			{
				MinecraftGuiWrapper wrapper = (MinecraftGuiWrapper)gui;

				if (wrapper.getFrame() instanceof GuiDecorator)
				{
					GuiDecorator decorator = (GuiDecorator)wrapper.getFrame();

					flag = decorator.getDecoratedElement() instanceof BugReportMenu;
				}
				else
				{
					flag = wrapper.getFrame() instanceof BugReportMenu;
				}
			}

			return gui instanceof GuiInventory || gui instanceof GuiEquipment || flag;
		}

		@Override
		public void onClose(EntityPlayer player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return Client.ICON;
		}
	}
}
