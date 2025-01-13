package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.AltarFuelSlot;
import com.aetherteam.aetherii.inventory.menu.slot.AltarResultSlot;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.set.AetherIIRecipePropertySets;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.List;

public class AltarMenu extends RecipeBookMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;
    private final RecipePropertySet acceptedInputs;

    public AltarMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(10), new SimpleContainerData(3));
    }

    public AltarMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(AetherIIMenuTypes.ALTAR.get(), containerId);
        checkContainerSize(container, 10);
        checkContainerDataCount(data, 3);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        this.acceptedInputs = this.level.recipeAccess().propertySet(AetherIIRecipePropertySets.ALTAR_INPUT);

        // Altar
        this.addSlot(new Slot(container, 0, 51, 58)); // Input
        int x = 51;
        int y = 26;
        Direction direction = Direction.WEST;
        for (int i = 1; i <= 8; i++) {
            this.addSlot(new AltarFuelSlot(this, container, i, x, y)); // Fuel
            if (i % 2 == 0) {
                direction = direction.getCounterClockWise();
            }
            x += (32 * direction.getStepX());
            y += (32 * direction.getStepZ());
        }
        this.addSlot(new AltarResultSlot(playerInventory.player, container, 9, 140, 58)); // Output

        // Inventory
        for (int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 190));
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
        return AetherIIRecipeBookTypes.ALTAR;
    }

//    @Override
//    public boolean shouldMoveToInventory(int slotIndex) { //todo
//        return slotIndex < 1 || slotIndex > 8;
//    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (slotIndex == 9) {
                if (!this.moveItemStackTo(slotStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else if (slotIndex > 8) {
                if (this.canProcess(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 10 && slotIndex < 37) {
                    if (!this.moveItemStackTo(slotStack, 37, 46, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 37 && slotIndex < 46 && !this.moveItemStackTo(slotStack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 10, 46, false)) {
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
        return stack.is(AetherIITags.Items.ALTAR_FUEL);
    }

    public ItemStack getInputStack() {
        return this.getItems().get(0);
    }

    public float getProcessingProgress() {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public int getFuelCount() {
        return this.data.get(2);
    }

    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean p_361547_, boolean p_363944_, RecipeHolder<?> recipeHolder, final ServerLevel level, Inventory container) { //todo no idea what some of these values do yet
        final List<Slot> list = List.of(this.getSlot(0), this.getSlot(9));

        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
            public void fillCraftSlotsStackedContents(StackedItemContents container) {
                AltarMenu.this.fillCraftSlotsStackedContents(container);
            }

            public void clearCraftingContent() {
                list.forEach((slot) -> slot.set(ItemStack.EMPTY));
            }

            public boolean recipeMatches(RecipeHolder<AltarEnchantingRecipe> holder) {
                return holder.value().matches(new SingleRecipeInput(AltarMenu.this.container.getItem(0)), level);
            }
        }, 1, 1, List.of(this.getSlot(0)), list, container, (RecipeHolder<AltarEnchantingRecipe>) recipeHolder, p_361547_, p_363944_);
    }
}
