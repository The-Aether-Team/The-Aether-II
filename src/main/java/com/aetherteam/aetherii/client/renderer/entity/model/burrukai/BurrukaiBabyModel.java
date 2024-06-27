package com.aetherteam.aetherii.client.renderer.entity.model.burrukai;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.animation.BurrukaiAnimation;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public class BurrukaiBabyModel extends HierarchicalModel<Burrukai> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart ears;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_rear_left;
    private final ModelPart leg_rear_right;

    public BurrukaiBabyModel(ModelPart root) {
        this.root = root;
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
    public void setupAnim(Burrukai kirrid, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.animate(kirrid.ramAnimationState, BurrukaiAnimation.RUSH_START, ageInTicks, 1.0F);
        this.animateWalk(BurrukaiAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}