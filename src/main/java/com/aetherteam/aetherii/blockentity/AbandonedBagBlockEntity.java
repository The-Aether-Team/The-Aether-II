package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.AbandonedBagBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AbandonedBagBlockEntity extends ChestBlockEntity {
    private static final Component NAME = Component.translatable("aether_ii.container.abandoned_bag");
    private final ContainerOpenersCounter bagOpenersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            AbandonedBagBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            AbandonedBagBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int oldCount, int newCount) {
            AbandonedBagBlockEntity.this.signalOpenCount(level, pos, state, oldCount, newCount);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof ChestMenu)) {
                return false;
            }
            Container container = ((ChestMenu) player.containerMenu).getContainer();
            return container == AbandonedBagBlockEntity.this || container instanceof CompoundContainer compoundContainer && compoundContainer.contains(AbandonedBagBlockEntity.this);
        }
    };

    public AbandonedBagBlockEntity() {
        this(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), BlockPos.ZERO, AetherIIBlocks.ABANDONED_BAG.get().defaultBlockState());
    }

    public AbandonedBagBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), pos, state);
    }

    protected AbandonedBagBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.bagOpenersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.bagOpenersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void recheckOpen() {
        if (!this.remove) {
            this.bagOpenersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected Component getDefaultName() {
        return NAME;
    }

    private static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        if (level != null && state.getBlock() instanceof AbandonedBagBlock) {
            level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
