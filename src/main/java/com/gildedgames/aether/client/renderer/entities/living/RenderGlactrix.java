package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelGlactrix;
import com.gildedgames.aether.client.renderer.EyeUtil;
import com.gildedgames.aether.client.renderer.entities.living.layers.LayerGlactrixCrystals;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.passive.EntityGlactrix;
import io.netty.handler.codec.spdy.SpdyHeaderBlockRawDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderGlactrix extends RenderLiving<EntityGlactrix>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/glactrix/glactrix.png");

	public RenderGlactrix(RenderManager renderManager)
	{
		super(renderManager, new ModelGlactrix(), 0.4f);

		this.addLayer(new LayerGlactrixCrystals(this));
	}

	@Override
	protected void preRenderCallback(EntityGlactrix entity, float partialTicks)
	{
		float scale = 1F;

		GlStateManager.scale(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGlactrix entity)
	{
		return TEXTURE;
	}

	@Override
	protected void renderModel(EntityGlactrix entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		GlStateManager.pushMatrix();

		if (entity.getIsToppled())
		{
			GlStateManager.rotate(180F , 0, 0, 1F);
			GlStateManager.translate(0, -2.3F, 0);
			GlStateManager.rotate(MathHelper.cos((ageInTicks % 100) / 4) * 10, 0, 0F, 1.0F);
			GlStateManager.rotate((ageInTicks % 100) / 100f * 360, 0, 1.0F, 0F);
		}

		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GlStateManager.popMatrix();
	}

}
