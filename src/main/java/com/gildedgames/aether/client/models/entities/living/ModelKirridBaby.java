package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.common.entities.animals.EntityKirrid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * babykirrid - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelKirridBaby extends ModelBaseAether
{
    public ModelRenderer body_main;
    public ModelRenderer tail;
    public ModelRenderer wool;
    public ModelRenderer head;
    public ModelRenderer leg_f_l;
    public ModelRenderer leg_f_r;
    public ModelRenderer leg_b_l;
    public ModelRenderer leg_b_r;
    public ModelRenderer plate;
    public ModelRenderer ears;

    public ModelKirridBaby() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wool = new ModelRenderer(this, 21, 0);
        this.wool.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.wool.addBox(-3.0F, -3.5F, -1.5F, 6, 5, 5, 0.0F);
        this.leg_f_l = new ModelRenderer(this, 54, 9);
        this.leg_f_l.setRotationPoint(1.7F, 1.0F, -0.9F);
        this.leg_f_l.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 10);
        this.head.setRotationPoint(0.0F, -0.2F, -2.0F);
        this.head.addBox(-2.0F, -5.1F, -4.7F, 4, 4, 6, 0.0F);
        this.setRotateAngle(head, 0.6373942428283291F, 0.0F, 0.0F);
        this.leg_b_l = new ModelRenderer(this, 54, 0);
        this.leg_b_l.setRotationPoint(1.7F, 1.0F, 5.5F);
        this.leg_b_l.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.leg_b_r = new ModelRenderer(this, 46, 0);
        this.leg_b_r.setRotationPoint(-1.7F, 1.0F, 5.5F);
        this.leg_b_r.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.tail = new ModelRenderer(this, 29, 25);
        this.tail.setRotationPoint(0.0F, -1.5F, 7.0F);
        this.tail.addBox(-0.5F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(tail, 0.5235987755982988F, 0.0F, 0.0F);
        this.leg_f_r = new ModelRenderer(this, 46, 9);
        this.leg_f_r.setRotationPoint(-1.7F, 1.0F, -0.9F);
        this.leg_f_r.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.plate = new ModelRenderer(this, 1, 0);
        this.plate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plate.addBox(-2.5F, -6.0F, -5.3F, 5, 5, 2, 0.0F);
        this.setRotateAngle(plate, -1.3203415791337103F, 0.0F, 0.0F);
        this.ears = new ModelRenderer(this, 0, 8);
        this.ears.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ears.addBox(-3.5F, -2.8F, 1.5F, 7, 1, 1, 0.0F);
        this.setRotateAngle(ears, 0.5235987755982988F, 0.0F, 0.0F);
        this.body_main = new ModelRenderer(this, 17, 10);
        this.body_main.setRotationPoint(0.0F, 17.0F, -0.5F);
        this.body_main.addBox(-2.5F, -2.5F, -3.0F, 5, 5, 10, 0.0F);
        this.body_main.addChild(this.wool);
        this.body_main.addChild(this.leg_f_l);
        this.body_main.addChild(this.head);
        this.body_main.addChild(this.leg_b_l);
        this.body_main.addChild(this.leg_b_r);
        this.body_main.addChild(this.tail);
        this.body_main.addChild(this.leg_f_r);
        this.head.addChild(this.plate);
        this.head.addChild(this.ears);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body_main.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
                                  float scaleFactor, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        this.tail.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.leg_f_l.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
        this.leg_f_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);

        this.leg_b_l.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
        this.leg_b_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        this.head.rotationPointY = -0.2F + ((EntityKirrid) entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 2.0F;
        this.head.rotateAngleX = ((EntityKirrid) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }
}
