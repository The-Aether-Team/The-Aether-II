package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;

public class DialogButton implements IDialogButton
{
	private final String label;

	private final IDialogAction action;

	public DialogButton(String label, IDialogAction action)
	{
		Validate.notNull(action, "Action is null");

		this.label = label;
		this.action = action;
	}

	@Nonnull
	@Override
	public ITextComponent getLocalizedLabel()
	{
		return new TextComponentString(this.label);
	}

	@Nonnull
	@Override
	public IDialogAction getAction()
	{
		return this.action;
	}
}
