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
import net.minecraft.item.Item;
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
	private static final AxisAlignedBB AERCLOUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);

	public static final BlockVariant
			COLD_AERCLOUD = new BlockVariant(0, "cold"),
			BLUE_AERCLOUD = new BlockVariant(1, "blue"),
			GREEN_AERCLOUD = new BlockVariant(2, "green"),
			GOLDEN_AERCLOUD = new BlockVariant(3, "golden"),
			STORM_AERCLOUD = new BlockVariant(4, "storm"),
			PURPLE_AERCLOUD = new BlockVariant(5, "purple");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", COLD_AERCLOUD, BLUE_AERCLOUD, GREEN_AERCLOUD, GOLDEN_AERCLOUD, STORM_AERCLOUD, PURPLE_AERCLOUD);

	public static final PropertyEnum<EnumFacing> PROPERTY_FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockAercloud()
	{
		super(Material.ICE);

		this.setSoundType(SoundType.CLOTH);

		this.setHardness(0.2f);
		this.setLightOpacity(0);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, COLD_AERCLOUD).withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
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
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState offsetState = world.getBlockState(pos.offset(side));

		Block block = offsetState.getBlock();

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
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		entityIn.fallDistance = 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		entity.fallDistance = 0;

		boolean canCollide = !entity.isSneaking() && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying);

		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

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
					double x = pos.getX() + world.rand.nextDouble();
					double y = pos.getY() + 1.0D;
					double z = pos.getZ() + world.rand.nextDouble();

					world.spawnParticle(EnumParticleTypes.WATER_SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}

			entity.motionY = 1.2D;
		}
		else if (variant == GREEN_AERCLOUD || variant == PURPLE_AERCLOUD)
		{
			EnumFacing facing = variant == GREEN_AERCLOUD ? EnumFacing.random(world.rand) : state.getValue(PROPERTY_FACING);

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
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (state.getValue(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			float x = pos.getX() + (rand.nextFloat() * 0.7f) + 0.15f;
			float y = pos.getY() + (rand.nextFloat() * 0.7f) + 0.15f;
			float z = pos.getZ() + (rand.nextFloat() * 0.7f) + 0.15f;

			EnumFacing facing = state.getValue(PROPERTY_FACING);

			float motionX = facing.getFrontOffsetX() * ((rand.nextFloat() * 0.01f) + 0.05f);
			float motionZ = facing.getFrontOffsetZ() * ((rand.nextFloat() * 0.01f) + 0.05f);

			world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, motionX, 0, motionZ);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		if (variant == BlockAercloud.COLD_AERCLOUD || variant == BlockAercloud.STORM_AERCLOUD)
		{
			return BlockAercloud.AERCLOUD_AABB;
		}

		return Block.NULL_AABB;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= PURPLE_AERCLOUD.getMeta())
		{
			return this.getDefaultState().withProperty(PROPERTY_VARIANT, PURPLE_AERCLOUD)
					.withProperty(PROPERTY_FACING, EnumFacing.getHorizontal(meta - PURPLE_AERCLOUD.getMeta()));
		}

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			return PURPLE_AERCLOUD.getMeta() + state.getValue(PROPERTY_FACING).getHorizontalIndex();
		}

		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_FACING);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());

	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	public IBlockState getAercloudState(BlockVariant variant)
	{
		return BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.PROPERTY_VARIANT, variant);
	}

}
