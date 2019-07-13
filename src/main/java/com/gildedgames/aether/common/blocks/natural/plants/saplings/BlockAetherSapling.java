package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherPlant;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.core.CreationData;
import com.gildedgames.orbis.lib.core.baking.BakedBlueprint;
import com.gildedgames.orbis.lib.core.util.BlueprintPlacer;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.api.distmarker.Dist;

import java.util.Random;

public abstract class BlockAetherSapling extends BlockAetherPlant implements IGrowable, IBlockMultiName
{
	public static final IntegerProperty PROPERTY_STAGE = IntegerProperty.create("growth_stage",0,1);

	private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);

	public BlockAetherSapling()
	{
		super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (!world.isRemote)
		{
			super.tick(state, world, pos, rand);

			if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
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

		if (TerrainGen.saplingGrowTree(world, rand, pos))
		{
			BlueprintDefinition tree = this.getBlueprint(state);

			if (tree != null)
			{
				BlockPos adjustedPos = pos.add(this.getBlueprintOffset(state));

				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

				BakedBlueprint baked = new BakedBlueprint(tree, new CreationData(world).pos(BlockPos.ZERO).placesAir(false).placesVoid(false));

				if (!BlueprintPlacer.place(world, baked, adjustedPos))
				{
					world.setBlockState(pos, state, 4);
				}
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : this.getPropertyVariant().getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return this.getPropertyVariant().fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.get(this.getPropertyVariant()).getMeta();
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(this.getPropertyVariant(), this.getPropertyVariant().fromMeta(meta & 7))
				.with(PROPERTY_STAGE, (meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(this.getPropertyVariant()).getMeta() | (state.get(PROPERTY_STAGE) << 3);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(this.getPropertyVariant(), PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(final World world, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final BlockState state)
	{
		return rand.nextFloat() < 0.45f;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final BlockState state, final IBlockReader source, final BlockPos pos)
	{
		return SAPLING_AABB;
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	public abstract BlueprintDefinition getBlueprint(BlockState state);

	public abstract BlockPos getBlueprintOffset(BlockState state);

	public abstract PropertyVariant getPropertyVariant();

}
