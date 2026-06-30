package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import com.aetherteam.aetherii.integration.AttributeTooltipContext;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class TieredShieldItem extends ShieldItem {
    public static final ResourceLocation BASE_SHIELD_BLOCKING_STRENGTH_ID = new ResourceLocation(AetherII.MODID, "base_shield_blocking_strength");

    private final double strength;

    public TieredShieldItem(Tier tier, double strength, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.strength = strength;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.forMap(Map.of(
                Holder.direct(AetherIIAttributes.BLOCKING_STRENGTH.get()), ItemAttributeModifiers.modifier(BASE_SHIELD_BLOCKING_STRENGTH_ID, this.getStrength(), AttributeModifier.Operation.ADDITION)
        ));
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents::add, AttributeTooltipContext.of(null, level, TooltipDisplay.DEFAULT, tooltipFlag), modifiers, "blocking");
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

    public double getStrength() {
        return this.strength;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public static void updatePlayerAttributes(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        {
            AttributeInstance blockingStrength = livingEntity.getAttribute(AetherIIAttributes.BLOCKING_STRENGTH.get());

            ItemStack useItem = livingEntity.getUseItem();
            if (livingEntity.isBlocking() && useItem.getItem() instanceof TieredShieldItem tieredShieldItem) {
                if (blockingStrength != null && !ItemAttributeModifiers.hasModifier(blockingStrength, BASE_SHIELD_BLOCKING_STRENGTH_ID)) {
                    blockingStrength.addTransientModifier(ItemAttributeModifiers.modifier(BASE_SHIELD_BLOCKING_STRENGTH_ID, tieredShieldItem.getStrength(), AttributeModifier.Operation.ADDITION));
                }
            } else {
                if (blockingStrength != null && ItemAttributeModifiers.hasModifier(blockingStrength, BASE_SHIELD_BLOCKING_STRENGTH_ID)) {
                    ItemAttributeModifiers.removeModifier(blockingStrength, BASE_SHIELD_BLOCKING_STRENGTH_ID);
                }
            }
        }
    }
}
