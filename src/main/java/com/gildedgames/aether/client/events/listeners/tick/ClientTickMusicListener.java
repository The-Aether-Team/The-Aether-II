package com.gildedgames.aether.client.events.listeners.tick;

import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientTickMusicListener
{
	@SubscribeEvent
	public static void onClientTick(final TickEvent.ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
		{
			return;
		}

		final ClientPlayerEntity player = Minecraft.getInstance().player;

		if (player != null && player.world != null)
		{
			AetherMusicManager.INSTANCE.update(PlayerAether.getPlayer(player));
		}
	}
}
