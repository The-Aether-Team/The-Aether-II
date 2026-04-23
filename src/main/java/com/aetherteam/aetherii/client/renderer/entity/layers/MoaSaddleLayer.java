package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddleModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class MoaSaddleLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private static final Identifier SADDLE_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle.png");
    private static final Identifier SADDLE_OVERLAY_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle_overlay.png");
    private final MoaSaddleModel saddle;

    public MoaSaddleLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddle = new MoaSaddleModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, MoaRenderState moa, float v, float v1) {
        if (!moa.isInvisible && moa.isSaddled()) {

            this.saddle.setupAnim(moa);
            submitNodeCollector.submitModel(this.saddle, moa, poseStack, AetherIIRenderTypes.entityDitherNoCull(SADDLE_TEXTURE), moa.lightCoords, OverlayTexture.NO_OVERLAY, ARGB.white(moa.opacity), null, moa.outlineColor, null);
            submitNodeCollector.submitModel(this.saddle, moa, poseStack, AetherIIRenderTypes.entityDitherNoCull(SADDLE_OVERLAY_TEXTURE), moa.lightCoords, OverlayTexture.NO_OVERLAY, ARGB.white(moa.opacity), null, moa.outlineColor, null);
        }
    }
}
