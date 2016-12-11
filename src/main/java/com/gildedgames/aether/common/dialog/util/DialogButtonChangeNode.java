package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class DialogButtonChangeNode implements IDialogButton
{

	private final String node;

	private final ITextComponent label;

	public DialogButtonChangeNode(ITextComponent label, String node)
	{
		this.label = label;
		this.node = node;
	}

	@Nonnull
	@Override
	public ITextComponent getLabel()
	{
		return this.label;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return null;
	}

	@Override
	public void onClicked(IDialogController controller)
	{
		controller.setNode(this.node);
	}

}
