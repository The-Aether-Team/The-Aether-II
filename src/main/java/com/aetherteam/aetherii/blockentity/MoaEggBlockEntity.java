package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import com.aetherteam.aetherii.entity.EntityReference;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MoaEggBlockEntity extends BlockEntity {
    private static final String COMPONENTS_TAG = "aether_ii_components";

    @Nullable
    private EntityReference<Player> placedBy;
    public int tickCount;

    public MoaEggBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.MOA_EGG.get(), pos, state);
    }

    @Nullable
    public EntityReference<Player> getPlacedBy() {
        return placedBy;
    }

    public void setPlacedBy(@Nullable EntityReference<Player> placedBy) {
        this.placedBy = placedBy;
        this.setChanged();
    }

    public void setPlacedBy(@Nullable Player placedBy) {
        this.placedBy = placedBy == null ? null : EntityReference.of(placedBy);
        this.setChanged();
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MoaEggBlockEntity moaEggBlockEntity) {
        moaEggBlockEntity.tickCount++;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.placedBy = tag.hasUUID("PlacedBy") ? EntityReference.of(tag.getUUID("PlacedBy")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.placedBy != null) {
            tag.putUUID("PlacedBy", this.placedBy.uuid());
        }
        CompoundTag components = tag.getCompound(COMPONENTS_TAG);
        BlockState state = this.getBlockState();
        MoaEggType type = new MoaEggType(state.getValue(MoaEggBlock.KERATIN), state.getValue(MoaEggBlock.EYES), state.getValue(MoaEggBlock.FEATHERS), state.getValue(MoaEggBlock.FEATHER_SHAPE));
        MoaEggType.CODEC.encodeStart(NbtOps.INSTANCE, type).result().ifPresent(encoded -> {
            components.put(AetherIIDataComponents.MOA_EGG_TYPE.id().toString(), encoded);
            tag.put(COMPONENTS_TAG, components);
        });
    }
}
