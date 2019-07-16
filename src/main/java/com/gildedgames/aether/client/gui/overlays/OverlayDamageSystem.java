package com.gildedgames.aether.client.gui.overlays;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class OverlayDamageSystem extends AbstractGui
{
	private static final ResourceLocation SLASH_ICON = AetherCore.getResource("textures/gui/overlay/slash_damage.png");

	private static final ResourceLocation PIERCE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_damage.png");

	private static final ResourceLocation IMPACT_ICON = AetherCore.getResource("textures/gui/overlay/impact_damage.png");

	private static final ResourceLocation SLASH_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/slash_defense.png");

	private static final ResourceLocation PIERCE_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_defense.png");

	private static final ResourceLocation IMPACT_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/impact_defense.png");

	private int slashModifier, pierceModifier, impactModifier;

	private final Minecraft minecraft;

	public OverlayDamageSystem(Minecraft minecraft)
	{
		this.minecraft = minecraft;
	}

	public void setSlashModifier(int modifier)
	{
		this.slashModifier = modifier;
	}

	public void setPierceModifier(int modifier)
	{
		this.pierceModifier = modifier;
	}

	public void setImpactModifier(int modifier)
	{
		this.impactModifier = modifier;
	}

	public void render()
	{
		int slashDefenseLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getValue();
		int pierceDefenseLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getValue();
		int impactDefenseLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getValue();

		boolean renderingDefense = slashDefenseLevel > 0 || pierceDefenseLevel > 0 || impactDefenseLevel > 0;

		if (renderingDefense)
		{
			this.renderDamageType(slashDefenseLevel, SLASH_DEFENSE_ICON, 12.0f, this.minecraft.mainWindow.getScaledHeight() - 24.0F, true);
			this.renderDamageType(pierceDefenseLevel, PIERCE_DEFENSE_ICON, 12.0f, this.minecraft.mainWindow.getScaledHeight() - 44.0F, true);
			this.renderDamageType(impactDefenseLevel, IMPACT_DEFENSE_ICON, 12.0f, this.minecraft.mainWindow.getScaledHeight() - 64.0F, true);
		}

		int slashLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getValue() + this.slashModifier;
		int pierceLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getValue() + this.pierceModifier;
		int impactLevel = (int) this.minecraft.player.getAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getValue() + this.impactModifier;

		float xOffset = 0;

		if (slashLevel > 0 || pierceLevel > 0 || impactLevel > 0)
		{
			this.renderDamageType(slashLevel, SLASH_ICON, this.minecraft.mainWindow.getScaledWidth() - 12.0f + xOffset, this.minecraft.mainWindow.getScaledHeight() - 24.0F, false);
			this.renderDamageType(pierceLevel, PIERCE_ICON, this.minecraft.mainWindow.getScaledWidth() - 12.0f + xOffset, this.minecraft.mainWindow.getScaledHeight() - 44.0F, false);
			this.renderDamageType(impactLevel, IMPACT_ICON, this.minecraft.mainWindow.getScaledWidth() - 12.0f + xOffset, this.minecraft.mainWindow.getScaledHeight() - 64.0F, false);
		}
	}

	private void renderDamageType(int level, ResourceLocation texture, float x, float y, boolean reverseIconAndText)
	{
		int textWidth = this.minecraft.fontRenderer.getStringWidth(String.valueOf(level));

		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableRescaleNormal();

		this.minecraft.getTextureManager().bindTexture(texture);

		GlStateManager.translatef(x - 7, y, 0.0f);

		AbstractGui.blit(0, 0, 0, 0, 14, 14, 14, 14);

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();

		this.drawString(this.minecraft.fontRenderer, String.valueOf(level),
				(int) (x - 7) + (!reverseIconAndText ? -10 : 10) + ((reverseIconAndText ? 2 : -1) * textWidth),
				(int) y + 3,
				0xFFFFFFFF);

		GlStateManager.popMatrix();
	}
}
