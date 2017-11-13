package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.api.orbis_core.block.BlockData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class SingleStateAccess implements IBlockAccess
{
	protected final BlockData data;

	protected final IBlockAccess world;

	public SingleStateAccess(final BlockData data, final World worldIn)
	{
		this.data = data;
		this.world = worldIn;
	}

	@Override
	public TileEntity getTileEntity(final BlockPos pos)
	{
		return this.data.getTileEntity();
	}

	@Override
	public int getCombinedLight(final BlockPos p_175626_1_, final int p_175626_2_)
	{
		// TODO Auto-generated method stub. Check out ChunkCache or sth
		return 0;
	}

	@Override
	public IBlockState getBlockState(final BlockPos pos)
	{
		return this.data.getBlockState();
	}

	@Override
	public boolean isAirBlock(final BlockPos pos)
	{
		return this.data.isAir();
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return this.world.getBiome(pos);
	}

	@Override
	public int getStrongPower(final BlockPos pos, final EnumFacing direction)
	{
		final IBlockState iblockstate = this.getBlockState(pos);
		return iblockstate.getBlock().getStrongPower(iblockstate, this, pos, direction);
	}

	@Override
	public WorldType getWorldType()
	{
		return this.world.getWorldType();
	}

	@Override
	public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default)
	{
		final IBlockState iblockstate = this.getBlockState(pos);
		return iblockstate.getBlock().isSideSolid(iblockstate, this, pos, side);
	}
}
