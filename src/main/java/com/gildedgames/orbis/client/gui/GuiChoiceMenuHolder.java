package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.gui.util.GuiText;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChoiceMenuHolder extends GuiFrame
{
	private final static ResourceLocation CHOICE_BAR = AetherCore.getResource("orbis/godmode/overlay/choice_tab_bar.png");

	private final static ResourceLocation CHOICE_TAB = AetherCore.getResource("orbis/godmode/overlay/choice_tab.png");

	private final static ResourceLocation CHOICE_TAB_UNPRESSED = AetherCore.getResource("orbis/godmode/overlay/choice_tab_unpressed.png");

	private static int choicePageIndex;

	private final GuiChoiceMenu[] menus;

	private final GuiTexture[] tabs;

	public GuiChoiceMenuHolder(final GuiChoiceMenu... menus)
	{
		this.menus = menus;
		this.tabs = new GuiTexture[this.menus.length];
	}

	public GuiTexture getCurrentTab()
	{
		return this.tabs[this.choicePageIndex];
	}

	public GuiChoiceMenu getCurrentMenu()
	{
		return this.menus[this.choicePageIndex];
	}

	private void setCurrentPage(final int choicePageIndex)
	{
		if (choicePageIndex >= this.menus.length)
		{
			return;
		}

		this.choicePageIndex = choicePageIndex;

		if (this.choicePageIndex >= this.menus.length)
		{
			this.choicePageIndex = 0;
		}

		if (this.choicePageIndex < 0)
		{
			this.choicePageIndex = this.menus.length - 1;
		}

		for (final GuiChoiceMenu menu : this.menus)
		{
			menu.setVisible(false);
			menu.setEnabled(false);
		}

		for (final GuiTexture tab : this.tabs)
		{
			tab.setResourceLocation(CHOICE_TAB_UNPRESSED);
		}

		this.getCurrentMenu().setEnabled(true);
		this.getCurrentMenu().setVisible(true);

		this.getCurrentTab().setResourceLocation(CHOICE_TAB);
	}

	@Override
	public void onMouseWheel(final int state)
	{
		if (state > 0)
		{
			this.setCurrentPage(this.choicePageIndex + 1);
		}
		else if (state < 0)
		{
			this.setCurrentPage(this.choicePageIndex - 1);
		}
	}

	@Override
	public void init()
	{
		final Pos2D center = InputHelper.getCenter();

		int i = 0;

		for (final GuiChoiceMenu menu : this.menus)
		{
			menu.setVisible(false);
			menu.setEnabled(false);

			this.addChild(menu);

			final GuiTexture choiceTab = new GuiTexture(Dim2D.build().pos(center).addY(-77).addX(-33).addX(i * 22).width(22).height(19).flush(),
					CHOICE_TAB_UNPRESSED);

			final GuiText number = new GuiText(Dim2D.build().pos(8, 7).flush(), new Text(new TextComponentString(String.valueOf(i + 1)), 1.0F));

			choiceTab.addChild(number);

			this.tabs[i] = choiceTab;

			this.addChild(choiceTab);

			i++;
		}

		this.getCurrentMenu().setEnabled(true);
		this.getCurrentMenu().setVisible(true);

		this.getCurrentTab().setResourceLocation(CHOICE_TAB);

		final GuiTexture choiceBar = new GuiTexture(Dim2D.build().pos(center).addY(-58).width(74).height(6).centerX(true).flush(), CHOICE_BAR);

		this.addChild(choiceBar);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		if (mouseButton == 0)
		{
			int i = 0;

			for (final GuiTexture tab : this.tabs)
			{
				if (InputHelper.isHovered(tab))
				{
					this.setCurrentPage(i);

					break;
				}

				i++;
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		switch (keyCode)
		{
			case Keyboard.KEY_1:
				this.setCurrentPage(0);
				break;
			case Keyboard.KEY_2:
				this.setCurrentPage(1);
				break;
			case Keyboard.KEY_3:
				this.setCurrentPage(2);
				break;
			case Keyboard.KEY_4:
				this.setCurrentPage(3);
				break;
			case Keyboard.KEY_5:
				this.setCurrentPage(4);
				break;
			case Keyboard.KEY_6:
				this.setCurrentPage(5);
				break;
			case Keyboard.KEY_7:
				this.setCurrentPage(6);
				break;
			case Keyboard.KEY_8:
				this.setCurrentPage(7);
				break;
			case Keyboard.KEY_9:
				this.setCurrentPage(8);
				break;
		}

		super.keyTyped(typedChar, keyCode);
	}
}
