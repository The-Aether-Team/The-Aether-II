package com.gildedgames.aether.client.ui.listeners;

import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;

public interface MouseListener extends Ui
{

	void onMouseInput(MouseInputPool pool, InputProvider input);

	void onMouseScroll(int scrollDifference, InputProvider input);

}
