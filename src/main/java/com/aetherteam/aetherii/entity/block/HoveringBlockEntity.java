package com.aetherteam.aetherii.entity.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class HoveringBlockEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    private BlockState blockState = Blocks.SAND.defaultBlockState();
    @Nullable
    public CompoundTag blockData;
    protected boolean held = true;
    protected boolean launched;
    protected int launchDuration;
    protected Vec3 targetSettlePosition;
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;

    public HoveringBlockEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    public HoveringBlockEntity(Level level, double x, double y, double z, BlockState state) {
        this(AetherIIEntityTypes.HOVERING_BLOCK.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_ID, -1);
        builder.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public void tick() {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (this.held) {
            if (holdingPlayer != null) {
                Vec3 playerToBlock = this.position().subtract(holdingPlayer.position().add(0, 1.15, 0));
                Vec3 target = holdingPlayer.getViewVector(1.0F).scale(2);
                Vec3 movement = target.subtract(playerToBlock);
                this.setDeltaMovement(movement.scale(0.5));
                if (holdingPlayer instanceof Player player) {
                    if (playerToBlock.length() > player.blockInteractionRange() + 1) {
                        this.markShouldSettle();
                    }
                }
            } else {
                this.markShouldSettle();
            }
        } else {
            if (holdingPlayer != null) {
                AetherIIPlayerAttachment attachment = holdingPlayer.getData(AetherIIDataAttachments.PLAYER);
                attachment.setGravititeHoldingFloatingBlock(false);
            }
            if (this.verticalCollision || this.horizontalCollision || this.onGround()) {
                this.markShouldSettle();
            }
        }
        if (this.targetSettlePosition != null) {
            this.settleBlock();
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }

        if (!this.level().isClientSide()) {
            if (this.lerpSteps > 0) {
                this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.getYRot(), this.getXRot());
                --this.lerpSteps;
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        if (this.launched) {
            if (this.launchDuration++ >= 100) {
                this.dropBlock(this.blockState);
            }
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.markShouldSettle();
            this.settleBlock();
        }
        return super.interact(pPlayer, pHand);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.launched = true;
            this.push(holdingPlayer.getViewVector(1.0F).x() * 2.5, holdingPlayer.getViewVector(1.0F).y() * 2.5, holdingPlayer.getViewVector(1.0F).z() * 2.5);
        }
        return super.hurt(pSource, pAmount);
    }

    private void markShouldSettle() {
        if (this.targetSettlePosition == null) {
            Optional<BlockPos> newPos = BlockPos.findClosestMatch(this.blockPosition(), 1, 1, (pos) -> this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty());
            Vec3 targetPos = this.blockPosition().getCenter().subtract(0, 0.5, 0);
            if (newPos.isPresent()) {
                targetPos = newPos.get().getCenter().subtract(0, 0.5, 0);
            }
            this.targetSettlePosition = targetPos;
        }
    }

    private void settleBlock() {
        Vec3 currentPos = this.position();
        Vec3 motion = this.targetSettlePosition.subtract(currentPos);
        this.setDeltaMovement(motion);
        if (this.position().distanceTo(this.targetSettlePosition) <= 0.001) {
            if (!this.level().isClientSide()) {
                BlockState levelState = this.level().getBlockState(this.blockPosition());
                if (!levelState.isAir()) {
                    this.level().destroyBlock(this.blockPosition(), true);
                }
                this.level().setBlock(this.blockPosition(), this.blockState, 2);
                levelState = this.level().getBlockState(this.blockPosition());
                if (levelState.is(this.getBlockState().getBlock())) {
                    if (this.blockData != null && this.getBlockState().hasBlockEntity()) {
                        BlockEntity blockEntity = this.level().getBlockEntity(this.blockPosition());
                        if (blockEntity != null) {
                            CompoundTag tag = blockEntity.saveWithoutMetadata(this.level().registryAccess());
                            for (String string : this.blockData.getAllKeys()) {
                                Tag blockDataTag = this.blockData.get(string);
                                if (blockDataTag != null) {
                                    tag.put(string, blockDataTag.copy());
                                }
                            }

                            try {
                                blockEntity.loadWithComponents(tag, this.level().registryAccess());
                            } catch (Exception exception) {
                                AetherII.LOGGER.error("Failed to load block entity from hovering block", exception);
                            }
                            blockEntity.setChanged();
                        }
                    }
                }
                this.discard();
            }
        }
    }

    private void dropBlock(BlockState state) {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (ItemStack stack : Block.getDrops(state, serverLevel, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    public Entity getHoldingPlayer() {
        int id = this.getEntityData().get(DATA_OWNER_ID);
        return id != -1 ? this.level().getEntity(id) : null;
    }

    public void setHoldingPlayer(@Nullable Entity entity) {
        int id = entity != null ? entity.getId() : -1;
        this.getEntityData().set(DATA_OWNER_ID, id);
    }

    public void setStartPos(BlockPos pStartPos) {
        this.entityData.set(DATA_START_POS, pStartPos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pSteps) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.setRot(pYRot, pXRot);
        this.lerpSteps = pSteps;
    }

    @Override
    public double lerpTargetX() {
        return this.lerpSteps > 0 ? this.lerpX : this.getX();
    }

    @Override
    public double lerpTargetY() {
        return this.lerpSteps > 0 ? this.lerpY : this.getY();
    }

    @Override
    public double lerpTargetZ() {
        return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        if (this.blockData != null) {
            tag.put("TileEntityData", this.blockData);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("BlockState")) {
            this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
        }
        if (tag.contains("TileEntityData", 10)) {
            this.blockData = tag.getCompound("TileEntityData");
        }
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1 + (double) ((1.0F - this.getBbHeight()) / 2.0F), d2);
        this.setStartPos(this.blockPosition());
    }
}
