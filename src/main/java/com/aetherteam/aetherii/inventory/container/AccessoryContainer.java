package com.aetherteam.aetherii.inventory.container;

import com.aetherteam.aetherii.network.packet.clientbound.UpdateAccessoriesPacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;

public class AccessoryContainer extends SimpleContainer implements INBTSerializable<ListTag> {
    private final NonNullList<ItemStack> lastItems;

    public AccessoryContainer(int size) {
        super(size);
        this.lastItems = NonNullList.withSize(size, ItemStack.EMPTY);
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

    public void postTickUpdate(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            if (!this.lastItems.equals(this.getItems())) {
                PacketDistributor.sendToAllPlayers(new UpdateAccessoriesPacket(this.getItems()));
                this.lastItems.clear();
                for (int i = 0; i < this.getItems().size(); i++) {
                    this.lastItems.set(i, this.getItem(i));
                }
            }
        }
    }

    public void dropItems(LivingEntity entity, Collection<ItemEntity> drops) {
        NonNullList<ItemStack> items = this.getItems();
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                if (!EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                    itemEntity.setDefaultPickUpDelay();
                    drops.add(itemEntity);
                }
                this.setItem(i, ItemStack.EMPTY);
            }
        }
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
