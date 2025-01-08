package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIEffectResistances;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.item.equipment.armor.abilities.ArkeniumArmor;
import com.aetherteam.aetherii.item.equipment.armor.abilities.BurrukaiPeltArmor;
import com.aetherteam.aetherii.item.equipment.armor.abilities.ZaniteArmor;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.impl.AccessoryAttributeLogic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.fml.ModList;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

public final class EquipmentUtil {
    private static final Map<TagKey<Item>, List<Pair<Holder<Attribute>, ResourceLocation>>> ARMOR_ABILITY_ATTRIBUTES = new ImmutableMap.Builder<TagKey<Item>, List<Pair<Holder<Attribute>, ResourceLocation>>>()
            .put(AetherIITags.Items.BURRUKAI_PELT_ARMOR, List.of(Pair.of(AetherIIAttributes.STUN_EFFECT_RESISTANCE, BurrukaiPeltArmor.BURRUKAI_PELT_STUN_RESISTANCE), Pair.of(Attributes.KNOCKBACK_RESISTANCE, BurrukaiPeltArmor.BURRUKAI_PELT_KNOCKBACK_RESISTANCE)))
            .put(AetherIITags.Items.ZANITE_ARMOR, List.of(Pair.of(Attributes.MOVEMENT_EFFICIENCY, ZaniteArmor.ZANITE_MOVEMENT_SPEED), Pair.of(Attributes.MINING_EFFICIENCY, ZaniteArmor.ZANITE_MINING_SPEED), Pair.of(Attributes.ATTACK_SPEED, ZaniteArmor.ZANITE_ATTACK_SPEED)))
            .put(AetherIITags.Items.ARKENIUM_ARMOR, List.of(Pair.of(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, ArkeniumArmor.ARKENIUM_BLAST_RESISTANCE)))
            .build();

    private static final Map<TagKey<Item>, List<Holder<Attribute>>> ITEM_EFFECT_RESISTANCES = new ImmutableMap.Builder<TagKey<Item>, List<Holder<Attribute>>>()
            .put(AetherIITags.Items.BURRUKAI_PELT_ARMOR, List.of(AetherIIAttributes.STUN_EFFECT_RESISTANCE))
            .build();

    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    public static int getArmorCount(LivingEntity entity, TagKey<Item> checkSet) {
        int armorTypeCount = 0;
        List<ItemStack> equipment = getEquipment(entity);
        for (ItemStack itemStack : equipment) {
            TagKey<Item> armorSet = itemStack.get(AetherIIDataComponents.ARMOR_SET);
            if (armorSet == checkSet) {
                armorTypeCount++;
            }
        }
        return armorTypeCount;
    }

    public static List<ItemStack> getEquipment(LivingEntity entity) {
        AccessoriesCapability accessories = AccessoriesCapability.get(entity);
        List<ItemStack> equipment = new ArrayList<>();
        entity.getArmorSlots().forEach(equipment::add);
        if (accessories != null) {
            SlotEntryReference slotEntryReference = accessories.getFirstEquipped((itemStack) -> itemStack.getItem() instanceof GlovesItem);
            if (slotEntryReference != null) {
                equipment.add(slotEntryReference.stack());
            }
        }
        return equipment;
    }

    public static boolean hasArmorAbility(LivingEntity entity, TagKey<Item> armorSet) {
        return getArmorCount(entity, armorSet) >= 3;
    }

