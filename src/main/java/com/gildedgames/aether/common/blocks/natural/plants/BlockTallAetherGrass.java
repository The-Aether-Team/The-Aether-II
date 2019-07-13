package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IShearable;

public class BlockTallAetherGrass extends BlockAetherPlant implements IShearable, IBlockSnowy
{
	public static final BlockVariant SHORT = new BlockVariant(0, "short"),
			NORMAL = new BlockVariant(1, "normal"),
			LONG = new BlockVariant(2, "long");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SHORT, NORMAL, LONG);

	public static final EnumProperty<Type> TYPE = EnumProperty
			.create("type", BlockTallAetherGrass.Type.class, Type.HIGHLANDS, Type.ENCHANTED, Type.ARCTIC, Type.MAGNETIC, Type.IRRADIATED);

	private static final VoxelShape GRASS_SHORT_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final VoxelShape GRASS_NORMAL_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final VoxelShape GRASS_LONG_AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	public BlockTallAetherGrass(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, SHORT).with(TYPE, Type.HIGHLANDS)
				.with(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public boolean isShearable(ItemStack item, IWorldReader world, BlockPos pos)
	{
		return true;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().with(SnowBlock.LAYERS, 1), 2);
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if (state.get(PROPERTY_VARIANT) == SHORT)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.get(PROPERTY_VARIANT) == NORMAL)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.get(PROPERTY_VARIANT) == LONG)
		{
			return GRASS_LONG_AABB;
		}

		return super.getShape(state, worldIn, pos, context);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(TYPE, PROPERTY_VARIANT, PROPERTY_SNOWY);
	}

	@Override
	public Vec3d getOffset(final BlockState state, final IBlockReader access, final BlockPos pos)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
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
