package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.AerbunnyRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class AerbunnyCollarLayer extends RenderLayer<AerbunnyRenderState, AerbunnyModel> {
    private static final Identifier COLLAR_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny_collar.png");
    private final AerbunnyModel model;

    public AerbunnyCollarLayer(RenderLayerParent<AerbunnyRenderState, AerbunnyModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new AerbunnyModel(modelSet.bakeLayer(AetherIIModelLayers.AERBUNNY_COLLAR));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, AerbunnyRenderState renderState, float v, float v1) {
        if (renderState.tame) {
            int color = this.getColor(renderState);
            if (!renderState.isInvisible) {
                this.model.setupAnim(renderState);
                submitNodeCollector.submitModel(this.model, renderState, poseStack, AetherIIRenderTypes.entityDitherNoCull(COLLAR_LOCATION), packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color, null);
            }
        }
    }

    protected int getColor(AerbunnyRenderState renderState) {
        int color = renderState.collarColor.getTextureDiffuseColor();
        float opacity = AerbunnyRenderer.calculateOpacity(renderState);
        if (opacity < 1.0F) {
            return ARGB.color(opacity, color);
        }
        return color;
    }

}
