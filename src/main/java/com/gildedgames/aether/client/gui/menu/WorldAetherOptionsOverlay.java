package com.gildedgames.aether.client.gui.menu;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.event.view.MouseEventGui;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.*;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.minecraft.util.wrappers.MinecraftButton;
import com.gildedgames.aether.client.ui.util.InputHelper;
import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class WorldAetherOptionsOverlay extends GuiFrame
{

	public static boolean toggle;

	private MinecraftButton button;

	public WorldAetherOptionsOverlay()
	{
		this.dim().mod().area(28, 28).flush();
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Rect dim = Dim2D.build().area(150, 20).center(true).pos(InputHelper.getCenter(input).clone().y(172).flush()).flush();

		this.button = new MinecraftButton(dim, "Aether Start: OFF");

		this.button.events().set("pressed", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				WorldAetherOptionsOverlay.this.toggle = !WorldAetherOptionsOverlay.this.toggle;

				WorldAetherOptionsOverlay.this.button.setText("Aether Start: " + (WorldAetherOptionsOverlay.this.toggle ? "ON" : "OFF"));
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

		this.content().set("button", GuiFactory.pressSound(this.button));
	}

	@Override
	public boolean isEnabled()
	{
		if (Minecraft.getMinecraft().currentScreen instanceof GuiWorldSelection)
		{
			if (this.button != null)
			{
				this.button.setText("Aether Start: OFF");
			}

			this.toggle = false;
			return false;
		}

		if (!(Minecraft.getMinecraft().currentScreen instanceof GuiCreateWorld))
		{
			return false;
		}

		GuiCreateWorld gui = (GuiCreateWorld) Minecraft.getMinecraft().currentScreen;
		boolean inMoreWorldOptionsDisplay = ObfuscationReflectionHelper.getPrivateValue(GuiCreateWorld.class, gui, ReflectionAether.IN_MORE_WORLD_OPTIONS_DISPLAY.getMappings());

		if (inMoreWorldOptionsDisplay)
		{
			return false;
		}

		return true;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.draw(graphics, input);
	}

}
