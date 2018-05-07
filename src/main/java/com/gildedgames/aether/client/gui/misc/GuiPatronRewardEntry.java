package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class GuiPatronRewardEntry extends GuiFrame
{
	private static final ResourceLocation BUTTON = AetherCore.getResource("textures/gui/patron/reward_button.png");

	private static final ResourceLocation BUTTON_HOVERED = AetherCore.getResource("textures/gui/patron/reward_button_hovered.png");

	public static int SELECTED_INDEX = -1;

	private String name;

	private ResourceLocation icon;

	private int index;

	private GuiAbstractButton button;

	/**
	 * @param name The text for ths button
	 * @param icon Has to be a 16x16 icon
	 */
	public GuiPatronRewardEntry(String name, ResourceLocation icon, int index)
	{
		super(Dim2D.build().width(110).height(22).flush());

		this.name = name;
		this.icon = icon;
		this.index = index;
	}

	@Override
	public void init()
	{
		GuiTexture icon = new GuiTexture(Dim2D.build().x(4).y(3).width(16).height(16).flush(), this.icon);

		this.button = new GuiAbstractButton(Dim2D.build().width(110).height(22).flush(),
				new GuiTexture(Dim2D.build().width(110).height(22).flush(), BUTTON),
				new GuiTexture(Dim2D.build().width(110).height(22).flush(), BUTTON_HOVERED),
				new GuiTexture(Dim2D.build().width(110).height(22).flush(), BUTTON));

		this.button.addClickEvent(() -> SELECTED_INDEX = this.index);

		GuiText text = new GuiText(
				Dim2D.build().centerY(true).x(25).y(this.dim().originalState().height() / 2).addY(2).flush(),
				new Text(new TextComponentString(I18n.format(this.name)), 1.0F));

		this.addChildren(this.button, text, icon);
	}

	@Override
	public void draw()
	{
		this.button.setSelected(this.index == SELECTED_INDEX);
	}
}
