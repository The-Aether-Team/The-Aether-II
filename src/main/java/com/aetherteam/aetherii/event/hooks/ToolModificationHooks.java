package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ToolModificationHooks {
    public static void stripMossyWisproot(LevelAccessor accessor, BlockState state, ItemStack stack, ItemAbility action, UseOnContext context) {
        if (action == ItemAbilities.AXE_STRIP) {
            if (state.is(AetherIIBlocks.MOSSY_WISPROOT_LOG)) {
                stripLog(accessor, stack, context, AetherIILoot.STRIP_MOSSY_WISPROOT);
            }
        }
    }

    public static void stripAmberoot(LevelAccessor accessor, BlockState state, ItemStack stack, ItemAbility action, UseOnContext context) {
        if (action == ItemAbilities.AXE_STRIP) {
            if (state.is(AetherIITags.Blocks.AMBEROOT_LOGS) && stack.is(AetherIITags.Items.GOLDEN_AMBER_HARVESTERS)) {
                stripLog(accessor, stack, context, AetherIILoot.STRIP_AMBEROOT);
            }
        }
    }

    private static void stripLog(LevelAccessor accessor, ItemStack stack, UseOnContext context, ResourceKey<LootTable> loot) {
        if (accessor instanceof Level level) {
            if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                Vec3 vector = context.getClickLocation();
                LootParams parameters = new LootParams.Builder(serverLevel).withParameter(LootContextParams.TOOL, stack).create(AetherIILootContexts.STRIPPING);
                LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(loot);
                List<ItemStack> list = lootTable.getRandomItems(parameters);
                for (ItemStack itemStack : list) {
                    ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }
}