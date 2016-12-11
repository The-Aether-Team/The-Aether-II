package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogAction;
import com.gildedgames.aether.common.dialog.IDialogButton;
import com.gildedgames.aether.common.dialog.IDialogNode;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collection;
import java.util.List;

public class DialogNodeBasic implements IDialogNode
{

	private final String id;

	private final ResourceLocation speaker;

	// Lists should be immutable
	private final List<ITextComponent> content;

	private final List<IDialogButton> buttons;

	private final List<IDialogAction> endActions;

	public DialogNodeBasic(String id, ResourceLocation speaker, List<ITextComponent> content, List<IDialogButton> buttons, List<IDialogAction> endActions)
	{
		this.id = id;

		this.speaker = speaker;
		this.content = content;
		this.buttons = buttons;
		this.endActions = endActions;
	}

	@Override
	public Collection<IDialogButton> getButtons() {
		return this.buttons;
	}

	@Override
	public Collection<ITextComponent> getContent() {
		return this.content;
	}

	@Override
	public Collection<IDialogAction> getEndActions() { return this.endActions; }

	@Override
	public ResourceLocation getSpeaker() {
		return this.speaker;
	}

	@Override
	public String getIdentifier() {
		return this.id;
	}
}
