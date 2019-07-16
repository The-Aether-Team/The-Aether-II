package com.gildedgames.aether.common.containers.overlays.tabs;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TabPatronRewards implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.patron_rewards";
	}

	@Override
	public void onOpen(PlayerEntity player)
	{
		BlockPos pos = player.getPosition();

		player.openGui(AetherCore.MOD_ID, AetherGuiHandler.PATRON_REWARDS_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
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
	public static class Client extends TabPatronRewards implements ITabClient
	{
		private static final ResourceLocation ICON = AetherCore.getResource("textures/gui/tabs/patron_rewards.png");

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
