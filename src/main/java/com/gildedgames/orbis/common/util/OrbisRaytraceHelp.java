package com.gildedgames.orbis.common.util;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.gildedgames.orbis.common.world_objects.WorldRegion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class OrbisRaytraceHelp
{

	private static final List<Class<? extends IWorldObject>> blueprintClass;

	static
	{
		blueprintClass = new ArrayList<>(1);
		blueprintClass.add(Blueprint.class);
	}

	public static double getFinalExtendedReach(final EntityPlayer player)
	{
		final BlockPos airRaytrace = raytraceWithRegionSnapping(player);

		if (isSnappingToRegion(player) || isSelectingCorner(player))
		{
			final double x1 = player.posX;
			final double x2 = airRaytrace.getX();

			final double y1 = player.posY;
			final double y2 = airRaytrace.getY();

			final double z1 = player.posZ;
			final double z2 = airRaytrace.getZ();

			return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + +(z1 - z2) * (z1 - z2));
		}

		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		return playerAether.getOrbisModule().getDeveloperReach();
	}

	/**
	 * Returns the BlockPos the player is selecting with
	 * extended reach. This doesn't take into account
	 * things like snapping and corners.
	 * @return
	 */
	public static BlockPos raytraceNoSnapping(final EntityPlayer player)
	{
		final Vec3d finalVec = getFinalVec(player);

		final int x = MathHelper.floor(finalVec.xCoord);
		final int y = MathHelper.floor(finalVec.yCoord);
		final int z = MathHelper.floor(finalVec.zCoord);

		return new BlockPos(x, y, z);
	}

	public static boolean isSnappingToRegion(final EntityPlayer player)
	{
		final Vec3d positionVec = getPositionEyes(1.0F, player);
		final Vec3d finalVec = getFinalVec(player);

		final BlockPos pos = findSnapPos(player, blueprintClass, positionVec, finalVec);

		return pos != null;
	}

	public static boolean isSelectingCorner(final EntityPlayer player)
	{
		return raytraceRegionCorners(player) != null;
	}

	public static Vec3d getPositionEyes(final float partialTicks, final Entity entity)
	{
		if (partialTicks == 1.0F)
		{
			return new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
		}
		else
		{
			final double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
			final double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) entity.getEyeHeight();
			final double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
			return new Vec3d(d0, d1, d2);
		}
	}

	/**
	 * Returns the coordinate the player is currently having its cursor.
	 * Does cool stuff like snap to the borders of regions and corners.
	 * @return
	 */
	public static BlockPos raytraceWithRegionSnapping(final EntityPlayer player)
	{
		final Vec3d positionVec = getPositionEyes(1.0F, player);
		final Vec3d finalVec = getFinalVec(player);

		BlockPos pos = raytraceRegionsCorners(player, positionVec, finalVec);

		if (pos == null)
		{
			pos = findSnapPos(player, blueprintClass, positionVec, finalVec);
		}

		return pos != null ? pos : new BlockPos(finalVec);
	}

	public static BlockPos raytraceRegionCorners(final EntityPlayer player)
	{
		final Vec3d positionVec = getPositionEyes(1.0F, player);
		final Vec3d finalVec = getFinalVec(player);

		return raytraceRegionsCorners(player, positionVec, finalVec);
	}

	public static IShape raytraceShapes(final EntityPlayer player, final List<Class<? extends IWorldObject>> regionType, final double distance,
			final float partialTicks, final boolean global)
	{
		final Vec3d playerPos = getPositionEyes(partialTicks, player);
		final Vec3d look = player.getLook(partialTicks);
		final Vec3d focus = playerPos.addVector(look.xCoord * distance, look.yCoord * distance, look.zCoord * distance);

		return raytraceRegions(player, regionType, playerPos, focus, global);
	}

	private static Vec3d getFinalVec(final EntityPlayer player)
	{
		final Vec3d positionVec = getPositionEyes(1.0F, player);
		final Vec3d lookVec = player.getLookVec();

		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		final double reach = playerAether.getOrbisModule().getDeveloperReach();

		return positionVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
	}

	public static IShape raytraceRegions(final EntityPlayer player, final List<Class<? extends IWorldObject>> dataType, final Vec3d currentPos,
			final Vec3d endPos,
			final boolean global)
	{
		return raytraceRegionsDo(player, dataType, currentPos, endPos, new RaytraceAction(), global);
	}

	/**
	 * Does a raytrace through different regions and returns a BlockPos
	 * when it is at the regions edge and the region contains the datatype.
	 * Used for snapping the players selector to edges of BlueprintRegions
	 */
	public static BlockPos findSnapPos(final EntityPlayer player, final List<Class<? extends IWorldObject>> dataType, final Vec3d currentPos,
			final Vec3d endPos)
	{
		final IShape shape = raytraceRegionsDo(player, dataType, currentPos, endPos,
				new RaytraceAction()
				{
					private int tries;

					private BlockPos cur;

					@Override
					public IShape onFoundShape(final IShape foundRegion, final BlockPos pos)
					{
						return null;
					}

					@Override
					public IShape onFoundShape(final IShape foundRegion, final BlockPos pos, final Vec3d endposition)
					{
						final IRegion region = ObjectFilter.cast(foundRegion, IRegion.class);

						if (region == null)
						{
							return null;
						}

						final BlockPos min = region.getMin();
						final BlockPos max = region.getMax();

						final boolean snappedMaxX = pos.getX() == max.getX() && pos.getX() <= endposition.xCoord;
						final boolean snappedMinX = pos.getX() == min.getX() && pos.getX() >= endposition.xCoord;

						final boolean snappedMaxY = pos.getY() == max.getY() && pos.getY() <= endposition.yCoord;
						final boolean snappedMinY = pos.getY() == min.getY() && pos.getY() >= endposition.yCoord;

						final boolean snappedMaxZ = pos.getZ() == max.getZ() && pos.getZ() <= endposition.zCoord;
						final boolean snappedMinZ = pos.getZ() == min.getZ() && pos.getZ() >= endposition.zCoord;

						if (RegionHelp.contains(region, pos) && (snappedMaxX || snappedMaxY || snappedMaxZ || snappedMinX || snappedMinY || snappedMinZ))
						{
							tries++;

							cur = pos;
						}
						return null;
					}

					@Override
					public IShape onIterateOutsideRegion(final BlockPos pos, final Vec3d endposition)
					{
						if (tries >= 1)
						{
							return new WorldRegion(cur, player.getEntityWorld());
						}

						return null;
					}
				}, false);
		final IRegion region = ObjectFilter.cast(shape, IRegion.class);
		return region != null ? region.getMin() : null;
	}

	public static BlockPos raytraceRegionsCorners(final EntityPlayer player, final Vec3d currentPos, final Vec3d endPos)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		//TODO: if (!player.canInteractWithItems())
		/*{
			final IShape shape = raytraceRegionsDo(playerHook.getCurrentPower().getDataClasses(), currentPos, endPos,
					new RaytraceAction()
					{
						@Override
						public IShape onFoundShape(IShape foundRegion, BlockPos pos)
						{
							return null;
						}

						@Override
						public IShape onFoundShape(IShape foundShape, BlockPos pos, Vec3 endposition)
						{
							final IRegion region = ObjectFilter.cast(foundShape, IRegion.class);
							if (region != null && RegionHelp.isACorner(pos, region))
							{
								return new WorldRegion(pos, OrbisRaytraceHelp.playerHook.getWrapperWorld());
							}
							return null;
						}
					}, false);
			final IRegion region = ObjectFilter.cast(shape, IRegion.class);
			return region != null ? region.getMin() : null;
		}*/

		return null;
	}

	private static IShape raytraceRegionsDo(
			final EntityPlayer player, final List<Class<? extends IWorldObject>> dataType, Vec3d currentPos, final Vec3d endPos, final RaytraceAction action,
			final boolean allProjects)
	{
		if (player == null)
		{
			return null;
		}

		if (!Double.isNaN(currentPos.xCoord) && !Double.isNaN(currentPos.yCoord) && !Double.isNaN(currentPos.zCoord))
		{
			if (!Double.isNaN(endPos.xCoord) && !Double.isNaN(endPos.yCoord) && !Double.isNaN(endPos.zCoord))
			{
				final int endX = MathHelper.floor(endPos.xCoord);
				final int endY = MathHelper.floor(endPos.yCoord);
				final int endZ = MathHelper.floor(endPos.zCoord);
				int curX = MathHelper.floor(currentPos.xCoord);
				int curY = MathHelper.floor(currentPos.yCoord);
				int curZ = MathHelper.floor(currentPos.zCoord);

				final BlockPos curPos = new BlockPos(curX, curY, curZ);
				IShape foundRegion = getRelevantRegionAt(player, dataType, curPos, endPos, action, allProjects);
				final IShape result = action.onFoundShape(foundRegion, curPos);

				if (result != null)
				{
					return result;
				}

				int i = 200;

				while (i-- >= 0)
				{
					if (Double.isNaN(currentPos.xCoord) || Double.isNaN(currentPos.yCoord) || Double.isNaN(currentPos.zCoord))
					{
						return null;
					}

					if (curX == endX && curY == endY && curZ == endZ)
					{
						return null;
					}

					boolean currentXnotEnd = true;
					boolean currentYnotEnd = true;
					boolean currentZnotEnd = true;

					double newX = 999.0D;
					double newY = 999.0D;
					double newZ = 999.0D;

					if (endX > curX)
					{
						newX = curX + 1.0D;
					}
					else if (endX < curX)
					{
						newX = curX + 0.0D;
					}
					else
					{
						currentXnotEnd = false;
					}

					if (endY > curY)
					{
						newY = curY + 1.0D;
					}
					else if (endY < curY)
					{
						newY = curY + 0.0D;
					}
					else
					{
						currentYnotEnd = false;
					}

					if (endZ > curZ)
					{
						newZ = curZ + 1.0D;
					}
					else if (endZ < curZ)
					{
						newZ = curZ + 0.0D;
					}
					else
					{
						currentZnotEnd = false;
					}

					double distanceFactorX = 999.0D;
					double distanceFactorY = 999.0D;
					double distanceFactorZ = 999.0D;
					final double disX = endPos.xCoord - currentPos.xCoord;
					final double disY = endPos.yCoord - currentPos.yCoord;
					final double disZ = endPos.zCoord - currentPos.zCoord;

					if (currentXnotEnd)
					{
						distanceFactorX = (newX - currentPos.xCoord) / disX;
					}

					if (currentYnotEnd)
					{
						distanceFactorY = (newY - currentPos.yCoord) / disY;
					}

					if (currentZnotEnd)
					{
						distanceFactorZ = (newZ - currentPos.zCoord) / disZ;
					}

					final byte distance;

					if (distanceFactorX < distanceFactorY && distanceFactorX < distanceFactorZ)
					{
						if (endX > curX)
						{
							distance = 4;
						}
						else
						{
							distance = 5;
						}
						currentPos = new Vec3d(newX, currentPos.yCoord + disY * distanceFactorX, currentPos.zCoord + disZ * distanceFactorX);
					}
					else if (distanceFactorY < distanceFactorZ)
					{
						if (endY > curY)
						{
							distance = 0;
						}
						else
						{
							distance = 1;
						}
						currentPos = new Vec3d(currentPos.xCoord + disX * distanceFactorY, newY, currentPos.zCoord + disZ * distanceFactorY);
					}
					else
					{
						if (endZ > curZ)
						{
							distance = 2;
						}
						else
						{
							distance = 3;
						}

						currentPos = new Vec3d(currentPos.xCoord + disX * distanceFactorZ, currentPos.yCoord + disY * distanceFactorZ, newZ);
					}
					curX = MathHelper.floor(currentPos.xCoord);
					curY = MathHelper.floor(currentPos.yCoord);
					curZ = MathHelper.floor(currentPos.zCoord);

					if (distance == 5)
					{
						--curX;
					}

					if (distance == 1)
					{
						--curY;
					}

					if (distance == 3)
					{
						--curZ;
					}

					foundRegion = getRelevantRegionAt(player, dataType, new BlockPos(curX, curY, curZ), endPos, action, allProjects);
					if (foundRegion != null)
					{
						return foundRegion;
					}
				}
			}
		}
		return null;
	}

	private static IShape getRelevantRegionAt(final EntityPlayer player, final List<Class<? extends IWorldObject>> dataType, final BlockPos pos,
			final Vec3d endPosition, final RaytraceAction action,
			final boolean allProjects)
	{
		final IWorldObjectManager manager = WorldObjectManager.get(player.world);

		final IShape foundRegion = allProjects ?
				RegionHelper.getIntersectingWorldShapeGlobal(pos, player.getEntityWorld()) :
				manager.getGroup(0).getIntersectingShape(pos);

		final IShape result;

		if (foundRegion != null)
		{
			result = action.onFoundShape(foundRegion, pos, endPosition);
		}
		else
		{
			result = action.onIterateOutsideRegion(pos, endPosition);
		}

		return result;
	}

	public static class RaytraceAction
	{
		public IShape onFoundShape(final IShape foundRegion, final BlockPos pos)
		{
			return foundRegion;
		}

		public IShape onFoundShape(final IShape foundRegion, final BlockPos pos, final Vec3d endposition)
		{
			return foundRegion;
		}

		public IShape onIterateOutsideRegion(final BlockPos pos, final Vec3d endposition)
		{
			return null;
		}
	}

}