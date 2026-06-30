package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.data.resources.registries.AetherIISkyrootLizardVariants;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.SkyrootLizard;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class SpawnSkyrootLizard extends LootItemConditionalFunction {
    private final Holder<Block> leafBlock;

    protected SpawnSkyrootLizard(LootItemCondition[] conditions, Holder<Block> leafBlock) {
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
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null) {
            if (serverLevel.getRandom().nextInt(10) == 0) {
                SkyrootLizard lizard = AetherIIEntityTypes.SKYROOT_LIZARD.get().create(serverLevel);
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
    public LootItemFunctionType getType() {
        return AetherIILootFunctions.SPAWN_SKYROOT_LIZARD.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SpawnSkyrootLizard> {
        @Override
        public void serialize(JsonObject jsonObject, SpawnSkyrootLizard function, JsonSerializationContext context) {
            super.serialize(jsonObject, function, context);
            jsonObject.addProperty("leaf_block", BuiltInRegistries.BLOCK.getKey(function.leafBlock.value()).toString());
        }

        @Override
        public SpawnSkyrootLizard deserialize(JsonObject jsonObject, JsonDeserializationContext context, LootItemCondition[] conditions) {
            Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(GsonHelper.getAsString(jsonObject, "leaf_block")));
            return new SpawnSkyrootLizard(conditions, block.builtInRegistryHolder());
        }
    }
}
