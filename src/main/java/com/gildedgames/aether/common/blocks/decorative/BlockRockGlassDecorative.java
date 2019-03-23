package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class BlockRockGlassDecorative extends BlockDecorative
{
	public static final BlockVariant SKYROOT_FRAME = new BlockVariant(0, "skyroot_frame"),
			ARKENIUM_FRAME = new BlockVariant(1, "arkenium_frame");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SKYROOT_FRAME, ARKENIUM_FRAME);

	public BlockRockGlassDecorative()
	{
		super(Material.GLASS);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);
		this.setSoundType(SoundType.GLASS);
	}

	@Nonnull
	@Override
	protected PropertyVariant getVariantProperty()
	{
		return PROPERTY_VARIANT;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
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
	@OnlyIn(Dist.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
