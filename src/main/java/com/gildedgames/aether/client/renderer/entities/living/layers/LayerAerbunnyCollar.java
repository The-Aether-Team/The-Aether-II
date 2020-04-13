package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelAerbunny;
import com.gildedgames.aether.client.renderer.entities.living.RenderAerbunny;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityAerbunny;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAerbunnyCollar implements LayerRenderer<EntityAerbunny>
{
    private static final ResourceLocation COLLAR = AetherCore.getResource("textures/entities/aerbunny/aerbunny_collar.png");

    private final RenderAerbunny renderer;

    public LayerAerbunnyCollar(RenderAerbunny renderer)
    {
        this.renderer = renderer;
    }

    public void doRenderLayer(EntityAerbunny entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible())
        {
            this.renderer.bindTexture(COLLAR);

            float[] afloat = entitylivingbaseIn.getCollarColor().getColorComponentValues();
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);

            if (this.renderer.getMainModel() instanceof ModelAerbunny)
            {
                ModelAerbunny model = (ModelAerbunny) this.renderer.getMainModel();

                GlStateManager.scale(1.01F, 1.015F, 1.01F);
                GlStateManager.translate(0.0F, -0.0001F, -0.0001F);

                model.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                model.render2(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
