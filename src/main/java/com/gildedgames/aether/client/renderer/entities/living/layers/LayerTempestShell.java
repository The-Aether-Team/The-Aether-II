package com.gildedgames.aether.client.renderer.entities.living.layers;

import com.gildedgames.aether.client.models.entities.living.ModelTempestShell;
import com.gildedgames.aether.common.entities.animals.EntitySheepuff;
import com.gildedgames.aether.common.entities.monsters.EntityTempest;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Resource;

public class LayerTempestShell implements LayerRenderer<EntityTempest> {

    public ModelTempestShell model;

    public ResourceLocation texture;

    public RenderLiving<EntityTempest> renderer;

    public LayerTempestShell(RenderLiving<EntityTempest> renderer, ResourceLocation texture, ModelTempestShell model) {
        this.texture = texture;
        this.model = model;
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityTempest temp, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
    {
        if (!temp.isInvisible())
        {
            GlStateManager.scale(1.01F, 1.01F, 1.01F);

            this.renderer.bindTexture(texture);

            this.model.setLivingAnimations(temp, p_177141_2_, p_177141_3_, p_177141_4_);
            this.model.render(temp, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
