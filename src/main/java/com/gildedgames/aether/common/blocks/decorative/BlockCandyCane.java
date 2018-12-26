package com.gildedgames.aether.common.blocks.decorative;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCandyCane extends BlockRotatedPillar
{
    public static final PropertyEnum<BlockCandyCane.EnumAxis> BLOCK_AXIS = PropertyEnum.<BlockCandyCane.EnumAxis>create("axis", BlockCandyCane.EnumAxis.class);

    public BlockCandyCane()
    {
        super(Material.GLASS);
        this.setHardness(1.3F);
        this.setSoundType(SoundType.GLASS);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((BlockCandyCane.EnumAxis)state.getValue(BLOCK_AXIS))
                {
                    case X:
                        return state.withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.Z);
                    case Z:
                        return state.withProperty(BLOCK_AXIS, BlockCandyCane.EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        BlockCandyCane.EnumAxis axis = BlockCandyCane.EnumAxis.NONE;

        switch (meta & 7)
        {
            case 1:
                axis = BlockCandyCane.EnumAxis.Y;
                break;
            case 2:
                axis = BlockCandyCane.EnumAxis.X;
                break;
            case 3:
                axis = BlockCandyCane.EnumAxis.Z;
                break;
        }

        return this.getDefaultState().withProperty(BLOCK_AXIS, axis);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;

        switch (state.getValue(BLOCK_AXIS))
        {
            case Y:
                meta |= 1;
                break;
            case X:
                meta |= 2;
                break;
            case Z:
                meta |= 3;
                break;
        }

        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BLOCK_AXIS);
    }

    public static enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z"),
        NONE("none");

        private final String name;

        private EnumAxis(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockCandyCane.EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch (axis)
            {
                case X:
                    return X;
                case Y:
                    return Y;
                case Z:
                    return Z;
                default:
                    return NONE;
            }
        }

        public String getName()
        {
            return this.name;
        }
    }
}
