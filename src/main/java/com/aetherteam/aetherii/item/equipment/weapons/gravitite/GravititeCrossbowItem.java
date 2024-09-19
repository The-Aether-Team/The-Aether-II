package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GravititeCrossbowItem extends TieredCrossbowItem {
    public GravititeCrossbowItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties());
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (weapon.has(AetherIIDataComponents.CROSSBOW_SPECIAL) && Boolean.TRUE.equals(weapon.get(AetherIIDataComponents.CROSSBOW_SPECIAL))) {
            projectile.setNoGravity(true);
        }
        return projectile;
    }
}
