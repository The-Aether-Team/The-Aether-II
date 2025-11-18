package com.aetherteam.aetherii.client.renderer.entity.model;

import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SentryModel extends EntityModel<DetonationSentryRenderState> {
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone4;
    private final ModelPart bone3;
    private final ModelPart bone5;

    public SentryModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.bone4 = this.bone.getChild("bone4");
        this.bone3 = this.bone.getChild("bone3");
        this.bone5 = this.bone.getChild("bone5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -14.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(57, 0).addBox(-7.0F, -14.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(40, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, -4.0F, -7.0F, -0.2618F, -0.7854F, 0.0F));

        PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(40, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(7.0F, -4.0F, 7.0F, 0.2618F, 0.7854F, 0.0F));

        PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 30).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, -4.0F, -7.0F, -0.2618F, 0.7854F, 0.0F));

        PartDefinition bone5 = bone.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(17, 43).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-7.0F, -4.0F, 7.0F, 0.2618F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(DetonationSentryRenderState renderState) {
        super.setupAnim(renderState);

        if (renderState.awake) {
            float f = renderState.walkAnimationSpeed;
            float f1 = renderState.walkAnimationPos;
            this.bone2.xRot += Mth.cos(f1 * 0.6662F) * 1.4F * f;
            this.bone3.xRot += Mth.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f;
            this.bone4.xRot += Mth.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f;
            this.bone5.xRot += Mth.cos(f1 * 0.6662F) * 1.4F * f;
        }
    }
}
