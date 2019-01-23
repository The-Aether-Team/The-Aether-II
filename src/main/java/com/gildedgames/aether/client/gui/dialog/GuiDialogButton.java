package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.dialog.IDialogButton;

public class GuiDialogButton extends GuiFlatButton
{

	private final IDialogButton buttonData;

	public GuiDialogButton(final int buttonId, final int x, final int y, final IDialogButton buttonData)
	{
		super(buttonId, x, y, 0, 15, "");

		this.buttonData = buttonData;
	}

	public IDialogButton getButtonData()
	{
		return this.buttonData;
	}

	@Override
	public String getText()
	{
		return this.buttonData.getLocalizedLabel().getFormattedText();
	}

}
