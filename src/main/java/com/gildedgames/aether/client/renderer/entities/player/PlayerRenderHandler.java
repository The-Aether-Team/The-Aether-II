package com.gildedgames.aether.client.renderer.entities.player;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerRenderHandler
{
	private RenderPlayerAether renderPlayerAether = new RenderPlayerAether();

	@SubscribeEvent
	public void onPlayerPreRender(RenderPlayerEvent.Pre event)
	{
		PlayerAether playerAether = PlayerAether.getPlayer(event.entityPlayer);

		if (playerAether != null)
		{
			this.renderPlayerAether.preRender(event.renderer.getRenderManager(), playerAether, event.partialRenderTick);
		}
	}

	@SubscribeEvent
	public void onPreClientTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			if (player != null)
			{
				PlayerAether playerAether = PlayerAether.getPlayer(player);

				if (playerAether != null)
				{
					this.renderPlayerAether.updateCamera(playerAether);
				}
			}
		}
	}
}
