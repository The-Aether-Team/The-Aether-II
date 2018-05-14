package com.gildedgames.aether.client.gui.misc;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;

/**
 * Ugly hack to override loading renderer when teleporting to Necromancer Tower
 */
public class CustomLoadingRenderer extends LoadingScreenRenderer
{
	public static ICustomLoading CURRENT;

	/** A reference to the Minecraft object. */
	private final Minecraft mc;

	private final Framebuffer framebuffer;

	private final LoadingScreenRenderer original;

	/** The system's time represented in milliseconds. */
	private long systemTime = Minecraft.getSystemTime();

	public CustomLoadingRenderer(final Minecraft mcIn, final LoadingScreenRenderer original)
	{
		super(mcIn);

		this.mc = mcIn;
		this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
		this.framebuffer.setFramebufferFilter(9728);

		this.original = original;
	}

	@Override
	public void resetProgressAndMessage(final String message)
	{
		if (CURRENT == null)
		{
			this.original.resetProgressAndMessage(message);
			return;
		}

		super.resetProgressAndMessage(message);
	}

	@Override
	public void displaySavingString(final String message)
	{
		if (CURRENT == null)
		{
			this.original.displaySavingString(message);
			return;
		}

		super.displaySavingString(message);
	}

	@Override
	public void displayLoadingString(final String message)
	{
		if (CURRENT == null)
		{
			this.original.displayLoadingString(message);
			return;
		}

		super.displayLoadingString(message);
	}

	@Override
	public void setDoneWorking()
	{
		if (CURRENT == null)
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
		if (CURRENT == null)
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

					CURRENT.drawCustomLoading();

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
			catch (final Exception ignored)
			{
			}
		}
	}

	public interface ICustomLoading
	{
		void drawCustomLoading();
	}
}
