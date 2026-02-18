package com.aetherteam.aetherii.inventory.container;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.EntityAccessor;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.SetAccessoriesPacket;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class AccessoryContainer extends SimpleContainer {
    public static final MapCodec<AccessoryContainer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.OPTIONAL_CODEC.sizeLimitedListOf(4).fieldOf("current_items").forGetter(SimpleContainer::getItems),
            ItemStack.OPTIONAL_CODEC.sizeLimitedListOf(4).fieldOf("last_items").forGetter(container -> container.lastItems)
    ).apply(instance, AccessoryContainer::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryContainer> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), SimpleContainer::getItems,
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), (container) -> container.lastItems,
            AccessoryContainer::new);

    private final NonNullList<ItemStack> lastItems;

    protected AccessoryContainer(List<ItemStack> currentItems, List<ItemStack> lastItems) {
        super(currentItems.toArray(ItemStack[]::new));
        this.lastItems = NonNullList.of(ItemStack.EMPTY, lastItems.toArray(ItemStack[]::new));
    }

    public AccessoryContainer() {
        super(4);
        this.lastItems = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    public void postTickUpdate(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            Map<Integer, ItemStack> map = null;
            for (int i = 0; i < this.getItems().size(); i++) {
                ItemStack lastStack = this.lastItems.get(i);
                ItemStack stack = this.getItem(i);
                if (!ItemStack.matches(stack, lastStack)) {
                    if (map == null) {
                        map = new Int2ObjectOpenHashMap<>();
                    }
                    map.put(i, stack);
                    AttributeMap entityAttributes = entity.getAttributes();
                    AetherII.LOGGER.info(lastStack + " " + stack);
                    if (!lastStack.isEmpty() && lastStack.getItem() instanceof AccessoryItem lastAccessory) {
                        Set<AccessoryItem.ConditionalAttribute> accessoryAttributes = lastAccessory.getBaseAttributes();
                        for (AccessoryItem.ConditionalAttribute conditionalAttribute : accessoryAttributes) {
                            AttributeInstance attributeinstance = entityAttributes.getInstance(conditionalAttribute.attribute());
                            if (attributeinstance != null) {
                                attributeinstance.removeModifier(conditionalAttribute.modifier().getModifier(lastStack));
                            }
                        }
                    }
                }
            }
            if (map != null) {
                for (Map.Entry<Integer, ItemStack> entry : map.entrySet()) {
                    ItemStack stack = entry.getValue();
                    if (!stack.isEmpty() && !stack.isBroken() && stack.getItem() instanceof AccessoryItem lastAccessory) {
                        Set<AccessoryItem.ConditionalAttribute> accessoryAttributes = lastAccessory.getBaseAttributes();
                        for (AccessoryItem.ConditionalAttribute conditionalAttribute : accessoryAttributes) {
                            AttributeInstance attributeinstance = entity.getAttributes().getInstance(conditionalAttribute.attribute());
                            if (attributeinstance != null) {
                                attributeinstance.removeModifier(conditionalAttribute.modifier().getModifier(stack).id());
                                attributeinstance.addTransientModifier(conditionalAttribute.modifier().getModifier(stack));
                            }
                        }
                    }
                }
            }
            if (map != null) {
                if (!map.isEmpty()) {
                    List<Pair<Integer, ItemStack>> newList = Lists.newArrayListWithCapacity(map.size());
                    for (int i = 0; i < newList.size(); i++) {
                        ItemStack copyStack = map.get(i).copy();
                        this.equipItem(entity, i, this.lastItems.get(i), copyStack);
                        newList.add(Pair.of(i, copyStack));
                        this.lastItems.set(i, copyStack);
                    }
                    PacketDistributor.sendToAllPlayers(new SetAccessoriesPacket(entity.getId(), newList));
                }
            }
        }
        for (int i = 0; i < this.getItems().size(); i++) {
            ItemStack stack = this.getItem(i);
            if (stack.getItem() instanceof AccessoryItem accessory) {
                accessory.tick(stack, entity, i);
            }
        }
    }

    public void setItemWithEquip(LivingEntity wearer, int i, ItemStack stack) {
        this.equipItem(wearer, i, this.getItems().set(i, stack), stack);
    }

    public void equipItem(LivingEntity wearer, int i, ItemStack oldItem, ItemStack newItem) {
        if (!wearer.isSpectator()) {
            if (!ItemStack.isSameItemSameComponents(oldItem, newItem) && !((EntityAccessor) wearer).aether_ii$getFirstTick()) {
                if (!wearer.isSilent()) {
                    if (!newItem.isEmpty() && newItem.getItem() instanceof AccessoryItem accessory) {
                        accessory.onEquip(newItem, wearer, i);
                    } else if (newItem.isEmpty() && !oldItem.isEmpty() && oldItem.getItem() instanceof AccessoryItem accessoryItem) {
                        accessoryItem.onUnequip(oldItem, wearer, i);
                    }
                }
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
