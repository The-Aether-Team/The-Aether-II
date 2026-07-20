package com.aetherteam.aetherii.mixin.mixins.client.sodium;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadView;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer")
public class DefaultFluidRendererMixin {

    @Shadow(remap = false)
    private int[] quadColors;

    @Inject(method = "updateQuad", at = @At("TAIL"), remap = false)
    private void aetherii$updateQuadAlpha(@Coerce Object quad, @Coerce Object level, BlockPos pos,
            @Coerce Object lighter, Direction dir,
            @Coerce Object facing, float brightness, @Coerce Object colorProvider, FluidState fluidState,
            CallbackInfo ci) {

        ClientLevel clientLevel = Minecraft.getInstance().level;
        boolean aetherEffects = clientLevel != null && clientLevel.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER);
        if (aetherEffects) {
            int bottomY = clientLevel.getMinY();
            int currentY = pos.getY();
            int range = 8;
            float opacityStep = 1.0F / range;
            int max = bottomY + range;

            if (currentY < max) {
                float offsetY = currentY - bottomY;

                ModelQuadView quadView = (ModelQuadView) quad;

                for (int i = 0; i < 4; i++) {
                    float y = quadView.getY(i);
                    boolean isUpperVertex = y > 0.005F;
                    float trueAlpha = isUpperVertex ? opacityStep * (offsetY + 1) : opacityStep * offsetY;

                    trueAlpha = Math.max(0.0F, Math.min(1.0F, trueAlpha));

                    int abgr = this.quadColors[i];
                    int origAlpha = (abgr >> 24) & 0xFF;
                    int newAlpha = (int) (origAlpha * trueAlpha);

                    newAlpha = Math.max(0, Math.min(255, newAlpha));

                    this.quadColors[i] = (abgr & 0x00FFFFFF) | (newAlpha << 24);
                }
            }
        }
    }
}
