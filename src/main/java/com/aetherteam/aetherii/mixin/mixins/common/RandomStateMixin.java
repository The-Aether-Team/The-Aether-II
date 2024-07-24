package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RandomState.class)
public class RandomStateMixin {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseRouter;mapAll(Lnet/minecraft/world/level/levelgen/DensityFunction$Visitor;)Lnet/minecraft/world/level/levelgen/NoiseRouter;"))
    private NoiseRouter init(NoiseRouter instance, DensityFunction.Visitor visitor, Operation<NoiseRouter> original, NoiseGeneratorSettings settings, HolderGetter<NormalNoise.NoiseParameters> noiseParametersGetter, final long levelSeed) {
        return original.call(instance, visitor).mapAll(PerlinNoiseFunction.createOrGetVisitor(levelSeed));
    }
}
