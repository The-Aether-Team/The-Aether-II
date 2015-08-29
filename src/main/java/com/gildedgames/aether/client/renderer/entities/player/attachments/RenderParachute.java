package com.gildedgames.aether.client.renderer.entities.player.attachments;

import com.gildedgames.aether.client.models.player.attachments.ModelParachute;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.consumables.ItemCloudParachute;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.abilites.AbilityParachute;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderParachute
{
	private final ModelParachute modelParachute = new ModelParachute();

	public void renderOnPlayer(RenderManager renderManager, PlayerAether aePlayer, float partialTicks)
	{
		AbilityParachute ability = aePlayer.getAbilityParachute();
		EntityPlayer player = aePlayer.getProfile().getEntity();

		GlStateManager.pushMatrix();

		GlStateManager.rotate(-180F, 0.005F, 0F, 10F);

		float angle = player.prevRenderYawOffset + ((player.renderYawOffset - player.prevRenderYawOffset)) * partialTicks;

		GlStateManager.rotate(angle, 0F, player.rotationYaw, 0F);
		GlStateManager.translate(0.0f, -1.45f, 0f);

		renderManager.renderEngine.bindTexture(this.getParachuteTexture(ability.getParachuteType()));

		this.modelParachute.draw(0.0625f);

		GlStateManager.popMatrix();
	}

	private ResourceLocation getParachuteTexture(ItemCloudParachute.ParachuteType type)
	{
		return new ResourceLocation(AetherCore.getResourcePath("textures/entities/player/attachments/cloud_parachute/"
				+ type.name().toLowerCase() + "_parachute.png"));
	}
}
