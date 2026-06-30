package com.aetherteam.aetherii.client.renderer.blockentity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class AlkahestPurifierModel extends Model {
    private final ModelPart main;
    private final ModelPart lid;

    public AlkahestPurifierModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("main");
        this.lid = this.main.getChild("lid");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(16, 21).addBox(-12.0F, -14.0F, 4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(45, 46).addBox(-16.0F, -2.0F, 1.0F, 16.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(47, 13).addBox(-11.0F, -14.5F, 5.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 13).addBox(-11.0F, -15.0F, 5.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-11.0F, -4.0F, 0.0F, 6.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.0F, 24.0F, -8.0F));

        main.addOrReplaceChild("vial_1", CubeListBuilder.create().texOffs(49, 33).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -4.0F, 8.0F, 0.0F, 0.0F, 0.7854F));
        main.addOrReplaceChild("vial_2", CubeListBuilder.create().texOffs(3, 33).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.25F, -4.0F, 8.0F, 0.0F, 0.0F, -0.7854F));
        main.addOrReplaceChild("vial_3", CubeListBuilder.create().texOffs(49, 24).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -9.5F, 8.0F, 0.0F, 0.0F, 0.7854F));
        main.addOrReplaceChild("vial_4", CubeListBuilder.create().texOffs(3, 24).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.25F, -9.5F, 8.0F, 0.0F, 0.0F, -0.7854F));
        main.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(13, 0).addBox(-5.0F, -2.0F, -9.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -14.0F, 12.0F));

        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    public void setupAnim(float openness) {
        this.lid.xRot = -(openness * ((float) Math.PI / 2.0F));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
