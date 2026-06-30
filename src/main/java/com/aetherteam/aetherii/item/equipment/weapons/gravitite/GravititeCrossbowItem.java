package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GravititeCrossbowItem extends TieredCrossbowItem {
    public GravititeCrossbowItem(Properties properties) {
        super(AetherIIToolMaterials.GRAVITITE, properties);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (AetherIIDataAttachments.get(shooter, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            projectile.setNoGravity(true);
        }
        return projectile;
    }
}
