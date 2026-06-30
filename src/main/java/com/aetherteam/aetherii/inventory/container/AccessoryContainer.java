package com.aetherteam.aetherii.inventory.container;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import com.aetherteam.aetherii.network.packet.clientbound.SetAccessoriesPacket;
import com.aetherteam.aetherii.util.ItemStackCodecs;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import com.aetherteam.aetherii.network.PacketDistributor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccessoryContainer extends SimpleContainer {
    public static final MapCodec<AccessoryContainer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackCodecs.OPTIONAL_CODEC.listOf().fieldOf("current_items").forGetter(AccessoryContainer::getCurrentItems),
            ItemStackCodecs.OPTIONAL_CODEC.listOf().fieldOf("last_items").forGetter(container -> container.lastItems)
    ).apply(instance, AccessoryContainer::new));
    public static final StreamCodec<FriendlyByteBuf, AccessoryContainer> STREAM_CODEC = StreamCodec.composite(
            ItemStackCodecs.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), AccessoryContainer::getCurrentItems,
            ItemStackCodecs.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), container -> container.lastItems,
            AccessoryContainer::new);

    private final NonNullList<ItemStack> lastItems;

    protected AccessoryContainer(List<ItemStack> currentItems, List<ItemStack> lastItems) {
        super(toContainerArray(currentItems));
        this.lastItems = toNonNullList(lastItems);
    }

    public AccessoryContainer() {
        super(4);
        this.lastItems = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    private static ItemStack[] toContainerArray(List<ItemStack> stacks) {
        ItemStack[] items = new ItemStack[4];
        for (int i = 0; i < items.length; i++) {
            items[i] = i < stacks.size() ? stacks.get(i) : ItemStack.EMPTY;
        }
        return items;
    }

    private static NonNullList<ItemStack> toNonNullList(List<ItemStack> stacks) {
        NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
        for (int i = 0; i < items.size() && i < stacks.size(); i++) {
            items.set(i, stacks.get(i));
        }
        return items;
    }

    private List<ItemStack> getCurrentItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < this.getContainerSize(); i++) {
            items.set(i, this.getItem(i));
        }
        return items;
    }

    public void postTickUpdate(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            Map<Integer, ItemStack> map = null;
            for (int i = 0; i < this.getContainerSize(); i++) {
                ItemStack lastStack = this.lastItems.get(i);
                ItemStack stack = this.getItem(i);
                if (!ItemStack.matches(stack, lastStack)) {
                    if (map == null) {
                        map = new Int2ObjectOpenHashMap<>();
                    }
                    map.put(i, stack);
                    AttributeMap entityAttributes = entity.getAttributes();
                    if (!lastStack.isEmpty() && lastStack.getItem() instanceof AccessoryItem lastAccessory) {
                        Set<AccessoryItem.ConditionalAttribute> accessoryAttributes = lastAccessory.getAttributes(lastStack);
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
                    if (!stack.isEmpty() && !isBroken(stack) && stack.getItem() instanceof AccessoryItem lastAccessory) {
                        Set<AccessoryItem.ConditionalAttribute> accessoryAttributes = lastAccessory.getAttributes(stack);
                        for (AccessoryItem.ConditionalAttribute conditionalAttribute : accessoryAttributes) {
                            AttributeInstance attributeinstance = entity.getAttributes().getInstance(conditionalAttribute.attribute());
                            if (attributeinstance != null) {
                                attributeinstance.removeModifier(conditionalAttribute.modifier().getModifier(stack).getId());
                                attributeinstance.addTransientModifier(conditionalAttribute.modifier().getModifier(stack));
                            }
                        }
                    }
                }
            }
            if (map != null) {
                if (!map.isEmpty()) {
                    List<Pair<Integer, ItemStack>> newList = Lists.newArrayListWithCapacity(map.size());
                    map.forEach((i, stack) -> {
                        ItemStack copyStack = stack.copy();
                        newList.add(Pair.of(i, copyStack));
                        this.lastItems.set(i, copyStack);
                    });
                    PacketDistributor.sendToAllPlayers(new SetAccessoriesPacket(entity.getId(), newList));
                }
            }
        }
        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack stack = this.getItem(i);
            if (stack.getItem() instanceof AccessoryItem accessory) {
                accessory.tick(stack, entity, i);
            }
        }
    }

    public void setItemWithEquip(LivingEntity wearer, int i, ItemStack stack) {
        ItemStack oldStack = this.getItem(i);
        this.setItem(i, stack);
        this.onEquipItem(wearer, i, oldStack, stack);
    }

    public void onEquipItem(LivingEntity wearer, int i, ItemStack oldItem, ItemStack newItem) {
        if (!wearer.isSpectator()) {
            if (!ItemStack.matches(oldItem, newItem) && !wearer.firstTick) {
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
            if (gameRules.getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                keepInventory = true;
            }
        }
        if (!keepInventory) {
            for (int i = 0; i < this.getContainerSize(); i++) {
                ItemStack stack = this.getItem(i);
                if (!stack.isEmpty()) {
                    if (true) {
                        ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack.copy());
                        itemEntity.setDefaultPickUpDelay();
                        drops.add(itemEntity);
                    }
                    this.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    private static boolean isBroken(ItemStack stack) {
        return stack.isDamageableItem() && stack.getDamageValue() >= stack.getMaxDamage();
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
