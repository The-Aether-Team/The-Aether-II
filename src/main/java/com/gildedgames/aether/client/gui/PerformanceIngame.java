package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.text.DecimalFormat;

public class PerformanceIngame extends Gui
{
	private static final DecimalFormat FORMATTER = new DecimalFormat("#.#");

	private static TextureAtlasSprite SERVER_STALL_ICON;

	private float alpha = -1.0f;

	private long warnUntil;

	private long lastTickSystemTime;

	private long lag;

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

		if (System.currentTimeMillis() < this.warnUntil)
		{
			this.alpha += 0.02f * mc.getRenderPartialTicks();

			if (this.alpha > 1.0f)
			{
				this.alpha = 1.0f;
			}
		}
		else
		{
			this.alpha -= 0.02f * mc.getRenderPartialTicks();

			if (this.alpha < 0.0f)
			{
				this.alpha = 0.0f;
			}
		}

		if (this.alpha > 0.0f)
		{
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			GlStateManager.enableBlend();

			if (this.lag > 1000)
			{
				GlStateManager.color(255 / 255.0f, 78 / 255.0f, 41 / 255.0f, this.alpha);
			}
			else if (this.lag > 500)
			{
				GlStateManager.color(255 / 255.0f, 137 / 255.0f, 41 / 255.0f, this.alpha);
			}
			else
			{
				GlStateManager.color(255 / 255.0f, 198 / 255.0f, 41 / 255.0f, this.alpha);
			}

			this.drawTexturedModalRect(res.getScaledWidth() - 24, 8, SERVER_STALL_ICON, 16, 16);

			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	private void update()
	{
		int tickCounter = FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter();

		if (tickCounter > this.lastWorldTick)
		{
			this.lastWorldTick = tickCounter;
			this.lastTickSystemTime = System.currentTimeMillis();
		}

		this.lag = System.currentTimeMillis() - this.lastTickSystemTime;

		if (this.lag > 150)
		{
			this.warnUntil = System.currentTimeMillis() + 500;
		}
	}

	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		SERVER_STALL_ICON = event.getMap().registerSprite(AetherCore.getResource("gui/overlay/server_stall"));
	}
}
