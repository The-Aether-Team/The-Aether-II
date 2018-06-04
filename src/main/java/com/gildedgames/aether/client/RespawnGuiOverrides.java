package com.gildedgames.aether.client;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetShouldRespawnAtCampfire;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class RespawnGuiOverrides
{
	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.DrawScreenEvent event)
	{
		GuiScreen gui = event.getGui();

		if (gui instanceof GuiGameOver && gui.mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			List<GuiButton> buttonList = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, gui, ReflectionAether.BUTTON_LIST.getMappings());

			buttonList.get(2).enabled = buttonList.get(1).enabled;
		}
	}

	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.InitGuiEvent.Post event)
	{
		GuiScreen gui = event.getGui();

		if (gui instanceof GuiGameOver && gui.mc.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			event.getButtonList().get(0).displayString = I18n.format("gui.aether.respawn.bed");
			event.getButtonList().get(1).y += 24;
			event.getButtonList().add(new GuiButton(2, gui.width / 2 - 100, gui.height / 4 + 96, I18n.format("gui.aether.campfire.bed")));
		}
	}

	@SubscribeEvent
	public static void onEvent(GuiScreenEvent.ActionPerformedEvent event)
	{
		GuiScreen gui = event.getGui();

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
