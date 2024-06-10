package com.aetherteam.aetherii.world.biome;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class AetherIISurfaceRules {

    public static final DeferredRegister<Codec<? extends SurfaceRules.ConditionSource>> CONDITIONS = DeferredRegister.create(BuiltInRegistries.MATERIAL_CONDITION, AetherII.MODID);

    public interface ConditionSource extends Function<SurfaceRules.Context, SurfaceRules.Condition> {
        Codec<SurfaceRules.ConditionSource> CODEC = BuiltInRegistries.MATERIAL_CONDITION
                .byNameCodec()
                .dispatch(conditionSource -> conditionSource.codec().codec(), Function.identity());

        static Codec<? extends SurfaceRules.ConditionSource> bootstrap(Registry<Codec<? extends SurfaceRules.ConditionSource>> registry) {
            return AetherIISurfaceRules.register(registry, "density_function", AetherIISurfaceRules.DensityFunctionConditionSource.CODEC);
        }

        KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec();
    }

    public static SurfaceRules.ConditionSource densityCondition(DensityFunction function, double minThreshold, double maxThreshold) {
        return new AetherIISurfaceRules.DensityFunctionConditionSource(function, minThreshold, maxThreshold);
    }

    record DensityFunctionConditionSource(DensityFunction function, double minThreshold, double maxThreshold) implements SurfaceRules.ConditionSource {
        static final KeyDispatchDataCodec<AetherIISurfaceRules.DensityFunctionConditionSource> CODEC = KeyDispatchDataCodec.of(
                RecordCodecBuilder.mapCodec(
                        instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density_function").forGetter(AetherIISurfaceRules.DensityFunctionConditionSource::function),
                                        Codec.DOUBLE.fieldOf("min_threshold").forGetter(AetherIISurfaceRules.DensityFunctionConditionSource::minThreshold),
                                        Codec.DOUBLE.fieldOf("max_threshold").forGetter(AetherIISurfaceRules.DensityFunctionConditionSource::maxThreshold)
                                )
                                .apply(instance, AetherIISurfaceRules.DensityFunctionConditionSource::new)
                )
        );


        @Override
        public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
            return CODEC;
        }

        public SurfaceRules.Condition apply(SurfaceRules.Context context) {

            class DensityFunctionCondition extends SurfaceRules.LazyXZCondition {
                DensityFunctionCondition(SurfaceRules.Context context) {
                    super(context);
                }

                @Override
                protected boolean compute() {
                    double density = function.compute(new DensityFunction.SinglePointContext(context.blockX, context.blockY, context.blockZ));
                    return density >= AetherIISurfaceRules.DensityFunctionConditionSource.this.minThreshold && density <= AetherIISurfaceRules.DensityFunctionConditionSource.this.maxThreshold;
                }
            }

            return new DensityFunctionCondition(context);
        }
    }


    static <A> Codec<? extends A> register(Registry<Codec<? extends A>> registry, String name, KeyDispatchDataCodec<? extends A> value) {
        return Registry.register(registry, name, value.codec());
    }
}