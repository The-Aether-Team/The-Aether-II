package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.CraftingMenuAccessor;
import com.mojang.datafixers.util.Pair;
import io.wispforest.accessories.api.menu.AccessoriesSlotGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class GuidebookEquipmentMenu extends AbstractContainerMenu {
    private static final Map<EquipmentSlot, ResourceLocation> TEXTURE_EMPTY_SLOTS = Map.of(
            EquipmentSlot.FEET,
            InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
            EquipmentSlot.LEGS,
            InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
            EquipmentSlot.CHEST,
            InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
            EquipmentSlot.HEAD,
            InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
    );
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 2, 2);
    private final ResultContainer resultSlots = new ResultContainer();
    private final Player owner;
    private int addedSlots = 0;

    public GuidebookEquipmentMenu(int containerId, Inventory playerInventory) {
        super(AetherIIMenuTypes.GUIDEBOOK.get(), containerId);
        this.owner = playerInventory.player;

        int xOffset = this.owner.isCreative() ? 19 : 0; //todo theres a bug with this being offset when it shouldnt be sometimes i guess

        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 202 + xOffset, 50));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new Slot(this.craftSlots, j + i * 2,  xOffset + 146 + j * 18, 40 + i * 18));
            }
        }

        AccessoriesSlotGenerator generator = AccessoriesSlotGenerator.of(this::addSlot, 64, 38, this.owner, AetherIIAccessorySlots.getRelicSlotType(), AetherIIAccessorySlots.getHandwearSlotType(), AetherIIAccessorySlots.getAccessorySlotType());

        if (generator != null) {
            this.addedSlots = generator.padding(0).column();
        }

        for (int k = 0; k < 4; k++) {
            EquipmentSlot equipmentslot = SLOT_IDS[k];
            ResourceLocation resourcelocation = TEXTURE_EMPTY_SLOTS.get(equipmentslot);
            this.addSlot(new ArmorSlot(playerInventory, this.owner, equipmentslot, 39 - k, -64, 38 + k * 18, resourcelocation));
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 102 + j1 * 18, 92 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 102 + i1 * 18, 150));
        }

        this.addSlot(new Slot(playerInventory, 40, -64, 112) {
            @Override
            public void setByPlayer(ItemStack p_270969_, ItemStack p_299918_) {
                GuidebookEquipmentMenu.this.owner.onEquipItem(EquipmentSlot.OFFHAND, p_299918_, p_270969_);
                super.setByPlayer(p_270969_, p_299918_);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container inventory) {
        CraftingMenuAccessor.callSlotChangedCraftingGrid(this, this.owner.level(), this.owner, this.craftSlots, this.resultSlots, null);
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultSlots.clearContent();
        if (!player.level().isClientSide) {
            this.clearContainer(player, this.craftSlots);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentslot = player.getEquipmentSlotForItem(itemstack);
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 1 && index < 5) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_RELICS) && !this.slots.get(5).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_RELICS) && !this.slots.get(6).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_HANDWEAR) && !this.slots.get(7).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 7, 8, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_ACCESSORIES) && !this.slots.get(8).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 8, 9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && !this.slots.get(12 - equipmentslot.getIndex()).hasItem()) {
                int i = 12 - equipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(49).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 49, 50, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 13 && index < 40) {
                if (!this.moveItemStackTo(itemstack1, 40, 49, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 40 && index < 49) {
                if (!this.moveItemStackTo(itemstack1, 13, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 13, 49, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY, itemstack);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 0) {
                player.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }
}
