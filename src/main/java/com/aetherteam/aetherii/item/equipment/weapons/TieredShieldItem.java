package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class TieredShieldItem extends ShieldItem {
    public static final Identifier BASE_SHIELD_BLOCKING_STRENGTH_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_shield_blocking_strength");

    private final double strength;

    public TieredShieldItem(ToolMaterial tier, double strength, Properties properties) {
        super(properties.durability(tier.durability()).repairable(tier.repairItems()).enchantable(tier.enchantmentValue()).equippableUnswappable(EquipmentSlot.OFFHAND)
                .delayedComponent(
                        DataComponents.BLOCKS_ATTACKS,
                        context -> new BlocksAttacks(
                                0.25F,
                                1.0F,
                                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                                Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                                Optional.of(SoundEvents.SHIELD_BLOCK),
                                Optional.of(SoundEvents.SHIELD_BREAK)
                        )
                )
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK));
        this.strength = strength;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.forMap(Map.of(
                AetherIIAttributes.BLOCKING_STRENGTH, new AttributeModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID, this.getStrength(), AttributeModifier.Operation.ADD_VALUE)
        ));
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), modifiers, "blocking");
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
    }

    public double getStrength() {
        return this.strength;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    public static void updatePlayerAttributes(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            AttributeInstance blockingStrength = livingEntity.getAttribute(AetherIIAttributes.BLOCKING_STRENGTH);

            ItemStack useItem = livingEntity.getUseItem();
            if (livingEntity.isBlocking() && useItem.getItem() instanceof TieredShieldItem tieredShieldItem) {
                if (blockingStrength != null && !blockingStrength.hasModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID)) {
                    blockingStrength.addTransientModifier(new AttributeModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID, tieredShieldItem.getStrength(), AttributeModifier.Operation.ADD_VALUE));
                }
            } else {
                if (blockingStrength != null && blockingStrength.hasModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID)) {
                    blockingStrength.removeModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID);
                }
            }
        }
    }
}
