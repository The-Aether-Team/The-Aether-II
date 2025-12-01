package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.dungeon.SentryCrateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SentryCrateBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public SentryCrateBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_CRATE.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                SentryCrateBlockEntity.this.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
                SentryCrateBlockEntity.this.updateBlockState(state, true);
            }

            @Override
            protected void onClose(Level level, BlockPos pos, BlockState state) {
                SentryCrateBlockEntity.this.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
                SentryCrateBlockEntity.this.updateBlockState(state, false);
            }

            @Override
            protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
            }

            @Override
            protected boolean isOwnContainer(Player player) {
                if (!(player.containerMenu instanceof ChestMenu menu)) {
                    return false;
                } else {
                    Container container = menu.getContainer();
                    return container == SentryCrateBlockEntity.this || container instanceof CompoundContainer compound && compound.contains(SentryCrateBlockEntity.this);
                }
            }
        };
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.threeRows(id, player, this);
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
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
            this.invalidateCapabilities();
        }
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.items);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
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
