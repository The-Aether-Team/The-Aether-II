package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

public class LockedBlockEntity extends BlockEntity {
    private BlockState mimicState;

    public LockedBlockEntity(BlockEntityType<LockedBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public LockedBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.LOCKED_BLOCK.get(), pos, blockState);
    }

    public BlockState getMimicState() {
        return this.mimicState;
    }

    public void setMimicState(BlockState mimicState) {
        this.mimicState = mimicState;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.mimicState != null) {
            output.store("mimic_state", BlockState.CODEC, this.mimicState);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.mimicState = input.read("mimic_state", BlockState.CODEC).orElse(null);
    }

    @Override
    public ModelData getModelData() {
        if (this.mimicState != null) {
            return ModelData.of(LockedData.PROPERTY, new LockedData(this.mimicState));
        }
        return super.getModelData();
    }

    public record LockedData(BlockState state) {
        public static final ModelProperty<LockedData> PROPERTY = new ModelProperty<>();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    public ItemStack getItem() {
        ItemStack stack = new ItemStack(AetherIIBlocks.LOCKED_BLOCK);
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter getter) {
        super.applyImplicitComponents(getter);
        this.mimicState = getter.getOrDefault(AetherIIDataComponents.BLOCK_STATE, null);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(AetherIIDataComponents.BLOCK_STATE, this.mimicState);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("mimic_state");
    }
}
