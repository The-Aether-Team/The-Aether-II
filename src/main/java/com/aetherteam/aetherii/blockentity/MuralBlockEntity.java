package com.aetherteam.aetherii.blockentity;

import java.util.Optional;

import javax.annotation.Nullable;

import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.MuralBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMurals;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class MuralBlockEntity extends BlockEntity {
    @Nullable
    private Holder<Mural> mural;

    public MuralBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.MURAL.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.getMuralId().ifPresent(id -> tag.putString("mural", id.toString()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.mural = tag.contains("mural", 8) ? this.getMuralHolder(new ResourceLocation(tag.getString("mural"))) : null;
    }

    public static ItemStack createMuralItem(@Nullable Holder<Mural> muralHolder, int offsetX, int offsetY) {
        ItemStack itemstack = new ItemStack(AetherIIBlocks.MURAL.get());
        if (muralHolder != null) {
            var mural = muralHolder.value();
            AetherIIDataComponents.set(itemstack, AetherIIDataComponents.MURAL_SECTION, new MuralSection(muralHolder, Mth.clamp(offsetX, 0, mural.width() - 1), Mth.clamp(offsetY, 0, mural.height() - 1)));
        } else {
            AetherIIDataComponents.remove(itemstack, AetherIIDataComponents.MURAL_SECTION);
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
        this.mural = mural;
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
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
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
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    public record MuralData(Direction facing, MuralSection section) {
        public static final ModelProperty<MuralData> PROPERTY = new ModelProperty<>();
    }

    @Override
    public ModelData getModelData() {
        Holder<Mural> mural = this.getMural().orElse(null);
        BlockState blockState = this.getBlockState();
        if (mural != null) {
            return ModelData.builder().with(MuralData.PROPERTY, new MuralData(this.getDirection(), new MuralSection(mural, blockState.getValue(MuralBlock.X_OFFSET), blockState.getValue(MuralBlock.Y_OFFSET)))).build();
        }
        return super.getModelData();
    }

    private Optional<ResourceLocation> getMuralId() {
        if (this.mural == null) {
            return Optional.empty();
        }
        return this.mural.unwrapKey()
                .map(ResourceKey::location)
                .or(() -> AetherIIMurals.getKey(this.mural.value()));
    }

    @Nullable
    private Holder<Mural> getMuralHolder(ResourceLocation id) {
        return AetherIIMurals.getHolder(ResourceKey.create(AetherIIRegistries.MURAL, id)).orElse(null);
    }
}
