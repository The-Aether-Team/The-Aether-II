package com.aetherteam.aetherii.item.combat.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneHammerItem extends HammerItem implements HolystoneWeapon {
    public HolystoneHammerItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(HammerItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 3, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
