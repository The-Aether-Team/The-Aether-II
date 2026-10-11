package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.attribute.WeatherAttributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnvironmentAttributeSystem.class)
public class EnvironmentAttributeSystemMixin {
    @Inject(method = "addDefaultLayers(Lnet/minecraft/world/attribute/EnvironmentAttributeSystem$Builder;Lnet/minecraft/world/level/Level;)V", at = @At(value = "TAIL"))
    private static void addDefaultLayers(EnvironmentAttributeSystem.Builder builder, Level level, CallbackInfo ci) {
        if (level.canHaveWeather()) {
            AetherIIEnvironmentAttributes.Weather.addBuiltinLayers(builder, level, WeatherAttributes.WeatherAccess.from(level));
        }
        AetherIIEnvironmentAttributes.Elevation.addBuiltinLayers(builder, level);
    }
}
