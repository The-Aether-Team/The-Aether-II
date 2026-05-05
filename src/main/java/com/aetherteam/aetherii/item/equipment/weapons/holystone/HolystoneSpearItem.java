package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneSpearItem extends TieredSpearItem implements HolystoneWeapon {
    public HolystoneSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.HOLYSTONE, 3, -2.67F, 0.75F, 0.82F, 0.7F, 4.5F, 13.0F, 9.0F, 13.75F, AetherIIStats.HOLYSTONE_SPEAR));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        super.hurtEnemy(stack, target, attacker);
    }
}
