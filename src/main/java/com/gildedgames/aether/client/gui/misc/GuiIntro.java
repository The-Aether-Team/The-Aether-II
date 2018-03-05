package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiFrameUtils;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.rect.Dim2D;
import com.gildedgames.orbis.client.rect.Pos2D;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.opengl.GL11;

public class GuiIntro extends GuiFrame
{
	private static final ResourceLocation GILDED_GAMES = AetherCore.getResource("textures/gui/intro/gg_logo.png");

	private static final ResourceLocation HIGHLANDS = AetherCore.getResource("textures/gui/intro/highlands.png");

	private static final ResourceLocation HUE_BACKGROUND = AetherCore.getResource("textures/gui/intro/hue_background.png");

	private GuiTexture ggLogo, highlands;

	private GuiText proudlyPresents;

	private long timeStarted;

	private boolean playedMusic;

	public GuiIntro()
	{
		preventInnerTyping = true;
	}

	private double getSecondsSinceStart()
	{
		return (System.currentTimeMillis() - this.timeStarted) / 1000.0D;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		AetherMusicManager.INSTANCE.stopMusic();
	}

	@Override
	public void preDrawChildren()
	{

	}

	@Override
	public void init()
	{
		final Pos2D center = InputHelper.getCenter();

		this.ggLogo = new GuiTexture(Dim2D.build().scale(0.5F).width(256).height(242).center(true).pos(center).flush(), GILDED_GAMES);
		this.highlands = new GuiTexture(Dim2D.build().scale(0.45F).width(512).height(235).center(true).pos(center).flush(), HIGHLANDS);
		this.proudlyPresents = new GuiText(Dim2D.build().center(true).pos(center).addY(70).flush(),
				new Text(new TextComponentString("Proudly Presents..."), 1.0F));

		this.proudlyPresents.dim().mod().addX(3).flush();

		this.proudlyPresents.setVisible(false);

		this.highlands.setVisible(false);

		this.addChildren(this.ggLogo, this.proudlyPresents, this.highlands);

		this.timeStarted = System.currentTimeMillis();
	}

	@Override
	public void draw()
	{
		float bgAlpha = Math.max(0.0F, 1.0F - (float) ((this.getSecondsSinceStart() - 108.3D) / 10.0D));

		if (this.getSecondsSinceStart() < 108.3)
		{
			bgAlpha = 1.0F;
		}

		final int bg = GuiFrameUtils.changeAlpha(0xFF000000, (int) (bgAlpha * 255));

		this.drawGradientRect(0, 0, this.width, this.height, bg, bg);

		if (this.getSecondsSinceStart() <= 40)
		{
			final float fade = Math.min(1.0F, (float) (this.getSecondsSinceStart() / 40.0D));
			final float scale = 0.25F + Math.max(0.0F, ((fade * 0.5F) / 2.0F));

			this.ggLogo.dim().mod().scale(scale).flush();
			this.ggLogo.setAlpha(fade);

			if (this.getSecondsSinceStart() >= 30)
			{
				this.proudlyPresents.setAlpha(Math.min(1.0F, Math.max(0.0F, (float) ((this.getSecondsSinceStart() - 30) / 10.0D))));

				this.proudlyPresents.setVisible(true);
			}
			else
			{
				this.proudlyPresents.setVisible(false);
			}
		}
		else if (this.getSecondsSinceStart() <= 50)
		{
			final float fade = Math.max(0.0F, 1.0F - Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 40) / 10.0D)));

			this.ggLogo.setAlpha(fade);
			this.proudlyPresents.setAlpha(fade);
		}
		else
		{
			this.ggLogo.setVisible(false);
			this.proudlyPresents.setVisible(false);
		}

		if (this.getSecondsSinceStart() >= 58.3 && this.getSecondsSinceStart() < 68.3)
		{
			this.highlands.setVisible(true);

			final float scale = (float) (((this.getSecondsSinceStart() - 58.3) / 10.0D) * 0.05F);

			this.highlands.dim().mod().scale(0.45F + scale).flush();
		}

		if (this.getSecondsSinceStart() >= 68.3)
		{
			final float dif = (float) ((this.getSecondsSinceStart() - 68.3D) * 10.0D);

			this.highlands.dim().mod().y(InputHelper.getCenter().y() - dif).flush();

			GlStateManager.pushMatrix();

			float alpha = Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 68.3D) / 10.0D));

			if (this.getSecondsSinceStart() >= 98.3D)
			{
				alpha = Math.min(1.0F, 1.0F - (float) ((this.getSecondsSinceStart() - 98.3D) / 10.0D));
			}

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager
					.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
							GlStateManager.SourceFactor.ZERO,
							GlStateManager.DestFactor.ONE);

			GL11.glEnable(GL11.GL_ALPHA_TEST);

			GlStateManager.enableAlpha();

			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

			this.mc.getTextureManager().bindTexture(HUE_BACKGROUND);

			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width,
					this.height,
					this.width, this.height);

			GlStateManager.popMatrix();
		}

		if (this.getSecondsSinceStart() >= 118.3)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
		}

		if (!this.playedMusic)
		{
			Minecraft.getMinecraft().getSoundHandler().stopSounds();
			AetherMusicManager.INSTANCE.playMusic(new SoundEvent(AetherCore.getResource("music.intro")));
			this.playedMusic = true;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}
}
