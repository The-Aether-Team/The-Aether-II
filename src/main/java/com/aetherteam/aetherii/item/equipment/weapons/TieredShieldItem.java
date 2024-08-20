package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class TieredShieldItem extends ShieldItem {
    public static final ResourceLocation BASE_SHIELD_STAMINA_REDUCTION_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_shield_stamina_reduction");

    private final Tier tier;

    public TieredShieldItem(Tier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.tier = tier;
    }

    public static ItemAttributeModifiers createAttributes(int staminaReductionRate) {
        return createAttributes((float) staminaReductionRate);
    }

    public static ItemAttributeModifiers createAttributes(float staminaReductionRate) {
        return ItemAttributeModifiers.builder()
                .add(AetherIIAttributes.SHIELD_STAMINA_REDUCTION, new AttributeModifier(BASE_SHIELD_STAMINA_REDUCTION_ID, staminaReductionRate, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND) //todo is there a way to have custom slot groups
                .build();
    }

    public Tier getTier() {
        return this.tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return this.tier.getRepairIngredient().test(repairItem);
    }
}
