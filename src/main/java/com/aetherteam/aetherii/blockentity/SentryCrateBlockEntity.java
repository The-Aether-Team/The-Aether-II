package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.dungeon.SentryCrateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SentryCrateBlockEntity extends RandomizableContainerBlockEntity {
    public NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            SentryCrateBlockEntity.this.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
            SentryCrateBlockEntity.this.updateBlockState(state, true);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            SentryCrateBlockEntity.this.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
            level.blockEvent(pos, state.getBlock(), 1, openCount);
        }

        @Override
        public boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof ChestMenu menu)) {
                return false;
            } else {
                Container container = menu.getContainer();
                return container == SentryCrateBlockEntity.this || container instanceof CompoundContainer compound && compound.contains(SentryCrateBlockEntity.this);
            }
        }
    };
    public final ChestLidController chestLidController = new ChestLidController();

    public SentryCrateBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_CRATE.get(), pos, blockState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.threeRows(id, player, this);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SentryCrateBlockEntity blockEntity) {
        blockEntity.chestLidController.tickLid();
        if (blockEntity.chestLidController.getOpenness(1.0F) == 0) {
            blockEntity.updateBlockState(state, false);
        }
    }

    @Override
    public void startOpen(Player user) {
        if (!this.remove && !user.isSpectator()) {
            this.openersCounter.incrementOpeners(user, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player user) {
        if (!this.remove && !user.isSpectator()) {
            this.openersCounter.decrementOpeners(user, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void setBlockState(BlockState state) {
        BlockState oldState = this.getBlockState();
        super.setBlockState(state);
        if (oldState.getValue(SentryCrateBlock.FACING) != state.getValue(SentryCrateBlock.FACING) || oldState.getValue(SentryCrateBlock.TYPE) != state.getValue(SentryCrateBlock.TYPE)) {
            this.invalidateCaps();
        }
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("aether_ii.container.sentry_crate");
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    private void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(SentryCrateBlock.OPEN, open), 3);
    }

    private void playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound) {
        ChestType type = state.getValue(ChestBlock.TYPE);
        if (type != ChestType.LEFT) {
            double d0 = pos.getX() + 0.5F;
            double d1 = pos.getY() + 0.5F;
            double d2 = pos.getZ() + 0.5F;
            if (type == ChestType.RIGHT) {
                Direction direction = ChestBlock.getConnectedDirection(state);
                d0 += direction.getStepX() * 0.5F;
                d2 += direction.getStepZ() * 0.5F;
            }
            level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }
}
