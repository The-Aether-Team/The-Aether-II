package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.client.gui.misc.GuiAetherLoading;
import com.gildedgames.aether.client.gui.misc.GuiBlackScreen;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.orbis.lib.client.PartialTicks;
import com.gildedgames.orbis.lib.client.gui.util.GuiFrameUtils;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiLoadingListener
{
	private static final Minecraft mc = Minecraft.getInstance();

	private static boolean DRAW_BLACK_SCREEN = false;

	private static boolean DRAW_LOADING_SCREEN = false;

	private static boolean DRAWING_BLACK_FADE_OUT = false;

	private static boolean DRAWING_BLACK_FADE_IN = false;

	private static boolean CHANGE_FROM_BLACK_TO_LOAD = false;

	private static double TIME_STARTED_FADE = -1;

	private static double TIME_TO_FADE;

	private static Runnable AFTER_FADE;

	private static GuiAetherLoading LOADING = new GuiAetherLoading();

	private static final CustomLoadingRenderer.ICustomLoading BLACK_LOADING = GuiLoadingListener::drawOverlay;

	public static boolean isFadingIn()
	{
		return DRAWING_BLACK_FADE_IN;
	}

	public static void drawBlackFade(double time)
	{
		TIME_STARTED_FADE = System.currentTimeMillis();

		DRAWING_BLACK_FADE_IN = false;
		DRAWING_BLACK_FADE_OUT = true;
		TIME_TO_FADE = time;
	}

	public static void drawBlackFadeIn(double time, Runnable after)
	{
		TIME_STARTED_FADE = System.currentTimeMillis();

		DRAWING_BLACK_FADE_OUT = false;
		DRAWING_BLACK_FADE_IN = true;
		TIME_TO_FADE = time;
		AFTER_FADE = after;
	}

	public static boolean isLoadingScreen()
	{
		return DRAW_LOADING_SCREEN;
	}

	public static void setDrawBlackScreen(boolean flag)
	{
		DRAW_BLACK_SCREEN = flag;

		if (flag)
		{
			CustomLoadingRenderer.CURRENT = BLACK_LOADING;
		}
		else if (CustomLoadingRenderer.CURRENT == BLACK_LOADING)
		{
			CustomLoadingRenderer.CURRENT = null;
		}
	}

	public static void setChangeFromBlackToLoad(boolean flag)
	{
		CHANGE_FROM_BLACK_TO_LOAD = flag;
	}

	public static void setDrawLoading(boolean flag)
	{
		DRAW_LOADING_SCREEN = flag;

		if (flag)
		{
			LOADING = new GuiAetherLoading();

			LOADING.setWorldAndResolution(Minecraft.getInstance(), MathHelper.floor(InputHelper.getScreenWidth()),
					MathHelper.floor(InputHelper.getScreenHeight()));
			LOADING.initGui();

			CustomLoadingRenderer.CURRENT = LOADING;
		}
		else if (CustomLoadingRenderer.CURRENT == LOADING)
		{
			CustomLoadingRenderer.CURRENT = null;
		}
	}

	@SubscribeEvent
	public static void onOpenGui(final GuiOpenEvent event)
	{
		if (mc.world != null && event.getGui() instanceof GuiInventory)
		{
			boolean necro = mc.world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER;

			if (necro && !mc.player.isCreative())
			{
				event.setCanceled(true);
			}
		}

		if (DRAW_BLACK_SCREEN && event.getGui() instanceof GuiDownloadTerrain)
		{
			event.setGui(new GuiBlackScreen());
		}
	}

	private static double getSecondsSinceStart()
	{
		return (System.currentTimeMillis() - TIME_STARTED_FADE) / 1000.0D;
	}

	public static void drawFade(boolean disableDepth)
	{
		if (DRAWING_BLACK_FADE_OUT || DRAWING_BLACK_FADE_IN)
		{
			final float bgAlpha = Math
					.max(0.0F, DRAWING_BLACK_FADE_OUT ?
							1.0F - (float) (getSecondsSinceStart() / TIME_TO_FADE) :
							(float) (getSecondsSinceStart() / TIME_TO_FADE));

			final int bg = GuiFrameUtils.changeAlpha(0xFF000000, (int) (bgAlpha * 255));

			GlStateManager.pushMatrix();

			if (disableDepth)
			{
				GlStateManager.disableDepth();
			}

			GuiUtils.drawGradientRect(0, 0, 0, MathHelper.floor(InputHelper.getScreenWidth()),
					MathHelper.floor(InputHelper.getScreenHeight()), bg, bg);

			if (disableDepth)
			{
				GlStateManager.enableDepth();
			}

			GlStateManager.popMatrix();

			if (getSecondsSinceStart() >= TIME_TO_FADE)
			{
				DRAWING_BLACK_FADE_OUT = false;
				DRAWING_BLACK_FADE_IN = false;

				if (AFTER_FADE != null)
				{
					AFTER_FADE.run();

					AFTER_FADE = null;
				}
			}
		}
	}

	public static void drawOverlay()
	{
		if (CHANGE_FROM_BLACK_TO_LOAD && DRAW_BLACK_SCREEN)
		{
			setChangeFromBlackToLoad(false);
			setDrawBlackScreen(false);

			drawBlackFade(2.0D);
			setDrawLoading(true);
		}

		if (DRAW_LOADING_SCREEN)
		{
			if (Minecraft.getInstance().world != null)
			{
				Minecraft.getInstance().getSoundHandler().stopSounds();
			}

			setChangeFromBlackToLoad(false);

			CustomLoadingRenderer.CURRENT = LOADING;

			GlStateManager.pushMatrix();

			if (!DRAWING_BLACK_FADE_OUT)
			{
				//GlStateManager.disableDepth();
			}

			LOADING.drawScreen(InputHelper.getMouseX(), InputHelper.getMouseY(), PartialTicks.get());

			if (!DRAWING_BLACK_FADE_OUT)
			{
				//GlStateManager.enableDepth();
			}

			GlStateManager.popMatrix();
		}
		else
		{
			if (DRAW_BLACK_SCREEN)
			{
				CustomLoadingRenderer.CURRENT = BLACK_LOADING;

				GlStateManager.pushMatrix();

				GlStateManager.disableDepth();

				GuiUtils.drawGradientRect(0, 0, 0, MathHelper.floor(InputHelper.getScreenWidth()),
						MathHelper.floor(InputHelper.getScreenHeight()), 0xFF000000, 0xFF000000);

				GlStateManager.enableDepth();

				GlStateManager.popMatrix();
			}

			drawFade(true);
		}
	}

	@SubscribeEvent
	public static void onRenderTick(final TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			drawOverlay();
		}
	}

	@SubscribeEvent
	public static void onTick(final ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			if (Minecraft.getInstance().loadingScreen.getClass() == LoadingScreenRenderer.class)
			{
				Minecraft.getInstance().loadingScreen = new CustomLoadingRenderer(Minecraft.getInstance(), Minecraft.getInstance().loadingScreen);
			}
		}
	}
}
