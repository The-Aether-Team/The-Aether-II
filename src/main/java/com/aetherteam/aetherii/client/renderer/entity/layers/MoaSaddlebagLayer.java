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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class MoaSaddlebagLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private static final ResourceLocation SADDLEBAG_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag.png");
    private static final ResourceLocation LARGE_SADDLEBAG_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddlebag/moa_saddlebag_large.png");

    private final MoaSaddlebagModel saddlebag;
    private final MoaLargeSaddlebagModel largeSaddlebag;

    public MoaSaddlebagLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddlebag = new MoaSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLEBAG));
        this.largeSaddlebag = new MoaLargeSaddlebagModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_LARGE_SADDLEBAG));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, MoaRenderState moa, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible) {
            float opacity = moa.opacity;
            int color = ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F);

            if (moa.saddlebag.is(AetherIIItems.MOA_SADDLEBAG)) {
                this.saddlebag.setupAnim(moa);
                this.saddlebag.renderToBuffer(poseStack, buffer.getBuffer(AetherIIRenderTypes.entityDitherNoCull(SADDLEBAG_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, color);
            } else if (moa.saddlebag.is(AetherIIItems.LARGE_MOA_SADDLEBAG)) {
                this.largeSaddlebag.setupAnim(moa);
                this.largeSaddlebag.renderToBuffer(poseStack, buffer.getBuffer(AetherIIRenderTypes.entityDitherNoCull(LARGE_SADDLEBAG_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, color);
            }
        }
    }
}
