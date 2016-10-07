package com.gildedgames.aether.client.main_menu;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.AssetLocation;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.event.view.MouseEventGui;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.ButtonState;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.MouseButton;
import com.gildedgames.util.modules.ui.input.MouseInput;
import com.gildedgames.util.modules.ui.input.MouseInputPool;
import com.gildedgames.util.modules.ui.util.Button;
import com.gildedgames.util.modules.ui.util.InputHelper;
import com.gildedgames.util.modules.ui.util.Text;
import com.gildedgames.util.modules.ui.util.TextureElement;
import com.gildedgames.util.modules.ui.util.decorators.ScrollingGui;

import java.awt.*;

public class AetherMenu extends GuiFrame
{

	private static final AssetLocation UNHOVERED = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/button.png");

	private static final AssetLocation HOVERED = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/button_hovered.png");

	private static final AssetLocation BG = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/bg.png");

	private static final AssetLocation LOGO = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/logo.png");

	public AetherMenu()
	{
		this.dim().mod().area(28, 28).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Button button = new Button(Dim2D.build().pos(5, 5).area(28, 28).flush(), GuiFactory.texture(AetherMenu.UNHOVERED), GuiFactory.texture(HOVERED), GuiFactory.texture(AetherMenu.UNHOVERED));

		button.events().set("press", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				UiModule.locate().close();
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
		TextureElement logo = GuiFactory.texture(LOGO);

		logo.dim().mod().area(275, 114).pos(InputHelper.getTopCenter(input)).centerX(true).addPos(0, 5).scale(0.5F).flush();

		this.content().set("bg", bg);

		this.content().set("logo", logo);

		this.content().set("button", GuiFactory.pressSound(button));

		Text[] texts = new Text[50];

		for (int i = 0; i < 50; i++)
		{
			texts[i] = GuiFactory.text("Patreon username", Color.WHITE);
		}

		GuiFrame text = GuiFactory.centeredTextBox(Dim2D.build().area(200, 200).pos(InputHelper.getCenter(input)).center(true).flush(), false, texts);

		text = new ScrollingGui(text.dim(), text);

		this.content().set("text", text);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		this.content().get("text", ScrollingGui.class).scrollPercent += 0.001F;
	}

}
