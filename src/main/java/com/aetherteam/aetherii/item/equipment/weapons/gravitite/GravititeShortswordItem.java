package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GravititeShortswordItem extends TieredShortswordItem {
    public GravititeShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIToolMaterials.GRAVITITE, 3, -2.4F, AetherIIStats.GRAVITITE_SHORTSWORD));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.forceAddEffect(new MobEffectInstance(AetherIIMobEffects.GRAVITATIONAL_PULL, 50, 0, false, false, false), attacker);
        super.hurtEnemy(stack, target, attacker);
    }
}
