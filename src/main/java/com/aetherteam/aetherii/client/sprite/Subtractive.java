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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Subtractive implements SpriteSource {
    public static final MapCodec<Subtractive> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter((subtractive) -> subtractive.textures),
            Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC).fieldOf("overlays").forGetter((subtractive) -> subtractive.overlays)
    ).apply(instance, Subtractive::new));
    private final List<ResourceLocation> textures;
    private final Map<String, ResourceLocation> overlays;

    public Subtractive(List<ResourceLocation> textures, Map<String, ResourceLocation> overlays) {
        this.textures = textures;
        this.overlays = overlays;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for (ResourceLocation location : this.textures) {
            ResourceLocation originalTextureLocation = TEXTURE_ID_CONVERTER.idToFile(location);
            Optional<Resource> originalTexture = resourceManager.getResource(originalTextureLocation);
            if (originalTexture.isPresent()) {
                LazyLoadedImage originalImage = new LazyLoadedImage(originalTextureLocation, originalTexture.get(), this.overlays.size());
                for (Map.Entry<String, ResourceLocation> overlayEntry : this.overlays.entrySet()) {
                    ResourceLocation overlayTextureLocation = TEXTURE_ID_CONVERTER.idToFile(overlayEntry.getValue());
                    Optional<Resource> overlayTexture = resourceManager.getResource(overlayTextureLocation);
                    if (overlayTexture.isPresent()) {
                        LazyLoadedImage overlayImage = new LazyLoadedImage(overlayTextureLocation, overlayTexture.get(), this.textures.size());
                        ResourceLocation outputLocation = location.withSuffix("_" + overlayEntry.getKey());
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

    public record SubtractiveSpriteSupplier(LazyLoadedImage baseImage, LazyLoadedImage overlayImage, ResourceLocation outputLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        public SpriteContents apply(SpriteResourceLoader p_295023_) {
            try {
                NativeImage nativeBaseImage = this.baseImage.get();
                NativeImage nativeOverlayImage = this.overlayImage.get();
                NativeImage nativeImage = new NativeImage(nativeBaseImage.getWidth(), nativeBaseImage.getHeight(), false);

                for (int i = 0; i < nativeImage.getHeight(); i++) {
                    for (int j = 0; j < nativeImage.getWidth(); j++) {
                        if (nativeOverlayImage.getLuminanceOrAlpha(i, j) == 0) {
                            nativeImage.setPixel(i, j, nativeBaseImage.getPixel(i, j));
                        }
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage, ResourceMetadata.EMPTY);
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
