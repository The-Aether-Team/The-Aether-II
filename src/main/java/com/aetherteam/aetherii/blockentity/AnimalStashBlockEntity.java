package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AnimalStashBlockEntity extends RandomizableContainerBlockEntity {
    private static final Component NAME = Component.translatable("aether_ii.container.animal_stash");
    private NonNullList<ItemStack> items;

    protected AnimalStashBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
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

    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
        }

    }

    protected void saveAdditional(ValueOutput input) {
        super.saveAdditional(input);
        if (!this.trySaveLootTable(input)) {
            ContainerHelper.saveAllItems(input, this.items);
        }

    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    protected AbstractContainerMenu createMenu(int slots, Inventory inventory) {
        return ChestMenu.threeRows(slots, inventory, this);
    }
}