package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AtmosphericFogEnvironment.class)
public class AtmosphericFogEnvironmentMixin {
    @Unique
    private float sunriseSunsetMultiplier;

    @WrapOperation(method = "getBaseColor(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/Camera;IF)I", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;dot(FFF)F"))
    private float dot(Vector3fc instance, float x, float y, float z, Operation<Float> original, @Local(argsOnly = true, name = "level") ClientLevel level, @Local(argsOnly = true, name = "camera") Camera camera, @Local(argsOnly = true, name = "partialTicks") float partialTicks) {
        float dot = original.call(instance, x, y, z);
        BlockPos blockPos = camera.blockPosition();
        Holder<Biome> biome = level.getBiome(blockPos);
        float skyLightLevelMultiplier = 1.0F;
        if (biome.is(AetherIITags.Biomes.SUNRISE_SUNSET_MODIFICATION)) {
            skyLightLevelMultiplier = Mth.clamp((level.getLightEngine().getLayerListener(LightLayer.SKY).getLightValue(blockPos) - 8.0F) / 7.0F, 0.0F, 1.0F);
        }
        this.sunriseSunsetMultiplier += (skyLightLevelMultiplier - this.sunriseSunsetMultiplier) * Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks() * 0.2F;
        dot *= this.sunriseSunsetMultiplier;
        return dot;
    }
}
