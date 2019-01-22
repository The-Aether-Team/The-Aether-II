package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class EffectSystemOverlay extends Gui
{
	private static final ResourceLocation BAR_OUTLINE = AetherCore.getResource("textures/gui/overlay/effects/bar_outline.png");
	private static final ResourceLocation RED_BAR = AetherCore.getResource("textures/gui/overlay/effects/red_bar.png");

	private final int BAR_OUTLINE_TEXTURE_WIDTH = 22;
	private final int BAR_OUTLINE_TEXTURE_HEIGHT = 5;

	private final int BAR_TEXTURE_WIDTH = 20;
	private final int BAR_TEXTURE_HEIGHT = 3;

	public void renderIcons(Minecraft mc)
	{
		ScaledResolution res = new ScaledResolution(mc);

		if (mc.player.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
		{
			IAetherStatusEffectPool statusEffectPool = mc.player.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null);

			if (statusEffectPool != null)
			{
				// position is representative of the bar outlines, not the bars themselves.
				float xPos = (res.getScaledWidth() / 2.f) + 10.f; 	// add 1F to get bar x position.
				float yPos = (res.getScaledHeight() - 45.f);		// subtract 1F to get bar y position.
				int numOfEffectsDisplayed = 0;
				int barWidth = 0;

				for (IAetherStatusEffects effect : statusEffectPool.getPool().values())
				{
					if (effect == null)
					{
						continue;
					}

					if (effect.getBuildup() > 0)
					{
						++numOfEffectsDisplayed;
						this.renderBar(mc, BAR_OUTLINE, 22,5, this.BAR_OUTLINE_TEXTURE_WIDTH, this.BAR_OUTLINE_TEXTURE_HEIGHT, xPos*numOfEffectsDisplayed, yPos*numOfEffectsDisplayed);
						barWidth = effect.getBuildup();
						this.renderBar(mc, RED_BAR, barWidth, 3,
								this.BAR_TEXTURE_WIDTH, this.BAR_TEXTURE_HEIGHT, (xPos + 1F)*numOfEffectsDisplayed, (yPos - 1F)*numOfEffectsDisplayed);
					}

					if (effect.getIsEffectApplied())
					{

					}
				}
			}
		}
	}

	private void renderBar(Minecraft mc, ResourceLocation texture, int width, int height, int textureWidth, int textureHeight, float x, float y)
	{
		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableRescaleNormal();

		mc.getTextureManager().bindTexture(texture);
		GlStateManager.translate(x,y,0.0f);

		Gui.drawModalRectWithCustomSizedTexture(0,0,0, 0, width, height, textureWidth, textureHeight);

		GlStateManager.popMatrix();
	}
}
