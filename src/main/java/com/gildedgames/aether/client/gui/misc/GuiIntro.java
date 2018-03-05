package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.client.gui.dialog.GuiNextArrow;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTextBox;
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

import java.io.IOException;

public class GuiIntro extends GuiFrame
{
	private static final ResourceLocation GILDED_GAMES = AetherCore.getResource("textures/gui/intro/gg_logo.png");

	private static final ResourceLocation HIGHLANDS = AetherCore.getResource("textures/gui/intro/highlands.png");

	private static final ResourceLocation HUE_BACKGROUND = AetherCore.getResource("textures/gui/intro/hue_background.png");

	private int tipIndex = 0;

	private GuiTexture ggLogo, highlands;

	private GuiNextArrow nextArrow;

	private GuiText proudlyPresents;

	private GuiTextBox prologue, tip1, tip2, tip3;

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
		this.prologue = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).addY(30).flush(), false,
				new Text(new TextComponentString(
						"200 years after a lone adventurer defeated the tyrannical Sun Spirit, Karthuul, a new traveller is about to enter the Aether…"),
						1.0F));

		this.tip1 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentString(
						"The world you’re about to enter is completely separate from Minecraft, in both lore, time and space."),
						1.0F));

		this.tip2 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentString(
						"Your inventory will be separate from your Minecraft save, but you will be able to send items back to your original world."),
						1.0F));

		this.tip3 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentString(
						"Let the journey begin…"),
						1.0F));

		this.nextArrow = new GuiNextArrow();

		this.nextArrow.dim().mod().center(true).pos(center).addY(-7).addX(-170).flush();

		this.proudlyPresents.dim().mod().addX(3).flush();

		this.proudlyPresents.setVisible(false);
		this.prologue.setVisible(false);
		this.highlands.setVisible(false);

		this.nextArrow.setVisible(false);
		this.tip1.setVisible(false);
		this.tip2.setVisible(false);
		this.tip3.setVisible(false);

		this.addChildren(this.ggLogo, this.proudlyPresents, this.highlands, this.prologue, this.tip1, this.tip2, this.tip3, this.nextArrow);

		this.timeStarted = System.currentTimeMillis();
	}

	@Override
	public void draw()
	{
		final int bg = 0xFF000000;

		this.drawGradientRect(0, 0, this.width, this.height, bg, bg);

		if (this.getSecondsSinceStart() <= 15)
		{
			final float fade = Math.min(1.0F, (float) ((this.getSecondsSinceStart()) / 15.0D));

			this.ggLogo.setAlpha(fade);

			if (this.getSecondsSinceStart() >= 10)
			{
				this.proudlyPresents.setAlpha(Math.min(1.0F, Math.max(0.0F, (float) ((this.getSecondsSinceStart() - 10) / 5.0D))));

				this.proudlyPresents.setVisible(true);
			}
			else
			{
				this.proudlyPresents.setVisible(false);
			}
		}
		else if (this.getSecondsSinceStart() <= 20)
		{
			final float fade = Math.max(0.0F, 1.0F - Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 15) / 5.0D)));

			this.ggLogo.setAlpha(fade);
			this.proudlyPresents.setAlpha(fade);
		}
		else
		{
			this.ggLogo.setVisible(false);
			this.proudlyPresents.setVisible(false);
		}

		if (this.getSecondsSinceStart() >= 20 && this.getSecondsSinceStart() < 30)
		{
			final float alpha = (float) Math.min(1.0F, (this.getSecondsSinceStart() - 20) / 10.0D);

			this.highlands.setAlpha(alpha);

			this.highlands.setVisible(true);
		}

		if (this.getSecondsSinceStart() >= 30)
		{
			final float dif = (float) ((this.getSecondsSinceStart() - 30D) * 10.0D);

			this.highlands.dim().mod().y(InputHelper.getCenter().y() - Math.min(55, dif)).flush();

			GlStateManager.pushMatrix();

			float alpha = Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 30D) / 10.0D));
			float pAlpha = Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 32.5D) / 10.0D));

			if (this.getSecondsSinceStart() >= 42.5D)
			{
				pAlpha = alpha = Math.min(1.0F, 1.0F - (float) ((this.getSecondsSinceStart() - 42.5D) / 10.0D));

				this.highlands.setAlpha(alpha);
			}

			this.prologue.setAlpha(pAlpha);
			this.prologue.setVisible(true);

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

		if (this.getSecondsSinceStart() >= 60)
		{
			this.tip1.setVisible(this.tipIndex == 0);
			this.tip2.setVisible(this.tipIndex == 1);
			this.tip3.setVisible(this.tipIndex == 2);

			this.nextArrow.setVisible(true);
		}

		if (!this.playedMusic)
		{
			Minecraft.getMinecraft().getSoundHandler().stopSounds();
			AetherMusicManager.INSTANCE.playMusic(new SoundEvent(AetherCore.getResource("music.intro")));
			this.playedMusic = true;
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.getSecondsSinceStart() >= 60)
		{
			if (this.tipIndex >= 2)
			{
				ClientEventHandler.drawBlackFade();
				Minecraft.getMinecraft().displayGuiScreen(null);
			}

			this.tipIndex++;
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}
}
