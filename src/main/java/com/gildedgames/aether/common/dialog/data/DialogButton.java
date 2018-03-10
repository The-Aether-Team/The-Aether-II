package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.Collection;

public class DialogButton implements IDialogButton
{
	private final String label;

	private final Collection<IDialogAction> actions;

	public DialogButton(final String label, final Collection<IDialogAction> actions)
	{
		Validate.notNull(actions, "Action is null");

		this.label = label;
		this.actions = actions;
	}

	@Nonnull
	@Override
	public String getLabel()
	{
		return this.label;
	}

	@Nonnull
	@Override
	public ITextComponent getLocalizedLabel()
	{
		return new TextComponentString(this.label);
	}

	@Nonnull
	@Override
	public Collection<IDialogAction> getActions()
	{
		return this.actions;
	}
}
