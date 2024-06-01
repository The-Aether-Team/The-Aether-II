package com.aetherteam.aetherii.client.renderer.model;// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KirridWoolModel<T extends Kirrid> extends EntityModel<T> {
    private final ModelPart wool;

    public KirridWoolModel(ModelPart root) {
        this.wool = root.getChild("wool");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wool = partdefinition.addOrReplaceChild("wool", CubeListBuilder.create().texOffs(8, 86).addBox(-6.5F, -6.0F, -11.5F, 13.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 1.0F));

        PartDefinition wool_neck_r1 = wool.addOrReplaceChild("wool_neck_r1", CubeListBuilder.create().texOffs(19, 73).addBox(-4.5F, -5.0F, -13.5F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition wool_rear_r1 = wool.addOrReplaceChild("wool_rear_r1", CubeListBuilder.create().texOffs(13, 109).addBox(-5.5F, -21.0F, -1.5F, 11.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wool.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}