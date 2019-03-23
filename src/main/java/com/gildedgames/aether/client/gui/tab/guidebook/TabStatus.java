package com.gildedgames.aether.client.gui.tab.guidebook;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TabStatus implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.guidebook.status";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();

		player.openGui(AetherCore.MOD_ID, AetherGuiHandler.STATUS_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean isEnabled()
	{
		//return Minecraft.getMinecraft().currentScreen instanceof AbstractGuidebookPage;
		return false;
	}

	@Override
	public boolean isRemembered()
	{
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Client extends TabStatus implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/guidebook_status.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return gui instanceof GuiInventory || gui instanceof AbstractGuidebookPage;
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
