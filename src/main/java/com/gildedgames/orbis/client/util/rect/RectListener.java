package com.gildedgames.orbis.client.util.rect;

import java.util.List;

public interface RectListener
{

	void notifyDimChange(List<RectModifier.ModifierType> changedType);

}
