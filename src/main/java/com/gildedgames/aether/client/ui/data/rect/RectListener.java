package com.gildedgames.aether.client.ui.data.rect;

import com.gildedgames.aether.client.ui.data.rect.RectModifier.ModifierType;

import java.util.List;

public interface RectListener
{

	void notifyDimChange(List<ModifierType> changedType);

}
