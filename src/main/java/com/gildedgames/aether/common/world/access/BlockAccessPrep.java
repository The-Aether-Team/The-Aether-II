package com.gildedgames.aether.common.world.access;

import com.gildedgames.aether.api.world.preparation.*;
import com.gildedgames.aether.common.world.preparation.PrepChunkManager;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public abstract class BlockAccessPrep implements IBlockAccessExtended
{
	protected IChunkMaskTransformer transformer;

	private World world;

	private IPrepChunkManager<? extends IChunkInfo> chunkManager;

	private IPrepSectorData sectorData;

	public BlockAccessPrep(World world, IPrepSectorData sectorData, IPrepRegistryEntry<? extends IChunkInfo> registryEntry)
	{
		this.world = world;

		this.sectorData = sectorData;

		this.chunkManager = new PrepChunkManager<>(world, registryEntry);
		this.transformer = this.chunkManager.createMaskTransformer();
	}

	@Override
	@Nullable
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public boolean canAccess(BlockPos pos, int radius)
	{
		return true;
	}

	@Override
	public BlockPos getTopPos(BlockPos pos)
	{
		return new BlockPos(pos.getX(), this.getTopY(pos.getX(), pos.getZ()), pos.getZ());
	}

	@Override
	public int getTopY(int x, int z)
	{
		int chunkX = x >> 4;
		int chunkZ = z >> 4;

		IChunkMask mask = this.chunkManager.getChunk(this.sectorData, chunkX, chunkZ);

		return mask.getHighestBlock(x & 15, z & 15);
	}

	@Override
	public void setBlockToAir(BlockPos pos)
	{

	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state)
	{
		return false;
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state, int flags)
	{
		return false;
	}

	@Override
	public void setTileEntity(BlockPos pos, TileEntity tileEntity)
	{

	}

	@Override
	public void spawnEntity(Entity entity)
	{

	}

	@Nullable
	@Override
	public TileEntity getTileEntity(BlockPos pos)
	{
		return null;
	}

	@Override
	public int getCombinedLight(BlockPos pos, int lightValue)
	{
		return 0;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		IChunkMask chunk = this.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

		return this.transformer.getBlockState(chunk.getBlock(pos.getX() & 15, pos.getY(), pos.getZ() & 15));
	}

	@Override
	public boolean isAirBlock(BlockPos pos)
	{
		return this.getBlockState(pos).getBlock() == Blocks.AIR;
	}

	@Override
	public Biome getBiome(BlockPos pos)
	{
		return this.world.getBiome(pos);
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction)
	{
		return 0;
	}

	@Override
	public WorldType getWorldType()
	{
		return null;
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
	{
		return false;
	}

	protected IChunkMask getChunk(int x, int z)
	{
		IChunkMask chunk = this.chunkManager.getChunk(this.sectorData, x, z);

		if (chunk == null)
		{
			throw new RuntimeException("ChunkMask is null at position: x(" + x + "), z(" + z + ")");
		}

		return chunk;
	}
}
