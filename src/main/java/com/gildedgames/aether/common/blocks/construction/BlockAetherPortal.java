package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.client.renderer.particles.ParticleAetherPortal;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockAetherPortal extends BreakableBlock
{
	public static final EnumProperty<Direction.Axis> PROPERTY_AXIS = EnumProperty.create("axis", Direction.Axis.class,
			Direction.Axis.X, Direction.Axis.Z);

	protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
			Z_AABB = Block.makeCuboidShape(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
			Y_AABB = Block.makeCuboidShape(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public BlockAetherPortal(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_AXIS, Direction.Axis.X));
	}

	public static int getMetaForAxis(Direction.Axis axis)
	{
		return axis == Direction.Axis.X ? 1 : (axis == Direction.Axis.Z ? 2 : 0);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch (state.get(PROPERTY_AXIS))
		{
			case X:
				return X_AABB;
			case Y:
			default:
				return Y_AABB;
			case Z:
				return Z_AABB;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (rand.nextInt(150) == 0)
		{
			world.playSound(
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D, new SoundEvent(AetherCore.getResource("portal.glowstone.hum")), SoundCategory.BLOCKS, 0.2F,
					(rand.nextFloat() * 0.2F) + 0.9F, false);
		}

		for (int count = 0; count < 4; count++)
		{
			double posX = pos.getX() + rand.nextFloat();
			double posY = pos.getY() + rand.nextFloat();
			double posZ = pos.getZ() + rand.nextFloat();
			double motionX = (rand.nextFloat() - 0.5D) * 0.5D;
			double motionY = (rand.nextFloat() - 0.5D) * 0.5D;
			double motionZ = (rand.nextFloat() - 0.5D) * 0.5D;

			int angle = rand.nextInt(2) * 2 - 1;

			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this)
			{
				posX = pos.getX() + 0.5D + 0.25D * angle;
				motionX = (rand.nextFloat() * 2.0F * angle);
			}
			else
			{
				posZ = pos.getZ() + 0.5D + 0.25D * angle;
				motionZ = (rand.nextFloat() * 2.0F * angle);
			}

			ParticleAetherPortal effect = new ParticleAetherPortal(world, posX, posY, posZ, motionX, motionY, motionZ);
			Minecraft.getInstance().particles.addEffect(effect);
		}
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity)
	{
		if (entity instanceof PlayerEntity)
		{
			PlayerAether playerAether = PlayerAether.getPlayer((PlayerEntity) entity);
			playerAether.getModule(PlayerTeleportingModule.class).processTeleporting();
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.get(PROPERTY_AXIS))
				{
					case X:
						return state.with(PROPERTY_AXIS, Direction.Axis.Z);
					case Z:
						return state.with(PROPERTY_AXIS, Direction.Axis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		final Direction.Axis axis = state.get(PROPERTY_AXIS);

		if (axis == Direction.Axis.X || axis == Direction.Axis.Z)
		{
			final BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, axis);

			if (!size.isWithinSizeBounds() || size.portalBlocks < size.height * size.width)
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_AXIS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public static class Size
	{
		private final World world;

		private final Direction.Axis axis;

		private final Direction rightSideFacing;

		private final Direction leftSideFacing;

		private BlockPos portalPos;

		private int portalBlocks = 0;

		private int height;

		private int width;

		public Size(World world, BlockPos pos, Direction.Axis axis)
		{
			this.world = world;
			this.axis = axis;

			if (axis == Direction.Axis.X)
			{
				this.leftSideFacing = Direction.EAST;
				this.rightSideFacing = Direction.WEST;
			}
			else
			{
				this.leftSideFacing = Direction.NORTH;
				this.rightSideFacing = Direction.SOUTH;
			}

			final BlockPos offsetPos = pos;

			while (pos.getY() > offsetPos.getY() - 21 && pos.getY() > 0 && this.isBlockSuitable(world.getBlockState(pos.down())))
			{
				pos = pos.down();
			}

			final int x = this.getWidth(pos, this.leftSideFacing) - 1;

			if (x >= 0)
			{
				this.portalPos = pos.offset(this.leftSideFacing, x);
				this.width = this.getWidth(this.portalPos, this.rightSideFacing);

				if (this.width < 2 || this.width > 21)
				{
					this.portalPos = null;
					this.width = 0;
				}
			}

			if (this.portalPos != null)
			{
				this.height = this.getHeight();
			}
		}

		private int getWidth(BlockPos pos, Direction facing)
		{
			int x;

			for (x = 0; x < 22; ++x)
			{
				final BlockPos offsetPos = pos.offset(facing, x);

				if (!this.isBlockSuitable(this.world.getBlockState(offsetPos))
						|| !this.isBlockValidFrame(this.world.getBlockState(offsetPos.down())))
				{
					break;
				}
			}

			final BlockState state = this.world.getBlockState(pos.offset(facing, x));
			return this.isBlockValidFrame(state) ? x : 0;
		}

		private int getHeight()
		{
			int x;
			loop:

			for (this.height = 0; this.height < 21; ++this.height)
			{
				for (x = 0; x < this.width; ++x)
				{
					final BlockPos blockpos = this.portalPos.offset(this.rightSideFacing, x).up(this.height);
					BlockState state = this.world.getBlockState(blockpos);

					if (!this.isBlockSuitable(state))
					{
						break loop;
					}

					if (state.getBlock() == BlocksAether.aether_portal)
					{
						++this.portalBlocks;
					}

					if (x == 0)
					{
						state = this.world.getBlockState(blockpos.offset(this.leftSideFacing));

						if (!this.isBlockValidFrame(state))
						{
							break loop;
						}
					}
					else if (x == this.width - 1)
					{
						state = this.world.getBlockState(blockpos.offset(this.rightSideFacing));

						if (!this.isBlockValidFrame(state))
						{
							break loop;
						}
					}
				}
			}

			for (x = 0; x < this.width; ++x)
			{
				if (!this.isBlockValidFrame(this.world.getBlockState(this.portalPos.offset(this.rightSideFacing, x).up(this.height))))
				{
					this.height = 0;
					break;
				}
			}

			if (this.height <= 21 && this.height >= 3)
			{
				return this.height;
			}
			else
			{
				this.portalPos = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}

		private boolean isBlockSuitable(BlockState state)
		{
			return state.getMaterial() == Material.AIR || state.getBlock() == Blocks.WATER
					|| state.getBlock() == BlocksAether.aether_portal;
		}

		public boolean isWithinSizeBounds()
		{
			return this.portalPos != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}

		public int getPortalBlocks()
		{
			return this.portalBlocks;
		}

		private boolean isBlockValidFrame(BlockState state)
		{
			return state.getBlock() == Blocks.GLOWSTONE || state.getBlock() == Blocks.QUARTZ_BLOCK;
		}

		public void createPortal()
		{
			for (int i = 0; i < this.width; ++i)
			{
				final BlockPos blockpos = this.portalPos.offset(this.rightSideFacing, i);

				for (int j = 0; j < this.height; ++j)
				{
					this.world.setBlockState(blockpos.up(j),
							BlocksAether.aether_portal.getDefaultState().with(BlockAetherPortal.PROPERTY_AXIS, this.axis), 2);
				}
			}
		}
	}
}
