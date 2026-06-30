package com.aetherteam.aetherii.entity.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
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
import java.util.function.Predicate;

public class HoveringBlockEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> DATA_START_POS_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.BLOCK_POS);

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
    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER_ID, -1);
        this.entityData.define(DATA_START_POS_ID, BlockPos.ZERO);
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
                if (holdingPlayer instanceof Player player && playerToBlock.length() > player.getBlockReach() + 1) {
                    this.markShouldSettle();
                }
            } else {
                this.markShouldSettle();
            }
        } else {
            if (this.verticalCollision || this.horizontalCollision || this.onGround()) {
                this.markShouldSettle();
            }
            this.clearHoldingState(holdingPlayer);
        }
        if (this.targetSettlePosition != null) {
            this.settleBlock();
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }

        if (this.level().isClientSide() && this.lerpSteps > 0) {
            double x = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double y = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double z = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            this.setPos(x, y, z);
            --this.lerpSteps;
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        if (this.launched && this.launchDuration++ >= 100) {
            this.dropBlock(this.blockState);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.markShouldSettle();
            this.settleBlock();
        }
        return super.interact(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.launched = true;
            this.push(holdingPlayer.getViewVector(1.0F).x() * 2.5, holdingPlayer.getViewVector(1.0F).y() * 2.5, holdingPlayer.getViewVector(1.0F).z() * 2.5);
            this.clearHoldingState(holdingPlayer);
        }
        return false;
    }

    private void markShouldSettle() {
        Predicate<BlockPos> findPos = (pos) -> {
            BlockState state = this.level().getBlockState(pos);
            return !state.is(AetherIITags.Blocks.HOVERING_BLOCK_CANT_REPLACE) && state.getCollisionShape(this.level(), pos).isEmpty();
        };

        if (this.targetSettlePosition == null) {
            Optional<BlockPos> newPos = BlockPos.findClosestMatch(this.blockPosition(), 1, 1, findPos);
            this.targetSettlePosition = newPos.orElse(this.blockPosition()).getCenter().subtract(0, 0.5, 0);
        }
    }

    private void settleBlock() {
        Entity holdingPlayer = this.getHoldingPlayer();
        Vec3 currentPos = this.position();
        Vec3 motion = this.targetSettlePosition.subtract(currentPos);
        BlockPos newPos = BlockPos.containing(this.targetSettlePosition.x(), this.targetSettlePosition.y(), this.targetSettlePosition.z());
        this.setDeltaMovement(motion);
        this.clearHoldingState(holdingPlayer);
        if (this.position().distanceTo(this.targetSettlePosition) <= 0.001 && !this.level().isClientSide()) {
            BlockState levelState = this.level().getBlockState(newPos);
            if (!levelState.isAir()) {
                this.level().destroyBlock(newPos, true);
            }
            if (this.level().setBlock(newPos, this.blockState, 3)) {
                this.loadCopiedBlockEntity(newPos);
            }
            this.discard();
        }
    }

    private void loadCopiedBlockEntity(BlockPos pos) {
        if (this.blockData != null && this.getBlockState().hasBlockEntity()) {
            BlockEntity blockEntity = this.level().getBlockEntity(pos);
            if (blockEntity != null) {
                CompoundTag tag = blockEntity.saveWithoutMetadata();
                for (String key : this.blockData.getAllKeys()) {
                    Tag blockDataTag = this.blockData.get(key);
                    if (blockDataTag != null) {
                        tag.put(key, blockDataTag.copy());
                    }
                }
                try {
                    blockEntity.load(tag);
                } catch (Exception exception) {
                    AetherII.LOGGER.error("Failed to load block entity from hovering block", exception);
                }
                blockEntity.setChanged();
            }
        }
    }

    private void clearHoldingState(@Nullable Entity holdingPlayer) {
        if (holdingPlayer != null) {
            AetherIIDataAttachments.get(holdingPlayer, AetherIIDataAttachments.ABILITY_BEHAVIOR).setGravititeHoldingFloatingBlock(false);
            AetherIIDataAttachments.sync(holdingPlayer, AetherIIDataAttachments.ABILITY_BEHAVIOR);
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

    @Nullable
    public Entity getHoldingPlayer() {
        int id = this.getEntityData().get(DATA_OWNER_ID);
        return id != -1 ? this.level().getEntity(id) : null;
    }

    public void setHoldingPlayer(@Nullable Entity entity) {
        int id = entity != null ? entity.getId() : -1;
        this.getEntityData().set(DATA_OWNER_ID, id);
    }

    public void setStartPos(BlockPos startPos) {
        this.entityData.set(DATA_START_POS_ID, startPos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS_ID);
    }

    public void setBlockEntityData(CompoundTag tag) {
        this.blockData = tag;
    }

    @Nullable
    public CompoundTag getBlockEntityData() {
        return this.blockData;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.setRot(yRot, xRot);
        this.lerpSteps = steps;
    }

    public double lerpTargetX() {
        return this.lerpSteps > 0 ? this.lerpX : this.getX();
    }

    public double lerpTargetY() {
        return this.lerpSteps > 0 ? this.lerpY : this.getY();
    }

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
        if (tag.contains("TileEntityData", Tag.TAG_COMPOUND)) {
            this.blockData = tag.getCompound("TileEntityData");
        }
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setStartPos(this.blockPosition());
    }
}
