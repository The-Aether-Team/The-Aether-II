package com.aetherteam.aetherii.data.resources;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class AetherIIFeatureRules {
    public static final RuleTest HOLYSTONE = new TagMatchTest(AetherIITags.Blocks.HOLYSTONE);
    public static final RuleTest UNDERSHALE = new BlockMatchTest(AetherIIBlocks.UNDERSHALE.get());
    public static final RuleTest UNDERGROUND = new TagMatchTest(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS);
}