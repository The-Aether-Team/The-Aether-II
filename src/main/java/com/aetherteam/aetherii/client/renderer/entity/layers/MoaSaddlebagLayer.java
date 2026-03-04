package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaLargeSaddlebagModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddlebagModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class MoaSaddlebagLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private static final Identifier SADDLEBAG_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag.png");
    private static final Identifier LARGE_SADDLEBAG_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag_large.png");

    private final MoaSaddlebagModel saddlebag;
    private final MoaLargeSaddlebagModel largeSaddlebag;

    public MoaSaddlebagLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddlebag = new MoaSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLEBAG));
        this.largeSaddlebag = new MoaLargeSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_LARGE_SADDLEBAG));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, MoaRenderState moa, float v, float v1) {
        if (!moa.isInvisible) {
            float opacity = moa.opacity;
            int color = ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F);

            if (moa.saddlebag.is(AetherIIItems.MOA_SADDLEBAG)) {
                this.saddlebag.setupAnim(moa);
                submitNodeCollector.submitModel(this.saddlebag, moa, poseStack, AetherIIRenderTypes.entityDitherNoCull(SADDLEBAG_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, color, null);
            } else if (moa.saddlebag.is(AetherIIItems.LARGE_MOA_SADDLEBAG)) {
                this.largeSaddlebag.setupAnim(moa);
                submitNodeCollector.submitModel(this.largeSaddlebag, moa, poseStack, AetherIIRenderTypes.entityDitherNoCull(LARGE_SADDLEBAG_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, color, null);
            }
        }
    }
}
