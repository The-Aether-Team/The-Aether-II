package com.gildedgames.aether.client.gui.main_menu;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.AssetLocation;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.event.view.MouseEventGui;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.*;
import com.gildedgames.util.modules.ui.util.Button;
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
				UiModule.locate().open("aetherMenu", new AetherMenu());
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
