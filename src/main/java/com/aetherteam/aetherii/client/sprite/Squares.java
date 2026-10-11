package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public record Squares(List<Identifier> textures, int width, int height) implements SpriteSource {
    public static final MapCodec<Squares> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Identifier.CODEC).fieldOf("textures").forGetter(Squares::textures),
            Codec.INT.fieldOf("width").forGetter(Squares::width),
            Codec.INT.fieldOf("height").forGetter(Squares::height)
    ).apply(instance, Squares::new));

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for (Identifier location : this.textures()) {
            Identifier  originalTextureLocation = TEXTURE_ID_CONVERTER.idToFile(location);
            Optional<Resource> originalTexture = resourceManager.getResource(originalTextureLocation);
            if (originalTexture.isPresent()) {
                LazyLoadedImage originalImage = new LazyLoadedImage(originalTextureLocation, originalTexture.get(), this.width() * this.height());
                for (int x = 0; x < this.width(); x++) {
                    for (int y = 0; y < this.height(); y++) {
                        Identifier  outputLocation = location.withSuffix("_" + x + "_" + y);
                        output.add(outputLocation, new SquaresSpriteSupplier(originalImage, x * 16, y * 16, outputLocation));
                    }
                }
            }
        }
    }

    @Override
    public MapCodec<? extends SpriteSource> codec() {
        return CODEC;
    }

    public record SquaresSpriteSupplier(LazyLoadedImage baseImage, int xOffset, int yOffset, Identifier outputLocation) implements SpriteSource.DiscardableLoader {
        @Nullable
        public SpriteContents get(SpriteResourceLoader p_295023_) {
            try {
                NativeImage nativeBaseImage = this.baseImage().get();
                NativeImage nativeImage = new NativeImage(16, 16, false);

                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        int color = nativeBaseImage.getPixel(i + this.xOffset(), j + yOffset());
                        nativeImage.setPixel(i, j, color);
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage);
            } catch (IOException | IllegalArgumentException ioexception) {
                AetherII.LOGGER.error("unable to create square sprite at {}", this.outputLocation(), ioexception);
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
