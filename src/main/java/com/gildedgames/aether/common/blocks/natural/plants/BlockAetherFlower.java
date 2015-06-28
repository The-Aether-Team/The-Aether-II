package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithSubtypes;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAetherFlower extends BlockBush implements IAetherBlockWithSubtypes
{
	public static final BlockVariant
			WHITE_ROSE = new BlockVariant(0, "white_rose"),
			PURPLE_FLOWER = new BlockVariant(1, "purple_flower");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", WHITE_ROSE, PURPLE_FLOWER);

	public BlockAetherFlower()
	{
		super(Material.leaves);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setBlockBounds(0.3f, 0.0F, 0.3f, 0.7f, 0.6f, 0.7f);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WHITE_ROSE));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
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
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return this.canPlaceBlockOn(world.getBlockState(pos.down()).getBlock());
	}

	@Override
	public boolean canPlaceBlockOn(Block ground)
	{
		return ground == BlocksAether.aether_grass || ground == this;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT);
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
