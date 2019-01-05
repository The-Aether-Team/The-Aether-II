package com.gildedgames.aether.common.world.lighting;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
public class LightingHooks
{
	public static void onChunkPacketCreated(Chunk chunk)
	{
		LightingHooks.getLightingEngine(chunk.getWorld()).procLightUpdates();
	}

	public static void onChunkProviderSaveChunks(World world)
	{
		LightingHooks.getLightingEngine(world).procLightUpdates();
	}

	public static boolean onWorldCheckLightFor(World world, EnumSkyBlock lightType, BlockPos pos)
	{
		LightingHooks.getLightingEngine(world).scheduleLightUpdate(lightType, pos);

		return true;
	}

	@SideOnly(Side.CLIENT)
	public static void onMinecraftTick(World world)
	{
		LightingHooks.getLightingEngine(world).procLightUpdates();
	}

	public static void onChunkLoaderSaveChunk(World world, Chunk chunk)
	{
		LightingHooks.getLightingEngine(world).procLightUpdates();
	}

	public static void initSkylightForSection(final World world, final Chunk chunk, final ExtendedBlockStorage section)
	{
		if (world.provider.hasSkyLight())
		{
			for (int x = 0; x < 16; ++x)
			{
				for (int z = 0; z < 16; ++z)
				{
					if (chunk.getHeightValue(x, z) <= section.getYLocation())
					{
						for (int y = 0; y < 16; ++y)
						{
							section.setSkyLight(x, y, z, EnumSkyBlock.SKY.defaultLightValue);
						}
					}
				}
			}
		}
	}

	private static final EnumSkyBlock[] ENUM_SKY_BLOCK_VALUES = EnumSkyBlock.values();

	private static final EnumFacing.AxisDirection[] ENUM_AXIS_DIRECTION_VALUES = EnumFacing.AxisDirection.values();

	private static final int FLAG_COUNT = 32; //2 light types * 4 directions * 2 halves * (inwards + outwards)

	public static void relightSkylightColumn(final World world, final Chunk chunk, final int x, final int z, final int height1, final int height2)
	{
		final int yMin = Math.min(height1, height2);
		final int yMax = Math.max(height1, height2) - 1;

		final ExtendedBlockStorage[] sections = chunk.getBlockStorageArray();

		final int xBase = (chunk.x << 4) + x;
		final int zBase = (chunk.z << 4) + z;

		scheduleRelightChecksForColumn(world, EnumSkyBlock.SKY, xBase, zBase, yMin, yMax);

		if (sections[yMin >> 4] == Chunk.NULL_BLOCK_STORAGE && yMin > 0)
		{
			world.checkLightFor(EnumSkyBlock.SKY, new BlockPos(xBase, yMin - 1, zBase));
		}

		short emptySections = 0;

		for (int sec = yMax >> 4; sec >= yMin >> 4; --sec)
		{
			if (sections[sec] == Chunk.NULL_BLOCK_STORAGE)
			{
				emptySections |= 1 << sec;
			}
		}

		if (emptySections != 0)
		{
			for (final EnumFacing dir : EnumFacing.HORIZONTALS)
			{
				final int xOffset = dir.getXOffset();
				final int zOffset = dir.getZOffset();

				final boolean neighborColumnExists =
						(((x + xOffset) | (z + zOffset)) & 16) == 0
								//Checks whether the position is at the specified border (the 16 bit is set for both 15+1 and 0-1)
								|| world.getChunkProvider().getLoadedChunk(chunk.x + xOffset, chunk.z + zOffset) != null;

				if (neighborColumnExists)
				{
					for (int sec = yMax >> 4; sec >= yMin >> 4; --sec)
					{
						if ((emptySections & (1 << sec)) != 0)
						{
							scheduleRelightChecksForColumn(world, EnumSkyBlock.SKY, xBase + xOffset, zBase + zOffset, sec << 4, (sec << 4) + 15);
						}
					}
				}
				else
				{
					flagChunkBoundaryForUpdate(chunk, emptySections, EnumSkyBlock.SKY, dir, getAxisDirection(dir, x, z), EnumBoundaryFacing.OUT);
				}
			}
		}
	}

	private static void scheduleRelightChecksForArea(final World world, final EnumSkyBlock lightType, final int xMin, final int yMin, final int zMin,
			final int xMax, final int yMax, final int zMax)
	{
		for (int x = xMin; x <= xMax; ++x)
		{
			for (int z = zMin; z <= zMax; ++z)
			{
				scheduleRelightChecksForColumn(world, lightType, x, z, yMin, yMax);
			}
		}
	}

	private static void scheduleRelightChecksForColumn(final World world, final EnumSkyBlock lightType, final int x, final int z, final int yMin,
			final int yMax)
	{
		for (int y = yMin; y <= yMax; ++y)
		{
			world.checkLightFor(lightType, new BlockPos(x, y, z));
		}
	}

	public enum EnumBoundaryFacing
	{
		IN, OUT;

