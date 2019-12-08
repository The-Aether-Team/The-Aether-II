package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTallAetherGrass extends BlockAetherPlant implements IShearable, IBlockMultiName, IBlockSnowy
{
	public static final BlockVariant SHORT = new BlockVariant(0, "short"),
			NORMAL = new BlockVariant(1, "normal"),
			LONG = new BlockVariant(2, "long");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SHORT, NORMAL, LONG);

	public static final PropertyEnum<BlockTallAetherGrass.Type> TYPE = PropertyEnum
			.create("type", BlockTallAetherGrass.Type.class, Type.HIGHLANDS, Type.ENCHANTED, Type.ARCTIC, Type.MAGNETIC, Type.IRRADIATED);

	private static final AxisAlignedBB GRASS_SHORT_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final AxisAlignedBB GRASS_NORMAL_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final AxisAlignedBB GRASS_LONG_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	public BlockTallAetherGrass()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, SHORT).withProperty(TYPE, Type.HIGHLANDS)
				.withProperty(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);

		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11)
		{
			if (state.getValue(PROPERTY_SNOWY))
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_SNOWY, Boolean.FALSE), 2);
			}
		}
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
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return null;
	}

	@Override
	public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune)
	{
		final List<ItemStack> drops = new ArrayList<>();
		IBlockState state = world.getBlockState(pos);

		drops.add(new ItemStack(this, 1, this.getMetaFromState(state) - (state.getValue(PROPERTY_SNOWY) ? PROPERTY_VARIANT.getAllowedValues().size() : 0)));

		return drops;
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().withProperty(BlockSnow.LAYERS, 1), 2);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		if (state.getValue(PROPERTY_VARIANT) == SHORT)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == NORMAL)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == LONG)
		{
			return GRASS_LONG_AABB;
		}

		return super.getBoundingBox(state, source, pos);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final boolean snowy = meta >= PROPERTY_VARIANT.getAllowedValues().size();
		final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta - (snowy ? PROPERTY_VARIANT.getAllowedValues().size() : 0));

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant).withProperty(PROPERTY_SNOWY, snowy);
	}

	@Override
	public boolean isReplaceable(final IBlockAccess worldIn, final BlockPos pos)
	{
		return true;
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta() + (state.getValue(PROPERTY_SNOWY) ? PROPERTY_VARIANT.getAllowedValues().size() : 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, TYPE, PROPERTY_VARIANT, PROPERTY_SNOWY);
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public Vec3d getOffset(final IBlockState state, final IBlockAccess access, final BlockPos pos)
	{
		if (state.getValue(PROPERTY_SNOWY))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	public enum Type implements IStringSerializable
	{
		HIGHLANDS("highlands"), ENCHANTED("enchanted"), ARCTIC("arctic"), MAGNETIC("magnetic"), IRRADIATED("irradiated"), SNOWY("snowy");

		private final String name;

		Type(final String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}

}
