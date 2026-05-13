package com.aetherteam.aetherii.entity.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class HoveringBlockEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> DATA_START_POS_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<CompoundTag> DATA_BLOCK_ENTITY_DATA_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, AetherIIDataSerializers.COMPOUND_TAG.get());

    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);

    private BlockState blockState = Blocks.SAND.defaultBlockState();
    protected boolean held = true;
    protected boolean launched;
    protected int launchDuration;
    protected Vec3 targetSettlePosition;

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
        builder.define(DATA_START_POS_ID, BlockPos.ZERO);
        builder.define(DATA_BLOCK_ENTITY_DATA_ID, new CompoundTag());
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            this.interpolation.interpolate();
            return;
        }

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
            if (this.verticalCollision || this.horizontalCollision || this.onGround()) {
                this.markShouldSettle();
            }
            if (holdingPlayer != null) {
                holdingPlayer.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setGravititeHoldingFloatingBlock(false);
                holdingPlayer.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
            }
        }
        if (this.targetSettlePosition != null) {
            this.settleBlock();
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }


        this.move(MoverType.SELF, this.getDeltaMovement());

        if (this.launched) {
            if (this.launchDuration++ >= 100) {
                this.dropBlock(this.blockState);
            }
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.markShouldSettle();
            this.settleBlock();
        }
        return super.interact(player, hand, location);
    }

    @Override
    public boolean skipAttackInteraction(Entity entity) {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            this.held = false;
            this.launched = true;
            this.push(holdingPlayer.getViewVector(1.0F).x() * 2.5, holdingPlayer.getViewVector(1.0F).y() * 2.5, holdingPlayer.getViewVector(1.0F).z() * 2.5);
            holdingPlayer.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setGravititeHoldingFloatingBlock(false);
            holdingPlayer.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
        }
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource pSource, float pAmount) {
        return false;
    }

    private void markShouldSettle() {
        Predicate<BlockPos> findPos = (pos) -> {
            var state = this.level().getBlockState(pos);
            if (state.is(AetherIITags.Blocks.HOVERING_BLOCK_CANT_REPLACE)) {
                return false;
            }
            return state.getCollisionShape(level(), pos).isEmpty();
        };

        if (this.targetSettlePosition == null) {
            Optional<BlockPos> newPos = BlockPos.findClosestMatch(this.blockPosition(), 1, 1, findPos);
            Vec3 targetPos = this.blockPosition().getCenter().subtract(0, 0.5, 0);
            if (newPos.isPresent()) {
                targetPos = newPos.get().getCenter().subtract(0, 0.5, 0);
            }
            this.targetSettlePosition = targetPos;
        }
    }

    private void settleBlock() {
        Entity holdingPlayer = this.getHoldingPlayer();
        Vec3 currentPos = this.position();
        Vec3 motion = this.targetSettlePosition.subtract(currentPos);
        BlockPos newPos = BlockPos.containing(this.targetSettlePosition.x(), this.targetSettlePosition.y(), this.targetSettlePosition.z());
        this.setDeltaMovement(motion);
        if (holdingPlayer != null) {
            holdingPlayer.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setGravititeHoldingFloatingBlock(false);
            holdingPlayer.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
        }
        if (this.position().distanceTo(this.targetSettlePosition) <= 0.001) {
            if (!this.level().isClientSide()) {
                BlockState levelState = this.level().getBlockState(newPos);
                if (!levelState.isAir()) {
                    this.level().destroyBlock(newPos, true);
                }
                this.level().setBlock(newPos, this.blockState, 3);
                levelState = this.level().getBlockState(newPos);
                if (levelState.is(this.getBlockState().getBlock())) {
                    if (this.getBlockEntityData() != null && this.getBlockState().hasBlockEntity()) {
                        BlockEntity blockEntity = this.level().getBlockEntity(newPos);
                        if (blockEntity != null) {
                            CompoundTag tag = blockEntity.saveWithoutMetadata(this.level().registryAccess());
                            for (String string : this.getBlockEntityData().keySet()) {
                                Tag blockDataTag = this.getBlockEntityData().get(string);
                                if (blockDataTag != null) {
                                    tag.put(string, blockDataTag.copy());
                                }
                            }

                            try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(AetherII.LOGGER)) {
                                blockEntity.loadWithComponents(TagValueInput.create(problemreporter$scopedcollector, this.level().registryAccess(), tag));
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
                this.spawnAtLocation(serverLevel, stack);
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
        this.entityData.set(DATA_START_POS_ID, pStartPos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS_ID);
    }

    public void setBlockEntityData(CompoundTag tag) {
        this.entityData.set(DATA_BLOCK_ENTITY_DATA_ID, tag);
    }

    public CompoundTag getBlockEntityData() {
        return this.entityData.get(DATA_BLOCK_ENTITY_DATA_ID);
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    public InterpolationHandler getInterpolation() {
        return this.interpolation;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.store("BlockState", BlockState.CODEC, this.blockState);
        if (this.getBlockEntityData() != null) {
            output.store("TileEntityData", CompoundTag.CODEC, this.getBlockEntityData());
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        this.blockState = input.read("BlockState", BlockState.CODEC).orElse(Blocks.SAND.defaultBlockState());
        input.read("TileEntityData", CompoundTag.CODEC).ifPresent(this::setBlockEntityData);
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
