package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.construction.AmbrosiumWallTorchBlock;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class TorchBolt extends AbstractArrow {
    private static final ItemStack AMBROSIUM_TORCH_BOLT = new ItemStack(AetherIIItems.AMBROSIUM_TORCH_BOLT.get());

    public TorchBolt(EntityType<? extends TorchBolt> entityType, Level level) {
        super(entityType, level);
    }

    public TorchBolt(Level level, double x, double y, double z, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.AMBROSIUM_TORCH_BOLT.get(), x, y, z, level, pickupStack, weaponStack);
    }

    public TorchBolt(Level level, LivingEntity owner, ItemStack pickupStack, ItemStack weaponStack) {
        super(AetherIIEntityTypes.AMBROSIUM_TORCH_BOLT.get(), owner, level, pickupStack, weaponStack);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStackTemplate(AetherIIItems.SWET_GEL.get()));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.random.nextInt(2) == 0) {
                this.level().addParticle(AetherIIParticleTypes.AMBROSIUM.get(), this.position().x(), this.position().y(), this.position().z(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        Direction direction = result.getDirection();
        BlockPos placementPos = pos.relative(direction);
        BlockState state = null;
        if (direction == Direction.UP) {
            state = AetherIIBlocks.AMBROSIUM_TORCH.get().defaultBlockState();
        } else if (direction.getAxis().isHorizontal()) {
            state = AetherIIBlocks.AMBROSIUM_WALL_TORCH.get().defaultBlockState().setValue(AmbrosiumWallTorchBlock.FACING, direction);
        }
        if (state != null && state.canSurvive(this.level(), placementPos) && this.level().getBlockState(placementPos).isAir()) {
            if (!this.level().isClientSide()) {
                this.level().setBlock(placementPos, state, 1 | 2);
                this.level().broadcastEntityEvent(this, (byte) 3);
                this.discard();
            }
        } else {
            if (!this.level().isClientSide()) {
                this.level().broadcastEntityEvent(this, (byte) 3);
            }
            if (direction == Direction.UP) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    this.spawnAtLocation(serverLevel, this.getDefaultPickupItem());
                    this.discard();
                }
            } else {
                this.setInGround(false);
                this.deflect(ProjectileDeflection.REVERSE, null, this.owner, false);
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.02, 0.2, 0.02));
            }
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return AMBROSIUM_TORCH_BOLT;
    }
}
