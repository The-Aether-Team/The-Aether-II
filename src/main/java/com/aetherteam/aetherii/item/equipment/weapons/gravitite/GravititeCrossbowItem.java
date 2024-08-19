package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;

public class GravititeCrossbowItem extends TieredCrossbowItem {
    public GravititeCrossbowItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties());
    }

//    @Override
//    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) { //todo special charge data component check.
//        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
//        projectile.setNoGravity(true);
//        return projectile;
//    }
}
