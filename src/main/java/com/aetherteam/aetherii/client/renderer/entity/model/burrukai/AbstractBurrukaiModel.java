package com.aetherteam.aetherii.client.renderer.entity.model.burrukai;

import com.aetherteam.aetherii.client.renderer.entity.animation.BurrukaiAnimation;
import com.aetherteam.aetherii.client.renderer.entity.state.BurrukaiRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractBurrukaiModel extends EntityModel<BurrukaiRenderState> {
    public final ModelPart body_main;
    public final ModelPart body_front;
    public final ModelPart body_rear;
    public final ModelPart tail;
    public final ModelPart body_plate_rear;
    public final ModelPart body_plate_front;
    public final ModelPart body_neck;
    public final ModelPart leg_front_left;
    public final ModelPart leg_front_left_lower;
    public final ModelPart leg_front_left_plate;
    public final ModelPart leg_front_right;
    public final ModelPart leg_front_right_lower;
    public final ModelPart leg_front_right_plate;
    public final ModelPart leg_rear_right;
    public final ModelPart leg_rear_right_lower;
    public final ModelPart leg_rear_left;
    public final ModelPart leg_rear_left_lower;
    public final ModelPart head_main;
    public final ModelPart head_front;
    public final ModelPart head_plate;
    public final ModelPart antler_right;
    public final ModelPart antler_left;
    public final ModelPart ear_left;
    public final ModelPart ear_right;
    public final ModelPart head_rear;
    public final ModelPart mouth;

    public AbstractBurrukaiModel(ModelPart root) {
        super(root);
        this.body_main = root.getChild("body_main");
        this.body_front = this.body_main.getChild("body_front");
        this.body_rear = this.body_main.getChild("body_rear");
        this.tail = this.body_main.getChild("tail");
        this.body_plate_rear = this.body_main.getChild("body_plate_rear");
        this.body_plate_front = this.body_main.getChild("body_plate_front");
        this.body_neck = this.body_main.getChild("body_neck");
        this.leg_front_left = this.body_main.getChild("leg_front_left");
        this.leg_front_left_lower = this.leg_front_left.getChild("leg_front_left_lower");
        this.leg_front_left_plate = this.leg_front_left.getChild("leg_front_left_plate");
        this.leg_front_right = this.body_main.getChild("leg_front_right");
        this.leg_front_right_lower = this.leg_front_right.getChild("leg_front_right_lower");
        this.leg_front_right_plate = this.leg_front_right.getChild("leg_front_right_plate");
        this.leg_rear_right = this.body_main.getChild("leg_rear_right");
        this.leg_rear_right_lower = this.leg_rear_right.getChild("leg_rear_right_lower");
        this.leg_rear_left = this.body_main.getChild("leg_rear_left");
        this.leg_rear_left_lower = this.leg_rear_left.getChild("leg_rear_left_lower");
        this.head_main = this.body_main.getChild("head_main");
        this.head_front = this.head_main.getChild("head_front");
        this.head_plate = this.head_main.getChild("head_plate");
        this.antler_right = this.head_main.getChild("antler_right");
        this.antler_left = this.head_main.getChild("antler_left");
        this.ear_left = this.head_main.getChild("ear_left");
        this.ear_right = this.head_main.getChild("ear_right");
        this.head_rear = this.head_main.getChild("head_rear");
        this.mouth = this.head_main.getChild("mouth");
    }

    @Override
    public void setupAnim(BurrukaiRenderState burrukai) {
        super.setupAnim(burrukai);
        this.head_main.yRot = burrukai.yRot * (float) (Math.PI / 180.0);
        this.head_main.xRot = burrukai.xRot * (float) (Math.PI / 180.0);
        this.animate(burrukai.ramAnimationState, BurrukaiAnimation.RUSH_START, burrukai.ageInTicks, 1.0F);
        this.animateWalk(BurrukaiAnimation.WALK, burrukai.walkAnimationPos, burrukai.walkAnimationSpeed, 2.0F, 2.0F);
    }
}