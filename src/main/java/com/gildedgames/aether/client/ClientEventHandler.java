package com.gildedgames.aether.client;

import com.gildedgames.aether.common.items.armor.ItemObsidianArmor;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientEventHandler
{
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (player != null)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemObsidianArmor.class))
			{
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);

				player.setSneaking(false);
			}
		}
	}
}
