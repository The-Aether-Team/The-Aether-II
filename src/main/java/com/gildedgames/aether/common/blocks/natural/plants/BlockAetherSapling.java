package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.world.templates.TemplatePlacer;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.baking.BakedBlueprint;
import com.gildedgames.orbis_api.core.util.BlueprintPlacer;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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

public class BlockAetherSapling extends BlockAetherPlant implements IGrowable, IBlockMultiName
{
	public static final BlockVariant
			BLUE_SKYROOT = new BlockVariant(0, "blue_skyroot"),
			GREEN_SKYROOT = new BlockVariant(1, "green_skyroot"),
			DARK_BLUE_SKYROOT = new BlockVariant(2, "dark_blue_skyroot"),
			GOLDEN_OAK = new BlockVariant(3, "golden_oak"),
			MUTANT_TREE = new BlockVariant(4, "mutant_tree"); // Edison's Crazy Mutant Tree (christmas exclusive).

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", BLUE_SKYROOT, GREEN_SKYROOT, DARK_BLUE_SKYROOT, GOLDEN_OAK, MUTANT_TREE);

	public static final PropertyInteger PROPERTY_STAGE = PropertyInteger.create("stage", 0, 1);

	private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);

	public BlockAetherSapling()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, BLUE_SKYROOT).withProperty(PROPERTY_STAGE, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		return SAPLING_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
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

	public void generateTree(final World world, final BlockPos pos, final IBlockState state, final Random random)
	{
		if (TerrainGen.saplingGrowTree(world, random, pos))
		{
			final int meta = state.getValue(PROPERTY_VARIANT).getMeta();

			// TODO: Change all templates to blueprints.

			TemplateDefinition tree = null;
			BlueprintDefinition treeBp = null;

			if (meta == BLUE_SKYROOT.getMeta())
			{
				tree = GenerationAether.blue_skyroot_tree.getRandomDefinition(random);
			}
			else if (meta == GREEN_SKYROOT.getMeta())
			{
				tree = GenerationAether.green_skyroot_tree.getRandomDefinition(random);
			}
			else if (meta == GOLDEN_OAK.getMeta())
			{
				tree = GenerationAether.golden_oak.getRandomDefinition(random);
			}
			else if (meta == DARK_BLUE_SKYROOT.getMeta())
			{
				tree = GenerationAether.dark_blue_skyroot_oak.getRandomDefinition(random);
			}
			else if (meta == MUTANT_TREE.getMeta())
			{
				treeBp = GenerationAether.CRAZY_MUTANT_TREE;
			}

			if (tree != null)
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

				if (!TemplatePlacer.place(world, tree, new TemplateLoc().set(pos).set(true), random))
				{
					world.setBlockState(pos, state, 4);
				}
			}
			if (treeBp != null)
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
				BakedBlueprint baked = new BakedBlueprint(GenerationAether.CRAZY_MUTANT_TREE.getData(), new CreationData(world).pos(BlockPos.ORIGIN));
				baked.bake();
				if (!BlueprintPlacer.place(world, baked, treeBp.getConditions(), pos, true))
				{
					world.setBlockState(pos, state, 4);
				}
			}
		}
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta & 7)).withProperty(PROPERTY_STAGE,
				(meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta() | (state.getValue(PROPERTY_STAGE) << 3);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_STAGE);
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
	public void grow(final World world, final Random rand, final BlockPos pos, final IBlockState state)
	{
		if (state.getValue(PROPERTY_STAGE) == 0)
		{
			world.setBlockState(pos, state.cycleProperty(PROPERTY_STAGE), 4);
		}
		else
		{
			this.generateTree(world, pos, state, rand);
		}
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