		public EnumBoundaryFacing getOpposite()
		{
			return this == IN ? OUT : IN;
		}
	}

	public static void flagSecBoundaryForUpdate(final Chunk chunk, final BlockPos pos, final EnumSkyBlock lightType, final EnumFacing dir,
			final EnumBoundaryFacing boundaryFacing)
	{
		flagChunkBoundaryForUpdate(chunk, (short) (1 << (pos.getY() >> 4)), lightType, dir, getAxisDirection(dir, pos.getX(), pos.getZ()), boundaryFacing);
	}

	private static void flagChunkBoundaryForUpdate(final Chunk chunk, final short sectionMask, final EnumSkyBlock lightType, final EnumFacing dir,
			final EnumFacing.AxisDirection axisDirection, final EnumBoundaryFacing boundaryFacing)
	{
		initNeighborLightChecks(chunk);
		getNeighborLightChecks(chunk)[getFlagIndex(lightType, dir, axisDirection, boundaryFacing)] |= sectionMask;
		chunk.markDirty();
	}

	private static int getFlagIndex(final EnumSkyBlock lightType, final int xOffset, final int zOffset, final EnumFacing.AxisDirection axisDirection,
			final EnumBoundaryFacing boundaryFacing)
	{
		return (lightType == EnumSkyBlock.BLOCK ? 0 : 16) | ((xOffset + 1) << 2) | ((zOffset + 1) << 1) | (axisDirection.getOffset() + 1) | boundaryFacing
				.ordinal();
	}

	private static int getFlagIndex(final EnumSkyBlock lightType, final EnumFacing dir, final EnumFacing.AxisDirection axisDirection,
			final EnumBoundaryFacing boundaryFacing)
	{
		return getFlagIndex(lightType, dir.getXOffset(), dir.getZOffset(), axisDirection, boundaryFacing);
	}

	private static EnumFacing.AxisDirection getAxisDirection(final EnumFacing dir, final int x, final int z)
	{
		return ((dir.getAxis() == EnumFacing.Axis.X ? z : x) & 15) < 8 ? EnumFacing.AxisDirection.NEGATIVE : EnumFacing.AxisDirection.POSITIVE;
	}

	public static void scheduleRelightChecksForChunkBoundaries(final World world, final Chunk chunk)
	{
		for (final EnumFacing dir : EnumFacing.HORIZONTALS)
		{
			final int xOffset = dir.getXOffset();
			final int zOffset = dir.getZOffset();

			final Chunk nChunk = world.getChunkProvider().getLoadedChunk(chunk.x + xOffset, chunk.z + zOffset);

			if (nChunk == null)
			{
				continue;
			}

			for (final EnumSkyBlock lightType : ENUM_SKY_BLOCK_VALUES)
			{
				for (final EnumFacing.AxisDirection axisDir : ENUM_AXIS_DIRECTION_VALUES)
				{
					//Merge flags upon loading of a chunk. This ensures that all flags are always already on the IN boundary below
					mergeFlags(lightType, chunk, nChunk, dir, axisDir);
					mergeFlags(lightType, nChunk, chunk, dir.getOpposite(), axisDir);

					//Check everything that might have been canceled due to this chunk not being loaded.
					//Also, pass in chunks if already known
					//The boundary to the neighbor chunk (both ways)
					scheduleRelightChecksForBoundary(world, chunk, nChunk, null, lightType, xOffset, zOffset, axisDir);
					scheduleRelightChecksForBoundary(world, nChunk, chunk, null, lightType, -xOffset, -zOffset, axisDir);
					//The boundary to the diagonal neighbor (since the checks in that chunk were aborted if this chunk wasn't loaded, see scheduleRelightChecksForBoundary)
					scheduleRelightChecksForBoundary(world, nChunk, null, chunk, lightType, (zOffset != 0 ? axisDir.getOffset() : 0),
							(xOffset != 0 ? axisDir.getOffset() : 0), dir.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ?
									EnumFacing.AxisDirection.NEGATIVE :
									EnumFacing.AxisDirection.POSITIVE);
				}
			}
		}
	}

	private static void mergeFlags(final EnumSkyBlock lightType, final Chunk inChunk, final Chunk outChunk, final EnumFacing dir,
			final EnumFacing.AxisDirection axisDir)
	{
		if (getNeighborLightChecks(outChunk) == null)
		{
			return;
		}

		initNeighborLightChecks(inChunk);

		final int inIndex = getFlagIndex(lightType, dir, axisDir, EnumBoundaryFacing.IN);
		final int outIndex = getFlagIndex(lightType, dir.getOpposite(), axisDir, EnumBoundaryFacing.OUT);

		getNeighborLightChecks(inChunk)[inIndex] |= getNeighborLightChecks(outChunk)[outIndex];
		//no need to call Chunk.setModified() since checks are not deleted from outChunk
	}

