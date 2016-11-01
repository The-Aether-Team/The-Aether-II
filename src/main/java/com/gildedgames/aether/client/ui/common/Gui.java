package com.gildedgames.aether.client.ui.common;

import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;

public interface Gui extends Ui, RectHolder
{

	void draw(Graphics2D graphics, InputProvider input);

	/**
	 * There are 20 ticks in a second.
	 * @return
	 */
	int ticksClosing();

	/**
	 * There are 20 ticks in a second.
	 * @return
	 */
	int ticksOpening();

	boolean isVisible();

	void setVisible(boolean visible);

	boolean isFocused();

	void setFocused(boolean focused);

	/**
	 * @param input
	 * @return True if this object meets the criteria passed through the input
	 */
	boolean query(Object... input);

}
