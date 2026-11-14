package com.aetherteam.aetherii.world.structure.processor.ruletest;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.processor.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRuleTests {
    public static final DeferredRegister<RuleTestType<?>> RULE_TESTS = DeferredRegister.create(Registries.RULE_TEST, AetherII.MODID);

    public static final DeferredHolder<RuleTestType<?>, RuleTestType<BlockIgnoreTest>> BLOCK_IGNORE_TEST = RULE_TESTS.register("block_ignore_test", () -> () -> BlockIgnoreTest.CODEC);
}