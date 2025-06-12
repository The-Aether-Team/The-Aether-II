package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.CanisterSlot;
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

        // Alkahest Purifier //todo
        this.addSlot(new Slot(container, 0, 80, 20)); // Input

        this.addSlot(new CanisterSlot(this, container, 1, 16, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 2, 38, 20)); // Canister
        this.addSlot(new CanisterSlot(this, container, 3, 16, 75)); // Canister
        this.addSlot(new CanisterSlot(this, container, 4, 38, 75)); // Canister

        this.addSlot(new Slot(container, 5, 144, 45)); // Output

        this.addSlot(new Slot(container, 6, 144, 75)); // Byproduct

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
    public ItemStack quickMoveStack(Player player, int slotIndex) { //todo
        ItemStack itemStack = ItemStack.EMPTY;
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
        return stack.is(AetherIIItems.ARKENIUM_ACID_CANISTER);
    }

//    public ItemStack getInputStack() {
//        return this.getItems().get(0);
//    }

    public float getProcessingProgress() {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

//    public int getFuelCount() {
//        return this.data.get(2);
//    }

    public Container getContainer() {
        return this.container;
    }

    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean p_361547_, boolean p_363944_, RecipeHolder<?> recipeHolder, final ServerLevel level, Inventory container) { //todo no idea what some of these values do yet
        final List<Slot> list = List.of(this.getSlot(0), this.getSlot(9));

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
        }, 1, 1, List.of(this.getSlot(0)), list, container, (RecipeHolder<AlkahestPurificationRecipe>) recipeHolder, p_361547_, p_363944_);
    }
}
