package com.aetherteam.aetherii.client.renderer.blockentity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import java.util.EnumSet;
import java.util.Set;

public class SageChestModel extends Model {
    private final ModelPart root;
    private final ModelPart lid;

    public SageChestModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.root = root;
        this.lid = root.getChild("lid");
    }

    public static LayerDefinition createSingleBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("lid", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.0F, -4.0F, -13.0F, 14.0F, 4.0F, 14.0F)
                .texOffs(28, 54).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 3.0F, 1.0F)
                .texOffs(38, 54).addBox(-1.0F, 1.0F, -14.0F, 2.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 14.0F, 6.0F));
        root.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 18).addBox(-7.0F, -2.0F, -7.0F, 14.0F, 2.0F, 14.0F)
                .texOffs(0, 34).addBox(-6.0F, -10.0F, -6.0F, 12.0F, 8.0F, 12.0F)
                .texOffs(56, 0).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 8.0F, 0.0F)
                .texOffs(56, 8).addBox(-7.0F, -10.0F, 7.0F, 14.0F, 8.0F, 0.0F)
                .texOffs(48, 34).addBox(7.0F, -10.0F, -7.0F, 0.0F, 8.0F, 14.0F)
                .texOffs(0, 54).addBox(-7.0F, -10.0F, -7.0F, 0.0F, 8.0F, 14.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    public static LayerDefinition createDoubleBodyRightLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        Set<Direction> visibleFaces = allExcept(Direction.EAST);

        root.addOrReplaceChild("lid", CubeListBuilder.create()
                .texOffs(46, 68).addBox(22.0F, 1.0F, -14.0F, 1.0F, 1.0F, 1.0F, visibleFaces)
                .texOffs(36, 68).addBox(21.0F, -2.0F, -14.0F, 2.0F, 3.0F, 1.0F, visibleFaces)
                .texOffs(0, 18).addBox(8.0F, -4.0F, -13.0F, 15.0F, 4.0F, 14.0F, visibleFaces), PartPose.offset(-15.0F, 14.0F, 6.0F));
        root.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 52).addBox(-7.0F, -2.0F, -7.0F, 15.0F, 2.0F, 14.0F, visibleFaces)
                .texOffs(58, 20).addBox(-6.0F, -10.0F, -6.0F, 14.0F, 8.0F, 12.0F, visibleFaces)
                .texOffs(58, 62).addBox(-7.0F, -10.0F, -7.0F, 0.0F, 8.0F, 14.0F)
                .texOffs(0, 84).addBox(-7.0F, -10.0F, 7.0F, 15.0F, 8.0F, 0.0F)
                .texOffs(30, 84).addBox(-7.0F, -10.0F, -7.0F, 15.0F, 8.0F, 0.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    public static LayerDefinition createDoubleBodyLeftLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        Set<Direction> visibleFaces = allExcept(Direction.WEST);

        root.addOrReplaceChild("lid", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -4.0F, -13.0F, 15.0F, 4.0F, 14.0F, visibleFaces)
                .texOffs(30, 68).addBox(-8.0F, -2.0F, -14.0F, 2.0F, 3.0F, 1.0F, visibleFaces)
                .texOffs(42, 68).addBox(-8.0F, 1.0F, -14.0F, 1.0F, 1.0F, 1.0F, visibleFaces), PartPose.offset(0.0F, 14.0F, 6.0F));
        root.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 36).addBox(-8.0F, -2.0F, -7.0F, 15.0F, 2.0F, 14.0F, visibleFaces)
                .texOffs(58, 0).addBox(-8.0F, -10.0F, -6.0F, 14.0F, 8.0F, 12.0F, visibleFaces)
                .texOffs(58, 40).addBox(7.0F, -10.0F, -7.0F, 0.0F, 8.0F, 14.0F)
                .texOffs(0, 68).addBox(-8.0F, -10.0F, -7.0F, 15.0F, 8.0F, 0.0F)
                .texOffs(0, 76).addBox(-8.0F, -10.0F, 7.0F, 15.0F, 8.0F, 0.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    public void setupAnim(float open) {
        this.lid.xRot = -(open * Mth.HALF_PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private static Set<Direction> allExcept(Direction direction) {
        EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
        directions.remove(direction);
        return directions;
    }
}
