package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogAction;
import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogNode;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class DialogNodeFactory
{

	private final String id;

	private final List<ITextComponent> content = Lists.newArrayList();

	private final List<IDialogButton> buttons = Lists.newArrayList();

	private final List<IDialogAction> endActions = Lists.newArrayList();

	private ResourceLocation speaker;

	private DialogNodeFactory(String id)
	{
		this.id = id;
	}

	public static DialogNodeFactory build(String id)
	{
		return new DialogNodeFactory(id);
	}

	public DialogNodeFactory backToRootAction()
	{
		this.endActions.add(new DialogActionBackToRoot());

		return this;
	}

	// Creates a button that changes the node
	public DialogNodeFactory backToRootButton()
	{
		this.buttons.add(new DialogButtonBackToRoot());

		return this;
	}

	// Creates a button that changes the node
	public DialogNodeFactory button(String name, String node)
	{
		this.buttons.add(new DialogButtonChangeNode(name, node));

		return this;
	}

	public DialogNodeFactory closeButton(String name)
	{
		this.buttons.add(new DialogButtonClose(name));

		return this;
	}

	public DialogNodeFactory text(String name)
	{
		// uses vanilla text formatting
		this.content.add(new TextComponentTranslation(name));

		return this;
	}

	public DialogNodeFactory text(String name, int textCount)
	{
		for (int i = 0; i < textCount; i++)
		{
			this.content.add(new TextComponentTranslation(name + "." + String.valueOf(i + 1)));
		}

		return this;
	}

	public DialogNodeFactory speaker(ResourceLocation speaker)
	{
		this.speaker = speaker;

		return this;
	}

	public IDialogNode flush()
	{
		return new DialogNodeBasic(this.id, this.speaker, this.content, this.buttons, this.endActions);
	}

}
