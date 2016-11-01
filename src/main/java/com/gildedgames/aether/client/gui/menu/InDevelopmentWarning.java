package com.gildedgames.aether.client.gui.menu;

import com.gildedgames.aether.client.ui.UiManager;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.client.ui.util.InputHelper;
import com.gildedgames.aether.client.ui.util.ScrollBar;
import com.gildedgames.aether.client.ui.util.Text;
import com.gildedgames.aether.client.ui.util.TextureElement;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.MinecraftAssetLocation;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.util.decorators.ScrollableGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InDevelopmentWarning extends GuiFrame
{

	private static final AssetLocation BG = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/bg.png");

	private static final AssetLocation LOGO = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/logo.png");

	public InDevelopmentWarning()
	{
		this.dim().mod().area(28, 28).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiFrame button = GuiFactory.button(InputHelper.getCenter(input).clone().addY(100).flush(), 80, I18n.format("indev.button"), true);

		button.events().set("press", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				File areaFile = new File(Minecraft.getMinecraft().mcDataDir, "//config/in_development_displayed.dat");

				if (!areaFile.exists())
				{
					try
					{
						areaFile.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				UiManager.inst().close();
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{

			}

			@Override
			public void initEvent()
			{

			}

		});

		TextureElement bg = GuiFactory.texture(BG, Dim2D.build().area(input.getScreenWidth(), input.getScreenHeight()).flush());

		if (input.getScreenWidth() > bg.dim().width() || input.getScreenHeight() > bg.dim().height())
		{
			float scale = input.getScreenHeight() > input.getScreenWidth() ? Math.abs(bg.dim().height() - input.getScreenHeight()) / bg.dim().height() : Math.abs(bg.dim().width() - input.getScreenWidth()) / bg.dim().width();

			bg.dim().mod().scale(1.0F + scale).flush();
		}

		TextureElement logo = GuiFactory.texture(LOGO);

		logo.dim().mod().area(275, 114).pos(InputHelper.getTopCenter(input)).centerX(true).addPos(0, 5).scale(0.5F).flush();

		this.content().set("bg", bg);
		this.content().set("logo", logo);
		this.content().set("button", button);

		Text[] texts = new Text[12];

		texts[0] = GuiFactory.text(TextFormatting.BOLD + I18n.format("indev.line1"), Color.WHITE);
		texts[1] = GuiFactory.text(" ", Color.WHITE);
		texts[2] = GuiFactory.text(I18n.format("indev.line2"), Color.LIGHT_GRAY);
		texts[3] = GuiFactory.text(" ", Color.WHITE);
		texts[4] = GuiFactory.text("\u2022 " + I18n.format("indev.line3"), Color.WHITE);
		texts[5] = GuiFactory.text("\u2022 " + I18n.format("indev.line4"), Color.WHITE);
		texts[6] = GuiFactory.text("\u2022 " + I18n.format("indev.line5"), Color.WHITE);
		texts[7] = GuiFactory.text("\u2022 " + I18n.format("indev.line6"), Color.WHITE);
		texts[8] = GuiFactory.text(" ", Color.WHITE);
		texts[9] = GuiFactory.text(I18n.format("indev.line7"), Color.LIGHT_GRAY);
		texts[10] = GuiFactory.text(" ", Color.WHITE);
		texts[11] = GuiFactory.text(I18n.format("indev.line8"), Color.LIGHT_GRAY);

		Rect rect = Dim2D.build().area(350, 130).pos(InputHelper.getCenter(input).clone().addY(15).flush()).center(true).flush();

		GuiFrame text = GuiFactory.textBox(rect, false, texts);

		ScrollBar scrollBar = GuiFactory.createScrollBar();

		scrollBar.setScrollSpeed(0.2F);

		text = new ScrollableGui(rect, text, scrollBar, null, null, 7, 6, true);

		this.content().set("text", text);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

}
