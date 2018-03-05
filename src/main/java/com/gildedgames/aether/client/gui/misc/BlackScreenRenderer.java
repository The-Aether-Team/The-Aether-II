package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;

/**
 * Ugly hack to override loading renderer when teleporting to Necromancer Tower
 */
public class BlackScreenRenderer extends LoadingScreenRenderer
{
	/** A reference to the Minecraft object. */
	private final Minecraft mc;

	private final Framebuffer framebuffer;

	private final LoadingScreenRenderer original;

	/** The system's time represented in milliseconds. */
	private long systemTime = Minecraft.getSystemTime();

	public BlackScreenRenderer(final Minecraft mcIn, final LoadingScreenRenderer original)
	{
		super(mcIn);

		this.mc = mcIn;
		this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
		this.framebuffer.setFramebufferFilter(9728);

		this.original = original;
	}

	protected static void drawGradientRect(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor)
	{
		final float f = (float) (startColor >> 24 & 255) / 255.0F;
		final float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		final float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		final float f3 = (float) (startColor & 255) / 255.0F;
		final float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		final float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		final float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		final float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos((double) right, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	@Override
	public void resetProgressAndMessage(final String message)
	{
		if (!ClientEventHandler.DRAW_BLACK_SCREEN)
		{
			this.original.resetProgressAndMessage(message);
			return;
		}

		super.resetProgressAndMessage(message);
	}

	@Override
	public void displaySavingString(final String message)
	{
		if (!ClientEventHandler.DRAW_BLACK_SCREEN)
		{
			this.original.displaySavingString(message);
			return;
		}

		super.displaySavingString(message);
	}

	@Override
	public void displayLoadingString(final String message)
	{
		if (!ClientEventHandler.DRAW_BLACK_SCREEN)
		{
			this.original.displayLoadingString(message);
			return;
		}

		super.displayLoadingString(message);
	}

	@Override
	public void setDoneWorking()
	{
		if (!ClientEventHandler.DRAW_BLACK_SCREEN)
		{
			this.original.setDoneWorking();
			return;
		}

		super.setDoneWorking();
	}

	public LoadingScreenRenderer getOriginal()
	{
		return this.original;
	}

	@Override
	public void setLoadingProgress(final int progress)
	{
		if (!ClientEventHandler.DRAW_BLACK_SCREEN)
		{
			this.original.setLoadingProgress(progress);
			return;
		}

		final long i = Minecraft.getSystemTime();

		if (i - this.systemTime >= 100L)
		{
			this.systemTime = i;
			final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			final int j = scaledresolution.getScaleFactor();
			final int k = scaledresolution.getScaledWidth();
			final int l = scaledresolution.getScaledHeight();

			if (OpenGlHelper.isFramebufferEnabled())
			{
				this.framebuffer.framebufferClear();
			}
			else
			{
				GlStateManager.clear(256);
			}

			this.framebuffer.bindFramebuffer(false);
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.0F, 0.0F, -200.0F);

			if (!OpenGlHelper.isFramebufferEnabled())
			{
				GlStateManager.clear(16640);
			}

			try
			{
				if (!net.minecraftforge.fml.client.FMLClientHandler.instance()
						.handleLoadingScreen(scaledresolution)) //FML Don't render while FML's pre-screen is rendering
				{
					GlStateManager.disableDepth();

					drawGradientRect(0, 0, (int) InputHelper.getScreenWidth(), (int) InputHelper.getScreenHeight(), 0xFF000000, 0xFF000000);

					GlStateManager.enableDepth();

					GlStateManager.enableBlend();
				}
			}
			catch (final java.io.IOException e)
			{
				throw new RuntimeException(e);
			} //FML End
			this.framebuffer.unbindFramebuffer();

			if (OpenGlHelper.isFramebufferEnabled())
			{
				this.framebuffer.framebufferRender(k * j, l * j);
			}

			this.mc.updateDisplay();

			try
			{
				Thread.yield();
			}
			catch (final Exception var15)
			{
				;
			}
		}
	}
}
