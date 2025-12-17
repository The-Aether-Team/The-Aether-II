package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
        input.read("mimic_state", BlockState.CODEC).ifPresent(state -> this.copyState = state);
    }

    @Override
    public ModelData getModelData() {
        if (this.copyState != null) {
            return ModelData.of(CopyData.PROPERTY, new CopyData(this.copyState));
        }
        return super.getModelData();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
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

    public abstract ItemStack getItem();

    public record CopyData(BlockState state) {
        public static final ModelProperty<CopyData> PROPERTY = new ModelProperty<>();
    }
}
