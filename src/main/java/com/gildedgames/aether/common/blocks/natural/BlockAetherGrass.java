package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.world.dimensions.aether.features.trees.WorldGenOrangeTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockAetherGrass extends BlockGrass implements IBlockVariants
{
	public static final BlockVariant AETHER = new BlockVariant(0, "normal"),
			ENCHANTED = new BlockVariant(1, "enchanted"),
			KURA = new BlockVariant(2, "kura"),
			FROSTROOT = new BlockVariant(3, "frostroot"),
			BLIGHTED = new BlockVariant(4, "blighted");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER, ENCHANTED);

	public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	public BlockAetherGrass()
	{
		super();

		this.setSoundType(SoundType.PLANT);

		this.setHardness(0.5F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, AETHER).withProperty(SNOWY, Boolean.FALSE));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos.up()).getBlock();

		return state.withProperty(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote && state.getValue(PROPERTY_VARIANT) == AETHER)
		{
			if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getLightFromNeighbors(pos.up()) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						BlockPos randomNeighbor = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (randomNeighbor.getY() >= 0 && randomNeighbor.getY() < 256 && !world.isBlockLoaded(randomNeighbor))
						{
							return;
						}

						IBlockState aboveState = world.getBlockState(randomNeighbor.up());
						IBlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt
								&& neighborState.getValue(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4
								&& aboveState.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							IBlockState grassState = this.getDefaultState().withProperty(PROPERTY_VARIANT, AETHER);

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		int damage = BlockAetherDirt.DIRT.getMeta();

		if (state.getValue(PROPERTY_VARIANT).getMeta() == FROSTROOT.getMeta())
		{
			damage = BlockAetherDirt.PERMAFROST.getMeta();
		}

		return damage;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this, 1, state.getValue(PROPERTY_VARIANT).getMeta());
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SNOWY, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		int count = 0;

		while (count < 128)
		{
			BlockPos nextPos = pos.up();
			int grassCount = 0;

			while (true)
			{
				if (grassCount < count / 16)
				{
					nextPos = nextPos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

					if (worldIn.getBlockState(nextPos.down()).getBlock() == BlocksAether.aether_grass &&
							!worldIn.getBlockState(nextPos).isNormalCube())
					{
						grassCount++;

						continue;
					}
				}
				else if (worldIn.isAirBlock(nextPos))
				{
					if (rand.nextInt(50) == 0)
					{
						if (rand.nextInt(2) == 0 && BlocksAether.orange_tree.canPlaceBlockAt(worldIn, nextPos))
						{
							WorldGenOrangeTree orangeTree = new WorldGenOrangeTree();

							orangeTree.generate(worldIn, rand, nextPos);
						}
						else if (BlocksAether.blueberry_bush.canPlaceBlockAt(worldIn, nextPos))
						{
							worldIn.setBlockState(nextPos, BlocksAether.blueberry_bush.getDefaultState().withProperty(BlockBlueberryBush.PROPERTY_HARVESTABLE,
									rand.nextInt(3) == 0));
						}
					}
					else if (rand.nextInt(8) == 0 && BlocksAether.aether_flower.canPlaceBlockAt(worldIn, nextPos))
					{
						int randFlower = rand.nextInt(3);

						if (randFlower >= 2)
						{
							worldIn.setBlockState(nextPos, BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE));
						}
						else
						{
							worldIn.setBlockState(nextPos, BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER));
						}
					}
					else
					{
						IBlockState nextState = BlocksAether.tall_aether_grass.getDefaultState();

						if (BlocksAether.tall_aether_grass.canPlaceBlockAt(worldIn, nextPos))
						{
							worldIn.setBlockState(nextPos, nextState, 3);
						}
					}
				}

				++count;
				break;
			}
		}
	}

	@Override
	public void addItemsToCreativeTab(Item item, CreativeTabs tab, List<ItemStack> stackList)
	{

	}

}
