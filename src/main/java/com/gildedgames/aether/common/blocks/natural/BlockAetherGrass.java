package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherGrass extends Block
{
	public static final BlockVariant AETHER = new BlockVariant(0, "normal"),
			ENCHANTED = new BlockVariant(1, "enchanted"),
			ARCTIC = new BlockVariant(2, "arctic"),
			MAGNETIC = new BlockVariant(3, "magnetic"),
			IRRADIATED = new BlockVariant(4, "irradiated");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER, ENCHANTED, ARCTIC, MAGNETIC, IRRADIATED);

	public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

	public BlockAetherGrass(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, AETHER).with(SNOWY, Boolean.FALSE));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote() && state.get(PROPERTY_VARIANT) != ENCHANTED)
		{
			BlockPos.PooledMutableBlockPos up = BlockPos.PooledMutableBlockPos.retain();
			up.setPos(pos.getX(), pos.getY() + 1, pos.getZ());

			if (world.getBrightness(up) < 4 && world.getBlockState(up).getOpacity(world, up) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getBrightness(up) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						final BlockPos randomNeighbor = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (randomNeighbor.getY() >= 0 && randomNeighbor.getY() < 256 && !world.isBlockLoaded(randomNeighbor))
						{
							return;
						}

						final BlockState aboveState = world.getBlockState(randomNeighbor.up());
						final BlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt
								&& neighborState.get(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getBrightness(randomNeighbor.up()) >= 4
								&& aboveState.getOpacity(world, randomNeighbor.up()) <= 2)
						{
							final BlockState grassState = this.getDefaultState().with(PROPERTY_VARIANT, state.get(PROPERTY_VARIANT));

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}

			up.close();
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(SNOWY, PROPERTY_VARIANT);
	}
}
