package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.common.entities.animals.EntityGlactrix;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * NewProject - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelGlactrix extends EntityModel<EntityGlactrix>
{
    public RendererModel body_main;
    public RendererModel body_left;
    public RendererModel body_right;
    public RendererModel body_back;
    public RendererModel leg_front_right;
    public RendererModel leg_back_left_1;
    public RendererModel leg_back_right_1;
    public RendererModel body_front;
    public RendererModel leg_front_left;
    public RendererModel head;
    public RendererModel leg_back_left_2;
    public RendererModel leg_back_right_2;
    public RendererModel ear_right;
    public RendererModel ear_left;
    public RendererModel snout;
    public RendererModel nose;
    public RendererModel head_top;

    public ModelGlactrix() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body_back = new RendererModel(this, 15, 14);
        this.body_back.setRotationPoint(4.0F, 2.0F, 4.9F);
        this.body_back.addBox(0.0F, -1.5F, -1.5F, 8, 3, 3, 0.0F);
        this.setRotateAngle(body_back, -2.356194490192345F, 0.0F, 3.141592653589793F);
        this.snout = new RendererModel(this, 34, 12);
        this.snout.setRotationPoint(0.0F, 0.6F, -1.8F);
        this.snout.addBox(-1.5F, -2.0F, -2.3F, 3, 2, 3, 0.0F);
        this.setRotateAngle(snout, 0.3490658503988659F, 0.0F, 0.0F);
        this.leg_front_left = new RendererModel(this, 0, 14);
        this.leg_front_left.setRotationPoint(3.5F, 2.5F, -2.5F);
        this.leg_front_left.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.leg_back_left_2 = new RendererModel(this, 24, 25);
        this.leg_back_left_2.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.leg_back_left_2.addBox(-0.6F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(leg_back_left_2, -0.7853981633974483F, 0.0F, 0.0F);
        this.nose = new RendererModel(this, 50, 0);
        this.nose.setRotationPoint(0.0F, -0.5F, -4.4F);
        this.nose.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(nose, 0.0F, 0.0F, 0.8028514559173915F);
        this.body_left = new RendererModel(this, 0, 14);
        this.body_left.setRotationPoint(4.8F, 2.0F, 0.0F);
        this.body_left.addBox(-2.0F, -1.0F, -4.1F, 3, 3, 9, 0.0F);
        this.setRotateAngle(body_left, 0.0F, 0.0F, 0.7853981633974483F);
        this.leg_back_left_1 = new RendererModel(this, 24, 20);
        this.leg_back_left_1.setRotationPoint(4.0F, 2.5F, 2.0F);
        this.leg_back_left_1.addBox(-0.5F, -0.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leg_back_left_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.leg_back_right_2 = new RendererModel(this, 24, 25);
        this.leg_back_right_2.mirror = true;
        this.leg_back_right_2.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.leg_back_right_2.addBox(-1.4F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(leg_back_right_2, -0.7853981633974483F, 0.0F, 0.0F);
        this.leg_back_right_1 = new RendererModel(this, 24, 20);
        this.leg_back_right_1.mirror = true;
        this.leg_back_right_1.setRotationPoint(-4.0F, 2.5F, 2.0F);
        this.leg_back_right_1.addBox(-1.5F, -0.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leg_back_right_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.leg_front_right = new RendererModel(this, 0, 14);
        this.leg_front_right.mirror = true;
        this.leg_front_right.setRotationPoint(-3.5F, 2.5F, -2.5F);
        this.leg_front_right.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.ear_right = new RendererModel(this, 45, 0);
        this.ear_right.setRotationPoint(-3.0F, -2.2F, -1.5F);
        this.ear_right.addBox(-1.0F, -3.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(ear_right, 0.0F, 0.0F, -2.530727415391778F);
        this.body_front = new RendererModel(this, 0, 26);
        this.body_front.setRotationPoint(-3.0F, 2.0F, -3.4F);
        this.body_front.addBox(0.0F, -2.0F, -2.0F, 6, 3, 3, 0.0F);
        this.setRotateAngle(body_front, 0.7853981633974483F, 0.0F, 0.0F);
        this.ear_left = new RendererModel(this, 45, 0);
        this.ear_left.mirror = true;
        this.ear_left.setRotationPoint(3.0F, -2.2F, -1.5F);
        this.ear_left.addBox(0.0F, -3.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(ear_left, 0.0F, 0.0F, 2.530727415391778F);
        this.body_main = new RendererModel(this, 0, 0);
        this.body_main.setRotationPoint(0.0F, 18.5F, 0.0F);
        this.body_main.addBox(-4.0F, -1.0F, -4.2F, 8, 5, 9, 0.0F);
        this.head_top = new RendererModel(this, 34, 7);
        this.head_top.setRotationPoint(0.0F, -2.2F, -3.5F);
        this.head_top.addBox(-2.5F, -3.0F, 0.0F, 5, 3, 2, 0.0F);
        this.setRotateAngle(head_top, -1.1344640137963142F, 0.0F, 0.0F);
        this.body_right = new RendererModel(this, 0, 14);
        this.body_right.mirror = true;
        this.body_right.setRotationPoint(-4.8F, 2.0F, 0.0F);
        this.body_right.addBox(-1.0F, -1.0F, -4.1F, 3, 3, 9, 0.0F);
        this.setRotateAngle(body_right, 0.0F, 0.0F, -0.7853981633974483F);
        this.head = new RendererModel(this, 25, 0);
        this.head.setRotationPoint(0.0F, 3.0F, -4.5F);
        this.head.addBox(-3.0F, -2.2F, -4.0F, 6, 3, 4, 0.0F);
        this.body_main.addChild(this.body_back);
        this.head.addChild(this.snout);
        this.body_main.addChild(this.leg_front_left);
        this.leg_back_left_1.addChild(this.leg_back_left_2);
        this.head.addChild(this.nose);
        this.body_main.addChild(this.body_left);
        this.body_main.addChild(this.leg_back_left_1);
        this.leg_back_right_1.addChild(this.leg_back_right_2);
        this.body_main.addChild(this.leg_back_right_1);
        this.body_main.addChild(this.leg_front_right);
        this.head.addChild(this.ear_right);
        this.body_main.addChild(this.body_front);
        this.head.addChild(this.ear_left);
        this.head.addChild(this.head_top);
        this.body_main.addChild(this.body_right);
        this.body_main.addChild(this.head);
    }

    @Override
    public void render(EntityGlactrix entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.body_main.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(EntityGlactrix entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        float swingVal = entity.getIsToppled() ? (ageInTicks % 100) : limbSwing;
        float swingAmount = entity.getIsToppled() ? .5f : (limbSwingAmount * 4f);

        this.leg_front_left.rotateAngleX = MathHelper.cos(swingVal * 0.6662F + (float)Math.PI) * swingAmount;
        this.leg_back_right_1.rotateAngleX = MathHelper.cos(swingVal * 0.6662F + (float)Math.PI) * swingAmount;
        this.leg_back_left_1.rotateAngleX = MathHelper.cos(swingVal * 0.6662F)  * swingAmount;
        this.leg_front_right.rotateAngleX = MathHelper.cos(swingVal * 0.6662F) * swingAmount;
    }
}
