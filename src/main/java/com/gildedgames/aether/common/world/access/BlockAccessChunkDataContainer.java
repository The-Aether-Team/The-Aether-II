package com.gildedgames.aether.common.world.access;

import com.gildedgames.aether.common.world.preparation.mask.ChunkDataContainer;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class BlockAccessChunkDataContainer implements IBlockAccess
{
	private final World world;

	private final ChunkDataContainer container;

	private final int offsetX, offsetZ;

	public BlockAccessChunkDataContainer(final World world, final ChunkDataContainer container)
	{
		this.world = world;
		this.container = container;

		this.offsetX = (this.container.getChunkX() * 16);
		this.offsetZ = (this.container.getChunkZ() * 16);
	}

	@Nullable
	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public boolean canAccess(final BlockPos pos)
	{
		//TODO:
		return true;
	}

	@Override
	public boolean canAccess(BlockPos pos, int radius)
	{
		//TODO:
		return true;
	}

	@Override
	public boolean canAccess(final int x, final int z)
	{
		//TODO:
		return true;
	}

	@Override
	public boolean canAccess(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		//TODO:
		return true;
	}

	@Override
	public BlockPos getTopPos(final BlockPos pos)
	{
		return this.world.getTopSolidOrLiquidBlock(pos);
	}

	@Override
	public int getTopY(final int x, final int z)
	{
		return this.world.getHeight(x, z);
	}

	@Override
	public void setBlockToAir(final BlockPos pos)
	{
		this.container.setBlockState(pos.getX() - this.offsetX, pos.getY(), pos.getZ() - this.offsetZ, Blocks.AIR.getDefaultState());
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final BlockState state)
	{
		this.container.setBlockState(pos.getX() - this.offsetX, pos.getY(), pos.getZ() - this.offsetZ, state);

		return true;
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final BlockState state, final int flags)
	{
		this.container.setBlockState(pos.getX() - this.offsetX, pos.getY(), pos.getZ() - this.offsetZ, state);

		return true;
	}

	@Override
	public void setTileEntity(final BlockPos pos, final TileEntity tileEntity)
	{
		this.container.setTileEntity(pos.toImmutable(), tileEntity);
	}

	@Override
	public void spawnEntity(Entity entity)
	{
		this.container.addEntity(entity);
	}

	@Nullable
	@Override
	public TileEntity getTileEntity(final BlockPos pos)
	{
		return this.container.getTileEntity(pos);
	}

	@Override
	public int getCombinedLight(final BlockPos pos, final int lightValue)
	{
		return this.world.getCombinedLight(pos, lightValue);
	}

	@Override
	public BlockState getBlockState(final BlockPos pos)
	{
		return this.container.getBlockState(pos.getX() - this.offsetX, pos.getY(), pos.getZ() - this.offsetZ);
	}

	@Override
	public boolean isAirBlock(final BlockPos pos)
	{
		return this.getBlockState(pos) != Blocks.AIR.getDefaultState();
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return this.world.getBiome(pos);
	}

	@Override
	public int getStrongPower(final BlockPos pos, final Direction direction)
	{
		return this.world.getStrongPower(pos, direction);
	}

	@Override
	public WorldType getWorldType()
	{
		return this.world.getWorldType();
	}

	@Override
	public boolean isSideSolid(final BlockPos pos, final Direction side, final boolean _default)
	{
		return this.world.isSideSolid(pos, side, _default);
	}
}
