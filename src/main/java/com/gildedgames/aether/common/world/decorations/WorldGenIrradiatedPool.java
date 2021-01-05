package com.gildedgames.aether.common.world.decorations;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecorationGenerator;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenIrradiatedPool implements WorldDecorationGenerator
{
    public WorldGenIrradiatedPool() {}

    @Override
    public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
    {
        World world = slice.getWorld();
        pos = slice.getHighestBlockPos(pos.getX(), pos.getZ());

        if (world.getBlockState(pos.down()) != BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED))
        {
            return false;
        }

        pos = pos.down(1);

        int radius = 4;
        int radiusExterior = radius + 1;

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++)
        {
            for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++)
            {
                if (world.getBlockState(new BlockPos(x, pos.down(1).getY(), z)) == Blocks.AIR.getDefaultState())
                {
                    return false;
                }
            }
        }

        for (int x = pos.getX() - radiusExterior; x < pos.getX() + radiusExterior; x++)
        {
            for (int y = pos.getY() - radiusExterior; y < pos.getY() + radiusExterior; y++)
            {
                for (int z = pos.getZ() - radiusExterior; z < pos.getZ() + radiusExterior; z++)
                {
                    float formula = (float) (Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2) + Math.pow(z - pos.getZ(), 2));

                    if (formula <= Math.pow(radiusExterior, 2))
                    {
                        if ((world.getBlockState(new BlockPos(x, y, z)) != Blocks.AIR.getDefaultState()
                                && world.getBlockState(new BlockPos(x, y, z)) != Blocks.FLOWING_WATER.getDefaultState())
                                || y <= pos.getY() - 1)
                        {
                            world.setBlockState(new BlockPos(x, y, z), BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.PROPERTY_VARIANT, BlockHolystone.IRRADIATED_HOLYSTONE), 2 | 16);
                        }
                    }
                }
            }
        }

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++)
        {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++)
            {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++)
                {
                    float formula = (float) (Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2) + Math.pow(z - pos.getZ(), 2));

                    if (formula <= Math.pow(radius, 2))
                    {
                        world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 2 | 16);

                        if (y <= pos.getY() - 1)
                        {
                            world.setBlockState(new BlockPos(x, y, z), Blocks.FLOWING_WATER.getDefaultState(), 2 | 16);
                        }
                    }
                }
            }
        }

        for (int x = pos.getX() - 1; x < pos.getX() + 1; x++)
        {
            for (int z = pos.getZ() - 1; z < pos.getZ() + 1; z++)
            {
                if (rand.nextInt(2) == 0)
                {
                    BlockPos down = new BlockPos(x, pos.down(radius - 1).getY(), z);

                    if (x == pos.getX() && z == pos.getZ())
                    {
                        down = new BlockPos(x, pos.down(radius).getY(), z);
                    }

                    world.setBlockState(down, BlocksAether.irradiated_dust_block.getDefaultState());
                }
            }
        }

        return true;
    }
}
