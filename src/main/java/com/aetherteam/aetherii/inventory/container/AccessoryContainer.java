package com.aetherteam.aetherii.inventory.container;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class AccessoryContainer extends SimpleContainer implements INBTSerializable<ListTag> {
    public AccessoryContainer(int size) {
        super(size);
    }

    @Override
    public ListTag serializeNBT(HolderLookup.Provider provider) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.putByte("Slot", (byte) i);
                listTag.add(itemstack.save(provider, tag));
            }
        }

        return listTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, ListTag listTag) {
        this.clearContent();

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag tag = listTag.getCompound(i);
            int index = tag.getByte("Slot") & 255;
            if (index < this.getContainerSize()) {
                this.setItem(index, ItemStack.parse(provider, tag).orElse(ItemStack.EMPTY));
            }
        }
    }

    public void tick(LivingEntity livingEntity) {

    }

    public enum SlotType {
        RELIC(new int[] { 0 }),
        HANDWEAR(new int[] { 1 }),
        ACCESSORY(new int[] { 2, 3 });

        private final int[] index;

        SlotType(int[] index) {
            this.index = index;
        }

        public int[] getIndex() {
            return this.index;
        }
    }
}
