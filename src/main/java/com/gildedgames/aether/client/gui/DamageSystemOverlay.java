package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class DamageSystemOverlay extends Gui
{
	private static final ResourceLocation SLASH_ICON = AetherCore.getResource("textures/gui/overlay/slash_damage.png");

	private static final ResourceLocation PIERCE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_damage.png");

	private static final ResourceLocation IMPACT_ICON = AetherCore.getResource("textures/gui/overlay/impact_damage.png");

	private static final ResourceLocation SLASH_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/slash_defense.png");

	private static final ResourceLocation PIERCE_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/pierce_defense.png");

	private static final ResourceLocation IMPACT_DEFENSE_ICON = AetherCore.getResource("textures/gui/overlay/impact_defense.png");

	private int slashModifier, pierceModifier, impactModifier;

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

	public void renderIcons(Minecraft mc)
	{
		ScaledResolution res = new ScaledResolution(mc);

		int slashDefenseLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
		int pierceDefenseLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();
		int impactDefenseLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();

		boolean renderingDefense = slashDefenseLevel > 0 || pierceDefenseLevel > 0 || impactDefenseLevel > 0;

		if (renderingDefense)
		{
			this.renderDamageType(mc, slashDefenseLevel, SLASH_DEFENSE_ICON, 12.0f, res.getScaledHeight() - 24.0F, true);
			this.renderDamageType(mc, pierceDefenseLevel, PIERCE_DEFENSE_ICON, 12.0f, res.getScaledHeight() - 44.0F, true);
			this.renderDamageType(mc, impactDefenseLevel, IMPACT_DEFENSE_ICON, 12.0f, res.getScaledHeight() - 64.0F, true);
		}

		int slashLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue() + this.slashModifier;
		int pierceLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getAttributeValue() + this.pierceModifier;
		int impactLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue() + this.impactModifier;

		float xOffset = 0;

		if (slashLevel > 0 || pierceLevel > 0 || impactLevel > 0)
		{
			this.renderDamageType(mc, slashLevel, SLASH_ICON, res.getScaledWidth() - 12.0f + xOffset, res.getScaledHeight() - 24.0F, false);
			this.renderDamageType(mc, pierceLevel, PIERCE_ICON, res.getScaledWidth() - 12.0f + xOffset, res.getScaledHeight() - 44.0F, false);
			this.renderDamageType(mc, impactLevel, IMPACT_ICON, res.getScaledWidth() - 12.0f + xOffset, res.getScaledHeight() - 64.0F, false);
		}
	}

	private void renderDamageType(Minecraft mc, int level, ResourceLocation texture, float x, float y, boolean reverseIconAndText)
	{
		int textWidth = mc.fontRenderer.getStringWidth(String.valueOf(level));

		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableRescaleNormal();

		mc.getTextureManager().bindTexture(texture);

		GlStateManager.translate(x - 7, y, 0.0f);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 14, 14, 14, 14);

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();

		this.drawString(mc.fontRenderer, String.valueOf(level),
				(int) (x - 7) + (!reverseIconAndText ? -10 : 10) + ((reverseIconAndText ? 2 : -1) * textWidth),
				(int) y + 3,
				0xFFFFFFFF);

		GlStateManager.popMatrix();
	}
}
