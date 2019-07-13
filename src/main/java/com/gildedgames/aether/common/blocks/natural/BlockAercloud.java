package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

	public static final EnumProperty<Direction> PROPERTY_FACING = EnumProperty.create("facing", Direction.class);

	private static final AxisAlignedBB AERCLOUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);

	public BlockAercloud(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_VARIANT, COLD_AERCLOUD).with(PROPERTY_FACING, Direction.NORTH));
	}

	public static BlockState getAercloudState(final BlockVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().with(BlockAercloud.PROPERTY_VARIANT, variant);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(final BlockState state, final IBlockReader world, final BlockPos pos, final Direction side)
	{
		final BlockState offsetState = world.getBlockState(pos.offset(side));

		final Block block = offsetState.getBlock();

		if (block == this)
		{
			if (state.get(PROPERTY_VARIANT) != offsetState.getValue(PROPERTY_VARIANT))
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
	public void onEntityCollision(final World world, final BlockPos pos, final BlockState state, final Entity entity)
	{
		entity.fallDistance = 0;

		final boolean canCollide = !entity.isSneaking() && !(entity instanceof PlayerEntity && ((PlayerEntity) entity).capabilities.isFlying);

		final BlockVariant variant = state.get(PROPERTY_VARIANT);

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
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), new SoundEvent(AetherCore.getResource("block.aercloud.bounce")), SoundCategory.BLOCKS, 0.8f,
						0.9f + (world.rand.nextFloat() * 0.2f), false);

				for (int i = 0; i < 50; i++)
				{
					final double x = pos.getX() + world.rand.nextDouble();
					final double y = pos.getY() + 1.0D;
					final double z = pos.getZ() + world.rand.nextDouble();

					world.spawnParticle(ParticleTypes.WATER_SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}

			entity.motionY = 1.2D;
		}
		else if (variant == GREEN_AERCLOUD || variant == PURPLE_AERCLOUD)
		{
			final Direction facing = variant == GREEN_AERCLOUD ? Direction.random(world.rand) : state.get(PROPERTY_FACING);

			entity.motionX = facing.getXOffset() * 2.5D;
			entity.motionZ = facing.getZOffset() * 2.5D;
		}
		else if (variant == GOLDEN_AERCLOUD)
		{
			entity.motionY = -1.2D;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(final BlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (state.get(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			final float x = pos.getX() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float y = pos.getY() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float z = pos.getZ() + (rand.nextFloat() * 0.7f) + 0.15f;

			final Direction facing = state.get(PROPERTY_FACING);

			final float motionX = facing.getXOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);
			final float motionZ = facing.getZOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);

			world.spawnParticle(ParticleTypes.CLOUD, x, y, z, motionX, 0, motionZ);
		}
	}

	@Override
	public boolean isOpaqueCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(final BlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(final IBlockReader world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final BlockState state, final IBlockReader world, final BlockPos pos)
	{
		final BlockVariant variant = state.get(PROPERTY_VARIANT);

		if (variant == BlockAercloud.COLD_AERCLOUD || variant == BlockAercloud.STORM_AERCLOUD)
		{
			return BlockAercloud.AERCLOUD_AABB;
		}

		return Block.NULL_AABB;
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		if (meta >= PURPLE_AERCLOUD.getMeta())
		{
			return this.getDefaultState().with(PROPERTY_VARIANT, PURPLE_AERCLOUD)
					.with(PROPERTY_FACING, Direction.byHorizontalIndex(meta - PURPLE_AERCLOUD.getMeta()));
		}

		return this.getDefaultState().with(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		if (state.get(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			return PURPLE_AERCLOUD.getMeta() + state.get(PROPERTY_FACING).getHorizontalIndex();
		}

		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.get(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, PROPERTY_FACING);
	}

	@Override
	public BlockState getStateForPlacement(final World world, final BlockPos pos, final Direction facing, final float hitX, final float hitY,
			final float hitZ, final int meta,
			final LivingEntity placer)
	{
		return this.getStateFromMeta(meta).with(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());

	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

}
