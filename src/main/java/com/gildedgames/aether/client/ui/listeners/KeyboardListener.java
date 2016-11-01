package com.gildedgames.aether.client.ui.listeners;

import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.KeyboardInputPool;

public interface KeyboardListener extends Ui
{

	boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input);

}
