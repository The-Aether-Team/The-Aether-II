package com.gildedgames.aether.client.ui.minecraft.util.wrappers;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.ButtonState;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.common.util.ObjectFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.util.List;

public class MinecraftButton extends GuiFrame
{

	protected final static Minecraft mc = Minecraft.getMinecraft();

	protected String text;

	protected GuiButton button;

	public MinecraftButton(GuiButton button, boolean centered)
	{
		super(Dim2D.build().pos(button.xPosition, button.yPosition).area(button.getButtonWidth(), button.height).center(centered).flush());

		this.button = button;
	}

	public MinecraftButton(Rect dim, String text)
	{
		super(dim);

		this.text = text;

		this.button = new GuiButton(-1, (int) dim.x(), (int) dim.y(), (int) dim.width(), (int) dim.height(), text);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);

		this.button.enabled = enabled;
	}

	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);

		this.button.visible = visible;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		this.button.displayString = this.text;
		this.button.drawButton(mc, (int) input.getMouseX(), (int) input.getMouseY());

		this.button.xPosition = (int) this.dim().x();
		this.button.yPosition = (int) this.dim().y();

		this.button.width = (int) this.dim().width();
		this.button.height = (int) this.dim().height();
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (pool.contains(ButtonState.PRESS))
		{
			this.button.mousePressed(mc, (int) input.getMouseX(), (int) input.getMouseY());
		}

		if (pool.contains(ButtonState.RELEASE))
		{
			this.button.mouseReleased((int) input.getMouseX(), (int) input.getMouseY());
		}

		super.onMouseInput(pool, input);
	}

	@Override
	public boolean query(Object... input)
	{
		List<String> strings = ObjectFilter.getTypesFrom(input, String.class);

		for (String string : strings)
		{
			if (string != null && this.text.toLowerCase().contains(string.toLowerCase()))
			{
				return true;
			}
		}

		return false;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}
