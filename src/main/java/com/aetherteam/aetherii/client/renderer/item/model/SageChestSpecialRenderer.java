package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.SageChestRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SageChestModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SageChestSpecialRenderer {
    private static final Codec<ChestType> CHEST_TYPE_CODEC = StringRepresentable.fromEnum(ChestType::values);

    private final SageChestModel model;
    private final ResourceLocation texture;
    private final float openness;

    public SageChestSpecialRenderer(SageChestModel model, ResourceLocation texture, float openness) {
        this.model = model;
        this.texture = texture;
        this.openness = openness;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        SageChestRenderer.renderModel(this.model, this.texture, this.openness, 180.0F, poseStack, buffer, packedLight, packedOverlay);
    }

    public static record Unbaked(ResourceLocation texture, float openness, ChestType chestType) {
        public static final MapCodec<SageChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(SageChestSpecialRenderer.Unbaked::texture),
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(SageChestSpecialRenderer.Unbaked::openness),
                CHEST_TYPE_CODEC.optionalFieldOf("chest_type", ChestType.SINGLE).forGetter(SageChestSpecialRenderer.Unbaked::chestType)
        ).apply(instance, SageChestSpecialRenderer.Unbaked::new));

        public Unbaked(ResourceLocation texture, ChestType chestType) {
            this(texture, 0.0F, chestType);
        }

        public Unbaked(ResourceLocation texture) {
            this(texture, 0.0F, ChestType.SINGLE);
        }

        public MapCodec<SageChestSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public SageChestSpecialRenderer bake(EntityModelSet modelSet) {
            return new SageChestSpecialRenderer(new SageChestModel(modelSet.bakeLayer(layer(this.chestType))), SageChestRenderer.texture(this.chestType), this.openness);
        }

        private static ModelLayerLocation layer(ChestType chestType) {
            return switch (chestType) {
                case LEFT -> AetherIIModelLayers.DOUBLE_SAGE_CHEST_LEFT;
                case RIGHT -> AetherIIModelLayers.DOUBLE_SAGE_CHEST_RIGHT;
                case SINGLE -> AetherIIModelLayers.SAGE_CHEST;
            };
        }
    }
}
