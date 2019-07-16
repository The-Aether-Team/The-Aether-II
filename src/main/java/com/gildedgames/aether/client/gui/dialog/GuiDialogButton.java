package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.dialog.IDialogButton;

public class GuiDialogButton extends GuiFlatButton
{

	private final IDialogButton buttonData;

	public GuiDialogButton(final int x, final int y, final IDialogButton buttonData, IPressable pressable)
	{
		super(x, y, 0, 15, "", pressable);

		this.buttonData = buttonData;
	}

	public IDialogButton getButtonData()
	{
		return this.buttonData;
	}

	public String getText()
	{
		return this.buttonData.getLocalizedLabel().getFormattedText();
	}

}
