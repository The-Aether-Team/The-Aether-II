package com.gildedgames.aether.client.gui.container.guidebook.discovery.stats;

import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiLibHelper;
import com.gildedgames.orbis.lib.client.rect.Dim2D;

public class GuiStat extends GuiElement
{
	private final GuiTexture icon;

	private final Text text;

	private GuiText textElement;

	public GuiStat(final GuiTexture icon, final Text text)
	{
		super(Dim2D.flush(), true);

		this.text = text;
		this.icon = icon;
	}

	@Override
	public void build()
	{
		this.textElement = new GuiText(Dim2D.build().x(this.icon.dim().maxX() + 5).y(this.icon.dim().centerY()).flush(), this.text);
		this.textElement.dim().mod().centerY(true).flush();

		this.context().addChildren(this.icon, this.textElement);

		GuiLibHelper.assembleMinMaxArea(this);
	}
}
