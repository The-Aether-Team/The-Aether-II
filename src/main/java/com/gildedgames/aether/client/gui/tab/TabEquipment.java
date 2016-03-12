package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerAccessories;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.player.PacketOpenContainer;
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

public class TabEquipment implements ITab
{
	private static final ResourceLocation iconResource = AetherCore.getResource("textures/gui/tabs/equipment.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.equipment";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return gui instanceof GuiInventory || gui instanceof GuiEquipment;
	}

	@Override
	public void renderIcon(int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(iconResource);

		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiEquipment(PlayerAether.get(player)));
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
