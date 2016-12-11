package com.gildedgames.aether.common.dialog.util;

import com.gildedgames.aether.common.dialog.IDialogAction;
import com.gildedgames.aether.common.dialog.IDialogController;

public class DialogActionBackToRoot implements IDialogAction
{

	@Override
	public void onAction(IDialogController controller) {
		controller.setNode(controller.getCurrentScene().getRootNode().getIdentifier());
	}

}
