package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AccessorySlot extends Slot {
    private final Player owner;
    private final AccessoryContainer.SlotType slotType;

    public AccessorySlot(Container container, Player owner, AccessoryContainer.SlotType slotType, int index, int xPosition, int yPosition, Identifier emptyIcon) {
        super(container, index, xPosition, yPosition);
        this.owner = owner;
        this.slotType = slotType;
        this.setBackground(emptyIcon);
    }

    @Override
    public void setByPlayer(ItemStack newItem, ItemStack oldItem) {
        if (this.container instanceof AccessoryContainer accessoryContainer) {
            accessoryContainer.onEquipItem(this.owner, this.index, oldItem, newItem);
        }
        super.setByPlayer(newItem, oldItem);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) { //todo: items of the same type with different components cant be swapped
        return stack.is(this.slotType.getAccessoryTag()) && !this.container.hasAnyMatching((otherStack) -> otherStack.getItem() == stack.getItem());
    }

    @Override
    public boolean mayPickup(Player player) {
        ItemStack stack = this.getItem();
        return (stack.isEmpty() || player.isCreative() || !EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(player);
    }
}
