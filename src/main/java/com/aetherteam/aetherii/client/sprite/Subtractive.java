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
import java.util.Map;
import java.util.Optional;

public record Subtractive(List<Identifier> textures, Map<String, Identifier> overlays) implements SpriteSource {
    public static final MapCodec<Subtractive> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Identifier.CODEC).fieldOf("textures").forGetter(Subtractive::textures),
            Codec.unboundedMap(Codec.STRING, Identifier.CODEC).fieldOf("overlays").forGetter(Subtractive::overlays)
    ).apply(instance, Subtractive::new));

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for (Identifier location : this.textures) {
            Identifier originalTextureLocation = TEXTURE_ID_CONVERTER.idToFile(location);
            Optional<Resource> originalTexture = resourceManager.getResource(originalTextureLocation);
            if (originalTexture.isPresent()) {
                LazyLoadedImage originalImage = new LazyLoadedImage(originalTextureLocation, originalTexture.get(), this.overlays.size());
                for (Map.Entry<String, Identifier> overlayEntry : this.overlays.entrySet()) {
                    Identifier overlayTextureLocation = TEXTURE_ID_CONVERTER.idToFile(overlayEntry.getValue());
                    Optional<Resource> overlayTexture = resourceManager.getResource(overlayTextureLocation);
                    if (overlayTexture.isPresent()) {
                        LazyLoadedImage overlayImage = new LazyLoadedImage(overlayTextureLocation, overlayTexture.get(), this.textures.size());
                        Identifier outputLocation = location.withSuffix("_" + overlayEntry.getKey());
                        output.add(outputLocation, new SubtractiveSpriteSupplier(originalImage, overlayImage, outputLocation));
                    }
                }
            }
        }
    }

    @Override
    public MapCodec<? extends SpriteSource> codec() {
        return CODEC;
    }

    public record SubtractiveSpriteSupplier(LazyLoadedImage baseImage, LazyLoadedImage overlayImage, Identifier outputLocation) implements SpriteSource.DiscardableLoader {
        @Nullable
        public SpriteContents get(SpriteResourceLoader p_295023_) {
            try {
                NativeImage nativeBaseImage = this.baseImage.get();
                NativeImage nativeOverlayImage = this.overlayImage.get();
                NativeImage nativeImage = new NativeImage(nativeBaseImage.getWidth(), nativeBaseImage.getHeight(), false);

                for (int i = 0; i < nativeImage.getHeight(); i++) {
                    for (int j = 0; j < nativeImage.getWidth(); j++) {
                        int color = nativeOverlayImage.getLuminanceOrAlpha(i, j) == 0 ? nativeBaseImage.getPixel(i, j) : 0x000000;
                        nativeImage.setPixel(i, j, color);
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage);
            } catch (IOException | IllegalArgumentException ioexception) {
                AetherII.LOGGER.error("unable to create subtractive sprite at {}", this.outputLocation(), ioexception);
            } finally {
                this.baseImage.release();
                this.overlayImage.release();
            }
            return null;
        }

        @Override
        public void discard() {
            this.baseImage.release();
            this.overlayImage.release();
        }
    }
}
