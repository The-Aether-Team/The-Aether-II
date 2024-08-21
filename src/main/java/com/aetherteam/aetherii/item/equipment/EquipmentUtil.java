package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.AetherIIAccessorySlots;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EquipmentUtil {
    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    public static int getArmorCount(LivingEntity entity, Holder<ArmorMaterial> material) {
        Map<Holder<ArmorMaterial>, Integer> armorTypeCount = new HashMap<>();
        for (ItemStack itemStack : entity.getArmorSlots()) {
            if (itemStack.getItem() instanceof ArmorItem armorItem) {
                Holder<ArmorMaterial> materialHolder = armorItem.getMaterial();
                if (armorTypeCount.containsKey(materialHolder)) {
                    armorTypeCount.put(materialHolder, armorTypeCount.get(materialHolder) + 1);
                } else {
                    armorTypeCount.put(materialHolder, 1);
                }
            }
        }
        AccessoriesCapability accessories = AccessoriesCapability.get(entity);
        if (accessories != null) {
            SlotEntryReference slotEntryReference = accessories.getFirstEquipped((itemStack) -> itemStack.getItem() instanceof GlovesItem);
            if (slotEntryReference != null && slotEntryReference.stack().getItem() instanceof GlovesItem glovesItem) {
                Holder<ArmorMaterial> materialHolder = glovesItem.getMaterial();
                if (armorTypeCount.containsKey(materialHolder)) {
                    armorTypeCount.put(materialHolder, armorTypeCount.get(materialHolder) + 1);
                } else {
                    armorTypeCount.put(materialHolder, 1);
                }
            }
        }
        return armorTypeCount.computeIfAbsent(material, i -> 0);
    }

    public static boolean hasArmorAbility(LivingEntity entity, Holder<ArmorMaterial> material) {
        return getArmorCount(entity, material) >= 3;
    }

    public static void addAbilityTooltips(Player player, ItemStack stack, List<Component> components) {
        for (int i = 1; i <= 5; i++) {
            String string = stack.getDescriptionId() + "." + AetherII.MODID + ".ability.tooltip." + i;
            if (I18n.exists(string)) {
                if (player != null && (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof GlovesItem) && Component.translatable(string).getString().contains("%s")) {
                    Holder<ArmorMaterial> material = null;
                    if (stack.getItem() instanceof ArmorItem armorItem) {
                        material = armorItem.getMaterial();
                    } else if (stack.getItem() instanceof GlovesItem glovesItem) {
                        material = glovesItem.getMaterial();
                    }
                    if (material != null) {
                        int currentEquipmentCount = EquipmentUtil.getArmorCount(player, material);
                        Component component;
                        if (currentEquipmentCount >= 3) {
                            component = Component.literal("3/3").withStyle(ChatFormatting.WHITE);
                        } else {
                            component = Component.literal(currentEquipmentCount + "/3").withStyle(ChatFormatting.GRAY);
                        }
                        components.add(i, Component.translatable(string, component));
                    }
                } else {
                    components.add(i, Component.translatable(string));
                }
            }
        }
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

    public static void addGloveTooltips(Player player, List<Component> components, ItemStack stack) {
        if (stack.getItem() instanceof GlovesItem) {
            int attributeTooltip = components.size() - 1;

            Component attributeText = Component.translatable(AetherIIAttributes.SHIELD_STAMINA_RESTORATION.value().getDescriptionId());

            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(attributeText.getString())) {
                    attributeTooltip = i;
                }
            }

            int value = 0;
            for (AttributeModifier entry : AccessoriesAPI.getAttributeModifiers(stack, player, AetherIIAccessorySlots.getHandwearSlotType().slotName(), 0).getAttributeModifiers(false).values()) {
                if (entry.id().getPath().contains(GlovesItem.BASE_GLOVES_STAMINA_RESTORATION_ID.getNamespace())) {
                    value = (int) ((entry.amount() / DamageSystemAttachment.MAX_SHIELD_STAMINA) * 100);
                }
            }
            components.remove(attributeTooltip);
            components.add(attributeTooltip, Component.empty().append(Component.translatable("attribute.modifier.equals.0", "+" + value + "%", Component.translatable(AetherIIAttributes.SHIELD_STAMINA_RESTORATION.value().getDescriptionId())).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addReinforcingTooltip(ItemStack stack, List<Component> components) { //todo component tooltip?
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
            components.add(position, Component.literal("Reinforcement ").append(Component.translatable("enchantment.level." + tier.getTier())).withColor(14408667));
        }
    }
}
