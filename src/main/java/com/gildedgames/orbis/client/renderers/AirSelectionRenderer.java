package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.player.godmode.GodPowerCreative;
import com.gildedgames.orbis_core.data.region.IMutableRegion;
import com.gildedgames.orbis_core.util.RaytraceHelp;
import com.gildedgames.orbis_core.world_objects.WorldRegion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AirSelectionRenderer
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static final float fadeMax = 0.15F;

	public static float PARTIAL_TICKS;

	private static long lastTimeRegionAlphaChanged;

	private static double prevReach;

	private static float timeDifRequired, timeToFade = 500.0F, fadeMin;

	private static float startedFrom;

	private static int prevSlotIndex;

	private static IMutableRegion region;

	private static RenderShape renderRegion;

	private static BlockPos prevPos;

	private AirSelectionRenderer()
	{

	}

	private static void resetRegionAlpha()
	{
		lastTimeRegionAlphaChanged = System.currentTimeMillis();

		startedFrom = renderRegion.boxAlpha;
	}

	@SubscribeEvent
	public static void onRenderWorldLast(final RenderWorldLastEvent event)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(mc.player);

		if (!playerAether.getOrbisModule().inDeveloperMode())
		{
			return;
		}

		final BlockPos airRaytrace = playerAether.getOrbisModule().raytraceNoSnapping();

		if (!(playerAether.getOrbisModule().powers().getCurrentPower() instanceof GodPowerCreative))
		{
			Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(mc.player, mc.player.getLook(1.0F));
		}

		if (region == null || renderRegion == null)
		{
			region = new WorldRegion(new BlockPos(0, 0, 0), mc.world);
			renderRegion = new RenderShape(region);

			renderRegion.renderBorder = false;
			renderRegion.useCustomColors = true;
			renderRegion.renderDimensionsAbove = false;

			renderRegion.boxAlpha = fadeMax;
			renderRegion.gridAlpha = 0.0F;
		}

		final IGodPowerClient powerClient = playerAether.getOrbisModule().powers().getCurrentPower().getClientHandler();

		final boolean has3DCursor = powerClient.has3DCursor(playerAether.getOrbisModule());

		if (!has3DCursor)
		{
			fadeMin = powerClient.minFade3DCursor(playerAether.getOrbisModule());
		}
		else
		{
			fadeMin = 0.05F;
		}

		if (prevSlotIndex != mc.player.inventory.currentItem)
		{
			resetRegionAlpha();

			timeDifRequired = 0.0F;
			timeToFade = 500.0F;

			prevSlotIndex = mc.player.inventory.currentItem;
		}

		final BlockPos pos = RaytraceHelp.doOrbisRaytrace(playerAether, airRaytrace);

		final IBlockState state = mc.world.getBlockState(pos);

		if (state != Blocks.AIR.getDefaultState())
		{
			final int color = state.getBlock().getMapColor(state).colorValue;

			//region.setBorderColor(color);
			//region.setGridColor(color);
		}
		else
		{
			//region.setBorderColor(0xFFFFFF);
			//region.setGridColor(0xFFFFFF);
		}

		if (!pos.equals(prevPos) && has3DCursor)
		{
			resetRegionAlpha();

			renderRegion.boxAlpha = fadeMax;

			timeDifRequired = 700.0F;
			timeToFade = 800.0F;

			prevPos = pos;
		}

		if (prevReach != playerAether.getOrbisModule().getDeveloperReach())
		{
			resetRegionAlpha();

			renderRegion.boxAlpha = fadeMax;
			startedFrom = fadeMax;

			timeDifRequired = 0.0F;
			timeToFade = 500.0F;

			prevReach = playerAether.getOrbisModule().getDeveloperReach();
		}

		region.setBounds(pos, pos);

		final float timeDif = System.currentTimeMillis() - lastTimeRegionAlphaChanged;
		final float timeMinusReq = timeDif - timeDifRequired;

		if (timeMinusReq > 0.0F && renderRegion.boxAlpha > fadeMin)
		{
			final float fadeProgress = 1.0F - (timeMinusReq / timeToFade);

			renderRegion.boxAlpha = Math.max(fadeMin, fadeProgress * startedFrom);
		}

		if (timeMinusReq < 0.0F && renderRegion.boxAlpha < fadeMax)
		{
			final float fadeProgress = 1.0F + (timeDif / timeToFade);

			//renderRegion.boxAlpha = Math.min(fadeMax, fadeProgress * fadeMin);
		}

		if (renderRegion.boxAlpha < fadeMin)
		{
			renderRegion.boxAlpha = fadeMin;
		}

		if (renderRegion.boxAlpha > fadeMax)
		{
			renderRegion.boxAlpha = fadeMax;
		}

		GlStateManager.pushMatrix();
		GlStateManager.disableDepth();

		final int color = 0xFFFFFF;

		renderRegion.colorGrid = color;
		renderRegion.colorBorder = color;

		PARTIAL_TICKS = event.getPartialTicks();

		renderRegion.renderFully(mc.world, PARTIAL_TICKS);
		renderRegion.onRemoved();

		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

}
