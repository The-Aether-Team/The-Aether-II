package com.gildedgames.aether.client.ui.minecraft.util.decorators;

import com.gildedgames.aether.client.ui.UiManager;
import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiDecorator;
import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.KeyboardInputPool;
import com.gildedgames.aether.client.ui.util.RectangleElement;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class MinecraftGui extends GuiDecorator<Gui>
{

	private boolean drawBackground = true;

	private Minecraft mc = Minecraft.getMinecraft();

	public MinecraftGui(Gui view)
	{
		super(view);
	}

	public boolean shouldDrawBackground()
	{
		return this.drawBackground;
	}

	public MinecraftGui setDrawBackground(boolean drawBackground)
	{
		this.drawBackground = drawBackground;

		return this;
	}

	@Override
	public void preInitContent(InputProvider input)
	{
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));

			Rect dim = Dim2D.build().area(input.getScreenWidth(), input.getScreenHeight()).flush();

			this.content().set("darkBackground", new RectangleElement(dim, startColor, endColor));
		}
	}

	@Override
	protected void postInitContent(InputProvider input)
	{

	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		if (super.onKeyboardInput(pool, input))
		{
			return true;
		}

		if (pool.has(Keyboard.KEY_ESCAPE) || pool.has(this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			UiManager.inst().close();
		}

		return false;
	}

}
