package com.gildedgames.aether.client.models.entities.player;

import com.gildedgames.aether.client.models.entities.living.ModelSwetHead;
import com.gildedgames.aether.client.models.entities.living.ModelSwetJelly;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class LayerSwetLatch extends LayerBipedArmor
{
	private final RenderLivingBase<?> renderer;

	private final ModelSwetHead head;

	private final ModelSwetJelly jelly;

	public LayerSwetLatch(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);

		this.renderer = rendererIn;
		this.head = new ModelSwetHead();
		this.jelly = new ModelSwetJelly();
	}

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		if (entity instanceof EntityPlayer)
		{
			this.renderSwet(PlayerAether.getPlayer((EntityPlayer) entity), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	private void renderSwet(PlayerAether player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
			float netHeadYaw, float headPitch, float scale)
	{
		List<EntitySwet> swets = player.getModule(PlayerSwetTrackerModule.class).getLatchedSwets();

		for (int i = 0; i < swets.size(); i++)
		{
			GlStateManager.pushMatrix();
			EntitySwet swet = swets.get(i);
			float s = (float) Math.cos(ageInTicks / 2f) / 20f;

			GlStateManager.scale(.3f, .3f, .3f);

			if (i == 0)
			{
				GlStateManager.rotate(180, 0, 1, 0);
				GlStateManager.rotate(30, -1, 0, .2f);
				GlStateManager.translate(-0.1f, .2f, 1.2f);
			}
			else if (i == 1)
			{
				GlStateManager.rotate(180, 0, 1, 0);
				GlStateManager.rotate(30, 1, 0, -.2f);
				GlStateManager.translate(0.1f, .3f, -.2f);
			}
			else if (i == 2)
			{
				GlStateManager.rotate(30, 1, 0, .5f);
				GlStateManager.translate(0.1f, .3f, .2f);
			}

			this.renderer.bindTexture(swet.getType().texture_head);

			this.head.renderRaw(swet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			this.renderer.bindTexture(swet.getType().texture_jelly);

			GlStateManager.color(1.0F, 1.0F, 1.0F, .7F);
			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			float s2 = swet.getTimeSinceSucking() / 2000f;
			GlStateManager.scale(1 + s / 3f + s2, 1 + s / 5f + s2, 1 + s + s2);
			this.jelly.renderRaw(swet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

			GlStateManager.disableNormalize();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
}
