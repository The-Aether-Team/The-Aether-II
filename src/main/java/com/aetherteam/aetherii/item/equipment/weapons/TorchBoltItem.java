package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.entity.projectile.BrettlRopeBolt;
import com.aetherteam.aetherii.entity.projectile.TorchBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TorchBoltItem extends ArrowItem {
    public TorchBoltItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weaponStack) {
        return new TorchBolt(level, shooter, stack.copyWithCount(1), weaponStack);
    }
}
