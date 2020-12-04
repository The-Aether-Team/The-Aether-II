package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.util.selectors.ItemEntry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class BlockIrradiatedDust extends Block
{
    private final List<ItemEntry> drops = new ArrayList<>();

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
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        this.initializeDrops();

        Random rand = new Random();
        ItemEntry itemEntry = WeightedRandom.getRandomItem(rand, this.drops);
        if (itemEntry.getItem() != null)
        {
            int dropCount = 1;

            if (itemEntry.getItem() == ItemsAether.irradiated_dust)
            {
                dropCount = dropCount + rand.nextInt(4);
            }

            drops.add(new ItemStack(itemEntry.getItem(), dropCount));
        }
    }

    private void initializeDrops()
    {
        this.drops.add(new ItemEntry(ItemsAether.irradiated_dust, 50));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_tool, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_sword, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_armor, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_charm, 10));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_ring, 7));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_neckwear, 5));
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
