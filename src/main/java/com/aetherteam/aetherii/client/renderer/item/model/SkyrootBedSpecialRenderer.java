package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

public class SkyrootBedSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SkyrootBedRenderer skyrootBedRenderer;
    private final Identifier location;

    public SkyrootBedSpecialRenderer(SkyrootBedRenderer skyrootBedRenderer, Identifier location) {
        this.skyrootBedRenderer = skyrootBedRenderer;
        this.location = location;
    }

    @Override
    public void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean partialTick) {
        this.skyrootBedRenderer.renderInHand(poseStack, buffer, packedLight, packedOverlay, this.location);
    }

    public void getExtents(Set<Vector3f> p_428290_) {
        this.skyrootBedRenderer.getExtents(p_428290_);
    }

    public record Unbaked(Identifier texture) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<SkyrootBedSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(SkyrootBedSpecialRenderer.Unbaked::texture)
        ).apply(instance, SkyrootBedSpecialRenderer.Unbaked::new));

        public MapCodec<SkyrootBedSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            return new SkyrootBedSpecialRenderer(new SkyrootBedRenderer(entityModelSet), this.texture());
        }
    }
}
