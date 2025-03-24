package com.aetherteam.aetherii.client.sprite;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
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

public class Overlaid implements SpriteSource {
    public static final MapCodec<Overlaid> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter((overlaid) -> overlaid.textures),
            Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC).fieldOf("overlays").forGetter((overlaid) -> overlaid.overlays)
    ).apply(instance, Overlaid::new));
    private final List<ResourceLocation> textures;
    private final Map<String, ResourceLocation> overlays;

    public Overlaid(List<ResourceLocation> textures, Map<String, ResourceLocation> overlays) {
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
                        output.add(outputLocation, new OverlaidSpriteSupplier(originalImage, overlayImage, outputLocation));
                    }
                }
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return AetherIISpriteSourceTypes.OVERLAID;
    }

    public record OverlaidSpriteSupplier(LazyLoadedImage baseImage, LazyLoadedImage overlayImage, ResourceLocation outputLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        public SpriteContents apply(SpriteResourceLoader p_295023_) {
            try {
                NativeImage nativeBaseImage = this.baseImage.get();
                NativeImage nativeOverlayImage = this.overlayImage.get();
                NativeImage nativeImage = new NativeImage(nativeBaseImage.getWidth(), nativeBaseImage.getHeight(), false);

                for (int i = 0; i < nativeImage.getHeight(); i++) {
                    for (int j = 0; j < nativeImage.getWidth(); j++) {
                        int color = nativeOverlayImage.getLuminanceOrAlpha(i, j) != 0 ? nativeOverlayImage.getPixel(i, j) : nativeBaseImage.getPixel(i, j);
                        nativeImage.setPixel(i, j, color);
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage, ResourceMetadata.EMPTY);
            } catch (IOException | IllegalArgumentException ioexception) {
                AetherII.LOGGER.error("unable to create overlaid sprite at {}", this.outputLocation(), ioexception);
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
