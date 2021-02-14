package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.client.renderer.ModelBaseAether;
import com.gildedgames.aether.client.renderer.ModelRendererAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * babymoa - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelMoaBaby extends ModelBaseAether {
    public ModelRendererAether body_main;
    public ModelRendererAether tail;
    public ModelRendererAether leg_l_1;
    public ModelRendererAether head;
    public ModelRendererAether wing_l;
    public ModelRendererAether wing_r;
    public ModelRendererAether leg_r_1;
    public ModelRendererAether neck;
    public ModelRendererAether leg_l_2;
    public ModelRendererAether feather_1;
    public ModelRendererAether feather_2;
    public ModelRendererAether feather_3;
    public ModelRendererAether feather_4;
    public ModelRendererAether leg_r_2;

    public ModelMoaBaby() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_l_2 = new ModelRendererAether(this, 56, 24);
        this.leg_l_2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.leg_l_2.addBox(-0.5F, 0.2F, 0.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(leg_l_2, 0.6981317007977318F, 0.0F, 0.0F);
        this.wing_l = new ModelRendererAether(this, 48, 3);
        this.wing_l.setRotationPoint(3.0F, 1.0F, -1.9F);
        this.wing_l.addBox(0.0F, -2.0F, -1.0F, 1, 4, 7, 0.0F);
        this.setRotateAngle(wing_l, 0.0F, 0.4363323129985824F, 0.0F);
        this.head = new ModelRendererAether(this, 0, 0);
        this.head.setRotationPoint(0.0F, -4.0F, -2.5F);
        this.head.addBox(-2.5F, -3.7F, -5.4F, 5, 5, 7, 0.0F);
        this.leg_r_2 = new ModelRendererAether(this, 48, 24);
        this.leg_r_2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.leg_r_2.addBox(-1.5F, 0.2F, 0.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(leg_r_2, 0.6981317007977318F, 0.0F, 0.0F);
        this.tail = new ModelRendererAether(this, 26, 25);
        this.tail.setRotationPoint(0.0F, -1.0F, 5.5F);
        this.tail.addBox(-2.5F, 0.0F, 0.0F, 5, 1, 6, 0.0F);
        this.setRotateAngle(tail, -1.0471975511965976F, 0.0F, 0.0F);
        this.feather_2 = new ModelRendererAether(this, 21, 12);
        this.feather_2.setRotationPoint(0.0F, 0.0F, 0.2F);
        this.feather_2.addBox(1.3F, -3.0F, 1.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(feather_2, 0.08726646259971647F, 0.2617993877991494F, 0.0F);
        this.neck = new ModelRendererAether(this, 24, 1);
        this.neck.setRotationPoint(0.0F, -1.0F, -3.0F);
        this.neck.addBox(-1.0F, -4.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(neck, -0.13962634015954636F, 0.0F, 0.0F);
        this.feather_1 = new ModelRendererAether(this, 21, 16);
        this.feather_1.setRotationPoint(0.0F, 0.0F, 0.2F);
        this.feather_1.addBox(1.3F, -0.5F, 1.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(feather_1, -0.08726646259971647F, 0.2617993877991494F, 0.0F);
        this.leg_l_1 = new ModelRendererAether(this, 50, 14);
        this.leg_l_1.setRotationPoint(2.5F, 2.0F, 2.5F);
        this.leg_l_1.addBox(-1.0F, -2.0F, -2.1F, 3, 6, 4, 0.0F);
        this.setRotateAngle(leg_l_1, -0.6981317007977318F, 0.0F, 0.0F);
        this.feather_3 = new ModelRendererAether(this, 1, 12);
        this.feather_3.setRotationPoint(0.0F, 0.0F, 0.2F);
        this.feather_3.addBox(-2.3F, -3.0F, 1.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(feather_3, 0.08726646259971647F, -0.2617993877991494F, 0.0F);
        this.body_main = new ModelRendererAether(this, 0, 16);
        this.body_main.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.body_main.addBox(-3.0F, -1.5F, -3.5F, 6, 6, 9, 0.0F);
        this.wing_r = new ModelRendererAether(this, 32, 3);
        this.wing_r.setRotationPoint(-3.0F, 1.0F, -1.9F);
        this.wing_r.addBox(-1.0F, -2.0F, -1.0F, 1, 4, 7, 0.0F);
        this.setRotateAngle(wing_r, 0.0F, -0.4363323129985824F, 0.0F);
        this.leg_r_1 = new ModelRendererAether(this, 36, 14);
        this.leg_r_1.setRotationPoint(-2.5F, 2.0F, 2.5F);
        this.leg_r_1.addBox(-2.0F, -2.0F, -2.1F, 3, 6, 4, 0.0F);
        this.setRotateAngle(leg_r_1, -0.6981317007977318F, 0.0F, 0.0F);
        this.feather_4 = new ModelRendererAether(this, 1, 16);
        this.feather_4.setRotationPoint(0.0F, 0.0F, 0.2F);
        this.feather_4.addBox(-2.3F, -0.5F, 1.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(feather_4, -0.08726646259971647F, -0.2617993877991494F, 0.0F);
        this.leg_l_1.addChild(this.leg_l_2);
        this.body_main.addChild(this.wing_l);
        this.body_main.addChild(this.head);
        this.leg_r_1.addChild(this.leg_r_2);
        this.body_main.addChild(this.tail);
        this.head.addChild(this.feather_2);
        this.body_main.addChild(this.neck);
        this.head.addChild(this.feather_1);
        this.body_main.addChild(this.leg_l_1);
        this.head.addChild(this.feather_3);
        this.body_main.addChild(this.wing_r);
        this.body_main.addChild(this.leg_r_1);
        this.head.addChild(this.feather_4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body_main.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity)
    {
        boolean flying = !entity.onGround;

        EntityMoa moa = (EntityMoa) entity;

        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scaleFactor, entity);

        float pitch = headPitch * 0.017453292F;
        float yaw = headYaw * 0.017453292F;

        this.head.rotateAngleX = pitch;
        this.head.rotateAngleY = yaw;

        float leftSwingX = (MathHelper.cos(limbSwing * 0.6662F) * 0.55F * limbSwingAmount);
        float rightSwingX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.55F * limbSwingAmount);

        if (!flying)
        {
            this.leg_l_1.offsetY = leftSwingX / 4f + .01f;
            this.leg_r_1.offsetY = rightSwingX / 4f + .01f;

            this.leg_l_1.rotateAngleX = (rightSwingX * 1.2f) - 0.6981317007977318F;
            this.leg_r_1.rotateAngleX = (leftSwingX * 1.2f) - 0.6981317007977318F;
        }
        else
        {
            this.setRotateAngle(this.leg_l_1, -0.6981317007977318F, 0.0F, 0.0F);
            this.setRotateAngle(this.leg_r_1, -0.6981317007977318F, 0.0F, 0.0F);
        }

        float tailSwayRange = 0.05F;

        this.tail.rotateAngleZ = (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);
        this.tail.rotateAngleX = -1.0471975511965976F + (MathHelper.cos((0.0F + ageInTicks) * 0.15662F) * tailSwayRange);

        float ageDif = ageInTicks - moa.getAgeSinceOffGround();

        float unfoldTimeInSeconds = 0.3F;
        float foldTimeInSeconds = 0.4F;

        if (flying)
        {
            float foldTime = (unfoldTimeInSeconds * 20.0F);

            float wingTime = ageDif / foldTime;
            float wingAlpha = Math.min(1.0F, wingTime);

            this.setRotateAngle(this.wing_r, 0.0F, NoiseUtil.lerp(0.0F, -0.4363323129985824F, wingAlpha), 0.0F);
            this.setRotateAngle(this.wing_l, 0.0F, NoiseUtil.lerp(0.0F, 0.4363323129985824F, wingAlpha), 0.0F);

            if (ageDif >= foldTime)
            {
                if (ageDif <= foldTime + 5.0F)
                {
                    wingAlpha = (ageDif - foldTime) / 5.0F;

                    this.wing_r.rotateAngleY = NoiseUtil
                            .lerp(this.wing_r.rotateAngleY, this.wing_r.rotateAngleY + (MathHelper.cos((0.0F + ageDif - 15.0F) * 0.175662F) * 0.6F),
                                    wingAlpha);
                    this.wing_l.rotateAngleX = NoiseUtil
                            .lerp(this.wing_l.rotateAngleY, this.wing_l.rotateAngleY + (MathHelper.cos((0.0F + ageDif - 15.0F) * 0.175662F) * -0.6F),
                                    wingAlpha);
                }
                else
                {
                    this.wing_r.rotateAngleY = this.wing_r.rotateAngleY + (MathHelper.cos((0.0F + ageDif - foldTime) * 0.175662F) * 0.6F);
                    this.wing_l.rotateAngleY = this.wing_l.rotateAngleY + (MathHelper.cos((0.0F + ageDif - foldTime) * 0.175662F) * -0.6F);
                }
            }
        }
        else
        {
            float foldTime = (foldTimeInSeconds * 20.0F);

            ageDif = Math.abs(moa.getAgeSinceOffGround() - ageInTicks);

            float wingAlpha = Math.min(1.0F, ageDif / foldTime);

            this.setRotateAngle(this.wing_r, 0.0F, NoiseUtil.lerp(0.0F, -0.4363323129985824F, wingAlpha), 0.0F);
            this.setRotateAngle(this.wing_l, 0.0F, NoiseUtil.lerp(0.0F, 0.4363323129985824F, wingAlpha), 0.0F);
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
