package com.gildedgames.aether.client.gui.tab;

import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabClient;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
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
			return gui instanceof GuiInventory || gui instanceof GuiEquipment;
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
