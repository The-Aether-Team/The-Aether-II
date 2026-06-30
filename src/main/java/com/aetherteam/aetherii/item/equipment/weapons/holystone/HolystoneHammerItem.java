package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HolystoneHammerItem extends TieredHammerItem implements HolystoneWeapon {
    public HolystoneHammerItem(Properties properties) {
        super(AetherIIToolMaterials.HOLYSTONE, 3, -2.4F, AetherIIStats.HOLYSTONE_HAMMER, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
