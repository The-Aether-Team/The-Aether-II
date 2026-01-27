package com.aetherteam.aetherii.inventory.container;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;

import java.util.Collection;
import java.util.List;

public class AccessoryContainer extends SimpleContainer {
    public static final MapCodec<AccessoryContainer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.OPTIONAL_CODEC.sizeLimitedListOf(4).fieldOf("items").forGetter(container -> container.lastItems)
    ).apply(instance, AccessoryContainer::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryContainer> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), (container) -> container.lastItems,
            AccessoryContainer::new);

    private final NonNullList<ItemStack> lastItems;

    protected AccessoryContainer(List<ItemStack> lastItems) {
        super(lastItems.toArray(ItemStack[]::new));
        this.lastItems = NonNullList.of(ItemStack.EMPTY, lastItems.toArray(ItemStack[]::new));
    }

    public AccessoryContainer() {
        super(4);
        this.lastItems = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    public void postTickUpdate(LivingEntity entity) {
        if (!this.lastItems.equals(this.getItems())) {
            for (int i = 0; i < this.getItems().size(); i++) {
                ItemStack thisItem = this.getItem(i);
                ItemStack lastItem = this.lastItems.get(i);
                if (!ItemStack.isSameItem(lastItem, thisItem)) {
                    if (!thisItem.isEmpty() && thisItem.getItem() instanceof AccessoryItem accessory) {
                        accessory.onEquip(thisItem, entity);
                    } else if (thisItem.isEmpty() && !lastItem.isEmpty() && lastItem.getItem() instanceof AccessoryItem accessoryItem) {
                        accessoryItem.onUnequip(lastItem, entity);
                    }
                }
                this.lastItems.set(i, thisItem.copy());
                entity.syncData(AetherIIDataAttachments.ACCESSORIES);
            }
        }
        for (ItemStack stack : this.getItems()) {
            if (stack.getItem() instanceof AccessoryItem accessory) {
                accessory.tick(stack, entity);
            }
        }
    }

    public void dropItems(LivingEntity entity, Collection<ItemEntity> drops) {
        boolean keepInventory = false;
        if (entity instanceof ServerPlayer serverPlayer && serverPlayer.level() instanceof ServerLevel serverLevel) {
            GameRules gameRules = serverLevel.getGameRules();
            if (gameRules.getRule(GameRules.RULE_KEEPINVENTORY).get()) {
                keepInventory = true;
            }
        }
        if (!keepInventory) {
            NonNullList<ItemStack> items = this.getItems();
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = items.get(i);
                if (!stack.isEmpty()) {
                    if (!EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                        ItemEntity itemEntity = ((LivingEntityAccessor) entity).callCreateItemStackToDrop(stack.copy(), true, false);
                        if (itemEntity != null) {
                            drops.add(itemEntity);
                        }
                    }
                    this.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public enum SlotType {
        RELIC(AetherIITags.Items.EQUIPMENT_RELICS, 0, 1),
        HANDWEAR(AetherIITags.Items.EQUIPMENT_HANDWEAR, 2),
        ACCESSORY(AetherIITags.Items.EQUIPMENT_ACCESSORIES, 3);

        private final TagKey<Item> accessoryTag;
        private final int[] index;

        SlotType(TagKey<Item> accessoryTag, int... index) {
            this.accessoryTag = accessoryTag;
            this.index = index;
        }

        public TagKey<Item> getAccessoryTag() {
            return this.accessoryTag;
        }

        public int[] getIndex() {
            return this.index;
        }
    }
}