    public static void addShieldTooltips(List<Component> components, ItemStack stack) { //todo i need to make an easy abstracted/scaleable system for replacing specific tooltip lines.
        if (stack.getItem() instanceof TieredShieldItem) {
            int useTooltip = components.size() - 1;
            int attributeTooltip = components.size() - 1;

            Component useText = Component.translatable("item.modifiers." + EquipmentSlotGroup.HAND.getSerializedName());
            Component attributeText = Component.translatable(AetherIIAttributes.SHIELD_STAMINA_REDUCTION.value().getDescriptionId());

            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(useText.getString())) {
                    useTooltip = i;
                }
                if (component.getString().contains(attributeText.getString())) {
                    attributeTooltip = i;
                }
            }
            int value = 0;
            for (ItemAttributeModifiers.Entry entry : stack.getAttributeModifiers().modifiers()) {
                if (entry.modifier().is(TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID)) {
                    value = (int) ((entry.modifier().amount() / DamageSystemAttachment.MAX_SHIELD_STAMINA) * 100);
                }
            }
            components.remove(useTooltip);
            components.add(useTooltip, Component.translatable("aether_ii.tooltip.item.modifiers.blocking").withStyle(ChatFormatting.GRAY));
            components.remove(attributeTooltip);
            components.add(attributeTooltip, CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", value + "%", Component.translatable(AetherIIAttributes.SHIELD_STAMINA_REDUCTION.value().getDescriptionId())).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addArmorTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null && (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof GlovesItem)) {
            TagKey<Item> armorSet = stack.get(AetherIIDataComponents.ARMOR_SET);
            if (armorSet != null) {
                if (ARMOR_ABILITY_ATTRIBUTES.containsKey(armorSet) && ARMOR_ABILITY_ATTRIBUTES.get(armorSet) != null) {
                    if (EquipmentUtil.hasArmorAbility(player, armorSet)) {
                        int position = 1;
                        String text = "attribute.";
                        for (int i = components.size() - 1; i >= 0; i--) {
                            Component component = components.get(i);
                            if (component.toString().contains(text)) {
                                position = i + 1;
                                break;
                            }
                        }
                        for (Pair<Holder<Attribute>, ResourceLocation> attribute : ARMOR_ABILITY_ATTRIBUTES.get(armorSet)) {
                            if (player.getAttributes().hasAttribute(attribute.getFirst()) && player.getAttribute(attribute.getFirst()) != null) {
                                AttributeModifier modifier = player.getAttribute(attribute.getFirst()).getModifier(attribute.getSecond());
                                if (modifier != null) {
                                    double d0 = modifier.amount();
                                    double d1;
                                    if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE || modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                                        d1 = d0 * 100.0;
                                    } else if (attribute.getFirst().is(Attributes.KNOCKBACK_RESISTANCE)) {
                                        d1 = d0 * 10.0;
                                    } else {
                                        d1 = d0;
                                    }
                                    if (d0 > 0.0) {
                                        components.add(position, Component.translatable(
                                                                "attribute.modifier.plus." + modifier.operation().id(),
                                                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                                                                Component.translatable(attribute.getFirst().value().getDescriptionId())
                                                ).withStyle(attribute.getFirst().value().getStyle(true))
                                        );
                                    } else if (d0 < 0.0) {
                                        components.add(position, Component.translatable(
                                                                "attribute.modifier.take." + modifier.operation().id(),
                                                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-d1),
                                                                Component.translatable(attribute.getFirst().value().getDescriptionId())
                                                ).withStyle(attribute.getFirst().value().getStyle(false))
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void addGloveTooltips(Player player, List<Component> components, ItemStack stack) {
        if (stack.getItem() instanceof GlovesItem) {
            int attributeTooltip = components.size() - 1;

            Component attributeText = Component.translatable(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION.value().getDescriptionId());

            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(attributeText.getString())) {
                    attributeTooltip = i;
                }
            }

            int value = 0;
            for (AttributeModifier entry : AccessoryAttributeLogic.getAttributeModifiers(stack, player, AetherIIAccessorySlots.getHandwearSlotType().slotName(), 0).getAttributeModifiers(false).values()) {
                if (entry.id().getPath().contains(GlovesItem.BASE_GLOVES_COOLDOWN_RESTORATION_ID.getNamespace())) {
                    value = (int) ((entry.amount() / 300.0F) * 100);
                }
            }
            components.remove(attributeTooltip);
            components.add(attributeTooltip, Component.empty().append(Component.translatable("attribute.modifier.equals.0", "+" + value + "%", Component.translatable(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION.value().getDescriptionId())).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addReinforcingTooltip(ItemStack stack, List<Component> components) {
        ReinforcementTier tier = stack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            int position = 1;
            String text = "ability.tooltip";
            for (int i = 1; i < components.size(); i++) {
                Component component = components.get(i);
                if (component.toString().contains(text)) {
                    position = i + 1;
                    break;
                }
            }
            components.add(position, Component.literal("Reinforcement ").append(Component.translatable("enchantment.level." + tier.getTier())).withColor(14408667)); //todo component tooltip?
        }
    }

    public static void addEffectResistanceTooltips(Player player, ItemStack stack, List<Component> components) { //todo clean somehow
        if (player != null) {
            for (Map.Entry<Holder<Attribute>, Holder<MobEffect>> entry : AetherIIEffectResistances.RESISTANCES.inverse().entrySet()) {
                if (player.getAttributes().hasAttribute(entry.getKey()) && player.getAttribute(entry.getKey()) != null) {
                    if (ITEM_EFFECT_RESISTANCES.containsKey(stack.getItemHolder()) && ITEM_EFFECT_RESISTANCES.get(stack.getItemHolder()).stream().anyMatch((holder) -> holder.is(entry.getKey()))) {
                        boolean canAdd = false;
                        int useTooltip = components.size() - 1;
                        double modifierValue = 0.0;
                        String modifierKey = "";
                        String attributeTypeKey = "";

                        for (int i = components.size() - 1; i >= 0; i--) {
                            Component component = components.get(i);
                            if (component.getContents() instanceof TranslatableContents translatableContents) {
                                if (translatableContents.getKey().contains("attribute.modifier")) {
                                    if (Arrays.stream(translatableContents.getArgs()).anyMatch((object) -> object instanceof Component subComponent
                                            && subComponent.getContents() instanceof TranslatableContents subContents
                                            && subContents.getKey().contains("attributes") && subContents.getKey().contains("effect_resistance") && component.getString().contains("%s"))) {
                                        for (Object arg : translatableContents.getArgs()) {
                                            if (arg instanceof String value) {
                                                if (NumberUtils.isCreatable(value)) {
                                                    modifierValue = Double.parseDouble(value);
                                                }
                                            } else if (arg instanceof Component subComponent) {
                                                if (subComponent.getContents() instanceof TranslatableContents subContents) {
                                                    canAdd = true;
                                                    useTooltip = i;
                                                    modifierKey = translatableContents.getKey();
                                                    attributeTypeKey = subContents.getKey();
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }

                        if (canAdd) {
                            components.remove(useTooltip);
                            components.add(useTooltip, Component.translatable(modifierKey, (int) (modifierValue * 100) + "%", Component.translatable(attributeTypeKey, Component.translatable(entry.getValue().value().getDescriptionId()).withColor(entry.getValue().value().getColor()))).withStyle(ChatFormatting.BLUE));
                        }
                    }
                }
            }
        }
    }
}
