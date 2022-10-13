package com.gildedgames.aetherii.api.dialog;

import com.gildedgames.aetherii.api.TalkableController;

public interface IDialogCondition {
	/**
	 * Called when the condition needs to be met.
	 */
	boolean isMet(TalkableController controller);
}