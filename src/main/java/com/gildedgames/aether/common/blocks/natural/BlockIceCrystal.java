package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.blocks.util.BlockBuilder;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;


public class BlockIceCrystal extends BlockBuilder implements IBlockMultiName {
    public static final BlockVariant
            STALAGMITE = new BlockVariant(0, "stalagmite"),
            STALAGTITE = new BlockVariant(1, "stalagtite");

    public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", STALAGMITE, STALAGTITE);

    protected static final AxisAlignedBB STALAGTITE_BB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D);
    protected static final AxisAlignedBB STALAGMITE_BB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1D, 0.75D);

    public BlockIceCrystal()
    {
        super(Material.GLASS);
        setSoundType(SoundType.GLASS);
        this.setHardness(1f);
        this.setResistance(2000f);
    }

    @Override
    public int damageDropped(final IBlockState state)
    {
        return STALAGMITE.getMeta();
    }

    @Override
    public IBlockState getStateFromMeta(final int meta)
    {
        final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);
        return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
    }

    @Override
    public int getMetaFromState(final IBlockState state)
    {
        return state.getValue(PROPERTY_VARIANT).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, PROPERTY_VARIANT);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - this.getDefaultState().getLightOpacity())
        {
            worldIn.setBlockToAir(pos);
        }
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof EntityPlayer) {
            worldIn.getBlockState(pos).getBlock().removedByPlayer(worldIn.getBlockState(pos), worldIn, pos, (EntityPlayer) entityIn, false);
            if (entityIn.world.isRemote) {
                entityIn.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1f, 1f);

                for (int i = 0; i < 50; i++)
                {
                    final double x = pos.getX() + worldIn.rand.nextDouble();
                    final double y = pos.getY() + 1.0D;
                    final double z = pos.getZ() + worldIn.rand.nextDouble();

                    worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {

        if (state.getValue(PROPERTY_VARIANT) == STALAGTITE)
        {
            if (worldIn.isAirBlock(pos.up()) || worldIn.getBlockState(pos.up()).getBlock() == Blocks.WATER)
            {
                worldIn.setBlockToAir(pos);
            }
        }
        if (state.getValue(PROPERTY_VARIANT) == STALAGMITE)
        {
            if (worldIn.isAirBlock(pos.down()) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.WATER)
            {
                worldIn.setBlockToAir(pos);
            }
        }
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (hitY == 0)
        {
            return this.getDefaultState().withProperty(PROPERTY_VARIANT, STALAGTITE);
        }

        return this.getDefaultState().withProperty(PROPERTY_VARIANT, STALAGMITE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (state.getValue(PROPERTY_VARIANT).getMeta() == STALAGMITE.getMeta())
        {
            return STALAGMITE_BB;
        }

        return STALAGTITE_BB;
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
    }

    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state)
    {

        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {

        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {

        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {

        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType getOffsetType()
    {
        return EnumOffsetType.XZ;
    }
}
