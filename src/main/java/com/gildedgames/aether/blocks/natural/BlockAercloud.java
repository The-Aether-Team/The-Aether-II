package com.gildedgames.aether.blocks.natural;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.blocks.util.blockstates.PropertyVariant;
import com.gildedgames.aether.creativetabs.AetherCreativeTabs;

public class BlockAercloud extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
	COLD_AERCLOUD = new BlockVariant(0, "aercloud_cold"),
	BLUE_AERCLOUD = new BlockVariant(1, "aercloud_blue"),
	GREEN_AERCLOUD = new BlockVariant(2, "aercloud_green"),
	GOLDEN_AERCLOUD = new BlockVariant(3, "aercloud_golden"),
	STORM_AERCLOUD = new BlockVariant(4, "aercloud_storm"),
	PURPLE_AERCLOUD = new BlockVariant(5, "aercloud_purple");

	public static final PropertyVariant AERCLOUD_TYPE = PropertyVariant.create("variant", COLD_AERCLOUD, BLUE_AERCLOUD, GREEN_AERCLOUD, GOLDEN_AERCLOUD, STORM_AERCLOUD, PURPLE_AERCLOUD);

	public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockAercloud()
	{
		super(Material.ice);
		this.setStepSound(Block.soundTypeCloth);
		this.setHardness(0.2f);
		this.setLightOpacity(1);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(AERCLOUD_TYPE, COLD_AERCLOUD).withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : AERCLOUD_TYPE.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		BlockPos otherBlock = pos.offset(side.getOpposite());

		if (worldIn.getBlockState(otherBlock).getBlock() == worldIn.getBlockState(pos).getBlock())
		{
			if (worldIn.getBlockState(otherBlock).getValue(AERCLOUD_TYPE) == worldIn.getBlockState(pos).getValue(AERCLOUD_TYPE))
			{
				return false;
			}
		}

		return super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		BlockVariant variant = (BlockVariant) state.getValue(AERCLOUD_TYPE);

		entityIn.fallDistance = 0;

		if (!entityIn.isSneaking())
		{
			if (variant == BLUE_AERCLOUD)
			{
				entityIn.motionY = 2.0D;
				return;
			}
			else if (variant == GOLDEN_AERCLOUD)
			{
				entityIn.motionY = -1.5D;
				return;
			}
			else if (variant == GREEN_AERCLOUD)
			{
				EnumFacing randomSide = EnumFacing.random(worldIn.rand);

				entityIn.motionX = randomSide.getFrontOffsetX() * 2.5D;
				entityIn.motionZ = randomSide.getFrontOffsetZ() * 2.5D;

				return;
			}
			else if (variant == PURPLE_AERCLOUD)
			{
				EnumFacing side = (EnumFacing) state.getValue(FACING);

				entityIn.motionX = side.getFrontOffsetX() * 2.5D;
				entityIn.motionZ = side.getFrontOffsetZ() * 2.5D;

				return;
			}
		}

		if (entityIn.motionY < 0)
		{
			entityIn.motionY *= 0.005D;
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + this.maxX, pos.getY(), pos.getZ() + this.maxZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= PURPLE_AERCLOUD.getMeta())
		{
			return this.getDefaultState().withProperty(AERCLOUD_TYPE, PURPLE_AERCLOUD).withProperty(FACING, EnumFacing.getFront(meta - PURPLE_AERCLOUD.getMeta()));
		}

		return this.getDefaultState().withProperty(AERCLOUD_TYPE, AERCLOUD_TYPE.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(AERCLOUD_TYPE) == PURPLE_AERCLOUD)
		{
			return PURPLE_AERCLOUD.getMeta() + ((EnumFacing) state.getValue(FACING)).getIndex();
		}

		return ((BlockVariant) state.getValue(AERCLOUD_TYPE)).getMeta();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(AERCLOUD_TYPE)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { AERCLOUD_TYPE, FACING });
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return AERCLOUD_TYPE.getVariantFromMeta(stack.getMetadata()).getName();
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(this, 1, ((BlockVariant) state.getValue(AERCLOUD_TYPE)).getMeta());
	}

}
