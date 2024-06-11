package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.entity.projectile.ScatterglassBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ScatterglassBoltItem extends ArrowItem {
    public ScatterglassBoltItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new ScatterglassBolt(level, shooter, stack.copyWithCount(1));
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return super.isInfinite(stack, bow, player); //todo?
    }
}
