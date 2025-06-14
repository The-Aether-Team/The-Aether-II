package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.CanisterSlot;
import com.aetherteam.aetherii.inventory.menu.slot.PurifierByproductSlot;
import com.aetherteam.aetherii.inventory.menu.slot.PurifierResultSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.set.AetherIIRecipePropertySets;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.Level;

import java.util.List;

public class AlkahestPurifierMenu extends RecipeBookMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;
    private final RecipePropertySet acceptedInputs;

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
        this.acceptedInputs = this.level.recipeAccess().propertySet(AetherIIRecipePropertySets.ALKAHEST_PURIFIER_INPUT);
        container.startOpen(playerInventory.player);

        // Alkahest Purifier
        this.addSlot(new Slot(container, 0, 80, 20)); // Input

        this.addSlot(new CanisterSlot(this, container, 1, 16, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 2, 38, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 3, 16, 75)); // Canister
        this.addSlot(new CanisterSlot(this, container, 4, 38, 75)); // Canister

        this.addSlot(new PurifierResultSlot(playerInventory.player, container, 5, 144, 45)); // Output

        this.addSlot(new PurifierByproductSlot(container, 6, 144, 75)); // Byproduct

        // Inventory
        for (int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 169));
        }
        this.addDataSlots(data);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents itemHelper) {
        if (this.container instanceof StackedContentsCompatible container) {
            container.fillStackedContents(itemHelper);
        }
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return AetherIIRecipeBookTypes.ALKAHEST_PURIFIER;
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
        return this.acceptedInputs.test(stack);
    }

    public boolean isFuel(ItemStack stack) {
        return stack.is(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
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

    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean isCreative, RecipeHolder<?> recipeHolder, final ServerLevel level, Inventory container) {
        final List<Slot> list = List.of(this.getSlot(0), this.getSlot(5), this.getSlot(6));

        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
            public void fillCraftSlotsStackedContents(StackedItemContents container) {
                AlkahestPurifierMenu.this.fillCraftSlotsStackedContents(container);
            }

            public void clearCraftingContent() {
                list.forEach((slot) -> slot.set(ItemStack.EMPTY));
            }

            public boolean recipeMatches(RecipeHolder<AlkahestPurificationRecipe> holder) {
                return holder.value().matches(new SingleRecipeInputWithRandom(AlkahestPurifierMenu.this.container.getItem(0), AlkahestPurifierMenu.this.level.getRandom()), level);
            }
        }, 1, 1, List.of(this.getSlot(0)), list, container, (RecipeHolder<AlkahestPurificationRecipe>) recipeHolder, useMaxItems, isCreative);
    }
}
