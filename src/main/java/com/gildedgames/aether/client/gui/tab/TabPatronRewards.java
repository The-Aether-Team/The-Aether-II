package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.client.gui.misc.GuiPatronRewards;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabPatronRewards implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.patron_rewards";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		if (player.world.isRemote)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiPatronRewards());
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
	public static class Client extends TabPatronRewards implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/patron_rewards.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return gui instanceof GuiInventory || gui instanceof GuiEquipment || gui instanceof GuiPatronRewards;
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
