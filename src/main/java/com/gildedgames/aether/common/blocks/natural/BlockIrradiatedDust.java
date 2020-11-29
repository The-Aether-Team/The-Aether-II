package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockIrradiatedDust extends Block
{
    public BlockIrradiatedDust()
    {
        super(Material.ROCK);

        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.setLightLevel(0.4f);

        this.useNeighborBrightness = true;

        this.setSoundType(SoundType.SAND);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        int random = rand.nextInt(50);

        if (random == 49)
        {
            return ItemsAether.irradiated_chunk;
        }
        else if (random == 48)
        {
            return ItemsAether.irradiated_tool;
        }
        else if (random == 47)
        {
            return ItemsAether.irradiated_sword;
        }
        else if (random == 46)
        {
            return ItemsAether.irradiated_armor;
        }
        else if (random == 45)
        {
            return ItemsAether.irradiated_ring;
        }
        else if (random == 44)
        {
            return ItemsAether.irradiated_neckwear;
        }
        else if (random == 43)
        {
            return ItemsAether.irradiated_charm;
        }
        else
        {
            return ItemsAether.irradiated_dust;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (world instanceof ChunkCache)
        {
            return 0;
        }

        return super.getLightValue(state, world, pos);
    }
}
