package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

public class AccessoryItem extends Item {
    private final AccessoryContainer.SlotType slotType;
    private final Set<ConditionalAttribute> attributes;

    public AccessoryItem(Properties properties, AccessoryContainer.SlotType slotType) {
        super(properties.stacksTo(1));
        this.slotType = slotType;
        this.attributes = this.gatherAttributes(new HashSet<>());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return AccessoryUtil.equip(player, player.getItemInHand(hand), this.getSlotType());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        Multimap<Holder<Attribute>, AttributeModifier> attributesMap = ArrayListMultimap.create();
        for (ConditionalAttribute attribute : this.getBaseAttributes()) {
            attributesMap.put(attribute.attribute(), attribute.modifier().getModifier(stack));
        }
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), attributesMap, this.getSlotType().name().toLowerCase(Locale.ROOT));
    }

    public void tick(ItemStack stack, LivingEntity wearer, int slot) {
        for (ConditionalAttribute entry : this.getAttributes(stack)) {
            AttributeInstance attribute = wearer.getAttribute(entry.attribute());
            AttributeModifier modifier = entry.modifier().getModifier(stack);

            if (attribute != null) {
                if (!attribute.hasModifier(modifier.id()) && entry.condition().test(stack, wearer)) {
                    attribute.addTransientModifier(modifier);
                } else if (attribute.hasModifier(modifier.id()) && (!entry.condition().test(stack, wearer))) {
                    attribute.removeModifier(modifier.id());
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
            wearer.gameEvent(equip ? GameEvent.EQUIP : GameEvent.UNEQUIP);
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
        return AetherIISoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC;
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
                        conditionalAttributes.add(new ConditionalAttribute(entry.attribute(), new ConditionalModifier(EquipmentUtil.getSlotModifierId(entry.modifier().id(), itemStack, i, this.getSlotType().name()), entry.modifier().amount(), entry.modifier().operation()), (stack, wearer) -> true));
                    }
                }
            }
        }
        return conditionalAttributes;
    }

    public record ConditionalAttribute(Holder<Attribute> attribute, ConditionalModifier modifier, BiPredicate<ItemStack, LivingEntity> condition) { }

    public record ConditionalModifier(Identifier location, Function<ItemStack, Double> amount, AttributeModifier.Operation operation) {
        public ConditionalModifier(Identifier location, double amount, AttributeModifier.Operation operation) {
            this(location, (stack) -> amount, operation);
        }

        public AttributeModifier getModifier(ItemStack stack) {
            return new AttributeModifier(this.location(), this.amount().apply(stack), this.operation());
        }
    }
}
