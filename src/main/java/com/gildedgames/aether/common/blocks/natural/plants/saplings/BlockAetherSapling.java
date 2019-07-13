package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherPlant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.core.CreationData;
import com.gildedgames.orbis.lib.core.baking.BakedBlueprint;
import com.gildedgames.orbis.lib.core.util.BlueprintPlacer;
import com.gildedgames.orbis.lib.processing.BlockAccessExtendedWrapper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockAetherSapling extends BlockAetherPlant implements IGrowable
{
	public static final IntegerProperty PROPERTY_STAGE = IntegerProperty.create("growth_stage", 0, 1);

	private static final VoxelShape SAPLING_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);

	public BlockAetherSapling()
	{
		super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote())
		{
			super.tick(state, world, pos, rand);

			if (world.getBrightness(pos.up()) >= 9 && rand.nextInt(7) == 0)
			{
				this.grow(world, rand, pos, state);
			}
		}
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final BlockState state)
	{
		if (state.get(PROPERTY_STAGE) == 0)
		{
			world.setBlockState(pos, state.cycle(PROPERTY_STAGE), 4);

			return;
		}

		BlueprintDefinition tree = this.getBlueprint(state);

		if (tree != null)
		{
			BlockPos adjustedPos = pos.add(this.getBlueprintOffset(state));

			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

			BakedBlueprint baked = new BakedBlueprint(tree, new CreationData(world).pos(BlockPos.ZERO).placesAir(false).placesVoid(false));

			if (!BlueprintPlacer.place(new BlockAccessExtendedWrapper(world), baked, adjustedPos))
			{
				world.setBlockState(pos, state, 4);
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(this.getPropertyVariant(), PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final BlockState state)
	{
		return rand.nextFloat() < 0.45f;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SAPLING_AABB;
	}

	@Override
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}

	public abstract BlueprintDefinition getBlueprint(BlockState state);

	public abstract BlockPos getBlueprintOffset(BlockState state);

	public abstract PropertyVariant getPropertyVariant();

}
