package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.UnknownNullability;

public class MoaEyesLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    private final TextureAtlas moaEyesAtlas;

    public MoaEyesLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EntityRendererProvider.@UnknownNullability Context context) {
        super(renderer);
        this.moaEyesAtlas = context.getAtlas(AetherIIAtlases.MOA_EYES_ID);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, MoaRenderState moa, float v, float v1) {
        if (!moa.isInvisible && !moa.hasSpecialTexture()) {
            String name = moa.isBaby ? "moa_baby_eyes" : "moa_eyes";
            Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "entity/mobs/moa/" + name + "_" + moa.eyeColor);
            TextureAtlasSprite sprite = this.moaEyesAtlas.getSprite(texture);
            float opacity = moa.opacity;

            submitNodeCollector.submitModel(this.getParentModel(), moa, poseStack, AetherIIRenderTypes.entityDitherNoCull(AetherIIAtlases.MOA_EYES_SHEET), packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F), sprite, moa.outlineColor, null);
        }
    }
}
