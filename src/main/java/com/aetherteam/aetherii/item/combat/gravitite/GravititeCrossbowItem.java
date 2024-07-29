package com.aetherteam.aetherii.item.combat.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.AetherIICrossbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GravititeCrossbowItem extends AetherIICrossbowItem {
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
