package com.gildedgames.aether.client.renderer.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public abstract class RenderLivingChild<T extends EntityLiving> extends RenderLiving<T>
{
    private final ModelBase childModel;

    private final ResourceLocation adultTexture, childTexture;

    public RenderLivingChild(RenderManager rendermanagerIn, ModelBase adultModel, ModelBase childModel, ResourceLocation adultTexture, ResourceLocation childTexture, float shadowsizeIn)
    {
        super(rendermanagerIn, adultModel, shadowsizeIn);

        this.childModel = childModel;
        this.adultTexture = adultTexture;
        this.childTexture = childTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
        return !entity.isChild() ? adultTexture : childTexture;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ModelBase prev = this.mainModel;

        if (entity.isChild())
        {
            this.mainModel = this.childModel;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        this.mainModel = prev;
    }
}
