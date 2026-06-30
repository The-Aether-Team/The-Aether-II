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

public class AbandonedBagModel extends Model {
    private final ModelPart root;
    private final ModelPart lid;

    public AbandonedBagModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.root = root;
        this.lid = root.getChild("lid");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("lid", CubeListBuilder.create()
                .texOffs(0, 11).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 6.0F));

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 20).addBox(4.0F, -4.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        main.addOrReplaceChild("axe", CubeListBuilder.create()
                .texOffs(0, 20).addBox(0.0F, -6.0F, -3.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.3491F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void setupAnim(float openness) {
        this.lid.xRot = -(openness * ((float) Math.PI / 2.0F));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
