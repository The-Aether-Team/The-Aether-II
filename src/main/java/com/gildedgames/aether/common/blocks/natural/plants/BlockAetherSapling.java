package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.world.features.trees.WorldGenSkyrootTree;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockAetherSapling extends BlockAetherPlant implements IGrowable, IAetherBlockWithVariants
{
	public static final BlockVariant
			BLUE_SKYROOT_SAPLING = new BlockVariant(0, "blue_skyroot"),
			GREEN_SKYROOT_SAPLING = new BlockVariant(1, "green_skyroot"),
			DARK_BLUE_SKYROOT_SAPLING = new BlockVariant(2, "dark_blue_skyroot"),
			GOLDEN_OAK_SAPLING = new BlockVariant(3, "golden_oak"),
			PURPLE_CRYSTAL_SAPLING = new BlockVariant(4, "purple_crystal");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", BLUE_SKYROOT_SAPLING, GREEN_SKYROOT_SAPLING, DARK_BLUE_SKYROOT_SAPLING,
			GOLDEN_OAK_SAPLING, PURPLE_CRYSTAL_SAPLING);

	public static final PropertyInteger PROPERTY_STAGE = PropertyInteger.create("stage", 0, 1);

	public BlockAetherSapling()
	{
		super(Material.plants);

		this.setStepSound(Block.soundTypeGrass);

		this.setBlockBounds(0.1f, 0.0F, 0.1f, 0.9f, 0.8f, 0.9f);

		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, BLUE_SKYROOT_SAPLING)
				.withProperty(PROPERTY_STAGE, 0));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
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

	public void generateTree(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (TerrainGen.saplingGrowTree(world, random, pos))
		{
			int meta = ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();

			WorldGenerator treeGenerator = null;

			if (meta == BLUE_SKYROOT_SAPLING.getMeta())
			{
				treeGenerator = new WorldGenSkyrootTree(BlocksAether.skyroot_log, BlocksAether.blue_skyroot_leaves, 0, 4);
			}
			else if (meta == GREEN_SKYROOT_SAPLING.getMeta())
			{
				treeGenerator = new WorldGenSkyrootTree(BlocksAether.skyroot_log, BlocksAether.green_skyroot_leaves, 0, 4);
			}

			if (treeGenerator != null)
			{
				world.setBlockToAir(pos);

				treeGenerator.generate(world, random, pos);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta & 7))
				.withProperty(PROPERTY_STAGE, (meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta() | ((Integer) state.getValue(PROPERTY_STAGE) << 3);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT, PROPERTY_STAGE);
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
	{
		return world.rand.nextFloat() < 0.45f;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		if ((Integer) state.getValue(PROPERTY_STAGE) == 0)
		{
			world.setBlockState(pos, state.cycleProperty(PROPERTY_STAGE), 4);
		}
		else
		{
			this.generateTree(world, pos, state, rand);
		}
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
