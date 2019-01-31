package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.living.passive.EntityGlactrix;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * NewProject - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelGlactrix extends ModelBase {
    public ModelRenderer body_main;
    public ModelRenderer body_left;
    public ModelRenderer body_right;
    public ModelRenderer body_back;
    public ModelRenderer leg_front_right;
    public ModelRenderer leg_back_left_1;
    public ModelRenderer leg_back_left_1_1;
    public ModelRenderer body_front;
    public ModelRenderer leg_front_left;
    public ModelRenderer head;
    public ModelRenderer leg_back_left_2;
    public ModelRenderer leg_back_left_2_1;
    public ModelRenderer ear_right;
    public ModelRenderer ear_left;
    public ModelRenderer snout;
    public ModelRenderer nose;
    public ModelRenderer head_top;

    public ModelGlactrix() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_back_left_2_1 = new ModelRenderer(this, 0, 0);
        this.leg_back_left_2_1.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.leg_back_left_2_1.addBox(-1.4F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(leg_back_left_2_1, -0.7853981633974483F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 0, 0);
        this.snout.setRotationPoint(0.0F, 0.6F, -1.8F);
        this.snout.addBox(-1.5F, -2.0F, -2.3F, 3, 2, 3, 0.0F);
        this.setRotateAngle(snout, 0.3490658503988659F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setRotationPoint(0.0F, -0.5F, -4.4F);
        this.nose.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(nose, 0.0F, 0.0F, 0.8028514559173915F);
        this.body_left = new ModelRenderer(this, 0, 0);
        this.body_left.setRotationPoint(4.8F, 2.0F, 0.0F);
        this.body_left.addBox(-2.0F, -1.0F, -4.1F, 3, 3, 9, 0.0F);
        this.setRotateAngle(body_left, 0.0F, 0.0F, 0.7853981633974483F);
        this.body_main = new ModelRenderer(this, 0, 0);
        this.body_main.setRotationPoint(0.0F, 18.5F, 0.0F);
        this.body_main.addBox(-4.0F, -1.0F, -4.2F, 8, 5, 9, 0.0F);
        this.body_right = new ModelRenderer(this, 0, 0);
        this.body_right.setRotationPoint(-4.8F, 2.0F, 0.0F);
        this.body_right.addBox(-1.0F, -2.0F, -4.1F, 3, 3, 9, 0.0F);
        this.setRotateAngle(body_right, 0.0F, 0.0F, 0.7853981633974483F);
        this.head = new ModelRenderer(this, 22, 0);
        this.head.setRotationPoint(0.0F, 3.0F, -4.5F);
        this.head.addBox(-3.0F, -2.2F, -4.0F, 6, 3, 4, 0.0F);
        this.ear_right = new ModelRenderer(this, 16, 1);
        this.ear_right.mirror = true;
        this.ear_right.setRotationPoint(-3.0F, -2.2F, -1.5F);
        this.ear_right.addBox(-1.0F, -3.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(ear_right, 0.0F, 0.0F, -2.530727415391778F);
        this.ear_left = new ModelRenderer(this, 16, 1);
        this.ear_left.setRotationPoint(3.0F, -2.2F, -1.5F);
        this.ear_left.addBox(0.0F, -3.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(ear_left, 0.0F, 0.0F, 2.530727415391778F);
        this.head_top = new ModelRenderer(this, 0, 0);
        this.head_top.setRotationPoint(0.0F, -2.2F, -3.5F);
        this.head_top.addBox(-2.5F, -3.0F, 0.0F, 5, 3, 2, 0.0F);
        this.setRotateAngle(head_top, -1.1344640137963142F, 0.0F, 0.0F);
        this.leg_front_right = new ModelRenderer(this, 0, 0);
        this.leg_front_right.setRotationPoint(-3.5F, 2.5F, -2.5F);
        this.leg_front_right.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.leg_back_left_1 = new ModelRenderer(this, 0, 0);
        this.leg_back_left_1.setRotationPoint(4.0F, 2.5F, 2.0F);
        this.leg_back_left_1.addBox(-0.5F, -0.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leg_back_left_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.body_back = new ModelRenderer(this, 0, 0);
        this.body_back.setRotationPoint(-4.0F, 2.0F, 5.6F);
        this.body_back.addBox(0.0F, -2.0F, -2.0F, 8, 3, 3, 0.0F);
        this.setRotateAngle(body_back, 0.7853981633974483F, 0.0F, 0.0F);
        this.body_front = new ModelRenderer(this, 0, 0);
        this.body_front.setRotationPoint(-3.0F, 2.0F, -3.4F);
        this.body_front.addBox(0.0F, -2.0F, -2.0F, 6, 3, 3, 0.0F);
        this.setRotateAngle(body_front, 0.7853981633974483F, 0.0F, 0.0F);
        this.leg_front_left = new ModelRenderer(this, 0, 0);
        this.leg_front_left.setRotationPoint(3.5F, 2.5F, -2.5F);
        this.leg_front_left.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.leg_back_left_2 = new ModelRenderer(this, 0, 0);
        this.leg_back_left_2.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.leg_back_left_2.addBox(-0.6F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(leg_back_left_2, -0.7853981633974483F, 0.0F, 0.0F);
        this.leg_back_left_1_1 = new ModelRenderer(this, 0, 0);
        this.leg_back_left_1_1.setRotationPoint(-4.0F, 2.5F, 2.0F);
        this.leg_back_left_1_1.addBox(-1.5F, -0.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leg_back_left_1_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.leg_back_left_1_1.addChild(this.leg_back_left_2_1);
        this.head.addChild(this.snout);
        this.head.addChild(this.nose);
        this.body_main.addChild(this.body_left);
        this.body_main.addChild(this.body_right);
        this.body_main.addChild(this.head);
        this.head.addChild(this.ear_right);
        this.head.addChild(this.ear_left);
        this.head.addChild(this.head_top);
        this.body_main.addChild(this.leg_front_right);
        this.body_main.addChild(this.leg_back_left_1);
        this.body_main.addChild(this.body_back);
        this.body_main.addChild(this.body_front);
        this.body_main.addChild(this.leg_front_left);
        this.leg_back_left_1.addChild(this.leg_back_left_2);
        this.body_main.addChild(this.leg_back_left_1_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof EntityGlactrix)
        {
            EntityGlactrix glactrix = (EntityGlactrix)entity;

            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();

            if (glactrix.getIsToppled())
            {
                GlStateManager.rotate(180F, 0, 0, 1.0F);
                GlStateManager.translate(0, -2.3F, 0);
            }
            this.body_main.render(f5);

            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
        }

    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
