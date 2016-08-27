package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.client.renderer.particles.ParticleAetherPortal;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.DimensionsAether;
import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAetherPortal extends BlockBreakable
{
	public static final PropertyEnum<EnumFacing.Axis> PROPERTY_AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class,
			EnumFacing.Axis.X, EnumFacing.Axis.Z);

	protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
			Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
			Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public BlockAetherPortal()
	{
		super(Material.PORTAL, false);

		this.setSoundType(SoundType.GLASS);

		this.setHardness(-1.0F);
		this.setLightLevel(0.75F);

		this.setTickRandomly(true);

		this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTY_AXIS, EnumFacing.Axis.X));
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(PROPERTY_AXIS))
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

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (rand.nextInt(150) == 0)
		{
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundsAether.glowstone_portal_hum, SoundCategory.BLOCKS, 0.2F, (rand.nextFloat() * 0.2F) + 0.9F, false);
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
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(effect);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		EnumFacing.Axis axis = null;
		final IBlockState state = world.getBlockState(pos);

		if (world.getBlockState(pos).getBlock() == this)
		{
			axis = state.getValue(PROPERTY_AXIS);

			if (axis == null)
			{
				return false;
			}

			if (axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
			{
				return false;
			}

			if (axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
			{
				return false;
			}
		}

		final boolean westFlag = world.getBlockState(pos.west()).getBlock() == this && world.getBlockState(pos.west(2)).getBlock() != this;
		final boolean eastFlag = world.getBlockState(pos.east()).getBlock() == this && world.getBlockState(pos.east(2)).getBlock() != this;
		final boolean northFlag = world.getBlockState(pos.north()).getBlock() == this && world.getBlockState(pos.north(2)).getBlock() != this;
		final boolean southFlag = world.getBlockState(pos.south()).getBlock() == this && world.getBlockState(pos.south(2)).getBlock() != this;
		final boolean wexFlag = westFlag || eastFlag || axis == EnumFacing.Axis.X;
		final boolean nszFlag = northFlag || southFlag || axis == EnumFacing.Axis.Z;

		return wexFlag && side == EnumFacing.WEST || (wexFlag && side == EnumFacing.EAST || (nszFlag && side == EnumFacing.NORTH || nszFlag && side == EnumFacing.SOUTH));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(entity);

			playerAether.getTeleportingModule().processTeleporting();
		}
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(PROPERTY_AXIS))
				{
				case X:
					return state.withProperty(PROPERTY_AXIS, EnumFacing.Axis.Z);
				case Z:
					return state.withProperty(PROPERTY_AXIS, EnumFacing.Axis.X);
				default:
					return state;
				}

			default:
				return state;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return getMetaForAxis(state.getValue(PROPERTY_AXIS));
	}

	public static int getMetaForAxis(EnumFacing.Axis axis)
	{
		return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
	{
		final EnumFacing.Axis axis = state.getValue(PROPERTY_AXIS);

		if (axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z)
		{
			final BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, axis);

			if (!size.isWithinSizeBounds() || size.portalBlocks < size.height * size.width)
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_AXIS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public static class Size
	{
		private final World world;

		private final EnumFacing.Axis axis;

		private final EnumFacing rightSideFacing;

		private final EnumFacing leftSideFacing;

		private BlockPos portalPos;

		private int portalBlocks = 0;

		private int height;

		private int width;

		public Size(World world, BlockPos pos, EnumFacing.Axis axis)
		{
			this.world = world;
			this.axis = axis;

			if (axis == EnumFacing.Axis.X)
			{
				this.leftSideFacing = EnumFacing.EAST;
				this.rightSideFacing = EnumFacing.WEST;
			}
			else
			{
				this.leftSideFacing = EnumFacing.NORTH;
				this.rightSideFacing = EnumFacing.SOUTH;
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
				this.height = this.func_150858_a();
			}
		}

		protected int getWidth(BlockPos pos, EnumFacing facing)
		{
			int x;

			for (x = 0; x < 22; ++x)
			{
				final BlockPos offsetPos = pos.offset(facing, x);

				if (!this.isBlockSuitable(this.world.getBlockState(offsetPos)) || this.world.getBlockState(offsetPos.down()).getBlock() != Blocks.GLOWSTONE)
				{
					break;
				}
			}

			final Block block = this.world.getBlockState(pos.offset(facing, x)).getBlock();
			return block == Blocks.GLOWSTONE ? x : 0;
		}

		protected int func_150858_a()
		{
			int x;
			loop:

			for (this.height = 0; this.height < 21; ++this.height)
			{
				for (x = 0; x < this.width; ++x)
				{
					final BlockPos blockpos = this.portalPos.offset(this.rightSideFacing, x).up(this.height);
					IBlockState state = this.world.getBlockState(blockpos);

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

						if (state.getBlock() != Blocks.GLOWSTONE)
						{
							break loop;
						}
					}
					else if (x == this.width - 1)
					{
						state = this.world.getBlockState(blockpos.offset(this.rightSideFacing));

						if (state.getBlock() != Blocks.GLOWSTONE)
						{
							break loop;
						}
					}
				}
			}

			for (x = 0; x < this.width; ++x)
			{
				if (this.world.getBlockState(this.portalPos.offset(this.rightSideFacing, x).up(this.height)).getBlock() != Blocks.GLOWSTONE)
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

		protected boolean isBlockSuitable(IBlockState state)
		{
			return state.getMaterial() == Material.AIR || state.getBlock() == Blocks.WATER || state.getBlock() == BlocksAether.aether_portal;
		}

		public boolean isWithinSizeBounds()
		{
			return this.portalPos != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}

		public void createPortal()
		{
			for (int i = 0; i < this.width; ++i)
			{
				final BlockPos blockpos = this.portalPos.offset(this.rightSideFacing, i);

				for (int j = 0; j < this.height; ++j)
				{
					this.world.setBlockState(blockpos.up(j), BlocksAether.aether_portal.getDefaultState().withProperty(BlockAetherPortal.PROPERTY_AXIS, this.axis), 2);
				}
			}
		}

		public int getPortalBlocks()
		{
			return this.portalBlocks;
		}
	}
}
