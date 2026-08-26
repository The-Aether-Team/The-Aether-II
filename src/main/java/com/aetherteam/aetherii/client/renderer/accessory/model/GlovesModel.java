package com.aetherteam.aetherii.client.renderer.accessory.model;

import com.aetherteam.aetherii.client.renderer.accessory.GlovesModelSet;
import com.google.common.collect.Maps;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;

public class GlovesModel extends HumanoidModel<HumanoidRenderState> {
    protected static final Map<HumanoidArm, String> PART_FOR_ARM = Maps.newEnumMap(Map.of(
            HumanoidArm.RIGHT, "right_arm",
            HumanoidArm.LEFT, "left_arm"
    ));

    public GlovesModel(ModelPart root) {
        super(root);
    }

    public static GlovesModelSet<MeshDefinition> createGlovesMeshSet(CubeDeformation deformation, boolean slim) {
        return createGlovesMeshSet(GlovesModel::createBaseGlovesMesh, PART_FOR_ARM, deformation, slim);
    }

    protected static GlovesModelSet<MeshDefinition> createGlovesMeshSet(BiFunction<CubeDeformation, Boolean, MeshDefinition> baseFactory, Map<HumanoidArm, String> partForArm, CubeDeformation deformation, boolean slim) {
        MeshDefinition right = baseFactory.apply(deformation, slim);
        right.getRoot().retainPartsAndChildren(Collections.singleton(partForArm.get(HumanoidArm.RIGHT)));
        MeshDefinition left = baseFactory.apply(deformation, slim);
        left.getRoot().retainPartsAndChildren(Collections.singleton(partForArm.get(HumanoidArm.LEFT)));
        return new GlovesModelSet<>(right, left);
    }

    public static MeshDefinition createBaseGlovesMesh(CubeDeformation cube, boolean isSlim) {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();
        if (!isSlim) {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        } else {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        }
        return meshDefinition;
    }
}
