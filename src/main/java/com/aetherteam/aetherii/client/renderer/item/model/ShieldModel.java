package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;

public class ShieldModel {
    private static final Codec<List<ResourceLocation>> FOUR_TEXTURES_CODEC = ResourceLocation.CODEC.listOf().comapFlatMap((textures) ->
            textures.size() == 4 ? DataResult.success(textures) : DataResult.error(() -> "Expected exactly 4 textures"), (textures) -> textures);

    public static float px(float offset) {
        return offset / 16.0F;
    }

    public record Textures(List<ResourceLocation> front, List<ResourceLocation> back, ResourceLocation handle, ResourceLocation particle) {
        public static final Codec<ShieldModel.Textures> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                FOUR_TEXTURES_CODEC.fieldOf("front").forGetter(ShieldModel.Textures::front),
                FOUR_TEXTURES_CODEC.fieldOf("back").forGetter(ShieldModel.Textures::back),
                ResourceLocation.CODEC.fieldOf("handle").forGetter(ShieldModel.Textures::handle),
                ResourceLocation.CODEC.fieldOf("particle").forGetter(ShieldModel.Textures::particle)
        ).apply(instance, ShieldModel.Textures::new));
    }

    public record Unbaked(ResourceLocation parent, ShieldModel.Textures textures) {
        public static final MapCodec<ShieldModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("parent").forGetter(ShieldModel.Unbaked::parent),
                ShieldModel.Textures.CODEC.fieldOf("textures").forGetter(ShieldModel.Unbaked::textures)
        ).apply(instance, ShieldModel.Unbaked::new));

        public MapCodec<ShieldModel.Unbaked> type() {
            return MAP_CODEC;
        }

        public ShieldModel bake() {
            return new ShieldModel();
        }

        public void resolveDependencies(Consumer<ResourceLocation> resolver) {
            resolver.accept(this.parent());
        }
    }
}
