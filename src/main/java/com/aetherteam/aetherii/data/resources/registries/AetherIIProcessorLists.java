package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.ValkyrieSproutBlock;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class AetherIIProcessorLists {
    public static final ResourceKey<StructureProcessorList> CAMP_HIGHFIELDS = createKey("camp_highfields");

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
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
    }

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    private static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> resourceKey, List<StructureProcessor> list) {
        context.register(resourceKey, new StructureProcessorList(list));
    }
}