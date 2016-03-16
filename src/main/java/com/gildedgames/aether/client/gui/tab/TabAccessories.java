package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiAccessories;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerAccessories;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.modules.tab.common.util.ITab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TabAccessories implements ITab
{
	private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/accessories.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.accessories";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return gui instanceof GuiInventory || gui instanceof GuiAccessories;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return TabAccessories.ICON;
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiAccessories(PlayerAether.get(player)));
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		EntityPlayerSP spPlayer = Minecraft.getMinecraft().thePlayer;

		spPlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(player.openContainer.windowId));
		spPlayer.openContainer = player.inventoryContainer;

		spPlayer.inventory.setItemStack(null);
	}

	@Override
	public Container getCurrentContainer(EntityPlayer player, World world, int posX, int posY, int posZ)
	{
		return new ContainerAccessories(PlayerAether.get(player));
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
}
