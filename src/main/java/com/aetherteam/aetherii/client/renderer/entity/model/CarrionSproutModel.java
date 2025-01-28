package com.aetherteam.aetherii.client.renderer.entity.model;// Made with Blockbench 4.12.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.animation.CarrionSproutAnimations;
import com.aetherteam.aetherii.client.renderer.entity.state.CarrionSproutRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CarrionSproutModel<T extends CarrionSproutRenderState> extends EntityModel<T> {
    private final ModelPart stem;
    private final ModelPart stemLeefRoot;
    private final ModelPart stemLeef;
    private final ModelPart stemLeef2;
    private final ModelPart stemLeef3;
    private final ModelPart stemLeef4;
    private final ModelPart stemLeef5;
    private final ModelPart stemLeef6;
    private final ModelPart stemLeef7;
    private final ModelPart stemLeef8;
    private final ModelPart flowerRoot;
    private final ModelPart flower;
    private final ModelPart flower_part;
    private final ModelPart flower_part2;
    private final ModelPart flower_part3;
    private final ModelPart flower2;
    private final ModelPart flower_part4;
    private final ModelPart flower_part5;
    private final ModelPart flower_part6;
    private final ModelPart flower3;
    private final ModelPart flower_part7;
    private final ModelPart flower_part8;
    private final ModelPart flower_part9;
    private final ModelPart flower4;
    private final ModelPart flower_part10;
    private final ModelPart flower_part11;
    private final ModelPart flower_part12;
    private final ModelPart flower5;
    private final ModelPart flower6;

    public CarrionSproutModel(ModelPart root) {
        super(root);
        this.stem = root.getChild("stem");
        this.stemLeefRoot = this.stem.getChild("stemLeefRoot");
        this.stemLeef = this.stemLeefRoot.getChild("stemLeef");
        this.stemLeef2 = this.stemLeefRoot.getChild("stemLeef2");
        this.stemLeef3 = this.stemLeefRoot.getChild("stemLeef3");
        this.stemLeef4 = this.stemLeefRoot.getChild("stemLeef4");
        this.stemLeef5 = this.stemLeefRoot.getChild("stemLeef5");
        this.stemLeef6 = this.stemLeefRoot.getChild("stemLeef6");
        this.stemLeef7 = this.stemLeefRoot.getChild("stemLeef7");
        this.stemLeef8 = this.stemLeefRoot.getChild("stemLeef8");
        this.flowerRoot = this.stem.getChild("flowerRoot");
        this.flower = this.flowerRoot.getChild("flower");
        this.flower_part = this.flower.getChild("flower_part");
        this.flower_part2 = this.flower.getChild("flower_part2");
        this.flower_part3 = this.flower.getChild("flower_part3");
        this.flower2 = this.flowerRoot.getChild("flower2");
        this.flower_part4 = this.flower2.getChild("flower_part4");
        this.flower_part5 = this.flower2.getChild("flower_part5");
        this.flower_part6 = this.flower2.getChild("flower_part6");
        this.flower3 = this.flowerRoot.getChild("flower3");
        this.flower_part7 = this.flower3.getChild("flower_part7");
        this.flower_part8 = this.flower3.getChild("flower_part8");
        this.flower_part9 = this.flower3.getChild("flower_part9");
        this.flower4 = this.flowerRoot.getChild("flower4");
        this.flower_part10 = this.flower4.getChild("flower_part10");
        this.flower_part11 = this.flower4.getChild("flower_part11");
        this.flower_part12 = this.flower4.getChild("flower_part12");
        this.flower5 = this.flowerRoot.getChild("flower5");
        this.flower6 = this.flowerRoot.getChild("flower6");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition stem = partdefinition.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(24, 45).addBox(-1.5F, 2.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition stemLeefRoot = stem.addOrReplaceChild("stemLeefRoot", CubeListBuilder.create().texOffs(18, 37).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition stemLeef = stemLeefRoot.addOrReplaceChild("stemLeef", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.5F, -2.0F, 1.3963F, 0.7854F, 0.0F));

        PartDefinition stemLeef2 = stemLeefRoot.addOrReplaceChild("stemLeef2", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.5F, -2.0F, 1.3963F, -7.0686F, 0.0F));

        PartDefinition stemLeef3 = stemLeefRoot.addOrReplaceChild("stemLeef3", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.5F, 2.0F, 1.3963F, -8.6394F, 0.0F));

        PartDefinition stemLeef4 = stemLeefRoot.addOrReplaceChild("stemLeef4", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.5F, 2.0F, 1.3963F, -3.927F, 0.0F));

        PartDefinition stemLeef5 = stemLeefRoot.addOrReplaceChild("stemLeef5", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.5F, 0.0F, 1.3963F, -4.7124F, 0.0F));

        PartDefinition stemLeef6 = stemLeefRoot.addOrReplaceChild("stemLeef6", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.5F, 0.0F, 1.3963F, -7.854F, 0.0F));

        PartDefinition stemLeef7 = stemLeefRoot.addOrReplaceChild("stemLeef7", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 2.0F, 1.3963F, -9.4248F, 0.0F));

        PartDefinition stemLeef8 = stemLeefRoot.addOrReplaceChild("stemLeef8", CubeListBuilder.create().texOffs(6, 37).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 1.3963F, -6.2832F, 0.0F));

        PartDefinition flowerRoot = stem.addOrReplaceChild("flowerRoot", CubeListBuilder.create().texOffs(10, 25).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition flower = flowerRoot.addOrReplaceChild("flower", CubeListBuilder.create().texOffs(19, 6).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 5.0F, 0.1F, -9.4248F, 0.0F));

        PartDefinition flower_part = flower.addOrReplaceChild("flower_part", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -0.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition flower_part2 = flower.addOrReplaceChild("flower_part2", CubeListBuilder.create().texOffs(15, 6).addBox(3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower_part3 = flower.addOrReplaceChild("flower_part3", CubeListBuilder.create().texOffs(15, 6).addBox(-3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower2 = flowerRoot.addOrReplaceChild("flower2", CubeListBuilder.create().texOffs(19, 6).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.0F, 0.0F, 0.1F, -4.7124F, 0.0F));

        PartDefinition flower_part4 = flower2.addOrReplaceChild("flower_part4", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -0.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition flower_part5 = flower2.addOrReplaceChild("flower_part5", CubeListBuilder.create().texOffs(15, 6).addBox(3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower_part6 = flower2.addOrReplaceChild("flower_part6", CubeListBuilder.create().texOffs(15, 6).addBox(-3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower3 = flowerRoot.addOrReplaceChild("flower3", CubeListBuilder.create().texOffs(19, 6).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.0F, 0.0F, 0.1F, -7.854F, 0.0F));

        PartDefinition flower_part7 = flower3.addOrReplaceChild("flower_part7", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -0.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition flower_part8 = flower3.addOrReplaceChild("flower_part8", CubeListBuilder.create().texOffs(15, 6).addBox(3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower_part9 = flower3.addOrReplaceChild("flower_part9", CubeListBuilder.create().texOffs(15, 6).addBox(-3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower4 = flowerRoot.addOrReplaceChild("flower4", CubeListBuilder.create().texOffs(19, 6).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -5.0F, 0.1F, -6.2832F, 0.0F));

        PartDefinition flower_part10 = flower4.addOrReplaceChild("flower_part10", CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -0.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition flower_part11 = flower4.addOrReplaceChild("flower_part11", CubeListBuilder.create().texOffs(15, 6).addBox(3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower_part12 = flower4.addOrReplaceChild("flower_part12", CubeListBuilder.create().texOffs(15, 6).addBox(-3.0F, -8.0F, 0.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flower5 = flowerRoot.addOrReplaceChild("flower5", CubeListBuilder.create().texOffs(20, 6).addBox(0.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition flower6 = flowerRoot.addOrReplaceChild("flower6", CubeListBuilder.create().texOffs(20, 6).addBox(0.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.animate(entity.trapActiveAnimationState, CarrionSproutAnimations.trap_active, entity.ageInTicks);
        this.animate(entity.trapDeactiveAnimationState, CarrionSproutAnimations.trap_deactive, entity.ageInTicks);
    }
}