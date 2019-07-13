package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockAercloud extends Block
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

	private static final VoxelShape AERCLOUD_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);

	public BlockAercloud(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, COLD_AERCLOUD).with(PROPERTY_FACING, Direction.NORTH));
	}

	public static BlockState getAercloudState(final BlockVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().with(BlockAercloud.PROPERTY_VARIANT, variant);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void onFallenUpon(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance)
	{
		entityIn.fallDistance = 0;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		entity.fallDistance = 0;

		final boolean canCollide = !entity.isSneaking() && !(entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.isFlying);

		final BlockVariant variant = state.get(PROPERTY_VARIANT);

		Vec3d motion = entity.getMotion();

		if (!canCollide || variant == COLD_AERCLOUD || variant == STORM_AERCLOUD)
		{
			if (motion.getY() < 0.0D)
			{
				entity.setMotion(motion.getX(), motion.getY() * 0.005D, motion.getZ());
			}
		}
		else if (variant == BLUE_AERCLOUD)
		{
			if (entity.world.isRemote())
			{
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), new SoundEvent(AetherCore.getResource("block.aercloud.bounce")), SoundCategory.BLOCKS, 0.8f,
						0.9f + (world.rand.nextFloat() * 0.2f), false);

				for (int i = 0; i < 50; i++)
				{
					final double x = pos.getX() + world.rand.nextDouble();
					final double y = pos.getY() + 1.0D;
					final double z = pos.getZ() + world.rand.nextDouble();

					world.addParticle(ParticleTypes.SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}

			entity.setMotion(motion.getX(), 1.2D, motion.getZ());
		}
		else if (variant == GREEN_AERCLOUD || variant == PURPLE_AERCLOUD)
		{
			final Direction facing = variant == GREEN_AERCLOUD ? Direction.random(world.rand) : state.get(PROPERTY_FACING);

			entity.setMotion(facing.getXOffset() * 2.5D, motion.getY(), facing.getZOffset() * 2.5D);
		}
		else if (variant == GOLDEN_AERCLOUD)
		{
			entity.setMotion(motion.getX(), -1.2D, motion.getZ());
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(final BlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (state.get(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			final float x = pos.getX() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float y = pos.getY() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float z = pos.getZ() + (rand.nextFloat() * 0.7f) + 0.15f;

			final Direction facing = state.get(PROPERTY_FACING);

			final float motionX = facing.getXOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);
			final float motionZ = facing.getZOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);

			world.addParticle(ParticleTypes.CLOUD, x, y, z, motionX, 0, motionZ);
		}
	}

//	@Override
//	public AxisAlignedBB getCollisionBoundingBox(final BlockState state, final IBlockReader world, final BlockPos pos)
//	{
//		final BlockVariant variant = state.get(PROPERTY_VARIANT);
//
//		if (variant == BlockAercloud.COLD_AERCLOUD || variant == BlockAercloud.STORM_AERCLOUD)
//		{
//			return BlockAercloud.AERCLOUD_AABB;
//		}
//
//		return Block.NULL_AABB;
//	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, PROPERTY_FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(PROPERTY_FACING, context.getPlacementHorizontalFacing().getOpposite());

	}

}
