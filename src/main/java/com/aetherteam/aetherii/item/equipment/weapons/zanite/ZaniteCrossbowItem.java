package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.ZaniteBuff;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class ZaniteCrossbowItem extends TieredCrossbowItem implements ZaniteBuff {
    public ZaniteCrossbowItem(Properties properties) {
        super(AetherIIToolMaterials.ZANITE, properties.attributes(new ItemAttributeModifiers(AetherIIStats.ZANITE_CROSSBOW)));
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) { //todo make this noticeable in tooltip?
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (shooter.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            if (projectile instanceof AbstractArrow arrow) {
                arrow.setBaseDamage(this.calculateZaniteBuff(weapon, ((AbstractArrowAccessor) arrow).aether$getBaseDamage()));
            }
        }
        return projectile;
    }
}
