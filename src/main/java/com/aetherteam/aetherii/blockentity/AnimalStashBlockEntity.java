package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AnimalStashBlockEntity extends RandomizableContainerBlockEntity {
    private static final Component NAME = Component.translatable("aether_ii.container.animal_stash");
    private NonNullList<ItemStack> items;

    protected AnimalStashBlockEntity(BlockEntityType<?> p_155327_, BlockPos p_155328_, BlockState p_155329_) {
        super(p_155327_, p_155328_, p_155329_);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
    }

    public AnimalStashBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.ANIMAL_STASH.get(), pos, state);
    }

    public int getContainerSize() {
        return 27;
    }

    protected Component getDefaultName() {
        return NAME;
    }

    protected void loadAdditional(ValueInput p_422326_) {
        super.loadAdditional(p_422326_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_422326_)) {
            ContainerHelper.loadAllItems(p_422326_, this.items);
        }

    }

    protected void saveAdditional(ValueOutput p_422157_) {
        super.saveAdditional(p_422157_);
        if (!this.trySaveLootTable(p_422157_)) {
            ContainerHelper.saveAllItems(p_422157_, this.items);
        }

    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> p_59110_) {
        this.items = p_59110_;
    }

    protected AbstractContainerMenu createMenu(int p_59082_, Inventory p_59083_) {
        return ChestMenu.threeRows(p_59082_, p_59083_, this);
    }

    public void setBlockState(BlockState p_155251_) {
        BlockState oldState = this.getBlockState();
        super.setBlockState(p_155251_);
        if (oldState.getValue(ChestBlock.FACING) != p_155251_.getValue(ChestBlock.FACING) || oldState.getValue(ChestBlock.TYPE) != p_155251_.getValue(ChestBlock.TYPE)) {
            this.invalidateCapabilities();
        }

    }
}