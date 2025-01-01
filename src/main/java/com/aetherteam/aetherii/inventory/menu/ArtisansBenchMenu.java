package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class ArtisansBenchMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private SelectableRecipe.SingleInputSet<StonecutterRecipe> recipesForInput;
    private ItemStack input = ItemStack.EMPTY;
    long lastSoundTime;
    final Slot inputSlot;
    final Slot resultSlot;
    Runnable slotUpdateListener = () -> { };
    public final Container container = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            super.setChanged();
            ArtisansBenchMenu.this.slotsChanged(this);
            ArtisansBenchMenu.this.slotUpdateListener.run();
        }
    };
    final ResultContainer resultContainer = new ResultContainer();

    public ArtisansBenchMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ArtisansBenchMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(AetherIIMenuTypes.ARTISANS_BENCH.get(), containerId);
        this.selectedRecipeIndex = DataSlot.standalone();
        this.recipesForInput = SelectableRecipe.SingleInputSet.empty();
        this.access = access;
        this.level = playerInventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());
                ArtisansBenchMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());
                ItemStack itemStack = ArtisansBenchMenu.this.inputSlot.remove(1);
                if (!itemStack.isEmpty()) {
                    ArtisansBenchMenu.this.setupResultSlot(ArtisansBenchMenu.this.selectedRecipeIndex.get());
                }

                access.execute((level, pos) -> {
                    long l = level.getGameTime();
                    if (ArtisansBenchMenu.this.lastSoundTime != l) {
                        level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        ArtisansBenchMenu.this.lastSoundTime = l;
                    }
                });
                super.onTake(player, stack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(ArtisansBenchMenu.this.inputSlot.getItem());
            }
        });

        for (int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public SelectableRecipe.SingleInputSet<StonecutterRecipe> getVisibleRecipes() {
        return this.recipesForInput;
    }

    public int getNumberOfVisibleRecipes() {
        return this.recipesForInput.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipesForInput.isEmpty();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, AetherIIBlocks.ARTISANS_BENCH.get());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (this.selectedRecipeIndex.get() == id) {
            return false;
        } else {
            if (this.isValidRecipeIndex(id)) {
                this.selectedRecipeIndex.set(id);
                this.setupResultSlot(id);
            }
            return true;
        }
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipesForInput.size();
    }

    @Override
    public void slotsChanged(Container inventory) {
        ItemStack itemStack = this.inputSlot.getItem();
        if (!itemStack.is(this.input.getItem())) {
            this.input = itemStack.copy();
            this.setupRecipeList(itemStack);
        }
    }

    private void setupRecipeList(ItemStack stack) {
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipesForInput = this.level.recipeAccess().stonecutterRecipes().selectByInput(stack);
        } else {
            this.recipesForInput = SelectableRecipe.SingleInputSet.empty();
        }

    }

    void setupResultSlot(int slot) {
        Optional<RecipeHolder<StonecutterRecipe>> optional;
        if (!this.recipesForInput.isEmpty() && this.isValidRecipeIndex(slot)) {
            SelectableRecipe.SingleInputEntry<StonecutterRecipe> singleinputentry = this.recipesForInput.entries().get(slot);
            optional = singleinputentry.recipe().recipe();
        } else {
            optional = Optional.empty();
        }
        optional.ifPresentOrElse((recipeHolder) -> {
            this.resultContainer.setRecipeUsed(recipeHolder);
            this.resultSlot.set(recipeHolder.value().assemble(new SingleRecipeInput(this.container.getItem(0)), this.level.registryAccess()));
        }, () -> {
            this.resultSlot.set(ItemStack.EMPTY);
            this.resultContainer.setRecipeUsed(null);
        });
        this.broadcastChanges();
    }

    @Override
    public MenuType<?> getType() {
        return AetherIIMenuTypes.ARTISANS_BENCH.get();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            Item item = itemStack1.getItem();
            itemStack = itemStack1.copy();
            if (index == 1) {
                item.onCraftedBy(itemStack1, player.level(), player);
                if (!this.moveItemStackTo(itemStack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemStack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.recipeAccess().stonecutterRecipes().acceptsInput(itemStack1)) {
                if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemStack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemStack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack1);
            this.broadcastChanges();
        }

        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_40313_, p_40314_) -> this.clearContainer(player, this.container));
    }
}