package com.aetherteam.aetherii.inventory.menu.slot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessorySlot extends Slot {
    private final TagKey<Item> accessoryTag;

    public AccessorySlot(Container container, TagKey<Item> accessoryTag, int index, int xPosition, int yPosition, ResourceLocation emptyIcon) {
        super(container, index, xPosition, yPosition);
        this.accessoryTag = accessoryTag;
        this.setBackground(emptyIcon);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(this.accessoryTag) && super.mayPlace(stack);
    }
}
