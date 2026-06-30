package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.animation.AechorPlantAnimation;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class AechorPlantModel extends HierarchicalModel<AechorPlant> {
    private final ModelPart root;
    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart petal;
    private final ModelPart petal2;
    private final ModelPart petal3;
    private final ModelPart petal4;
    private final ModelPart petal5;
    private final ModelPart petal6;
    private final ModelPart underPetal;
    private final ModelPart dart;
    private final ModelPart outerDart;

    public AechorPlantModel(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
        this.body = this.main.getChild("body");
        this.petal = this.main.getChild("petal");
        this.petal2 = this.petal.getChild("petal2");
        this.petal3 = this.petal.getChild("petal3");
        this.petal4 = this.petal.getChild("petal4");
        this.petal5 = this.petal.getChild("petal5");
        this.petal6 = this.petal.getChild("petal6");
        this.underPetal = this.main.getChild("under_petal");
        this.dart = this.main.getChild("dart");
        this.outerDart = this.main.getChild("outer_dart");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(3, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        main.addOrReplaceChild("stem_r1", CubeListBuilder.create().texOffs(28, 6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        main.addOrReplaceChild("stem_r2", CubeListBuilder.create().texOffs(28, 6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition petal = main.addOrReplaceChild("petal", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition petal2 = petal.addOrReplaceChild("petal2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        petal2.addOrReplaceChild("petal_r1", CubeListBuilder.create().texOffs(-8, 0).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal3 = petal.addOrReplaceChild("petal3", CubeListBuilder.create(), PartPose.offsetAndRotation(1.9487F, 0.0F, -0.4499F, 0.0F, -1.2566F, 0.0F));

        petal3.addOrReplaceChild("petal_r2", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal4 = petal.addOrReplaceChild("petal4", CubeListBuilder.create(), PartPose.offsetAndRotation(1.1756F, 0.0F, 1.618F, 0.0F, -2.5133F, 0.0F));

        petal4.addOrReplaceChild("petal_r3", CubeListBuilder.create().texOffs(-8, 0).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal5 = petal.addOrReplaceChild("petal5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.1756F, 0.0F, 1.618F, 0.0F, 2.5133F, 0.0F));

        petal5.addOrReplaceChild("petal_r4", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal6 = petal.addOrReplaceChild("petal6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9487F, 0.0F, -0.4499F, 0.0F, 1.2566F, 0.0F));

        petal6.addOrReplaceChild("petal_r5", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition underPetal = main.addOrReplaceChild("under_petal", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 0.0F));

        underPetal.addOrReplaceChild("under_petal_r1", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0543F, 0.7854F, 3.1416F));

        underPetal.addOrReplaceChild("under_petal_r2", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.7854F, 0.0F));

        underPetal.addOrReplaceChild("under_petal_r3", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0543F, -0.7854F, 3.1416F));

        underPetal.addOrReplaceChild("under_petal_r4", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.7854F, 0.0F));

        PartDefinition dart = main.addOrReplaceChild("dart", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        dart.addOrReplaceChild("dart_r1", CubeListBuilder.create().texOffs(24, 5).addBox(-0.25F, -4.25F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, -1.309F, 3.1416F));

        dart.addOrReplaceChild("dart_r2", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, -0.2182F, 3.1416F));

        dart.addOrReplaceChild("dart_r3", CubeListBuilder.create().texOffs(24, 5).addBox(-0.25F, -4.5F, -2.25F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, 0.8727F, 3.1416F));

        dart.addOrReplaceChild("dart_r4", CubeListBuilder.create().texOffs(24, 5).addBox(0.0F, -4.5F, -2.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.75F, 0.3054F, 1.0472F, 0.0F));

        dart.addOrReplaceChild("dart_r5", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        dart.addOrReplaceChild("dart_r6", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -4.75F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -1.0036F, 0.0F));

        PartDefinition outerDart = main.addOrReplaceChild("outer_dart", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        outerDart.addOrReplaceChild("outer_dart_r1", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -4.75F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -0.48F, 0.0F));

        outerDart.addOrReplaceChild("outer_dart_r2", CubeListBuilder.create().texOffs(26, 5).addBox(-0.25F, -4.25F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -1.309F, 0.0F));

        outerDart.addOrReplaceChild("outer_dart_r3", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, -0.7418F, 3.1416F));

        outerDart.addOrReplaceChild("outer_dart_r4", CubeListBuilder.create().texOffs(26, 5).addBox(-0.25F, -4.5F, -2.25F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.3491F, 3.1416F));

        outerDart.addOrReplaceChild("outer_dart_r5", CubeListBuilder.create().texOffs(26, 5).addBox(0.0F, -4.5F, -2.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.75F, 0.48F, 1.5708F, -0.3054F));

        outerDart.addOrReplaceChild("outer_dart_r6", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(AechorPlant aechorPlant, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float slowCycle = (1.0F - Mth.cos(ageInTicks * Mth.TWO_PI / 80.0F)) * 0.5F;
        float fastCycle = (1.0F - Mth.cos(ageInTicks * Mth.TWO_PI / 40.0F)) * 0.5F;

        this.main.y += 0.5F * slowCycle;
        this.main.yRot -= 3.5F * Mth.DEG_TO_RAD * slowCycle;
        this.body.y -= 0.25F * slowCycle;
        this.body.yRot += 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.petal.y -= 0.2F * slowCycle;
        this.underPetal.y -= 0.25F * fastCycle;
        this.underPetal.yRot += 3.0F * Mth.DEG_TO_RAD * slowCycle;
        this.petal2.xRot -= 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.petal3.xRot -= 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.petal4.xRot -= 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.petal5.xRot -= 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.petal6.xRot -= 2.0F * Mth.DEG_TO_RAD * fastCycle;
        this.dart.y -= 0.15F * fastCycle;
        this.dart.yRot += 8.0F * Mth.DEG_TO_RAD * slowCycle;
        this.outerDart.y -= 0.25F * fastCycle;
        this.outerDart.yRot -= 5.0F * Mth.DEG_TO_RAD * slowCycle;

        this.animate(aechorPlant.attackAnimationState, AechorPlantAnimation.ATTACK, ageInTicks, 1.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
