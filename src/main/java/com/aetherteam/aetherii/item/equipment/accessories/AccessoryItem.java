package com.aetherteam.aetherii.item.equipment.accessories;

import java.util.List;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.aetherteam.aetherii.util.RegistryObjectUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import com.aetherteam.aetherii.integration.AttributeTooltipContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

public class AccessoryItem extends Item {
    private final AccessoryContainer.SlotType slotType;
    private final Set<ConditionalAttribute> attributes;

    public AccessoryItem(Properties properties, AccessoryContainer.SlotType slotType) {
        super(properties);
        this.slotType = slotType;
        this.attributes = this.gatherAttributes(new HashSet<>());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = AccessoryUtil.equip(player, hand, player.getItemInHand(hand), this.getSlotType());
        return new InteractionResultHolder<>(result, player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
        Multimap<Holder<Attribute>, AttributeModifier> attributesMap = ArrayListMultimap.create();
        for (ConditionalAttribute attribute : this.getBaseAttributes()) {
            attributesMap.put(attribute.attribute(), attribute.modifier().getModifier(stack));
        }
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents::add, AttributeTooltipContext.of(null, level, TooltipDisplay.DEFAULT, tooltipFlag), attributesMap, this.getSlotType().name().toLowerCase(Locale.ROOT));
    }

    public void tick(ItemStack stack, LivingEntity wearer, int slot) {
        for (ConditionalAttribute entry : this.getAttributes(stack)) {
            AttributeInstance attribute = wearer.getAttribute(entry.attribute().value());
            AttributeModifier modifier = entry.modifier().getModifier(stack);

            if (attribute != null) {
                AttributeModifier existing = attribute.getModifier(modifier.getId());
                if (existing == null && entry.condition().test(stack, wearer)) {
                    attribute.addTransientModifier(modifier);
                } else if (existing != null && (!entry.condition().test(stack, wearer))) {
                    attribute.removeModifier(existing);
                }
            }
        }
    }

    public void onEquip(ItemStack stack, LivingEntity wearer, int slot) {
        this.playEquipSound(wearer, true);
    }

    public void onUnequip(ItemStack stack, LivingEntity wearer, int slot) {
        this.playEquipSound(wearer, false);
    }

    public void playEquipSound(LivingEntity wearer, boolean equip) {
        if (!wearer.level().isClientSide() && !wearer.isSpectator() && !wearer.isSilent()) {
            wearer.level().playSeededSound(
                    null,
                    wearer.getX(),
                    wearer.getY(),
                    wearer.getZ(),
                    this.getEquipSound(),
                    wearer.getSoundSource(),
                    1.0F,
                    1.0F,
                    wearer.getRandom().nextLong()
            );
            wearer.gameEvent(GameEvent.EQUIP);
        }
    }

    public boolean rendersInFirstPerson(ItemStack stack) {
        return false;
    }

    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        return attributes;
    }

    public AccessoryContainer.SlotType getSlotType() {
        return this.slotType;
    }

    public Holder<SoundEvent> getEquipSound() {
        return RegistryObjectUtil.holder(AetherIISoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC);
    }

    public Set<ConditionalAttribute> getBaseAttributes() {
        return this.attributes;
    }

    public Set<ConditionalAttribute> getAttributes(ItemStack itemStack) {
        Set<ConditionalAttribute> conditionalAttributes = new HashSet<>(this.getBaseAttributes());
        List<Charms.CharmHolder> charmHolders = Charms.getCharmsForItem(itemStack);
        if (charmHolders != null) {
            for (int i = 0; i < charmHolders.size(); i++) {
                Charms.CharmHolder charmHolder = charmHolders.get(i);
                if (charmHolder.getStack().getItem() instanceof CharmItem charmItem) {
                    for (ItemAttributeModifiers.Entry entry : charmItem.getCharmAttributes()) {
                        conditionalAttributes.add(new ConditionalAttribute(entry.attribute(), new ConditionalModifier(EquipmentUtil.getSlotModifierId(ItemAttributeModifiers.id(entry.modifier()), itemStack, i, this.getSlotType().name()), entry.modifier().getAmount(), entry.modifier().getOperation()), (stack, wearer) -> true));
                    }
                }
            }
        }
        return conditionalAttributes;
    }

    public record ConditionalAttribute(Holder<Attribute> attribute, ConditionalModifier modifier, BiPredicate<ItemStack, LivingEntity> condition) {
        public ConditionalAttribute(Attribute attribute, ConditionalModifier modifier, BiPredicate<ItemStack, LivingEntity> condition) {
            this(Holder.direct(attribute), modifier, condition);
        }

        public ConditionalAttribute(RegistryObject<Attribute> attribute, ConditionalModifier modifier, BiPredicate<ItemStack, LivingEntity> condition) {
            this(RegistryObjectUtil.attribute(attribute), modifier, condition);
        }
    }

    public record ConditionalModifier(ResourceLocation location, Function<ItemStack, Double> amount, AttributeModifier.Operation operation) {
        public ConditionalModifier(ResourceLocation location, double amount, AttributeModifier.Operation operation) {
            this(location, (stack) -> amount, operation);
        }

        public AttributeModifier getModifier(ItemStack stack) {
            return ItemAttributeModifiers.modifier(this.location(), this.amount().apply(stack), this.operation());
        }
    }
}
