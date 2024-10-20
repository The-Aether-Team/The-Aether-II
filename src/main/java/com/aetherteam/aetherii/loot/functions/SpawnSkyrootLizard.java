package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpawnSkyrootLizard extends LootItemConditionalFunction {
    public static final MapCodec<SpawnSkyrootLizard> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance)
            .apply(instance, SpawnSkyrootLizard::new)
    );

    protected SpawnSkyrootLizard(List<LootItemCondition> conditions) {
        super(conditions);
    }

    /**
     * Spawns an entity from a Whirlwind.
     *
     * @param stack   The {@link ItemStack} for the loot pool.
     * @param context The {@link LootContext}.
     * @return The {@link ItemStack} for the loot pool.
     */
    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        ServerLevel serverLevel = context.getLevel();
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null) {
            SkyrootLizard lizard = AetherIIEntityTypes.SKYROOT_LIZARD.get().create(serverLevel.getLevel());
            assert lizard != null;
            lizard.setPos(originVec.x + 0.5, originVec.y + 0.5, originVec.z + 0.5);
            serverLevel.getLevel().addFreshEntity(lizard);
        }
        return stack;
    }

    public static Builder<?> builder() {
        return LootItemConditionalFunction.simpleBuilder(SpawnSkyrootLizard::new);
    }

    @Override
    public LootItemFunctionType getType() {
        return AetherIILootFunctions.SPAWN_SKYROOT_LIZARD.get();
    }
}