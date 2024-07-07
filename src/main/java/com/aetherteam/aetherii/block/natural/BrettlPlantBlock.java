package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.neoforge.common.util.TriState;

public class BrettlPlantBlock extends Block {
    public static final EnumProperty<BrettlPlantBlock.BrettlType> TYPE = EnumProperty.create("brettl_type", BrettlPlantBlock.BrettlType.class);
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;


    public BrettlPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, BrettlType.BASE).setValue(GROWN, false));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        TriState soilDecision = stateBelow.canSustainPlant(level, pos.below(), Direction.UP, stateBelow);
        if (!soilDecision.isDefault()) return soilDecision.isTrue();
        return stateBelow.is(AetherIIBlocks.QUICKSOIL) && (stateBelow.getValue(TYPE) == BrettlType.BASE) && (stateBelow.getValue(TYPE) == BrettlType.MIDDLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, GROWN);
    }

    public enum BrettlType implements StringRepresentable {
        BASE,
        MIDDLE,
        TIP;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}