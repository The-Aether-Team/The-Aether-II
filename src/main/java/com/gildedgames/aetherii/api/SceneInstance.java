package com.gildedgames.aetherii.api;

import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.gildedgames.aetherii.api.dialog.IDialogButton;
import com.gildedgames.aetherii.api.dialog.IDialogLine;
import com.gildedgames.aetherii.api.dialog.IDialogNode;
import com.gildedgames.aetherii.api.dialog.IDialogScene;
import com.gildedgames.aetherii.api.dialog.scene.ISceneInstance;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SceneInstance implements ISceneInstance {
	private final TalkableController controller;

	private final IDialogScene scene;

	private IDialogNode node;

	private List<IDialogLine> lines;

	private Collection<IDialogButton> buttons;

	private Collection<IDialogAction> endActions;

	private int index;


	protected SceneInstance(final TalkableController controller, final IDialogScene scene) {

		this.controller = controller;
		this.scene = scene;

		this.setNode(this.scene.getStartingNode());
	}

	@Override
	public IDialogScene getScene() {
		return this.scene;
	}

	@Override
	public IDialogNode getNode() {
		return this.node;
	}

	protected void setNode(final IDialogNode node) {
		Validate.notNull(node);

		this.node = node;

		this.lines = node.getLines();
		this.buttons = node.getButtons();
		this.endActions = node.getEndActions();

		this.index = 0;

		this.controller.updateListeners();
	}


	@Override
	public IDialogLine getLine() {
		return this.lines.get(this.index);
	}

	@Override
	public boolean isDoneReading() {
		return this.index >= this.lines.size() - 1;
	}

	@Override
	public void navigate(final String nodeId) {
		final Optional<IDialogNode> node = this.scene.getNode(nodeId);

		this.setNode(node.orElseThrow(() ->
				new IllegalArgumentException("Node " + nodeId + " doesn't exist")));
	}

	@Override
	public void forwards() {
		if (this.isDoneReading()) {
			if (this.buttons.size() <= 0) {
				IDialogNode node = this.getNode();

				for (final IDialogAction action : this.endActions) {
					action.performAction(this.controller);
				}

				// Make sure actions haven't navigated somewhere
				if (this.getNode() == node) {
					this.setNode(this.scene.getStartingNode());
				}
			}
		} else {
			this.index++;

			this.controller.updateListeners();
		}
	}
}