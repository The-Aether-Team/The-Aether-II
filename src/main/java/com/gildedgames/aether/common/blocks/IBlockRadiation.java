package com.gildedgames.aether.common.blocks;

import net.minecraft.block.Block;

public interface IBlockRadiation
{
    Block setRadiationDistance(int distance);

    int getRadiationDistance();

    Block setRadiationAmount(int amount);

    int getRadiationAmount();

    Block setRadiationRate(int rate);

    int getRadiationRate();
}
