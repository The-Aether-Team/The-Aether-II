package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.client.AetherIIClientProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public abstract class CopyBlockEntity extends BlockEntity {
    protected BlockState copyState;

    public CopyBlockEntity(BlockEntityType<? extends CopyBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public BlockState getCopyState() {
        return this.copyState;
    }

    public void setCopyState(BlockState copyState) {
        this.copyState = copyState;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.saveCopyState(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.copyState = loadCopyState(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.setChanged();
        this.getLevel().blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, 0);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            BlockPos pos = this.getBlockPos();
            this.level.getLightEngine().checkBlock(pos);
            this.requestModelDataUpdate();
            if (this.level.isClientSide()) {
                AetherIIClientProxy.setSectionDirty(SectionPos.of(pos));
            }
        }
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.setChanged();
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag input) {
        super.handleUpdateTag(input);
        this.requestModelDataUpdate();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            this.handleUpdateTag(tag);
        }
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ModelData getModelData() {
        if (this.copyState != null) {
            return ModelData.builder().with(CopyData.PROPERTY, new CopyData(this.copyState)).build();
        }
        return super.getModelData();
    }

    public BlockState open(Level level, BlockPos pos) {
        return null;
    }

    public BlockState close(Level level, BlockPos pos) {
        return null;
    }

    public BlockState destroy(Level level, BlockPos pos) {
        return null;
    }

    public abstract ItemStack getItem();

    protected void saveCopyState(CompoundTag tag) {
        if (this.copyState != null) {
            BlockState.CODEC.encodeStart(NbtOps.INSTANCE, this.copyState).result().ifPresent(copyStateTag -> tag.put("copy_state", copyStateTag));
        }
    }

    public static BlockState loadCopyState(CompoundTag tag) {
        Tag copyStateTag = tag.get("copy_state");
        if (copyStateTag == null) {
            return null;
        }
        return BlockState.CODEC.parse(NbtOps.INSTANCE, copyStateTag).result().orElse(null);
    }

    public record CopyData(BlockState state) {
        public static final ModelProperty<CopyData> PROPERTY = new ModelProperty<>();
    }
}
