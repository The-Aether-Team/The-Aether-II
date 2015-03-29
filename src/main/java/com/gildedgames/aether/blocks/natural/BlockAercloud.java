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

import com.gildedgames.aether.blocks.util.IAetherBlockVariant;
import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.creativetabs.AetherCreativeTabs;

public class BlockAercloud extends Block implements IAetherBlockWithVariants
{
	public enum AercloudVariant implements IAetherBlockVariant
	{
		COLD(0, "aercloud_cold"),
		BLUE(1, "aercloud_blue"),
		GREEN(2, "aercloud_green"),
		GOLDEN(3, "aercloud_golden"),
		STORM(4, "aercloud_storm"),
		PURPLE(5, "aercloud_purple");

		private static final AercloudVariant[] metaLookup = new AercloudVariant[AercloudVariant.values().length];

		static
		{
			for (AercloudVariant variant : AercloudVariant.values())
			{
				metaLookup[variant.getMetadata()] = variant;
			}
		}

		private int metadata;

		private String name;

		AercloudVariant(int metadata, String name)
		{
			this.metadata = metadata;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		@Override
		public int getMetadata()
		{
			return this.metadata;
		}

		public static AercloudVariant getVariantFromMetadata(int meta)
		{
			return AercloudVariant.metaLookup[meta];
		}
	}

	public static final PropertyEnum AERCLOUD_TYPE = PropertyEnum.create("variant", AercloudVariant.class);

	public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockAercloud()
	{
		super(Material.ice);
		this.setStepSound(Block.soundTypeCloth);
		this.setHardness(0.2f);
		this.setLightOpacity(1);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(AERCLOUD_TYPE, AercloudVariant.COLD).withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (AercloudVariant type : AercloudVariant.values())
		{
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
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
		AercloudVariant variant = (AercloudVariant) state.getValue(AERCLOUD_TYPE);

		entityIn.fallDistance = 0;

		if (!entityIn.isSneaking())
		{
			if (variant == AercloudVariant.BLUE)
			{
				entityIn.motionY = 2.0D;
				return;
			}
			else if (variant == AercloudVariant.GOLDEN)
			{
				entityIn.motionY = -1.5D;
				return;
			}
			else if (variant == AercloudVariant.GREEN)
			{
				EnumFacing randomSide = EnumFacing.random(worldIn.rand);

				entityIn.motionX = randomSide.getFrontOffsetX() * 2.5D;
				entityIn.motionZ = randomSide.getFrontOffsetZ() * 2.5D;

				return;
			}
			else if (variant == AercloudVariant.PURPLE)
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
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + this.maxX, pos.getY(), pos.getZ() + this.maxZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= AercloudVariant.PURPLE.getMetadata())
		{
			return this.getDefaultState().withProperty(AERCLOUD_TYPE, AercloudVariant.PURPLE).withProperty(FACING, EnumFacing.getFront(meta - AercloudVariant.PURPLE.getMetadata()));
		}

		return this.getDefaultState().withProperty(AERCLOUD_TYPE, AercloudVariant.getVariantFromMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(AERCLOUD_TYPE) == AercloudVariant.PURPLE)
		{
			return AercloudVariant.PURPLE.getMetadata() + ((EnumFacing) state.getValue(FACING)).getIndex();
		}

		return ((AercloudVariant) state.getValue(AERCLOUD_TYPE)).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((AercloudVariant) state.getValue(AERCLOUD_TYPE)).getMetadata();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { AERCLOUD_TYPE, FACING });
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return AercloudVariant.getVariantFromMetadata(stack.getMetadata()).getName();
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(this, 1, ((AercloudVariant) state.getValue(AERCLOUD_TYPE)).getMetadata());
	}

}
