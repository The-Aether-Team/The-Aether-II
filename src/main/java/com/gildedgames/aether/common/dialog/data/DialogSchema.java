package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.*;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DialogSchema implements IDialogScene
{
	@SerializedName("nodes")
	private Collection<DialogNodeSchema> nodes;

	@Override
	public Optional<IDialogNode> getNode(final String id)
	{
		for (final DialogNodeSchema node : this.nodes)
		{
			if (id.equals(node.getIdentifier()))
			{
				return Optional.of(node);
			}
		}

		return Optional.empty();
	}

	@Nonnull
	@Override
	public IDialogNode getStartingNode()
	{
		return this.getNode("#start").orElseThrow(() ->
				new IllegalArgumentException("Couldn't find node with id: '#start'"));
	}

	private class DialogNodeSchema implements IDialogNode
	{
		@SerializedName("buttons")
		private final Collection<DialogButtonSchema> buttons = null;

		@SerializedName("end_actions")
		private final Collection<IDialogAction> actions = null;

		@SerializedName("name")
		private String name;

		@SerializedName("lines")
		private List<DialogLineSchema> lines;

		@Nonnull
		@Override
		public List<IDialogLine> getLines()
		{
			return Collections.unmodifiableList(this.lines);
		}

		@Nonnull
		@Override
		public Collection<IDialogButton> getButtons()
		{
			return this.buttons == null ? Collections.emptyList() : Collections.unmodifiableCollection(this.buttons);
		}

		@Nonnull
		@Override
		public Collection<IDialogAction> getEndActions()
		{
			return this.actions == null ? Collections.emptyList() : Collections.unmodifiableCollection(this.actions);
		}

		@Nonnull
		@Override
		public String getIdentifier()
		{
			return this.name;
		}
	}

	private class DialogButtonSchema implements IDialogButton
	{
		@SerializedName("label")
		private String label;

		@SerializedName("actions")
		private Collection<IDialogAction> actions;

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
			return new TextComponentTranslation(this.label);
		}

		@Nonnull
		@Override
		public Collection<IDialogAction> getActions()
		{
			return this.actions;
		}
	}

	private class DialogLineSchema implements IDialogLine
	{
		@SerializedName("speaker")
		private final String speaker = null;

		@SerializedName("text")
		private String text;

		@Override
		public ITextComponent getLocalizedBody()
		{
			return new TextComponentTranslation(this.text);
		}

		@Override
		public Optional<ResourceLocation> getSpeaker()
		{
			return this.speaker != null ? Optional.of(new ResourceLocation(this.speaker)) : Optional.empty();
		}

	}
}
