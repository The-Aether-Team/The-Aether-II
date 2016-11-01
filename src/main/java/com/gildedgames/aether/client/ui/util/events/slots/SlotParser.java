package com.gildedgames.aether.client.ui.util.events.slots;

import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;

public interface SlotParser<E>
{

	boolean isAllowed(SlotStack<E> state);

	void onContentsChange(SlotBehavior<E> slot, SlotStack<E> newContents);

	/**
	 * Return true to cancel the normal slot behavior on mouse input.
	 * @param pool
	 * @param input
	 * @return
	 */
	boolean onMouseInput(MouseInputPool pool, InputProvider input);

}
