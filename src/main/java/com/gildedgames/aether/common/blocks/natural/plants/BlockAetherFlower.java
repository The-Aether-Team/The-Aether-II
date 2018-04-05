package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAetherFlower extends BlockAetherPlant implements IBlockMultiName
{
	public static final BlockVariant
			WHITE_ROSE = new BlockVariant(0, "white_rose"),
			PURPLE_FLOWER = new BlockVariant(1, "purple_flower"),
			BURSTBLOSSOM = new BlockVariant(2, "burstblossom"),
			WHITE_ROSE_SNOWY = new BlockVariant(3, "white_rose_snowy"),
			PURPLE_FLOWER_SNOWY  = new BlockVariant(4, "purple_flower_snowy"),
			BURSTBLOSSOM_SNOWY  = new BlockVariant(5, "burstblossom_snowy");

	public static final int SNOW_START = WHITE_ROSE_SNOWY.getMeta();

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", WHITE_ROSE, PURPLE_FLOWER, BURSTBLOSSOM,
			WHITE_ROSE_SNOWY, PURPLE_FLOWER_SNOWY, BURSTBLOSSOM_SNOWY);

	public BlockAetherFlower()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WHITE_ROSE));
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return super.canPlaceBlockAt(world, pos);
	}

	@Override
	public int getLightValue(final IBlockState state)
	{
		return this.lightValue;
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
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public Vec3d getOffset(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		if (getMetaFromState(state) >= SNOW_START)
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
