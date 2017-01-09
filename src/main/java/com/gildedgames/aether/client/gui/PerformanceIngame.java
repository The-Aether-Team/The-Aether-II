package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PerformanceIngame extends Gui
{
	private static TextureAtlasSprite SERVER_STALL_ICON;

	private float pulse = -1.0f;

	private long pulseUntil;

	private long lastTickSystemTime;

	private int lastWorldTick;

	public void draw()
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.isGamePaused())
		{
			return;
		}

		this.update();

		ScaledResolution res = new ScaledResolution(mc);

		if (System.currentTimeMillis() < this.pulseUntil)
		{
			this.pulse += 0.02f * mc.getRenderPartialTicks();

			if (this.pulse > 1.0f)
			{
				this.pulse = -1.0f;
			}
		}
		else
		{
			this.pulse = Math.abs(this.pulse);

			this.pulse -= 0.05f * mc.getRenderPartialTicks();

			if (this.pulse < 0.0f)
			{
				this.pulse = 0.0f;
			}
		}

		float alpha = Math.abs(this.pulse);

		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.enableBlend();
		GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);

		this.drawTexturedModalRect(res.getScaledWidth() - 24, 8, SERVER_STALL_ICON, 16, 16);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	private void update()
	{
		int tickCounter = FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter();

		if (tickCounter > this.lastWorldTick)
		{
			this.lastWorldTick = tickCounter;
			this.lastTickSystemTime = System.currentTimeMillis();
		}

		long lag = System.currentTimeMillis() - this.lastTickSystemTime;

		if (lag > 100)
		{
			this.pulseUntil = System.currentTimeMillis() + 500;
		}
	}

	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		SERVER_STALL_ICON = event.getMap().registerSprite(AetherCore.getResource("gui/overlay/server_stall"));
	}
}
