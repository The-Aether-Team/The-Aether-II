package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.state.TamableRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public abstract class TamableCollarLayer<S extends TamableRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private final ResourceLocation collarLocation;
    private final M model;

    public TamableCollarLayer(RenderLayerParent<S, M> renderer, M model, ResourceLocation collarLocation) {
        super(renderer);
        this.collarLocation = collarLocation;
        this.model = model;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S livingEntity, float netHeadYaw, float headPitch) {
        if (livingEntity.tame) {
            int color = this.getColor(livingEntity);
            coloredCutoutModelCopyLayerRender(this.model, this.collarLocation, poseStack, buffer, packedLight, livingEntity, color);
        }
    }

    public abstract int getColor(S entity);
}
