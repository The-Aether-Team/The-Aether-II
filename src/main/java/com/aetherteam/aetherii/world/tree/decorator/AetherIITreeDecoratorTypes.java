package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIITreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, AetherII.MODID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<GroundFeatureDecorator>> GROUND_FEATURE = TREE_DECORATORS.register("ground_feature", () -> new TreeDecoratorType<>(GroundFeatureDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<SnowDecorator>> SNOW = TREE_DECORATORS.register("snow", () -> new TreeDecoratorType<>(SnowDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<WisprootTreeDecorator>> WISPROOT_DECORATOR = TREE_DECORATORS.register("wisproot_decorator", () -> new TreeDecoratorType<>(WisprootTreeDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<IrradiationTreeDecorator>> IRRADIATION = TREE_DECORATORS.register("irradiation", () -> new TreeDecoratorType<>(IrradiationTreeDecorator.CODEC));
}