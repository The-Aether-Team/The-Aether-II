package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.AechorPlantRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;

public class AechorPlantModel extends EntityModel<AechorPlantRenderState> {
    public static final AnimationHolder PASSIVE_ANIMATION = Model.getAnimation(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aechor_plant/passive"));
    public static final AnimationHolder ATTACK_ANIMATION = Model.getAnimation(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aechor_plant/attack"));

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
        super(root);
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

        PartDefinition stem_r1 = main.addOrReplaceChild("stem_r1", CubeListBuilder.create().texOffs(28, 6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition stem_r2 = main.addOrReplaceChild("stem_r2", CubeListBuilder.create().texOffs(28, 6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition petal = main.addOrReplaceChild("petal", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition petal2 = petal.addOrReplaceChild("petal2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition petal_r1 = petal2.addOrReplaceChild("petal_r1", CubeListBuilder.create().texOffs(-8, 0).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal3 = petal.addOrReplaceChild("petal3", CubeListBuilder.create(), PartPose.offsetAndRotation(1.9487F, 0.0F, -0.4499F, 0.0F, -1.2566F, 0.0F));

        PartDefinition petal_r2 = petal3.addOrReplaceChild("petal_r2", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal4 = petal.addOrReplaceChild("petal4", CubeListBuilder.create(), PartPose.offsetAndRotation(1.1756F, 0.0F, 1.618F, 0.0F, -2.5133F, 0.0F));

        PartDefinition petal_r3 = petal4.addOrReplaceChild("petal_r3", CubeListBuilder.create().texOffs(-8, 0).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal5 = petal.addOrReplaceChild("petal5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.1756F, 0.0F, 1.618F, 0.0F, 2.5133F, 0.0F));

        PartDefinition petal_r4 = petal5.addOrReplaceChild("petal_r4", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition petal6 = petal.addOrReplaceChild("petal6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9487F, 0.0F, -0.4499F, 0.0F, 1.2566F, 0.0F));

        PartDefinition petal_r5 = petal6.addOrReplaceChild("petal_r5", CubeListBuilder.create().texOffs(-8, 0).mirror().addBox(-3.0F, 0.0F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 0).mirror().addBox(-3.0F, 0.01F, -10.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition under_petal = main.addOrReplaceChild("under_petal", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 0.0F));

        PartDefinition under_petal_r1 = under_petal.addOrReplaceChild("under_petal_r1", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0543F, 0.7854F, 3.1416F));

        PartDefinition under_petal_r2 = under_petal.addOrReplaceChild("under_petal_r2", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.7854F, 0.0F));

        PartDefinition under_petal_r3 = under_petal.addOrReplaceChild("under_petal_r3", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0543F, -0.7854F, 3.1416F));

        PartDefinition under_petal_r4 = under_petal.addOrReplaceChild("under_petal_r4", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.7854F, 0.0F));

        PartDefinition dart = main.addOrReplaceChild("dart", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition dart_r1 = dart.addOrReplaceChild("dart_r1", CubeListBuilder.create().texOffs(24, 5).addBox(-0.25F, -4.25F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, -1.309F, 3.1416F));

        PartDefinition dart_r2 = dart.addOrReplaceChild("dart_r2", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, -0.2182F, 3.1416F));

        PartDefinition dart_r3 = dart.addOrReplaceChild("dart_r3", CubeListBuilder.create().texOffs(24, 5).addBox(-0.25F, -4.5F, -2.25F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8362F, 0.8727F, 3.1416F));

        PartDefinition dart_r4 = dart.addOrReplaceChild("dart_r4", CubeListBuilder.create().texOffs(24, 5).addBox(0.0F, -4.5F, -2.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.75F, 0.3054F, 1.0472F, 0.0F));

        PartDefinition dart_r5 = dart.addOrReplaceChild("dart_r5", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition dart_r6 = dart.addOrReplaceChild("dart_r6", CubeListBuilder.create().texOffs(24, 5).addBox(-0.5F, -4.75F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -1.0036F, 0.0F));

        PartDefinition outer_dart = main.addOrReplaceChild("outer_dart", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition outer_dart_r1 = outer_dart.addOrReplaceChild("outer_dart_r1", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -4.75F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -0.48F, 0.0F));

        PartDefinition outer_dart_r2 = outer_dart.addOrReplaceChild("outer_dart_r2", CubeListBuilder.create().texOffs(26, 5).addBox(-0.25F, -4.25F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -1.309F, 0.0F));

        PartDefinition outer_dart_r3 = outer_dart.addOrReplaceChild("outer_dart_r3", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, -0.7418F, 3.1416F));

        PartDefinition outer_dart_r4 = outer_dart.addOrReplaceChild("outer_dart_r4", CubeListBuilder.create().texOffs(26, 5).addBox(-0.25F, -4.5F, -2.25F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.3491F, 3.1416F));

        PartDefinition outer_dart_r5 = outer_dart.addOrReplaceChild("outer_dart_r5", CubeListBuilder.create().texOffs(26, 5).addBox(0.0F, -4.5F, -2.5F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.75F, 0.48F, 1.5708F, -0.3054F));

        PartDefinition outer_dart_r6 = outer_dart.addOrReplaceChild("outer_dart_r6", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -5.0F, -2.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(AechorPlantRenderState aechorPlant) {
        super.setupAnim(aechorPlant);
        this.animateWalk(PASSIVE_ANIMATION, aechorPlant.ageInTicks, aechorPlant.ageInTicks, 1.0F, 1.0F);
        this.animate(aechorPlant.attackAnimationState, ATTACK_ANIMATION, aechorPlant.ageInTicks, 1.0F);
    }
}
