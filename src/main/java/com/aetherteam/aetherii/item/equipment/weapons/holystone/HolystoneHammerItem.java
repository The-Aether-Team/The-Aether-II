package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneHammerItem extends TieredHammerItem implements HolystoneWeapon {
    public HolystoneHammerItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(AetherIIDamageStats.merge(TieredHammerItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 3, -2.4F), AetherIIDamageStats.HOLYSTONE_HAMMER)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
