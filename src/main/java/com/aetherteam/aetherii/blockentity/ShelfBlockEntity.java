package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ShelfBlockEntity extends BlockEntity implements Container {
    public static final int SLOT_COUNT = 3;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.SHELF.get(), pos, state);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(this.items, slot, amount);
        if (!stack.isEmpty()) {
            this.updateSlot(slot);
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    public ItemStack swapItemNoUpdate(int slot, ItemStack stack) {
        ItemStack removed = this.removeItemNoUpdate(slot);
        this.setItemNoUpdate(slot, stack);
        return removed;
    }

    public void setItemNoUpdate(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        this.lastInteractedSlot = slot;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        this.updateSlot(slot);
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !stack.isEmpty() && this.getItem(slot).isEmpty();
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    private void updateSlot(int slot) {
        this.lastInteractedSlot = slot;
        this.setChanged(GameEvent.BLOCK_ACTIVATE);
    }

    public void setChanged(@Nullable GameEvent event) {
        super.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            this.level.updateNeighbourForOutputSignal(this.worldPosition, this.getBlockState().getBlock());
            if (event != null) {
                this.level.gameEvent(event, this.worldPosition, GameEvent.Context.of(this.getBlockState()));
            }
        }
    }

    @Override
    public void setChanged() {
        this.setChanged(GameEvent.BLOCK_ACTIVATE);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items);
        this.lastInteractedSlot = tag.getInt("last_interacted_slot");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items, true);
        tag.putInt("last_interacted_slot", this.lastInteractedSlot);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            this.load(tag);
        }
    }
}
