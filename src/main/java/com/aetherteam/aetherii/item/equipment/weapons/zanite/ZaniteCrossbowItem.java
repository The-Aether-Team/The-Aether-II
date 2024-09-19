package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ZaniteCrossbowItem extends TieredCrossbowItem implements ZaniteTool {
    public ZaniteCrossbowItem() {
        super(AetherIIItemTiers.ZANITE, new Properties());
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (weapon.has(AetherIIDataComponents.CROSSBOW_SPECIAL) && Boolean.TRUE.equals(weapon.get(AetherIIDataComponents.CROSSBOW_SPECIAL))) {
            if (projectile instanceof AbstractArrow arrow) {
                arrow.setBaseDamage(this.calculateZaniteBuff(weapon, arrow.getBaseDamage()));
            }
        }
        return projectile;
    }
}
