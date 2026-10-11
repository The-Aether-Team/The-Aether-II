package com.aetherteam.aetherii.mixin.mixins.client.sodium;

import com.aetherteam.aetherii.AetherIITags;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.world.biome.LevelBiomeSlice;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.world.biome.LevelBiomeSlice")
public abstract class LevelBiomeSliceMixin {

    @Shadow(remap = false)
    protected abstract void copySectionBiomeData(ChunkRenderContext context, int sectionX, int sectionY, int sectionZ, Holder<Biome> defaultBiome);

    @Redirect(method = "copyBiomeData", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/world/biome/LevelBiomeSlice;copySectionBiomeData(Lnet/caffeinemc/mods/sodium/client/world/cloned/ChunkRenderContext;IIILnet/minecraft/core/Holder;)V"), remap = false)
    private void aetherii$protectBoundarySections(LevelBiomeSlice instance, ChunkRenderContext context, int sectionX, int sectionY, int sectionZ, Holder<Biome> defaultBiome, @Local(argsOnly = true) Level level) {
        if (defaultBiome.is(AetherIITags.Biomes.THE_AETHER)) {
            int absoluteY = (context.getOrigin().getY() - 1) + sectionY;
            if (absoluteY < level.getMinSectionY()) {
                defaultBiome = level.getBiome(context.getOrigin().center());
            }
        }

        this.copySectionBiomeData(context, sectionX, sectionY, sectionZ, defaultBiome);
    }
}
