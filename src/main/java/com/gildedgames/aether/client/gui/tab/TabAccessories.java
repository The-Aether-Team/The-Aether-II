package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiAccessories;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabAccessories implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.accessories";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();

		player.openGui(AetherCore.MOD_ID, AetherGuiHandler.INVENTORY_ACCESSORIES_ID, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
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

	@SideOnly(Side.CLIENT)
	public static class Client extends TabAccessories implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/accessories.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return gui instanceof GuiInventory || gui instanceof GuiAccessories;
		}

		@Override
		public void onClose(EntityPlayer player)
		{
//			EntityPlayerSP spPlayer = Minecraft.getMinecraft().thePlayer;
//
//			spPlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(player.openContainer.windowId));
//			spPlayer.openContainer = player.inventoryContainer;
//
//			spPlayer.inventory.setItemStack(null);
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabAccessories.Client.ICON;
		}
	}
}
