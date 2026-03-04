package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.state.TamableRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public abstract class TamableCollarLayer<S extends TamableRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private final Identifier collarLocation;
    private final M model;

    public TamableCollarLayer(RenderLayerParent<S, M> renderer, M model, Identifier collarLocation) {
        super(renderer);
        this.collarLocation = collarLocation;
        this.model = model;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S livingEntity, float v, float v1) {
        if (livingEntity.tame) {
            int color = this.getColor(livingEntity);
            coloredCutoutModelCopyLayerRender(this.model, this.collarLocation, poseStack, submitNodeCollector, packedLight, livingEntity, color, 1);
        }
    }

    public abstract int getColor(S entity);
}
