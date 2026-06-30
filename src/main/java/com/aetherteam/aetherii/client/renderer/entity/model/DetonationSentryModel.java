package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DetonationSentryModel extends HierarchicalModel<DetonationSentry> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart legs_pair_1;
    private final ModelPart leg_back_left;
    private final ModelPart leg_front_right;
    private final ModelPart legs_pair_2;
    private final ModelPart leg_back_right;
    private final ModelPart leg_front_left;

    public DetonationSentryModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.legs_pair_1 = this.body.getChild("legs_pair_1");
        this.leg_back_left = this.legs_pair_1.getChild("leg_back_left");
        this.leg_front_right = this.legs_pair_1.getChild("leg_front_right");
        this.legs_pair_2 = this.body.getChild("legs_pair_2");
        this.leg_back_right = this.legs_pair_2.getChild("leg_back_right");
        this.leg_front_left = this.legs_pair_2.getChild("leg_front_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(57, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition legs_pair_1 = body.addOrReplaceChild("legs_pair_1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition leg_back_left = legs_pair_1.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(40, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, 0.0F, 7.0F, 0.2618F, 0.7854F, 0.0F));

        PartDefinition leg_front_right = legs_pair_1.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, 0.0F, -7.0F, -0.2618F, 0.7854F, 0.0F));

        PartDefinition legs_pair_2 = body.addOrReplaceChild("legs_pair_2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition leg_back_right = legs_pair_2.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 7.0F, 0.2618F, -0.7854F, 0.0F));

        PartDefinition leg_front_left = legs_pair_2.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(40, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, 0.0F, -7.0F, -0.2618F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(DetonationSentry entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.legs_pair_1.xRot = Mth.cos(limbSwing * 0.6662F) * 0.35F * limbSwingAmount;
        this.legs_pair_2.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 0.35F * limbSwingAmount;
        if (entity.isAwake()) {
            float timerScale = entity.getTimer(Mth.clamp(ageInTicks - entity.tickCount, 0.0F, 1.0F)) / (float) DetonationSentry.MAX_TIMER;
            this.head.y += Mth.sin(ageInTicks * (4.0F + timerScale * 12.0F)) * timerScale * 0.4F;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
