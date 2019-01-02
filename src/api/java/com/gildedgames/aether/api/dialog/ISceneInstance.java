package com.gildedgames.aether.api.dialog;

import java.util.Map;

public interface ISceneInstance
{
	IDialogScene getScene();

	IDialogNode getNode();

	IDialogLine getLine();

	void navigate(String nodeId);

	void forwards();

	Map<String, Boolean> getConditionsMet();

	void setConditionsMet(Map<String, Boolean> conditionsMet);

	boolean isDoneReading();
}
