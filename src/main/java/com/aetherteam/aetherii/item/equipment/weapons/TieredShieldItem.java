package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.Optional;

public class TieredShieldItem extends ShieldItem {
    public static final ResourceLocation BASE_SHIELD_BLOCKING_STRENGTH_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_shield_blocking_strength");

    public TieredShieldItem(ToolMaterial tier, Properties properties) {
        super(properties.durability(tier.durability()).repairable(tier.repairItems()).enchantable(tier.enchantmentValue()).equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(
                        DataComponents.BLOCKS_ATTACKS,
                        new BlocksAttacks(
                                0.25F,
                                1.0F,
                                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                Optional.of(SoundEvents.SHIELD_BLOCK),
                                Optional.of(SoundEvents.SHIELD_BREAK)
                        )
                )
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK));
    }

    public static ItemAttributeModifiers createAttributes(float strength) {
        return ItemAttributeModifiers.builder()
                .add(AetherIIAttributes.BLOCKING_STRENGTH, new AttributeModifier(BASE_SHIELD_BLOCKING_STRENGTH_ID, strength, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                .build();
    }
}
