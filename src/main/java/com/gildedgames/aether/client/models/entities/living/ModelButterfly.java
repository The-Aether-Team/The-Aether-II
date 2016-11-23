package com.gildedgames.aether.client.models.entities.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelButterfly extends ModelBase
{

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer bodyback;
    public ModelRenderer wing_1;
    public ModelRenderer wing_2;
    public ModelRenderer shape8;

    public ModelButterfly()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wing_1 = new ModelRenderer(this, 0, 0);
        this.wing_1.setRotationPoint(-3.3F, 22.3F, 1.0F);
        this.wing_1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(wing_1, 3.141592653589793F, 0.0F, -0.41887902047863906F);
        this.shape8 = new ModelRenderer(this, 16, 0);
        this.shape8.setRotationPoint(-1.8F, 21.6F, -0.5F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 3, 2, 2, 0.0F);
        this.setRotateAngle(shape8, 0.0F, 0.0F, -0.13962634015954636F);
        this.wing_2 = new ModelRenderer(this, 0, 0);
        this.wing_2.setRotationPoint(-3.3F, 22.3F, 0.0F);
        this.wing_2.addBox(0.0F, 0.0F, 0.0F, 5, 5, 0, 0.0F);
        this.setRotateAngle(wing_2, -3.141592653589793F, 0.0F, -0.41887902047863906F);
        this.head = new ModelRenderer(this, 10, 0);
        this.head.setRotationPoint(1.0F, 20.0F, 0.0F);
        this.head.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.bodyback = new ModelRenderer(this, 14, 0);
        this.bodyback.setRotationPoint(-2.4F, 22.4F, 0.0F);
        this.bodyback.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(bodyback, 0.0F, 0.0F, -1.0927506446736497F);
        this.body = new ModelRenderer(this, 24, 0);
        this.body.setRotationPoint(-1.8F, 21.4F, 0.0F);
        this.body.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(body, 0.0F, 0.0F, -0.40980330836826856F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.wing_1.offsetX, this.wing_1.offsetY, this.wing_1.offsetZ);
        GlStateManager.translate(this.wing_1.rotationPointX * f5, this.wing_1.rotationPointY * f5, this.wing_1.rotationPointZ * f5);
        GlStateManager.scale(1.5D, 1.5D, 1.5D);
        GlStateManager.translate(-this.wing_1.offsetX, -this.wing_1.offsetY, -this.wing_1.offsetZ);
        GlStateManager.translate(-this.wing_1.rotationPointX * f5, -this.wing_1.rotationPointY * f5, -this.wing_1.rotationPointZ * f5);
        this.wing_1.render(f5);
        GlStateManager.popMatrix();
        this.shape8.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.wing_2.offsetX, this.wing_2.offsetY, this.wing_2.offsetZ);
        GlStateManager.translate(this.wing_2.rotationPointX * f5, this.wing_2.rotationPointY * f5, this.wing_2.rotationPointZ * f5);
        GlStateManager.scale(1.4D, 1.5D, 1.5D);
        GlStateManager.translate(-this.wing_2.offsetX, -this.wing_2.offsetY, -this.wing_2.offsetZ);
        GlStateManager.translate(-this.wing_2.rotationPointX * f5, -this.wing_2.rotationPointY * f5, -this.wing_2.rotationPointZ * f5);
        this.wing_2.render(f5);
        GlStateManager.popMatrix();
        this.head.render(f5);
        this.bodyback.render(f5);
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        this.wing_1.rotateAngleX = -f2;
        this.wing_2.rotateAngleX = f2;
    }
}
