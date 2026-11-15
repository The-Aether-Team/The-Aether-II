package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.model.LykonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.LykonRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class LykonScarfLayer extends RenderLayer<LykonRenderState, LykonModel<LykonRenderState>> {
    private static final Identifier COLLAR_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/lykon/lykon_scarf.png");
    private final LykonModel model;

    public LykonScarfLayer(RenderLayerParent<LykonRenderState, LykonModel<LykonRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new LykonModel(modelSet.bakeLayer(AetherIIModelLayers.LYKON_SCARF));
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, LykonRenderState renderState, float yRot, float xRot_) {
        int color = getColor(renderState);
        if (!renderState.isInvisible && renderState.tame) {
            this.model.setupAnim(renderState);
            collector.order(1).submitModel(this.getParentModel(), renderState, poseStack, AetherIIRenderTypes.entityDitherNoCull(COLLAR_LOCATION), lightCoords, OverlayTexture.NO_OVERLAY, color, null, renderState.outlineColor, null);
        }

    }

    protected int getColor(LykonRenderState renderState) {
        int color = renderState.collarColor.getTextureDiffuseColor();
        return color;
    }
}