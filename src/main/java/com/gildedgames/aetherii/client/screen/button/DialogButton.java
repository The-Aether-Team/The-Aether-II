package com.gildedgames.aetherii.client.screen.button;

import com.gildedgames.aetherii.api.dialog.IDialogButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class DialogButton extends Button {
	private final IDialogButton buttonData;

	public DialogButton(int x, int y, int width, int height, IDialogButton buttonData, OnPress onPress) {
		super(x, y, width, height, buttonData.getLocalizedLabel(), onPress);

		this.buttonData = buttonData;
	}

	public IDialogButton getButtonData() {
		return this.buttonData;
	}

	@Override
	public Component getMessage() {
		return this.buttonData.getLocalizedLabel();
	}

	@Override
	public void renderButton(PoseStack p_93746_, int p_93747_, int p_93748_, float p_93749_) {
		super.renderButton(p_93746_, p_93747_, p_93748_, p_93749_);
	}
}
