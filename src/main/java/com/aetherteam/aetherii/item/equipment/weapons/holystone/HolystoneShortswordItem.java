package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneShortswordItem extends TieredShortswordItem implements HolystoneWeapon {
    public HolystoneShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIToolMaterials.HOLYSTONE, 3, -2.4F, AetherIIStats.HOLYSTONE_SHORTSWORD));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        super.hurtEnemy(stack, target, attacker);
    }
}
