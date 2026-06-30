package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AccessorySlot extends Slot {
    private final Player owner;
    private final AccessoryContainer.SlotType slotType;

    public AccessorySlot(Container container, Player owner, AccessoryContainer.SlotType slotType, int index, int xPosition, int yPosition, ResourceLocation emptyIcon) {
        super(container, index, xPosition, yPosition);
        this.owner = owner;
        this.slotType = slotType;
        this.setBackground(InventoryMenu.BLOCK_ATLAS, emptyIcon);
    }

    @Override
    public void setByPlayer(ItemStack newItem) {
        ItemStack oldItem = this.getItem().copy();
        if (this.container instanceof AccessoryContainer accessoryContainer) {
            accessoryContainer.onEquipItem(this.owner, this.index, oldItem, newItem);
        }
        super.setByPlayer(newItem);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        boolean matchesTag = stack.is(this.slotType.getAccessoryTag());
        boolean duplicate = this.container.hasAnyMatching((otherStack) -> otherStack.getItem() == stack.getItem());
        return matchesTag && !duplicate;
    }

    @Override
    public boolean mayPickup(Player player) {
        ItemStack stack = this.getItem();
        return (stack.isEmpty() || player.isCreative() || !stack.isEnchanted()) && super.mayPickup(player);
    }

    public AccessoryContainer.SlotType getSlotType() {
        return this.slotType;
    }
}
