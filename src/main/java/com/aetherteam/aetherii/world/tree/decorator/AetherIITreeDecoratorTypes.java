package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIITreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, AetherII.MODID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<RandomDecorator>> RANDOM = TREE_DECORATORS.register("random", () -> new TreeDecoratorType<>(RandomDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<GroundFeatureDecorator>> GROUND_FEATURE = TREE_DECORATORS.register("ground_feature", () -> new TreeDecoratorType<>(GroundFeatureDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<AlterGroundTagDecorator>> ALTER_GROUND_TAG = TREE_DECORATORS.register("alter_ground_tag", () -> new TreeDecoratorType<>(AlterGroundTagDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<SimpleTrunkTreeDecorator>> SIMPLE_TRUNK = TREE_DECORATORS.register("simple_trunk", () -> new TreeDecoratorType<>(SimpleTrunkTreeDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<CrystalTrunkTreeDecorator>> CRYSTALROOT_TRUNK = TREE_DECORATORS.register("crystalroot_trunk", () -> new TreeDecoratorType<>(CrystalTrunkTreeDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<MossFallenStumpsDecorator>> MOSS_FALLEN_STUMPS = TREE_DECORATORS.register("moss_fallen_stumps", () -> new TreeDecoratorType<>(MossFallenStumpsDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<MossFallenLogsDecorator>> MOSS_FALLEN_LOGS = TREE_DECORATORS.register("moss_fallen_logs", () -> new TreeDecoratorType<>(MossFallenLogsDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<MossCoverLogsDecorator>> MOSS_COVER_LOGS = TREE_DECORATORS.register("moss_cover_logs", () -> new TreeDecoratorType<>(MossCoverLogsDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<VerticalMossyWisprootLogsDecorator>> VERTICAL_MOSSY_WISPROOT_LOGS = TREE_DECORATORS.register("vertical_mossy_wisproot_logs", () -> new TreeDecoratorType<>(VerticalMossyWisprootLogsDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<HorizontalMossyWisprootLogsDecorator>> HORIZONTAL_MOSSY_WISPROOT_LOGS = TREE_DECORATORS.register("horizontal_mossy_wisproot_logs", () -> new TreeDecoratorType<>(HorizontalMossyWisprootLogsDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<SnowyLeavesDecorator>> SNOWY_LEAVES = TREE_DECORATORS.register("snowy_leaves", () -> new TreeDecoratorType<>(SnowyLeavesDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<MossyLeavesDecorator>> MOSSY_LEAVES = TREE_DECORATORS.register("mossy_leaves", () -> new TreeDecoratorType<>(MossyLeavesDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<IrradiatedLeavesDecorator>> IRRADIATED_LEAVES = TREE_DECORATORS.register("irradiated_leaves", () -> new TreeDecoratorType<>(IrradiatedLeavesDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<CrystalApplesDecorator>> CRYSTAL_APPLES = TREE_DECORATORS.register("crystal_apples", () -> new TreeDecoratorType<>(CrystalApplesDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<ShroudedCanopyDecorator>> SHROUDED_CANOPY = TREE_DECORATORS.register("shrouded_canopy", () -> new TreeDecoratorType<>(ShroudedCanopyDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<WisptopSandDecorator>> WISPTOP_SAND = TREE_DECORATORS.register("wisptop_sand", () -> new TreeDecoratorType<>(WisptopSandDecorator.CODEC));
}