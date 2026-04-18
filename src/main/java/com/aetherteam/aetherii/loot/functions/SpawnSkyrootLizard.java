package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.data.resources.registries.AetherIISkyrootLizardVariants;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpawnSkyrootLizard extends LootItemConditionalFunction {
    public static final MapCodec<SpawnSkyrootLizard> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter((function) -> function.predicates),
                    BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("leaf_block").forGetter((function) -> function.leafBlock)
    ).apply(instance, SpawnSkyrootLizard::new));

    private final Holder<Block> leafBlock;

    protected SpawnSkyrootLizard(List<LootItemCondition> conditions, Holder<Block> leafBlock) {
        super(conditions);
        this.leafBlock = leafBlock;
    }

    /**
     * Spawns a Skyroot Lizard.
     *
     * @param stack   The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel serverLevel = context.getLevel();
        Vec3 originVec = context.getOptionalParameter(LootContextParams.ORIGIN);
        if (originVec != null) {
            if (serverLevel.getRandom().nextInt(10) == 0) {
                SkyrootLizard lizard = AetherIIEntityTypes.SKYROOT_LIZARD.get().create(serverLevel.getLevel(), EntitySpawnReason.TRIGGERED);
                if (lizard != null) {
                    lizard.setPos(originVec.x + 0.5, originVec.y + 0.5, originVec.z + 0.5);
                    Holder<SkyrootLizardVariant> variant = AetherIISkyrootLizardVariants.getVariantForLeaves(serverLevel.registryAccess(), this.leafBlock);
                    lizard.setVariant(variant);
                    serverLevel.getLevel().addFreshEntity(lizard);
                }
            }
        }
        return stack;
    }

    public static Builder<?> builder(Holder<Block> leafBlock) {
        return LootItemConditionalFunction.simpleBuilder((conditions) -> new SpawnSkyrootLizard(conditions, leafBlock));
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

}