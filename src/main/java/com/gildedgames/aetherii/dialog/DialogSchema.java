package com.gildedgames.aetherii.dialog;

import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.gildedgames.aetherii.api.dialog.IDialogButton;
import com.gildedgames.aetherii.api.dialog.IDialogCondition;
import com.gildedgames.aetherii.api.dialog.IDialogLine;
import com.gildedgames.aetherii.api.dialog.IDialogNode;
import com.gildedgames.aetherii.api.dialog.IDialogScene;
import com.google.gson.annotations.SerializedName;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DialogSchema implements IDialogScene {
	@SerializedName("nodes")
	private Collection<DialogNodeSchema> nodes;

	private String startingNodeId;

	@Override
	public Optional<IDialogNode> getNode(final String id) {
		for (final DialogNodeSchema node : this.nodes) {
			if (id.equals(node.getIdentifier())) {
				return Optional.of(node);
			}
		}

		return Optional.empty();
	}

	@Nonnull
	@Override
	public IDialogNode getStartingNode() {
		return this.getNode(this.startingNodeId).orElseThrow(() ->
				new IllegalArgumentException("Couldn't find starting node with id: '" + this.startingNodeId + "'"));
	}

	@Override
	public void setStartingNode(String id) {
		this.startingNodeId = id;
	}

	private class DialogNodeSchema implements IDialogNode {
		@SerializedName("buttons")
		private final List<DialogButtonSchema> buttons = null;

		@SerializedName("end_actions")
		private final Collection<IDialogAction> actions = null;

		@SerializedName("name")
		private String name;

		@SerializedName("lines")
		private List<DialogLineSchema> lines;

		@Nonnull
		@Override
		public List<IDialogLine> getLines() {
			return Collections.unmodifiableList(this.lines);
		}

		@Nonnull
		@Override
		public Collection<IDialogButton> getButtons() {
			return this.buttons == null ? Collections.emptyList() : Collections.unmodifiableList(this.buttons);
		}

		@Nonnull
		@Override
		public Collection<IDialogAction> getEndActions() {
			return this.actions == null ? Collections.emptyList() : Collections.unmodifiableCollection(this.actions);
		}

		@Nonnull
		@Override
		public String getIdentifier() {
			return this.name;
		}
	}

	private class DialogButtonSchema implements IDialogButton {
		@SerializedName("orConditions")
		private Collection<IDialogCondition> orConditions;

		@SerializedName("conditions")
		private Collection<IDialogCondition> conditions;

		@SerializedName("label")
		private String label;

		@SerializedName("actions")
		private Collection<IDialogAction> actions;

		@Nonnull
		@Override
		public Collection<IDialogCondition> getOrConditions() {
			return this.orConditions == null ? Collections.emptyList() : this.orConditions;
		}

		@Nonnull
		@Override
		public Collection<IDialogCondition> getConditions() {
			return this.conditions == null ? Collections.emptyList() : this.conditions;
		}

		@Nonnull
		@Override
		public String getLabel() {
			return this.label;
		}

		@Nonnull
		@Override
		public Component getLocalizedLabel() {
			return Component.translatable(this.label);
		}

		@Nonnull
		@Override
		public Collection<IDialogAction> getActions() {
			return this.actions;
		}
	}

	private class DialogLineSchema implements IDialogLine {
		@SerializedName("talker")
		private final String talker = null;

		@SerializedName("text")
		private String text;

		@Override
		public Component getLocalizedBody() {
			return Component.translatable(this.text);
		}

		@Override
		public Optional<ResourceLocation> getSpeaker() {
			return this.talker != null ? Optional.of(new ResourceLocation(this.talker)) : Optional.empty();
		}

	}
}