package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetShouldRespawnAtCampfire;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiRespawnListener
{
	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.DrawScreenEvent event)
	{
		Screen gui = event.getGui();

		if (gui instanceof GuiGameOver && gui.mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			gui.buttonList.get(2).enabled = gui.buttonList.get(1).enabled;
		}
	}

	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.InitGuiEvent.Post event)
	{
		Screen gui = event.getGui();

		if (gui instanceof GuiGameOver && gui.mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			event.getButtonList().get(0).displayString = I18n.format("gui.aether.respawn.bed");
			event.getButtonList().get(1).y += 24;
			event.getButtonList().add(new Button(2, gui.width / 2 - 100, gui.height / 4 + 96, I18n.format("gui.aether.campfire.bed")));
		}
	}

	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.ActionPerformedEvent event)
	{
		Screen gui = event.getGui();

		if (gui instanceof GuiGameOver && gui.mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			if (event.getButton() != null && event.getButton().id == 2)
			{
				NetworkingAether.sendPacketToServer(new PacketSetShouldRespawnAtCampfire());
				gui.mc.player.respawnPlayer();
				gui.mc.displayGuiScreen(null);
			}
		}
	}
}
