package com.aetherteam.aetherii.item.components;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record Tool(List<Rule> rules, float defaultMiningSpeed, int damagePerBlock, boolean canDestroyBlocksInCreative) {
    public static final Codec<Tool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Rule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(Tool::rules),
            Codec.FLOAT.optionalFieldOf("default_mining_speed", 1.0F).forGetter(Tool::defaultMiningSpeed),
            Codec.INT.optionalFieldOf("damage_per_block", 1).forGetter(Tool::damagePerBlock),
            Codec.BOOL.optionalFieldOf("can_destroy_blocks_in_creative", true).forGetter(Tool::canDestroyBlocksInCreative)
    ).apply(instance, Tool::new));

    public record Rule(List<HolderSet<Block>> blocks, float speed, boolean correctForDrops) {
        private static final Codec<HolderSet<Block>> BLOCK_SET_CODEC = RegistryCodecs.homogeneousList(Registries.BLOCK);
        private static final Codec<List<HolderSet<Block>>> BLOCKS_CODEC = Codec.either(BLOCK_SET_CODEC, BLOCK_SET_CODEC.listOf())
                .xmap(either -> either.map(List::of, blocks -> blocks), blocks -> blocks.size() == 1 ? Either.left(blocks.get(0)) : Either.right(blocks));
        public static final Codec<Rule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BLOCKS_CODEC.fieldOf("blocks").forGetter(Rule::blocks),
                Codec.FLOAT.optionalFieldOf("speed", 1.0F).forGetter(Rule::speed),
                Codec.BOOL.optionalFieldOf("correct_for_drops", false).forGetter(Rule::correctForDrops)
        ).apply(instance, Rule::new));

        public static Rule deniesDrops(List<HolderSet<Block>> blocks) {
            return new Rule(blocks, 1.0F, false);
        }

        public static Rule deniesDrops(HolderSet<Block> blocks) {
            return deniesDrops(List.of(blocks));
        }

        public static Rule minesAndDrops(HolderSet<Block> blocks, float speed) {
            return new Rule(List.of(blocks), speed, true);
        }
    }
}
