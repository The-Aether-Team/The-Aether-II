package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GravititeSpearItem extends TieredSpearItem {
    public GravititeSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.GRAVITITE, 3, -3.05F, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 10.0F, AetherIIStats.GRAVITITE_SPEAR));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.forceAddEffect(new MobEffectInstance(AetherIIEffects.GRAVITATIONAL_PULL, 50, 0, false, false, false), attacker);
        super.hurtEnemy(stack, target, attacker);
    }
}
