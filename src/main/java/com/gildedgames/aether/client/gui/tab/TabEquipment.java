package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.client.gui.menu.BugReportMenu;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.client.ui.minecraft.viewing.MinecraftGuiWrapper;
import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.ui.common.GuiDecorator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabEquipment implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.equipment";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		BlockPos pos = player.getPosition();

		player.openGui(AetherCore.MOD_ID, AetherGuiHandler.INVENTORY_EQUIPMENT_ID, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
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
	public static class Client extends TabEquipment implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/equipment.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return this.isMainGui(gui) || gui instanceof GuiInventory || gui instanceof GuiEquipment;
		}

		@Override
		public boolean isMainGui(GuiScreen gui)
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

			return flag;
		}

		@Override
		public void onClose(EntityPlayer player)
		{
		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabEquipment.Client.ICON;
		}
	}
}
