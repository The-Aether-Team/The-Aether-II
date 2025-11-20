package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.ButterflyRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;

public class ButterflyModel extends EntityModel<ButterflyRenderState> {
    public static final AnimationHolder FLY_ANIMATION = Model.getAnimation(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "butterfly/fly"));
    private final KeyframeAnimation flyAnimation;

    private final ModelPart butterfly;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public ButterflyModel(ModelPart root) {
        super(root);
        this.flyAnimation = FLY_ANIMATION.get().bake(root);
        this.butterfly = root.getChild("butterfly");
        this.rightWing = this.butterfly.getChild("right_wing");
        this.leftWing = this.butterfly.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition butterfly = partdefinition.addOrReplaceChild("butterfly", CubeListBuilder.create().texOffs(-26, 26).addBox(-4.0F, -2.0F, -17.0F, 8.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 4.0F));
        butterfly.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-26, 0).addBox(-13.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        butterfly.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -17.0F, 13.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(ButterflyRenderState renderState) {
        super.setupAnim(renderState);
        float f = renderState.walkAnimationSpeed;
        float f1 = renderState.walkAnimationPos;
        this.rightWing.x -= renderState.wingXOffset;
        this.leftWing.x += renderState.wingXOffset;
        this.rightWing.zRot += renderState.wingZRotation;
        this.leftWing.zRot -= renderState.wingZRotation;
        this.flyAnimation.applyWalk(f1, f, 1.0F, 1.5F);
    }
}
