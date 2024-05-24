package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum AetherIIItemTiers implements Tier { //todo
    SKYROOT(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(AetherIITags.Items.SKYROOT_REPAIRING)),
    HOLYSTONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(AetherIITags.Items.HOLYSTONE_REPAIRING)),
    ZANITE(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(AetherIITags.Items.ZANITE_REPAIRING)),
    ARKENIUM(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherIITags.Items.ARKENIUM_REPAIRING)),
    GRAVITITE(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(AetherIITags.Items.GRAVITITE_REPAIRING));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    AetherIIItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
