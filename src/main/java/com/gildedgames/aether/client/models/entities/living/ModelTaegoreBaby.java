package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * babytaegore - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelTaegoreBaby extends ModelBaseAether {
    public ModelRenderer body_main;
    public ModelRenderer tail;
    public ModelRenderer plate_f_l;
    public ModelRenderer head;
    public ModelRenderer leg_f_l;
    public ModelRenderer leg_f_r;
    public ModelRenderer leg_b_l;
    public ModelRenderer leg_b_r;
    public ModelRenderer plate_f_r;
    public ModelRenderer plate_b_l;
    public ModelRenderer plate_b_r;
    public ModelRenderer ears;
    public ModelRenderer snout;

    public ModelTaegoreBaby() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_f_l = new ModelRenderer(this, 56, 17);
        this.leg_f_l.setRotationPoint(2.2F, 1.0F, -0.9F);
        this.leg_f_l.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.ears = new ModelRenderer(this, 0, 0);
        this.ears.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.ears.addBox(-4.5F, -2.5F, -3.2F, 9, 2, 1, 0.0F);
        this.leg_b_l = new ModelRenderer(this, 56, 9);
        this.leg_b_l.setRotationPoint(2.2F, 1.0F, 5.5F);
        this.leg_b_l.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.snout = new ModelRenderer(this, 1, 14);
        this.snout.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.snout.addBox(-1.5F, -0.1F, -4.4F, 3, 3, 1, 0.0F);
        this.leg_b_r = new ModelRenderer(this, 48, 9);
        this.leg_b_r.setRotationPoint(-2.2F, 1.0F, 5.5F);
        this.leg_b_r.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.plate_b_r = new ModelRenderer(this, 22, 0);
        this.plate_b_r.setRotationPoint(0.0F, 1.0F, 6.0F);
        this.plate_b_r.addBox(-4.0F, -3.0F, -3.0F, 1, 5, 4, 0.0F);
        this.setRotateAngle(plate_b_r, 0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 0, 27);
        this.tail.setRotationPoint(0.0F, -2.0F, 7.5F);
        this.tail.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
        this.setRotateAngle(tail, -1.0297442586766543F, 0.0F, 0.0F);
        this.plate_f_l = new ModelRenderer(this, 42, 0);
        this.plate_f_l.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.plate_f_l.addBox(3.0F, -3.0F, -3.0F, 1, 5, 4, 0.0F);
        this.setRotateAngle(plate_f_l, -0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.plate_f_r = new ModelRenderer(this, 32, 0);
        this.plate_f_r.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.plate_f_r.addBox(-4.0F, -3.0F, -3.0F, 1, 5, 4, 0.0F);
        this.setRotateAngle(plate_f_r, -0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.head = new ModelRenderer(this, 0, 3);
        this.head.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.head.addBox(-3.0F, -3.0F, -3.4F, 6, 6, 5, 0.0F);
        this.leg_f_r = new ModelRenderer(this, 48, 17);
        this.leg_f_r.setRotationPoint(-2.2F, 1.0F, -0.9F);
        this.leg_f_r.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.plate_b_l = new ModelRenderer(this, 52, 0);
        this.plate_b_l.setRotationPoint(0.0F, 1.0F, 6.0F);
        this.plate_b_l.addBox(3.0F, -3.0F, -3.0F, 1, 5, 4, 0.0F);
        this.setRotateAngle(plate_b_l, 0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.body_main = new ModelRenderer(this, 14, 14);
        this.body_main.setRotationPoint(0.0F, 18.0F, -0.5F);
        this.body_main.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 11, 0.0F);
        this.body_main.addChild(this.leg_f_l);
        this.head.addChild(this.ears);
        this.body_main.addChild(this.leg_b_l);
        this.head.addChild(this.snout);
        this.body_main.addChild(this.leg_b_r);
        this.body_main.addChild(this.plate_b_r);
        this.body_main.addChild(this.tail);
        this.body_main.addChild(this.plate_f_l);
        this.body_main.addChild(this.plate_f_r);
        this.body_main.addChild(this.head);
        this.body_main.addChild(this.leg_f_r);
        this.body_main.addChild(this.plate_b_l);
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

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.leg_f_l.rotateAngleX =  (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
        this.leg_f_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);

        this.leg_b_l.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
        this.leg_b_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
    }
}
