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
import java.util.Map;
import java.util.Optional;

public record Additive(List<ResourceLocation> textures, Map<String, ResourceLocation> overlays) implements SpriteSource {
    public static final Codec<Additive> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter(Additive::textures),
            Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC).fieldOf("overlays").forGetter(Additive::overlays)
    ).apply(instance, Additive::new));

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        for (ResourceLocation location : this.textures()) {
            ResourceLocation originalTextureLocation = TEXTURE_ID_CONVERTER.idToFile(location);
            Optional<Resource> originalTexture = resourceManager.getResource(originalTextureLocation);
            if (originalTexture.isPresent()) {
                LazyLoadedImage originalImage = new LazyLoadedImage(originalTextureLocation, originalTexture.get(), this.overlays().size());
                for (Map.Entry<String, ResourceLocation> overlayEntry : this.overlays().entrySet()) {
                    ResourceLocation overlayTextureLocation = TEXTURE_ID_CONVERTER.idToFile(overlayEntry.getValue());
                    Optional<Resource> overlayTexture = resourceManager.getResource(overlayTextureLocation);
                    if (overlayTexture.isPresent()) {
                        LazyLoadedImage overlayImage = new LazyLoadedImage(overlayTextureLocation, overlayTexture.get(), this.textures().size());
                        ResourceLocation outputLocation = location.withSuffix("_" + overlayEntry.getKey());
                        output.add(outputLocation, new AdditiveSpriteSupplier(originalImage, overlayImage, outputLocation));
                    }
                }
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return AetherIISpriteSourceTypes.ADDITIVE;
    }

    public record AdditiveSpriteSupplier(LazyLoadedImage baseImage, LazyLoadedImage overlayImage, ResourceLocation outputLocation) implements SpriteSource.SpriteSupplier {
        @Nullable
        @Override
        public SpriteContents get() {
            try {
                NativeImage nativeBaseImage = this.baseImage().get();
                NativeImage nativeOverlayImage = this.overlayImage().get();
                NativeImage nativeImage = new NativeImage(nativeBaseImage.getWidth(), nativeBaseImage.getHeight(), false);

                for (int y = 0; y < nativeImage.getHeight(); y++) {
                    for (int x = 0; x < nativeImage.getWidth(); x++) {
                        int color = nativeOverlayImage.getLuminanceOrAlpha(x, y) != 0 ? nativeOverlayImage.getPixelRGBA(x, y) : nativeBaseImage.getPixelRGBA(x, y);
                        nativeImage.setPixelRGBA(x, y, color);
                    }
                }
                return new SpriteContents(this.outputLocation(), new FrameSize(nativeImage.getWidth(), nativeImage.getHeight()), nativeImage, AnimationMetadataSection.EMPTY);
            } catch (IOException | IllegalArgumentException exception) {
                AetherII.LOGGER.error("Unable to create additive sprite at {}", this.outputLocation(), exception);
            } finally {
                this.baseImage().release();
                this.overlayImage().release();
            }
            return null;
        }

        @Override
        public void discard() {
            this.baseImage().release();
            this.overlayImage().release();
        }
    }
}
