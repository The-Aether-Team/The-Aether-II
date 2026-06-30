package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.HourglassResultSlot;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

public class AmberHourglassMenu extends RecipeBookMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;

    public AmberHourglassMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(5), new SimpleContainerData(4));
    }

    public AmberHourglassMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(AetherIIMenuTypes.AMBER_HOURGLASS.get(), containerId);
        checkContainerSize(container, 5);
        checkContainerDataCount(data, 4);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        container.startOpen(playerInventory.player);

        // Hourglass
        this.addSlot(new Slot(container, 0, 80, 30)); // Input
        this.addSlot(new Slot(container, 1, 80, 62) {
            public static final ResourceLocation SLOT_FUEL = new ResourceLocation(AetherII.MODID, "container/amber_hourglass/slot_golden_amber");

            @Override
            public boolean mayPlace(ItemStack stack) {
                return AmberHourglassMenu.this.isFuel(stack);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_FUEL);
            }
        }); // Fuel

        this.addSlot(new HourglassResultSlot(playerInventory.player, container, 2, 48, 94)); // Output
        this.addSlot(new HourglassResultSlot(playerInventory.player, container, 3, 80, 94)); // Output
        this.addSlot(new HourglassResultSlot(playerInventory.player, container, 4, 112, 94)); // Output

        this.addStandardInventorySlots(playerInventory, 8, 140);
        this.addDataSlots(data);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        if (this.container instanceof StackedContentsCompatible contents) {
            contents.fillStackedContents(itemHelper);
        }
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return AetherIIRecipeBookTypes.AMBER_HOURGLASS;
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe recipe) {
        return recipe instanceof HourglassRestoringRecipe restoringRecipe
                && restoringRecipe.matches(new SingleRecipeInputWithRandom(this.container.getItem(0), this.level.getRandom()), this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 2;
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
        return 5;
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
            if (slotIndex >= 2 && slotIndex < 5) {
                if (!this.moveItemStackTo(slotStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else if (slotIndex > 4) {
                if (this.canProcess(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 5 && slotIndex < 32) {
                    if (!this.moveItemStackTo(slotStack, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 32 && slotIndex < 41 && !this.moveItemStackTo(slotStack, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 5, 41, false)) {
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

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    protected boolean canProcess(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor(AetherIIRecipeTypes.HOURGLASS_RESTORING.get(), new SingleRecipeInputWithRandom(stack, this.level.getRandom()), this.level).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return AetherIIDataMaps.getAmberHourglassFuel(stack) != null;
    }

    public float getProcessingProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public float getPowerProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }
        return Mth.clamp((float) this.data.get(0) / (float) i, 0.0F, 1.0F);
    }

    public boolean isPowered() {
        return this.data.get(0) > 0;
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
