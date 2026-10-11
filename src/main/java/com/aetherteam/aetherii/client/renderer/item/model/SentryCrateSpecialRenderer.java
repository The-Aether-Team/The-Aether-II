package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class SentryCrateSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SentryCrateModel model;
    private final SpriteId spriteId;
    private final SpriteGetter sprites;

    public SentryCrateSpecialRenderer(SpriteGetter context, SentryCrateModel model, SpriteId spriteId) {
        this.sprites = context;
        this.model = model;
        this.spriteId = spriteId;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, this.spriteId.renderType(RenderTypes::entitySolid), i, i1, -1, this.sprites.get(spriteId), i2, null);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack posestack = new PoseStack();
        this.model.root().getExtentsForGui(posestack, consumer);
    }

    public record Unbaked(Identifier texture) implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<SentryCrateSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(SentryCrateSpecialRenderer.Unbaked::texture)
        ).apply(instance, SentryCrateSpecialRenderer.Unbaked::new));

        public MapCodec<SentryCrateSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public NoDataSpecialModelRenderer bake(BakingContext context) {
            SentryCrateModel model = new SentryCrateModel(context.entityModelSet().bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
            SpriteId spriteId = AetherIIAtlases.SENTRY_CRATE_MAPPER.apply(this.texture);
            return new SentryCrateSpecialRenderer(context.sprites(), model,spriteId);
        }
    }
}
