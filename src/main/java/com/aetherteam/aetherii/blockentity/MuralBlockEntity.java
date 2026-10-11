package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.MuralBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.Optional;

public class MuralBlockEntity extends BlockEntity {
    @Nullable
    private Holder<Mural> mural;

    public MuralBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.MURAL.get(), pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.mural != null) {
            output.store("mural", Mural.CODEC, this.mural);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.mural = input.read("mural", Mural.CODEC).orElse(null);
    }

    public static ItemStack createMuralItem(@Nullable Holder<Mural> muralHolder, int offsetX, int offsetY) {
        ItemStack itemstack = AetherIIBlocks.MURAL.asItem().getDefaultInstance();
        if (muralHolder != null) {
            var mural = muralHolder.value();
            itemstack.set(AetherIIDataComponents.MURAL_SECTION, new MuralSection(muralHolder, Math.clamp(offsetX, 0, mural.width() - 1), Math.clamp(offsetY, 0, mural.height() - 1)));
        } else {
            itemstack.remove(AetherIIDataComponents.MURAL_SECTION);
        }
        return itemstack;
    }

    public Direction getDirection() {
        return this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public Optional<Holder<Mural>> getMural() {
        return Optional.ofNullable(this.mural);
    }

    public void setMural(Optional<Holder<Mural>> mural) {
        this.setMural(mural.orElse(null));
    }

    public void setMural(@Nullable Holder<Mural> mural) {
        this.mural = mural == null ? null : mural.getDelegate();
    }

    public int getMuralOffsetX() {
        return this.getBlockState().getValue(MuralBlock.X_OFFSET);
    }

    public int getMuralOffsetY() {
        return this.getBlockState().getValue(MuralBlock.Y_OFFSET);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void collectImplicitComponents(Builder components) {
        super.collectImplicitComponents(components);
        if (this.mural != null) {
            var blockState = this.getBlockState();
            int offsetX = blockState.getValue(MuralBlock.X_OFFSET);
            int offsetY = blockState.getValue(MuralBlock.Y_OFFSET);
            var mural = this.mural.value();
            components.set(AetherIIDataComponents.MURAL_SECTION, new MuralSection(this.mural, Math.min(offsetX, mural.width() - 1), Math.min(offsetY, mural.height() - 1)));
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter componentGetter) {
        super.applyImplicitComponents(componentGetter);
        MuralSection muralSection = componentGetter.getOrDefault(AetherIIDataComponents.MURAL_SECTION, null);
        if (muralSection == null) {
            this.mural = null;
        } else {
            this.mural = muralSection.mural();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard("mural");
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

    public record MuralData(Direction facing, MuralSection section) {
        public static final ModelProperty<MuralData> PROPERTY = new ModelProperty<>();
    }

    @Override
    public ModelData getModelData() {
        Holder<Mural> mural = this.getMural().orElse(null);
        BlockState blockState = this.getBlockState();
        if (mural != null) {
            return ModelData.of(MuralData.PROPERTY, new MuralData(this.getDirection(), new MuralSection(mural, blockState.getValue(MuralBlock.X_OFFSET), blockState.getValue(MuralBlock.Y_OFFSET))));
        }
        return super.getModelData();
    }
}
