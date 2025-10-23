package com.aetherteam.aetherii.entity.vehicle;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CloudSkiff extends AbstractBoat {
    public CloudSkiff(Level level) {
        this(AetherIIEntityTypes.CLOUD_SKIFF.get(), level);
    }

    public CloudSkiff(EntityType<CloudSkiff> entityType, Level level) {
        super(entityType, level, AetherIIItems.CLOUD_SKIFF);
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.getInBlockState().is(AetherIIBlocks.COLD_AERCLOUD.get())) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), 0.2F, this.getDeltaMovement().z()));
            }
        }
    }

    @Override
    protected double rideHeight(EntityDimensions entityDimensions) {
        return entityDimensions.height();
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
}
