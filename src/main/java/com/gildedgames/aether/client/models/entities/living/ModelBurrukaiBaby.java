package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.ModelBaseAether;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * babyburrukai - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelBurrukaiBaby extends ModelBaseAether {
    public ModelRenderer body_main;
    public ModelRenderer tail;
    public ModelRenderer wool;
    public ModelRenderer head;
    public ModelRenderer leg_f_l;
    public ModelRenderer leg_f_r;
    public ModelRenderer leg_b_l;
    public ModelRenderer leg_b_r;
    public ModelRenderer wool_1;
    public ModelRenderer plate;
    public ModelRenderer ears;

    public ModelBurrukaiBaby() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wool = new ModelRenderer(this, 33, 0);
        this.wool.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.wool.addBox(2.5F, -4.0F, -2.5F, 2, 5, 4, 0.0F);
        this.setRotateAngle(wool, 0.008028514559173916F, 0.0F, 0.0F);
        this.plate = new ModelRenderer(this, 1, 0);
        this.plate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plate.addBox(-2.0F, -2.6F, -4.2F, 4, 3, 2, 0.0F);
        this.setRotateAngle(plate, -1.3089969389957472F, 0.0F, 0.0F);
        this.leg_b_l = new ModelRenderer(this, 56, 0);
        this.leg_b_l.setRotationPoint(2.7F, 1.0F, 6.5F);
        this.leg_b_l.addBox(-1.0F, -1.0F, -1.5F, 2, 9, 2, 0.0F);
        this.leg_f_l = new ModelRenderer(this, 56, 11);
        this.leg_f_l.setRotationPoint(2.7F, 1.0F, -0.9F);
        this.leg_f_l.addBox(-0.5F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
        this.wool_1 = new ModelRenderer(this, 21, 0);
        this.wool_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.wool_1.addBox(-4.5F, -4.0F, -2.5F, 2, 5, 4, 0.0F);
        this.body_main = new ModelRenderer(this, 11, 10);
        this.body_main.setRotationPoint(0.0F, 15.0F, -0.5F);
        this.body_main.addBox(-3.5F, -2.5F, -3.5F, 7, 6, 11, 0.0F);
        this.tail = new ModelRenderer(this, 28, 27);
        this.tail.setRotationPoint(0.0F, -2.0F, 7.5F);
        this.tail.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(tail, -0.8726646259971648F, 0.0F, 0.0F);
        this.leg_b_r = new ModelRenderer(this, 48, 0);
        this.leg_b_r.setRotationPoint(-2.7F, 1.0F, 6.5F);
        this.leg_b_r.addBox(-1.0F, -1.0F, -1.5F, 2, 9, 2, 0.0F);
        this.ears = new ModelRenderer(this, 0, 6);
        this.ears.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ears.addBox(-4.0F, -2.0F, 2.3F, 8, 2, 1, 0.0F);
        this.setRotateAngle(ears, 1.48352986419518F, 0.0F, 0.0F);
        this.leg_f_r = new ModelRenderer(this, 48, 11);
        this.leg_f_r.setRotationPoint(-2.7F, 1.0F, -0.9F);
        this.leg_f_r.addBox(-1.5F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 10);
        this.head.setRotationPoint(0.0F, -1.5F, -3.0F);
        this.head.addBox(-2.5F, -3.7F, -5.4F, 5, 5, 6, 0.0F);
        this.body_main.addChild(this.wool);
        this.head.addChild(this.plate);
        this.body_main.addChild(this.leg_b_l);
        this.body_main.addChild(this.leg_f_l);
        this.body_main.addChild(this.wool_1);
        this.body_main.addChild(this.tail);
        this.body_main.addChild(this.leg_b_r);
        this.head.addChild(this.ears);
        this.body_main.addChild(this.leg_f_r);
        this.body_main.addChild(this.head);
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        this.tail.rotateAngleZ = -0.5096392150483358F + (MathHelper.cos(ageInTicks * 0.1662F) * 0.2F);

        float pitch = headPitch * 0.017453292F;
        float yaw = netHeadYaw * 0.017453292F;

        this.head.rotateAngleX = pitch;
        this.head.rotateAngleY = yaw;

        float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.75F * limbSwingAmount);
        float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.75F * limbSwingAmount);

        this.head.rotateAngleZ = leftSwingX * .1f;
        this.head.offsetY = leftSwingX * .015f;

        this.leg_f_l.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);
        this.leg_f_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);

        this.leg_b_l.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount);
        this.leg_b_r.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount);

        //hind leg animation code is lifting them up off the ground somehow

        this.tail.rotateAngleX = 0.515060975741379F + rightSwingX;
    }
}
