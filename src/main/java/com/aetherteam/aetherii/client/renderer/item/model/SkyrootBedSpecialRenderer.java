package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;

public class SkyrootBedSpecialRenderer {
    private final SkyrootBedRenderer skyrootBedRenderer;
    private final ResourceLocation location;

    public SkyrootBedSpecialRenderer(SkyrootBedRenderer skyrootBedRenderer, ResourceLocation location) {
        this.skyrootBedRenderer = skyrootBedRenderer;
        this.location = location;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        this.skyrootBedRenderer.renderInHand(poseStack, buffer, packedLight, packedOverlay, this.location);
    }

    public record Unbaked(ResourceLocation texture) {
        public static final MapCodec<SkyrootBedSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyrootBedSpecialRenderer.Unbaked::texture)
        ).apply(instance, SkyrootBedSpecialRenderer.Unbaked::new));

        public SkyrootBedSpecialRenderer bake(EntityModelSet modelSet) {
            return new SkyrootBedSpecialRenderer(new SkyrootBedRenderer(modelSet), this.texture());
        }

        public MapCodec<SkyrootBedSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
