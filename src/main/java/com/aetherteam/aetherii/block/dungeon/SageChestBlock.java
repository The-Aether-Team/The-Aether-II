package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.SageChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class SageChestBlock extends ChestBlock {
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>() {
        @Override
        public Optional<MenuProvider> acceptDouble(final ChestBlockEntity first, final ChestBlockEntity second) {
            final Container container = new CompoundContainer(first, second);
            return Optional.of(new MenuProvider() {
                @Override
                public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                    if (first.canOpen(player) && second.canOpen(player)) {
                        first.unpackLootTable(inventory.player);
                        second.unpackLootTable(inventory.player);
                        return ChestMenu.sixRows(containerId, inventory, container);
                    } else {
                        Direction connectedDirection = ChestBlock.getConnectedDirection(first.getBlockState());
                        Vec3 firstCenter = first.getBlockPos().getCenter();
                        Vec3 centerBetweenChests = firstCenter.add(connectedDirection.getStepX() / 2.0F, 0.0F, connectedDirection.getStepZ() / 2.0F);
                        BaseContainerBlockEntity.sendChestLockedNotifications(centerBetweenChests, player, this.getDisplayName());
                        return null;
                    }
                }

                @Override
                public Component getDisplayName() {
                    if (first.hasCustomName()) {
                        return first.getDisplayName();
                    } else {
                        return second.hasCustomName() ? second.getDisplayName() : Component.translatable("aether_ii.container.sage_chest");
                    }
                }
            });
        }

        @Override
        public Optional<MenuProvider> acceptSingle(ChestBlockEntity single) {
            return Optional.of(single);
        }

        @Override
        public Optional<MenuProvider> acceptNone() {
            return Optional.empty();
        }
    };

    public SageChestBlock(BlockBehaviour.Properties properties) {
        super(AetherIIBlockEntityTypes.SAGE_CHEST::get, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SageChestBlockEntity(pos, state);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return this.combine(state, level, pos, false).apply(MENU_PROVIDER_COMBINER).orElse(null);
    }
}
