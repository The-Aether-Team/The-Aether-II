package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneSpearItem extends TieredSpearItem implements HolystoneWeapon {
    public HolystoneSpearItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(TieredSpearItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 3, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
