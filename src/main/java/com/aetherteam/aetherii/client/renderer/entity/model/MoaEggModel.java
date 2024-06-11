package com.aetherteam.aetherii.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class MoaEggModel extends Model {
    private final ModelPart moa_egg;

    public MoaEggModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.moa_egg = root.getChild("moa_egg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition moa_egg = partdefinition.addOrReplaceChild("moa_egg", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        moa_egg.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}