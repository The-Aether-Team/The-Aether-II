package com.gildedgames.aetherii.register;

import com.gildedgames.aetherii.dialog.DialogManager;

public class ContentRegistry {
	private static final DialogManager dialogManager = new DialogManager(true);

	public static void preInit() {

	}

	public static DialogManager getDialogManager() {
		return dialogManager;
	}
}
