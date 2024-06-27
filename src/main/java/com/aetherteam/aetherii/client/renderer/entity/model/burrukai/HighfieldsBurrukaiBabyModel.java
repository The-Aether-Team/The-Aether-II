package com.aetherteam.aetherii.client.renderer.entity.model.burrukai;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HighfieldsBurrukaiBabyModel extends BurrukaiBabyModel {
    public HighfieldsBurrukaiBabyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(11, 10).addBox(-3.5F, -2.5F, -3.5F, 7.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(21, 0).addBox(2.5F, -4.0F, -2.5F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(33, 0).addBox(-4.5F, -4.0F, -2.5F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -0.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 27).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 7.5F, -0.8727F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -3.7F, -5.4F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -3.0F));

        PartDefinition plate = head.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(1, 0).addBox(-2.0F, -2.6F, -4.2F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(0, 6).addBox(-4.0F, -2.0F, 2.3F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4835F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(56, 11).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.7F, 1.0F, -0.9F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(48, 11).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.7F, 1.0F, -0.9F));

        PartDefinition leg_rear_left = body.addOrReplaceChild("leg_rear_left", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.7F, 1.0F, 6.5F));

        PartDefinition leg_rear_right = body.addOrReplaceChild("leg_rear_right", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.7F, 1.0F, 6.5F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}