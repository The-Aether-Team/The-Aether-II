package com.gildedgames.aether.client.renderer.entities.player;

import com.gildedgames.aether.client.renderer.entities.player.attachments.RenderParachute;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;

public class RenderPlayerAether
{
	private RenderParachute parachute = new RenderParachute();

	private boolean wasParachuting;

	private int lastCameraMode;

	public void preRender(RenderManager renderManager, PlayerAether aePlayer, float partialTicks)
	{
		if (aePlayer.getAbilityParachute().isOpen())
		{
			this.parachute.renderOnPlayer(renderManager, aePlayer, partialTicks);
		}
	}

	public void updateCamera(PlayerAether aePlayer)
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;

		if (aePlayer.getAbilityParachute().isOpen())
		{
			if (!this.wasParachuting)
			{
				this.lastCameraMode = settings.thirdPersonView;

				this.wasParachuting = true;
			}

			settings.thirdPersonView = 1;
		}
		else
		{
			if (this.wasParachuting)
			{
				settings.thirdPersonView = this.lastCameraMode;

				this.wasParachuting = false;
			}
		}
	}
}
