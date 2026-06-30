package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.AetherIIGameEvents;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.FreezingBlock;
import com.aetherteam.aetherii.block.natural.HestveilBlock;
import com.aetherteam.aetherii.block.portal.AetherPortalShape;
import com.aetherteam.aetherii.blockentity.IcestoneBlockEntity;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import com.aetherteam.aetherii.loot.conditions.TierCompare;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.level.AlterGroundEvent;
import net.minecraftforge.event.ForgeEventFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class BlockHooks {
    private static final Method FORGE_ALTER_GROUND = findMethod(ForgeEventFactory.class, "alterGround", LevelSimulatedReader.class, RandomSource.class, BlockPos.class, BlockState.class);
    private static final Method ALTER_GROUND_GET_LEVEL = findMethod(AlterGroundEvent.class, "getLevel");
    private static final Method ALTER_GROUND_GET_POS = findMethod(AlterGroundEvent.class, "getPos");
    private static final Method ALTER_GROUND_GET_NEW_STATE = findMethod(AlterGroundEvent.class, "getNewAlteredState");
    private static final Method ALTER_GROUND_SET_NEW_STATE = findMethod(AlterGroundEvent.class, "setNewAlteredState", BlockState.class);

    public static void sendIcestoneFreezableUpdateEvent(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof ServerLevel level) {
            BlockState oldBlockState = level.getBlockState(pos);
            if (FreezingBlock.cachedBlocks.contains(oldBlockState.getBlock())) {
                level.gameEvent(null, AetherIIGameEvents.ICESTONE_FREEZABLE_UPDATE.get(), pos);
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

    public static void stripMossyWisproot(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
        if (action == ToolActions.AXE_STRIP) {
            if (state.is(AetherIIBlocks.MOSSY_WISPROOT_LOG.get()) || state.is(AetherIIBlocks.MOSSY_WISPROOT_WOOD.get()) || state.is(AetherIIBlocks.MOSSY_WISPROOT_TRUNK.get())) {
                stripLog(accessor, stack, context, AetherIILoot.STRIP_MOSSY_WISPROOT);
            } else if (state.is(AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get())) {
                stripLog(accessor, stack, context, AetherIILoot.STRIP_MOSSY_WISPROOT_BASE);
            }
        }
    }

    public static void stripAmberoot(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
        if (action == ToolActions.AXE_STRIP) {
            if (state.is(AetherIIBlocks.AMBEROOT_DEPOSIT.get()) && TierCompare.compareStack(stack, "#" + AetherIITags.Items.GOLDEN_AMBER_HARVESTERS.location())) {
                stripLog(accessor, stack, context, AetherIILoot.STRIP_AMBEROOT_DEPOSIT);
            }
        }
    }

    private static void stripLog(LevelAccessor accessor, ItemStack stack, UseOnContext context, ResourceKey<LootTable> loot) {
        if (accessor instanceof Level level) {
            if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                Vec3 vector = context.getClickLocation();
                LootParams parameters = new LootParams.Builder(serverLevel).withParameter(LootContextParams.TOOL, stack).create(AetherIILootContexts.STRIPPING);
                LootTable lootTable = level.getServer().getLootData().getElement(new LootDataId<>(LootDataType.TABLE, loot.location()));
                if (lootTable == null) {
                    AetherII.LOGGER.warn("Skipping stripping drops because loot table {} is missing", loot.location());
                    return;
                }
                List<ItemStack> list = lootTable.getRandomItems(parameters);
                for (ItemStack itemStack : list) {
                    ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }

    public static BlockState fireAlterGround(LevelSimulatedReader level, RandomSource random, BlockPos pos, BlockState alteredState) {
        if (FORGE_ALTER_GROUND != null) {
            try {
                return (BlockState) FORGE_ALTER_GROUND.invoke(null, level, random, pos, alteredState);
            } catch (ReflectiveOperationException | ClassCastException ignored) {
            }
        }
        return alteredState;
    }

    public static void modifyPodzolAlterGroundEvent(AlterGroundEvent event) {
        if (ALTER_GROUND_GET_LEVEL == null || ALTER_GROUND_GET_POS == null || ALTER_GROUND_GET_NEW_STATE == null || ALTER_GROUND_SET_NEW_STATE == null) {
            return;
        }
        try {
            LevelSimulatedReader level = (LevelSimulatedReader) ALTER_GROUND_GET_LEVEL.invoke(event);
            BlockPos pos = (BlockPos) ALTER_GROUND_GET_POS.invoke(event);
            BlockState attemptedState = (BlockState) ALTER_GROUND_GET_NEW_STATE.invoke(event);
            ALTER_GROUND_SET_NEW_STATE.invoke(event, modifyPodzolAlterGroundState(level, pos, attemptedState));
        } catch (ReflectiveOperationException | ClassCastException ignored) {
        }
    }

    public static BlockState modifyPodzolAlterGroundState(LevelSimulatedReader level, BlockPos pos, BlockState attemptedState) {
        AtomicReference<BlockState> oldState = new AtomicReference<>();
        if (level.isStateAtPosition(pos, state -> {
            if (state.is(AetherIITags.Blocks.AETHER_GROUND_BLOCKS)) {
                oldState.set(state);
                return true;
            } else {
                return false;
            }
        })) {
            return attemptedState.is(Blocks.PODZOL) ? oldState.get() : attemptedState;
        } else {
            return attemptedState;
        }
    }

    private static Method findMethod(Class<?> owner, String name, Class<?>... parameterTypes) {
        try {
            return owner.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException ignored) {
            return null;
        }
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

    public static boolean canBreathe(LivingEntity livingEntity) {
        return !(livingEntity.level().getBlockState(BlockPos.containing(livingEntity.getEyePosition())).getBlock() instanceof HestveilBlock);
    }
}
