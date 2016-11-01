package com.gildedgames.aether.client.gui.menu;

import com.gildedgames.aether.client.ui.UiManager;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.MinecraftAssetLocation;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.util.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class MainMenuOverlay extends GuiFrame
{

	private static final AssetLocation UNHOVERED = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/button.png");

	private static final AssetLocation HOVERED = new MinecraftAssetLocation(AetherCore.MOD_ID, "textures/gui/main_menu/button_hovered.png");

	private int ticks;

	public MainMenuOverlay()
	{
		this.dim().mod().area(28, 28).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Button button = new Button(Dim2D.build().pos(5, 5).area(28, 28).flush(), GuiFactory.texture(MainMenuOverlay.UNHOVERED), GuiFactory.texture(HOVERED), GuiFactory.texture(MainMenuOverlay.UNHOVERED));

		button.events().set("press", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				UiManager.inst().open("aetherMenu", new AetherMenu());
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

		this.content().set("button", GuiFactory.pressSound(button));
	}

	@Override
	public boolean isEnabled()
	{
		return Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && this.ticks > 10;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (!(Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu))
		{
			this.ticks = 0;
			return;
		}

		this.ticks++;

		super.draw(graphics, input);
	}

}
