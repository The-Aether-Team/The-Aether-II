package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.entity.projectile.ScatterglassBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ScatterglassBoltItem extends ArrowItem {
    public ScatterglassBoltItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weaponStack) {
        return new ScatterglassBolt(level, shooter, stack.copyWithCount(1), weaponStack);
    }
}
