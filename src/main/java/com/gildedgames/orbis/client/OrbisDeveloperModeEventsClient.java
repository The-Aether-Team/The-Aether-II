package com.gildedgames.orbis.client;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.client.gui.GuiRightClickBlueprint;
import com.gildedgames.orbis.common.player.PlayerSelectionModule;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import com.gildedgames.orbis.common.util.RaytraceHelp;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OrbisDeveloperModeEventsClient
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static double prevReach;

	private static IShape prevShape;

	@SubscribeEvent
	public static void onClienTick(final TickEvent.ClientTickEvent event)
	{
		if (mc.world != null && mc.player != null)
		{
			final PlayerAether player = PlayerAether.getPlayer(mc.player);

			if (player.getSelectionModule().getActiveSelection() == null && prevShape != null && prevReach != 0.0D)
			{
				prevShape = null;
				player.getOrbisModule().setExtendedReach(prevReach);
			}
		}
	}

	@SubscribeEvent
	public static void onMouseEvent(final MouseEvent event)
	{
		final PlayerAether player = PlayerAether.getPlayer(Minecraft.getMinecraft().player);

		if (event.getButton() == 0 || event.getButton() == 1)
		{
			if (player.getOrbisModule().inDeveloperMode())
			{
				event.setCanceled(true);

				final BlockPos pos = OrbisRaytraceHelp.getAirRaytracing(player.getEntity());

				final PlayerSelectionModule selector = player.getSelectionModule();

				final IShape selectedShape = player.getOrbisModule().getSelectedRegion();

				final EntityPlayer entity = player.getEntity();

				final int x = MathHelper.floor(entity.posX);
				final int y = MathHelper.floor(entity.posY);
				final int z = MathHelper.floor(entity.posZ);

				if (selectedShape instanceof Blueprint)
				{
					final boolean playerInside = selectedShape.contains(x, y, z) || selectedShape.contains(x, MathHelper.floor(entity.posY + entity.height), z);

					if (player.getEntity().getEntityWorld().isRemote && !playerInside)
					{
						if (System.currentTimeMillis() - GuiRightClickBlueprint.lastCloseTime > 200)
						{
							Minecraft.getMinecraft().displayGuiScreen(new GuiRightClickBlueprint((Blueprint) selectedShape));
						}
					}
				}
				else
				{
					if (!event.isButtonstate() && selector.getActiveSelection() != null || event.isButtonstate() && selector.getActiveSelection() == null)
					{
						selector.setActiveSelectionCorner(pos);
					}
				}
			}
		}

		if (event.getButton() == 2 && event.isButtonstate())
		{
			if (player.getOrbisModule().inDeveloperMode())
			{
				event.setCanceled(true);
			}
		}

		final IShape activeRegion = player.getSelectionModule().getActiveSelection();

		//Change reach
		if (OrbisKeyBindings.keyBindControl.isKeyDown() || activeRegion != null)
		{
			if (activeRegion != null && prevShape == null)
			{
				prevShape = activeRegion;

				prevReach = player.getOrbisModule().getExtendedReach();
			}

			if (OrbisKeyBindings.keyBindControl.isKeyDown())
			{
				prevReach = player.getOrbisModule().getExtendedReach();
			}

			final RayTraceResult blockRaytrace = RaytraceHelp
					.rayTraceNoBlocks(player.getOrbisModule().getFinalExtendedReach(), AirSelectionRenderer.PARTIAL_TICKS, player.getEntity());
			double reach = player.getOrbisModule().getFinalExtendedReach();

			if (event.getDwheel() > 0)
			{
				player.getOrbisModule().setExtendedReach(reach + 1);

				event.setCanceled(true);
			}
			else if (event.getDwheel() < 0)
			{
				if (blockRaytrace != null)
				{
					final int x = MathHelper.floor(player.getEntity().posX);
					final int y = MathHelper.floor(player.getEntity().posY);
					final int z = MathHelper.floor(player.getEntity().posZ);

					final double deltaX = x - blockRaytrace.hitVec.xCoord;
					final double deltaY = y - blockRaytrace.hitVec.yCoord;
					final double deltaZ = z - blockRaytrace.hitVec.zCoord;

					final float distance = MathHelper.floor((float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));

					player.getOrbisModule().setExtendedReach(distance);
					reach = player.getOrbisModule().getFinalExtendedReach();
				}

				player.getOrbisModule().setExtendedReach(reach - 1);

				event.setCanceled(true);
			}
		}
	}

}
