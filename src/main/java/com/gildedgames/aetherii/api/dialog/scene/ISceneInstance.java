package com.gildedgames.aetherii.api.dialog.scene;

import com.gildedgames.aetherii.api.dialog.IDialogLine;
import com.gildedgames.aetherii.api.dialog.IDialogNode;
import com.gildedgames.aetherii.api.dialog.IDialogScene;

public interface ISceneInstance {
	IDialogScene getScene();

	IDialogNode getNode();

	IDialogLine getLine();

	void navigate(String nodeId);

	void forwards();

	boolean isDoneReading();
}