package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.inventory.menu.slot.AltarResultSlot;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
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
import com.aetherteam.aetherii.recipe.input.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class AltarMenu extends RecipeBookMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;

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

        // Altar
        this.addSlot(new Slot(container, 0, 51, 58)); // Input
        int x = 51;
        int y = 26;
        Direction direction = Direction.WEST;
        for (int i = 1; i <= 8; i++) {
            this.addSlot(new Slot(container, i, x, y) {
                public static final ResourceLocation SLOT_FUEL = new ResourceLocation(AetherII.MODID, "container/altar/slot_fuel");

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return AltarMenu.this.isFuel(stack);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_FUEL);
                }
            }); // Fuel
            if (i % 2 == 0) {
                direction = direction.getCounterClockWise();
            }
            x += (32 * direction.getStepX());
            y += (32 * direction.getStepZ());
        }
        this.addSlot(new AltarResultSlot(playerInventory.player, container, 9, 140, 58)); // Output

        this.addStandardInventorySlots(playerInventory, 8, 132);
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
        return AetherIIRecipeBookTypes.ALTAR;
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(9).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe recipe) {
        return recipe instanceof AltarEnchantingRecipe altarRecipe
                && altarRecipe.matches(new SingleRecipeInput(this.container.getItem(0)), this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 9;
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
        return 10;
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex < 1 || slotIndex > 8;
    }

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
        return this.level.getRecipeManager().getRecipeFor(AetherIIRecipeTypes.ALTAR_ENCHANTING.get(), new SingleRecipeInput(stack), this.level).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return stack.is(AetherIITags.Items.ALTAR_FUEL);
    }

    public float getProcessingProgress() {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public int getFuelCount() {
        return this.data.get(2);
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
