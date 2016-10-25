package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.google.common.base.Supplier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGlowing<T extends EntityLiving> implements LayerRenderer<T>
{
	private final Supplier<ResourceLocation> glowingLayer;
    private final RenderLiving<T> renderer;

    public LayerGlowing(RenderLiving<T> renderer, final ResourceLocation glowingLayer)
    {
        this(renderer, new Supplier<ResourceLocation>()
        {
            @Override public ResourceLocation get()
            {
                return glowingLayer;
            }
        });
    }

    public LayerGlowing(RenderLiving<T> renderer, Supplier<ResourceLocation> glowingLayer)
    {
        this.renderer = renderer;
        this.glowingLayer = glowingLayer;
    }

    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (this.glowingLayer.get() == null)
        {
            return;
        }

        this.renderer.bindTexture(this.glowingLayer.get());

        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

		GlStateManager.scale(1.001f, 1.001f, 1.001f);
		
		GlStateManager.depthMask(true);

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        i = entity.getBrightnessForRender(partialTicks);
        j = i % 65536;
        k = i / 65536;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.renderer.setLightmap(entity, partialTicks);

		GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
