package com.gildedgames.aether.client.models.entities.living;

import com.gildedgames.aether.client.renderer.entities.living.layers.ILayeredModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTempestInsect  extends ModelBase implements ILayeredModel {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer back;
    public ModelRenderer shoulder_left;
    public ModelRenderer shoulder_right;
    public ModelRenderer jaw;
    public ModelRenderer butt;
    public ModelRenderer leg_left_1;
    public ModelRenderer leg_left_2;
    public ModelRenderer leg_right_1;
    public ModelRenderer leg_right_2;
    public ModelRenderer belly;
    public ModelRenderer tail_1;
    public ModelRenderer cloud_left_front;
    public ModelRenderer cloud_right_front;
    public ModelRenderer arm_left_1;
    public ModelRenderer arm_left_2;
    public ModelRenderer arm_right_1;
    public ModelRenderer arm_right_2;
    public ModelRenderer wing_left;
    public ModelRenderer wing_right;
    public ModelRenderer cloud_left_back;
    public ModelRenderer cloud_right_back;
    public ModelRenderer tail_2;

    public ModelTempestInsect() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.jaw = new ModelRenderer(this, 0, 12);
        this.jaw.setRotationPoint(0.0F, 2.0F, -3.0F);
        this.jaw.addBox(-3.0F, 0.7F, -5.0F, 6, 1, 5, 0.0F);
        this.leg_right_2 = new ModelRenderer(this, 1, 25);
        this.leg_right_2.mirror = true;
        this.leg_right_2.setRotationPoint(-0.8F, 0.5F, 9.0F);
        this.leg_right_2.addBox(-3.0F, 0.0F, -0.5F, 3, 4, 1, 0.0F);
        this.setRotateAngle(leg_right_2, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail_1 = new ModelRenderer(this, 44, 60);
        this.tail_1.setRotationPoint(0.0F, 1.1F, 12.0F);
        this.tail_1.addBox(0.0F, -1.0F, -0.5F, 1, 2, 9, 0.0F);
        this.setRotateAngle(tail_1, -0.17453292519943295F, 0.0F, 0.0F);
        this.cloud_right_back = new ModelRenderer(this, 0, 60);
        this.cloud_right_back.mirror = true;
        this.cloud_right_back.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.cloud_right_back.addBox(0.0F, -2.0F, -1.0F, 1, 4, 18, 0.0F);
        this.setRotateAngle(cloud_right_back, -0.08726646259971647F, 0.0F, 0.0F);
        this.arm_left_2 = new ModelRenderer(this, 20, 18);
        this.arm_left_2.setRotationPoint(0.0F, 3.5F, 0.0F);
        this.arm_left_2.addBox(-1.0F, -0.5F, -1.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(arm_left_2, -0.3490658503988659F, 0.0F, 0.0F);
        this.wing_left = new ModelRenderer(this, 1, 47);
        this.wing_left.setRotationPoint(2.0F, 0.0F, 5.0F);
        this.wing_left.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 9, 0.0F);
        this.setRotateAngle(wing_left, 0.4363323129985824F, 0.4363323129985824F, 0.0F);
        this.butt = new ModelRenderer(this, 36, 10);
        this.butt.setRotationPoint(0.0F, -2.0F, 7.0F);
        this.butt.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 6, 0.0F);
        this.setRotateAngle(butt, -0.20943951023931953F, 0.0F, 0.0F);
        this.arm_right_2 = new ModelRenderer(this, 20, 18);
        this.arm_right_2.mirror = true;
        this.arm_right_2.setRotationPoint(0.0F, 3.5F, 0.0F);
        this.arm_right_2.addBox(-1.0F, -0.5F, -1.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(arm_right_2, -0.3490658503988659F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.head.addBox(-3.5F, 0.0F, -9.0F, 7, 3, 9, 0.0F);
        this.setRotateAngle(head, 0.20943951023931953F, 0.0F, 0.0F);
        this.tail_2 = new ModelRenderer(this, 46, 71);
        this.tail_2.setRotationPoint(0.0F, 0.0F, 8.5F);
        this.tail_2.addBox(0.0F, -1.0F, -0.5F, 1, 2, 8, 0.0F);
        this.setRotateAngle(tail_2, 0.2617993877991494F, 0.0F, 0.0F);
        this.shoulder_left = new ModelRenderer(this, 0, 18);
        this.shoulder_left.setRotationPoint(3.0F, 2.0F, 1.0F);
        this.shoulder_left.addBox(0.0F, -1.5F, -1.5F, 3, 2, 5, 0.0F);
        this.setRotateAngle(shoulder_left, 0.3490658503988659F, -0.3490658503988659F, 0.0F);
        this.leg_right_1 = new ModelRenderer(this, 1, 25);
        this.leg_right_1.mirror = true;
        this.leg_right_1.setRotationPoint(-0.8F, 0.5F, 8.0F);
        this.leg_right_1.addBox(-3.0F, 0.0F, -0.5F, 3, 4, 1, 0.0F);
        this.setRotateAngle(leg_right_1, -0.2617993877991494F, 0.0F, 0.0F);
        this.arm_left_1 = new ModelRenderer(this, 16, 20);
        this.arm_left_1.setRotationPoint(2.0F, -0.3F, 1.5F);
        this.arm_left_1.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(arm_left_1, -0.5235987755982988F, 0.0F, 0.0F);
        this.arm_right_1 = new ModelRenderer(this, 16, 20);
        this.arm_right_1.mirror = true;
        this.arm_right_1.setRotationPoint(-2.0F, -0.3F, 1.5F);
        this.arm_right_1.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(arm_right_1, -0.5235987755982988F, 0.0F, 0.0F);
        this.leg_left_1 = new ModelRenderer(this, 1, 25);
        this.leg_left_1.setRotationPoint(0.8F, 0.5F, 8.0F);
        this.leg_left_1.addBox(0.0F, 0.0F, -0.5F, 3, 4, 1, 0.0F);
        this.setRotateAngle(leg_left_1, -0.2617993877991494F, 0.0F, 0.0F);
        this.leg_left_2 = new ModelRenderer(this, 1, 25);
        this.leg_left_2.setRotationPoint(0.8F, 0.5F, 9.0F);
        this.leg_left_2.addBox(0.0F, 0.0F, -0.5F, 3, 4, 1, 0.0F);
        this.setRotateAngle(leg_left_2, 0.2617993877991494F, 0.0F, 0.0F);
        this.shoulder_right = new ModelRenderer(this, 0, 18);
        this.shoulder_right.mirror = true;
        this.shoulder_right.setRotationPoint(-3.0F, 2.0F, 1.0F);
        this.shoulder_right.addBox(-3.0F, -1.5F, -1.5F, 3, 2, 5, 0.0F);
        this.setRotateAngle(shoulder_right, 0.3490658503988659F, 0.3490658503988659F, 0.0F);
        this.back = new ModelRenderer(this, 32, 0);
        this.back.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.back.addBox(-4.5F, 0.0F, 0.0F, 9, 3, 7, 0.0F);
        this.cloud_left_front = new ModelRenderer(this, 0, 25);
        this.cloud_left_front.setRotationPoint(2.0F, 2.0F, 3.0F);
        this.cloud_left_front.addBox(0.0F, -1.5F, -1.0F, 1, 3, 12, 0.0F);
        this.setRotateAngle(cloud_left_front, -0.3490658503988659F, 0.5235987755982988F, 0.0F);
        this.wing_right = new ModelRenderer(this, 1, 47);
        this.wing_right.mirror = true;
        this.wing_right.setRotationPoint(-2.0F, 0.0F, 5.0F);
        this.wing_right.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 9, 0.0F);
        this.setRotateAngle(wing_right, 0.4363323129985824F, -0.4363323129985824F, 0.0F);
        this.belly = new ModelRenderer(this, 26, 23);
        this.belly.setRotationPoint(0.0F, 2.7F, -3.0F);
        this.belly.addBox(-2.5F, 0.0F, 0.0F, 5, 1, 14, 0.0F);
        this.cloud_right_front = new ModelRenderer(this, 0, 25);
        this.cloud_right_front.mirror = true;
        this.cloud_right_front.setRotationPoint(-2.0F, 2.0F, 3.0F);
        this.cloud_right_front.addBox(-1.0F, -1.5F, -1.0F, 1, 3, 12, 0.0F);
        this.setRotateAngle(cloud_right_front, -0.3490658503988659F, -0.5235987755982988F, 0.0F);
        this.cloud_left_back = new ModelRenderer(this, 0, 60);
        this.cloud_left_back.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.cloud_left_back.addBox(-1.0F, -2.0F, -1.0F, 1, 4, 18, 0.0F);
        this.setRotateAngle(cloud_left_back, -0.08726646259971647F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 12, 38);
        this.body.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.body.addBox(-3.0F, 0.7F, -8.0F, 6, 2, 20, 0.0F);
        this.body.addChild(this.jaw);
        this.body.addChild(this.leg_right_2);
        this.body.addChild(this.tail_1);
        this.wing_right.addChild(this.cloud_right_back);
        this.arm_left_1.addChild(this.arm_left_2);
        this.butt.addChild(this.wing_left);
        this.body.addChild(this.butt);
        this.arm_right_1.addChild(this.arm_right_2);
        this.body.addChild(this.head);
        this.tail_1.addChild(this.tail_2);
        this.body.addChild(this.shoulder_left);
        this.body.addChild(this.leg_right_1);
        this.shoulder_left.addChild(this.arm_left_1);
        this.shoulder_right.addChild(this.arm_right_1);
        this.body.addChild(this.leg_left_1);
        this.body.addChild(this.leg_left_2);
        this.body.addChild(this.shoulder_right);
        this.body.addChild(this.back);
        this.body.addChild(this.cloud_left_front);
        this.butt.addChild(this.wing_right);
        this.body.addChild(this.belly);
        this.body.addChild(this.cloud_right_front);
        this.wing_left.addChild(this.cloud_left_back);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void preLayerRender() {
        this.back.isHidden = true;
        this.shoulder_right.isHidden = true;
        this.shoulder_left.isHidden = true;
        this.leg_left_1.isHidden = true;
        this.leg_left_2.isHidden = true;
        this.leg_right_1.isHidden = true;
        this.leg_right_2.isHidden = true;
        this.belly.isHidden = true;
        this.tail_1.isHidden = true;
    }

    @Override
    public void postLayerRender() {
        this.back.isHidden = false;
        this.shoulder_right.isHidden = false;
        this.shoulder_left.isHidden = false;
        this.leg_left_1.isHidden = false;
        this.leg_left_2.isHidden = false;
        this.leg_right_1.isHidden = false;
        this.leg_right_2.isHidden = false;
        this.belly.isHidden = false;
        this.tail_1.isHidden = false;
    }
}