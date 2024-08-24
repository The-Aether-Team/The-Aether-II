package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.AetherIIGameEvents;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.FreezingBlock;
import com.aetherteam.aetherii.block.portal.AetherPortalShape;
import com.aetherteam.aetherii.blockentity.IcestoneBlockEntity;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.level.AlterGroundEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class BlockHooks {
    public static void sendIcestoneFreezableUpdateEvent(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof Level level && !level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            FreezingBlock.cacheRecipes(level);
            if (FreezingBlock.matchesCache(oldBlockState.getBlock(), oldBlockState, accessor.getBiome(pos)) != null) {
                level.gameEvent(null, AetherIIGameEvents.ICESTONE_FREEZABLE_UPDATE, pos);
            }
        }
    }

    public static boolean activatePortalFromBlockUpdate(LevelAccessor levelAccessor, BlockPos pos, boolean cancellationStatus) {
        if (levelAccessor instanceof Level level) {
            BlockState blockState = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            if (fluidState.is(Fluids.WATER) && fluidState.createLegacyBlock().getBlock() == blockState.getBlock()) {
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension()) && !AetherIIConfig.SERVER.disable_aether_portal.get()) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, pos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        return true;
                    }
                }
            }
        }
        return cancellationStatus;
    }

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

    public static AlterGroundEvent.StateProvider modifyPodzolAlterGroundStateProvider(TreeDecorator.Context context, AlterGroundEvent.StateProvider provider) {
        return (rand, pos) -> {
            AtomicReference<BlockState> oldState = new AtomicReference<>(); // Ground to replace.
            BlockState attemptedState = provider.getState(rand, pos); // Ground to maybe replace with.
            if (context.level().isStateAtPosition(pos, state -> {
                if (state.is(AetherIITags.Blocks.AETHER_DIRT)) {
                    oldState.set(state);
                    return true;
                } else {
                    return false;
                }
            })) {
                return attemptedState.is(Blocks.PODZOL) ? oldState.get() : attemptedState; // Ground to actually replace with.
            } else {
                return attemptedState;
            }
        };
    }

    public static boolean preventBlockFreezing(LevelAccessor accessor, BlockPos sourcePos, BlockPos pos, boolean cancellationStatus) {
        if (accessor.getBlockEntity(sourcePos) instanceof IcestoneBlockEntity blockEntity) {
            for (Map.Entry<BlockPos, Integer> entry : blockEntity.getLastBrokenPositions().entrySet()) {
                if (entry.getKey().equals(pos) && entry.getValue() > 0) {
                    return true;
                }
            }
        }
        return cancellationStatus;
    }
}
