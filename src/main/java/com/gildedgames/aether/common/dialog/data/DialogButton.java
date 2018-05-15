package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogButton;
import com.gildedgames.aether.api.dialog.IDialogCondition;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.Collection;

public class DialogButton implements IDialogButton
{
	private final String label;

	private final Collection<IDialogAction> actions;

	private Collection<IDialogCondition> conditions;

	private Collection<IDialogCondition> orConditions;

	public DialogButton(Collection<IDialogCondition> orConditions, Collection<IDialogCondition> conditions, final String label,
			final Collection<IDialogAction> actions)
	{
		Validate.notNull(orConditions, "orConditions are null");
		Validate.notNull(conditions, "Conditions are null");
		Validate.notNull(actions, "Actions are null");

		this.orConditions = orConditions;
		this.conditions = conditions;
		this.label = label;
		this.actions = actions;
	}

	@Nonnull
	@Override
	public Collection<IDialogCondition> getOrConditions()
	{
		return this.orConditions;
	}

	@Nonnull
	@Override
	public Collection<IDialogCondition> getConditions()
	{
		return this.conditions;
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
