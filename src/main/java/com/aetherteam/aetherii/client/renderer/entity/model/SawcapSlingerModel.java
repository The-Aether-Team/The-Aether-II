package com.aetherteam.aetherii.client.renderer.entity.model;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aetherteam.aetherii.client.renderer.entity.state.SawcapSlingerRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SawcapSlingerModel<T extends SawcapSlingerRenderState> extends EntityModel<T> {
    private final ModelPart leg_left;
    private final ModelPart leg_right;
    private final ModelPart body;
    private final ModelPart sawcap_1;
    private final ModelPart sawcap_2;
    private final ModelPart sawcap_3;
    private final ModelPart sawcap_4;
    private final ModelPart sawcap_5;
    private final ModelPart sawcap_6;

    public SawcapSlingerModel(ModelPart root) {
        super(root);
        this.leg_left = root.getChild("leg_left");
        this.leg_right = root.getChild("leg_right");
        this.body = root.getChild("body");
        this.sawcap_1 = this.body.getChild("sawcap_1");
        this.sawcap_2 = this.body.getChild("sawcap_2");
        this.sawcap_3 = this.body.getChild("sawcap_3");
        this.sawcap_4 = this.body.getChild("sawcap_4");
        this.sawcap_5 = this.body.getChild("sawcap_5");
        this.sawcap_6 = this.body.getChild("sawcap_6");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg_left = partdefinition.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(86, 101).addBox(-11.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(38, 101).addBox(-7.0F, -4.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 101).addBox(-12.0F, -7.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(92, 39).addBox(-13.0F, -13.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 24.0F, 0.0F));

        PartDefinition leg_right = partdefinition.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(46, 103).addBox(2.0F, -4.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(98, 101).addBox(0.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(70, 101).addBox(3.0F, -7.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 94).addBox(3.0F, -13.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(52, 60).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 76).addBox(-3.0F, -22.0F, -3.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 27).addBox(-6.0F, -31.0F, 0.0F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -31.0F, -13.0F, 0.0F, 21.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -31.0F, -13.0F, 0.0F, 21.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition sawcap_1 = body.addOrReplaceChild("sawcap_1", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -7.0F, -9.0F));

        PartDefinition sawcap_2 = body.addOrReplaceChild("sawcap_2", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -10.0F, -8.0F));

        PartDefinition sawcap_3 = body.addOrReplaceChild("sawcap_3", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -13.0F, 9.0F));

        PartDefinition sawcap_4 = body.addOrReplaceChild("sawcap_4", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -16.0F, 9.0F));

        PartDefinition sawcap_5 = body.addOrReplaceChild("sawcap_5", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -19.0F, -9.0F));

        PartDefinition sawcap_6 = body.addOrReplaceChild("sawcap_6", CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(92, 51).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -3.0F, 10.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity) {
        super.setupAnim(entity);
        this.body.yRot = entity.rotate * (float) (Math.PI / 180.0);
    }
}