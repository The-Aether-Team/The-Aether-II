package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class LayerGlowing<T extends MobEntity, M extends EntityModel<T>> extends LayerRenderer<T, M>
{
	private final Supplier<ResourceLocation> glowingLayer;

	private final LivingRenderer<T, M> renderer;

	public LayerGlowing(final LivingRenderer<T, M> renderer, final ResourceLocation glowingLayer)
	{
		this(renderer, () -> glowingLayer);
	}

	public LayerGlowing(final LivingRenderer<T, M> renderer, final Supplier<ResourceLocation> glowingLayer)
	{
		super(renderer);

		this.renderer = renderer;
		this.glowingLayer = glowingLayer;
	}

	@Override
	public void render(final T entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks,
			final float netHeadYaw,
			final float headPitch, final float scale)
	{
		if (this.glowingLayer.get() == null)
		{
			return;
		}

		this.renderer.bindTexture(this.glowingLayer.get());

		GlStateManager.enableBlend();
		GlStateManager.disableAlphaTest();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

		GlStateManager.scalef(1.001f, 1.001f, 1.001f);

		GlStateManager.depthMask(true);
		GlStateManager.disableLighting();

		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.renderer.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		i = entity.getBrightnessForRender();
		j = i % 65536;
		k = i / 65536;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		this.renderer.setLightmap(entity);

		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.enableAlphaTest();
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
