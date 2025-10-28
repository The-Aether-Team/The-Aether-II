package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.ValkyrieSproutBlock;
import com.aetherteam.aetherii.world.structure.processor.CappedGravityProcessor;
import com.aetherteam.aetherii.world.structure.processor.DensityFunctionProcessor;
import com.aetherteam.aetherii.world.structure.processor.ReinforceBlocksProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class AetherIIProcessorLists {
    public static final ResourceKey<StructureProcessorList> CAMP_HIGHFIELDS = createKey("camp_highfields");
    public static final ResourceKey<StructureProcessorList> INFECTED_GUARDIAN_TREE = createKey("infected_guardian_tree");
    public static final ResourceKey<StructureProcessorList> INFECTED_GUARDIAN_TREE_TRUNK = createKey("infected_guardian_tree_trunk");
    public static final ResourceKey<StructureProcessorList> INFECTED_GUARDIAN_TREE_ROOTS = createKey("infected_guardian_tree_roots");

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        HolderGetter<DensityFunction> function = context.lookup(Registries.DENSITY_FUNCTION);

        register(context, CAMP_HIGHFIELDS, ImmutableList.of(
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.COARSE_AETHER_DIRT.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.HOLYSTONE.get(), 0.3F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.HOLYSTONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.BLUEBERRY_BUSH.get(), 0.2F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.BLUEBERRY_BUSH.get(), 0.4F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.BLUEBERRY_BUSH_STEM.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.VALKYRIE_SPROUT.get(), 0.2F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.VALKYRIE_SPROUT.get(), 0.25F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.VALKYRIE_SPROUT.get().defaultBlockState().setValue(ValkyrieSproutBlock.AGE, 0)),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.VALKYRIE_SPROUT.get(), 0.15F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.VALKYRIE_SPROUT.get().defaultBlockState().setValue(ValkyrieSproutBlock.AGE, 1))
                ))
        ));

        register(context, INFECTED_GUARDIAN_TREE, ImmutableList.of(
                new DensityFunctionProcessor(AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new DensityFunctionProcessor(AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.LOCKED_DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new DensityFunctionProcessor(AetherIIBlocks.GUARDIAN_ROOTS.get().defaultBlockState(), AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_STRIPPED_GUARDIAN_WOOD)),
                new DensityFunctionProcessor(AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.GUARDIAN_ROOTS.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_GUARDIAN_ROOTS)),
                new DensityFunctionProcessor(AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.LOCKED_GUARDIAN_ROOTS.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_GUARDIAN_ROOTS)),
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.GUARDIAN_ROOTS.get(), 0.025F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.LUCENT_GUARDIAN_ROOTS.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.GUARDIAN_ROOTS.get(), 0.01F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.GUARDIAN_LAMP.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.LOCKED_GUARDIAN_ROOTS.get(), 0.025F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.LOCKED_LUCENT_GUARDIAN_ROOTS.get().defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.LOCKED_GUARDIAN_ROOTS.get(), 0.01F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.LOCKED_GUARDIAN_LAMP.get().defaultBlockState()),

                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.PURPLE_CLOUDWOOL.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.PURPLE_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get(), 0.6F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
                )),
                new ReinforceBlocksProcessor()
        ));

        register(context, INFECTED_GUARDIAN_TREE_TRUNK, ImmutableList.of(
                new DensityFunctionProcessor(AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new DensityFunctionProcessor(AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.LOCKED_DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.PURPLE_CLOUDWOOL.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.PURPLE_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()),
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get(), 0.6F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
                )),
                new ReinforceBlocksProcessor()
        ));

        register(context, INFECTED_GUARDIAN_TREE_ROOTS, ImmutableList.of(
                new CappedGravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1, 8,8),
                new DensityFunctionProcessor(AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new DensityFunctionProcessor(AetherIIBlocks.LOCKED_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.LOCKED_DENSE_GUARDIAN_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEON_DENSE_GUARDIAN_WOOD)),
                new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState()),
                        new ProcessorRule(new BlockMatchTest(AetherIIBlocks.RED_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
                )),
                new ReinforceBlocksProcessor()
        ));
    }

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    private static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> resourceKey, List<StructureProcessor> list) {
        context.register(resourceKey, new StructureProcessorList(list));
    }
}