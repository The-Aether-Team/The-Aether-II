package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/*
 * Current state only allows growth through bonemeal.
 */

public class BlockValkyrieGrass extends BlockAetherPlant implements IBlockMultiName, IGrowable
{
	public static final BlockVariant SPROUT = new BlockVariant(0, "sprout"),
			MID = new BlockVariant(1, "mid"),
			FULL = new BlockVariant(2, "full");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SPROUT, MID, FULL);

	public static final BooleanProperty PROPERTY_HARVESTABLE = BooleanProperty.create("harvestable");

	private static final AxisAlignedBB GRASS_SHORT_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final AxisAlignedBB GRASS_NORMAL_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final AxisAlignedBB GRASS_LONG_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	private static final int VALKYRIE_GRASS_SPROUT = 0, VALKYRIE_GRASS_MID = 1, VALKYRIE_GRASS_FULL = 2;

	public BlockValkyrieGrass()
	{
		super(Material.PLANTS);
		this.setHardness(0.0f);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		// default state is set to fully grown and harvest-able because of odd activity when a world is loaded
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, FULL).withProperty(PROPERTY_HARVESTABLE, true));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_HARVESTABLE);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public BlockState getStateForPlacement(final World worldIn, final BlockPos pos, final Direction facing, final float hitX, final float hitY,
			final float hitZ, final int meta, final LivingEntity placer)
	{
		if (placer.getHeldItemMainhand().getMetadata() == VALKYRIE_GRASS_FULL)
		{
			return this.getStateFromMeta(meta).withProperty(PROPERTY_HARVESTABLE, true).withProperty(PROPERTY_VARIANT, FULL);
		}
		return this.getStateFromMeta(meta).withProperty(PROPERTY_HARVESTABLE, false).withProperty(PROPERTY_VARIANT, SPROUT);
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return !state.getValue(PROPERTY_HARVESTABLE);
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final BlockState state, final Random rand)
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
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		// if kirrid grass is anything but fully grown.
		if (!state.getValue(PROPERTY_HARVESTABLE))
		{
			// if sprout, grow to mid.
			if (worldIn.getBlockState(pos).getValue(PROPERTY_VARIANT).getMeta() == VALKYRIE_GRASS_SPROUT)
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_VARIANT, BlockValkyrieGrass.MID));
			}
			// if mid, grow to full and set harvestable.
			else if (worldIn.getBlockState(pos).getValue(PROPERTY_VARIANT).getMeta() == VALKYRIE_GRASS_MID)
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, true).withProperty(PROPERTY_VARIANT, BlockValkyrieGrass.FULL));
			}
		}
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return VALKYRIE_GRASS_SPROUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final BlockState state, final IBlockReader source, final BlockPos pos)
	{
		if (state.getValue(PROPERTY_VARIANT) == SPROUT)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == MID)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == FULL)
		{
			return GRASS_LONG_AABB;
		}

		return super.getBoundingBox(state, source, pos);
	}

	@Override
	public boolean removedByPlayer(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final boolean willHarvest)
	{
		if (state.getValue(PROPERTY_HARVESTABLE))
		{
			if (player.isCreative())
			{
				world.setBlockToAir(pos);

				return false;
			}

			if (!world.isRemote)
			{
				final Random random = new Random();
				Block.spawnAsEntity(world, pos, new ItemStack(ItemsAether.valkyrie_wings));

				// randomly spawn a kirrid grass sprout
				if (random.nextInt(3) == 1)
				{
					Block.spawnAsEntity(world, pos, new ItemStack(BlocksAether.valkyrie_grass));
				}
			}

			world.setBlockState(pos, state.withProperty(PROPERTY_HARVESTABLE, false).withProperty(PROPERTY_VARIANT, SPROUT));

			return false;

		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			// No reason to have the mid variant (as an option) in creative inventory.
			if (variant != MID)
			{
				list.add(new ItemStack(this, 1, variant.getMeta()));
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}
}