	private static void scheduleRelightChecksForBoundary(final World world, final Chunk chunk, Chunk nChunk, Chunk sChunk, final EnumSkyBlock lightType,
			final int xOffset, final int zOffset, final EnumFacing.AxisDirection axisDir)
	{
		if (getNeighborLightChecks(chunk) == null)
		{
			return;
		}

		final int flagIndex = getFlagIndex(lightType, xOffset, zOffset, axisDir, EnumBoundaryFacing.IN); //OUT checks from neighbor are already merged

		final int flags = getNeighborLightChecks(chunk)[flagIndex];

		if (flags == 0)
		{
			return;
		}

		if (nChunk == null)
		{
			nChunk = world.getChunkProvider().getLoadedChunk(chunk.x + xOffset, chunk.z + zOffset);

			if (nChunk == null)
			{
				return;
			}
		}

		if (sChunk == null)
		{
			sChunk = world.getChunkProvider()
					.getLoadedChunk(chunk.x + (zOffset != 0 ? axisDir.getOffset() : 0), chunk.z + (xOffset != 0 ? axisDir.getOffset() : 0));

			if (sChunk == null)
			{
				return; //Cancel, since the checks in the corner columns require the corner column of sChunk
			}
		}

		final int reverseIndex = getFlagIndex(lightType, -xOffset, -zOffset, axisDir, EnumBoundaryFacing.OUT);

		getNeighborLightChecks(chunk)[flagIndex] = 0;

		if (getNeighborLightChecks(nChunk) != null)
		{
			getNeighborLightChecks(nChunk)[reverseIndex] = 0; //Clear only now that it's clear that the checks are processed
		}

		chunk.markDirty();
		nChunk.markDirty();

		//Get the area to check
		//Start in the corner...
		int xMin = chunk.x << 4;
		int zMin = chunk.z << 4;

		//move to other side of chunk if the direction is positive
		if ((xOffset | zOffset) > 0)
		{
			xMin += 15 * xOffset;
			zMin += 15 * zOffset;
		}

		//shift to other half if necessary (shift perpendicular to dir)
		if (axisDir == EnumFacing.AxisDirection.POSITIVE)
		{
			xMin += 8 * (zOffset & 1); //x & 1 is same as abs(x) for x=-1,0,1
			zMin += 8 * (xOffset & 1);
		}

		//get maximal values (shift perpendicular to dir)
		final int xMax = xMin + 7 * (zOffset & 1);
		final int zMax = zMin + 7 * (xOffset & 1);

		for (int y = 0; y < 16; ++y)
		{
			if ((flags & (1 << y)) != 0)
			{
				scheduleRelightChecksForArea(world, lightType, xMin, y << 4, zMin, xMax, (y << 4) + 15, zMax);
			}
		}
	}

	private static void initNeighborLightChecks(final Chunk chunk)
	{
		if (getNeighborLightChecks(chunk) == null)
		{
			setNeighborLightChecks(chunk, new short[FLAG_COUNT]);
		}
	}

	private static final String neighborLightChecksKey = "NeighborLightChecks";

	public static void writeNeighborLightChecksToNBT(final Chunk chunk, final NBTTagCompound nbt)
	{
		if (getNeighborLightChecks(chunk) == null)
		{
			return;
		}

		boolean empty = true;

		final NBTTagList list = new NBTTagList();

		for (final short flags : getNeighborLightChecks(chunk))
		{
			list.appendTag(new NBTTagShort(flags));

			if (flags != 0)
			{
				empty = false;
			}
		}

		if (!empty)
		{
			nbt.setTag(neighborLightChecksKey, list);
		}
	}

	public static void readNeighborLightChecksFromNBT(final Chunk chunk, final NBTTagCompound nbt)
	{
		if (nbt.hasKey(neighborLightChecksKey, 9))
		{
			final NBTTagList list = nbt.getTagList(neighborLightChecksKey, 2);

			if (list.tagCount() == FLAG_COUNT)
			{
				initNeighborLightChecks(chunk);

				for (int i = 0; i < FLAG_COUNT; ++i)
				{
					getNeighborLightChecks(chunk)[i] = ((NBTTagShort) list.get(i)).getShort();
				}
			}
			else
			{
				AetherCore.LOGGER.warn("Chunk field %s had invalid length, ignoring it (chunk coordinates: %s %s)", neighborLightChecksKey, chunk.x, chunk.z);
			}
		}
	}

	// === STUBS ===
	// These are replaced by ASM-generated code and will never throw UnsupportedOperationException.

	private static short[] getNeighborLightChecks(Chunk chunk)
	{
		throw new UnsupportedOperationException();
	}

	private static void setNeighborLightChecks(Chunk chunk, short[] table)
	{
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("WeakerAccess")
	public static int getCachedLightFor(Chunk chunk, EnumSkyBlock type, BlockPos pos)
	{
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("WeakerAccess")
	public static LightingEngine getLightingEngine(World world)
	{
		throw new UnsupportedOperationException();
	}

	// === END OF STUBS ===
}
