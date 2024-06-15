package com.aetherteam.aetherii.item.combat;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class AetherIIShieldItem extends ShieldItem {
    private final Tier tier;
    private final int breakIncrement;

    public AetherIIShieldItem(Tier tier, int breakIncrement, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
        this.tier = tier;
        this.breakIncrement = breakIncrement;
    }

    public Tier getTier() {
        return this.tier;
    }

    public int getBreakIncrement() {
        return this.breakIncrement;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return this.tier.getRepairIngredient().test(repairItem);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }
}
