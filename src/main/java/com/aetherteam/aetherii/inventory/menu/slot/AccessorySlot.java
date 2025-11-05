package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.item.equipment.AccessoryItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AccessorySlot extends Slot {
    private final TagKey<Item> accessoryTag;

    public AccessorySlot(Container container, TagKey<Item> accessoryTag, int index, int xPosition, int yPosition, ResourceLocation emptyIcon) {
        super(container, index, xPosition, yPosition);
        this.accessoryTag = accessoryTag;
        this.setBackground(emptyIcon);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(this.accessoryTag);
    }

    @Override
    public boolean mayPickup(Player player) {
        ItemStack stack = this.getItem();
        return (stack.isEmpty() || player.isCreative() || !EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(player);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        if (!player.level().isClientSide() && stack.getItem() instanceof AccessoryItem accessory) {
            accessory.onUnequip(stack, player);
        }
        super.onTake(player, stack);
    }
}
