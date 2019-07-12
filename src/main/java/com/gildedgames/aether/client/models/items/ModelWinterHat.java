package com.gildedgames.aether.client.models.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

/**
 * winter_hat - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelWinterHat extends ModelBiped {
    public RendererModel base;
    public RendererModel main;
    public RendererModel end;
    public RendererModel bauble;

    public ModelWinterHat() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.end = new RendererModel(this, 5, 6);
        this.end.setRotationPoint(1.5F, -6.0F, 0.0F);
        this.end.addBox(0.0F, 0.0F, -1.5F, 6, 3, 3, 0.0F);
        this.setRotateAngle(end, 0.0F, 0.0F, -0.2617993877991494F);
        this.base = new RendererModel(this, 0, 23);
        this.base.setRotationPoint(1.0F, -8.0F, 0.5F);
        this.base.addBox(-3.5F, -2.0F, -3.5F, 7, 2, 7, 0.0F);
        this.setRotateAngle(base, 0.0F, 0.0F, 0.08726646259971647F);
        this.bauble = new RendererModel(this, 8, 0);
        this.bauble.setRotationPoint(6.0F, 2.9F, 0.0F);
        this.bauble.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(bauble, 0.0F, 0.7853981633974483F, -0.17453292519943295F);
        this.main = new RendererModel(this, 4, 12);
        this.main.setRotationPoint(-2.7F, -2.0F, 0.0F);
        this.main.addBox(0.0F, -6.0F, -2.5F, 5, 6, 5, 0.0F);
        this.setRotateAngle(main, 0.0F, 0.0F, 0.3490658503988659F);
        this.main.addChild(this.end);
        this.end.addChild(this.bauble);
        this.base.addChild(this.main);
        this.bipedHeadwear.addChild(this.base);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        bipedHead.isHidden=true;
        this.base.offsetY = 0.05F;
        this.base.offsetZ = 0.1F;
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void renderRaw(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.base.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
