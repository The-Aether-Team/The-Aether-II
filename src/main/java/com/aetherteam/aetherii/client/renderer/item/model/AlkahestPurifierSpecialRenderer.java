package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.blockentity.AlkahestPurifierRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Set;

public class AlkahestPurifierSpecialRenderer implements NoDataSpecialModelRenderer {
    private final AlkahestPurifierRenderer renderer;
    private final float openness;

    public AlkahestPurifierSpecialRenderer(AlkahestPurifierRenderer renderer, float openness) {
        this.renderer = renderer;
        this.openness = openness;
    }

    public void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean partialTick) {
        this.renderer.render(poseStack, buffer, packedLight, packedOverlay, Direction.SOUTH.toYRot(), 0, this.openness);
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
        //todo
    }

    @OnlyIn(Dist.CLIENT)
    public record Unbaked(float openness) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(AlkahestPurifierSpecialRenderer.Unbaked::openness)
        ).apply(instance, AlkahestPurifierSpecialRenderer.Unbaked::new));

        public Unbaked() {
            this(0.0F);
        }

        public Unbaked(float openness) {
            this.openness = openness;
        }

        public MapCodec<AlkahestPurifierSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            return new AlkahestPurifierSpecialRenderer(new AlkahestPurifierRenderer(entityModelSet), this.openness);
        }
    }
}
