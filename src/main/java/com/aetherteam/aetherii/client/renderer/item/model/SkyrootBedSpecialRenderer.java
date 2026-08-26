package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class SkyrootBedSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SkyrootBedRenderer skyrootBedRenderer;
    private final Identifier location;

    public SkyrootBedSpecialRenderer(SkyrootBedRenderer skyrootBedRenderer, Identifier location) {
        this.skyrootBedRenderer = skyrootBedRenderer;
        this.location = location;
    }

    @Override
    public void getExtents(Consumer<Vector3fc> p_428290_) {
        this.skyrootBedRenderer.getExtents(p_428290_);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        this.skyrootBedRenderer.renderInHand(poseStack, submitNodeCollector, i, i1, this.location);
    }

    public record Unbaked(Identifier texture) implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<SkyrootBedSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(SkyrootBedSpecialRenderer.Unbaked::texture)
        ).apply(instance, SkyrootBedSpecialRenderer.Unbaked::new));

        @Override
        public @Nullable NoDataSpecialModelRenderer bake(BakingContext bakingContext) {
            return new SkyrootBedSpecialRenderer(new SkyrootBedRenderer(bakingContext.entityModelSet()), this.texture());
        }

        public MapCodec<SkyrootBedSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

    }
}
