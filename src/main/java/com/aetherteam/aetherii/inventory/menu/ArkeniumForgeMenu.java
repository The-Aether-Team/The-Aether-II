package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.ReinforcementTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    public final Container equipmentContainer = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            ArkeniumForgeMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    public final Container materialsContainer = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            ArkeniumForgeMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    @Nullable
    private String itemName;

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(AetherIIMenuTypes.ARKENIUM_FORGE.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.equipmentContainer, 0, 29, 65));

        this.addSlot(new Slot(this.materialsContainer, 0, 69, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isPrimaryMaterial(stack);
            }
        });
        this.addSlot(new Slot(this.materialsContainer, 1, 91, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isSecondaryMaterial(stack);
            }
        });

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 169 + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 227));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, AetherIIBlocks.ARKENIUM_FORGE.get());
    }

//    @Override
//    public void slotsChanged(Container inventory) { //todo
//        ItemStack itemstack = this.materialsContainer.getItem(0);
//        ItemStack itemstack1 = this.materialsContainer.getItem(1);
//        ItemStack itemstack2 = this.resultContainer.getItem(2);
//        if (itemstack2.isEmpty() || !itemstack.isEmpty() && !itemstack1.isEmpty()) {
//            if (!itemstack.isEmpty() && !itemstack1.isEmpty()) {
//                this.setupResultSlot(itemstack, itemstack1, itemstack2);
//            }
//        } else {
//            this.resultContainer.removeItemNoUpdate(2);
//        }
//    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (slotIndex > 2) {
                if (this.isEquipment(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isPrimaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isSecondaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 3 && slotIndex < 30) {
                    if (!this.moveItemStackTo(slotStack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 30 && slotIndex < 39 && !this.moveItemStackTo(slotStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 3, 39, false)) {
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
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.equipmentContainer));
        this.access.execute((level, pos) -> this.clearContainer(player, this.materialsContainer));
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(0).hasItem()) {
                ItemStack itemstack = this.getSlot(0).getItem();
                if (StringUtil.isBlank(s)) {
                    itemstack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
        return s.length() <= 50 ? s : null;
    }

    public ItemStack getInput() {
        return this.equipmentContainer.getItem(0);
    }

    public Map<Integer, ReinforcementTier.Cost> getCosts(ItemStack itemStack) {
        Map<Integer, ReinforcementTier.Cost> costs = new HashMap<>();
        for (ReinforcementTier tier : ReinforcementTier.values()) {
            int value = tier.getTier();
            ReinforcementTier.Cost cost = tier.getCost(itemStack);
            if (costs != null) { //todo whys it saying this is never null
                costs.put(value, cost);
            }
        }
        return costs;
    }

    public int getTierCount(ItemStack itemStack) {
        return this.getCosts(itemStack).size();
    }

    public boolean isItemAtMaxTier(ItemStack itemStack) {
        int max = this.getTierCount(itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return tier != null && tier.getTier() == max;
    }

    public int getTierForItem(ItemStack itemStack) {
        int max = this.getTierCount(itemStack);
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            return Math.min(tier.getTier() + 1, max);
        } else {
            return 1;
        }
    }

    @Nullable
    public ReinforcementTier.Cost getCostForTier(ItemStack itemStack, int tier) {
        return this.getCosts(itemStack).getOrDefault(tier, null);
    }

    public boolean isEquipment(ItemStack itemStack) {
        return !itemStack.isStackable() && itemStack.getCount() == 1 && itemStack.has(DataComponents.MAX_DAMAGE);
    }

    public boolean isPrimaryMaterial(ItemStack itemStack) {
        ReinforcementTier.Cost cost = this.getCostForTier(this.getInput(), this.getTierForItem(this.getInput()));
        if (cost != null) {
            return itemStack.is(cost.primaryMaterial().asItem());
        }
        return false;
    }

    public boolean isSecondaryMaterial(ItemStack itemStack) {
        ReinforcementTier.Cost cost = this.getCostForTier(this.getInput(), this.getTierForItem(this.getInput()));
        if (cost != null) {
            return itemStack.is(cost.secondaryMaterial().asItem());
        }
        return false;
    }
}
