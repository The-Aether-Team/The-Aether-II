package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Current state only allows growth through bonemeal.
 */

public class BlockKirridGrass extends BlockAetherPlant implements IBlockMultiName, IGrowable
{
	private static final int KIRRID_GRASS_SPROUT = 0, KIRRID_GRASS_MID = 1, KIRRID_GRASS_FULL = 2;

	public static final BlockVariant SPROUT = new BlockVariant(0, "sprout"),
			MID = new BlockVariant(1, "mid"),
			FULL = new BlockVariant(2, "full");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SPROUT, MID, FULL);

	public static final PropertyBool PROPERTY_HARVESTABLE = PropertyBool.create("harvestable");

	public BlockKirridGrass()
	{
		super(Material.LEAVES);
		this.setHardness(0.0f);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, SPROUT).withProperty(PROPERTY_HARVESTABLE, Boolean.FALSE));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_HARVESTABLE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!state.getValue(PROPERTY_HARVESTABLE))
		{
			if (world.getLight(pos) >= 9)
			{
				if (rand.nextInt(60) == 0)
				{
					this.grow(world, rand, pos, state);
				}
			}
		}
	}

	// called when the plant grows.
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		// if kirrid grass is anything but fully grown.
		if (!state.getValue(PROPERTY_HARVESTABLE))
		{
			// if sprout, grow to mid.
			if (worldIn.getBlockState(pos).getValue(PROPERTY_VARIANT).getMeta() == KIRRID_GRASS_SPROUT)
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_VARIANT, BlockKirridGrass.MID));
			}
			// if mid, grow to full and set harvestable.
			else if (worldIn.getBlockState(pos).getValue(PROPERTY_VARIANT).getMeta() == KIRRID_GRASS_MID)
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, true).withProperty(PROPERTY_VARIANT, BlockKirridGrass.FULL));
			}
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return KIRRID_GRASS_SPROUT;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(ItemsAether.kirrid_flower));

		return items;
	}

	/*
	 * TODO: FIX THIS
	 * This method is called 3 times for some reason, and the block is destroyed even when the PROPERTY_HARVESTABLE is set to true
	 * so essentially when the Kirrid Grass is harvestable it is set back to a sprout, then this method is called again and the block
	 * is destroyed completely. Then this is called again Server side, and the block is destroyed on the server.
	 *
	 * If video evidence, more details of the problem is needed, a video can be provided by Chris but due to this being open source
	 * I don't want to link it here.
	 */

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			if (player.capabilities.isCreativeMode)
			{
				world.setBlockToAir(pos);

				return false;
			}

			world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, false).withProperty(PROPERTY_VARIANT, SPROUT));

			return false;

		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
}
