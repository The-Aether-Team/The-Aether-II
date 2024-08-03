package com.aetherteam.aetherii.client.renderer.entity.model;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.animation.SwetAnimation;
import com.aetherteam.aetherii.entity.monster.Swet;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SwetModel<T extends Swet> extends HierarchicalModel<T> {
    private final ModelPart root;
    public final ModelPart body;
    public final ModelPart gel;
    public final ModelPart squish;
    public final ModelPart head;
    private final ModelPart wisp_top_left;
    private final ModelPart wisp_top_right;
    private final ModelPart wisp_bottom_left;
    private final ModelPart wisp_bottom_right;

    public SwetModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.gel = this.body.getChild("gel");
        this.squish = this.gel.getChild("squish");
        this.head = this.body.getChild("head");
        this.wisp_top_left = this.head.getChild("wisp_top_left");
        this.wisp_top_right = this.head.getChild("wisp_top_right");
        this.wisp_bottom_left = this.head.getChild("wisp_bottom_left");
        this.wisp_bottom_right = this.head.getChild("wisp_bottom_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition gel = body.addOrReplaceChild("gel", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(1.1F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition squish = gel.addOrReplaceChild("squish", CubeListBuilder.create().texOffs(56, 32).addBox(-9.0F, -3.5F, -9.0F, 18.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-4.5F, -4.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -8.5F, -1.0F));

        PartDefinition wisp_top_left = head.addOrReplaceChild("wisp_top_left", CubeListBuilder.create(), PartPose.offset(4.5F, -3.5F, 2.5F));

        PartDefinition wisp_top_left_r1 = wisp_top_left.addOrReplaceChild("wisp_top_left_r1", CubeListBuilder.create().texOffs(2, 28).addBox(0.0F, -1.0F, 0.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -4.0F, 0.1309F, 0.1309F, 0.0F));

        PartDefinition wisp_top_right = head.addOrReplaceChild("wisp_top_right", CubeListBuilder.create(), PartPose.offset(-4.5F, -3.5F, 2.5F));

        PartDefinition wisp_top_right_r1 = wisp_top_right.addOrReplaceChild("wisp_top_right_r1", CubeListBuilder.create().texOffs(2, 28).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -4.0F, 0.1309F, -0.1309F, 0.0F));

        PartDefinition wisp_bottom_left = head.addOrReplaceChild("wisp_bottom_left", CubeListBuilder.create(), PartPose.offset(4.5F, 1.5F, 3.5F));

        PartDefinition wisp_bottom_left_r1 = wisp_bottom_left.addOrReplaceChild("wisp_bottom_left_r1", CubeListBuilder.create().texOffs(3, 32).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, -0.1309F, 0.1309F, 0.0F));

        PartDefinition wisp_bottom_right = head.addOrReplaceChild("wisp_bottom_right", CubeListBuilder.create(), PartPose.offset(-4.5F, 1.5F, 3.5F));

        PartDefinition wisp_bottom_right_r1 = wisp_bottom_right.addOrReplaceChild("wisp_bottom_right_r1", CubeListBuilder.create().texOffs(3, 32).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, -0.1309F, -0.1309F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.gel.visible = false;
        this.squish.visible = false;
        this.animate(entity.groundAnimationState, SwetAnimation.ground, ageInTicks);
        this.animate(entity.jumpAnimationState, SwetAnimation.jump, ageInTicks);
        this.gel.xScale *= 1 + 0.1F * entity.getFoodSaturation();
        this.gel.yScale *= 1 + 0.1F * entity.getFoodSaturation();
        this.gel.zScale *= 1 + 0.1F * entity.getFoodSaturation();
        this.gel.xScale *= (1.0F - entity.getWaterDamageScale());
        this.gel.yScale *= (1.0F - entity.getWaterDamageScale());
        this.gel.zScale *= (1.0F - entity.getWaterDamageScale());
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}