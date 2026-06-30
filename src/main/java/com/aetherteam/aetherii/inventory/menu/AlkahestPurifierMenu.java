package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.CanisterSlot;
import com.aetherteam.aetherii.inventory.menu.slot.PurifierByproductSlot;
import com.aetherteam.aetherii.inventory.menu.slot.PurifierResultSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public class AlkahestPurifierMenu extends RecipeBookMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;

    public AlkahestPurifierMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(7), new SimpleContainerData(3));
    }

    public AlkahestPurifierMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(AetherIIMenuTypes.ALKAHEST_PURIFIER.get(), containerId);
        checkContainerSize(container, 7);
        checkContainerDataCount(data, 3);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        container.startOpen(playerInventory.player);

        // Alkahest Purifier
        this.addSlot(new Slot(container, 0, 80, 20)); // Input

        this.addSlot(new CanisterSlot(this, container, 1, 16, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 2, 38, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 3, 16, 75)); // Canister
        this.addSlot(new CanisterSlot(this, container, 4, 38, 75)); // Canister

        this.addSlot(new PurifierResultSlot(playerInventory.player, container, 5, 144, 45)); // Output

        this.addSlot(new PurifierByproductSlot(container, 6, 144, 75)); // Byproduct

        this.addStandardInventorySlots(playerInventory, 8, 111);
        this.addDataSlots(data);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        if (this.container instanceof StackedContentsCompatible container) {
            container.fillStackedContents(itemHelper);
        }
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return AetherIIRecipeBookTypes.ALKAHEST_PURIFIER;
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe recipe) {
        return recipe instanceof AlkahestPurificationRecipe purifierRecipe
                && purifierRecipe.matches(new SingleRecipeInputWithRandom(this.container.getItem(0), this.level.getRandom()), this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 5;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 7;
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (slotIndex == 5 || slotIndex == 6) {
                if (!this.moveItemStackTo(slotStack, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else if (slotIndex > 6) {
                if (this.canProcess(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 7 && slotIndex < 34) {
                    if (!this.moveItemStackTo(slotStack, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 34 && slotIndex < 43 && !this.moveItemStackTo(slotStack, 7, 34, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 7, 43, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    protected boolean canProcess(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get(), new SingleRecipeInputWithRandom(stack, this.level.getRandom()), this.level).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return stack.is(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
    }

    public float getProcessingProgress() {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public int getAlkahestLevels() {
        return this.data.get(2);
    }

    public Container getContainer() {
        return this.container;
    }

    private void addStandardInventorySlots(Inventory playerInventory, int leftCol, int topRow) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, leftCol + column * 18, topRow + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, leftCol + column * 18, topRow + 58));
        }
    }
}
