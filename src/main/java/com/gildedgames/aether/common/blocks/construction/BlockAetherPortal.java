package com.gildedgames.aether.common.blocks.construction;

import java.util.Random;

import com.gildedgames.aether.client.renderer.effects.EntityAetherPortalFX;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAetherPortal extends BlockBreakable
{
	public static final PropertyEnum PROPERTY_AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class,
			new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z });

	public BlockAetherPortal()
	{
		super(Material.portal, false);

		this.setStepSound(soundTypeGlass);

		this.setHardness(-1.0F);
		this.setLightLevel(0.75F);

		this.setTickRandomly(true);

		this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTY_AXIS, EnumFacing.Axis.X));
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		final EnumFacing.Axis axis = (EnumFacing.Axis) world.getBlockState(pos).getValue(PROPERTY_AXIS);

		float xThickness = 0.125F, zThickness = 0.125F;

		if (axis == EnumFacing.Axis.X)
		{
			xThickness = 0.5F;
		}

		if (axis == EnumFacing.Axis.Z)
		{
			zThickness = 0.5F;
		}

		this.setBlockBounds(0.5F - xThickness, 0.0F, 0.5F - zThickness, 0.5F + xThickness, 1.0F, 0.5F + zThickness);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (rand.nextInt(100) == 0)
		{
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, AetherCore.getResourcePath("aeportal.portal"), 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int count = 0; count < 4; count++)
		{
			double posX = pos.getX() + rand.nextFloat();
			final double posY = pos.getY() + rand.nextFloat();
			double posZ = pos.getZ() + rand.nextFloat();
			double motionX = (rand.nextFloat() - 0.5D) * 0.5D;
			final double motionY = (rand.nextFloat() - 0.5D) * 0.5D;
			double motionZ = (rand.nextFloat() - 0.5D) * 0.5D;
			final int angle = rand.nextInt(2) * 2 - 1;

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

			final EntityAetherPortalFX effect = new EntityAetherPortalFX(world, posX, posY, posZ, motionX, motionY, motionZ);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(effect);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		EnumFacing.Axis axis = null;
		final IBlockState state = world.getBlockState(pos);

		if (world.getBlockState(pos).getBlock() == this)
		{
			axis = (EnumFacing.Axis) state.getValue(PROPERTY_AXIS);

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
		if (entity instanceof EntityPlayerMP)
		{
			final EntityPlayerMP player = (EntityPlayerMP) entity;
			final ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();

			final int transferToID = player.dimension == AetherCore.getAetherDimID() ? 0 : AetherCore.getAetherDimID();
			scm.transferPlayerToDimension(player, transferToID, AetherCore.getTeleporter());
		}
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		final EnumFacing.Axis axis = (EnumFacing.Axis) state.getValue(PROPERTY_AXIS);

		return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		final EnumFacing.Axis axis = (EnumFacing.Axis) state.getValue(PROPERTY_AXIS);

		if (axis == EnumFacing.Axis.X || axis == EnumFacing.Axis.Z)
		{
			final BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, axis);

			if (!size.isWithinSizeBounds() || size.field_150864_e < size.height * size.width)
			{
				world.setBlockState(pos, Blocks.air.getDefaultState());
			}
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_AXIS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	public static class Size
	{
		private final World world;

		private final EnumFacing.Axis axis;

		private final EnumFacing rightSideFacing;

		private final EnumFacing leftSideFacing;

		private BlockPos portalPos;

		// ???
		private int field_150864_e = 0;

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

			while (pos.getY() > offsetPos.getY() - 21 && pos.getY() > 0 && this.isBlockSuitable(world.getBlockState(pos.down()).getBlock()))
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

				if (!this.isBlockSuitable(this.world.getBlockState(offsetPos).getBlock()) || this.world.getBlockState(offsetPos.down()).getBlock() != Blocks.glowstone)
				{
					break;
				}
			}

			final Block block = this.world.getBlockState(pos.offset(facing, x)).getBlock();
			return block == Blocks.glowstone ? x : 0;
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
					Block block = this.world.getBlockState(blockpos).getBlock();

					if (!this.isBlockSuitable(block))
					{
						break loop;
					}

					if (block == Blocks.glowstone)
					{
						++this.field_150864_e;
					}

					if (x == 0)
					{
						block = this.world.getBlockState(blockpos.offset(this.leftSideFacing)).getBlock();

						if (block != Blocks.glowstone)
						{
							break loop;
						}
					}
					else if (x == this.width - 1)
					{
						block = this.world.getBlockState(blockpos.offset(this.rightSideFacing)).getBlock();

						if (block != Blocks.glowstone)
						{
							break loop;
						}
					}
				}
			}

			for (x = 0; x < this.width; ++x)
			{
				if (this.world.getBlockState(this.portalPos.offset(this.rightSideFacing, x).up(this.height)).getBlock() != Blocks.glowstone)
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

		protected boolean isBlockSuitable(Block block)
		{
			return block.getMaterial() == Material.air || block == Blocks.water || block == BlocksAether.aether_portal;
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

		public int get_field_150864_e()
		{
			return this.field_150864_e;
		}
	}
}
