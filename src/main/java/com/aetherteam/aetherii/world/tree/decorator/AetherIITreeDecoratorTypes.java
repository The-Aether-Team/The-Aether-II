package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIITreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, AetherII.MODID);

    public static final RegistryObject<TreeDecoratorType<GroundFeatureDecorator>> GROUND_FEATURE = TREE_DECORATORS.register("ground_feature", () -> new TreeDecoratorType<>(GroundFeatureDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<SnowDecorator>> SNOW = TREE_DECORATORS.register("snow", () -> new TreeDecoratorType<>(SnowDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<WisprootTreeDecorator>> WISPROOT = TREE_DECORATORS.register("wisproot", () -> new TreeDecoratorType<>(WisprootTreeDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<SimpleTrunkTreeDecorator>> SIMPLE_TRUNK = TREE_DECORATORS.register("simple_trunk", () -> new TreeDecoratorType<>(SimpleTrunkTreeDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<IrradiationTreeDecorator>> IRRADIATION = TREE_DECORATORS.register("irradiation", () -> new TreeDecoratorType<>(IrradiationTreeDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<AlterGroundTagDecorator>> ALTER_GROUND_TAG = TREE_DECORATORS.register("alter_ground_tag", () -> new TreeDecoratorType<>(AlterGroundTagDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<MossDecorator>> MOSS = TREE_DECORATORS.register("moss", () -> new TreeDecoratorType<>(MossDecorator.CODEC.codec()));
    public static final RegistryObject<TreeDecoratorType<ShroudedCanopyDecorator>> SHROUDED_CANOPY = TREE_DECORATORS.register("shrouded_canopy", () -> new TreeDecoratorType<>(ShroudedCanopyDecorator.CODEC.codec()));
}
