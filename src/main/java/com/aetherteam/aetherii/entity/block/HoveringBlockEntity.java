package com.aetherteam.aetherii.entity.block;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class HoveringBlockEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(HoveringBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    private BlockState blockState = Blocks.SAND.defaultBlockState();
    @Nullable
    public CompoundTag blockData;
//    private int ticksStuck = 0;

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
        this.getEntityData().define(DATA_OWNER_ID, -1);
        this.getEntityData().define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public void tick() {
        Entity holdingPlayer = this.getHoldingPlayer();
        if (holdingPlayer != null) {
            Vec3 playerToBlock = this.position().subtract(holdingPlayer.position().add(0, 1, 0));
            Vec3 target = holdingPlayer.getViewVector(1.0F).scale(2);
            Vec3 movement = target.subtract(playerToBlock);
            this.setDeltaMovement(movement.scale(0.5)); //todo lerping etc.
//            AetherII.LOGGER.info(String.valueOf(this.getDeltaMovement()));
            if (playerToBlock.length() > 5) {
                this.discard();
            }
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) { //right click

        return super.interact(pPlayer, pHand);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) { //left click
        this.setHoldingPlayer(null);
        return super.hurt(pSource, pAmount);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
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
