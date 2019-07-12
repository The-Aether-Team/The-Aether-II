package com.gildedgames.aether.common.world.decorations.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBrettlPlant;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBrettlPlant extends WorldGenerator
{

	private final BlockState brettlState;

	public WorldGenBrettlPlant()
	{
		this.brettlState = BlocksAether.brettl_plant.getDefaultState();
	}

	@Override
	public boolean generate(final World worldIn, final Random rand, final BlockPos position)
	{
		// growth stage controls what stage will be grown.
		// count is used to minimize the number of brettl plants that spawn in close proximity.
		int i = 0, growthStage, count = 0;
		while (i < 64 && count < 4)
		{
			BlockPos randomPos = position.add(rand.nextInt(8) - rand.nextInt(8), 0, rand.nextInt(8) - rand.nextInt(8));
			randomPos = worldIn.getHeight(randomPos).down();

			if (!worldIn.isBlockLoaded(randomPos))
			{
				continue;
			}

			if (worldIn.getBlockState(randomPos).getBlock() == BlocksAether.quicksoil && worldIn.isAirBlock(randomPos.up()))
			{
				growthStage = rand.nextInt(10);

				if (growthStage == 0)
				{
					((BlockBrettlPlant) BlocksAether.brettl_plant).fullyGrowPlant(worldIn, randomPos.up(2), this.brettlState);
				}
				else if (growthStage > 0 && growthStage <= 3)
				{
					((BlockBrettlPlant) BlocksAether.brettl_plant).fullyPrunePlant(worldIn, randomPos.up(2), this.brettlState);
				}
				else if (growthStage > 4 && growthStage <= 8)
				{
					worldIn.setBlockState(randomPos.up(), this.brettlState.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false)
							.withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.BASE), 2 | 16);
					worldIn.setBlockState(randomPos.up(2), this.brettlState.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false)
							.withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.MID), 2 | 16);
					worldIn.setBlockState(randomPos.up(3), this.brettlState.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false)
							.withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.TOP), 2 | 16);
				}
				else
				{
					worldIn.setBlockState(randomPos.up(), this.brettlState.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false)
							.withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.BASE), 2 | 16);
					worldIn.setBlockState(randomPos.up(2), this.brettlState.withProperty(BlockBrettlPlant.PROPERTY_HARVESTABLE, false)
							.withProperty(BlockBrettlPlant.PROPERTY_VARIANT, BlockBrettlPlant.TOP), 2 | 16);
				}

				count++;
			}

			i++;
		}

		return true;
	}
}
