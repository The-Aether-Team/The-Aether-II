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
import net.minecraft.util.Mth;

public class FungalCacheModel extends Model {
    private final ModelPart root;
    private final ModelPart petalInner1;
    private final ModelPart petalInner2;
    private final ModelPart petalInner3;
    private final ModelPart petalInner4;

    public FungalCacheModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
        this.petalInner1 = root.getChild("petal_1").getChild("petal_inner1");
        this.petalInner2 = root.getChild("petal_2").getChild("petal_inner2");
        this.petalInner3 = root.getChild("petal_3").getChild("petal_inner3");
        this.petalInner4 = root.getChild("petal_4").getChild("petal_inner4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition petal1 = partdefinition.addOrReplaceChild("petal_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 23.0F, -3.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition petalInner1 = petal1.addOrReplaceChild("petal_inner1", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        petalInner1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition petal2 = partdefinition.addOrReplaceChild("petal_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 23.0F, 3.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition petalInner2 = petal2.addOrReplaceChild("petal_inner2", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        petalInner2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition petal3 = partdefinition.addOrReplaceChild("petal_3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 23.0F, 3.0F, 0.0F, 2.3562F, 0.0F));
        PartDefinition petalInner3 = petal3.addOrReplaceChild("petal_inner3", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        petalInner3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition petal4 = partdefinition.addOrReplaceChild("petal_4", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 23.0F, -3.0F, 0.0F, -2.3562F, 0.0F));
        PartDefinition petalInner4 = petal4.addOrReplaceChild("petal_inner4", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        petalInner4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 16).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        main.addOrReplaceChild("inside_r1", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setupAnim(float openness) {
        float rotation = -(openness * (Mth.PI / 3.0F));
        this.petalInner1.zRot = rotation;
        this.petalInner2.zRot = rotation;
        this.petalInner3.zRot = rotation;
        this.petalInner4.zRot = rotation;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
