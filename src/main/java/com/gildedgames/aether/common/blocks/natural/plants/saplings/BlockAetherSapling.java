package com.gildedgames.aether.common.blocks.natural.plants.saplings;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherPlant;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSapling;
import com.gildedgames.aether.common.items.blocks.ItemBlockSkyrootButton;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.core.CreationData;
import com.gildedgames.orbis.lib.core.baking.BakedBlueprint;
import com.gildedgames.orbis.lib.core.util.BlueprintPlacer;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockAetherSapling extends BlockAetherPlant implements IGrowable, IBlockMultiName, IBlockWithItem
{
	public static final PropertyInteger PROPERTY_STAGE = PropertyInteger.create("growth_stage",0,1);

	private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);

	public BlockAetherSapling()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (!worldIn.isRemote)
		{
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
			{
				this.grow(worldIn, rand, pos, state);
			}
		}
	}

	@Override
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state)
	{
		if (state.getValue(PROPERTY_STAGE) == 0)
		{
			world.setBlockState(pos, state.cycleProperty(PROPERTY_STAGE), 4);

			return;
		}

		if (TerrainGen.saplingGrowTree(world, rand, pos))
		{
			BlueprintDefinition tree = this.getBlueprint(state);

			if (tree != null)
			{
				BlockPos adjustedPos = pos.add(this.getBlueprintOffset(state));

				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

				BakedBlueprint baked = new BakedBlueprint(tree, new CreationData(world).pos(BlockPos.ORIGIN).placesAir(false).placesVoid(false));

				if (!BlueprintPlacer.place(world, baked, adjustedPos))
				{
					world.setBlockState(pos, state, 4);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
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
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(this.getPropertyVariant()).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(this.getPropertyVariant(), this.getPropertyVariant().fromMeta(meta & 7))
				.withProperty(PROPERTY_STAGE, (meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(this.getPropertyVariant()).getMeta() | (state.getValue(PROPERTY_STAGE) << 3);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, this.getPropertyVariant(), PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(final World world, final BlockPos pos, final IBlockState state, final boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World world, final Random rand, final BlockPos pos, final IBlockState state)
	{
		return rand.nextFloat() < 0.45f;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		return SAPLING_AABB;
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockCustomSapling(this);
	}

	public abstract BlueprintDefinition getBlueprint(IBlockState state);

	public abstract BlockPos getBlueprintOffset(IBlockState state);

	public abstract PropertyVariant getPropertyVariant();
}
