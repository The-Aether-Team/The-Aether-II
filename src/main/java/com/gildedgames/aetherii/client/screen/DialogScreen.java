package com.gildedgames.aetherii.client.screen;

import com.gildedgames.aetherii.api.TalkableController;
import com.gildedgames.aetherii.api.dialog.IDialog;
import com.gildedgames.aetherii.api.dialog.IDialogButton;
import com.gildedgames.aetherii.api.dialog.IDialogChangeListener;
import com.gildedgames.aetherii.api.dialog.IDialogLine;
import com.gildedgames.aetherii.api.dialog.IDialogNode;
import com.gildedgames.aetherii.api.dialog.IDialogRenderer;
import com.gildedgames.aetherii.api.dialog.IDialogTalker;
import com.gildedgames.aetherii.api.dialog.scene.ISceneInstance;
import com.gildedgames.aetherii.api.entity.TalkableMob;
import com.gildedgames.aetherii.client.screen.button.DialogButton;
import com.gildedgames.aetherii.register.ContentRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.Map;

public class DialogScreen extends Screen implements IDialogChangeListener {
	public static boolean preventDialogControllerClose;

	private final TalkableController controller;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction = true;

	private IDialogNode node;

	private IDialogTalker talker;

	private IDialog dialog;

	private IDialogRenderer renderer;


	private ISceneInstance sceneInstance;

	private ResourceLocation talkerTexture;

	public DialogScreen(Player player, TalkableMob living) {
		super(GameNarrator.NO_TITLE);

		this.controller = living.getTalkableController();
	}

	@Override
	protected void init() {
		super.init();
		this.onDialogChanged();
	}


	@Override
	public void render(PoseStack poseStack, int p_96563_, int p_96564_, float p_96565_) {
		this.renderBackground(poseStack);
		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize) {
			baseBoxSize = this.width - 40;
		}

		if (this.dialog != null && this.renderer != null) {
			this.renderer.draw(this.dialog, poseStack, this.width, this.height, p_96563_, p_96564_, p_96565_);
		}

		final String name = I18n.get(this.talker.getUnlocalizedName());

		final Component textComponent = Component.translatable(name);

		this.font.draw(poseStack, textComponent, resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
				this.height - (107), 0xFAEB95);

		final IDialogLine line = this.controller.getCurrentLine();


		final Component textComponent2 = Component.translatable(line.getLocalizedBody().getString(), Minecraft.getInstance().getUser().getName());

		this.font.draw(poseStack, textComponent2, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, 0xFFFFFF);

		super.render(poseStack, p_96563_, p_96564_, p_96565_);
	}


	@Override
	public void onDialogChanged() {
		this.buildGui(this.controller.getSceneInstance().getNode());
	}

	@Override
	public void beforeSceneCloses() {

	}

	private void buildGui(final IDialogNode node) {
		if (this.node != node) {
			this.canApplyNextAction = false;
		}

		this.node = node;

		this.resetGui();

		final Collection<IDialogButton> beforeConditionButtons = node.getButtons();

		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize) {
			baseBoxSize = this.width - 40;
		}

		if (this.controller.isNodeFinished()) {
			int index = 0;

			for (final IDialogButton btn : beforeConditionButtons) {
				if (this.controller.conditionsMet(btn)) {
					Button button = new DialogButton((this.width / 2) - (baseBoxSize / 2), this.height - 65 + (index * 20), 200, 20, btn, (button2) -> {
						this.controller.activateButton(btn);
					});

					this.addRenderableWidget(button);
					button.visible = true;

					index++;
				}
			}
		}

		/*if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			this.topTextBox.setText(line.getLocalizedBody());
		}
		else
		{
			this.bottomTextBox.setText(line.getLocalizedBody());
		}*/

		if (this.controller.getCurrentLine().getSpeaker().isPresent()) {
			final ResourceLocation talkerPath = this.controller.getCurrentLine().getSpeaker().get();

			this.talker = ContentRegistry.getDialogManager().getTalker(talkerPath).orElseThrow(() ->
					new IllegalArgumentException("Couldn't getByte talker: " + talkerPath));

			final String address;

			// Check if the talker resourcelocation has a dialog address
			if (talkerPath.getPath().contains("_")) {
				// Obtain the dialog address from the Speaker resourcelocation
				address = talkerPath.getPath().substring(talkerPath.getPath().indexOf("_") + 1);

				this.dialog = ContentRegistry.getDialogManager().findDialog(address, this.talker).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find dialog: " + address));
			} else if (this.talker.getDialogs().isPresent()) {
				final Map<String, IDialog> dialogs = this.talker.getDialogs().get();

				if (!dialogs.isEmpty() && dialogs.containsKey("default")) {
					this.dialog = dialogs.get("default");
				}
			}

			if (this.dialog != null && this.dialog.getRenderer().isPresent()) {
				final String renderType = this.dialog.getRenderer().get();

				this.renderer = ContentRegistry.getDialogManager().findRenderer(renderType).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find dialog renderer: " + renderType));

				this.renderer.setup(this.dialog);
			}

			final boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;


		}
	}

	private void resetGui() {
		this.clearWidgets();
	}

	private void nextAction() {
		if (!this.canApplyNextAction) {
			this.canApplyNextAction = true;

			return;
		}

		this.controller.advance();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
