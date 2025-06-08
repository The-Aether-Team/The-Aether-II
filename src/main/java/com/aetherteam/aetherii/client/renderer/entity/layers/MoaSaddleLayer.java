package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddleModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class MoaSaddleLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle.png");
    private static final ResourceLocation SADDLE_OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/saddle/moa_saddle_overlay.png");
    private final MoaSaddleModel saddle;

    public MoaSaddleLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.saddle = new MoaSaddleModel(modelSet.bakeLayer(AetherIIModelLayers.MOA_SADDLE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, MoaRenderState moa, float netHeadYaw, float headPitch) {
        if (!moa.isInvisible && moa.isSaddled()) {
            ItemStack saddle = moa.saddle;
            int color = IClientItemExtensions.of(saddle).getDefaultDyeColor(saddle);

            this.saddle.setupAnim(moa);
            this.saddle.renderToBuffer(poseStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(SADDLE_TEXTURE), false), packedLight, OverlayTexture.NO_OVERLAY, color);
            this.saddle.renderToBuffer(poseStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(SADDLE_OVERLAY_TEXTURE), false), packedLight, OverlayTexture.NO_OVERLAY, -1);
        }
    }
}
