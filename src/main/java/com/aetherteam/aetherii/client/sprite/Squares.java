package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public record Squares(List<ResourceLocation> textures, int width, int height) implements SpriteSource {
    public static final Codec<Squares> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter(Squares::textures),
            Codec.INT.fieldOf("width").forGetter(Squares::width),
            Codec.INT.fieldOf("height").forGetter(Squares::height)
    ).apply(instance, Squares::new));

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for (ResourceLocation location : this.textures()) {
            ResourceLocation originalTextureLocation = TEXTURE_ID_CONVERTER.idToFile(location);
            Optional<Resource> originalTexture = resourceManager.getResource(originalTextureLocation);
            if (originalTexture.isPresent()) {
                LazyLoadedImage originalImage = new LazyLoadedImage(originalTextureLocation, originalTexture.get(), this.width() * this.height());
                for (int x = 0; x < this.width(); x++) {
                    for (int y = 0; y < this.height(); y++) {
                        ResourceLocation outputLocation = location.withSuffix("_" + x + "_" + y);
                        output.add(outputLocation, new SquaresSpriteSupplier(originalImage, x * 16, y * 16, outputLocation));
                    }
                }
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return AetherIISpriteSourceTypes.SQUARES;
    }

    public record SquaresSpriteSupplier(LazyLoadedImage baseImage, int xOffset, int yOffset, ResourceLocation outputLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        @Override
        public SpriteContents get() {
            try {
                NativeImage nativeBaseImage = this.baseImage().get();
                NativeImage nativeImage = new NativeImage(16, 16, false);

                for (int y = 0; y < 16; y++) {
                    for (int x = 0; x < 16; x++) {
                        int color = nativeBaseImage.getPixelRGBA(x + this.xOffset(), y + this.yOffset());
                        nativeImage.setPixelRGBA(x, y, color);
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage, AnimationMetadataSection.EMPTY);
            } catch (IOException | IllegalArgumentException exception) {
                AetherII.LOGGER.error("Unable to create square sprite at {}", this.outputLocation(), exception);
            } finally {
                this.baseImage().release();
            }
            return null;
        }

        @Override
        public void discard() {
            this.baseImage().release();
        }
    }
}
