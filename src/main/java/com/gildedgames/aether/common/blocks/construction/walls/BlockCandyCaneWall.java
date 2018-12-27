package com.gildedgames.aether.common.blocks.construction.walls;

import java.util.Random;

import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.util.helpers.IslandHelper;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCandyCaneWall extends BlockCustomWall {

    public BlockCandyCaneWall(IBlockState state, float hardness, float resistance)
    {
        super(state, hardness, resistance);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity)
    {
        super.onEntityWalk(world, pos, entity);
        if(world.isRaining() || (IslandHelper.getPartial(world, entity.chunkCoordX, entity.chunkCoordZ) != null &&
                IslandHelper.getPartial(world, entity.chunkCoordX, entity.chunkCoordZ).getPrecipitation().getType() != PrecipitationType.NONE))
        {
            entity.motionX *= 0.1D;
            entity.motionZ *= 0.1D;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.SUGAR;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 6 + random.nextInt(3);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.getBlockColor(EnumDyeColor.RED);
    }
}
