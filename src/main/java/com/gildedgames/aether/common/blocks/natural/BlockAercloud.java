package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAercloud extends Block implements IBlockMultiName
{
	public static final BlockVariant
			COLD_AERCLOUD = new BlockVariant(0, "cold"),
			BLUE_AERCLOUD = new BlockVariant(1, "blue"),
			GREEN_AERCLOUD = new BlockVariant(2, "green"),
			GOLDEN_AERCLOUD = new BlockVariant(3, "golden"),
			STORM_AERCLOUD = new BlockVariant(4, "storm"),
			PURPLE_AERCLOUD = new BlockVariant(5, "purple");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant
			.create("variant", COLD_AERCLOUD, BLUE_AERCLOUD, GREEN_AERCLOUD, GOLDEN_AERCLOUD, STORM_AERCLOUD, PURPLE_AERCLOUD);

	public static final PropertyEnum<EnumFacing> PROPERTY_FACING = PropertyEnum.create("facing", EnumFacing.class);

	private static final AxisAlignedBB AERCLOUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);

	public BlockAercloud()
	{
		super(Material.ICE);

		this.setSoundType(SoundType.CLOTH);

		this.setHardness(0.2f);
		this.setLightOpacity(0);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, COLD_AERCLOUD).withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	public static IBlockState getAercloudState(final BlockVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.PROPERTY_VARIANT, variant);
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side)
	{
		final IBlockState offsetState = world.getBlockState(pos.offset(side));

		final Block block = offsetState.getBlock();

		if (block == this)
		{
			if (state.getValue(PROPERTY_VARIANT) != offsetState.getValue(PROPERTY_VARIANT))
			{
				return true;
			}
		}

		return block != this && super.shouldSideBeRendered(state, world, pos, side);
	}

	@Override
	public void onFallenUpon(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance)
	{
		entityIn.fallDistance = 0;
	}

	@Override
	public void onEntityCollidedWithBlock(final World world, final BlockPos pos, final IBlockState state, final Entity entity)
	{
		entity.fallDistance = 0;

		final boolean canCollide = !entity.isSneaking() && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying);

		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (!canCollide || variant == COLD_AERCLOUD || variant == STORM_AERCLOUD)
		{
			if (entity.motionY < 0.0D)
			{
				entity.motionY *= 0.005D;
			}
		}
		else if (variant == BLUE_AERCLOUD)
		{
			if (entity.world.isRemote)
			{
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsAether.aercloud_bounce, SoundCategory.BLOCKS, 0.8f,
						0.9f + (world.rand.nextFloat() * 0.2f), false);

				for (int i = 0; i < 50; i++)
				{
					final double x = pos.getX() + world.rand.nextDouble();
					final double y = pos.getY() + 1.0D;
					final double z = pos.getZ() + world.rand.nextDouble();

					world.spawnParticle(EnumParticleTypes.WATER_SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}

			entity.motionY = 1.2D;
		}
		else if (variant == GREEN_AERCLOUD || variant == PURPLE_AERCLOUD)
		{
			final EnumFacing facing = variant == GREEN_AERCLOUD ? EnumFacing.random(world.rand) : state.getValue(PROPERTY_FACING);

			entity.motionX = facing.getFrontOffsetX() * 2.5D;
			entity.motionZ = facing.getFrontOffsetZ() * 2.5D;
		}
		else if (variant == GOLDEN_AERCLOUD)
		{
			entity.motionY = -1.2D;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final IBlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (state.getValue(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			final float x = pos.getX() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float y = pos.getY() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float z = pos.getZ() + (rand.nextFloat() * 0.7f) + 0.15f;

			final EnumFacing facing = state.getValue(PROPERTY_FACING);

			final float motionX = facing.getFrontOffsetX() * ((rand.nextFloat() * 0.01f) + 0.05f);
			final float motionZ = facing.getFrontOffsetZ() * ((rand.nextFloat() * 0.01f) + 0.05f);

			world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, motionX, 0, motionZ);
		}
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final IBlockState state, final IBlockAccess world, final BlockPos pos)
	{
		final BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAercloud.COLD_AERCLOUD || variant == BlockAercloud.STORM_AERCLOUD)
		{
			return BlockAercloud.AERCLOUD_AABB;
		}

		return Block.NULL_AABB;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		if (meta >= PURPLE_AERCLOUD.getMeta())
		{
			return this.getDefaultState().withProperty(PROPERTY_VARIANT, PURPLE_AERCLOUD)
					.withProperty(PROPERTY_FACING, EnumFacing.getHorizontal(meta - PURPLE_AERCLOUD.getMeta()));
		}

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			return PURPLE_AERCLOUD.getMeta() + state.getValue(PROPERTY_FACING).getHorizontalIndex();
		}

		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_FACING);
	}

	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY,
			final float hitZ, final int meta,
			final EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());

	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
