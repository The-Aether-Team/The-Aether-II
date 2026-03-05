package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class MoaEggBlockEntity extends BlockEntity {
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
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        var state = this.getBlockState();
        components.set(AetherIIDataComponents.MOA_EGG_TYPE, new MoaEggType(state.getValue(MoaEggBlock.KERATIN), state.getValue(MoaEggBlock.EYES), state.getValue(MoaEggBlock.FEATHERS), this.getBlockState().getValue(MoaEggBlock.FEATHER_SHAPE)));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.placedBy = input.read("PlacedBy", EntityReference.<Player>codec()).orElse(null);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.placedBy != null) {
            this.placedBy.store(output, "PlacedBy");
        }
    }
}