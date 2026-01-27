package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.copyState != null) {
            output.store("copy_state", BlockState.CODEC, this.copyState);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.copyState = input.read("copy_state", BlockState.CODEC).orElse(null);
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
            AuxiliaryLightManager lightManager = this.level.getAuxLightManager(pos);
            if (lightManager != null) {
                lightManager.setLightAt(pos, this.getCopyState() != null ? this.getCopyState().getLightEmission() : 0);
            }
            this.level.getLightEngine().checkBlock(pos);
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
    protected void applyImplicitComponents(DataComponentGetter getter) {
        super.applyImplicitComponents(getter);
        this.copyState = getter.getOrDefault(AetherIIDataComponents.BLOCK_STATE, null);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(AetherIIDataComponents.BLOCK_STATE, this.copyState);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("copy_state");
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);
        this.requestModelDataUpdate();
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        this.handleUpdateTag(valueInput);
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public ModelData getModelData() {
        if (this.copyState != null) {
            return ModelData.of(CopyData.PROPERTY, new CopyData(this.copyState));
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

    public record CopyData(BlockState state) {
        public static final ModelProperty<CopyData> PROPERTY = new ModelProperty<>();
    }
}
