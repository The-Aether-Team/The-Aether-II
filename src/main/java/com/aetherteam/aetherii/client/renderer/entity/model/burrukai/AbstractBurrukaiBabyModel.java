package com.aetherteam.aetherii.client.renderer.entity.model.burrukai;

import com.aetherteam.aetherii.client.renderer.entity.animation.BurrukaiAnimation;
import com.aetherteam.aetherii.client.renderer.entity.state.BurrukaiRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AbstractBurrukaiBabyModel extends EntityModel<BurrukaiRenderState> {
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart ears;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_rear_left;
    private final ModelPart leg_rear_right;

    public AbstractBurrukaiBabyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.head = this.body.getChild("head");
        this.ears = this.head.getChild("ears");
        this.leg_front_left = this.body.getChild("leg_front_left");
        this.leg_front_right = this.body.getChild("leg_front_right");
        this.leg_rear_left = this.body.getChild("leg_rear_left");
        this.leg_rear_right = this.body.getChild("leg_rear_right");
    }

    @Override
    public void setupAnim(BurrukaiRenderState burrukai) {
        super.setupAnim(burrukai);
        this.head.yRot = burrukai.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = burrukai.xRot * (float) (Math.PI / 180.0);
        this.animate(burrukai.ramAnimationState, BurrukaiAnimation.RUSH_START, burrukai.ageInTicks, 1.0F);
        this.animateWalk(BurrukaiAnimation.WALK, burrukai.walkAnimationPos, burrukai.walkAnimationSpeed, 2.0F, 2.0F);
    }
}