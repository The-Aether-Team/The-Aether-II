package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogController;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class DialogButtonChangeNode implements IDialogButton
{

	private final String name, node;

	public DialogButtonChangeNode(String name, String node)
	{
		this.name = name;
		this.node = node;
	}

	@Nonnull
	@Override
	public String getUnlocalizedLabel()
	{
		return this.name;
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
