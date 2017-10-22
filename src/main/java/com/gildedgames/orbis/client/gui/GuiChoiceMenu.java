package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiChoiceMenu extends GuiFrame
{

	private final static ResourceLocation GRADIENT_TEXTURE = AetherCore.getResource("orbis/godmode/overlay/choose_power_gradient.png");

	private final static ResourceLocation BACKDROP_TEXTURE = AetherCore.getResource("orbis/godmode/overlay/choose_power_backdrop.png");

	private final static ResourceLocation ARROW_TEXTURE = AetherCore.getResource("orbis/godmode/overlay/choose_power_arrow.png");

	private final static ResourceLocation CURSOR_TEXTURE = AetherCore.getResource("orbis/godmode/overlay/choose_power_cursor.png");

	private static boolean LAST_MOUSE_SET = false;

	private static float LAST_MOUSE_X, LAST_MOUSE_Y;

	private final float choiceRadius = 35;

	protected Choice[] choices;

	private GuiTexture arrow;

	private GuiText choiceName;

	private Choice hoveredChoice;

	public GuiChoiceMenu(final Choice... choices)
	{
		super(Dim2D.flush());

		this.choices = choices;
	}

	@Override
	public void onGuiClosed()
	{
		if (this.hoveredChoice != null)
		{
			this.hoveredChoice.onSelect(PlayerOrbisModule.get(mc.player));
		}

		GuiChoiceMenu.LAST_MOUSE_X = InputHelper.getMouseX();
		GuiChoiceMenu.LAST_MOUSE_Y = InputHelper.getMouseY();

		super.onGuiClosed();
	}

	@Override
	public void init()
	{
		final Pos2D center = InputHelper.getCenter();

		if (!GuiChoiceMenu.LAST_MOUSE_SET)
		{
			GuiChoiceMenu.LAST_MOUSE_X = center.x();
			GuiChoiceMenu.LAST_MOUSE_Y = center.y();

			GuiChoiceMenu.LAST_MOUSE_SET = true;
		}

		InputHelper.setMouseX(GuiChoiceMenu.LAST_MOUSE_X);
		InputHelper.setMouseY(GuiChoiceMenu.LAST_MOUSE_Y);

		final GuiTexture gradient = new GuiTexture(Dim2D.build().pos(center).center(true).width(125).height(125).flush(), GRADIENT_TEXTURE);
		final GuiTexture backdrop = new GuiTexture(Dim2D.build().pos(center).addX(-1).center(true).width(44).height(44).flush(), BACKDROP_TEXTURE);

		this.arrow = new GuiTexture(Dim2D.build().pos(center).addX(-0.5F).addY(-2).center(true).width(11).height(12).flush(), ARROW_TEXTURE);
		//this.arrow.dim().mod().origin(0, 3).flush();

		this.choiceName = new GuiText(Dim2D.build().center(true).pos(center).addY(47).flush(), null);

		this.addChild(gradient);
		this.addChild(backdrop);
		this.addChild(this.arrow);

		final float powerAngleStep = (float) Math.toDegrees((2 * Math.PI) / this.choices.length);

		float angle = -90;

		for (final Choice choice : this.choices)
		{
			final GuiTexture icon = choice.getIcon();

			final float x = this.getChoiceX(icon, angle, this.choiceRadius) + center.x();
			final float y = this.getChoiceY(icon, angle, this.choiceRadius) + center.y();

			icon.dim().mod().center(true).x(x).y(y).flush();

			this.addChild(icon);

			angle += powerAngleStep;
		}

		this.addChild(this.choiceName);
	}

	@Override
	public void draw()
	{
		final Pos2D center = Pos2D.flush(this.width / 2, this.height / 2);

		final float dx = center.x() - InputHelper.getMouseX();
		final float dy = center.y() - InputHelper.getMouseY();

		final float degrees = (float) (Math.toDegrees(Math.atan2(dy, dx))) - 90;

		this.arrow.dim().mod().degrees(degrees).flush();

		final float distance = (float) Math
				.sqrt((center.x() - InputHelper.getMouseX()) * (center.x() - InputHelper.getMouseX()) + (center.y() - InputHelper.getMouseY()) * (center.y()
						- InputHelper.getMouseY()));

		double closestDist = Double.MAX_VALUE;

		Choice closestChoice = null;

		for (final Choice choice : this.choices)
		{
			final GuiTexture icon = choice.getIcon();

			final double choiceDist = Math.sqrt((icon.dim().centerX() - InputHelper.getMouseX()) * (icon.dim().centerX() - InputHelper.getMouseX())
					+ (icon.dim().centerY() - InputHelper.getMouseY()) * (icon.dim().centerY() - InputHelper.getMouseY()));

			if (choiceDist < closestDist)
			{
				closestDist = choiceDist;
				closestChoice = choice;
			}
		}

		this.hoveredChoice = closestChoice;

		final float NORMAL_SCALE = 1F;
		final float SELECTED_SCALE = 1.1F;

		if (distance > this.choiceRadius + 10 && this.hoveredChoice != null)
		{
			final GuiTexture icon = this.hoveredChoice.getIcon();

			icon.dim().mod().scale(NORMAL_SCALE).flush();

			this.choiceName.setText(null);

			this.hoveredChoice = null;
		}
		else
		{
			this.choiceName.setText(new Text(new TextComponentString(this.hoveredChoice.name()), 1.0F));

			if (Mouse.isButtonDown(0))
			{
				closestChoice.onSelect(PlayerOrbisModule.get(mc.player));
			}

			for (final Choice choice : this.choices)
			{
				final GuiTexture icon = choice.getIcon();

				if (choice == closestChoice)
				{
					icon.dim().mod().scale(SELECTED_SCALE).flush();
				}
				else
				{
					icon.dim().mod().scale(NORMAL_SCALE).flush();
				}
			}
		}
	}

	private float getChoiceX(final GuiFrame gui, final float degreesAngle, final float radius)
	{
		return Math.round(radius * Math.cos(Math.toRadians(degreesAngle)));
	}

	private float getChoiceY(final GuiFrame gui, final float degreesAngle, final float radius)
	{
		return Math.round(radius * Math.sin(Math.toRadians(degreesAngle)));
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public interface Choice
	{

		void onSelect(PlayerOrbisModule module);

		GuiTexture getIcon();

		String name();

	}
}
