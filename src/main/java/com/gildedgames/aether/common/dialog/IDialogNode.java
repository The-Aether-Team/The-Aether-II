package com.gildedgames.aether.common.dialog;

import com.sun.istack.internal.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IDialogNode
{

	/**
	 * Returns a list of dialog buttons. If the list is empty, the dialog controller should simply
	 * move the dialog forward.
	 */
	@Nonnull
	Collection<IDialogButton> getButtons();

	/**
	 * Returns the content of this node. Basically the "body" of the dialogue.
	 */
	@Nonnull
	Collection<ITextComponent> getContent();

	/**
	 * Returns the actions of this node that activate when the node is finished processing.
	 */
	@Nonnull
	Collection<IDialogAction> getEndActions();

	/**
	 * Returns the speaker's resource location. i.e. 'aether:edison'. This could be used
	 * to render speaker animations too, such as 'aether:edison.idle' or 'aether:edision.yelling'.
	 *
	 * If null, the controller should fallback to a default character, such as "defaults:missingno".
	 * @return The {@link ResourceLocation} identifier of the speaker.
	 */
	@Nullable
	ResourceLocation getSpeaker();

	/**
	 * This node's unique ID for use by other parts of the dialog system. Must be unique.
	 * The scene should throw an error if it's not unique.
	 */
	@Nonnull
	String getIdentifier();

}
