package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ScatterglassBolt extends AbstractArrow {
    private static final ItemStack SCATTERGLASS_BOLT = new ItemStack(AetherIIItems.SCATTERGLASS_BOLT.get());

    public ScatterglassBolt(EntityType<? extends ScatterglassBolt> entityType, Level level) {
        super(entityType, level, SCATTERGLASS_BOLT);
    }

    public ScatterglassBolt(Level level, double x, double y, double z, ItemStack pickupStack) {
        super(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), x, y, z, level, pickupStack);
    }

    public ScatterglassBolt(Level level, LivingEntity owner, ItemStack pickupStack) {
        super(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), owner, level, pickupStack);
    }
}
