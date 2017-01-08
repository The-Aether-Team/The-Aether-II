package com.gildedgames.aether.client.ui.minecraft.util.decorators;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.common.GuiDecorator;
import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.RectModifier;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.minecraft.util.GuiFactory;
import com.gildedgames.aether.client.ui.util.TextureElement;

public class MinecraftBackpanel extends GuiDecorator<Gui>
{

	private final float leftPadding, rightPadding, topPadding, bottomPadding;

	public MinecraftBackpanel(Gui gui, final float padding)
	{
		this(gui, padding, padding, padding, padding);
	}

	public MinecraftBackpanel(Gui gui, final float leftPadding, final float rightPadding, final float topPadding, final float bottomPadding)
	{
		super(gui);

		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;

		this.topPadding = topPadding;
		this.bottomPadding = bottomPadding;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{
		final TextureElement backPanel = GuiFactory.panel(Dim2D.build().flush());

		backPanel.dim().mod().area(
				this.leftPadding + this.rightPadding,
				this.topPadding + this.bottomPadding).pos(-this.leftPadding, -this.topPadding).flush();

		backPanel.dim().add(this, RectModifier.ModifierType.AREA);

		this.content().set("backPanel", backPanel);
	}

	@Override
	protected void postInitContent(InputProvider input)
	{

	}

}
