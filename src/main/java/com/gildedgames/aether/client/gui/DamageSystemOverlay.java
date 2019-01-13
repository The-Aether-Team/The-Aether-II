package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
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

		int slashLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue() + this.slashModifier;
		int pierceLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getAttributeValue() + this.pierceModifier;
		int impactLevel = (int) mc.player.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue() + this.impactModifier;

		if (slashLevel > 0 || pierceLevel > 0 || impactLevel > 0)
		{
			this.renderDamageType(mc, slashLevel, SLASH_ICON, res.getScaledWidth() - 24.0f, res.getScaledHeight() - 24.0F);
			this.renderDamageType(mc, pierceLevel, PIERCE_ICON, res.getScaledWidth() - 24.0f, res.getScaledHeight() - 44.0F);
			this.renderDamageType(mc, impactLevel, IMPACT_ICON, res.getScaledWidth() - 24.0f, res.getScaledHeight() - 64.0F);
		}
	}

	private void renderDamageType(Minecraft mc, int damageLevel, ResourceLocation texture, float x, float y)
	{
		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableRescaleNormal();

		mc.getTextureManager().bindTexture(texture);

		GlStateManager.translate(x, y, 0.0f);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 14, 14, 14, 14);

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();

		this.drawString(mc.fontRenderer, String.valueOf(damageLevel), (int) x - 10 - mc.fontRenderer.getStringWidth(String.valueOf(damageLevel)), (int) y + 3,
				0xFFFFFFFF);

		GlStateManager.popMatrix();
	}
}
