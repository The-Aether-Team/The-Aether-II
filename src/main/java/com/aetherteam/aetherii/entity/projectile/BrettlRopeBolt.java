package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.RopeBlock;
import com.aetherteam.aetherii.block.utility.RopeStakeBlock;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BrettlRopeBolt extends AbstractArrow {
    private static final ItemStack BRETTL_ROPE_BOLT = new ItemStack(AetherIIItems.BRETTL_ROPE_BOLT.get());

    public BrettlRopeBolt(EntityType<? extends BrettlRopeBolt> entityType, Level level) {
        super(entityType, level);
    }

    public BrettlRopeBolt(Level level, double x, double y, double z, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.BRETTL_ROPE_BOLT.get(), x, y, z, level, pickupStack, weaponStack);
    }

    public BrettlRopeBolt(Level level, LivingEntity owner, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.BRETTL_ROPE_BOLT.get(), owner, level, pickupStack, weaponStack);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) { }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        Direction direction = result.getDirection();
        BlockPos placementPos = pos.relative(direction);
        FluidState replacedFluidState = this.level().getFluidState(placementPos);
        BlockState relativeState = this.level().getBlockState(pos);
        BlockState blockState = AetherIIBlocks.BRETTL_ROPE_STAKE.get().defaultBlockState().setValue(RopeStakeBlock.CONNECTION, direction.getOpposite()).setValue(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED).setValue(RopeStakeBlock.WATERLOGGED, replacedFluidState.is(Fluids.WATER));
        this.discard();
        if (direction != Direction.UP && relativeState.isFaceSturdy(this.level(), pos, direction, SupportType.CENTER)) {
            this.level().setBlock(placementPos, blockState, 1 | 2);
            this.level().scheduleTick(placementPos, AetherIIBlocks.BRETTL_ROPE_STAKE.get(), RopeBlock.DELAY);
        } else if (this.level() instanceof ServerLevel serverLevel) {
            this.spawnAtLocation(serverLevel, this.getDefaultPickupItem());
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return BRETTL_ROPE_BOLT;
    }
}
